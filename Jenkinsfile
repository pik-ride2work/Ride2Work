pipeline {
  agent any
  stages {
    stage('Build JAR') {
      steps {
        sh '''mvn clean install
echo \'hello\''''
      }
    }
     stage('Static code analysis') {
      environment {
        scannerHome = tool 'sonar_scanner'
      }
      steps {
        withSonarQubeEnv('Sonar_server') {
          sh "${scannerHome}/bin/sonar-scanner"
        }
      }
    }
    stage('Build Docker Image') {
      steps {
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }

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
    stage('Remove Unused Docker Image') {
      steps {
        sh "docker rmi $registry:$BUILD_NUMBER"
      }
    }
    stage('Approve deployment') {
      when {
        branch 'master'
      }
      steps {
        input 'Deploy on K8s?'
      }
    }
    stage('Deploy on K8s') {
      when {
        branch 'master'
      }
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
