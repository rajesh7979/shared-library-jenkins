def call(String project,String ImageTag, String hubUser){

    sh """
      sudo  docker image build -t ${hubUser}/${project} .
      sudo  docker image tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}
      sudo  docker image tag ${hubUser}/${project} ${hubUser}/${project}:latest
    """
}
