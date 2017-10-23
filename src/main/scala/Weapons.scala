package weaponFile

trait Weapon {
  val random = scala.util.Random
  val damageModifier: Int

  def weaponDamage: Int = {
    val normalDamage = random.nextInt(damageModifier + 1)

    if (normalDamage == damageModifier) {
      val critChance = random.nextInt(4)
      if (critChance == 1) {
        val critDamage = normalDamage * 2
        critDamage
      }
      else {
        normalDamage
      }
    }
    else {
      normalDamage
    }
  }
}

//TODO Make weapon = 12 damage if it's a boss, no matter the weapon, change to vars to allow it?
case object Longsword extends Weapon {
  val damageModifier: Int = 6
}

case object Mace extends Weapon {
  val damageModifier: Int = 8
}

case object Warhammer extends Weapon {
  val damageModifier: Int = 8
}

case object Axe extends Weapon {
  val damageModifier: Int = 8
}

case object Spear extends Weapon {
  val damageModifier: Int = 6
}

case object Maul extends Weapon {
  val damageModifier: Int = 10
}

case object Scimitar extends Weapon {
  val damageModifier: Int = 6
}

case object Lance extends Weapon {
  val damageModifier: Int = 12
}