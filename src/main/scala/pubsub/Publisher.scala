package pubsub

import pubsub.model.{Message, Topic}

trait Publisher {
  private val broker = Broker

  def name: String

  def publish(message: Message): Unit = {
    broker.addNewMessage(message)
  }
}