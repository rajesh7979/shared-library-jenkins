def call() {
    return this
}

def loadNotificationConfigFromYaml() {
    def yamlContent = readYaml(file: "${libraryResource.rootDir}/resources/params.yaml")
    return yamlContent.notificationConfig ?: [:]
}
