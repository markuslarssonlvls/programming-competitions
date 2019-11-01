package skeleton

private fun readLn() = readLine()!! // string line
private fun readInt() = readLn().toInt() // single int
private fun readLong() = readLn().toLong() // single long
private fun readFloat() = readLn().toFloat() // single float

// Read line split by space
private fun readStrings() = readLn().split(" ") // list of strings
private fun readInts() = readStrings().map { it.toInt() } // list of ints
private fun readLongs() = readStrings().map { it.toLong() } // list of longs
private fun readFloats() = readStrings().map { it.toFloat() } // list of floats

// val (n, k) = readInts() - destructuring declaration
// println(a.joinToString("\n"))

fun main() {

    printOutput()
}

private fun printOutput() {

}
