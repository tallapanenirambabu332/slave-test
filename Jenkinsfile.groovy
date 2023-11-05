pipeline {
    agent any

    stages {
        stage('Check Snapshot Dependencies') {
            steps {
                script {
                    def xmlFilePath = 'path/to/your/xml/file.xml'
                    def xmlText = readFile(file: xmlFilePath)

                    def dependencies = new XmlParser().parseText(xmlText)

                    def foundSnapshot = false
                    dependencies.dependency.each { dependency ->
                        def version = dependency.version.text()
                        if (version.contains("SNAPSHOT")) {
                            echo "Found snapshot version in ${dependency.groupId.text()}:${dependency.artifactId.text()}:${version}"
                            foundSnapshot = true
                        }
                    }

                    if (foundSnapshot) {
                        error("Snapshot dependencies found. Failing the pipeline.")
                    }
                }
            }
        }

        stage('Build and Deploy') {
            steps {
                // Your build and deploy steps here
            }
        }
    }
}
