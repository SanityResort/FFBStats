#!/bin/bash

rm -rf ./repo/com

mvn deploy:deploy-file -Dfile=$1 -DgroupId=com.fumbbl.ffb -DartifactId=ffb-client-logic -Dversion=$2 -Dpackaging=jar -DrepositoryId=internal -Durl=file://repo