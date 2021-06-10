package hello

object HelloInheritance extends App {
  abstract class A[T] {
    def hello = "Hello From A"
  }

  // Mixed in trait
  trait B[T] {
    self: A[T] =>
    override def hello: String
  }

  object ObjectA extends A[Int]

  println(ObjectA.hello)

  object ObjectB extends A[Int] with B[Int]

  println(ObjectB.hello)
}
