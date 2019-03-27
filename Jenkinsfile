pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean install'
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
    stage('Sonar') {
      steps {
        waitForQualityGate true
      }
    }
  }
}