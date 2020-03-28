import scala.util.control.NoStackTrace

sealed trait BusinessError extends NoStackTrace
case object OutOfStock extends BusinessError
