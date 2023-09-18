pipeline {
    agent none
    
    parameters {
        string(name: 'vaultString', defaultValue: 'my_secret_string', description: 'Ansible Vault String to Encrypt')
        string(name: 'ansibleFileName', defaultValue: 'my_ansible_file', description: 'Ansible File to Encrypt')
    }
    
    stages {
        stage('Encrypt Ansible Vault String') {
            when {
                expression { params.vaultString != '' }
            }
            steps {
                script {
                    def vaultPassword = withCredentials([string(credentialsId: 'your_vault_password_credential_id', variable: 'VAULT_PASSWORD')]) {
                        sh "echo '\${VAULT_PASSWORD}' | ansible-vault encrypt_string --name '${params.vaultString}'"
                    }
                }
            }
        }
        
        stage('Encrypt Ansible File with Parameters') {
            when {
                expression { params.ansibleFileName != '' }
            }
            steps {
                script {
                    def vaultPassword = withCredentials([string(credentialsId: 'your_vault_password_credential_id', variable: 'VAULT_PASSWORD')]) {
                        sh "echo '\${VAULT_PASSWORD}' | ansible-vault encrypt ${params.ansibleFileName}"
                    }
                }
            }
        }
    }
}
