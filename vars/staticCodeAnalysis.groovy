def call(credentialsId){
    withSonarQubeEnv(credentialsId: sonar-token) {
       sh 'mvn clean package sonar:sonar'
   } 
}
