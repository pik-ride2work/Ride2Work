pipeline {
  agent any
  stages {
    stage('Build JAR') {
      steps {
        sh 'mvn clean install'
      }
    }
    stage('Build Docker Image') {
      steps {
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }

      }
    }
    stage('Static code analysis') {
      environment {
        scannerHome = '/opt/sonar_scanner'
      }
      steps {
        withSonarQubeEnv('sonarqube') {
          sh "${scannerHome}/bin/sonar-scanner"
        }

        timeout(time: 10, unit: 'MINUTES') {
          waitForQualityGate true
        }

      }
    }
    stage('Push to repositories') {
      parallel {
        stage('Push JAR to Nexus') {
          steps {
            sh 'mvn deploy'
          }
        }
        stage('Push Image To DockerHub') {
          steps {
            script {
              docker.withRegistry('', registryCredential) {
                dockerImage.push()
                dockerImage.push('latest')
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
    stage('Approve deployment') {
      steps {
        input 'Deploy on K8s?'
      }
    }
    stage('Deploy on K8s') {
      steps {
        withKubeConfig(credentialsId: 'k8scli', serverUrl: 'https://35.204.194.137') {
          sh 'kubectl set image deployment/ride2work ride2work=ride2work/ride2work:$BUILD_NUMBER'
        }
      }
    }
  }
  environment {
    registry = 'ride2work/ride2work'
    registryCredential = 'dockerhub'
    dockerImage = ''
  }
}
