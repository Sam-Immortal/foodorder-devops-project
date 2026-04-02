pipeline {
    agent any

    tools {
        maven 'Maven3' 
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
                // Changed sh to bat for Windows
                bat 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                // Changed sh to bat for Windows
                bat 'mvn package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building the Docker Image...'
                // Changed sh to bat for Windows
                bat 'docker build -t foodorder-app:latest .'
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