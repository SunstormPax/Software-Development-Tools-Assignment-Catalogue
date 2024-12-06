package ie.setu
import utils.readNextInt
import utils.readNextLine
import ie.setu.controllers.ItemAPI
import ie.setu.models.Item
import ie.setu.models.Status
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
            6 -> addStatusToItem()
            7 -> updateItemStatus()
            8 -> deleteItemStatus()
            9 -> searchItems()
            10 -> searchStatuss()
            0 -> exitApp()

            else -> println("Invalid item choice: $option")
        }
    } while (true)
}

fun Catalogue() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  Cataloguing App                  |
         > -----------------------------------------------------  
         > | Item Catalogue                                    |
         > |   1) Add an item                                  |
         > |   2) List items                                   |
         > |   3) Update an item                               |
         > |   4) Delete an item                               |
         > |   5) Archive an item                              |
         > |   6) Add status to an item                        |
         > |   7) Update the status of an item                 |
         > |   8) Remove a status applied to an item           |
         > |   9) Search for an item                           |
         > |   10) Search for a status                         |
         > ----------------------------------------------------- 
         > |   0) Exit the Catalogue                           |
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

private fun addStatusToItem() {
    val item: Item? = askUserToChooseActiveItem()
    if (item != null) {
        if (item.addStatus(Status(statusContents = readNextLine("\t Status Contents: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

private fun askUserToChooseActiveItem(): Item? {
    listActiveItems()
    if (itemAPI.numberOfActiveItems() > 0) {
        val item = itemAPI.findItem(readNextInt("\nEnter the id of the item: "))
        if (item != null) {
            if (item.isItemArchived) {
                println("Item is NOT Active, it is Archived")
            } else {
                return item
            }
        } else {
            println("Item id is not valid")
        }
    }
    return null
}

fun updateItemStatus() {
    val item: Item? = askUserToChooseActiveItem()
    if (item != null) {
        val status: Status? = askUserToChooseStatus(item)
        if (status != null) {
            val newContents = readNextLine("Enter new contents: ")
            if (item.update(status.statusId, Status(statusContents = newContents))) {
                println("Status updated")
            } else {
                println("Status NOT updated")
            }
        } else {
            println("Invalid Status")
        }
    }
}

private fun askUserToChooseStatus(item: Item): Status? {
    if (item.numberOfStatuss() > 0) {
        print(item.listStatuss())
        return item.findOne(readNextInt("\nEnter the status: "))
    }
    else{
        println ("No status chosen")
        return null
    }


}

fun exitApp() {
    println("Exiting")
    exitProcess(0)
}


fun deleteItemStatus() {
    val item: Item? = askUserToChooseActiveItem()
    if (item != null) {
        val status: Status? = askUserToChooseStatus(item)
        if (status != null) {
            val isDeleted = item.delete(status.statusId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

fun searchItems() {
    val searchName = readNextLine("Enter a description of the item to search by: ")
    val searchResults = itemAPI.searchItemsByName(searchName)
    if (searchResults.isEmpty()) {
        println("No results found")
    } else {
        println(searchResults)
    }
}

fun searchStatuss() {
    val searchContents = readNextLine("Enter the status you wish to search: ")
    val searchResults = itemAPI.searchStatusByContents(searchContents)
    if (searchResults.isEmpty()) {
        println("Status not found")
    } else {
        println(searchResults)
    }
}
