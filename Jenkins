pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                // Example build step, replace with your actual build commands
                echo 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Example test step, replace with your actual test commands
                echo 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                // Example deployment step, replace with your actual deployment commands
                echo 'kubectl apply -f deployment.yaml'
            }
        }
    }
}
