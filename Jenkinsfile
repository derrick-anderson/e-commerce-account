pipeline{
    agent any
    stages{
        stage('Test'){
            steps{
                git 'https://github.com/derrick-solstice/e-commerce-account'
            }
            steps{
                'gradle test'
            }
        }
    }
}