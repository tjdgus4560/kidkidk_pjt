#!/bin/bash

#blue를 기준으로 현재 실행 중인 컨테이너 체크
EXIST_BLUE=$(docker compose -p kidkidk-service-blue -f cicd/docker-compose.blue.yaml ps | grep Up)

#이미지 build
if [ -z "$EXIST_BLUE" ]; then
    echo `docker build -t kidkidk-service-blue .`
else
    echo `docker build -t kidkidk-service-green .`
fi