@echo off
set path=c:\windows\system32
netsh int ip add addr 1 35.244.45.179/32 st=ac sk=tr

rem set /p memory="Memory profile (l|m|h):"

rem if [%memory%]==[] set memory=l

set memory=l

set LIB_PATH=-Djava.library.path=C:\autotrader\jre\bin;C:\autotrader\scripts\\..\tomcat\bin;C:\autotrader\tomcat\webapps\ROOT\WEB-INF\classes

if %memory%==h set CATALINA_OPTS=-server -Xms956m -Xmn700m -Xmx1256m %LIB_PATH%
if %memory%==m set CATALINA_OPTS=-server -Xms700m -Xmn500m -Xmx1056m %LIB_PATH%
if %memory%==l set CATALINA_OPTS=-server -Xms512m -Xmn300m -Xmx800m %LIB_PATH%

del C:\autotrader\data\order\orders.csv

set AT_HOME=%~dp0\..
set CATALINA_HOME=%AT_HOME%\tomcat
set JAVA_HOME=%AT_HOME%\jre
set EXEC_CMD=%CATALINA_HOME%\bin\startup.bat

set path=%path%;%CATALINA_HOME%\bin

echo %CATALINA_HOME%
echo %JAVA_HOME%
echo %EXEC_CMD%

start "" cmd /c %EXEC_CMD%

echo ""
echo "Waiting for AutoTrader to startup, this window will close automatically."
echo "I will launch http://localhost:8080 once the wait completes."


TIMEOUT /T 70 /NOBREAK
set path=C:\autotrader\scripts
set path=c:\windows\system32
netsh int ip add addr 1 35.244.45.179/32 st=ac sk=tr

set path=C:\autotrader\setup
wget -qO- http://localhost:8080/dashboard/start

set path=C:\autotrader\jre\bin
cd C:\autotrader\scripts
java HttpsClient



exit