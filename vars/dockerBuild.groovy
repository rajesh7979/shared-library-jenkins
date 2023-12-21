def call(String project,String ImageTag, String hubUser){

    sh """
      docker image build -t ${imageName}:${tag} -f ${dockerfilePath} ${dockerDirectory}
 //     docker image tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}
   //   docker image tag ${hubUser}/${project} ${hubUser}/${project}:latest
    """
}
