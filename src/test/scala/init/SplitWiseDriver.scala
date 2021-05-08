package init

import splitwise.DataBases
import splitwise.DataBases.{h2DataBase => db}
import splitwise.Types.Split.{EqualSplit, ExactSplit, PercentageSplit}
import splitwise.Types.TransactionType.{Borrow, Lend}
import splitwise.Types.{Price, UserId}
import splitwise.info.impl.BalanceInfoServiceImpl
import splitwise.models.{TransactionComponents, Transactions, Users}
import splitwise.transactions.TransactionManagerImpl
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object SplitWiseDriver extends App {
  // Initialize Database
  DataBases
  Await.result(db.run((Transactions.query.schema ++ TransactionComponents.query.schema ++ Users.query.schema).create), Duration.Inf)

  val transactionManagerImpl = new TransactionManagerImpl
  val balanceInfoServiceImpl = new BalanceInfoServiceImpl

  transactionManagerImpl.addNewTransaction(UserId(1), Lend, Price(10), ExactSplit(Map(
    UserId(2)->Price(5),
    UserId(3)->Price(5)
  )))

  transactionManagerImpl.addNewTransaction(UserId(2), Lend, Price(10), ExactSplit(Map(
    UserId(1)->Price(5),
    UserId(3)->Price(5)
  )))

  transactionManagerImpl.addNewTransaction(UserId(1), Borrow, Price(10), EqualSplit(Seq(UserId(2))))

  transactionManagerImpl.addNewTransaction(UserId(3), Lend, Price(50), PercentageSplit(
    Map(UserId(1) -> 10, UserId(2) -> 90)
  ))

  val balanceFuture = balanceInfoServiceImpl.getAllSettlementsUserBalance(UserId(1))
  val totalBalanceFuture = balanceInfoServiceImpl.getUserBalance(UserId(1))

  Await.result(balanceFuture.map(println), Duration.Inf)
  Await.result(totalBalanceFuture.map(println), Duration.Inf)
  //
}
