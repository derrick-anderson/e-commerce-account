pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh './gradlew check'
                junit 'build/test-results/test/*.xml'
                junit 'build/reports/tests/test/**'
            }
        }
        stage('Build'){
            steps{
                sh './gradlew clean assemble'
            }
        }
    }
}