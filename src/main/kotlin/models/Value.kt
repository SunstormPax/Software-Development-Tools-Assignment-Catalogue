package ie.setu.models

data class Status (var statusId: Int = 0, var statusContents : String, var isStatusComplete: Boolean = false){

    override fun toString(): String {
        if (isStatusComplete)
            return "$statusId: $statusContents (Complete)"
        else
            return "$statusId: $statusContents (TODO)"
    }

}