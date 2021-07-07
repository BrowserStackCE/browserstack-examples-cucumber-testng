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
        def notifySlack(String buildStatus = 'STARTED') {
                // Build status of null means success.
                buildStatus = buildStatus ?: 'SUCCESS'

                def color

                if (buildStatus == 'STARTED') {
                    color = '#D4DADF'
                } else if (buildStatus == 'SUCCESS') {
                    color = '#BDFFC3'
                } else if (buildStatus == 'UNSTABLE') {
                    color = '#FFFE89'
                } else {
                    color = '#FF9FA1'
                }

                def msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL}"
                //if (buildStatus != 'STARTED' && buildStatus !='SUCCESS') {
                    slackSend channel: 'jenkins-vgm-alerts', message: msg, tokenCredentialId: 'slack-token'
        }
}
}
