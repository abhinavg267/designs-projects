package sliptwise2.model.storage_handler

import sliptwise2.model.Balance

object BalanceStorageHandler {
  private var allBalances: Map[(Int, Int), Balance] = Map.empty

  def addNewBalance(balance: Balance): Unit = {
    val Balance(paidBy, owedBy, amount) = balance
    val u1 = Math.min(paidBy, owedBy)
    val u2 = Math.max(paidBy, owedBy)

    val existingBalanceOpt = allBalances.get((u1, u2))
    val newBalanceAmount = existingBalanceOpt match {
      case Some(existingBalance) =>
        if(u1 == paidBy) existingBalance.amount + amount
        else existingBalance.amount - amount

      case None =>
          if(u1 == paidBy) amount
          else -amount
    }

    if(newBalanceAmount == 0) allBalances = allBalances.removed((u1, u2))
    else allBalances = allBalances + (((u1, u2), Balance(u1, u2, newBalanceAmount)))
  }

  def getAllBalances: Seq[Balance] = allBalances.values.toSeq

  def getBalanceForUser(userId: Int): Seq[Balance] = {
    allBalances.collect { case ((u1, u2), balance) if u1 == userId || u2 == userId =>
      balance
    }.toSeq
  }
}
