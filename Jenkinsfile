pipeline {
    agent any
    environment {
        registry = "ride2work/ride2work"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }
    stages {
        stage('Build') {
            parallel {
                stage('Build') {
                    steps {
                        sh 'mvn clean install'
                    }
                }
            }
        }
        stage('Building image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER"
                }
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn deploy'
            }
        }
        stage('Sonarqube') {
            environment {
                scannerHome = tool 'sonar_scanner'
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy Image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }
        stage('Remove Unused docker image') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
    }
}
