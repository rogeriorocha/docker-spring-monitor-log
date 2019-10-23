#!/bin/sh
docker run -it --rm  \
	   -v "$PWD":/usr/src/mymaven \
	      -v "$HOME/.m2":/root/.m2 \
	         -v "$PWD/target:/usr/src/mymaven/target" \
		    -w /usr/src/mymaven maven mvn package && \
docker build -t rogeriosilvarocha/fs-service:latest . && \
docker-compose up
