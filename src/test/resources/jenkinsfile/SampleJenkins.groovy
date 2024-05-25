pipeline {
    agent any
    parameters {
        //parameters here
    }
    stages {
        stage('Clear Workspace') {
            steps {
                cleanWs()
            }
        }
        stage('Build') {
            steps {
                echo "Start Building.."
            }
        }
        stage('git ') {
            steps {
                echo "git.."
                git(url: '<git url here>', branch: 'main')
            }
        }
        stage('copy env') {
            steps {
                sh '''
                #!/bin/bash
                cp /Users/bambooagent/.jenkins/env.properties src/main/resources/env.properties
                '''
            }
        }
        stage('Copy history') {
            steps {
                // Copy Allure results from the previous pipeline run's workspace
                script {
                    def previousBuild = currentBuild.rawBuild.getPreviousBuild()
                    println("previousBuild $previousBuild")
                    if (previousBuild) {
                        def previousAllureResults = previousBuild.artifacts.find { it.fileName.startsWith('allure-results/') }
                        println("previousAllureResults $previousAllureResults")
                        if (previousAllureResults) {
                            println("copying")
                            copyArtifacts(projectName: previousBuild.project.name, filter: previousAllureResults.fileName, target: '.')
                        } //previousAllureResults
                    } //previousBuild
                } //script
            } //steps
        } //stage
        stage('Test') {
            steps {
                script {
                    sh "./gradlew :test --tests '<testsuite.name.here>' -Denv=Android13_${choice} -Dlogin=true"
                    stash includes: 'build/allure-results/*', name: 'test1'
                }
            } //steps
        } // stage

    } // stages
    post {
        always {
            sh 'mkdir -p allure-reporting'
            dir('allure-reporting'){
                catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS'){
                    unstash 'test1'
                }
            }
            sh 'cp environment.properties allure-reporting/build/allure-results/'
            allure includeProperties: false, jdk: '', results: [[path: 'allure-reporting/build/allure-results']]
            sh 'zip -r allure-report.zip allure-report'
            archiveArtifacts artifacts: 'allure-report.zip'
        } //always
        unstable {
            script {
                slackSend color: 'warning', message: ":android: test UNSTABLE \n <${env.BUILD_URL}allure/|View Testreport> | <${env.BUILD_URL}console|Build results>", teamDomain: '<team domain here>', channel: '<slack channel here>'
            }
        }
        failure {
            script {
                slackSend color: 'danger', message: ":android: test FAILED \n <${env.BUILD_URL}allure/|View Testreport> | <${env.BUILD_URL}console|Build results>", teamDomain: '<team domain here>', channel: '<slack channel here>'
            }
        }
        success {
            script {
                slackSend color: 'good', message: ":android: test PASSED \n <${env.BUILD_URL}allure/|View Testreport> | <${env.BUILD_URL}console|Build results>", teamDomain: '<team domain here>', channel: '<slack channel here>'
            }
        }
    } //post
} // pipeline
