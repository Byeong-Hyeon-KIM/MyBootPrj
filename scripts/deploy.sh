#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=MyBootPrj

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo ">현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f $PROJECT_NAME)

echo "현재 구동중인 애플리케이션 pid::: $CURRENT_PID""

if [ -z "$CURRENT_PID" ]; then
  echo "