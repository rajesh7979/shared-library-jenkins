def call(String ImageName,String ImageTag, String hubUser){

    withCredentials([usernamePassword(
    credentialsId: 'Docker-hub',
    passwordVariable: 'PASS', 
    usernameVariable: 'USER'
    )]) {
        sh "docker login -u '$USER' -p '$PASS'"
    }
    sh "docker image push ${hubUser}/${ImageName}:${ImageTag}"
}

//    sh "docker image push ${hubUser}/${ImageName}:latest"
