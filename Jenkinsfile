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
                bat "docker build -t=pvaddocker/selenium:latest ."
            }
        }
        stage('Push Image') {
            environment {
                DOCKER_HUB = credentials('dockerhub-credentials')
            }
            steps{
                bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
                bat "docker push pvaddocker/selenium:latest"
            }
        }
    }
    post {
        always {
            bat "docker logout"
        }
    }
}