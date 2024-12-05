package ie.setu.models

data class Value (var valueId: Int = 0, var valueContents : String, var isValueComplete: Boolean = false){

    override fun toString(): String {
        if (isValueComplete)
            return "$valueId: $valueContents (Complete)"
        else
            return "$valueId: $valueContents (TODO)"
    }

}