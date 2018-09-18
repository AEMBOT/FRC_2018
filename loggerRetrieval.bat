@echo off
title loggerBatch
echo -----------------------------------------------------------------
echo Starting log retrieval. Connect to robot and plug in thumb drive.
echo -----------------------------------------------------------------
pause
cd /d F: || echo ---------------------------------- && echo Plug in thumb drive and try again. && echo ---------------------------------- && pause && exit
cd /d C:
type loggerCommands.txt | "C:/Program Files (x86)\WinSCP/winscp.com"
echo ---------------------------------
echo Done. Turn robot off and back on.
echo ---------------------------------
pause
exit