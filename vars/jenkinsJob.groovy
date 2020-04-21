def call(){
	pipeline{
	agent any
	tools{
	maven 'MAVEN_HOME'
	}
  stages {
        	stage("Code Checkout") {
                     steps {
                	git branch: branch_repo,
                	url: repoUrl
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
  }}
}
