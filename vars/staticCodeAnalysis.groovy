// SonarUtils.groovy in shared library

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
