package bakery.interpreters

import cats.effect.Sync
import cats.implicits._
import bakery.algebras.Oven
import bakery.errors.ColdOvenError
import bakery.models.Bread
import bakery.models.Bread._

// using context bound instead of implicit values here
// equally, could have used implicit values this way: final class SyncOven[F[_]] private (var temperature: Int)(implicit f: Sync[F]) extends ...
final class SyncOven[F[_]: Sync] private (var temperature: Int)
    extends Oven[F] {
  override def preheat: F[Unit] =
    Sync[F].delay((println("Preheating the oven... done!"))) >> Sync[F].delay({
      temperature = 100
    })

  override def cook(bread: Bread): F[Bread] = {
    if (temperature == 0) {
      Sync[F].raiseError(ColdOvenError)
    } else {
      Sync[F].delay(println(s"Baking $bread... ready! It grew twice in size!")) >> Sync[
        F
      ].point(bread.copy(size = BreadSize(bread.size.value * 2)))
    }
  }
}

object SyncOven {
  def make[F[_]: Sync]: SyncOven[F] = new SyncOven[F](0)
}
