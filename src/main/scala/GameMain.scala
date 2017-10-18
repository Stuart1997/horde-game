package game

import java.io.{File, FileInputStream, FileOutputStream, PrintWriter}

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

    if (isItABoss == true) {
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
          killCount += 1
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

  var killScore = killCount * 10
  var hpScore = healthPotions * 5
  var score = killScore + hpScore

  val pw = new PrintWriter(new FileOutputStream(new File("HordeLeaderboard.txt"), true))
  if (score >= 100) {
    println(s"Final score = $score")
    println(s"($killCount kills = $killScore, $healthPotions health potions remaining = $hpScore)")
    println()

    var scoreForLeaderboard = s"${player.name} (${player.weapon}) = $score"
    pw.println(scoreForLeaderboard)
    scala.io.StdIn.readLine("Press enter to view the leaderboard")
    println()
  }
  else {
    println(s"Final score = $score")
    println(s"($killCount kills = $killScore, $healthPotions health potions remaining = $hpScore)")
    println("Unfortunately your score was not high enough for the leaderboard, press enter to view the leaderboard")
    scala.io.StdIn.readLine()
  }
  pw.close()


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

sealed trait Weapon {
  val random = scala.util.Random
  val damageModifier: Int

  def weaponDamage: Int = {
    val normalDamage = random.nextInt(damageModifier + 1)

    if (normalDamage == damageModifier) {
      val critChance = random.nextInt(4)
      if (critChance == 1) {
        val critDamage = normalDamage * 2
        critDamage
      }
      else {
        normalDamage
      }
    }
    else {
      normalDamage
    }
  }
}

case object Longsword extends Weapon {
  val damageModifier: Int = 8
}

case object Mace extends Weapon {
  val damageModifier: Int = 6
}

case object Warhammer extends Weapon {
  val damageModifier: Int = 8
}

case object Axe extends Weapon {
  val damageModifier: Int = 8
}

case object Spear extends Weapon {
  val damageModifier: Int = 6
}

case object Maul extends Weapon {
  val damageModifier: Int = 10
}

case object Scimitar extends Weapon {
  val damageModifier: Int = 6
}

case object Lance extends Weapon {
  val damageModifier: Int = 12
}

trait Character {
  var name: String
  var race: String
  var health: Int
  var weapon: Weapon
  val playerColour: String

  def attack(character: Character): Int = {
    val random = scala.util.Random
    val damage = weapon.weaponDamage

    println(s"Attacking ${character.name} the ${character.race} ${character.playerColour}(${character.health} health)${Console.RESET} with $weapon")
    var enemyHealthPoints = character.health - damage
    if (damage == 0) {
      println(s"${Console.BLUE}Miss!${Console.RESET}")
    }
    else if (damage > 10) {
      println(s"${Console.YELLOW}CRIT!${Console.RESET} $damage damage, ${character.name} now has ${character.playerColour}($enemyHealthPoints health)${Console.RESET}")
    }
    else {
      println(s"${Console.YELLOW}Hit!${Console.RESET} $damage damage, ${character.name} now has ${character.playerColour}($enemyHealthPoints health)${Console.RESET}")
    }
    damage
  }
}

class Player(var name: String, var race: String, var health: Int, var weapon: Weapon) extends Character {
  override val playerColour: String = Console.GREEN

  def heal(playerHealth: Int, healthPot: Int): Int = {
    var healthPotion = healthPot

    var playerHealth = 50
    healthPotion = healthPotion - 1

    println(s"You are now back to $playerColour($playerHealth health)${Console.RESET} and have $playerColour($healthPotion health potions)${Console.RESET} left")

    scala.io.StdIn.readLine()
    playerHealth
  }
}

class Enemy(var name: String, var race: String, var health: Int, var weapon: Weapon) extends Character {
  override val playerColour: String = Console.RED
}

object methods {
  def rollInitiative: Int = {
    val random = scala.util.Random
    val initiative = random.nextInt(19) + 1
    initiative
  }
}