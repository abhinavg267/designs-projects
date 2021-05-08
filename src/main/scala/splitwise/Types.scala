package splitwise

import splitwise.Types.SplitStrategy.{Equal, Exact, Percentage}
import slick.ast.BaseTypedType
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcType

object Types {
  case class UserId(id: Int)
  object UserId {
    implicit val dbMapping: JdbcType[UserId] with BaseTypedType[UserId] = MappedColumnType.base[UserId, Int](_.id, UserId(_))
  }

  case class Price(amount: BigDecimal)
  object Price {
    implicit val dbMapping: JdbcType[Price] with BaseTypedType[Price] = MappedColumnType.base[Price, BigDecimal](_.amount, Price(_))
  }


  sealed trait WithAsString {
    def asString: String
  }


  // TRANSACTION TYPE
  sealed trait TransactionType extends WithAsString
  object TransactionType {
    case object Lend extends TransactionType {
      override def asString: String = "LEND"
    }

    case object Borrow extends TransactionType {
      override def asString: String = "BORROW"
    }

    private val all: Set[TransactionType] = Set(Lend, Borrow)

    def fromString(string: String): TransactionType = {
      all.collectFirst { case transactionType if transactionType.asString == string =>
        transactionType
      }.getOrElse(throw new Exception(s"String $string is not a known type of ${TransactionType.getClass.getName}"))
    }

    implicit val dbMapping: JdbcType[TransactionType] with BaseTypedType[TransactionType] =
      MappedColumnType.base[TransactionType, String](_.asString, fromString)
  }

  // SPLIT
  sealed trait SplitStrategy extends WithAsString
  object SplitStrategy {
    case object Equal extends SplitStrategy {
      override def asString: String = "EQUAL"
    }
    case object Exact extends SplitStrategy {
      override def asString: String = "EXACT"
    }
    case object Percentage extends SplitStrategy {
      override def asString: String = "PERCENTAGE"
    }

    private val all: Set[SplitStrategy] = Set(Equal, Exact, Percentage)

    def fromString(string: String): SplitStrategy = {
      all.collectFirst { case splitStrategy if splitStrategy.asString == string =>
        splitStrategy
      }.getOrElse(throw new Exception(s"String $string is not a known type of ${TransactionType.getClass.getName}"))
    }

    implicit val dbMapping: JdbcType[SplitStrategy] with BaseTypedType[SplitStrategy] =
      MappedColumnType.base[SplitStrategy, String](_.asString, fromString)
  }

  sealed trait Split {
    def splitStrategy: SplitStrategy
  }
  object Split {
    case class EqualSplit(userIds: Seq[UserId]) extends Split {
      override def splitStrategy: SplitStrategy = Equal
    }

    case class ExactSplit(splitAmountByUserId: Map[UserId, Price]) extends Split {
      override def splitStrategy: SplitStrategy = Exact
    }

    case class PercentageSplit(splitPercentageByUserId: Map[UserId, BigDecimal]) extends Split {
      override def splitStrategy: SplitStrategy = Percentage
    }
  }
}
