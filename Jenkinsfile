pipeline {
    agent any
    environment {
        registry = "ride2work/ride2work"
        registryCredential = 'dockerhub'
        dockerImage = ''
    }
    stages {
        stage('Build JAR') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build registry + ":$BUILD_NUMBER " + registry + ":latest"
                }
            }
        }
        stage('Static code analysis') {
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
        stage('Push to repositories') {
            parallel {
                stage('Push JAR to Nexus repository') {
                    steps {
                        sh 'mvn deploy'
                    }
                }
                stage('Push Image To DockerHub') {
                    steps {
                        script {
                            docker.withRegistry('', registryCredential) {
                                dockerImage.push()
                            }
                        }
                    }
                }
            }
        }
        stage('Remove Unused Docker Image') {
            steps {
                sh "docker rmi $registry:$BUILD_NUMBER"
            }
        }
        stage('Confirm deployment'){
          steps{
            input 'Deploy on K8s?'
          }
        }
        stage('Deploy on K8s') {
            steps {
                withKubeConfig([credentialsId: 'k8scli', serverUrl: 'https://35.204.194.137']) {
                    sh 'pwd'
                }
            }
        }
    }
}

