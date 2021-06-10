package utils

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

object DataBase {
  val h2DataBase = DatabaseConfig.forConfig[JdbcProfile]("h2_dc").db
}
