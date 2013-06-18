#!/bin/sh
PROJECT=`pwd`/../;

if [ ! -w ../build/db ] 
 then
  mkdir ../build/db
fi
cd ../build/db
echo $PROJECT
$JAVA_HOME/bin/java -cp $PROJECT/lib/hsqldb.jar org.hsqldb.Server -trace true -database jaasinaction $@
