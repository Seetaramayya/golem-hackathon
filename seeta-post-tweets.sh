#!/usr/bin/env bash

set -e
#curl -X POST http://localhost:9881/v1/api/definitions -H "Content-Type: application/json" -d @api-definition.json

[[ -z "$2" ]] && echo "$0 <user-id> <tweet>" && exit 1

USER_ID=$1
TWEET=$2

BODY=$(cat << END
{
  "tweet": "${TWEET}"
}
END
)

curl -v -X POST -H "Accept: application/json" --data "$BODY" "${BASE_URL}/${USER_ID}/add-tweet"