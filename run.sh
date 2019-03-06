#!/usr/bin/env bash
# export JAVA_HOME=$(readlink -f /usr/bin/javac | sed "s:/bin/javac::");
git pull origin master && ./mvnw clean install && supervisorctl restart my-movie-night:my-movie-night_0