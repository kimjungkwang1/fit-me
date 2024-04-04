![infra.png](./resources/infra.png)

# 1. Stack

## Frontend

1. node.js LTS 20.11.1
2. npm 10.2.4
3. React 18.2.0

## CI / CD

1. Docker
2. Jenkins

## Backend

1. JVM openjdk:17-oracle
2. Spring Boot 3.2.3
3. NGINX 1.25.4

# 2. 외부 서비스

1. [카카오 로그인](https://developers.kakao.com/)
2. [AWS Parameterstore](https://docs.aws.amazon.com/ko_kr/systems-manager/latest/userguide/systems-manager-parameter-store.html)

# 3. DB

1. ERD
    
    ![ERD.png](./resources/erd.png)
    
2. Dump
    
    [Dump.sql](./3. dump.sql)
    

# 4. 로컬 환경 세팅

## Frontend

1. [node.js 설치](https://nodejs.org/dist/v20.11.0/node-v20.11.0-x64.msi)
2. vscode 설치
3. `front/fitme` cmd에서 실행
    
    ```bash
    > npm i
    ```
    
4. Frontend 서버 실행
    
    `front/fitme`로 이동
    
    ```bash
    > npm run start
    ```
    

## Backend

1. [Oracle Java SE 17](https://download.oracle.com/java/17/archive/jdk-17.0.9_windows-x64_bin.exe) 설치
2. [MySQL](https://downloads.mysql.com/archives/get/p/25/file/mysql-installer-community-8.0.34.0.msi) 설치
3. [IntelliJ](https://www.jetbrains.com/ko-kr/idea/download/?section=windows) 설치(community 버전도 가능)
4. AWS Parameter Store
    - `C:\Users\${USER}\.aws`에 이 파일들을 넣어주세요.
    
###서버 실행 시
1. Enable Annotation Processing
1. Add VM Options
```java
-Dspring.profiles.active=local

# 5. 배포

## EC2

### 접속

```bash
ssh -i J10A306T.pem ubuntu@j10a306.p.ssafy.io
```

## [Jenkins](https://velog.io/@penrose_15/AWSEC2-Linux2-Docker-Jenkins%EB%A1%9C-CICD-%EA%B5%AC%EC%84%B1%ED%95%98%EA%B8%B0)

```bash
sudo docker run -u 0 --privileged --name jenkins -d -p 4000:8080 -p 50000:50000 -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -v /home/jenkins:/var/jenkins_home jenkins/jenkins:jdk17
```

### Gitlab Webhook

new item을 눌러 fitmeGitlab이라는 이름으로 pipeline 생성

configuration

![Untitled](./resources/webhook1.png)

advanced를 눌러 generate secret token

![Untitled](./resources/webhook2.png)

gitlab에서

![Untitled](./resources/webhook3.png)

add new webhook

![Untitled](./resources/webhook4.png)

url과 secret token을 채우고 trigger 옵션에 push events를 활성화 / develop branch 설정

![Untitled](./resources/webhook5.png)

pipeline에 아래 코드 복붙하고 save

![Untitled](./resources/webhook6.png)

### fitmeGitlab

```groovy
pipeline {
    agent any

    stages {
        stage('gitlab clone') {
            steps {
                git branch: 'develop', credentialsId: 'fitme', url: 'https://lab.ssafy.com/s10-bigdata-recom-sub2/S10P22A306.git'
            }
        }
    }
    post {
        success {
        	script {
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                mattermostSend (color: 'good',
                message: "@@@@@@@@@@@@@@@@@@@@@ " + Author_ID + "님이 빌드 시작 @@@@@@@@@@@@@@@@@@@@@",
                endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa',
                channel: 'A306-A')
            }
        }
    }
}

```

<aside>
💡 나머지 Pipeline도 똑같은 방법으로 등록

</aside>

## Backend

### fitmeBackendBuild

```groovy
 pipeline {
        agent any

        stages {
             stage('Copy Folder') {
                steps {
                sh 'cp -r /var/jenkins_home/workspace/fitmeGitlab/back /var/jenkins_home/workspace/fitmeBackendBuild'
            }
        }
            stage('build'){
                steps{
                    dir('back'){
                        sh'''
                            echo build start
                            chmod +x ./gradlew
                            ./gradlew clean
                            ./gradlew bootJar
                        '''
                    }
                }
            }
        }
         post {
         
       
        failure {
        	script {
        	    dir('../fitmeGitlab') {
   
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'danger', 
                message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa', 
                channel: 'A306-A'
                )
        	}
            }
        }
    }
    }
```

### **BackendDockerImagePush**

```groovy
pipeline { 
    environment { 
        repository = "yuyeoul/fitme-back"  //docker hub id와 repository 이름
        DOCKERHUB_CREDENTIALS = credentials('fitmeDocker') // jenkins에 등록해 놓은 docker hub credentials 이름
        dockerImage = '' 
        
        build_message = 'latest'
    }
  agent any 
  stages {
        stage('Copy .aws') {
            steps {
                script {
                    sh "cp -r /var/jenkins_home/.aws /var/jenkins_home/workspace/BackendDockerImagePush"
                }
            }
        }
        stage('Copy Dockerfile') {
            steps {
                script {
                    sh "`cp /var/jenkins_home/BackendDockerfile /var/jenkins_home/workspace/BackendDockerImagePush/Dockerfile`"
                }
            }
        }
        stage('Copy jar File') {
            steps {
                script {
                    sh "cp /var/jenkins_home/workspace/fitmeBackendBuild/back/build/libs/fitme-0.0.1-SNAPSHOT.jar /var/jenkins_home/workspace/BackendDockerImagePush"
                }
            }
        }
        stage('Build Image') { 
            steps { 
                script { 
                    // def now = new Date()
                    // build_message = now.format("yyMMdd.HHmm", TimeZone.getTimeZone('Asia/Seoul'))
                    
                    dockerImage = docker.build repository + ":${build_message}" 
                }
            } 
        }
        stage('Login'){
            steps{
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin' // docker hub 로그인
            }
        }
        stage('Push Image') { 
            steps { 
                script {
                    sh "docker push ${repository}:${build_message}"
                }
            } 
        }
    }
     post {
         
        
        failure {
        	script {
        	    dir('../fitmeGitlab') {
   
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'danger', 
                message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa', 
                channel: 'A306-A'
                )
        	}
            }
        }
    }
}
```

### StopExistingBackendAndStartNewOne

```groovy
pipeline {
    agent any
    environment {
        DOCKER_IMAGE = 'yuyeoul/fitme-back:latest'
        NGINX_CONTAINER_NAME = 'nginxs'
    }
    stages {
        stage('Find Unused Port') {
            steps {
                script {
                    // A와 B 중 사용되지 않는 경우를 찾습니다.
                    if (sh(script: "docker ps -a --format '{{.Names}}' | grep -q 'BackendDev_A'", returnStatus: true) != 0) {
                        // A가 사용되지 않는 경우
                        // 로그 출력
                        echo "Let's go with A"

                        env.isA = true
                        env.UNUSED_PORT = '8080'
                        env.NEW_NAME = 'BackendDev_A'
                        env.OLD_NAME = 'BackendDev_B'
                    }
                    else {
                        // B가 사용되지 않는 경우
                        // 로그 출력
                        echo "Let's go with B"

                        env.isA = false
                        env.UNUSED_PORT = '8081'
                        env.NEW_NAME = 'BackendDev_B'
                        env.OLD_NAME = 'BackendDev_A'
                    }
                }
            }
        }
        stage('Run New Image') {
            steps {
                script {
                    sh "docker run -d -v /home/fitme/images:/fitme/backend/images -p 0.0.0.0:${env.UNUSED_PORT}:8080 --name ${env.NEW_NAME} yuyeoul/fitme-back:latest > container_id.txt"

                    // Get the ID of the container
                    containerId = readFile('container_id.txt').trim()

                    // 어플리케이션이 시작될 때까지 기다립니다.
                    // 최대 30초 동안 기다립니다.
                    def healthy = false
                    for (int i = 0; i < 60; i++) {
                        def logs = sh(script: "docker logs ${containerId}", returnStdout: true)
                        def result = (logs != null && logs.contains('Started FitmeApplication in '))
                        if (result) {
                            healthy = true
                            break
                        }

                        sleep 0.5
                    }

                    if (!healthy) {
                        // 실패하면 해당 컨테이너 삭제
                        sh "docker stop ${env.NEW_NAME}"
                        
                        error("Failed to start the application.")
                    }
                }
            }
        }
		stage('Backup BackendDev Log') {
            steps {
                script {
                    // 현재 날짜 및 시간을 가져옵니다.
                    String date = sh(returnStdout: true, script: "date '+%Y-%m-%d-%H:%M:%S'").trim()

                    // 해당 날짜 및 시간으로 이름이 지정된 폴더를 생성합니다.
                    sh "mkdir -p /home/ubuntu/back/${date}"

                    try {
                        // 컨테이너에서 로그 파일을 가져와서 생성한 폴더에 복사합니다.
                        sh "docker cp ${env.OLD_NAME}:/fitme/backend/logs/spring.log /home/ubuntu/back/${date}"
                    }
                    catch (err) {
                        echo err.getMessage()
                    }
                }
            }
        }
        // 새로 시작된 컨테이너의 ip 주소를 알아낸다.
        stage('Get New Container IP') {
            steps {
                script {
                    // 새로 시작된 컨테이너의 IP 주소를 알아냅니다.
                    def containerIP = sh(script: "docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' ${env.NEW_NAME}", returnStdout: true).trim()
                    echo "Container IP: ${containerIP}"
                    env.NEW_CONTAINER_IP = containerIP
                }
            }
        }
        // Nginx 컨테이너의 설정 파일을 복제한다.
        stage('Edit Nginx Conf File') {
            steps {
                script {
                    // Jenkins 컨테이너 내 설정 파일을 Nginx 컨테이너 내 설정로 복사
                    if (env.isA) {
                        sh "docker cp ../../nginx-conf/a ${env.NGINX_CONTAINER_NAME}://etc/nginx/sites-available/fit-me.site"
                    }
                    else {
                        sh "docker cp ../../nginx-conf/b ${env.NGINX_CONTAINER_NAME}://etc/nginx/sites-available/fit-me.site"
                    }
                }

                script {
                    // ${env.NGINX_CONTAINER_NAME}://etc/nginx/sites-available/fit-me.site 파일에서 0.0.0.0을 env.NEW_CONTAINER_IP로 바꿔준다.
                    sh "docker exec ${env.NGINX_CONTAINER_NAME} sed -i 's/0.0.0.0/${env.NEW_CONTAINER_IP}/g' /etc/nginx/sites-available/fit-me.site"

                    echo "docker exec ${env.NGINX_CONTAINER_NAME} cat /etc/nginx/sites-available/fit-me.site"
                }

                script {
                    // Nginx 설정 reload
                    sh "docker exec ${env.NGINX_CONTAINER_NAME} nginx -s reload"
                }
            }
        }
        stage('Stop Existing Container') {
            steps {
                script {
                    try {
                        sh "docker stop ${env.OLD_NAME}"
                    }
                    catch (err) {
                        echo err.getMessage()
                    }
                }
            }
        }
        stage('Remove Existing Container') {
            steps {
                script {
                    try {
                        sh "docker rm ${env.OLD_NAME}"
                    }
                    catch (err) {
                        echo err.getMessage()
                    }
                }
            }
        }
    }
    post {
        success {
        	script {
                dir('../fitmeGitlab') {

                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                    mattermostSend (color: 'good',
                    message: "빌드 성공: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)",
                    endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa',
                    channel: 'A306-A')
            	    }
                }
            }
        failure {
        	script {
        	    dir('../fitmeGitlab') {
                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                    mattermostSend (color: 'danger',
                    message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)",
                    endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa',
                    channel: 'A306-A')
        	    }
            }
        }
    }
}
```

## Frontend

### FrontendDockerImagePush

```groovy
pipeline { 
    environment { 
        repository = "yuyeoul/fitme-front"  //docker hub id와 repository 이름
        DOCKERHUB_CREDENTIALS = credentials('fitmeDocker') // jenkins에 등록해 놓은 docker hub credentials 이름
        dockerImage = '' 
        
        build_message = 'latest'
    }
  agent any 
  stages {
        stage('Remove Dir') {
            steps {
                script {
                    try {
                        sh "rm -r /var/jenkins_home/workspace/FrontendDockerImagePush"
                    }
                    catch (err) {
                        echo err.getMessage()
                    }
                }
            }
        }
        
        stage('Copy Dockerfile') {
            steps {
                script {
                    sh '''
                        cp /var/jenkins_home/FrontendDockerfile /var/jenkins_home/workspace/FrontendDockerImagePush/Dockerfile
                    '''
                }
            }
        }
        
         stage('Copy frontend file') {
            steps {
                script {
                    sh '''
                        cp -r /var/jenkins_home/workspace/fitmeGitlab/front/* /var/jenkins_home/workspace/FrontendDockerImagePush/
                        '''
                }
            }
        }
        stage('Copy Nginx.conf') {
            steps {
                script {
                    sh '''
                        cp /var/jenkins_home/default.conf /var/jenkins_home/workspace/FrontendDockerImagePush/
                    '''
                }
            }
        }
        
        stage('build') { 
            steps { 
                script { 
                    // def now = new Date()
                    // build_message = now.format("yyMMdd.HHmm", TimeZone.getTimeZone('Asia/Seoul'))
                    
                    dockerImage = docker.build repository + ":${build_message}" 
                }
            } 
        }
        stage('Login') {
            steps{
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin' // docker hub 로그인
            }
        }
        stage('Push Image') { 
            steps { 
                script {
                    sh "docker push ${repository}:${build_message}"
                }
            } 
        }
    }
     post {
        failure {
        	script {
        	    dir('../fitmeGitlab') {
                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                    mattermostSend (color: 'danger', 
                    message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                    endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa', 
                    channel: 'A306-A')
        	    }
            }
        }
    }
}
```

### StopExistingFrontendAndStartNewOne

```groovy
pipeline { 
    agent any 
    stages {
        stage('Stop Existing FrontendDev') {
            steps {
                script {
                    try {
                        sh "docker stop FrontendDev"
                    }
                    catch (err) {
                        echo err.getMessage()
                    }
                }
            }
        }
        stage('Remove Existing FrontendDev') {
            steps {
                script {
                    try {
                        sh "docker rm FrontendDev"
                    }
                    catch (err) {
                        echo err.getMessage()
                    }
                }
            }
        }
        stage('Remove Existing Image') {
            steps {
                script {
                    
                    try {
                        sh "docker rmi \$(docker images 'yuyeoul/fitme-front' -a -q)"
                    }
                    catch (err) {
                        echo err.getMessage()
                    }
                }
            }
        }
        stage('Remove All npm') {
            steps {
                script {
                    try {
                        sh "docker ps -a | grep \"/bin/sh -c 'npm run\" | awk '{print \$1}' | xargs docker rm"
                    }
                    catch (err) {
                        echo err.getMessage()
                    }
                }
            }
        }
        stage('Remove Untagged Docker Images') {
            steps {
                script {
                    try {
                        sh "docker images -f \"dangling=true\" -q | xargs docker rmi"
                    }
                    catch (err) {
                        echo err.getMessage()
                    }
                }
            }
        }
        stage('Run New Image') {
            steps {
                script {
                    sh "docker run -d -p :3000:3000 --name FrontendDev yuyeoul/fitme-front:latest > container_id.txt"
                    
                    // Get the ID of the container
                    containerId = readFile('container_id.txt').trim()
                    
                    // Wait for the application to start
                    // waitUntil {
                    //     def logs = sh(script: "docker logs ${containerId}", returnStdout: true)
                    //     return (logs != null && logs.contains('Ready in '))
                    // }
                    
                    currentBuild.result = 'SUCCESS'
                    sh 'exit 0'
                }
            }
        }
    }
    post {
        success {
        	script {
        	    dir('../fitmeGitlab') {
                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                    mattermostSend (color: 'good', 
                    message: "빌드 성공: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                    endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa', 
                    channel: 'A306-A')
    	        }
            }
        }
        failure {
        	script {
        	    dir('../fitmeGitlab') {
   
                    def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                    def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                    mattermostSend (color: 'danger', 
                    message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                    endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa', 
                    channel: 'A306-A')
        	    }
            }
        }
    }
}
```

## Batch

### **batchBuild**

```groovy
 pipeline {
        agent any

        stages {
             stage('Copy Folder') {
                steps {
                sh 'cp -r /var/jenkins_home/workspace/fitmeGitlab/batch /var/jenkins_home/workspace/batchBuild'
            }
        }
            stage('build'){
                steps{
                    dir('batch'){
                        sh'''
                            echo build start
                            chmod +x ./gradlew
                            ./gradlew clean
                            ./gradlew bootJar
                        '''
                    }
                }
            }
        }
         post {
         
       
        failure {
        	script {
        	    dir('../fitmeGitlab') {
   
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'danger', 
                message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa', 
                channel: 'A306-A'
                )
        	}
            }
        }
    }
    }
```

### **batchDokerfileAndRun**

```groovy
pipeline { 
    environment { 
        repository = "yuyeoul/fitme-batch"  //docker hub id와 repository 이름
        DOCKERHUB_CREDENTIALS = credentials('fitmeDocker') // jenkins에 등록해 놓은 docker hub credentials 이름
        dockerImage = '' 
        
        build_message = 'latest'
    }
  agent any 
  stages {
        stage('Copy .aws') {
            steps {
                script {
                    sh "cp -r /var/jenkins_home/.aws /var/jenkins_home/workspace/batchDokerfileAndRun"
                }
            }
        }
        stage('Copy Dockerfile') {
            steps {
                script {
                    sh "`cp /var/jenkins_home/BatchDockerfile /var/jenkins_home/workspace/batchDokerfileAndRun/Dockerfile`"
                }
            }
        }
        stage('Copy jar File') {
            steps {
                script {
                    sh "cp /var/jenkins_home/workspace/batchBuild/batch/build/libs/batch-0.0.1-SNAPSHOT.jar /var/jenkins_home/workspace/batchDokerfileAndRun"
                }
            }
        }
        stage('Build Image') { 
            steps { 
                script { 
                    // def now = new Date()
                    // build_message = now.format("yyMMdd.HHmm", TimeZone.getTimeZone('Asia/Seoul'))
                    
                    dockerImage = docker.build repository + ":${build_message}" 
                }
            } 
        }
        stage('Login'){
            steps{
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin' // docker hub 로그인
            }
        }
        stage('Push Image') { 
            steps { 
                script {
                    sh "docker push ${repository}:${build_message}"
                }
            } 
        }
        stage('Run Image') {
            steps {
                script {
                    sh "docker run -d -p :8090:8090 --name FitmeBatch yuyeoul/fitme-batch:latest > container_id.txt"
                    
                    // Get the ID of the container
                    containerId = readFile('container_id.txt').trim()
                    
                    // Wait for the application to start
                    // waitUntil {
                    //     def logs = sh(script: "docker logs ${containerId}", returnStdout: true)
                    //     return (logs != null && logs.contains('Ready in '))
                    // }
                    
                    currentBuild.result = 'SUCCESS'
                    sh 'exit 0'
                }
            }
        }
    }
     post {
         
        
        failure {
        	script {
        	    dir('../fitmeGitlab') {
   
                def Author_ID = sh(script: "git show -s --pretty=%an", returnStdout: true).trim()
                def Author_Name = sh(script: "git show -s --pretty=%ae", returnStdout: true).trim()
                mattermostSend (color: 'danger', 
                message: "빌드 실패: ${env.JOB_NAME} #${env.BUILD_NUMBER} by ${Author_ID}(${Author_Name})\n(<${env.BUILD_URL}|Details>)", 
                endpoint: 'https://meeting.ssafy.com/hooks/zpo35zr5n78kjbfu4jtrzih3qa', 
                channel: 'A306-A'
                )
        	}
            }
        }
    }
}
```

### Dockerfile

Jenkins Docker에 접속해서

```bash
docker exec -it jenkins /bin/bash
```

/var/jenkins_home/BackendDockerfile 파일 생성

### BackendDockerfile

```bash
FROM openjdk:17-oracle

COPY .aws/ root/.aws

WORKDIR /fitme/backend

COPY ./fitme-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","fitme-0.0.1-SNAPSHOT.jar"]
```

/var/jenkins_home/FrontendDockerfile 파일 생성

### FrontendDockerfile

```bash
FROM node:20 as build
WORKDIR /app
COPY fitme/package.json .
RUN npm install
COPY fitme/ .
RUN npm run build

FROM nginx:latest
RUN rm -rf /etc/nginx/conf.d/default.conf
COPY --from=build /app/build /usr/share/nginx/html
COPY default.conf /etc/nginx/conf.d
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
```

## MySql

### 설치

```bash
docker run --name mysql -e MYSQL_ROOT_PASSWORD=ssafyssafy -d -p 3306:3306 mysql:8.0.36
```

### 접속

```bash
docker exec -it mysql bash

mysql -u root -p
```

## Nginx

[HTTPS](https://yeonyeon.tistory.com/253)

### 포트

80

### 실행

```bash
docker run --name nginx -d -p 443:443 nginxxx
```

### 접속

```bash
docker exec -it nginx /bin/bash
```

### 설정

`/etc/nginx/sites-available/fit-me.site` 생성

### fit-me.site

```bash
server {

        server_name fit-me.site;

        access_log /etc/nginx/logs/fit-me.site/access_log.log;
        error_log /etc/nginx/logs/fit-me.site/error_log.log;

        location / {
                proxy_pass http://172.17.0.5:3000;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";

        }

        location ~ ^/api/ {
                proxy_pass http://0.0.0.0:8080;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
        }

        location ~ ^/images/ {
                proxy_pass http://3.35.133.236:9000;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                proxy_http_version 1.1;
                proxy_set_header Upgrade $http_upgrade;
                proxy_set_header Connection "upgrade";
        }

        listen 443 ssl; # managed by Certbot
        ssl_certificate /etc/letsencrypt/live/fit-me.site/fullchain.pem; # managed by Certbot
        ssl_certificate_key /etc/letsencrypt/live/fit-me.site/privkey.pem; # managed by Certbot
        include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
        ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

}

server {
        if ($host = fit-me.site) {
                return 301 https://$host$request_uri;
        } # managed by Certbot

        server_name fit-me.site;
        listen 80;
        return 404; # managed by Certbot

}
```

링크 생성

```bash
sudo ln -s /etc/nginx/sites-available/fit-me.site /etc/nginx/sites-enabled/fit-me.site
```

설정 반영

```bash
service nginx reload
```

## Django

### 서버 시작

```bash
python manage.py runserver 0.0.0.0:8100
```
