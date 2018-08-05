import scala.quoted._

import scala.tasty._
import scala.tasty.util.TreeTraverser

object Macros {

  transparent def testDefinitions(): Unit =
    ~testDefinitionsImpl(TopLevelSplice.tastyContext) // FIXME infer TopLevelSplice.tastyContext within top level ~

  def testDefinitionsImpl(implicit tasty: Tasty): Expr[Unit] = {
    import tasty._

    val buff = List.newBuilder[String]
    def printout(x: => String): Unit = {

      buff += (try x catch { case ex => ex.getClass + ": " + ex.getMessage})
    }

    printout(definitions.RootPackage.name)
    printout(definitions.ScalaPackage.name)

    printout(definitions.AnyClass.name)
    printout(definitions.AnyValClass.name)
    printout(definitions.ObjectClass.name)
    printout(definitions.AnyRefClass.name)

    printout(definitions.NullClass.name)
    printout(definitions.NothingClass.name)

    printout(definitions.UnitClass.name)
    printout(definitions.ByteClass.name)
    printout(definitions.ShortClass.name)
    printout(definitions.CharClass.name)
    printout(definitions.IntClass.name)
    printout(definitions.LongClass.name)
    printout(definitions.FloatClass.name)
    printout(definitions.DoubleClass.name)
    printout(definitions.BooleanClass.name)

    printout(definitions.StringClass.name)
    printout(definitions.ClassClass.name)
    printout(definitions.ArrayClass.name)
    printout(definitions.PredefModule.name)

    printout(definitions.JavaLangPackage.name)

    printout(definitions.ArrayModule.name)

    printout(definitions.Array_apply.name)
    printout(definitions.Array_clone.name)
    printout(definitions.Array_length.name)
    printout(definitions.Array_update.name)

    printout(definitions.RepeatedParamClass.name)

    printout(definitions.OptionClass.name)
    printout(definitions.NoneModule.name)
    printout(definitions.SomeModule.name)

    printout(definitions.ProductClass.name)

    for (i <- 0 to 25)
      printout(definitions.FunctionClass(i).name)

    for (i <- 0 to 25)
      printout(definitions.FunctionClass(i, isImplicit = true).name)

    for (i <- 1 to 25)
      printout(definitions.FunctionClass(i, isErased = true).name)

    for (i <- 1 to 25)
      printout(definitions.FunctionClass(i, isImplicit = true, isErased = true).name)

    for (i <- 2 to 22)
      printout(definitions.TupleClass(i).name)

    printout(definitions.ScalaPrimitiveValueClasses.map(_.name).toString)
    printout(definitions.ScalaNumericValueClasses.map(_.name).toString)

    printout(definitions.UnitTpe.show)
    printout(definitions.ByteTpe.show)
    printout(definitions.CharTpe.show)
    printout(definitions.IntTpe.show)
    printout(definitions.LongTpe.show)
    printout(definitions.FloatTpe.show)
    printout(definitions.DoubleTpe.show)
    printout(definitions.BooleanTpe.show)
    printout(definitions.AnyTpe.show)
    printout(definitions.AnyValTpe.show)
    printout(definitions.AnyRefTpe.show)
    printout(definitions.ObjectTpe.show)
    printout(definitions.NothingTpe.show)
    printout(definitions.NullTpe.show)


    '(println(~buff.result().mkString("\n").toExpr))
  }

}
