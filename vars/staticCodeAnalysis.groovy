def call(credentialsId){
    withSonarQubeEnv(credentialsId: sonar-token) {
        sh 'mvn sonar:sonar'
   } 
}
