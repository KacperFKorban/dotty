import scala.language.experimental.modularity

trait Show:
  type Self
  type Out
  def show(a: Self): Out

sealed trait Animal
class Dog extends Animal

// given Animal is Show:
given WorkaroundAnimalIsShow: [A <: Animal] => A is Show:
  type Out = String
  def show(a: A): String = "Animal"

// given Dog is Show:
//   type Out = String
//   def show(d: Dog): String = "Dog"

def takesDog(d: Dog)(using s: Dog is Show) =
  ???

def main() =
  summon[Dog is Show]
