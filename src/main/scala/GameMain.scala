package game

import weaponFile._
import characterFile._
import scoringFile._
import methodFile._

object GameMain extends App with Methods {

  val nameList = List("Bob", "Fred", "Jim", "Harold", "Clive", "Brian", "Vinnie", "Ted", "Gilbert", "Ed")
  val raceList = List("the Human", "the Orc", "the Dwarf", "the Elf", "the Gnome", "the Zombie",
    "the Troll", "the Skeleton", "the Vampire", "the Werewolf")
  val hpList = List(10, 12, 15, 18, 20)
  val weaponList = List(Longsword, Mace, Warhammer, Axe, Spear, Maul, Scimitar)
  val adjectives = List(" the Grotesque", " the Horrifying", " the Disgusting", " the Murderous",
    " the Relentless", " the Beastly", " the Insane", " the Bloodthirsty")
  val bosses = List("Butcher", "Giant", "Mutant", "Experiment", "Gargoyle", "Centaur", "Ghoul")
  val random = scala.util.Random

  //val userName = scala.io.StdIn.readLine("What is your name? ")
  //val userWeapon = scala.io.StdIn.readLine("What weapon would you like to use?")
  val player = new Player("Stuart", "the Human", 50, weaponList(random.nextInt(weaponList.length)))

  var healthPotions = 3
  var killCount = 0
  var bossKillCount = 0
  var continue = true

  while (continue) {
    val isItABoss = determineBossChance(killCount)
    spawnEnemy(isItABoss)

    healthPotions += receiveFreeHealthPotion(killCount, player.health, healthPotions)
    healthPotions -= promptHealingOpportunity(healthPotions)
  }
  scoring.writeToLeaderboardFile()
}

/*
object methods {
  def rollInitiative: Int = {
    val random = scala.util.Random
    val initiative = random.nextInt(19) + 1
    initiative
  }
}*/
