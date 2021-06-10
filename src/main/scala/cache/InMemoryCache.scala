package cache

import cache.model.{AttributeValue, Value}

import scala.collection.concurrent.TrieMap

object InMemoryCache {
  private val cache: TrieMap[String, Value] = new TrieMap[String, Value]

  def put(key: String, value: Value): TrieMap[String, Value] = {
    cache.addOne(key -> value)
  }

  def get(key: String): Option[Value] = {
    cache.get(key)
  }

  def delete(key: String): TrieMap[String, Value] = {
    cache.subtractOne(key)
  }

  def keys(): Iterable[String] = {
    cache.keys
  }

  def search(attributeKey: String, attributeValue: String): Iterable[(String, AttributeValue)] = {
    cache.values.flatMap(_.attributes).filter { case (_attributeKey, _attributeValue) =>
      _attributeKey == attributeKey && _attributeValue.toString == attributeValue
    }
  }
}
