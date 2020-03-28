package bakery

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import bakery.errors.ColdOvenError
import bakery.interpreters.ElectricOven
import bakery.models.Bread

object TaglessFinalKitchen extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val uncookedBread = Bread.prepare
    val oven = ElectricOven.make[IO]
    (oven.preheat >>
      oven
        .cook(uncookedBread))
      .recoverWith({
        case ColdOvenError =>
          IO.delay(
            println("The oven was cold, your bread might not be good to eat :(")
          ) >> IO.delay(uncookedBread)
      })
      .flatMap(bread => IO.delay(println(s"Here's your bread: $bread")))
      .as(ExitCode.Success)
  }
}
