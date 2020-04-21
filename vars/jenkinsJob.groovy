// Configure using microservice-pipelines and using "part2" branch
@Library("my-shared-library@part2") _

// Entry point into microservice-pipelines
jenkinsJob.call()

pipeline{
	agent any
	tools{
	maven 'MAVEN_HOME'
	}
  stages {
        	stage("Code Checkout") {
                     steps {
                	git branch: 'master',
                	url: 'https://github.com/pylagg/maven_demo.git'
                  }
              }
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
    }
