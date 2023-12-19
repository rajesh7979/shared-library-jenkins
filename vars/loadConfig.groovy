// loadConfig.groovy in your shared library under 'vars' directory

def loadNotificationConfigFromYaml() {
    def yamlContent = readYaml file: "${libraryResource.rootDir}/resources/params.yaml"
    return yamlContent.notificationConfig ?: [:]
}
