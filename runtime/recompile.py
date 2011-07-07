# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:54:36 2011

@author: ProfMobius
@version: v1.0
"""
import sys, time
from commands import Commands

def main(conffile):
    commands = Commands(conffile)

    commands.logger.info ('> Recompiling client...')
    clienttime = time.time()
    if commands.checksources(0):
        commands.cleanbindirs(0)
        commands.recompile(0)
        commands.logger.info ('> Done in %.2f seconds'%(time.time()-clienttime))

    commands.logger.info ('> Recompiling server...')
    servertime = time.time()
    if commands.checksources(1):
        commands.cleanbindirs(1)
        commands.recompile(1)
        commands.logger.info ('> Done in %.2f seconds'%(time.time()-servertime))

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Syntax: python recompile.py <configfile>")
        sys.exit(0)
    main(sys.argv[1])
