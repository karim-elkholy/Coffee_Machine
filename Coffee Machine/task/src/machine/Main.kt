package machine

class Coffee(val name: String, val water: Int, val milk: Int, val beans: Int, val cost: Int) {
    fun enoughWater(currentWater: Int) = currentWater >= this.water
    fun enoughMilk(currentMilk: Int) = currentMilk >= this.milk
    fun enoughBeans(currentBeans: Int) = currentBeans >= this.beans
}

class CoffeeMachine(var water: Int, var milk: Int, var beans: Int, var cups: Int, var money: Int) {

    /**
     * Holds a list of coffee types known by this machine
     */
    private var coffeeTypes = mutableMapOf<Int, Coffee>()

    /**
     * Registers a new coffee type.
     */
    fun addCoffee(
        name: String,
        ID: Int,
        water: Int,
        milk: Int,
        beans: Int,
        cost: Int
    ) {
        coffeeTypes[ID] = Coffee(name, water, milk, beans, cost)
    }

    /**
     * Prints stats about the coffee machine.
     */
    fun printStats() {

        val text = """
        The coffee machine has:
        $water ml of water
        $milk ml of milk
        $beans g of coffee beans
        $cups disposable cups
        $$money of money
        """.trimIndent()

        println(text)
    }

    fun buyCoffee(coffeeID: Int) {

        // Get the coffee type
        val coffeeType = coffeeTypes[coffeeID]

        if (coffeeType == null) { println("No coffee exists with ID $coffeeID") } else if (!coffeeType.enoughWater(this.water)) println("Sorry, not enough water!")
        else if (!coffeeType.enoughMilk(this.milk)) println("Sorry, not enough milk!")
        else if (!coffeeType.enoughBeans(this.beans)) println("Sorry, not enough coffee beans!")
        else {
            println("I have enough resources, making you a coffee!")
            this.water -= coffeeType.water
            this.milk -= coffeeType.milk
            this.beans -= coffeeType.beans
            this.cups -= 1
            this.money += coffeeType.cost
        }
    }

    fun fill(waterToAdd: Int, milkToAdd: Int, beansToAdd: Int, cupsToAdd: Int) {
        this.water += waterToAdd
        this.milk += milkToAdd
        this.beans += beansToAdd
        this.cups += cupsToAdd
    }

    fun take(): Int {

        // Take all the money from the machine
        val money = this.money
        this.money = 0
        return money
    }
}

fun main() {
    // Create the machine
    val coffeeMachine = CoffeeMachine(
        water = 400,
        milk = 540,
        beans = 120,
        cups = 9,
        money = 550
    )

    // Add the different types of coffee
    coffeeMachine.addCoffee("espresso", 1, water = 250, milk = 0, beans = 16, cost = 4)
    coffeeMachine.addCoffee("latte", 2, water = 350, milk = 75, beans = 20, cost = 7)
    coffeeMachine.addCoffee("cappuccino", 3, water = 200, milk = 100, beans = 12, cost = 6)

    // Start handling input
    while (true) {

        print("Write action (buy, fill, take, remaining, exit): ")
        val input = readln()
        if (input == "buy") {
            print("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ")
            val coffeeType = readln()
            if (coffeeType != "back") coffeeMachine.buyCoffee(coffeeType.toInt())
        } else if (input == "fill") {
            print("Write how many ml of water do you want to add: ")
            val waterToAdd = readln().toInt()

            print("Write how many ml of milk do you want to add: ")
            val milkToAdd = readln().toInt()

            print("Write how many grams of coffee beans do you want to add: ")
            val beansToAdd = readln().toInt()

            print("Write how many disposable cups of coffee do you want to add: ")
            val cupsToAdd = readln().toInt()

            coffeeMachine.fill(waterToAdd, milkToAdd, beansToAdd, cupsToAdd)
        } else if (input == "take") {
            println("I gave you ${coffeeMachine.take()}")
        } else if (input == "remaining") {
            coffeeMachine.printStats()
        } else if (input == "exit") {
            break
        }
    }
}
