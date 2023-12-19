// loadConfig.groovy in your shared library under 'vars' directory

def call() {
    return this
}

def loadNotificationConfigFromYaml() {
    def yamlContent = readYaml file: "${WORKSPACE}/params.yaml"
    return yamlContent.notificationConfig ?: [:]
}
