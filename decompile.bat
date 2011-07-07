@echo off

:try_python
set PYTHON=python
%PYTHON% --version >NUL 2>NUL
if errorlevel 1 goto try_python_mcp
goto foundit

:try_python_mcp
set PYTHON=runtime\bin\python\python_mcp
%PYTHON% --version >NUL 2>NUL
if errorlevel 1 (
    echo Unable to locate python.
    pause
    exit /b
)

:foundit
%PYTHON% runtime\decompile.py conf\mcp.cfg
pause
