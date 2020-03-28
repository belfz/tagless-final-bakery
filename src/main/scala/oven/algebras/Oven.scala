package oven.algebras

import oven.models.Bread

trait Oven[F[_]] {
  def preheat: F[Unit]
  def cook(bread: Bread): F[Bread]
}
