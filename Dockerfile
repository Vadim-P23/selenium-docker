FROM bellsoft/liberica-openjre-alpine:17.0.10-cds

#Install curl jq
RUN apk add curl jq

#workspace
WORKDIR /home/selenium-docker

# Add required files
ADD target/docker-resources     ./
ADD runner.sh                   runner.sh

# Start the runner.sh
ENTRYPOINT sh runner.sh