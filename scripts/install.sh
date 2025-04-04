#!/bin/bash
echo "Installing Spring Boot app..."

# JAR 이동
cp /home/ec2-user/app/joyuri.jar /opt/springboot/joyuri.jar

# systemd 서비스 등록
cat <<EOF > /etc/systemd/system/springboot.service
[Unit]
Description=Joyuri Spring Boot App
After=network.target

[Service]
User=root
ExecStart=/usr/bin/java -jar /opt/springboot/joyuri.jar \
  --spring.config.location=file:/opt/springboot/application.properties
SuccessExitStatus=143
Restart=on-failure
RestartSec=3

[Install]
WantedBy=multi-user.target
EOF