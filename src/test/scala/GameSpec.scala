import GameFixtures.GameFixtures
import game.GameMain
import org.scalatest._

class GameSpec extends FlatSpec with Matchers {
  "When it is the player's turn, the enemy " should " be attacked" in {
    val attack = GameFixtures.playerAttackingEnemy(enemyName = "Bob", enemyRace = "Orc", enemyHealth = 20)
    attack should be >= 0
  }

  "When it is the enemy's turn, the player " should " be attacked" in {
    val attack = GameFixtures.enemyAttackingPlayer(playerName = "Stuart", playerHealth = 100)
    attack should be >= 0
  }

  "When the player is prompted to heal and says Yes, they " should " be healed back to full health" in {
    val healing = GameFixtures.yesHeal(playerHealth = 25, healthPot = 3)
    healing should be (50)
  }

  "When the player is prompted to heal and says No, they " should " remain at the same health" in {
    val healing = GameFixtures.noHeal(playerHealth = 45)
    healing should be (45)
  }

}
