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
            3 -> updateItem()
            4 -> deleteItem()
            5 -> archiveItem()
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
         > |   1) Add an item                                  |
         > |   2) List items                                   |
         > |   3) Update an item                               |
         > |   4) Delete an item                                |
         > |   5) Archive an item                              |
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

fun updateItem() {
    listItems()
    if (itemAPI.numberOfItems() > 0) {
        val id = readNextInt("Enter the id of the item you wish to update: ")
        if (itemAPI.findItem(id) != null) {
            val itemName = readNextLine("Enter the item's name: ")
            val itemCode = readNextInt("Enter the item's Identification Number: ")
            val itemCategory = readNextLine("Enter a category for the item: ")

            if (itemAPI.update(id, Item(0, itemName, itemCode, itemCategory, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteItem() {
    listItems()
    if (itemAPI.numberOfItems() > 0) {
        val id = readNextInt("Enter the id of the note to delete: ")
        val itemToDelete = itemAPI.delete(id)
        if (itemToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveItem() {
    listActiveItems()
    if (itemAPI.numberOfActiveItems() > 0){
        val id = readNextInt("Enter the id of the note to archive: ")
        if (itemAPI.archiveItem(id)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}