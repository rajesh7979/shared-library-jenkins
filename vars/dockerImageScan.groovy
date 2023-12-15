// Load Trivy parameters with thresholds and report generation from params.yaml
def call() {
    def libraryRoot = libraryResource.rootDir.toString()
    def paramsFile = readYaml(file: "${libraryRoot}/params.yaml")

    // Accessing Trivy parameters with thresholds and report generation from the loaded YAML file
    def trivyImage = paramsFile.TRIVY_IMAGE
    def dockerfilePath = paramsFile.DOCKERFILE_PATH
    def targetImage = paramsFile.TARGET_IMAGE
    def severityThreshold = paramsFile.SEVERITY_THRESHOLD
    def ignoreUnfixed = paramsFile.IGNORE_UNFIXED
    def buildOnFailure = paramsFile.BUILD_ON_FAILURE
    def generateHtmlReport = paramsFile.GENERATE_HTML_REPORT
    def reportOutputPath = paramsFile.REPORT_OUTPUT_PATH

    echo "Trivy Image: ${trivyImage}"
    echo "Dockerfile Path: ${dockerfilePath}"
    echo "Target Image: ${targetImage}"
    echo "Severity Threshold: ${severityThreshold}"
    echo "Ignore Unfixed: ${ignoreUnfixed}"
    echo "Build On Failure: ${buildOnFailure}"
    echo "Generate HTML Report: ${generateHtmlReport}"
    echo "Report Output Path: ${reportOutputPath}"

    // Running Trivy scan command using these parameters
    def trivyScanCommand = "docker run --rm -v /var/run/docker.sock:/var/run/docker.sock ${trivyImage} --file ${dockerfilePath} --severity ${severityThreshold.toLowerCase()} ${ignoreUnfixed ? '--ignore-unfixed' : ''} ${targetImage}"

    // Generate HTML report if required
    if (generateHtmlReport) {
        trivyScanCommand += " --format html --output ${reportOutputPath}"
    }

    // Run Trivy scan command
    def trivyScanResult = sh(script: trivyScanCommand, returnStatus: true)

    // Evaluate Trivy scan result and fail the build if required
    if (buildOnFailure && trivyScanResult != 0) {
        error "Trivy found vulnerabilities that exceed the specified threshold. Failing the build."
    } else {
        echo "Trivy scan completed. Proceeding with the build."
    }
}

