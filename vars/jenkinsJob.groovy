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
			//stash name: 'war', includes: '**/*.war'
			
			}
		}
		       
  }  

	post {
		 
   		 failure {
      			  mail to: mail_id,
             		subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
             		body: "Something is wrong with ${env.BUILD_URL}"
    		}
		success{
      			  mail to: mail_id,
             		subject: "Success Pipeline: ${currentBuild.fullDisplayName}",
             		body: "Congratulations ${env.BUILD_URL}"
    		}
		}
	
}
}
