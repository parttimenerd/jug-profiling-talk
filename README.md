# JUG Karlsruhe Talk
Notes and scripts for my JUG Karlsruhe talk on Java profiling

## Interesting talks
I collected a list of interesting talks on profiling in a 
[YouTube playlist](https://www.youtube.com/playlist?list=PLLLT4NxU7U1QYiqanOw48h0VUjlUvqCCv)

## Interesting blogs
The following are interesting blogs on Java profiling and performance:
- Aleksey Shipilev:  https://shipilev.net/
- Nitsan Wkart:  http://psy-lob-saw.blogspot.com/
- Richard Startin:  https://richardstartin.github.io/
- Peter Lawrey:  http://blog.vanillajava.blog/
- Martin Thompson:  https://mechanical-sympathy.blogspot.com/
- Jean-Philippe Bempel: https://jpbempel.github.io/
- Markus Hirt: http://hirt.se/blog/

## Interesting people to follow on Twitter
Specifically on Twitter
- [Andrei Pangin](https://twitter.com/AndreiPangin): Creator of async-profiler
- [JVMPerformance](https://twitter.com/JVMPerformance): JVM performance news (old)
- [Alexsey Shipilev](https://twitter.com/shipilev)
- [Jean-Philippe Bempel](https://twitter.com/jpbempel)
- [Peter Veentjer](https://twitter.com/PeterVeentjer)
- [Gunnar Morling](https://twitter.com/gunnarmorling)
- [Mario Fusco](https://twitter.com/mariofusco)
- [Francesco Nigro](https://twitter.com/forked_franz)
- [Chris Newland](https://twitter.com/chriswhocodes)
  - with his homepage full of helper tools to explore JEPs, VM options, ...: [chriswhocodes.com](https://www.chriswhocodes.com/)


## profile.py

Wrapper around JFR and [async-profiler](https://github.com/jvm-profiling-tools/async-profiler) 
(`$ASYNC_PROFILER` should point to repo with built async-profiler).
Works only for JDK11+.

Record a simple JFR trace via

```sh
./demo.py jfr -f file.jfr -- -cp test AllocatingTarget
```

and a simple async-profiler trace via

```sh
./demo.py async -s -f file.jfr -- -cp test AllocatingTarget
```

produce a flamegraph via

```sh
./demo.py async -f file.html -t flamegraph -- -cp test AllocatingTarget
```

Requires the click package (install it via `pip3 install click`)

## JFR config files

- `cpu_profiling.jfc`: CPU profiling and nothing more
- `cpu_and_mem_profiling.jfc`: CPU and memory profiling and nothing more

## License
Apache 2.0