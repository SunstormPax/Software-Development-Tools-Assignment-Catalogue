package utils

/**
 * Reads the next integer input from the user.
 *
 * This function prompts the user for input using the specified prompt string, and repeatedly requests input
 * until a valid integer is entered. If the input cannot be parsed as an integer, an error message is displayed
 * and the user is prompted again.
 *
 * @param prompt The message to be displayed to the user before reading input. This is optional and can be `null`.
 * @return The integer value entered by the user.
 */

fun readNextInt(prompt: String?): Int {
    do {
        try {
            print(prompt)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a number please.")
        }
    } while (true)
}

/**
 * Reads the next line of input from the user.
 *
 * This function prompts the user for input using the specified prompt string and returns the input as a
 * `String`. It does not validate the input, so the returned string may contain any text, including empty
 * lines.
 *
 * @param prompt The message to be displayed to the user before reading input. This is optional and can be `null`.
 * @return The string entered by the user.
 */

fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}