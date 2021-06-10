package sliptwise2.model


sealed trait Split
case class EqualSplit(userIds: Seq[Int]) extends Split

