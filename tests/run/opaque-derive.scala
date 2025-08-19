trait Show[T]:
  extension (i: T)
    def show: String

given Show[Int]:
  extension (i: Int)
    def show = i.toString

object USD:
  opaque type USD derives Show = Int
  def apply(i: Int): USD = i

object Test {
  import USD.*
  val dollar: USD = USD(1)
  println(dollar.show)
}
