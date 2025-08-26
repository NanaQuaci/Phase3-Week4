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
                sh '''
                echo ">>> Running Cucumber tests inside Docker container"

                mvn clean test
                '''
            }
        }

        stage('Generate Allure Report') {
            steps {
                echo ">>> Generating Allure Report"
                sh 'mvn allure:report'
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo 'Archiving Allure results and reports...'
            archiveArtifacts artifacts: 'target/allure-results/**, target/allure-report/**', fingerprint: true

            script {
                try {
                    // Status mapping
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

                    // Commit hash
                    def commitHash = env.GIT_COMMIT ? env.GIT_COMMIT.take(8) : "unknown"

                    // Slack message
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
