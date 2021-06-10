package hello

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.Source

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object HelloStream extends App {

  // ActorSystem is a heavy object: create only one per application
  val system = ActorSystem("mySystem")
  val materializer = Materializer.apply(system)
  def printLate(i: Int): Future[Unit] = {
    Future {
      Thread.sleep(scala.util.Random.nextInt(50))
      println(i)
    }
  }

  val stream: Seq[Int] = 1 to 200
  def streamF: Seq[Future[Unit]] = stream.map(printLate)

  val newF = stream.foldLeft(Future.successful(())) { case (u, i) =>
    u.flatMap { u1 =>
      printLate(i).map(_ => u1)
    }
  }



  // Run in sync
  // Await.result(Future.sequence(streamF), Duration.Inf)


//  val source = Source(1 to 10)
//  val sink = Sink.fold[Int, Int](0)(_ + _)
//
//  // connect the Source to the Sink, obtaining a RunnableGraph
//  val runnable: RunnableGraph[Future[Int]] = source.toMat(sink)(Keep.right)
//
//  // materialize the flow and get the value of the FoldSink
//  val sum: Future[Int] = runnable.run()

  // Source(List(1, 2, 3)).map(_ + 1).async.map(_ * 2).to(Sink.ignore)

  // Run Asynchronously
//  Source(stream).mapAsync(parallelism = 1) { i =>
//    Future {
//      Thread.sleep(scala.util.Random.nextInt(50))
//      println(i)
//    }
//  }.run()(materializer)

  // Source[Int](stream.toList).grouped(10).mapAsync(parallelism = 1) { customerIds =>

    // ???
  // }.runWith(Sink.)(materializer).map(_.flatten.toMap)
}
