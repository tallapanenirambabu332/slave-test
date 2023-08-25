#!/bin/bash

# Replace these variables with your actual Jenkins master URL and credentials
JENKINS_URL="http://your-jenkins-master-url"
USERNAME="your-username"
PASSWORD="your-password-or-api-token"

# List of slave names and corresponding tokens
declare -A SLAVE_TOKENS
SLAVE_TOKENS["Slave1"]="slave1-token"
SLAVE_TOKENS["Slave2"]="slave2-token"
# Add more slave token mappings as needed

# Loop through the slave tokens and update them in Jenkins
for SLAVE in "${!SLAVE_TOKENS[@]}"; do
    TOKEN="${SLAVE_TOKENS[$SLAVE]}"
    java -jar jenkins-cli.jar -s "$JENKINS_URL" -auth "$USERNAME:$PASSWORD" set-node-property "$SLAVE" -token "$TOKEN"
    if [ $? -eq 0 ]; then
        echo "Token for $SLAVE updated successfully."
    else
        echo "Failed to update token for $SLAVE."
    fi
done
