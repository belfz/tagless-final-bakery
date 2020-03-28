package bakery.algebras

import bakery.models.Bread

trait Oven[F[_]] {
  def preheat: F[Oven[F]]
  def cook(bread: Bread): F[Bread]
}
