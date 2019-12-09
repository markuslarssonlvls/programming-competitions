package adventofcode.problem8

fun main() {
    a()
    b()
}

var bestOnes = 0
var bestTwos = 0
var bestZeros = Int.MAX_VALUE

fun a() {
    val wide = 25
    val height = 6
    var curZeros = 0
    var curOnes = 0
    var curTwos = 0
    var count = 0
    for (i in 0 until input8.length) {
        count++
        when (input8.get(i).toString()) {
            "0" -> {
                curZeros++
            }
            "1" -> {
                curOnes++
            }
            "2" -> {
                curTwos++
            }
        }

        if (count == wide * height) {
            count = 0
            if (bestZeros > curZeros) {
                bestZeros = curZeros
                bestOnes = curOnes
                bestTwos = curTwos
            }
            curZeros = 0
            curOnes = 0
            curTwos = 0
        }
    }
    println(bestTwos * bestOnes)
}

var finalImage = ""

fun b() {
    val wide = 25
    val height = 6
    var count = 0
    for (i in 0 until input8.length) {
        if (finalImage.length <= count) {
            finalImage += input8[i].toString()
        } else {
            if (finalImage[count].toString() == "2") {
                finalImage = finalImage.replaceRange(count, count + 1, input8[i].toString())
            }
        }

        count++
        if (count == wide * height) {
            count = 0
        }
    }
    println(finalImage)

    for (i in 0 until finalImage.length) {
        if (i % wide == 0) {
            println()
        }

        if (finalImage[i].toString() == "1") {
            print("x")
        } else {
            print(" ")
        }
    }
}
