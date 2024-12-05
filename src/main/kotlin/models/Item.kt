package ie.setu.models

data class Item (var itemId: Int = 0,
                var itemName: String,
                var itemCode: Int,
                var itemCategory: String,
                var isItemArchived: Boolean = false,
                var values : MutableSet<Value> = mutableSetOf()) {
    fun checkItemCompletionStatus(): Boolean {
        if (values.isNotEmpty()) {
            for (value in values) {
                if (!value.isValueComplete) {
                    return false
                }
            }
        }
        return true //a note with empty items can be archived, or all items are complete
    }
}




