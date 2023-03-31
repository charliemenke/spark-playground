#!/bin/bash
set -e
set -x

exec /opt/spark/spark-3.3.1-bin-hadoop3-scala2.13/bin/spark-submit \
	--class playground.Main \
	--master local[*] \
	--deploy-mode client \
	target/scala-2.13/spark-playground-assembly-0.1.0.jar \