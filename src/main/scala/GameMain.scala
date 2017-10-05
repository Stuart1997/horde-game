package game

object GameMain extends App {

  val nameList = List("Bob", "Fred", "Jim", "Harold", "Clive", "Brian")
  val raceList = List("Human", "Orc", "Dwarf", "Elf", "Gnome", "Zombie")
  val hpList = List(10, 12, 15, 18, 20)
  val weaponList = List(Longsword, Mace, Warhammer, Axe, Spear, Maul, Dagger)
  val random = scala.util.Random

  val player = new Player("Stuart", "Human", 50, Mace)

  //scala.io.StdIn.readLine("You come across two enemies, press enter to roll initiative")
  //var playerInitiative = methods.rollInitiative
  //var enemyInitiative = methods.rollInitiative
  var healthPotions = 3
  var killCount = 0
  var continue = true
  while (continue)
    {
      val enemy = new Enemy(nameList(random.nextInt(6)), raceList(random.nextInt(6)),
        hpList(random.nextInt(5)), weaponList(random.nextInt(7)))

      if (killCount % 5 == 0 && killCount != 0)
      {
        healthPotions += 1
        println(s"For killing 5 enemies without dying you have been awarded an extra health potion, you now have ${Console.GREEN}($healthPotions)${Console.RESET}")
        scala.io.StdIn.readLine()
      }

      println("AN ENEMY APPEARS")
      scala.io.StdIn.readLine()

      while (player.health > 0 && enemy.health > 0)
      {
        enemy.health -= player.attack(character = enemy)
        scala.io.StdIn.readLine()

        if (enemy.health <= 0)
        {
          enemy.health = 0
          println(s"${Console.RED}${enemy.name} is dead!${Console.RESET}")
          killCount+= 1
          scala.io.StdIn.readLine()
        }
        else
        {
          player.health -= enemy.attack(character = player)
          if (player.health <= 0)
          {
            player.health = 0
            println()
            println(s"${Console.RED}${player.name} is dead!${Console.RESET}")
            continue = false
          }
          scala.io.StdIn.readLine()
        }
      }


      if (player.health > 0 && healthPotions > 0)
        {
          var decision = scala.io.StdIn.readLine(s"Would you like to heal? ${Console.GREEN}(${player.health} health) ($healthPotions health potions)${Console.RESET} ").capitalize
          if (decision == "Yes")
          {
            player.health = player.heal(player.health, healthPotions)
            healthPotions -= 1
          }
          else if (decision == "No")
          {
            println(s"You remain at (${Console.GREEN}${player.health} health${Console.RESET})")
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

sealed trait Weapon
{
  val random = scala.util.Random
  val damageModifier:Int
  def weaponDamage:Int =
  {
    random.nextInt(damageModifier + 1)
  }
}
case object Longsword extends Weapon
{
  val damageModifier:Int = 8
}
  case object Mace extends Weapon
{
  val damageModifier:Int = 6
}
  case object Warhammer extends Weapon
{
  val damageModifier:Int = 8
}
  case object Axe extends Weapon
{
  val damageModifier:Int = 8
}
  case object Spear extends Weapon
{
  val damageModifier:Int = 6
}
  case object Maul extends Weapon
{
  val damageModifier:Int = 10
}
  case object Dagger extends Weapon
{
  val damageModifier:Int = 4
}

  trait Character {
    var name:String
    var race:String
    var health:Int
    var weapon:Weapon
    val playerColour:String

    def attack(character:Character):Int = {
      val random = scala.util.Random
      val damage = weapon.weaponDamage

      println(s"Attacking ${character.name} the ${character.race} ${character.playerColour}(${character.health} health)${Console.RESET} with $weapon")
      var enemyHealthPoints = character.health - damage
      if (damage == 0)
      {
        println(s"${Console.BLUE}Miss!${Console.RESET}")
      }
      else
      {
        println(s"${Console.YELLOW}Hit!${Console.RESET} $damage damage, ${character.name} now has ${character.playerColour}($enemyHealthPoints health)${Console.RESET}")
      }
      damage
    }

  }

  class Player(var name:String, var race:String, var health:Int, var weapon:Weapon) extends Character
  {
    override val playerColour: String = Console.GREEN
  def heal(playerHealth:Int, healthPot:Int):Int = {
    var healthPotion = healthPot

    var playerHealth = 50
    healthPotion = healthPotion - 1

    println(s"You are now back to ${playerColour}($playerHealth health)${Console.RESET} and have ${playerColour}($healthPotion health potions)${Console.RESET} left")

    scala.io.StdIn.readLine()
    playerHealth
  }
}

class Enemy(var name:String, var race:String, var health:Int, var weapon:Weapon) extends Character
{
  override val playerColour: String = Console.RED
}

object methods {
  def rollInitiative:Int = {
    val random = scala.util.Random
    val initiative = random.nextInt(19)+1
    initiative
  }
}