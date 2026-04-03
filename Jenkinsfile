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
                
                // Tell Jenkins to explicitly ignore errors for these two commands
                bat returnStatus: true, script: 'docker stop food-server'
                bat returnStatus: true, script: 'docker rm food-server'
                
                // Start the fresh container (We DO want Jenkins to catch errors here!)
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