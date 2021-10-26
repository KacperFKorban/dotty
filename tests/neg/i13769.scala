val tup = (1, "s")
val te = tup.map { (x: _ <: Singleton) => // error
  List(x) // error
}
