package methodFile

import game.GameMain._
import characterFile._
import weaponFile._

trait Methods {
  def determineBossChance(killCount:Int): Unit = {
    var isItABoss = false

    if (killCount >= 10) {
      val bossChance = random.nextInt(9)

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
  }

  def combatScenario(Name: String, Race: String, HP: Int, Weapon: Weapon): Unit = {
    val enemy = new Enemy(Name, Race, HP, Weapon)

    if (enemy.health > 20) {
      println(s"${Console.RED}A BOSS STANDS BEFORE YOU - ${enemy.name} ${enemy.race} (${enemy.health} health)${Console.RESET}")
    }
    else {
      println(s"AN ENEMY APPEARS - ${enemy.name} ${enemy.race} (${enemy.health} health) equipped with: " +
        s"${enemy.weapon}")
    }

    scala.io.StdIn.readLine()

    while (player.health > 0 && enemy.health > 0) {
      enemy.health -= player.attack(character = enemy)
      scala.io.StdIn.readLine()

      if (enemy.health <= 0) {
        enemy.health = 0

        if (enemy.weapon == Lance) {
          bossKillCount += 1
          killCount += 1
        }
        else {
          killCount += 1
        }
        println(s"${Console.RED}${enemy.name} ${enemy.race} is dead! You now have $killCount kills.${Console.RESET}")
        scala.io.StdIn.readLine()
      }
      else {
        player.health -= enemy.attack(character = player)
        if (player.health <= 0) {
          player.health = 0
          println()
          println(s"${Console.RED}Oh dear, you are dead!${Console.RESET}")
          continue = false
        }
        scala.io.StdIn.readLine()
      }
    }
  }

  def receiveFreeHealthPotion(killCount:Int, playerHealth:Int, healthPotions:Int):Int = {
    if (killCount % 5 == 0 && killCount != 0 && playerHealth > 0) {
      val healthPots = healthPotions + 1
      println(s"For killing 5 enemies without dying you have been awarded an extra health potion, you now have ${Console.GREEN}($healthPots)${Console.RESET}")
      scala.io.StdIn.readLine()
      1
    }
    else {
      0
    }
  }

  def promptHealingOpportunity(healthPotions:Int):Int = {
    if (player.health > 0 && healthPotions > 0) {
      val decision = scala.io.StdIn.readLine(s"Would you like to heal? ${Console.GREEN}(${player.health} health) ($healthPotions health potions)${Console.RESET} ").capitalize
      if (decision == "Yes" || decision.startsWith("Y")) {
        player.health = player.heal(player.health, healthPotions)
        1
      }
      else if (decision == "No") {
        println(s"You remain at (${Console.GREEN}${player.health} health${Console.RESET})")
        scala.io.StdIn.readLine()
        0
      }
      else {
        println("Invalid response, you have missed your healing opportunity")
        scala.io.StdIn.readLine()
        0
      }
    }
    else {
      0
    }
  }
}
