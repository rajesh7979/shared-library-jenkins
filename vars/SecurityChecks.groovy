def getTrivyConfig() {
    def configFile = "${WORKSPACE}/params.yaml" // Assuming the file is in the Jenkins job's workspace
    def configFileContent = readFile(file: configFile)

    def slurper = new groovy.yaml.YamlSlurper()
    def config = slurper.parseText(configFileContent)

    return config.trivy ?: [:]
}
