def call(String project,String ImageTag, String hubUser){

    sh """
      sudo -s docker image build -t ${hubUser}/${project} .
      sudo -s docker image tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}
      sudo -s  docker image tag ${hubUser}/${project} ${hubUser}/${project}:latest
    """
}
