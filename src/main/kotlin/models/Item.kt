package ie.setu.models

data class Item (var itemId: Int = 0,
                var itemName: String,
                var itemCode: Int,
                var itemCategory: String,
                var isItemArchived: Boolean = false,
                var values : MutableSet<Value> = mutableSetOf())





