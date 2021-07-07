JAVA_TOOL = 'Java_7'
JAVA_OPTS = ''
GRADLE_TOOL = 'Gradle_2.13'


supersedeBuilds

node('master') {
    stage('Checkout') {
        checkout scm
    }


    stage('Build') {


		echo "Hello World"
		sh 'export BROWSERSTACK_USERNAME="mudassardemo"'
		sh 'export BROWSERSTACK_ACCESSKEY="Mz55zvYU9iCdyV9dvsKv"'
        sh '/opt/gradle/gradle-5.6/bin/gradle test'

    }
}
