// loadConfig.groovy in your shared library under 'vars' directory

def call() {
    def loadNotificationConfigFromYaml() {
        def yamlContent = readYaml(file: "${libraryResource.rootDir}/resources/params.yaml")

        return yamlContent.notificationConfig ?: [:]
    }

    return this
}
