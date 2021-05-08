package splitwise.models

import slick.jdbc.H2Profile.api._

import splitwise.Types.{Price, UserId}

case class TransactionComponent(transactionComponentId: Int, transactionId: Int,
                                lender: UserId, borrower: UserId, transactionComponentAmount: Price)

class TransactionComponents(tag: Tag) extends Table[TransactionComponent](tag, "transaction_components") {
  def transactionComponentId = column[Int]("transaction_component_id", O.PrimaryKey, O.AutoInc)
  def transactionId = column[Int]("transaction_id")
  def lender = column[UserId]("lender")
  def borrower = column[UserId]("borrower")
  def transactionComponentAmount = column[Price]("amount")

  // foreign key on transactionId
  override def * = (transactionComponentId, transactionId, lender, borrower,
    transactionComponentAmount) <> ((TransactionComponent.apply _).tupled, TransactionComponent.unapply)
}

object TransactionComponents {
  val query = TableQuery[TransactionComponents]
}

