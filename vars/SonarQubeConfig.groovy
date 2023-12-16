
import org.yaml.snakeyaml.Yaml

def getSonarQubeConfig() {
    def workspace = env.WORKSPACE
    def configFile = "${workspace}/params.yaml" // YAML file path in the workspace
    def sonarConfig = [:]

    try {
        def yaml = new Yaml()
        def inputStream = new FileInputStream(new File(configFile))
        sonarConfig = yaml.load(inputStream)
    } catch (Exception e) {
        println("Failed to load SonarQube configuration: ${e.message}")
    }

    return sonarConfig?.sonarqube ?: [:]
}
