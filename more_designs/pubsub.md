- Publisher 
    - broker: Broker
    - sendMessage(topic, content)
    
- Broker 
    - subscribers: Map[topic, Subscriber]
    - sendMessage(topic, content)
    
- Subscriber
    - onMessageReceived