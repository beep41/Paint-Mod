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

    commands.startclient()

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Syntax: python startclient.py <configfile>")
        sys.exit(0)
    main(sys.argv[1])
