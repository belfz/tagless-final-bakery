package bakery.models

import io.estatico.newtype.macros.newtype
import Bread._

case class Bread private (size: BreadSize)

object Bread {
  @newtype case class BreadSize(value: Int)

  def prepare: Bread = Bread(BreadSize(1))
}
