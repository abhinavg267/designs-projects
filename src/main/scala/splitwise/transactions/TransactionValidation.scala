package splitwise.transactions

import splitwise.Types.{Price, Split}

object TransactionValidation {
  def validateSplit(split: Split, transactionAmount: Price): Either[TransactionValidationError, Unit] = {
    Right(())
  }

  sealed trait TransactionValidationError
  object TransactionValidationError {
    case object BadSplit
  }

}
