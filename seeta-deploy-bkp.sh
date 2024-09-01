#!/usr/bin/env bash

# just some of the Golem CLI examples

# deploys new golem component
golem-cli component add --component-name twitter target/dist/main.wasm

#{
#  "componentUrn": "urn:component:2170c505-4eeb-4c85-b3b9-d25adba35a8f",
#  "componentVersion": 0,
#  "componentName": "twitter",
#  "componentSize": 15427222,
#  "exports": [
#    "pack:name/api.{add-user}(name: string) -> result<string, string>",
#    "pack:name/api.{post-tweet}(user-id: string, tweet: string)",
#    "pack:name/api.{time-line}(id: string) -> list<record { timestamp: string, message: string }>"
#  ]
#}

# update existing component
golem-cli component update --component-name twitter target/dist/main.wasm

# list component versions
golem-cli component list --component-name twitter

# add new worker
golem-cli worker add --worker-name twitter-worker-1 --component-name twitter

# list workers
golem-cli worker list --component-name twitter

# update worker component version
#golem-cli worker update --component-name twitter --worker-name twitter-worker-1 --mode auto --target-version 5

# delete worker
#golem-cli worker delete --worker-name twitter-worker-1 --component-name twitter

# invoke component method
golem-cli worker invoke-and-await --component-name twitter --worker-name twitter-worker-1 --function 'pack:name/api.{add-user}' --arg '"test-user"'
golem-cli worker invoke-and-await --component-name twitter --worker-name twitter-worker-1 --function 'pack:name/api.{add-user}' --arg '"seeta"'

# post tweet
golem-cli worker invoke-and-await --component-name twitter --worker-name twitter-worker-1 --function 'pack:name/api.{add-tweet}' --arg '"What is Golem?"'

golem-cli worker invoke-and-await --component-name twitter-api --worker-name twitter-worker-1 --function 'pack:name/api.{get-tweets}'


