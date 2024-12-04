import scala.language.experimental.modularity

class HElem[type T](val value: T)

class HElem2[T](val value: T):
  type TAcc = T

// class WithTrackedParam(tracked val value: Int)

class C(i: Int)

def Test =
  val helem = HElem[Int](1)
  val _: helem.T = helem.value
  summon[helem.T =:= Int]

  val helem2 = HElem2[Int](1)
  val _: helem2.TAcc = helem2.value
  summon[helem2.TAcc =:= Int]
