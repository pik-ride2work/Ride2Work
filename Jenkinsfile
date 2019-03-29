pipeline {
  environment {
      registry = "ride2work/ride2work"
      registryCredential = 'dockerhub'
  }
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Build') {
          steps {
            sh 'mvn clean install'
          }
        }
        stages{
          stage('Building image'){
            steps{
              docker.build registry + "$BUILD_NUMBER"
            }
          }
        }
      }
    }
    stage('Deploy') {
      steps {
        sh 'mvn deploy'
      }
    }
    stage('Start') {
      steps {
        sh 'java -jar target/ride2work-0.0.1-SNAPSHOT.jar &'
      }
    }
  }
}