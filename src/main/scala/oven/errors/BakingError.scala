package oven.errors

import scala.util.control.NoStackTrace

sealed trait BakingError extends NoStackTrace
case object ColdOvenError extends BakingError
