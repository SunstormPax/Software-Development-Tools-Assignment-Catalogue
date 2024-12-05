package ie.setu
import utils.readNextInt
import utils.readNextLine
import ie.setu.controllers.ItemAPI
import ie.setu.models.Item
import ie.setu.models.Value
import kotlin.system.exitProcess

private val itemAPI = ItemAPI()

fun main() = runCatalogue()

fun runCatalogue() {
    do {
        when (val option = Catalogue()) {
            1 -> addItem()
            2 -> listItems()
            else -> println("Invalid item choice: $option")
        }
    } while (true)
}

fun Catalogue() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  Cataloguing App                  |
         > -----------------------------------------------------  
         > | Catalogue MENU                                    |
         > |   1) Add a note                                   |
         > |   2) List notes                                   |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

fun addItem() {
    val itemName = readNextLine("Enter a title for the item: ")
    val itemCode = readNextInt("Enter an identification number for the item: ")
    val itemCategory = readNextLine("Enter a category for the item: ")
    val isAdded = itemAPI.add(Item(itemName = itemName, itemCode = itemCode, itemCategory = itemCategory))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}



fun listItems() {
    if (itemAPI.numberOfItems() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL items          |
                  > |   2) View ACTIVE items       |
                  > |   3) View ARCHIVED items     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllItems()
            2 -> listActiveItems()
            3 -> listArchivedItems()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllItems() = println(itemAPI.listAllItems())
fun listActiveItems() = println(itemAPI.listActiveItems())
fun listArchivedItems() = println(itemAPI.listArchivedItems())