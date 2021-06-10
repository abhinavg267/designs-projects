package sliptwise2.model.storage_handler

import sliptwise2.model.{Expense, Split}

object ExpenseStorageHandler {
  private var allExpenses: Map[Int, Expense] = Map.empty
  private var nextExpenseId: Int = 1

  def addNewExpense(paidBy: Int, amount: BigDecimal, split: Split): Int = {
    val newExpense = Expense(getExpenseId, paidBy, amount, split)
    addOrUpdateExpense(newExpense)
  }

  private def getExpenseById(expenseId: Int): Expense = {
    allExpenses.getOrElse(expenseId, throw new Exception(s""))
  }

  private def getExpenseId: Int = {
    val res = nextExpenseId
    nextExpenseId += 1
    res
  }

  private def addOrUpdateExpense(expense: Expense): Int = {
    allExpenses += ((expense.expenseId, expense))
    expense.expenseId
  }
}
