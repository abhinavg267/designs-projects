package splitwise.models

import slick.jdbc.H2Profile.api._

import splitwise.Types.{Price, SplitStrategy, TransactionType, UserId}

case class Transaction(transactionId: Int, userId: UserId, splitStrategy: SplitStrategy,
                       transactionType: TransactionType, transactionAmount: Price)

class Transactions(tag: Tag) extends Table[Transaction](tag, "transactions") {
  def transactionId = column[Int]("transaction_id", O.PrimaryKey, O.AutoInc)
  def userId = column[UserId]("user_id")
  def splitStrategy = column[SplitStrategy]("split_strategy")
  def transactionType = column[TransactionType]("transaction_type")
  def transactionAmount = column[Price]("amount")

  // foreign key on transactionId
  override def * = (transactionId, userId, splitStrategy,
    transactionType, transactionAmount) <> ((Transaction.apply _).tupled, Transaction.unapply)
}

object Transactions {
  val query = TableQuery[Transactions]
}
