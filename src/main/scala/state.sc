import cats.data.State

val nextIntOp: State[Int, Int] = State(s => (s + 1, s * 2)) // (newState, result)

def program: State[Int, Int] =
  for {
    n1 <- nextIntOp
    n2 <- nextIntOp
    n3 <- nextIntOp
  } yield n1 + n2 + n3

val initialState = 1
val (finalState, result) = program.run(initialState).value // (4, 12)


def addLog(nextLogLine: String): State[String, Int] = State(log => {
  val nextLogState = s"$log\n$nextLogLine"
  (nextLogState, nextLogState.length)
})

def loggingProgram: State[String, Int] =
  for {
    _ <- addLog("hello")
    _ <- addLog("world")
    finalLogLength <- addLog("!")
  } yield finalLogLength

val (finalLog, finalLogLength) = loggingProgram.run("").value
