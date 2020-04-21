def createDeploymentJob(jobName, repoUrl, repo_branch) {
    pipelineJob(jobName) {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(repoUrl)
                        }
                        branches(repo_branch)
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
    createDeploymentJob(jobName, repoUrl, repo_branch)
}

buildPipelineJobs()
