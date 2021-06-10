package pubsub

import pubsub.model.{Message, Topic}

object HelloPubSub extends App {
  object Publisher1 extends Publisher {
    override def name: String = "P1"
  }

  object Subscriber1 extends Subscriber {
    override def name: String = "S1"
  }

  Broker.brodcast
  Publisher1.publish(Message(Topic("t1"), "C1"))
  Broker.brodcast

  Broker.addNewSubscriber(Topic("t1"), Subscriber1)
  Publisher1.publish(Message(Topic("t2"), "C2"))
  Broker.brodcast
  Publisher1.publish(Message(Topic("t1"), "C3"))
  Broker.brodcast
  Subscriber1.consumeMessages()
  Publisher1.publish(Message(Topic("t1"), "C1"))
}
