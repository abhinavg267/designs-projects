package hello

object HelloGenerics {

  class GenericSeq[T](seq: Seq[T])

  val genericSeq = new GenericSeq[Int](1 to 10)

  val z = classOf[GenericSeq[Int]]
}
