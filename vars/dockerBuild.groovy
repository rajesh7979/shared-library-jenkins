def call(String project,String ImageTag, String hubUser){

    sh """
     sudo -S docker image build -t ${hubUser}/${project} .
      sudo -S docker image tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}
      sudo -S docker image tag ${hubUser}/${project} ${hubUser}/${project}:latest
    """
}
