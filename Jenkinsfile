pipeline {
    agent any

    tools {
        allure 'Allure_Report'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/NanaQuaci/Phase3-Week4.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo ">>> Building Docker image selenium-cucumber-tests"
                sh 'docker build -t selenium-cucumber-tests .'
            }
        }

        stage('Run Tests in Docker') {
            steps {
                echo ">>> Running Cucumber tests inside Docker container"
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE'){
                sh '''
                    docker run --rm \
                    -v $WORKSPACE:/app \
                    -w /app \
                    selenium-cucumber-tests clean test
                '''
                }
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'allure-results']]
                ])
            }
        }

        stage('Generate Cucumber Report') {
                steps {
                    echo ">>> Generating Cucumber HTML report"
                    sh '''
                        docker run --rm \
                        -v $WORKSPACE:/app \
                        -w /app \
                        selenium-cucumber-tests \
                        verify
                    '''
                }
            }

        stage('Publish Cucumber Report') {
            steps {
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target',
                    reportFiles: 'cucumber.html',
                    reportName: 'Cucumber HTML Report'
                ])
            }
        }
    }


    post {
        always {
            echo 'Archiving Allure results and Cucumber reports...'
            archiveArtifacts artifacts: 'allure-results/**', fingerprint: true
            archiveArtifacts artifacts: 'target/cucumber.html', fingerprint: true

            script {
                try {
                    def statusEmoji = ""
                    def statusText = ""

                    if (currentBuild.result == 'SUCCESS') {
                        statusEmoji = ":white_check_mark:"
                        statusText = "success"
                    } else if (currentBuild.result == 'UNSTABLE') {
                        statusEmoji = ":warning:"
                        statusText = "unstable"
                    } else {
                        statusEmoji = ":x:"
                        statusText = "failed"
                    }

                    def commitHash = env.GIT_COMMIT ? env.GIT_COMMIT.take(8) : "unknown"

                    def message = "${statusEmoji} Selenium Cucumber Tests finished with status: ${statusText}\n" +
                                 ":package: Repo: ${env.JOB_NAME}\n" +
                                 ":link: Commit: ${commitHash}\n" +
                                 ":bar_chart: Allure Report: ${env.BUILD_URL}allure/"

                    slackSend(
                        channel: '#ci-alerts',
                        color: currentBuild.result == 'SUCCESS' ? 'good' : 'danger',
                        message: message
                    )
                } catch (err) {
                    echo "Slack notification failed: ${err}"
                }
            }
        }
    }
}
