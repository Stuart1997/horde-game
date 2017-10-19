package GameFixtures
import game._
import weaponFile._
import characterFile._
import game.GameMain._

object GameFixtures {
  val player = new Player("Stuart", "Human", 100, Mace)
  val enemy = new Enemy("Bob", "Orc", 20, Warhammer)

  var healthPotions = 3

  def playerAttackingEnemy(enemyName:String, enemyRace:String, enemyHealth:Int):Int = {
    val random = scala.util.Random
    val damage = random.nextInt(10)

    println(s"Attacking $enemyName the $enemyRace (${Console.RED}$enemyHealth health${Console.RESET}) with ${player.weapon}")
    var enemyHealthPoints = enemyHealth - damage
    if (damage == 0)
    {
      println(s"${Console.BLUE}Miss!${Console.RESET}")
    }
    else
    {
      println(s"${Console.YELLOW}Hit!${Console.RESET} $damage damage, $enemyName now has ${Console.RED}($enemyHealthPoints health)${Console.RESET}")
    }
    damage
  }

  def heal(playerHealth:Int, healthPot:Int):Int = {
    var healthPotion = healthPot

    var playerHealth = 50
    healthPotion = healthPotion - 1

    println(s"You are now back to ${Console.GREEN}($playerHealth health)${Console.RESET} and have ${Console.GREEN}($healthPotion health potions)${Console.RESET} left")

    playerHealth
  }

  def receiveFreeHealthPotion(noOfKills:Int, healthPotions:Int):Int = {
    var healthPots = healthPotions
    if (noOfKills % 5 == 0 && noOfKills != 0)
      {
        println(s"For killing 5 enemies without dying you have been awarded an extra health potion, you now have ${Console.GREEN}($healthPots)${Console.RESET}")
        healthPots + 1
      }
    else
      {
        0
      }
  }


  def enemyAttackingPlayer(playerName:String, playerHealth:Int):Int = {
    val random = scala.util.Random
    val damage = random.nextInt(10)

    println(s"Attacking $playerName (${Console.GREEN}$playerHealth health${Console.RESET}) with ${enemy.weapon}")
    var playerHealthPoints = playerHealth - damage
    if (damage == 0)
    {
      println(s"${Console.BLUE}Miss!${Console.RESET}")
    }
    else
    {
      println(s"${Console.YELLOW}Hit!${Console.RESET} $damage damage, $playerName now has ${Console.GREEN}($playerHealthPoints health)${Console.RESET}")
    }
    damage
  }







  def determineBossChance(killCount:Int): Unit = {
    var isItABoss = false

    if (killCount >= 10) {
      val bossChance = random.nextInt(1)

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
      combatScenario("Bob the Grotesque", "Mutant", 30, Lance)
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






}
