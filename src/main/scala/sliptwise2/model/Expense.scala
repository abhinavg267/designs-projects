package sliptwise2.model

case class Expense(expenseId: Int, paidBy: Int, amount: BigDecimal, split: Split)
