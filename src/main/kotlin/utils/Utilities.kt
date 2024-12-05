package utils

import ie.setu.models.Item
import ie.setu.models.Status

fun formatListString(itemsToFormat: List<Item>): String =
    itemsToFormat
        .joinToString(separator = "\n") { item -> "$item" }

fun formatSetString(valuesToFormat: Set<Status>): String =
    valuesToFormat
        .joinToString(separator = "\n") { status -> "\t$status" }