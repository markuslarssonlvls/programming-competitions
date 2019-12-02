package skeleton

import java.security.acl.Group

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
    val (groups, amountOfCandy) = readInts()
    val sizeOfGroups = readInts()
    var index = 0

    var bestPapers = Int.MAX_VALUE
    for(i in 0 until 9) {
        var currentCandy = amountOfCandy - i
        var toiletPapers = 0;
        sizeOfGroups.forEach {
            if (it <= currentCandy) {
                currentCandy -= it
            } else {
                toiletPapers++
            }
        }
        bestPapers = Math.min(bestPapers, toiletPapers)
    }
    println(bestPapers)

}

private fun giveCandy(toiletPaper: Int, candyLeft: Int, groupIndex: Int, group: List<Group>) {


}

private fun printOutput() {

}

