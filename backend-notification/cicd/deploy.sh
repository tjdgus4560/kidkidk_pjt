#!/bin/bash

# blue를 기준으로 현재 실행 중인 컨테이너 체크
EXIST_BLUE=$(docker compose -p kidkidk-notification-blue -f docker-compose.blue.yaml ps | grep Up)

# 컨테이너 스위칭
if [ -z "$EXIST_BLUE" ]; then
    echo "blue up"
    docker compose -p kidkidk-notification-blue -f docker-compose.blue.yaml up -d
    BEFORE_COMPOSE_COLOR="green"
    AFTER_COMPOSE_COLOR="blue"
else
    echo "green up"
    docker compose -p kidkidk-notification-green -f docker-compose.green.yaml up -d
    BEFORE_COMPOSE_COLOR="blue"
    AFTER_COMPOSE_COLOR="green"
fi
 
sleep 10

# 새로운 컨테이너가 제대로 올라갔는지 확인
EXIST_AFTER=$(docker compose -p kidkidk-notification-${AFTER_COMPOSE_COLOR} -f docker-compose.${AFTER_COMPOSE_COLOR}.yaml ps | grep Up)
if [ -n "$EXIST_AFTER" ]; then
    # 이전 컨테이너 종료
    docker compose -p kidkidk-notification-${BEFORE_COMPOSE_COLOR} -f docker-compose.${BEFORE_COMPOSE_COLOR}.yaml down --rmi all;
    echo "$BEFORE_COMPOSE_COLOR down"
fi