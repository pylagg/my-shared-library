def createDeploymentJob(jobName, repoUrl, branch_repo) {
    pipelineJob(jobName) {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(repoUrl)
                        }
                        branches(branch_repo)
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
    createDeploymentJob(jobName, repoUrl, branch_repo)
}

buildPipelineJobs()
