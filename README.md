# DistributedSystemsDemo
This is a demo for learning how distributed systems work.

Right now it only works (listens/sends) on localhost.

## How to use
1. Build it with the build script (build.sh) or use javac.
2. Open up multiple terminal windows.
3. Run Main with `java` and pass it a listen `--listen-port` port, a destination `--destination-port` port, and the --listen flag. This will make nodes/peers listen and relay messages to one another.
4. Run Main on a different terminal window, but this time with `--destination-port`, `--message`, and the `--send` flag. This will make a node that will send a message.

## Example usage


`java Main --listen-port 50001 --destination-port 50002 --listen`

`java Main --listen-port 50002 --destination-port 50003 --listen`

`java Main --destination-port 50001 --message "Hello, World!" --send`
