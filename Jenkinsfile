node('master') {
try{

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
            sh './gradlew test -Dnum.parallels=20 --stacktrace'
        }
      }
  }catch (e) {
              currentBuild.result = 'FAILURE'
              throw e
               } finally {
              notifySlack(currentBuild.result)
          }
      }

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
              slackSend(color: color, message: msg, tokenCredentialId: 'team_se_ci_alerts')
          //}
      }
}
