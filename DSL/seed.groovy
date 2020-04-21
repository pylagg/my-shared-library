def createDeploymentJob(jobName, repoUrl, branch) {
    pipelineJob(jobName) {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(repoUrl)
                        }
                        branches(branch)
                        extensions {
                            cleanBeforeCheckout()
                        }
                    }
                }
                scriptPath("Jenkinsfile")
            }
        }
    }
}
def buildPipelineJobs() {
    createDeploymentJob(jobName, repoUrl, branch)
}

buildPipelineJobs()
