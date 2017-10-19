import GameFixtures.GameFixtures
import game.GameMain
import org.scalatest._
import weaponFile._

class GameSpec extends FlatSpec with Matchers {
  "When it is the player's turn, the enemy " should " be attacked" in {
    val attackDamage = GameFixtures.playerAttackingEnemy(enemyName = "Bob", enemyRace = "Orc", enemyHealth = 20)
    attackDamage should be >= 0
  }

  "When it is the enemy's turn, the player " should " be attacked" in {
    val attackDamage = GameFixtures.enemyAttackingPlayer(playerName = "Stuart", playerHealth = 100)
    attackDamage should be >= 0
  }

  "When the player is prompted to heal and says Yes they " should " be healed back to full health" in {

    val healing = GameFixtures.heal(playerHealth = 25, healthPot = 3)
    healing should be (50)
  }

  "When the player has killed 5 enemies they " should " receive a health potion" in {
    val currentHealthPots = GameFixtures.receiveFreeHealthPotion(5, 3)

    currentHealthPots should be (4)
  }

  "When the player has killed 10+ enemies and the determineBossChance method rolls a 1, a boss " should "spawn" in {
    val killCount = 10

    val enemyObject = GameFixtures.determineBossChance(killCount)
    //GameFixtures.combatScenario(enemyObject)

    enemyObject should be ("Bob the Grotesque", "Mutant", 30, Lance)
  }

}
