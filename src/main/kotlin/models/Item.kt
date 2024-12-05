package ie.setu.models

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
}




