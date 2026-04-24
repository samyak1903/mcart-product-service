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
                steps { bat 'mvn clean package -DskipTests' }
            }
            stage('Push to ECR') {
                steps {
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-creds']]) {
                        bat "aws ecr get-login-password --region %AWS_REGION% | docker login --username AWS --password-stdin %ECR_REGISTRY%"
                        bat "docker build -t %APP_NAME% ."
                        bat "docker tag %APP_NAME%:latest %ECR_REGISTRY%/%APP_NAME%:latest"
                        bat "docker push %ECR_REGISTRY%/%APP_NAME%:latest"
                    }
                }
            }
            stage('Deploy to EKS') {
                steps {
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-creds']]) {
                        bat "aws eks update-kubeconfig --region %AWS_REGION% --name %EKS_CLUSTER%"
                        bat "kubectl rollout restart deployment product-deployment"
                    }
                }
            }
    }
}