package utils

import ie.setu.models.Item
import ie.setu.models.Value

fun formatListString(itemsToFormat: List<Item>): String =
    itemsToFormat
        .joinToString(separator = "\n") { item -> "$item" }

fun formatSetString(valuesToFormat: Set<Value>): String =
    valuesToFormat
        .joinToString(separator = "\n") { value -> "\t$value" }