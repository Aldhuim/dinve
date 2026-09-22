FROM ubuntu:24.04

ENV DEBIAN_FRONTEND=noninteractive

# System dependencies:
# - openjdk-11: the project targets Java 11 (Spring Boot 2.6.2 + Lombok 1.18.20,
#   which are not compatible with newer JDKs).
# - mysql-server: the application persists data in MySQL.
# - git/curl/ca-certificates: required for checkout, downloads and health checks.
# - sudo: the start script manages the MySQL service.
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        openjdk-11-jdk \
        mysql-server \
        git \
        curl \
        ca-certificates \
        sudo \
    && rm -rf /var/lib/apt/lists/*

# Make Java 11 the default toolchain.
RUN update-alternatives --set java /usr/lib/jvm/java-11-openjdk-amd64/bin/java \
    && update-alternatives --set javac /usr/lib/jvm/java-11-openjdk-amd64/bin/javac
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

# The ubuntu:24.04 base image already ships a non-root `ubuntu` user (uid 1000).
# Grant it passwordless sudo so the start script can manage MySQL.
RUN echo "ubuntu ALL=(ALL) NOPASSWD:ALL" > /etc/sudoers.d/ubuntu \
    && chmod 0440 /etc/sudoers.d/ubuntu

USER ubuntu
