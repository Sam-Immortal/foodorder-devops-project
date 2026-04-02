pipeline {
    agent any

    tools {
        maven 'Maven3' // Make sure this name matches your Jenkins Global Tool Configuration
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from Git...'
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Compiling and running automated tests...'
                sh 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building the Docker Image...'
                // The name 'foodorder-app' is arbitrary for local testing
                sh 'docker build -t foodorder-app:latest .'
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution complete.'
        }
        success {
            echo 'SUCCESS: All tests passed and image built successfully.'
        }
        failure {
            echo 'FAILURE: The pipeline failed. Check logs for details.'
        }
    }
}