package ie.setu.models
import utils.formatSetString

/**
 * Data class representing an [Item] with an associated list of [Status]es.
 *
 * The [Item] class holds information about a specific item, such as its ID, name, code, category,
 * archived status, and the statuses associated with the item. It also provides functionality for adding,
 * updating, deleting statuses, and checking the completion status of the item based on its statuses.
 *
 * @property itemId The unique identifier for the item.
 * @property itemName The name of the item.
 * @property itemCode The code associated with the item.
 * @property itemCategory The category of the item.
 * @property isItemArchived Indicates whether the item is archived.
 * @property statuses A mutable set of [Status] objects associated with the item.
 */

data class Item(
    var itemId: Int = 0,
    var itemName: String,
    var itemCode: Int,
    var itemCategory: String,
    var itemPrice: String,
    var isItemArchived: Boolean = false,
    var statuses: MutableSet<Status> = mutableSetOf()) {

    private var lastStatusId = 0

    /**
     * Generates the next unique status ID for the item.
     *
     * This function is used to automatically assign a new ID to each status when it is added.
     *
     * @return The next available status ID.
     */

    private fun getStatusId() = lastStatusId++

    /**
     * Checks if all statuses associated with the item are complete.
     *
     * This function iterates through the item’s statuses and returns false if any status is not complete.
     * If all statuses are complete, or if there are no statuses, the function returns true, allowing the item
     * to be archived.
     *
     * @return True if all statuses are complete or there are no statuses, false otherwise.
     */

    fun checkItemCompletionStatus(): Boolean {
        if (statuses.isNotEmpty()) {
            for (status in statuses) {
                if (!status.isStatusComplete) {
                    return false
                }
            }
        }
        return true //a note with empty items can be archived, or all items are complete
    }

    /**
     * Adds a new status to the item.
     *
     * This function adds a [Status] to the item’s set of statuses and assigns a unique status ID to it.
     *
     * @param status The [Status] to be added.
     * @return True if the status was added successfully, false otherwise.
     */

    fun addStatus(status: Status) : Boolean {
        status.statusId = getStatusId()
        return statuses.add(status)
    }

    /**
     * Returns the total number of statuses associated with the item.
     *
     * @return The number of statuses.
     */

    fun numberOfStatuses() = statuses.size

    /**
     * Lists all statuses associated with the item.
     *
     * This function returns a string representation of all statuses of the item. If no statuses are present,
     * it returns a message indicating that no statuses have been added.
     *
     * @return A string representation of the statuses, or a message if no statuses are added.
     */

    fun listStatuses() =
        if (statuses.isEmpty())  "\tNO ITEMS ADDED"
        else  formatSetString(statuses)

    /**
     * Finds a specific status by its ID.
     *
     * This function searches for a [Status] object in the item’s statuses set based on its ID.
     *
     * @param id The ID of the status to be found.
     * @return The [Status] object with the matching ID, or null if not found.
     */

    fun findOne(id: Int): Status?{
        return statuses.find{ status -> status.statusId == id }
    }

    /**
     * Updates an existing status by its ID.
     *
     * This function updates the properties of a [Status] in the item’s statuses set if the status ID matches.
     * It updates the status contents and completion status based on the provided [newStatus] object.
     *
     * @param id The ID of the status to update.
     * @param newStatus The new [Status] object containing updated details.
     * @return True if the status was successfully updated, false if no matching status was found.
     */

    fun update(id: Int, newStatus : Status): Boolean {
        val foundStatus = findOne(id)


        if (foundStatus != null){
            foundStatus.statusContents = newStatus.statusContents
            foundStatus.isStatusComplete = newStatus.isStatusComplete
            return true
        }


        return false
    }

    /**
     * Deletes a status by its ID.
     *
     * This function removes the status with the given ID from the item’s statuses set.
     *
     * @param id The ID of the status to be deleted.
     * @return True if the status was successfully removed, false if no matching status was found.
     */

    fun delete(id: Int): Boolean {
        return statuses.removeIf { status -> status.statusId == id}
    }
}








