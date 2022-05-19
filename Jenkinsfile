pipeline {
    agent any
    
    tools {
        maven "maven"
    }
    
    stages {
        stage('git Pull') {
            steps {

                git branch: 'main', changelog: false, poll: false, credentialsId:'musicat-access-token-2', url: 'https://github.com/jhk1231/musicat.git'

            }
        }
        
        stage('Build') {
            steps {
                sh "mvn -Dmaven.test.failure.ignore=true -N -f pom.xml clean package"
            }
        }
        
        stage('Deploy') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat-username-password', path: '', url: 'http://3.38.21.160/')], contextPath: '/', onFailure: false, war: '**/*.war'
			}
        }
        
        stage('Restart') {
            steps {
                sh '''curl -u tomcat:1111 http://3.38.21.160/host-manager/text/stop
curl -u tomcat:1111 http://3.38.21.160/host-manager/text/start'''
            }
        }
    }
}
