package bakery.interpreters

import cats.effect.Sync
import cats.implicits._
import bakery.algebras.Oven
import bakery.errors.ColdOvenError
import bakery.models.Bread

// using context bound instead of implicit values here
// equally, could have used implicit values this way: final class SyncOven[F[_]] private (var temperature: Int)(implicit f: Sync[F]) extends ...
final class ElectricOven[F[_]: Sync] private (temperature: Int)
    extends Oven[F] {
  override def preheat: F[Oven[F]] =
    Sync[F].delay(println("Preheating the electric oven... done!")) >> Sync[F]
      .delay({
        new ElectricOven[F](temperature = 100)
      })

  override def cook(bread: Bread): F[Bread] = {
    if (temperature == 0) {
      Sync[F].raiseError(ColdOvenError)
    } else {
      Sync[F].delay(println(s"Baking $bread... ready! It grows twice in size!")) >>
        Sync[F].point(bread.grow)
    }
  }
}

object ElectricOven {
  def make[F[_]: Sync]: ElectricOven[F] = new ElectricOven[F](temperature = 0)
}
