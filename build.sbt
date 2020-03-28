name := "tagless-final-bakery"

version := "0.1"

ThisBuild / scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  compilerPlugin(
    "org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full
  ),
  compilerPlugin("org.augustjune" %% "context-applied" % "0.1.2"),
  "org.typelevel" %% "cats-core" % "2.1.0",
  "org.typelevel" %% "cats-effect" % "2.1.0",
  "io.estatico" %% "newtype" % "0.4.3",
)

scalacOptions += "-Ymacro-annotations"
