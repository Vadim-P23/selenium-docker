pipeline{

    agent any

    stages{

        stage('Build Jar'){
            steps{
                bat "mvn clean package -DskipTests"
            }
        }
        stage('Build image') {
            steps{
                bat "docker build -t=pvaddocker/selenium ."
            }
        }
        stage('Push Image') {
            steps{
                bat "docker push pvaddocker/selenium"
            }
        }
    }
}