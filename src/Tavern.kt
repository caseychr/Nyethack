import java.io.File
import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.math.roundToInt

const val TAVERN_NAME = "Taernyl's Folly"

var playerGold = 10
var playerSilver = 10
val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableListOf<String>()
val menuList = File("data/tavern-menu-items.txt").readText().split("\n")
//val patronGold = mapOf("Eli" to 10.5, "Mordoc" to 8.0, "Sophie" to 5.5)
val patronGold = mutableMapOf<String, Double>()

fun main(args: Array<String>) {
    //placeOrder("shandy,Dragon's Breath,5.91")
    //placeOrder("elixir,Shirley's Temple,4.12")

    /*for(patron in patronList) {
        println("Good evening, $patron")
    }
    patronList.forEach {patron ->
        println("Good evening, $patron")
    }*/

    /*patronList.forEachIndexed {index, patron ->
        println("Good evening, $patron - you're #${index+1} in line")
        //placeOrder(patron, "shandy,Dragon's Breath,5.91")
        placeOrder(patron, menuList.shuffled().first())
    }*/

    menuList.forEachIndexed {index, data ->
        println("$index : $data")
    }

    println("*** Welcome to $TAVERN_NAME ***")
    println()
    for(menuItem in menuList) {
        var (_, name, price) = menuItem.split(",")
        name = name.substring(0,1).toUpperCase()+name.substring(1,name.length)
        name.forEachIndexed { index, char ->
            if(index == 0) {
                name = name.substring(0,1).toUpperCase()+name.substring(1, name.length)
            }
            if(char.equals(' ')) {
                name = name.substring(0, index+1)+name.substring(index+1, index+2).toUpperCase()+name.substring(index+2, name.length)
            }
        }
        var item = name+price
        var dots = "."
        while(item.length+dots.length < 34) {
            dots += "."
        }
        item =name+dots+price
        println(item)
    }

    (0..9).forEach {
        val first = patronList.shuffled().first()
        val last = lastName.shuffled().first()
        val name = "$first $last"
        //println(name)
        uniquePatrons += name
    }
    println(uniquePatrons)

    uniquePatrons.forEach {
        patronGold[it] = 6.0
    }
    println(patronGold)

    displayPatronBalances()
}

fun formatMenu(menuItem: String) {
    var (_, name, price) = menuItem.split(",")
    name = name.substring(0,1).toUpperCase()+name.substring(1,name.length)
    name.forEachIndexed { index, char ->
        if(index == 0) {
            name = name.substring(0,1).toUpperCase()+name.substring(1, name.length)
        }
        if(char.equals(' ')) {
            name = name.substring(0, index+1)+name.substring(index+1, index+2).toUpperCase()+name.substring(index+2, name.length)
        }
    }
    var item = name+price
    var dots = "."
    while(item.length+dots.length < 34) {
        dots += "."
    }
    item =name+dots+price
    println(item)
}

/*fun performPurchase(price: Double) {
    displayBalance()
    val totalPurse = playerGold + (playerSilver/100.0)
    println("Total purse: $totalPurse")
    println("Purchasing item for $price")

    val remainingBalance = totalPurse - price
    println("Remaining balance: ${"%.2f".format(remainingBalance)}")

    val remainingGold = remainingBalance.toInt()
    val remainingSilver = (remainingBalance % 1 * 100).roundToInt()
    playerGold = remainingGold
    playerSilver = remainingSilver
    displayBalance()
}*/

fun performPurchase(price: Double, patronName: String) {
    val totalPurse = patronGold.getValue(patronName)
    patronGold[patronName] = totalPurse - price
}

private fun displayPatronBalances() {
    patronGold.forEach {patron, balance ->
        println("$patron, balance: ${"%.2f".format(balance)}")
    }
}

private fun displayBalance() {
    println("Player's purse balance: Gold: $playerGold, Silver: $playerSilver")
}

private fun toDragonSpeak(phrase: String)=
    phrase.replace(Regex("[aeiouAEIOU]")) {
        when (it.value) {
            "a" -> "4"
            "e" -> "3"
            "i" -> "1"
            "o" -> "0"
            "u" -> "|_|"
            "A" -> "4"
            "E" -> "3"
            "I" -> "1"
            "O" -> "0"
            "U" -> "|_|"
            else -> it.value
        }
    }


private fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    //println("Madrigal speaks with $tavernMaster about their order.")
    println("$patronName speaks with $tavernMaster about their order.")
    val (type, name, price) = menuData.split(',')
    //val message = "Madrigal buys a $name ($type) for $price"
    val message = "$patronName buys a $name ($type) for $price"
    println(message)
    //val phrase = "Ah, delicious $name!"
    //println("Madrigal exclaims: ${toDragonSpeak(phrase)}")

    performPurchase(price.toDouble(), patronName)

    val phrase = if(name == "Dragon's Breath") {
        //"Madrigal exclaims ${toDragonSpeak("Ah, delicious $name!")}"
        "$patronName exclaims ${toDragonSpeak("Ah, delicious $name!")}"
    } else {
        //"Madrigal says: Thanks for the $name."
        "$patronName says: Thanks for the $name."
    }
    println(phrase)

    println("UPPER CASE")
    println("${name.toUpperCase()}")
    val phraseUpperCase = if(name.toUpperCase() == "DRAGON'S BREATH") {
        "MADRIGAL EXCLAIMS ${toDragonSpeak("AH, DELICIOUS ${name.toUpperCase()}!")}"
    } else {
        "MADRIGAL SAYS: THANKS FOR THE $name."
    }
    println(phraseUpperCase)
}