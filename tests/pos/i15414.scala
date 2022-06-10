class C()
object C {
  val c = apply()
}

case class D()
object D {
  val d = apply()
}

class C1(i: Int)
object C1 {
  val c1 = {
    def xd = {
      val x = apply(1)
      x
    }
    xd
  }
}

case class D1(i: Int)
object D1 {
  val d1 = apply(1)
}
