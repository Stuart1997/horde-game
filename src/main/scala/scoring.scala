package scoringFile

import java.io._

import game.GameMain._

import scala.io.BufferedSource

object scoring {
  val bossKillScore = bossKillCount * 50
  val killScore = killCount * 10
  val hpScore = healthPotions * 5
  val score = killScore + hpScore + bossKillScore

  def writeToLeaderboardFile(): Unit = {
    val pw = new PrintWriter(new FileOutputStream(new File("HordeLeaderboard.txt"), true))
    println(s"Final score = $score")
    println(s"($killCount total kills = $killScore, $bossKillCount boss kills = $bossKillScore, $healthPotions health potions remaining = $hpScore)")
    println()

    if (score >= 100) {
      val scoreForLeaderboard = s"${player.name} (${player.weapon}) = $score"
      pw.println(scoreForLeaderboard)
      scala.io.StdIn.readLine("Press enter to view the leaderboard \n")
    }
    else {
      println("Unfortunately your score was not high enough for the leaderboard, press enter to view the leaderboard")
      scala.io.StdIn.readLine()
    }
    displayLeaderboard()
    pw.close()
  }

  private def displayLeaderboard(): Unit = {
    println("LEADERBOARD")
    //Read all
    val source = new BufferedSource(new FileInputStream("HordeLeaderboard.txt"))
    //Finds number at the end of line
    val regex = "([0-9]+)$".r
    //Go through line by line, construct tuple of the string and then the number at the end of the string
    val leaderboardScore = source.getLines().map { x => (x, regex.findAllIn(x).toList.head.toInt) }.toList
    //Sort the tuple based on the score, descending, print (_: goes through all tuples which are strings/ints
    //_2 looks only at ints, reverse as it is low - high by default
    //Each tuple has been assigned a value, and then scores are ordered by tuple value by printing the string (._1)
    leaderboardScore.sorted(Ordering.by((_: (String, Int))._2).reverse).foreach(x => println(x._1))
  }


}


