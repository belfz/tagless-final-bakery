package bakery

import bakery.errors.ColdOvenError
import bakery.interpreters.ElectricOven
import bakery.models.Bread
import zio._
import zio.console._
import zio.interop.catz._

object MainZIO extends CatsApp {

  override def run(args: List[String]): ZIO[zio.ZEnv, Nothing, Int] = {
    val unbakedBread = Bread.prepare
    val coldOven = ElectricOven.make[Task]

    val bakingProgram = for {
      _ <- putStrLn("Preheating the electric oven... done!")
      preheatedOven <- coldOven.preheat
      _ <- putStrLn(s"Baking the $unbakedBread...")
      bakedBread <- preheatedOven.bake(unbakedBread)
      _ <- putStrLn(s"The bread grows twice in size!")
    } yield bakedBread

    bakingProgram
      .catchAll({
        case ColdOvenError =>
          for {
            _ <- putStrLn(
              "The oven was cold, your bread might not be good to eat :("
            )
          } yield unbakedBread
      })
      .flatMap(bread => putStrLn(s"Here's your bread: $bread"))
      .map(_ => 0)
  }
}
