package ie.setu.controllers
import ie.setu.models.Item
import java.util.ArrayList
import utils.formatListString

/**
 * A controller class that manages a collection of [Item] objects.
 *
 * The `ItemAPI` class provides methods to add, update, list, delete, and search items. It supports
 * operations to list all items, active items, archived items, as well as searching items by name or by their status contents.
 * It also tracks the item IDs and ensures each new item gets a unique ID.
 */

class ItemAPI() {
    private var items = ArrayList<Item>()
    private var lastItemId = 0

    /**
     * Generates the next unique ID for an item.
     *
     * This function is used to automatically assign a new ID to each item when it is added.
     *
     * @return The next available item ID.
     */

    private fun getNextId() = lastItemId++

    /**
     * Adds a new [Item] to the collection.
     *
     * The item will be assigned a unique ID and added to the `items` list. Returns true if the item
     * was successfully added, or false if the addition failed.
     *
     * @param item The [Item] to be added to the collection.
     * @return True if the item was added successfully, false otherwise.
     */

    fun add(item: Item): Boolean {
        item.itemId = getNextId()
        return items.add(item)
    }

    /**
     * Lists all items in the collection.
     *
     * This function returns a string representation of all items in the collection. If no items are present,
     * a message "No items stored" is returned.
     *
     * @return A string of all items in the collection, or a message if no items are stored.
     */

    fun listAllItems() =
        if (items.isEmpty()) "No items stored"
        else formatListString(items)

    /**
     * Lists all active (non-archived) items in the collection.
     *
     * This function returns a string representation of active items (those that are not archived).
     * If no active items are present, a message "No active items stored" is returned.
     *
     * @return A string of active items, or a message if no active items are found.
     */

    fun listActiveItems() =
        if (numberOfActiveItems() == 0) "No active items stored"
        else formatListString(items.filter { item -> !item.isItemArchived })

    /**
     * Lists all archived items in the collection.
     *
     * This function returns a string representation of archived items. If no archived items are present,
     * a message "No archived items stored" is returned.
     *
     * @return A string of archived items, or a message if no archived items are found.
     */

    fun listArchivedItems() =
        if (numberOfArchivedItems() == 0) "No archived items stored"
        else formatListString(items.filter { item -> item.isItemArchived })

    /**
     * Returns the total number of items in the collection.
     *
     * @return The total number of items.
     */

    fun numberOfItems() = items.size

    /**
     * Returns the total number of archived items.
     *
     * @return The number of archived items.
     */

    fun numberOfArchivedItems(): Int = items.count { item: Item -> item.isItemArchived }

    /**
     * Returns the total number of active (non-archived) items.
     *
     * @return The number of active items.
     */

    fun numberOfActiveItems(): Int = items.count { item: Item -> !item.isItemArchived }

    /**
     * Updates an existing item in the collection by its ID.
     *
     * The function updates the properties of an item (name, code, category) if the item is found by its ID.
     * Returns true if the item was successfully updated, or false if the item couldn't be found or the input item is null.
     *
     * @param id The ID of the item to be updated.
     * @param item The new [Item] data to update with.
     * @return True if the item was successfully updated, false otherwise.
     */

    fun update(id: Int, item: Item?): Boolean {

        val foundItem = findItem(id)
        if ((foundItem != null) && (item != null)) {
            foundItem.itemName = item.itemName
            foundItem.itemCode = item.itemCode
            foundItem.itemCategory = item.itemCategory
            return true

        }
        return false
    }

    /**
     * Finds an item by its ID.
     *
     * This function returns the item if found, or null if no item matches the provided ID.
     *
     * @param itemId The ID of the item to find.
     * @return The [Item] with the matching ID, or null if no such item exists.
     */

    fun findItem(itemId: Int) = items.find { item -> item.itemId == itemId }

    /**
     * Deletes an item by its ID.
     *
     * This function removes the item with the specified ID from the collection.
     * Returns true if the item was successfully deleted, or false if no item with the given ID was found.
     *
     * @param id The ID of the item to delete.
     * @return True if the item was deleted, false otherwise.
     */

    fun delete(id: Int) = items.removeIf { item -> item.itemId == id }

    /**
     * Archives an item by its ID.
     *
     * This function sets the `isItemArchived` property of the item with the given ID to true if the item is not already archived
     * and if its completion status allows it to be archived. Returns true if the item was successfully archived, or false otherwise.
     *
     * @param id The ID of the item to archive.
     * @return True if the item was successfully archived, false otherwise.
     */

    fun archiveItem(id: Int): Boolean {
        val foundItem = findItem(id)
        if (( foundItem != null) && (!foundItem.isItemArchived)
            && ( foundItem.checkItemCompletionStatus())) {
            foundItem.isItemArchived = true
            return true
        }
        return false
    }

    /**
     * Searches for items by name.
     *
     * This function searches for items whose name contains the provided search string (case-insensitive).
     * It returns a formatted string of items matching the search criteria.
     *
     * @param searchString The string to search for in the item names.
     * @return A formatted string of items matching the search, or a message if no items were found.
     */

    fun searchItemsByName(searchString: String) =
        formatListString(
            items.filter { item -> item.itemName.contains(searchString, ignoreCase = true) }
        )

    /**
     * Searches for items by status contents.
     *
     * This function searches the contents of each item's status for the provided search string (case-insensitive).
     * It returns a formatted string of items with matching status contents. If no such items are found, a message is returned.
     *
     * @param searchString The string to search for in the status contents.
     * @return A formatted string of items with matching status contents, or a message if no matching items are found.
     */

    fun searchStatusByContents(searchString: String): String {
        return if (numberOfItems() == 0) "No items stored"
        else {
            var listOfItems = ""
            for (item in items) {
                for (status in item.statuses) {
                    if (status.statusContents.contains(searchString, ignoreCase = true)) {
                        listOfItems += "${item.itemId}: ${item.itemName} \n\t${status}\n"
                    }
                }
            }
            if (listOfItems == "") "No status found for: $searchString"
            else listOfItems
        }
    }
    }


