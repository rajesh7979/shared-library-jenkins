def getTeamNotification(teamName) {
    def configFile = "${WORKSPACE}/params.yaml" // Assuming the file is in the Jenkins job's workspace
    def notificationConfig = [:]

    try {
        def configFileContent = readFile configFile
        notificationConfig = readYaml text: configFileContent
    } catch (FileNotFoundException e) {
        println("File not found: ${configFile}")
    } catch (Exception e) {
        println("Failed to load notification configuration: ${e.message}")
    }

    return notificationConfig?.teams[teamName] ?: [:]
}
