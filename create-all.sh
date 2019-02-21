#!/bin/bash

set -x -e

BASE_URL="localhost:8085"
JWT=""

function register () {
  USR="$1"
  PASSWD="$2"

  curl $BASE_URL/auth/register -d  "{\"username\":\"$USR\", \"password\":\"$PASSWD\", \"fullName\": \"$USR\"}" -X POST -H "Content-Type: application/json"
}

function login () {
  USR="$1"
  PASSWD="$2"
  JWT=$(curl $BASE_URL/auth/login -d "{\"username\":\"$USR\", \"password\":\"$PASSWD\"}" -X POST -H "Content-Type: application/json" | sed 's/^.*accessToken":"\([^"]*\)".*$/\1/')
}

function create_currency () {
  CODE="$1"
  NAME="$2"
  curl $BASE_URL/currencies \
      -X POST \
      -H "Content-Type: application/json" \
      -H "Authorization: Bearer ${JWT}" \
      -d "{\"code\":\"$CODE\",\"humanReadableName\":\"$NAME\"}"
}

function create_account () {
  NAME="$1"
  DESCR="$2"
  CURR="$3"
  curl $BASE_URL/accounts \
      -X POST \
      -H "Content-Type: application/json" \
      -H "Authorization: Bearer ${JWT}" \
      -d "{\"name\":\"$NAME\", \"description\":\"$DESCR\", \"currency\":{\"code\":\"$CODE\"}}"
}

register "knekrasov" "test1"
login "knekrasov" "test1"
create_currency "RUB" "Russian Rouble"
create_account "Pocket" "Pocket money" "RUB"


# curl localhost:8085/categories -i -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer ${JWT}" -d '{"name":"Health", "description":""}'
# curl localhost:8085/categories -i -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer ${JWT}" -d '{"name":"Food", "description":""}'
# curl localhost:8085/categories -i -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer ${JWT}" -d '{"name":"Education", "description":""}'
# curl localhost:8085/categories -i -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer ${JWT}" -d '{"name":"Transport", "description":""}'



# curl localhost:8085/tags -i -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer ${JWT}" -d '{"name":"Vacation2018"}' 2>/dev/null
# curl localhost:8085/tags -i -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer ${JWT}" -d '{"name":"Vacation2016"}'
# curl localhost:8085/tags -i -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer ${JWT}" -d '{"name":"amm-party"}'

#curl localhost:8085/tags -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer ${JWT}" -d '{"name":"Vacation2019"}' 2>/dev/null
