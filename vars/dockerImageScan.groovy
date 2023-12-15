
    // Evaluate Trivy scan result and fail the build if required
    if (buildOnFailure && trivyScanResult != 0) {
        error "Trivy found vulnerabilities that exceed the specified threshold. Failing the build."
    } else {
        echo "Trivy scan completed. Proceeding with the build."
    }
}

