// File: vars/mavenTasks.groovy
def call(){
    sh "chmod +x scripts/mvnw" 
    sh 'scripts/mvnw clean package'
}


/*def call(String projectDirectory, String mvnCommand) {
    return {
        dir(projectDirectory) {
            // Set permissions for the directory
            sh "chmod +x scripts/mvnw"  // Change permissions as needed
            
            // Execute Maven command based on the parameter
            sh "${mvnCommand}"
        }
    }
}*/
