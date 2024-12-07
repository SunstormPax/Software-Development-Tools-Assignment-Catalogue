package ie.setu.models

/**
 * Data class representing the status of an item.
 *
 * The [Status] class holds information about a specific status associated with an item, including its ID,
 * contents, and completion state. It is used to track the progress or state of an item within a collection.
 *
 * @property statusId The unique identifier for the status.
 * @property statusContents The content or description of the status.
 * @property isStatusComplete Indicates whether the status is complete or not.
 */

data class Status (var statusId: Int = 0, var statusContents : String, var isStatusComplete: Boolean = false){

    override fun toString(): String {

        /**
         * Returns a string representation of the status.
         *
         * This function generates a human-readable string for the status, including its ID, contents, and
         * whether it is complete or still a "TODO". If the status is complete, it appends "(Complete)" to the string;
         * otherwise, it appends "(TODO)".
         *
         * @return A string representation of the status in the format: "statusId: statusContents (Complete/TODO)".
         */

        if (isStatusComplete)
            return "$statusId: $statusContents (Complete)"
        else
            return "$statusId: $statusContents (TODO)"
    }

}