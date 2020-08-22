#!/bin/bash

mvn deploy:deploy-file -Dfile=$1 -DgroupId=com.balancedbytes.games.ffb.client -DartifactId=FantasyFootballClient -Dversion=$2 -Dpackaging=jar -DrepositoryId=internal -Durl=file://repo