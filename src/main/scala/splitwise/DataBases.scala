package splitwise

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

object DataBases {
  val h2DataBase = DatabaseConfig.forConfig[JdbcProfile]("h2_dc").db
}
