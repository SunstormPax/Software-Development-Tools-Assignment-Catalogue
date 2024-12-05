package ie.setu.models
import utils.formatSetString

data class Item (var itemId: Int = 0,
                var itemName: String,
                var itemCode: Int,
                var itemCategory: String,
                var isItemArchived: Boolean = false,
                var statuss : MutableSet<Status> = mutableSetOf()) {

    private var lastStatusId = 0
    private fun getStatusId() = lastStatusId++

    fun checkItemCompletionStatus(): Boolean {
        if (statuss.isNotEmpty()) {
            for (status in statuss) {
                if (!status.isStatusComplete) {
                    return false
                }
            }
        }
        return true //a note with empty items can be archived, or all items are complete
    }

    fun addStatus(status: Status) : Boolean {
        status.statusId = getStatusId()
        return statuss.add(status)
    }

    fun numberOfStatuss() = statuss.size

    fun listStatuss() =
        if (statuss.isEmpty())  "\tNO ITEMS ADDED"
        else  formatSetString(statuss)

    fun findOne(id: Int): Status?{
        return statuss.find{ status -> status.statusId == id }
    }

    fun update(id: Int, newStatus : Status): Boolean {
        val foundStatus = findOne(id)

        //if the object exists, use the details passed in the newItem parameter to
        //update the found object in the Set
        if (foundStatus != null){
            foundStatus.statusContents = newStatus.statusContents
            foundStatus.isStatusComplete = newStatus.isStatusComplete
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }
}








