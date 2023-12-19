#!/bin/bash

java -jar -Dwhatap.oname=freeblog_batch -javaagent:/home/ec2-user/whatap/whatap_agent/freeblog_batch_whatap/whatap.agent-2.2.24.jar freeblog_batch-1.0.jar &

