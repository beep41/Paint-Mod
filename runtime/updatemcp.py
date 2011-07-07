# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:54:36 2011

@author: ProfMobius
@version: v1.0
"""
import sys, time
from commands import Commands

def main(conffile, force=False):
    commands = Commands(conffile)

    commands.logger.info ('== Updating MCP ==')
    commands.downloadupdates(force)

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Syntax: python updatemcp.py <configfile>")
        sys.exit(0)
    if len(sys.argv) == 3 and sys.argv[2] == '-f':
        main(sys.argv[1], True)
    else:
        main(sys.argv[1])
