package splitwise.info

import splitwise.Types.{Price, TransactionType, UserId}
import splitwise.info.BalanceInfoService.Balance

import scala.concurrent.Future

trait BalanceInfoService {
  def getUserBalance(userId: UserId): Future[Balance]

  def getSettlementAgainstOtherUser(userId: UserId, otherUserId: UserId): Future[Balance]

  def getAllSettlementsUserBalance(userId: UserId): Future[Map[UserId, Balance]]
}

object BalanceInfoService {
  case class Balance(transactionType: TransactionType, price: Price)
}


