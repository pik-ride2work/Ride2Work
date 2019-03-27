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
        withSonarQubeEnv 'SonarQube'
        waitForQualityGate true
      }
    }
  }
}