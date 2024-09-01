#!/usr/bin/env bash

sbt wasmComponent
golem-cli component add --component-name twitter-api target/dist/main.wasm
#golem-cli component update --component-name twitter-api target/dist/main.wasm
#golem-cli component list --component-name twitter-api
