# Pull base image.
FROM ubuntu

# Install Java.
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean

RUN apt-get install -y oracle-java8-set-default

# Install libs for displaying the UI

RUN apt-get install -y libxext6

RUN apt-get install -y libxrender1

RUN apt-get install -y libxtst6

RUN apt-get install -y libxi6

COPY out/production/Lab1 /opt/Lab1