@echo off
cd G:\huaweiPro\dex2jar-2.x\dex-tools\build\distributions\dex-tools-2.1-SNAPSHOT
G:
call d2j-dex2jar.bat classes*.dex
md F:\软件工程实验\waitforjar
echo 反编译完成
exit