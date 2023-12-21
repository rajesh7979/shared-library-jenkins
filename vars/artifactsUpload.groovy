def call(String jfrogArtifactory, String imageName) { 
  sh "docker login -u ${jfrog_username} fisdemo1.jfrog.io -p ${jfrog_pass}"
  sh "docker tag ${imageName} ${jfrogArtifactory}/${imageName}:latest"
  sh "docker push fisdemo1.jfrog.io/demo-fis-docker-docker-local/${imageName}:latest"
}
