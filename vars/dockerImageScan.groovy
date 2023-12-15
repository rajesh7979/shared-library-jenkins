
  def call() {
    // Load Trivy parameters and perform Trivy scan
   def loadTrivyParameters() {
    try {
        def libraryRoot = libraryResource.rootDir.toString()
        def paramsFile = readYaml(file: "${libraryRoot}/../resources/params.yaml")

        // Access Trivy parameters
        def trivyImage = paramsFile.TRIVY_IMAGE
        def dockerfilePath = paramsFile.DOCKERFILE_PATH
        def targetImage = paramsFile.TARGET_IMAGE
        def severityThreshold = paramsFile.SEVERITY_THRESHOLD
        def ignoreUnfixed = paramsFile.IGNORE_UNFIXED

        // Use or process the parameters here

    } catch (Exception e) {
        error "Error loading Trivy parameters: ${e.message}"
    }
}

}
