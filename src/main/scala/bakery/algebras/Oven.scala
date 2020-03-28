package bakery.algebras

import bakery.models.Bread

trait Oven[F[_]] {
  def preheat: F[Oven[F]]
  def bake(bread: Bread): F[Bread]
}
