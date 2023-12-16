def call() {
    def loadTrivyParameters = {
        try {
            def paramsFile = readFile("../resources/params.yaml")
            def slurper = new groovy.json.JsonSlurper()
            def params = slurper.parseText(paramsFile)

         //   def params = new groovy.yaml.Yaml().parseText(paramsFile)

            // Access Trivy parameters
            def trivyImage = params.TRIVY_IMAGE
            def dockerfilePath = params.DOCKERFILE_PATH
            def targetImage = params.TARGET_IMAGE
            def severityThreshold = params.SEVERITY_THRESHOLD
            def ignoreUnfixed = params.IGNORE_UNFIXED

            echo "Trivy Image: ${trivyImage}"
            echo "Dockerfile Path: ${dockerfilePath}"
            echo "Target Image: ${targetImage}"
            echo "Severity Threshold: ${severityThreshold}"
            echo "Ignore Unfixed: ${ignoreUnfixed}"

            // Execute Trivy scan using the loaded parameters
            sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock ${trivyImage} --file ${dockerfilePath} --severity ${severityThreshold.toLowerCase()} ${ignoreUnfixed ? '--ignore-unfixed' : ''} ${targetImage}"
        } catch (Exception e) {
            error "Error loading Trivy parameters: ${e.message}"
        }
    }

    // Call the closure to load Trivy parameters and perform the scan
    loadTrivyParameters()
}


/*def call() {
    def loadTrivyParameters = {
        try {
          //  def libraryRoot = libraryResource.rootDir.toString()
            def paramsFile = readYaml(file: "../resources/params.yaml")

            // Access Trivy parameters
            def trivyImage = paramsFile.TRIVY_IMAGE
            def dockerfilePath = paramsFile.DOCKERFILE_PATH
            def targetImage = paramsFile.TARGET_IMAGE
            def severityThreshold = paramsFile.SEVERITY_THRESHOLD
            def ignoreUnfixed = paramsFile.IGNORE_UNFIXED

            echo "Trivy Image: ${trivyImage}"
            echo "Dockerfile Path: ${dockerfilePath}"
            echo "Target Image: ${targetImage}"
            echo "Severity Threshold: ${severityThreshold}"
            echo "Ignore Unfixed: ${ignoreUnfixed}"

            // Execute Trivy scan using the loaded parameters
            sh "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock ${trivyImage} --file ${dockerfilePath} --severity ${severityThreshold.toLowerCase()} ${ignoreUnfixed ? '--ignore-unfixed' : ''} ${targetImage}"
        } catch (Exception e) {
            error "Error loading Trivy parameters: ${e.message}"
        }
    }

    // Call the closure to load Trivy parameters and perform the scan
    loadTrivyParameters()
}*/
