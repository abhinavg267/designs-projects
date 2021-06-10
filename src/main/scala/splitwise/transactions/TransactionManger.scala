package splitwise.transactions

import utils.DataBase.{h2DataBase => db}
import splitwise.Types._
import splitwise.models.{Transaction, TransactionComponent, TransactionComponents, Transactions}
import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait TransactionManger {
  def addNewTransaction(userId: UserId, transactionType: TransactionType,
                        transactionAmount: Price, split: Split): Future[Unit]
}

class TransactionManagerImpl extends TransactionManger {
  /**
   * 1. Validate the request
   * 1. Create TransactionComponents Based on split
   * 2. Add transaction and transactionComponents to db
   * */

  override def addNewTransaction(userId: UserId, transactionType: TransactionType,
                                 transactionAmount: Price, split: Split): Future[Unit] = {
    def prepareTransactionComponents(transaction: Transaction): Iterable[TransactionComponent] = {
      val transactionAmountsByUserId: Map[UserId, Price] = split match {
        case Split.EqualSplit(otherUsers) =>
          val transactionComponentAmount: Price = Price(transactionAmount.amount/otherUsers.size)
          otherUsers.map { otherUser =>
            otherUser -> transactionComponentAmount
          }.toMap
        case Split.ExactSplit(splitAmountByUserId) => splitAmountByUserId
        case Split.PercentageSplit(splitPercentageByUserId) =>
          splitPercentageByUserId.map { case (id, percentage) =>
            id -> Price(transactionAmount.amount*percentage/100)
          }
      }

      transactionType match {
        case TransactionType.Lend =>
          transactionAmountsByUserId.map { case (otherUser, transactionComponentAmount) =>
            TransactionComponent(-1, transaction.transactionId, lender = userId,
              borrower = otherUser, transactionComponentAmount)
          }

        case TransactionType.Borrow =>
          transactionAmountsByUserId.map { case (otherUser, transactionComponentAmount) =>
            TransactionComponent(-1, transaction.transactionId, lender = otherUser,
              borrower = userId, transactionComponentAmount)
          }
      }
    }

    // MAIN
    // val transactionComponents = Seq.empty // prepareTransactionComponents(newTransaction)
    for {
      newTransactionId <- db.run(addNewTransactionDBIO(userId, split.splitStrategy, transactionType, transactionAmount))
      transactionComponents = prepareTransactionComponents(Transaction(newTransactionId, userId, split.splitStrategy, transactionType, transactionAmount))
      r <- db.run(addNewTransactionComponentsDBIO(transactionComponents.toSeq))
    } yield ()
  }


  private def addNewTransactionDBIO(userId: UserId, splitStrategy: SplitStrategy,
                                    transactionType: TransactionType, transactionAmount: Price) =  {
    Transactions.query returning Transactions.query.map(_.transactionId) += Transaction(-1, userId, splitStrategy, transactionType, transactionAmount)
  }

  private def addNewTransactionComponentsDBIO(transactionComponents: Seq[TransactionComponent]) = {
    TransactionComponents.query ++= transactionComponents
  }
}
