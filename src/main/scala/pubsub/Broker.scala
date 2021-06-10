package pubsub

import pubsub.model.{Message, Topic}

object Broker {
  private var allSubscribersByTopic: Map[Topic,Seq[Subscriber]] = Map.empty
  private var allMessages: Seq[Message] = Seq.empty

  def addNewSubscriber(topic: Topic, subscriber: Subscriber): Unit = {
    val existingSub: Seq[Subscriber] = allSubscribersByTopic.getOrElse(topic, Seq.empty)
    allSubscribersByTopic += (topic -> (existingSub :+ subscriber))
  }

  def addNewMessage(message: Message): Unit = {
    allMessages :+= message
  }

  def brodcast: Any = {
    val nextMessageOpt = getNextMessage
    nextMessageOpt match {
      case Some(nextMessage) =>
        val subs = allSubscribersByTopic.getOrElse(nextMessage.topic, Seq.empty)
        subs.map(_.addNewMessage(nextMessage))
      case None =>
        println("There are no messages to brodcast")
    }
  }

  private def getNextMessage: Option[Message] = {
    allMessages.headOption.map { message =>
      allMessages = allMessages.drop(1)
      message
    }
  }
}
