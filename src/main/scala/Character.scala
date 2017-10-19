package characterFile

import weaponFile.Weapon

trait Character {
  var name: String
  var race: String
  var health: Int
  var weapon: Weapon
  val playerColour: String

  def attack(character: Character): Int = {
    val random = scala.util.Random
    val damage = weapon.weaponDamage

    println(s"Attacking ${character.name} ${character.race} ${character.playerColour}(${character.health} health)${Console.RESET} with $weapon")
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