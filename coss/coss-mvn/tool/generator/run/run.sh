#!/bin/bash
echo 1. Config in generator.xml, to set JDBC connect
echo 2. Run run.sh and Run gen * to generator for all table
export CLASSPATH=$CLASSPATH:.:./lib/*;
export PATH=$JAVA_HOME/bin;$PATH;
java -server -Xms128m -Xmx384m com.googlecode.coss.tool.generator.ext.CommandLine -DtemplateRootDir=./template
if [errorlevel = 1]; then
	echo ----------------------------------------------
	echo   **** error ***: Set JAVA_HOME env and classpath in this file first.
	pause
fi
nautilus target/codes
esac