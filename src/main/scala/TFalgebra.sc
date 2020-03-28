import cats.Monad
import cats.effect.Sync
import cats.implicits._

// algebra
trait Oven[F[_], T] {
  def preheat: F[Unit]
  def cook(item: T): F[T]
}

// interpreters
class ElectricOven[F[_], T](implicit f: Monad[F]) extends Oven[F, T] {
  override def preheat: F[Unit] = f.point(println("preheating... done!"))

  override def cook(item: T): F[T] = {
    println(s"cooking $item...")
    Monad[F].point(item)
  }
}

class SyncOven[F[_], T](implicit f: Sync[F]) extends Oven[F, T] {
  override def preheat: F[Unit] = f.delay((println("preheating... done!")))

  override def cook(item: T): F[T] =
    f.delay(println(s"cooking $item... cooked!")) >> f.point(item)
}
