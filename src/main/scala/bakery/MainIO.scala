package bakery

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import bakery.errors.ColdOvenError
import bakery.interpreters.ElectricOven
import bakery.models.Bread

object MainIO extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val unbakedBread = Bread.prepare
    val coldOven = ElectricOven.make[IO]

    val bakingProgram = for {
      _ <- IO(println("Preheating the electric oven... done!"))
      preheatedOven <- coldOven.preheat
      _ <- IO(println(s"Baking the $unbakedBread..."))
      bakedBread <- preheatedOven.bake(unbakedBread)
      _ <- IO(println(s"The bread grows twice in size!"))
    } yield bakedBread

    bakingProgram
      .recoverWith({
        case ColdOvenError =>
          IO(
            println("The oven was cold, your bread might not be good to eat :(")
          ) >> IO(unbakedBread)
      })
      .flatMap(bread => IO(println(s"Here's your bread: $bread")))
      .as(ExitCode.Success)
  }
}
