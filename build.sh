#!/usr/bin/bash
set -e

mkdir -p bin

javac -g -d bin/ src/Peer.java src/Main.java

exit 0