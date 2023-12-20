// File: vars/mavenTasks.groovy

def call(String projectDirectory, String mvnCommand) {
    return {
        dir(projectDirectory) {
            // Set permissions for the directory
            sh "chmod +x scripts/mvnw"  // Change permissions as needed
            
            // Execute Maven command based on the parameter
            sh "${mvnCommand}"
        }
    }
}
