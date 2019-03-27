pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean install'
      }
    }
    stage('Sonar') {
      steps {
        withSonarQubeEnv 'test'
        waitForQualityGate true
      }
    }
  }
}