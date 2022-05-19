pipeline {
    agent any
    
    tools {
        maven "maven"
    }
    
    stages {
        stage('git Pull') {
            steps {

                git branch: 'main', changelog: false, poll: false, credentialsId:'jenkins_acccess_token', url: 'https://github.com/da77777/musicat-refactoring.git'

            }
        }
        
        stage('Build') {
            steps {
                sh "mvn -Dmaven.test.failure.ignore=true -N -f pom.xml clean package"
            }
        }
        
        stage('Deploy') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'jekins_access_token', path: '', url: 'http://3.38.88.242/')], contextPath: '/', onFailure: false, war: '**/*.war'
			}
        }
        
        stage('Restart') {
            steps {
                sh '''curl -u admin:javatomcat http://3.38.88.242/host-manager/text/stop
curl -u admin:javatomcat http://3.38.88.242/host-manager/text/start'''
            }
        }
    }
}
