def call(credentialsId){
    withSonarQubeEnv('sonar') {
        sh 'mvn sonar:sonar'
    }
//    withSonarQubeEnv(credentialsId: sonar-token) {
//        sh 'mvn sonar:sonar'
//   } 
}
