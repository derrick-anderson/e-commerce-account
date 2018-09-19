pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh './gradlew check'
                junit 'build/test-results/test/*.xml'
            }
        }
        stage('Build'){
            steps{
                sh './gradlew clean assemble'
            }
        }
        stage('Archive'){
            steps{
                archiveArtifacts 'build/libs/*.jar'
            }
        }
    }
}