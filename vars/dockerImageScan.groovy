
  def call() {
    // Load Trivy parameters and perform Trivy scan
    def loadTrivyParameters() {
        def libraryRoot = libraryResource.rootDir.toString()
        def paramsFile = readYaml(file: "${libraryRoot}/../resources/params.yaml")

        // Access Trivy parameters
        def trivyImage = paramsFile.TRIVY_IMAGE
        def dockerfilePath = paramsFile.DOCKERFILE_PATH
        def targetImage = paramsFile.TARGET_IMAGE
        def severityThreshold = paramsFile.SEVERITY_THRESHOLD
        def ignoreUnfixed = paramsFile.IGNORE_UNFIXED
        def generateHtmlReport = paramsFile.GENERATE_HTML_REPORT
        def reportOutputPath = paramsFile.REPORT_OUTPUT_PATH
        def buildOnFailure = paramsFile.BUILD_ON_FAILURE

        // Execute Trivy scan
        def trivyCommand = "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock ${trivyImage} --file ${dockerfilePath} --severity ${severityThreshold.toLowerCase()} ${ignoreUnfixed ? '--ignore-unfixed' : ''} ${targetImage}"

        // Include report generation if enabled
        if (generateHtmlReport) {
            trivyCommand += " --format html --output ${reportOutputPath}"
        }

        // Execute the Trivy command
        def trivyScanResult = sh(script: trivyCommand, returnStatus: true)

        // Check for build pass or fail based on vulnerability severity
        if (buildOnFailure && trivyScanResult != 0) {
            error "Trivy found vulnerabilities that exceed the specified threshold. Failing the build."
        } else {
            echo "Trivy scan completed without critical vulnerabilities. Proceeding with the build."
        }
    }

    // Call the function to load Trivy parameters and perform the scan
    loadTrivyParameters()
}
