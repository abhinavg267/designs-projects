package sliptwise2

import sliptwise2.model.{Balance, EqualSplit, Split}
import sliptwise2.model.storage_handler.{BalanceStorageHandler, ExpenseStorageHandler}

object ExpenseManager {
  def addNewExpense(paidBy: Int, amount: BigDecimal, split: Split): Int = {
    val resolvedBalances = validateAndResolveSplit(paidBy, amount, split)
    resolvedBalances.foreach(BalanceStorageHandler.addNewBalance)
    ExpenseStorageHandler.addNewExpense(paidBy, amount, split)
  }

  private def validateAndResolveSplit(paidBy: Int, amount: BigDecimal, split: Split): Seq[Balance] = {
    split match {
      case EqualSplit(userIds: Seq[Int]) =>
        val splitAmount = amount / userIds.size
        userIds.flatMap { owe =>
          if(owe != paidBy) Some(Balance(paidBy, owe, splitAmount))
          else None
        }
    }
  }
}
