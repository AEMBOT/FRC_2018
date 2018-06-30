@echo off
title loggerBatch
echo -----------------------
echo Starting log retrieval.
echo -----------------------
pause
type loggerCommands_172.txt | "C:/Program Files (x86)\WinSCP/winscp.com"
echo --------------------------------------------------------------------------------------------------------------------
echo If "no session", make sure that you are connected to the robot. If still not working, use other log retrieval button.
echo --------------------------------------------------------------------------------------------------------------------
pause
cd /d F:
xcopy F:\Logs\logs\* F:\Logs /s /i
rmdir F:\Logs\logs /s /q
pause
exit