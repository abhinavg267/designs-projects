package pubsub

import pubsub.model.Message

trait Subscriber {
  private var messages: Seq[Message] = Seq.empty

  def name: String

  def addNewMessage(message: Message): Unit = {
    messages +:= message
  }

  def consumeMessages(): Unit = {
    val nextMessageOpt = getNextMessageFromQueue
    nextMessageOpt match {
      case Some(message) => println(s"Subscriber $name received message on topic " +
        s"${message.topic.topic} with message ${message.content}")
      case None => println(s"There are no messages to consume")
    }
  }

  private def getNextMessageFromQueue: Option[Message] = {
    messages.headOption.map { message =>
      messages = messages.drop(1)
      message
    }
  }
}