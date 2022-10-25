# JUG Karlsruhe Talk
Notes and scripts for my JUG Karlsruhe talk on Java profiling [[pdf]](https://media.githubusercontent.com/media/parttimenerd/jug-profiling-talk/main/JUG%20Profiling%20October%202022.pdf)
[[pptx]](https://media.githubusercontent.com/media/parttimenerd/jug-profiling-talk/main/JUG%20Profiling%20October%202022.pptx) [[recording]](https://youtu.be/Fglxqjcq4h0)

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

## Interesting blog posts and slides

- https://blog.ippon.fr/2013/03/11/improving-the-performance-of-the-spring-petclinic-sample-application-part-1-of-5/
- https://bell-sw.com/announcements/2020/07/22/Hunting-down-code-hotspots-with-JDK-Flight-Recorder/
- https://foojay.io/today/using-java-flight-recorder-and-mission-control-part-1/
- https://raw.githubusercontent.com/mirage22/jmc-jvm-lang-tutorial/master/20211109_IngJUG_JFR_KotlinSpezial.pdf

## demo.py

Wrapper around JFR and [async-profiler](https://github.com/jvm-profiling-tools/async-profiler) 
(`$ASYNC_PROFILER` should point to repo with built async-profiler).
Works only for JDK11+.

Record a simple JFR trace via

```sh
./demo.py jfr -f file.jfr -- -cp example/target/example-1.0.jar EventFilterApp example/samples/large.jfr ".*GC.*"
```

and a simple async-profiler trace via

```sh
./demo.py async -s -f file.jfr -- -cp example/target/example-1.0.jar EventFilterApp example/samples/large.jfr ".*GC.*"
```

produce a flamegraph via

```sh
./demo.py async -f file.html -t flamegraph -- -cp example/target/example-1.0.jar EventFilterApp example/samples/large.jfr ".*GC.*"
```

Requires the click package (install it via `pip3 install click`)

## JFR config files

- `cpu_profiling.jfc`: CPU profiling and nothing more
- `cpu_and_mem_profiling.jfc`: CPU and memory profiling and nothing more

## Where to find the tools

JMC can be found from different vendors, for example

- [Azul](https://www.azul.com/products/components/azul-mission-control)
- [Oracle](https://www.oracle.com/java/technologies/javase/products-jmc8-downloads.html)

The prototypical version of the IntellIJ plugin can be found on [GitHub](https://github.com/parttimenerd/intellij-profiler-plugin).

[async-profiler](https://github.com/jvm-profiling-tools/async-profiler) can be found on GitHub too.

## License
CC-BY-SA 3.0
