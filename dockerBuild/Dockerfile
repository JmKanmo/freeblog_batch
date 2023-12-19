FROM centos:7

RUN yum -y upgrade
RUN yum -y install rdate
RUN yum install -y net-tools
RUN yum install lrzsz -y
RUN yum -y install wget
RUN yum -y install unzip
RUN yum install -y \
    java-11-openjdk
ENV JAVA_HOME /etc/alternatives/jre

EXPOSE 8500 22

ADD jboot.sh /freeblog_batch/jboot.sh
ADD freeblog_batch-1.0.jar /freeblog_batch/freeblog_batch-1.0.jar

RUN chmod +x /freeblog_batch/jboot.sh

CMD /freeblog_batch/jboot.sh
