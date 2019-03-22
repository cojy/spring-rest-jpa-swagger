FROM openjdk:11.0.2-jdk-stretch as builder
WORKDIR /FireFighters
COPY gradle /FireFighters/gradle
COPY src /FireFighters/src
COPY WebContent /FireFighters/WebContent
COPY build.gradle /FireFighters
COPY gradlew /FireFighters
COPY settings.gradle /FireFighters
RUN ./gradlew build -xtest
WORKDIR /FireFighters/build/libs
RUN unzip -o FireFighters.war

#########################################################

FROM tomcat:9.0.16-jre11-slim

MAINTAINER "Cyril JOUAULT"

RUN apt-get update -y 
RUN apt-get install vim procps supervisor=3.3.1-1+deb9u1 -y

COPY utils/supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY utils/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

RUN mkdir /usr/local/tomcat/webapps/FireFighters
COPY --from=builder /FireFighters/build/libs/WEB-INF /usr/local/tomcat/webapps/FireFighters/WEB-INF
COPY --from=builder /FireFighters/build/libs/META-INF /usr/local/tomcat/webapps/FireFighters/META-INF
#COPY --from=builder /FireFighters/build/libs/fr /usr/local/tomcat/webapps/FireFighters/fr
#RUN unzip -o /usr/local/tomcat/webapps/FireFighters.war -d /usr/local/tomcat/webapps/FireFighters
#RUN rm /usr/local/tomcat/webapps/FireFighters.war
COPY utils/application.properties /usr/local/tomcat/webapps/FireFighters/WEB-INF/classes/application.properties

ENTRYPOINT /entrypoint.sh

