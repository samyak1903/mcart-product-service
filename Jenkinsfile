pipeline {
    agent any
    environment {
        AWS_REGION    = 'ap-south-1'
        ECR_REGISTRY  = '096568562814.dkr.ecr.ap-south-1.amazonaws.com'
        APP_NAME      = 'mcart-product-service' 
        EKS_CLUSTER   = 'mcart-cluster'
    }
    stages {
        stage('Build Java') {
            steps { sh 'mvn clean package -DskipTests' }
        }
        stage('Push to ECR') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-creds']]) {
                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}"
                    sh "docker build -t ${APP_NAME} ."
                    sh "docker tag ${APP_NAME}:latest ${ECR_REGISTRY}/${APP_NAME}:latest"
                    sh "docker push ${ECR_REGISTRY}/${APP_NAME}:latest"
                }
            }
        }
        stage('Deploy to EKS') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-creds']]) {
                    sh "aws eks update-kubeconfig --region ${AWS_REGION} --name ${EKS_CLUSTER}"
                    sh "kubectl rollout restart deployment product-deployment"
                }
            }
        }
    }
}