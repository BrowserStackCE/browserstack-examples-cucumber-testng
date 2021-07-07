node('master') {

    stage('Pull repository from GitHub') {
        git credentialsId: 'Jenkins_Samiran_Git', url: 'https://github.com/browserstack/browserstack-examples-cucumber-testng.git'
    }
    stage('Checkout') {
        sh "git checkout 'iteration2_develop_reporting'"
    }


    stage('Build') {


		echo "Hello World"
		sh 'export BROWSERSTACK_USERNAME="mudassardemo"'
		sh 'export BROWSERSTACK_ACCESSKEY="Mz55zvYU9iCdyV9dvsKv"'
        sh '/opt/gradle/gradle-5.6/bin/gradle test'

    }
}
