node('master') {
    stage('Checkout') {
        sh "git branch: 'iteration2_develop_reporting', url: 'https://github.com/browserstack/browserstack-examples-cucumber-testng.git'"
    }


    stage('Build') {


		echo "Hello World"
		sh 'export BROWSERSTACK_USERNAME="mudassardemo"'
		sh 'export BROWSERSTACK_ACCESSKEY="Mz55zvYU9iCdyV9dvsKv"'
        sh '/opt/gradle/gradle-5.6/bin/gradle test'

    }
}
