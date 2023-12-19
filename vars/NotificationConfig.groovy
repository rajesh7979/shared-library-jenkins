def sendTeamsNotification(webhookUrl, message) {
    // Send a notification to Microsoft Teams using webhookUrl and message
    teams([webhookUrl: webhookUrl, message: message])
}
