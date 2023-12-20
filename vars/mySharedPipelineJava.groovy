@Library('shared-library-jenkins') _
def configUtils = loadConfig()

// def configFile = "${WORKSPACE}/params.yaml" // Assuming the YAML file is in the Jenkins job's workspace
// def configFileContent = readFile(file: configFile)

// def slurper = new groovy.yaml.YamlSlurper()
// def config = slurper.parseText(configFileContent)

// def trivyConfig = config.trivy // Access the Trivy configuration

                   // Use Trivy configuration values as needed
// def severityThreshold = trivyConfig.severityThreshold
// def vulnThreshold = trivyConfig.vulnThreshold
def call(String repositoryURL) {
pipeline {
  agent any
 /* tools {
      maven 'maven3'
      jdk 'JDK8'
      jfrog 'cli'
    }*/
   environment {
     //   DOCKER_USERNAME = credentials('JFROG_USER')
    //	DOCKER_IMAGE_NAME = "$DOCKER_REGISTRY/$DOCKER_REPO/webapp.${env.BUILD_NUMBER}"
      //  ARTIFACTORY_ACCESS_TOKEN = credentials('jf_access_token')
        WEBHOOK_URL = credentials("teams_webhook")
      //  BUILD_NO = "${env.BUILD_NUMBER}"
    //	DOCKER_REGISTRY = 'fisdemo1.jfrog.io'
     //   DOCKER_REPO = 'fis-demo-dockerhub-docker-local'
     //   DOCKER_PASSWORD = credentials('JFROG_PASSWORD')
    //	DOCKER_PASS = credentials('w_docker_pass')
    //	DOCKER_TAG = 'latest'
    }
    parameters {
        choice(name: 'action', choices:'create\ndelete', description: 'Choose create/destroy')
        string(name: 'ImageName', description: 'name of the docker build', defaultValue: 'javapp')
        string(name: 'ImageTag', description: 'Tag of the docker build', defaultValue: 'v1')
        string(name: 'DockerHubUser', description: 'name of App', defaultValue: 'rajkavala')
        
    }
	
    stages {
        
      stage ('clean workspace') {
      steps {
        cleanWorkspace()
        }  
      }
        
      stage('Checkout Repository') {
        when { expression { params.action == 'create'} }		      
                steps {
                    script {
                        // Checkout the repository dynamically based on the passed URL
                        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], userRemoteConfigs: [[url: repositoryURL]]])
                    }
                }
            }
                    
        stage('Maven Build'){
        when { expression { params.action == 'create'} }

               steps {
                  script {
                    mvnBuild()
                  }
               }  
        }
       /* stage('Sonarqube Static Code Analysis'){

        when { expression { params.action == 'create'} }

               steps {
                  script {
                    def projectName = 'petclinic'
                    def projectKey = 'petclinic'

                    SonarUtils.runSonarAnalysis(petclinic, petclinic)
                  //  staticCodeAnalysis()
                  }
               }  
        }*/
      stage('Parallel Stages') {
       parallel {
         stage('SonarQube check Analysis') {
            
          when { expression { params.action == 'create'} }
            steps {
                script {
                    def workspace = pwd() // Get the workspace path
                    def configFile = "${workspace}/params.yaml" // YAML file path in the workspace
                    def sonarConfig = [:]

                    try {
                        def yaml = new org.yaml.snakeyaml.Yaml()
                        def inputStream = new FileInputStream(new File(configFile))
                        sonarConfig = yaml.load(inputStream)
                    } catch (Exception e) {
                        println("Failed to load SonarQube configuration: ${e.message}")
                    }

                    // Accessing quality gate parameters
                    def passCondition = sonarConfig?.sonarqube?.qualityGate?.passCondition
                    def threshold = sonarConfig?.sonarqube?.qualityGate?.threshold

                    echo "SonarQube Quality Gate Pass Condition: ${passCondition}"
                    echo "SonarQube Quality Gate Threshold: ${threshold}"

                    // Perform SonarQube analysis and quality gate checks here
                    // Use passCondition and threshold as needed in your analysis steps
                }
            }
        }    
        
        stage('SonarQube test') {
            
         when { expression { params.action == 'create'} }
            steps {
                script {
                    def sonarConfig = new SonarQubeConfig().getSonarQubeConfig()
                        // Accessing quality gate parameters
                    def passCondition = sonarConfig?.qualityGate?.passCondition
                    def threshold = sonarConfig?.qualityGate?.threshold

                    echo "SonarQube Quality Gate Pass Condition: ${passCondition}"
                    echo "SonarQube Quality Gate Threshold: ${threshold}"

                    // Perform SonarQube analysis and quality gate checks here
                    // Use passCondition and threshold as needed in your analysis steps
                }
            }
        }    
        
        
        stage('Checkstyle Analysis'){
        when { expression { params.action == 'create'} }

               steps {
                  script {
                    checkStyle()
            
                  }
               }  
         }
        
        stage('Docker Image Build'){

        when { expression { params.action == 'create'} }

               steps {
                  script {
                    dockerBuild("${params.ImageName}","${params.ImageTag}","${params.DockerHubUser}")
                  }
               }  
        }
       stage('Trivy Docker Image Scan'){

        when { expression { params.action == 'create'} }
            steps {
                
                dockerImageScan()
                    // Using function from the shared library
                //    dockerImageScan("${params.ImageName}","${params.ImageTag}","${params.DockerHubUser}")
                 //   loadTrivyParametersAndGenerateReport()
            }
         }
        }
      }    
     /*   stage('Trivy Scan result') {
             when { expression { params.action == 'create'} }
            steps {
                script {
                    // Run Trivy scan and collect results
                    def vulnerabilitiesCount = sh(script: 'trivy --format=json image:tag | jq length', returnStdout: true).trim() // Count vulnerabilities found by Trivy
                    def trivyOutput = sh(script: 'trivy --format=json ${params.ImageName}:${params.ImageTag}', returnStdout: true).trim()
                    def trivyJSON = readJSON text: trivyOutput

                    def highSeverityVulns = trivyJSON.findAll { it.Severity == 'HIGH' } // Adjust according to Trivy's output
                    def highSeverityCount = highSeverityVulns.size()
             //       def highSeverityCount =  // Count high severity vulnerabilities

                    // Check if the conditions for build failure are met
                    if (highSeverityCount > 0 || vulnerabilitiesCount >= trivyConfig.vulnThreshold) {
                        currentBuild.result = 'FAILURE'
                        error "Trivy scan detected vulnerabilities that exceed thresholds"
                    } else {
                        echo "Trivy scan passed"
                    }
                }
            }
        }*/    
        stage('Push Docker Image on DockerHub'){

        when { expression { params.action == 'create'} }

               steps {
                  script {
                    dockerImagePush("${params.ImageName}","${params.ImageTag}","${params.DockerHubUser}")
                  }
               }  
        }
        
    }
    
post {
        success {
            script {
                def config = configUtils.loadNotificationConfigFromYaml()
                def teamAWebhook = config.teamAWebhook
                echo "Success! Team A Webhook URL: ${teamAWebhook}"
            //    sendTeamsNotification(teamAWebhook, "Success Message")
                sendTeamsNotification('${WEBHOOK_URL}', "Build Successful", "success")
                
                }

        //        if (teamBConfig.notificationEnabled) {
        //            sendTeamsMessage(teamBConfig.teamsWebhook, "Build Success: Team B")
        //        }
            }

        failure {
            script {
    
                def config = configUtils.loadNotificationConfigFromYaml()
                def teamAWebhook = config.teamAWebhook
                echo "Failure! Team A Webhook URL: ${teamAWebhook}"
                sendTeamsNotification('${WEBHOOK_URL}', "Build Failed", "alert")
           
             //   if (teamBConfig.notificationEnabled) {
              //      sendTeamsMessage(teamBConfig.teamsWebhook, "Build Failure: Team B")
             //   }
            }
        } 
    }        

  }    
}	
	
  
def sendTeamsNotification(webhookUrl, message, color) {
    def currentBuildUrl = "${env.BUILD_URL}"
    def buildNumber = "${env.BUILD_NUMBER}"
    def jobName = "${env.JOB_NAME}"
    def payload = """
    {
        "themeColor": "${color}",
        "title": "${message}",
        "text": "Build Number: ${buildNumber}\nJob Name: ${jobName}\nBuild Details: ${currentBuildUrl}"
    }
    """
    sh "curl -X POST -H 'Content-Type: application/json' -d '${payload}' ${WEBHOOK_URL}"
}  

	

/*def sendTeamsNotification(webhookUrl, message) {
    def payload = "{\"text\": \"${message}\"}"
    sh "curl -X POST -H 'Content-Type: application/json' -d '${payload}' ${WEBHOOK_URL}"
}*/
