// vars/Docker.groovy

def buildDockerImage(String dockerDirectory, String dockerfilePath, String imageName, String tag) {
    def dockerCmd = "docker build -t ${imageName}:${tag} -f ${dockerfilePath} ${dockerDirectory}"
    
    def proc = dockerCmd.execute()
    proc.waitFor()

    if (proc.exitValue() != 0) {
        throw new Exception("Failed to build Docker image")
    }

    return "${imageName}:${tag}"
}

// Other Docker-related functions could be defined here as standalone functions
