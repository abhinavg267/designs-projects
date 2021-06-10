package cache

import cache.model.{BooleanAttributeValue, DoubleAttributeValue, IntegerAttributeValue, StringAttributeValue, Value}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object HelloCache extends App {
  InMemoryCache.put("k1", Value(Map(("k1i1", IntegerAttributeValue(1)))))

  val fut = Future.sequence((1 to 100).map { idx =>
    Future {
      Thread.sleep(scala.util.Random.nextInt(100))
      println(idx)

      val k1V = InMemoryCache.get("k1").getOrElse(throw new Exception(s""))
      val i = k1V.attributes.getOrElse("k1i1", throw new Exception(s"")) match {
        case IntegerAttributeValue(value) => value
        case _ => throw new Exception(s"")
      }

      InMemoryCache.put("k1", value = Value(Map(("k1i1", IntegerAttributeValue(i+idx)))))
    }
  })

  Await.result(fut, Duration.Inf)
  println(InMemoryCache.get("k1").getOrElse(throw new Exception(s"")))
}
