def getSonarQubeConfig() {
    def workspace = env.WORKSPACE
    def configFile = "${workspace}/params.yaml" // YAML file path in the workspace
    def sonarConfig = [:]

    try {
        def configFileContent = readFile configFile
        sonarConfig = readJSON text: configFileContent
    } catch (Exception e) {
        println("Failed to load SonarQube configuration: ${e.message}")
    }

    return sonarConfig?.sonarqube ?: [:]
}

        
