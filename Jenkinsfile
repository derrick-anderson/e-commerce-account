pipeline{
    agent any
    stages{
        stage('Checkout'){
            steps{
                git 'https://github.com/derrick-solstice/e-commerce-account'
            }
        }
        stage('Test'){
            steps{
                sh 'gradle test'
            }
        }
    }
}