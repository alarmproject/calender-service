pipeline {
    // 스테이지 별로 다른 거
    agent any

    tools {
        maven 'mvn'
    }

//     triggers {
//         pollSCM('*/3 * * * *')
//     }

    stages {
        // 레포지토리를 다운로드 받음
        stage('git pull') {
            agent any

            steps {
                git url: 'https://github.com/alarmproject/calender-service.git',
                    branch: 'develop',
                    credentialsId: 'tokenForJenkins'
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    echo 'Successfully Cloned Repository'
                }

                always {
                  echo "i tried..."
                }

                cleanup {
                  echo "after all other post condition"
                }

                failure {
                  error 'maven build fail...'
                }
            }
        }

        stage ('Build Maven') {
          agent any
          steps {
            echo 'Build Maven'
            sh 'mvn clean package -P local'
          }

          post {
            success {
                echo 'mvn clean package success..'
            }
            failure {
              error 'maven build fail...'
            }
          }
        }

        stage('Build Docker') {
          agent any
          steps {
            echo 'Build Dokcer'
            sh '''
            docker tag rlabotjd/mysend:latest-alarm-calender rlabotjd/mysend:backup-alarm-calender
            docker build -t rlabotjd/mysend:latest-alarm-calender .
            '''
          }

          post {
            success {
                echo 'alarm-calender latest docker build success'
            }
            failure {
              error 'alarm-calender latest docker build failed..'
            }
          }

        }

        stage('Push Docker hub') {
            agent any

            steps {
                sh '''
                docker login -u rlabotjd -p 251fcc1f-8ec9-4b0a-b440-c97a95a68e9e
                docker push rlabotjd/mysend:latest-alarm-calender
                docker push rlabotjd/mysend:backup-alarm-calender
                '''
            }

            post {
                success {
                    echo 'alarm-image backup and latest version push in docker-hub success'
                }
                failure {
                    error 'alarm-image backup and latest version push in docker-hub failed...'
                }
            }
        }

//         stage('Connect Home-Server and build') {
//           agent any
//           steps {
//             sshPublisher(
//                 continueOnError: false, failOnError: true,
//                 publishers: [
//                     sshPublisherDesc(
//                         configName: "alarm-service",
//                         verbose: true,
//                         transfers: [
//                             sshTransfer(
//                                 sourceFiles: "docker-compose-calender.yml",
//                             ),
//                             sshTransfer(execCommand: "docker login -u rlabotjd -p 251fcc1f-8ec9-4b0a-b440-c97a95a68e9e"),
//                             sshTransfer(execCommand: "docker pull rlabotjd/mysend:latest-alarm-calender"),
//                             sshTransfer(execCommand: "docker service rm alarm_alarm-calender"),
//                             sshTransfer(execCommand: "docker stack deploy --compose-file /home/ec2-user/alarm-service/docker-compose-calender.yml alarm"),
//                             sshTransfer(execCommand: "docker image prune -f")
//                         ]
//                     )
//                 ]
//             )
//           }
//
//           post {
//             success {
//                 echo 'connect Home Server deploy success..'
//             }
//             failure {
//               error 'connect Home Server deploy fail...'
//             }
//           }
//         }
    }
}