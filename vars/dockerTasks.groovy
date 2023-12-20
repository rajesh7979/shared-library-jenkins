def call(Map params) {
    return {
        // Extract parameters or provide defaults
        def dockerfilePath = params.dockerfilePath ?: 'docker/Dockerfile'
        def imageName = params.imageName ?: 'my-image'
        def imageTag = params.imageTag ?: 'latest'  // Default tag is 'latest'
        
        // Building Docker image using the provided Dockerfile path, image name, and tag
        sh "docker build -t ${imageName}:${imageTag} -f ${dockerfilePath} ."
    }
}
