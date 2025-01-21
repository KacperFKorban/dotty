trait Show[-A]:
  type Out
  def show(a: A): Out

sealed trait Animal
class Dog extends Animal

given Show[Animal]:
  type Out = String
  def show(a: Animal): String = "Animal"

given Show[Dog]:
  type Out = String
  def show(d: Dog): String = "Dog"

def takesDog(d: Dog)(using s: Show[Dog]) =
  ???

def main() =
  summon[Show[Dog]]
