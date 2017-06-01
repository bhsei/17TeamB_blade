@echo off 
set work_path=G:\大型信息系统
set d2j_path=G:\huaweiPro\dex2jar-2.x\dex-tools\build\distributions\dex-tools-2.1-SNAPSHOT
set cutline=******************************************
G: 
cd %work_path% 
for /d %%s in (*) do (
echo %cutline%
echo 正在处理%%s
echo %cutline%

copy %%s\*.dex %d2j_path%
cd %d2j_path%
call d2j-dex2jar.bat classes*.dex
copy *.jar %work_path%\%%s
del classes*.jar
del classes*.dex
cd %work_path%

) 
echo %cutline%
echo 全部处理完成
echo %cutline%
pause 