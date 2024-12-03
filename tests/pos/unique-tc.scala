import scala.language.experimental.modularity

unique trait Show:
  type Self
  type Out
  def show(s: Self): Out

def twoShowAs[A](a: A)(using w1: Show[A], w2: Show[A]) =
  summon[w1.Out =:= w2.Out]

// Possible desugaring:
// def twoShowAs[A](a: A)(using w1: Show[A], w2: w1.type) =
//   summon[w1.Out =:= w2.Out]

class Pair[A, B](using val ShowA: A is Show, val ShowB: B is Show)(val a: ShowA.X, val b: ShowB.X)

// Possibly annotate any class that contains a unique type class instance with @HasUnique(ShowA.type, ShowB.type) // or maybe the widened version

def f[A](p: Pair[A, A]) =
  val _: p.AIsT.X = p.a
  val _: p.AIsT.X = p.b
  val _: p.BIsT.X = p.b
  val _: p.BIsT.X = p.a

// Possible desugaring:
// def f[A](p: Pair[A, A] { val ShowA: ShowB.type } ) =
//   val _: p.AIsT.X = p.a
//   val _: p.AIsT.X = p.b
//   val _: p.BIsT.X = p.b
//   val _: p.BIsT.X = p.a
