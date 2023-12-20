def call(Map params) {
    return {
        // Extract parameters or provide defaults
        def dockerfilePath = params.dockerfilePath ?: 'docker/Dockerfile'
        def imageName = params.imageName ?: 'my-image'
        
        // Building Docker image using the provided Dockerfile path and image name
        sh "docker build -t ${imageName} -f ${dockerfilePath} ."
    }
}
