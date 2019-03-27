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
        withSonarQubeEnv('SonarQube') {
                sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
        }

      }
    }
  }
}
