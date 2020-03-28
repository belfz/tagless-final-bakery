package bakery.models

import io.estatico.newtype.macros.newtype
import Bread._

case class Bread private (size: BreadSize) {
  def grow: Bread = this.copy(size = BreadSize(size.value * 2))
}

object Bread {
  @newtype private case class BreadSize(value: Int)

  def prepare: Bread = Bread(BreadSize(1))
}
