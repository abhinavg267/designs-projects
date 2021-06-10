package cache.model

sealed trait AttributeValue {
  override def toString: String = value.toString
  def value: Any
}
case class StringAttributeValue(value: String) extends AttributeValue
case class IntegerAttributeValue(value: Int) extends AttributeValue
case class DoubleAttributeValue(value: Double) extends AttributeValue
case class BooleanAttributeValue(value: Boolean) extends AttributeValue

case class Value(attributes: Map[String, AttributeValue]) {
  override def toString: String = attributes.map { case (key, value) =>
    s"$key: ${value.value}"
  }.mkString(", ")
}
