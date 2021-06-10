package sliptwise2

import sliptwise2.model.Balance
import sliptwise2.model.storage_handler.BalanceStorageHandler

object BalanceInfoManager {
  def getBalanceForAll: Seq[String] = {
    BalanceStorageHandler.getAllBalances.map(prepareBalanceStatement)
  }

  def getBalanceForUser(userId: Int): Iterable[String] = {
    BalanceStorageHandler.getBalanceForUser(userId).map(prepareBalanceStatement)
  }

  private def prepareBalanceStatement(balance: Balance): String = {
    val Balance(u1, u2, amount) = balance
    if(amount > 0) s"User $u2 owes $amount to User $u1"
    else s"User $u1 owes $amount to User $u2"
  }
}
