package GameFixtures
import game._
import weaponFile._
import characterFile._
import game.GameMain._

object GameFixtures {
  val player = new Player("Stuart", "the Human", 50, Mace)
  val enemy = new Enemy("Bob", "the Orc", 20, Warhammer)
  val random = scala.util.Random

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

  def promptHealingOpportunity(playerHealth:Int, healthPotions: Int): Boolean = {
    var wouldYouLikeToHeal = false  //TODO VAR
    if (player.health > 0 && healthPotions > 0) {
      println(s"Would you like to heal? ${Console.GREEN}(${player.health} health) ($healthPotions health potions)${Console.RESET}")
      val decision = "Yes"
      if (decision.startsWith("Y")) {
        //player.health = player.heal(player.health, healthPotions)
        wouldYouLikeToHeal = true
        wouldYouLikeToHeal
      }
      else if (decision == "No") {
        println(s"You remain at (${Console.GREEN}${player.health} health${Console.RESET})")
        wouldYouLikeToHeal
      }
      else {
        println("Invalid response, you have missed your healing opportunity")
        wouldYouLikeToHeal
      }
    }
    else {
      wouldYouLikeToHeal
    }
  }

  def healPlayer(wouldYouLikeToHeal:Boolean, playerHealth:Int, healthPotions:Int):Int = {
    /*if (wouldYouLikeToHeal) {
      //TODO change the values without making them
      playerHealth = 50
      healthPotions -= 1
      println(s"You are now back to ${Console.GREEN}($playerHealth health)${Console.RESET} and have ${Console.GREEN}($healthPotions health potions)${Console.RESET} left")
    }
    else {
      println(s"You remain at (${Console.GREEN}$playerHealth health${Console.RESET})")
    }*/

    var healthPotion = healthPotions

    val playerHealth = 50
    healthPotion = healthPotion - 1

    println(s"You are now back to ($playerHealth health) and have ($healthPotion health potions) left")

    playerHealth
  }


  /*def receiveFreeHealthPotion(killCount:Int, playerHealth:Int, healthPotions:Int):Int = {
    if (killCount % 5 == 0 && killCount != 0 && playerHealth > 0) {
      val healthPots = healthPotions + 1
      println(s"For killing 5 enemies without dying you have been awarded an extra health potion, you now have ${Console.GREEN}($healthPots)${Console.RESET}")
      scala.io.StdIn.readLine()
      1
    }
    else {
      0
    }
  }*/

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

  def determineRoom(killCount: Int): String = {
    if (killCount == 0 || killCount % 5 == 0) {
      val roomType = List("ice", "water", "fire", "air")
      val room = roomType(random.nextInt(4))
      if (room.startsWith("a") || room.startsWith("e") || room.startsWith("i") || room.startsWith("o") || room.startsWith("u")){
        //s"You enter: an $room room"
        "You enter: an ice room"
      }
      else {
        //s"You enter: a $room room"
        "You enter: a fire room"
      }
    }
    else {
      val roomText = "You have not yet cleared the current room"
      roomText
    }
  }

  def roomModifier(roomType: String): Unit = {
    if (roomType == "fire"){
      println("In this room your damage is slightly increased")
      player.weapon.damageModifier += 2
    }
    else if (roomType == "water"){
      println("In this room your damage is slightly decreased")
      player.weapon.damageModifier -= 2
    }
      //TODO change what these two do
    else if (roomType == "air"){
      println("In this room your chance to hit has slightly increased")
    }
    else {
      println("In this room your chance to hit has slightly decreased")
    }
  }

}
