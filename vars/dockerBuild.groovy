def call(String project,String ImageTag, String hubUser){

    sh -s """
      docker image build -t ${hubUser}/${project} .
      docker image tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}
      docker image tag ${hubUser}/${project} ${hubUser}/${project}:latest
    """
}
