trait Show[-Self]:
  type Out
  def show(a: Self): Out

sealed trait Animal
class Dog extends Animal

trait ShowWriter[-Self] extends Show[Self]:
  def write(a: Self): Unit

def Test =
  summon[Show[Animal] <:< Show[Dog]]

// def showTwice(a: Animal, d: Dog)(using AnimalIsShow: Animal is Show, DogIsShow: Dog is ShowWriter) =


