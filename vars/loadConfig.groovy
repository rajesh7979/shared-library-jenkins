// loadConfig.groovy in your shared library under 'vars' directory

// loadConfig.groovy in your shared library under 'vars' directory

def call() {
    return this
}

def loadNotificationConfigFromYaml() {
    def yamlContent = readYaml file: "${WORKSPACE}/config.yaml"
    return yamlContent.notificationConfig ?: [:]
}
