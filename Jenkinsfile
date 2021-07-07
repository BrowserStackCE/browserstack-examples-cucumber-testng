node('master') {

    stage('Pull repository from GitHub') {
        git branch: "iteration2_develop_reporting",url: 'https://github.com/browserstack/browserstack-examples-cucumber-testng.git'
    }
    stage('Checkout') {
        sh "git checkout 'iteration2_develop_reporting'"
    }


    stage('Build') {


		echo "Hello World"
		sh 'export BROWSERSTACK_USERNAME="mudassardemo"'
		sh 'export BROWSERSTACK_ACCESSKEY="Mz55zvYU9iCdyV9dvsKv"'
		sh 'chmod +x gradlew'
		browserstack('mudassardemo'){
		}
		withGradle {
            sh './gradlew test --stacktrace'
        }
    }
     stage('Notify on Slack') {
        slackSend channel: 'jenkins-vgm-alerts', message: '${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}', teamDomain: '/var/lib/jenkins/workspace/cucumberreporting', tokenCredentialId: 'slack-token'
    }
}
