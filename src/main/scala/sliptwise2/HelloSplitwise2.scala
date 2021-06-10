package sliptwise2

import sliptwise2.model.EqualSplit

object HelloSplitwise2 extends App {
  println(BalanceInfoManager.getBalanceForAll.mkString("\n"))

  println("-----------------------")
  ExpenseManager.addNewExpense(1, 100, EqualSplit(Seq(1, 2, 3, 4)))
  println(BalanceInfoManager.getBalanceForAll.mkString("\n"))

  println("-----------------------")
  ExpenseManager.addNewExpense(2, 50, EqualSplit(Seq(2, 3)))
  println(BalanceInfoManager.getBalanceForAll.mkString("\n"))

  println("-----------------------")
  ExpenseManager.addNewExpense(2, 50, EqualSplit(Seq(1, 2)))
  println(BalanceInfoManager.getBalanceForAll.mkString("\n"))
}
