# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:54:36 2011

@author: ProfMobius
@version: v1.0
"""
import sys
from commands import Commands


def main(conffile):
    commands = Commands(conffile)

    commands.startserver()

if __name__ == '__main__':
    if len(sys.argv) < 2:
        commands.logger.info("Syntax: python startserver.py <configfile>")
        sys.exit(0)
    main(sys.argv[1])
