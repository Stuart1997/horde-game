package game

import java.io.{File, FileInputStream, FileOutputStream, PrintWriter}

import weaponFile._
import characterFile._
import scoringFile._

import scala.io.{BufferedSource, Source}

object GameMain extends App {

  val nameList = List("Bob", "Fred", "Jim", "Harold", "Clive", "Brian", "Vinnie", "Ted", "Gilbert", "Ed")
  val raceList = List("Human", "Orc", "Dwarf", "Elf", "Gnome", "Zombie", "Troll", "Skeleton", "Vampire", "Werewolf")
  val hpList = List(10, 12, 15, 18, 20)
  val weaponList = List(Longsword, Mace, Warhammer, Axe, Spear, Maul, Scimitar)
  val adjectives = List(" the Grotesque", " the Horrifying", " the Disgusting", " the Murderous",
    " the Relentless", " the Beastly", " the Insane", " the Bloodthirsty")
  val bosses = List("Butcher", "Giant", "Mutant", "Experiment", "Gargoyle", "Centaur", "Ghoul")
  val random = scala.util.Random

  //val userName = scala.io.StdIn.readLine("What is your name? ")
  //val userWeapon = scala.io.StdIn.readLine("What weapon would you like to use?")
  val player = new Player("Stuart", "Human", 50, weaponList(random.nextInt(7)))

  var healthPotions = 3
  var killCount = 0
  var bossKillCount = 0
  var continue = true
  while (continue) {
    var isItABoss = false

    if (killCount >= 10) {
      val bossChance = random.nextInt(8)

      if (bossChance == 1) {
        isItABoss = true
      }
      else {
        isItABoss = false
      }
    }
    else {
      isItABoss = false
    }

    if (isItABoss) {
      val bossName = nameList(random.nextInt(10)) + adjectives(random.nextInt(8))
      val bossRace = bosses(random.nextInt(7))
      combatScenario(bossName, bossRace, 30, Lance)
    }
    else {
      combatScenario(nameList(random.nextInt(10)), raceList(random.nextInt(10)),
        hpList(random.nextInt(5)), weaponList(random.nextInt(7)))
    }

    if (killCount % 5 == 0 && killCount != 0 && player.health > 0) {
      healthPotions += 1
      println(s"For killing 5 enemies without dying you have been awarded an extra health potion, you now have ${Console.GREEN}($healthPotions)${Console.RESET}")
      scala.io.StdIn.readLine()
    }

    if (player.health > 0 && healthPotions > 0) {
      var decision = scala.io.StdIn.readLine(s"Would you like to heal? ${Console.GREEN}(${player.health} health) ($healthPotions health potions)${Console.RESET} ").capitalize
      if (decision == "Yes" || decision.startsWith("Y")) {
        player.health = player.heal(player.health, healthPotions)
        healthPotions -= 1
      }
      else if (decision == "No") {
        println(s"You remain at (${Console.GREEN}${player.health} health${Console.RESET})")
        scala.io.StdIn.readLine()
      }
      else {
        println("Invalid response, you have missed your healing opportunity")
        scala.io.StdIn.readLine()
      }
    }

    def combatScenario(Name: String, Race: String, HP: Int, Weapon: Weapon): Unit = {
      val enemy = new Enemy(Name, Race, HP, Weapon)

      if (enemy.health > 20) {
        println(s"${Console.RED}A BOSS STANDS BEFORE YOU - ${enemy.name} ${enemy.race} (${enemy.health} health)${Console.RESET}")
      }
      else {
        println(s"AN ENEMY APPEARS - ${enemy.name} the ${enemy.race} (${enemy.health} health) equipped with: " +
          s"${enemy.weapon}")
      }

      scala.io.StdIn.readLine()

      while (player.health > 0 && enemy.health > 0) {
        enemy.health -= player.attack(character = enemy)
        scala.io.StdIn.readLine()

        if (enemy.health <= 0) {
          enemy.health = 0
          println(s"${Console.RED}${enemy.name} is dead!${Console.RESET}")
          if (enemy.weapon == Lance) {
              bossKillCount += 1
            }
          else {
            killCount += 1
          }
          scala.io.StdIn.readLine()
        }
        else {
          player.health -= enemy.attack(character = player)
          if (player.health <= 0) {
            player.health = 0
            println()
            println(s"${Console.RED}${player.name} is dead!${Console.RESET}")
            continue = false
          }
          scala.io.StdIn.readLine()
        }
      }
    }
  }

  scoring.writeToLeaderboardFile()

}






object methods {
  def rollInitiative: Int = {
    val random = scala.util.Random
    val initiative = random.nextInt(19) + 1
    initiative
  }
}