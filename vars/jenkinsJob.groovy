def call(){
	pipeline{
	tools{
	maven 'MAVEN_HOME'
	}
  stages {
	      stage('Build Stage')
		{
			agent {
			label 'window'
			}
			steps{
				bat 'mvn package'
			}
		}
		
		stage('Compile Stage')
		{
			
			agent {
			label 'window'
			}
			steps{
				 bat 'mvn compile'
			}
		}
		stage('Testing Stage')
		{
			
			agent {
			label 'window'
			}
			steps{
				 bat 'mvn test'
			}
		}
	  	stage('build && SonarQube analysis') 
		{
			
			agent {
			label 'window'
			}
            		steps {
                		withSonarQubeEnv('sonar') {
                                     		bat 'mvn sonar:sonar'
                		}
            		}
        	}    
		stage('Deploy artifact using maven')
		{
			
			agent {
			label 'window'
			}
			steps{
			bat 'mvn deploy'
			stash name: 'war', includes: '**/*.war'
			}
		}
		stage('Deploy to tomcat')
		{
			agent {
			label 'window'
			}
			steps{
			bat "copy target\\HelloWorld.war \"C:\\Users\\Payal\\Software\\New folder\\apache-tomcat-8.5.53\\webapps\""
			}
		}		
		stage('Building image') {
 			 agent {
			label 'linux'
			}
			steps{
				unstash 'war'
				sh 'docker build -t pylagg/first_repo":${BUILD_NUMBER}" .'
			}
  		}
		stage('Deploy image') {
			agent {
			label 'linux'
			}
			steps{
				sh 'docker push pylagg/first_repo":${BUILD_NUMBER}"'
			}
		}
		stage('Run container'){
			agent {
			label 'linux'
			}
			steps{
				sh( script: '''#!/bin/bash
           				 if [ "$(docker ps -aq -f name=con1)" ]; then
					docker stop con1
					docker rm con1
					fi
        				'''.stripIndent())
				sh 'docker run -d --name con1 -p 80:8080 pylagg/first_repo":${BUILD_NUMBER}"'
			}
		}
}
}
}
