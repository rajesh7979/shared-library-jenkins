def call(String jfrogArtifactory, String imageName, String username, String password) { 
  sh "docker login -u ${username} fisdemo1.jfrog.io -p ${password}"
  sh "docker tag ${imageName} ${jfrogArtifactory}/${imageName}:latest"
  sh "docker push fisdemo1.jfrog.io/demo-fis-docker-docker-local/${imageName}:latest"
}
