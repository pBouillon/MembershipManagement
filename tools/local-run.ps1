# Setup location
$rootDirectory = (get-item $PSScriptRoot).parent.FullName
Set-Location -Path $rootDirectory

$javaExecutablePath = $env:JAVA_HOME + '\bin\java.exe'

# Generate and run the API project
cd '.\membership-management'
start powershell `
 "echo 'Clean and package the API project' ; `
 .\gradlew clean ; `
 .\gradlew build -x test ; `
  echo '`nStart the API' ; `
  & '$javaExecutablePath' -jar .\build\libs\api-0.0.1-SNAPSHOT.jar"
cd ..
# Start RabbitMQ container
# TODO Serveur rabbitMQ du prof

#docker-compose up -d rabbitmq-container

# Generate and run the logging client project
cd '.\rabbitmq-clients\logger'
start powershell `
 "echo 'Clean and package the logging client project' ; `
 .\gradlew clean ; `
 .\gradlew build -x test ; `
  echo '`nStart the logging client' ; `
  & '$javaExecutablePath' -jar .\build\libs\logger-0.0.1-SNAPSHOT.jar"
cd ..\..

# Generate and run the monitoring client project
cd '.\rabbitmq-clients\monitoring'
start powershell `
 "echo 'Clean and package the monitoring client project' ; `
 .\gradlew clean ; `
 .\gradlew build -x test ; `
  echo '`nStart the monitoring client' ; `
  & '$javaExecutablePath' -jar .\build\libs\monitoring-0.0.1-SNAPSHOT.jar"
cd ..\..
