#!/bin/bash

# Set your Ansible Tower API token
API_TOKEN="your_api_token_here"

# Ansible Tower API URL
TOWER_URL="https://your-ansible-tower-url/api/v2"

# Get the hostname of the current server
HOST_NAME="$HOSTNAME"

# Make API request to fetch host details by name
HOST_JSON=$(curl -s -H "Authorization: Bearer $API_TOKEN" "$TOWER_URL/hosts/?name=$HOST_NAME")

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
