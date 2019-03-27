pipeline {
  agent any
  stages {
    stage('Init') {
      steps {
        sh '''mvn -version
mvn clean install'''
      }
    }
  }
}