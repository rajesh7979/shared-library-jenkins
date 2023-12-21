def call(String jfrogArtifactory, String imageName2) { 
  sh "docker login -u ${jfrog_username} fisdemo1.jfrog.io -p ${jfrog_pass}"
  sh "docker tag ${imageName2} ${jfrogArtifactory}/${imageName2}:latest"
  sh "docker push fisdemo1.jfrog.io/demo-fis-docker-docker-local/${imageName2}:latest"
}
