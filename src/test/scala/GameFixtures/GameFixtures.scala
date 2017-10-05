package GameFixtures
import game._

object GameFixtures {
  val player = new Player("Stuart", "Human", 100, "Mace")
  val enemy = new Enemy("Bob", "Orc", 20, "Warhammer")

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

  def yesHeal(playerHealth:Int, healthPot:Int):Int = {
    var healthPotion = healthPot

    var playerHealth = 50
    healthPotion = healthPotion - 1

    println(s"You are now back to ${Console.GREEN}($playerHealth health)${Console.RESET} and have ${Console.GREEN}($healthPotion health potions)${Console.RESET} left")

    playerHealth
  }

  def noHeal(playerHealth:Int):Int = {
    println(s"You still have ${Console.GREEN}($playerHealth health)${Console.RESET}")

    playerHealth
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
}
