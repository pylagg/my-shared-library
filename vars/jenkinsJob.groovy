def call(){
	pipeline{
	agent any
	tools{
	maven 'MAVEN_HOME'
	}
  stages {
	      stage('Build Stage')
		{
			steps{
				bat 'mvn package'
			}
		}
		
		stage('Compile Stage')
		{
			steps{
				 bat 'mvn compile'
			}
		}
		stage('Testing Stage')
		{
			steps{
				 bat 'mvn test'
			}
		}
	  	stage('build && SonarQube analysis') 
		{
            		steps {
                		withSonarQubeEnv('sonar') {
                                     		bat 'mvn sonar:sonar'
                		}
            		}
        	}    
		stage('Deploy artifact using maven')
		{
			steps{
			bat 'mvn deploy'
			stash name: 'war', includes: '**/*.war'
			}
		}
		stage('Building image') {
 			 agent {
			 label 'slave_docker'
			 }
			 steps{
				unstash 'war'
				sh 'docker build -t pylagg/first_repo":${BUILD_NUMBER}" .'
			}
  		}
		stage('Deploy image') {
			agent {
			label 'slave_docker'
			}
			steps{
				sh 'docker push pylagg/first_repo":${BUILD_NUMBER}"'
			}
		}
		stage('Run container'){
			agent {
			label 'slave_docker'
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
