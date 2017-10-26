import GameFixtures.GameFixtures
import game.GameMain._
import org.scalatest._
import weaponFile._
import Character._

import characterFile.Player

class GameSpec extends FlatSpec with Matchers {
  val player = new Player("Stuart", " the human", 30, weaponFile.Maul)

  "When it is the player's turn, the enemy " should " be attacked" in {
    val attackDamage = GameFixtures.playerAttackingEnemy(enemyName = "Bob", enemyRace = "Orc", enemyHealth = 20)
    attackDamage should be >= 0
  }

  "When it is the enemy's turn, the player " should " be attacked" in {
    val attackDamage = GameFixtures.enemyAttackingPlayer(player.name, player.health)
    attackDamage should be >= 0
  }

  "When the player has killed an enemy & has at least one health potion they " should " be given the chance to heal" in {
    player.health = 30
    var healthPotions = 3

    val healingOpportunity = GameFixtures.promptHealingOpportunity(player.health, healthPotions)
    healingOpportunity should be (true)
    }

  "When the player has killed an enemy but has no health potions they " should " not be given the chance to heal" in {
    player.health = 30
    var healthPotions = 0

    val healingOpportunity = GameFixtures.promptHealingOpportunity(player.health, healthPotions)
    healingOpportunity should be (false)
  }

  /*"When the player agrees to healing themselves they " should " return to 50 health and have one less health potion" in {
    player.health = 30
    val healthPotions = 3
    val healingOpportunity = GameFixtures.promptHealingOpportunity(player.health, healthPotions)

    GameFixtures.healPlayer(healingOpportunity, player.health, healthPotions)
    player.health should be (50)
    healthPotions should be (2)
  }*/

  "When the player does not agree to healing themselves they " should " return remain at the same health and their health potion count stays the same" in {
    player.health = 30
    val healthPotions = 3
    val healingOpportunity = GameFixtures.promptHealingOpportunity(player.health, healthPotions)

    GameFixtures.healPlayer(healingOpportunity, player.health, healthPotions)
    player.health should be (30)
    healthPotions should be (3)
  }



  /*"When the player has killed 5 enemies they " should " receive a health potion" in {
    var currentHealthPots = 3
      currentHealthPots += GameFixtures.receiveFreeHealthPotion(5, 30, 3)

    currentHealthPots should be (4)
  }*/

  "At the start of the game a room type" should " be determined" in {
    val killCount = 0
    val roomType = GameFixtures.determineRoom(killCount)
    roomType shouldBe "You enter: an ice room"
  }

  "When the player's killcount is not 0 or a multiple of 5, a room" should " not be determined" in {
    val killCount = 1
    val roomType = GameFixtures.determineRoom(killCount)
    roomType shouldBe "You have not yet cleared the current room"
  }

}
