@echo off
call mvn clean package -e -Dmaven.test.skip
@pause