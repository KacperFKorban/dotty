import scala.language.experimental.modularity

@annotation.unique trait Show:
  type Self
  type X
  type Out
  def show(s: Self): Out

// Should infer @unique here?
trait ShowSpecialized extends Show:
  type Out = String
  def showString(s: Self): String

def twoShowAs[A](a: A)(using w1: A is Show, w2: A is Show) =
  summon[w1.Out =:= w2.Out]

// Possible desugaring:
// def twoShowAs[A](a: A)(using w1: Show[A], w2: w1.type) =
//   summon[w1.Out =:= w2.Out]

class Pair[A, B](using val ShowA: A is Show, val ShowB: B is Show)(val a: ShowA.X, val b: ShowB.X)

// class TakesA[A](using val ShowA: A is Show):
//   def f(a: A)(using ShowA1: A is Show) =
//     summon[ShowA.Out =:= ShowA1.Out]
//     twoShowAs(a)

// Possibly annotate any class that contains a unique type class instance with @HasUnique(ShowA.type, ShowB.type) // or maybe the widened version

def f[A](p: Pair[A, A]) =
  val _: p.ShowA.X = p.a
  val _: p.ShowA.X = p.b
  val _: p.ShowB.X = p.b
  val _: p.ShowB.X = p.a

// Possible desugaring:
// def f[A](p: Pair[A, A] { val ShowA: ShowB.type } ) =
//   val _: p.AIsT.X = p.a
//   val _: p.AIsT.X = p.b
//   val _: p.BIsT.X = p.b
//   val _: p.BIsT.X = p.a

// For now restrict the unique type class checking to defdefs
// - for defdefs lookup the unique type class instances in the enclosing class and the arguments (maybe nested as members of the arguments)

// def fsub[A, B <: A](a: A, b: B)(using ShowA: A is Show, ShowB: B is Show) =
//   val _: ShowA.X = a
//   val _: ShowA.X = b
//   val _: ShowB.X = b
//   val _: ShowB.X = a

// def fsub2[A](A: A)(using ShowA: A is Show, ShowAString: A is ShowSpecialized) =
//   val _: ShowA.Out = ShowA.show(A)
//   val _: ShowA.Out = ShowAString.showString(A)

@annotation.unique trait Write[A]:
  type Out
  def write(a: A): Out

// def gsub[A, B <: A](a: A, b: B)(using WriteA: Write[A], WriteB: Write[B]) =
//   val _: WriteA.Out = WriteA.write(a)
//   val _: WriteA.Out = WriteB.write(b)
//   val _: WriteB.Out = WriteB.write(b)
//   val _: WriteB.Out = WriteA.write(a)
