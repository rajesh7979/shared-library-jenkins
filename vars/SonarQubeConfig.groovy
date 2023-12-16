package com.acme.jenkins // Update with your package name

import org.yaml.snakeyaml.Yaml

class SonarQubeConfig {
    def static getConfig() {
        def workspace = pwd() // Get the workspace path
        def configFile = "${workspace}/params.yaml" // YAML file path in the workspace
        def sonarConfig = [:]

        try {
            def yaml = new org.yaml.snakeyaml.Yaml()
            def inputStream = new FileInputStream(new File(configFile))
            sonarConfig = yaml.load(inputStream)
        } catch (Exception e) {
            println("Failed to load SonarQube configuration: ${e.message}")
        }

        return sonarConfig.sonarqube ?: [:]
    }
}
