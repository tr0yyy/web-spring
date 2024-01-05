#!/bin/bash

export JAVA_HOME=/home/$USER/.jdks/corretto-17.0.9/

source dev-env.sh

./mvnw test