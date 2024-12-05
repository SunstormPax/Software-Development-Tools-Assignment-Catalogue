package ie.setu.controllers
import ie.setu.models.Item
import java.util.ArrayList
import utils.formatListString


class ItemAPI() {
    private var items = ArrayList<Item>()
    private var lastItemId = 0
    private fun getNextId() = lastItemId++

    fun add(item: Item): Boolean {
        item.itemId = getNextId()
        return items.add(item)
    }


    fun listAllItems() =
        if (items.isEmpty()) "No items stored"
        else formatListString(items)

    fun listActiveItems() =
        if (numberOfActiveItems() == 0) "No active items stored"
        else formatListString(items.filter { item -> !item.isItemArchived })

    fun listArchivedItems() =
        if (numberOfArchivedItems() == 0) "No archived items stored"
        else formatListString(items.filter { item -> item.isItemArchived })



    fun numberOfItems() = items.size
    fun numberOfArchivedItems(): Int = items.count { item: Item -> item.isItemArchived }
    fun numberOfActiveItems(): Int = items.count { item: Item -> !item.isItemArchived }
}


