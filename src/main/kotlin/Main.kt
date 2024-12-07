package ie.setu
import utils.readNextInt
import utils.readNextLine
import ie.setu.controllers.ItemAPI
import ie.setu.models.Item
import ie.setu.models.Status
import kotlin.system.exitProcess

private val itemAPI = ItemAPI()

/**
 * Main entry point of the cataloguing application.
 * Starts the cataloguing system by calling [runCatalogue].
 */

fun main() = runCatalogue()

/**
 * Displays the main catalogue menu and processes user input.
 * Based on the user input, different actions are taken (e.g. adding, updating, deleting items).
 */

fun runCatalogue() {
    do {
        when (val option = catalogue()) {
            1 -> addItem()  // Add an item
            2 -> listItems()  // List all items
            3 -> updateItem() // Update an item
            4 -> deleteItem()  // Delete an item
            5 -> archiveItem()  // Archive an item
            6 -> addStatusToItem()  // Add status to an item
            7 -> updateItemStatus()  // Update item status
            8 -> deleteItemStatus()  // Delete item status
            9 -> searchItems()  // Search for an item by name
            10 -> searchStatuses()  // Search for an item by status
            11 -> giveHelp()  // Display help information
            0 -> exitApp()  // Exit the application

            else -> println("Invalid option entered. Please enter a valid option between 0 and 11")
        }
    } while (true)
}

/**
 * Displays the main catalogue options and returns the user's choice as an integer.
 *
 * @return the chosen option as an integer.
 */

fun catalogue() = readNextInt(
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
         > |   11) Give Help navigating the catalogue          |
         > ----------------------------------------------------- 
         > |   0) Exit the Catalogue                           |
         > ----------------------------------------------------- 
         > ==>> """.trimMargin(">")
)

/**
 * Adds a new item to the catalogue.
 * The item name, code, and category are input by the user.
 */

fun addItem() {
    val itemName = readNextLine("Enter a title for the item: ")
    val itemCode = readNextInt("Enter an identification number for the item: ")
    val itemCategory = readNextLine("Enter a category for the item: ")
    val itemPrice = readNextLine("Enter the item's price: ")
    val isAdded = itemAPI.add(Item(itemName = itemName, itemCode = itemCode, itemCategory = itemCategory, itemPrice = itemPrice))


    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
/**
 * Lists all items in the catalogue. The user can choose between different categories of items.
 */


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
            else -> println("Invalid option entered. Please enter a valid option between 1 and 3")
        }
    } else {
        println("No items are available. Please add an item first")
    }
}

/**
 * Displays all items in the catalogue.
 */

fun listAllItems() = println(itemAPI.listAllItems())

/**
 * Displays all active items in the catalogue.
 */

fun listActiveItems() = println(itemAPI.listActiveItems())

/**
 * Displays all archived items in the catalogue.
 */

fun listArchivedItems() = println(itemAPI.listArchivedItems())

/**
 * Updates the details of an existing item.
 * Prompts the user for the item ID and new details.
 */

fun updateItem() {
    listItems()
    if (itemAPI.numberOfItems() > 0) {
        val id = readNextInt("Enter the id of the item you wish to update: ")
        if (itemAPI.findItem(id) != null) {
            val itemName = readNextLine("Enter the item's name: ")
            val itemCode = readNextInt("Enter the item's Identification Number: ")
            val itemCategory = readNextLine("Enter a category for the item: ")
            val itemPrice = readNextLine("Enter the item's price: ")

            if (itemAPI.update(id, Item(0, itemName, itemCode, itemCategory, itemPrice, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

/**
 * Deletes an item from the catalogue.
 * Prompts the user for the item ID and deletes the item.
 */

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

/**
 * Archives an active item, removing it from the active catalogue but retaining its record.
 */

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

/**
 * Adds a status to an item.
 * Prompts the user to select an active item and enter a status.
 */

private fun addStatusToItem() {
    val item: Item? = askUserToChooseActiveItem()
    if (item != null) {
        if (item.addStatus(Status(statusContents = readNextLine("\t Status Contents: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

/**
 * Prompts the user to select an active item.
 *
 * @return the selected active item or null if no valid item is selected.
 */

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

/**
 * Updates the status of an item.
 * Prompts the user to select an active item and a status to update.
 */

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

/**
 * Prompts the user to choose a status from a list of statuses for an item.
 *
 * @param item the item to which the status is applied.
 * @return the selected status or null if no valid status is chosen.
 */

private fun askUserToChooseStatus(item: Item): Status? {
    if (item.numberOfStatuses() > 0) {
        print(item.listStatuses())
        return item.findOne(readNextInt("\nEnter the status: "))
    }
    else{
        println ("No status chosen")
        return null
    }

    /**
     * Exits the catalogue application.
     */

}

fun exitApp() {
    println("Exiting")
    exitProcess(0)
}

/**
 * Deletes a status from an item.
 * Prompts the user to select an active item and a status to delete.
 */

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

/**
 * Searches for items in the catalogue by their name.
 * Prompts the user for a description of the item to search by and displays the search results.
 * If no items are found, a message is displayed indicating that no matching items were found.
 */

fun searchItems() {
    val searchName = readNextLine("Enter a description of the item to search by: ")
    val searchResults = itemAPI.searchItemsByName(searchName)
    if (searchResults.isEmpty()) {
        println("No items found matching: $searchName")
    } else {
        println(searchResults)
    }
}

/**
 * Searches for statuses in the catalogue by their content.
 * Prompts the user for a status description and displays the search results.
 * If no statuses are found, a message is displayed indicating that no matching statuses were found.
 */

fun searchStatuses() {
    val searchContents = readNextLine("Enter the status you wish to search: ")
    val searchResults = itemAPI.searchStatusByContents(searchContents)
    if (searchResults.isEmpty()) {
        println("Status not found")
    } else {
        println(searchResults)
    }
}

/**
 * Displays help information to the user.
 * Provides a list of actions the user can perform in the catalogue application, along with their corresponding numeric options.
 */

fun giveHelp() {

    println("""
        > -----------------------------------------------------  
        > |                      Help                         |
        > -----------------------------------------------------  
        > |   Press the number key that matches the action    |
        > |              you wish to perform                  |
        > |   1) Add a new item to the catalogue              |
        > |   2) List the items currently listed within       |
        > |      the catalogue                                |
        > |   3) Update the details of an item currently      |
        > |      in the catalogue                             |
        > |   4) Delete an item currently in the catalogue    |
        > |   5) Archive an item in the catalogue. This will  |
        > |      remove the item but leave a record of it.    |
        > |   6) Add a status to an item, e.g. Available,     |
        > |      Incoming, Expired                            |
        > |   7) Change the status of an item that has        |
        > |      already had a status given to it             |
        > |   8) Remove a status applied to an item without   |
        > |      deleting the item itself                     |
        > |   9) Search for a specific item currently in      |
        > |      the catalogue by name                        |
        > |   10) Search for a specific status within the     |
        > |       catalogue. All items with this status       |
        > |       will be displayed                           |
        > |   0) Turn the catalogue off                       |
        > -----------------------------------------------------
    """.trimMargin(">"))
}
