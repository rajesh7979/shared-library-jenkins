class NotificationConfig {
    def getTeamNotification(teamName) {
        def workspace = env.WORKSPACE
        def configFile = "${workspace}/params.yaml"
        def notificationConfig = [:]

        try {
            def configFileContent = readFile configFile
            notificationConfig = readYaml text: configFileContent
        } catch (Exception e) {
            println("Failed to load notification configuration: ${e.message}")
        }

        def teamConfig = notificationConfig?.teams?.find { it.key == teamName }?.value
        return teamConfig ?: [:]
    }
}
