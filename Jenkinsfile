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
                bat 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                bat 'mvn package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building the Docker Image...'
                bat 'docker build -t foodorder-app:latest .'
            }
        }
        
        stage('Deploy Local Server') {
            steps {
                echo 'Restarting Docker Container...'
                // Windows compatible way to ignore errors if the container doesn't exist yet
                bat 'docker stop food-server || echo "Container was not running"'
                bat 'docker rm food-server || echo "Container was already removed"'
                
                // Start the fresh container
                bat 'docker run -d -p 8080:8080 --name food-server foodorder-app:latest'
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution complete.'
        }
        success {
            echo 'SUCCESS: Server is running with the latest code!'
        }
        failure {
            echo 'FAILURE: The pipeline failed. Check logs for details.'
        }
    }
}