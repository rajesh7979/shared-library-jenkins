def call(String jfrogArtifactory, String imageName) { 
  sh "docker login -u ${jfrog_username} fisdemo1.jfrog.io -p ${jfrog_pass}"
  sh "docker build -t ${jfrogArtifactory}/${imageName}:latest -f docker/Dockerfile ."
  sh "docker tag ${jfrogArtifactory}/${imageName} ${jfrogArtifactory}/${imageName}:latest"
  sh "docker push ${jfrogArtifactory}/${imageName}:latest"
}
