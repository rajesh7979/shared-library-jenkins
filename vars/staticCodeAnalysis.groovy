// SonarUtils.groovy in shared library

def call() {
  withSonarQubeEnv('sonar-server') {
        sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.host.url=http://172.203.233.93:9000 -Dsonar.projectName=petclinic -Dsonar.projectKey=petclinic -Dsonar.token=sqa_afd1f2a998809c2a3506faa46cedcb8ef1fe06d6 '''
    }
}

def runSonarAnalysis(String petclinic, String petclinic) {
    // Fetch SonarQube server details from Jenkins global configuration
    def sonarScannerHome = tool 'SonarQubeScanner' // Name of the SonarQube Scanner tool in Jenkins

    // Run SonarQube analysis
    sh "${sonarScannerHome}/bin/sonar-scanner -Dsonar.projectName=${projectName} -Dsonar.projectKey=${projectKey}"
}



/*def call(credentialsId){
    withSonarQubeEnv('sonar') {
        sh 'mvn sonar:sonar'
    }*/
//    withSonarQubeEnv(credentialsId: sonar-token) {
//        sh 'mvn sonar:sonar'
//   } 
}
