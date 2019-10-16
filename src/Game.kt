
fun main(args: Array<String>) {
    /*val name = "Madrigal"
    var healthPoints = 100
    val isBlessed = true
    val isImmortal = false*/

    //instantiating a Player instance
    val player  = Player("Madrigal", 89, true, false)
    player.castFireball()

    //var currentRoom = Room("Foyer")
    var currentRoom: Room = TownSquare()
    println(currentRoom.description())
    println(currentRoom.load())

    //Aura
    //val (auraVisible, auraColor) = auraColor(isBlessed, healthPoints, isImmortal)
    //println(auraColor)
    val auraColor = player.auraColor()
    printPlayerStatus(player)

    //val healthStatus = formatHealthStatus(healthPoints, isBlessed)

    //printPlayerStatus(auraColor, isBlessed, player.name, healthStatus)
    castFireball()
    println(getInebriationStatus(0))

    //println("(HP: $healthPoints) (Aura: ${if(auraVisible) "GREEN" else "NONE"}) -> $healthStatus")
}

private fun printPlayerStatus(
    player: Player) {
    println("(Aura: ${player.auraColor()}) " + "(Blessed: ${if (player.isBlessed) "YES" else "NO"})")
    println("${player.name.toString()} ${player.formatHealthStatus()}")
}

/*private fun auraColor(
    isBlessed: Boolean,
    healthPoints: Int,
    isImmortal: Boolean
): Pair<Boolean, String> {
    val auraVisible = isBlessed && healthPoints > 50 || isImmortal
    val auraColor = if (auraVisible) "GREEN" else "NONE"
    return Pair(auraVisible, auraColor)
}

private fun formatHealthStatus(healthPoints: Int, isBlessed: Boolean) =  when (healthPoints) {
        100 -> "is in excellent condition!"
        in 90..99 -> "has a few scratches."
        in 75..89 -> if (isBlessed) {
            " has some minor wounds but is healing quite quickly!"
        } else {
            " has some minor wounds."
        }
        in 15..74 -> " looks pretty hurt."
        else -> "is in awful condition!"
    }*/

private fun castFireball(numFireballs: Int=2): Int {

    println("A glass of Fireball springs into existence. (x$numFireballs)")
    return numFireballs
}

private fun getInebriationStatus(inebriationValue: Int=0) = when (inebriationValue) {
    0 -> "Normal"
    in 1..10 -> "tipsy"
    in 11..20 -> "sloshed"
    in 21..30 -> "soused"
    in 31..40 -> "stewed"
    in 41..50 -> "..t0aSt3d"
    else -> "Normal"
}

