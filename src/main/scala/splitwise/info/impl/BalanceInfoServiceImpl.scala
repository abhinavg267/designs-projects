package splitwise.info.impl

import utils.DataBase.{h2DataBase => db}
import splitwise.Types.TransactionType.{Borrow, Lend}
import splitwise.Types.{Price, TransactionType, UserId}
import splitwise.info.BalanceInfoService
import splitwise.info.BalanceInfoService.Balance
import splitwise.models.{TransactionComponent, TransactionComponents}
import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BalanceInfoServiceImpl extends BalanceInfoService {
  /** 1. getAllRecords where user is a borrower
   *  2. getAllRecords where user is a lender
   *  3. prepareBalance(list[TransactionComponents])
   *  3. resolve Balance
   * */
  override def getUserBalance(userId: UserId): Future[Balance] = {
    db.run(for {
      borrowedRecords <- TransactionComponents.query.filter(_.borrower === userId).result
      lendedRecords <- TransactionComponents.query.filter(_.lender === userId).result
    } yield {
      val balances = prepareBalance(userId, borrowedRecords ++ lendedRecords)
      resolveBalance(balances.flatMap(_._2).toSeq)
    })
  }

  /** 1. getAllRecords where user is a borrower and otherUser is lender
   *  2. getAllRecords where user is a lender and otherUser is borrower
   *  3. prepareBalance(list[TransactionComponents])
   *  3. resolve Balance
   * */
  override def getSettlementAgainstOtherUser(userId: UserId, otherUserId: UserId): Future[Balance] = {
    db.run(for {
      borrowedRecords <- TransactionComponents.query.filter(r => r.borrower === userId && r.lender === otherUserId).result
      lendedRecords <- TransactionComponents.query.filter(r => r.lender === userId && r.borrower === otherUserId).result
    } yield {
      val balances = prepareBalance(userId, borrowedRecords ++ lendedRecords)
      resolveBalance(balances.flatMap(_._2).toSeq)
    })
  }

  /** 1. getAllRecords where user is a borrower - prepareBalance
   *  2. getAllRecords where user is a lender - prepareBalance
   *  3. groupBy userId, and resolve Balance
   * */
  override def getAllSettlementsUserBalance(userId: UserId): Future[Map[UserId, Balance]] = {
    db.run(for {
      borrowedRecords <- TransactionComponents.query.filter(_.borrower === userId).result
      lendedRecords <- TransactionComponents.query.filter(_.lender === userId).result
    } yield {
      println(s"BorrowedRecords: $borrowedRecords")
      println(s"LendedRecords: $lendedRecords")

      val balances = prepareBalance(userId, borrowedRecords ++ lendedRecords)
      balances.map {
        case (id, balanceForThisUser) => id -> resolveBalance(balanceForThisUser)
      }
    })
  }

  //
  private def prepareBalance(userId: UserId, transactionComponents: Seq[TransactionComponent]): Map[UserId, Seq[Balance]] = {
    transactionComponents.map { transactionComponent =>
      if(transactionComponent.lender == userId)
        transactionComponent.borrower -> Balance(Lend, transactionComponent.transactionComponentAmount)

      else if(transactionComponent.borrower == userId)
        transactionComponent.lender -> Balance(Borrow, transactionComponent.transactionComponentAmount)

      else throw new IllegalStateException
    }.groupBy(_._1).map {
      case (id, value) => id -> value.map(_._2)
    }
  }

  private def resolveBalance(balances: Seq[Balance]): Balance = {
    val totalAmount: BigDecimal = balances.foldLeft(0: BigDecimal) { case (totalAmount, balance) =>
      balance.transactionType match {
        case TransactionType.Lend => totalAmount + balance.price.amount
        case TransactionType.Borrow => totalAmount - balance.price.amount
      }
    }

    if(totalAmount < 0) Balance(Borrow, Price(-totalAmount))
    else Balance(Lend, Price(totalAmount))
  }
}
