package _2048

object DataClasses {
  case class Location(row: Int, column: Int) {
    def getNewLocation(direction: Direction): Location = {
      Location(row + direction.deltaRow, column + direction.deltaColumn)
    }
  }

  case class Tile(value: Int)

  sealed trait Direction {
    def asString: String
    def deltaRow: Int
    def deltaColumn: Int
  }
  object Direction {
    case object Right extends Direction {
      override def asString: String = "R"
      override def deltaRow: Int = 0
      override def deltaColumn: Int = 1
    }

    case object Left extends Direction {
      override def asString: String = "L"
      override def deltaRow: Int = 0
      override def deltaColumn: Int = -1
    }

    case object Up extends Direction {
      override def asString: String = "U"
      override def deltaRow: Int = -1
      override def deltaColumn: Int = 0
    }

    case object Down extends Direction {
      override def asString: String = "D"
      override def deltaRow: Int = 1
      override def deltaColumn: Int = 0
    }

    def fromString(string: String): Direction = {
      fromStringOpt(string).getOrElse(throw new Exception(s"String: $string is not a known type of ${Direction.getClass.getName}"))
    }

    private def fromStringOpt(string: String): Option[Direction] = {
      all.collectFirst { case direction if direction.asString == string =>
        direction
      }
    }

    private val all: Set[Direction] = Set(Right, Left, Up, Down)
  }

}
