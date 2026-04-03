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
        
        // --- ADD THIS NEW STAGE ---
        stage('Deploy Local Server') {
            steps {
                echo 'Restarting Docker Container...'
                // Stop the old container if it is running (|| true prevents errors if it doesn't exist)
                bat 'docker stop food-server || true'
                bat 'docker rm food-server || true'
                
                // Start the fresh container in the background (-d) on port 8080
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