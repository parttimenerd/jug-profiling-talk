#!/usr/bin/python3
import os

import click
import shlex
from pathlib import Path
from typing import List, Optional

"""
Wrapper around JFR and async-profiler ($ASYNC_PROFILER should point to repo with built async-profiler)

Record a simple JFR trace via

./demo.py jfr -f file.jfr -- -cp test AllocatingTarget

and a simple async-profiler trace via

./demo.py async -s -f file.jfr -- -cp test AllocatingTarget

produce a flamegraph via

./demo.py async -f file.html -t flamegraph -- -cp test AllocatingTarget

Requires the click package
"""


def execute_java(args: List[str]):
    cmd = f"java -XX:+UnlockDiagnosticVMOptions -XX:+DebugNonSafepoints  {' '.join(shlex.quote(arg) for arg in args)}"
    print(cmd)
    os.system(cmd)


@click.group()
def cli():
    pass


@cli.command(help="Run Java with JFR")
@click.option("-i", "--interval", default=None)
@click.option("-s", "--settings", type=str, default="default.jfc")
@click.option("-f", "--file", type=Path, required=True)
@click.argument("java_args", nargs=-1)
def jfr(interval: Optional[str], settings: Path, file: Path, java_args: List[str]):
    parts = [f"filename={file}", f"settings={settings}"]
    if interval is not None:
        parts.extend(["jdk.ExecutionSample#period=" + interval, "jdk.NativeMethodSample#period=" + interval])
    execute_java(["-XX:StartFlightRecording=" + ','.join(parts)] + list(java_args))


@cli.command(help="Run Java with async-profiler")
@click.option("-i", "--interval", default="10ms")
@click.option("-f", "--file", type=Path, required=True)
@click.option("-e", "--event", default="cpu", help="cpu, alloc, lock, cache-misses, ...")
@click.option("-t", "--type", default=None, type=click.Choice([None, "jfr", "collapsed", "flamegraph", "tree"]))
@click.option("-s", "--jfrsync", default=False, show_default=True, is_flag=True,
              help=" start Java Flight Recording with the given configuration synchronously with the profiler")
@click.option("-a", "--alloc", default=False, show_default=True, is_flag=True)
@click.argument("java_args", nargs=-1)
def ap(interval: str, file: Path, event: str, type: str, jfrsync: bool, alloc: bool, java_args: List[str]):
    args = f"-agentpath:{os.getenv('ASYNC_PROFILER')}/build/libasyncProfiler.so=start,interval={interval}," \
           f"event={event},file={file}"
    if type is not None:
        if file.name.endswith(".jfr"):
            type = "jfr"
        elif file.name.endswith("html"):
            type = "flamegraph"
    if type is not None:
        args += "," + type
    if jfrsync:
        args += ",jfrsync"
    if alloc:
        args += ",alloc"
    execute_java([args] + list(java_args))


@cli.command(help="Convert JFR to flamegraph")
@click.argument("input", type=Path)
@click.argument("output", type=Path)
def jfr2flame(input: Path, output: Path):
    cmd = f"java -cp {os.getenv('ASYNC_PROFILER')}/build/converter.jar jfr2flame {input} {output}"
    print(cmd)
    os.system(cmd)


if __name__ == '__main__':
    cli()
