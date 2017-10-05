package game

object GameMain extends App {

  val nameList = List("Bob", "Fred", "Jim", "Harold", "Clive", "Brian")
  val raceList = List("Human", "Orc", "Dwarf", "Elf", "Gnome", "Zombie")
  val hpList = List(10, 12, 15, 18, 20)
  val weaponList = List("Sword", "Mace", "Warhammer", "Axe", "Spear", "Maul", "Knife")
  val random = scala.util.Random

  val player = new Player("Stuart", "Human", 50, "Mace")

  var playerHealth = player.health

  //scala.io.StdIn.readLine("You come across two enemies, press enter to roll initiative")
  //var playerInitiative = methods.rollInitiative
  //var enemyInitiative = methods.rollInitiative
  var healthPotions = 3
  var killCount = 0
  var continue = true
  while (continue)
    {
      val enemy1 = new Enemy(nameList(random.nextInt(6)), raceList(random.nextInt(6)),
        hpList(random.nextInt(5)), weaponList(random.nextInt(7)))
      var enemyHealth = enemy1.health

      if (killCount % 5 == 0 && killCount != 0)
      {
        healthPotions += 1
        println(s"For killing 5 enemies without dying you have been awarded an extra health potion, you now have ${Console.GREEN}($healthPotions)${Console.RESET}")
        scala.io.StdIn.readLine()
      }

      println("AN ENEMY APPEARS")
      scala.io.StdIn.readLine()

      while (playerHealth > 0 && enemyHealth > 0)
      {
        enemyHealth = enemyHealth - player.attack(enemy1.name, enemy1.race, enemyHealth)
        scala.io.StdIn.readLine()

        if (enemyHealth <= 0)
        {
          enemyHealth = 0
          println(s"${Console.RED}${enemy1.name} is dead!${Console.RESET}")
          killCount+= 1
          scala.io.StdIn.readLine()
        }
        else
        {
          playerHealth = playerHealth - enemy1.attack(player.name, playerHealth)
          if (playerHealth <= 0)
          {
            playerHealth = 0
            println()
            println(s"${Console.RED}${player.name} is dead!${Console.RESET}")
            continue = false
          }
          scala.io.StdIn.readLine()
        }
      }

      if (playerHealth > 0 && healthPotions > 0)
        {
          var decision = scala.io.StdIn.readLine(s"Would you like to heal? ${Console.GREEN}($playerHealth health) ($healthPotions health potions)${Console.RESET} ").capitalize
          if (decision == "Yes")
          {
            playerHealth = player.heal(playerHealth, healthPotions)
            healthPotions -= 1
          }
          else if (decision == "No")
          {
            println(s"You remain at (${Console.GREEN}$playerHealth health${Console.RESET})")
            scala.io.StdIn.readLine()
          }
          else
          {
            println("Invalid response, you have missed your healing opportunity")
            scala.io.StdIn.readLine()
          }

        }
    }

  var killScore = killCount * 10
  var hpScore = healthPotions * 5
  var score = killScore + hpScore
  println(s"Final score = $score")
  println(s"($killCount kills = $killScore, $healthPotions health potions remaining = $hpScore)")

}

trait Character {
  var name:String
  var race:String
  var health:Int
  var weapon:String
}

class Player(var name:String, var race:String, var health:Int, var weapon:String) extends Character
{
  def attack(enemyName:String, enemyRace:String, enemyHealth:Int):Int = {
    val random = scala.util.Random
    val damage = random.nextInt(10)

    println(s"Attacking $enemyName the $enemyRace ${Console.RED}($enemyHealth health)${Console.RESET} with $weapon")
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

    scala.io.StdIn.readLine()
    playerHealth
  }
}

class Enemy(var name:String, var race:String, var health:Int, var weapon:String) extends Character
{
  def attack(playerName:String, playerHealth:Int):Int = {
    val random = scala.util.Random
    val damage = random.nextInt(10)

    println(s"You ${Console.GREEN}($playerHealth health)${Console.RESET} are being attacked with $weapon")
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
}

object methods {
  def rollInitiative:Int = {
    val random = scala.util.Random
    val initiative = random.nextInt(19)+1
    initiative
  }
}