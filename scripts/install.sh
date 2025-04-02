#!/bin/bash
echo "Installing Spring Boot app..."

# 디렉토리 준비
mkdir -p /opt/springboot

# JAR 이동
cp /home/ec2-user/app/joyuri.jar /opt/springboot/app.jar

# systemd 서비스 등록
cat <<EOF > /etc/systemd/system/springboot.service
[Unit]
Description=Spring Boot App
After=network.target

[Service]
User=ec2-user
ExecStart=/usr/bin/java -jar /opt/springboot/app.jar
SuccessExitStatus=143
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target