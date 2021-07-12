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
		sh 'chmod +x gradlew'
		browserstack('mudassardemo'){
		}

		withGradle {
            sh './gradlew test -Dnum.parallels=25'
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

          def msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n build url: ${env.BUILD_URL} \n Test Report:Download the zip and open 'cucumber-html-reports/overview-features.html'"

          def attachments = [
            [
              text: "Link: ${env.BUILD_URL}execution/node/3/ws/reports/tests/cucumber/output/*zip*/output.zip",
              fallback: 'this is a feedback message.',
              color: '#ff0000'
            ]
          ]

          //if (buildStatus != 'STARTED' && buildStatus !='SUCCESS') {
              //slackSend(color: color, message: msg,attachments: attachments)
              //def workspace = pwd()
              //sh "cp $workspace/reports/tests/cucumber/timeline/index.html ."
              //slackUploadFile filePath: "index.html", initialComment:  msg
              //slackUploadFile channel: 'vgm', filePath: '/var/lib/jenkins/workspace/cucumberreporting/reports/tests/cucumber/json/cucumber.json', initialComment: 'Here is the report'
          //}
 }
