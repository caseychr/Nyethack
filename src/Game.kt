import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    /*val name = "Madrigal"
    var healthPoints = 100
    val isBlessed = true
    val isImmortal = false*/

    //instantiating a Player instance
    //val player  = Player("Madrigal", 89, true, false)
    //player.castFireball()

    /*var currentRoom = Room("Foyer")
    var currentRoom: Room = TownSquare()
    println(currentRoom.description())
    println(currentRoom.load())*/

    //Aura
    //val (auraVisible, auraColor) = auraColor(isBlessed, healthPoints, isImmortal)
    //println(auraColor)
    //val auraColor = player.auraColor()
    //printPlayerStatus(player)

    //val healthStatus = formatHealthStatus(healthPoints, isBlessed)

    //printPlayerStatus(auraColor, isBlessed, player.name, healthStatus)
    //castFireball()
    //println(getInebriationStatus(0))

    //println("(HP: $healthPoints) (Aura: ${if(auraVisible) "GREEN" else "NONE"}) -> $healthStatus")
    Game.play()
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

object Game {
    private val player  = Player("Madrigal")
    private var currentRoom: Room = TownSquare()

    private var worldMap = listOf(
        listOf(currentRoom, Room("Tavern"), Room("Back Room")),
        listOf(Room("Long Corridor"), Room("Generic Room")))

    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.toUpperCase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if(!newPosition.isInBounds) {
                throw IllegalStateException("$direction is out of bounds.")
            }
            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            "OK, you move $direction to the ${newRoom.name}. \n${newRoom.load()}"
        } catch (e: Exception) {
            "Invalid direction: $directionInput."
        }

    private fun fight() = currentRoom.monster?.let {
        while (player.healthPoints > 0 && it.healthPoints > 0) {
            slay(it)
            Thread.sleep(1000)
        }
        "Combat complete."
    } ?: "There's nothing here to fight"

    private fun slay(monster: Monster) {
        println("${monster.name} did ${monster.attack(player)} damage!")
        println("${player.name} did ${player.attack(monster)} damage!")

        if(player.healthPoints <= 0) {
            println(">>>> You have been defeated! Thanks for playing. <<<<")
            exitProcess(0)
        }

        if(monster.healthPoints <= 0) {
            println(">>>> ${monster.name} have been defeated! <<<<")
            currentRoom.monster = null
        }
    }

    init {
        println("Welcome, adventurer")
        player.castFireball()
    }

    fun play() {
        while(true) {
            // Play NyetHack
            println(currentRoom.description())
            println(currentRoom.load())

            // Player status
            printPlayerStatus(player)

            print("> Enter your command: ")
            //println("Last command: ${readLine()}")
            println(GameInput(readLine()).processCommand())
        }
    }

    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.auraColor()})"+"(Blessed: ${if(player.isBlessed) "YES" else "NO"})")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(args: String?) {
        private val input = args ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })

        fun processCommand() = when(command.toLowerCase()) {
            "fight" -> fight()
            "move" -> move(argument)
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"
    }
}

