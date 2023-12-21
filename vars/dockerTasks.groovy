// vars/Docker.groovy

def callBuildDockerImage(String dockerDirectory, String dockerfilePath, String imageName) {
    def dockerCmd = "docker build -t $imageName -f $dockerfilePath $dockerDirectory"
    
    def proc = dockerCmd.execute()
    proc.waitFor()

    if (proc.exitValue() != 0) {
        throw new Exception("Failed to build Docker image")
    }

    return imageName
}

// Other Docker-related functions could be defined here as standalone functions
