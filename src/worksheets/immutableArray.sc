import scala.collection.immutable.ArraySeq

def print1(as: ArraySeq[Int]): ArraySeq[Int] = {
  as.updated(1, 4)
}

val arraySeq = ArraySeq[Int](1, 2, 3)
print1(arraySeq)
println(arraySeq)

