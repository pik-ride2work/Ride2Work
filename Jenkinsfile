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
          sh 'kubectl set image deployment/ride2work ride2work=ride2work/ride2work'
          sh 'kubectl set image deployment/ride2work ride2work=ride2work/ride2work:latest'
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