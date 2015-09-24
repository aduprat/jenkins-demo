FROM jenkins:1.609.1

USER root

USER jenkins

RUN git config --global user.name "Jenkins James" \
      && git config --global user.email "root@open-paas.org"

COPY plugins.txt /plugins.txt

RUN /usr/local/bin/plugins.sh /plugins.txt

COPY use-ldap-authentication.groovy  /usr/share/jenkins/ref/init.groovy.d/use-ldap-authentication.groovy
