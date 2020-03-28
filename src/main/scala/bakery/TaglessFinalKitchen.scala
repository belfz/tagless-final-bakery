package bakery

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import bakery.errors.ColdOvenError
import bakery.interpreters.ElectricOven
import bakery.models.Bread

object TaglessFinalKitchen extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val uncookedBread = Bread.prepare
    val coldOven = ElectricOven.make[IO]

    val bakingProgram = for {
      _ <- IO(println("Preheating the electric oven... done!"))
      preheatedOven <- coldOven.preheat
      _ <- IO(println(s"Baking the $uncookedBread..."))
      cookedBread <- preheatedOven.bake(uncookedBread)
      _ <- IO(println(s"The bread grows twice in size!"))
    } yield cookedBread

    bakingProgram
      .recoverWith({
        case ColdOvenError =>
          IO(
            println("The oven was cold, your bread might not be good to eat :(")
          ) >> IO(uncookedBread)
      })
      .flatMap(bread => IO(println(s"Here's your bread: $bread")))
      .as(ExitCode.Success)
  }
}
