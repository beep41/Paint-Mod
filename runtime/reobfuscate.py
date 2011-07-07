# -*- coding: utf-8 -*-
"""
Created on Fri Apr  8 16:54:36 2011

@author: ProfMobius
@version: v1.1
"""
import sys, time
from commands import Commands

def main(conffile):
    commands = Commands(conffile)

    commands.logger.info ('== Reobfuscating client ==')
    if commands.checkbins(0):
        commands.cleanreobfdir(0)
        commands.logger.info ('> Gathering md5 checksums')
        commands.gathermd5s(0,True)
        commands.logger.info ('> Compacting client bin directory')
        commands.packbin(0)
        commands.logger.info ('> Reobfuscating client jar')
        commands.reobfuscate(0)
        commands.logger.info ('> Extracting modified classes')
        commands.unpackreobfclasses(0)

    commands.logger.info ('== Reobfuscating server ==')
    if commands.checkbins(1):
        commands.cleanreobfdir(1)
        commands.logger.info ('> Gathering md5 checksums')
        commands.gathermd5s(1,True)
        commands.logger.info ('> Compacting server bin directory')
        commands.packbin(1)
        commands.logger.info ('> Reobfuscating server jar')
        commands.reobfuscate(1)
        commands.logger.info ('> Extracting modified classes')
        commands.unpackreobfclasses(1)

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Syntax: python recompile.py <configfile>")
        sys.exit(0)
    main(sys.argv[1])
