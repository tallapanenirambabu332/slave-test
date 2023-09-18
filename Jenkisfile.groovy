pipeline {
    agent any
    
    stages {
        stage('Encrypt Ansible Vault String') {
            when {
                expression { params.encryptVaultString }
            }
            steps {
                script {
                    def vaultPassword = credentials('your_vault_password_credential_id')
                    sh "ansible-vault encrypt_string --vault-password-file=${vaultPassword} 'my_secret_string' --name 'my_secret'"
                }
            }
        }
        
        stage('Encrypt Ansible File with Parameters') {
            when {
                expression { params.encryptFile }
            }
            steps {
                script {
                    def vaultPassword = credentials('your_vault_password_credential_id')
                    sh "ansible-vault encrypt my_ansible_file --vault-password-file=${vaultPassword}"
                }
            }
        }
    }
    
    parameters {
        booleanParam(name: 'encryptVaultString', defaultValue: true, description: 'Encrypt Ansible Vault String')
        booleanParam(name: 'encryptFile', defaultValue: true, description: 'Encrypt Ansible File with Parameters')
    }
}
