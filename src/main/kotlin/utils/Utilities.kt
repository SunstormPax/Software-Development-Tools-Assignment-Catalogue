package utils

import ie.setu.models.Item
import ie.setu.models.Status

/**
 * Formats a list of [Item] objects into a string representation.
 *
 * This function takes a list of [Item] objects and converts it into a string, with each item in the list
 * separated by a newline (`\n`). The string representation of each [Item] is obtained by calling its `toString` method.
 *
 * @param itemsToFormat The list of [Item] objects to be formatted.
 * @return A string where each [Item] in the list is represented as a line.
 */

fun formatListString(itemsToFormat: List<Item>): String =
    itemsToFormat
        .joinToString(separator = "\n") { item -> "$item" }

/**
 * Formats a set of [Status] objects into a string representation.
 *
 * This function takes a set of [Status] objects and converts it into a string, with each status in the set
 * separated by a newline (`\n`). Each status is indented with a tab character (`\t`) for readability.
 *
 * @param valuesToFormat The set of [Status] objects to be formatted.
 * @return A string where each [Status] in the set is represented as a line, indented with a tab.
 */

fun formatSetString(valuesToFormat: Set<Status>): String =
    valuesToFormat
        .joinToString(separator = "\n") { status -> "\t$status" }