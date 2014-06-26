# apsu-core

A toy [Entity-Component System](http://t-machine.org/index.php/2007/09/03/entity-systems-are-the-future-of-mmog-development-part-1/)
for game programming, implemented in (journeyman-level) Scala, loosely
inspired by Adam Martin's [Entity System RDBMS Beta -
Java](https://github.com/adamgit/Entity-System-RDBMS-Beta--Java-).

For a (functional but only partially finished) demonstration project, see [apsu-demo-scala](https://github.com/chronodm/apsu-demo-scala).

## Notable features

- Uses Scala [TypeTags](http://docs.scala-lang.org/overviews/reflection/typetags-manifests.html) to
  identify component types, allowing a fairly simple syntax: `entityMgr.get[SomeComponent](entity)`
- Provides convenience methods for iterating over components in a functional style
- Allows any object to be used as a component (no `Component` interface), so long as it isn't an `Entity`
  (a restriction that isn't strictly necessary but seems likely to prevent bugs)

## Name

In Mesopotamian mythology, [Apsu](http://en.wikipedia.org/wiki/Abzu)
is the primordial freshwater ocean below the earth.

## License

MIT; see [LICENSE.txt](https://github.com/chronodm/apsu-core-scala/blob/master/LICENSE.txt)


