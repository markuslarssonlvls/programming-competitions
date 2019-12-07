package adventofcode.problem3

import kotlin.math.abs
import kotlin.math.min

val points: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()
var steps = 0
var steps2 = 0
fun main() {
    val input = input3.split("\n")
    val first = input[0].split(",")
    val second = input[1].split(",")

    var posX = 0
    var posY = 0

    first.forEach {
        val value = it.substring(1, it.length).toInt()
        when (it.substring(0, 1)) {
            "R" -> {
                for (i in 0 until (value)) {
                    posX++
                    addPoint(posX, posY)
                }
            }
            "U" -> {
                for (i in 0 until (value)) {
                    posY++
                    addPoint(posX, posY)
                }
            }
            "D" -> {
                for (i in 0 until (value)) {
                    posY--
                    addPoint(posX, posY)
                }
            }
            "L" -> {
                for (i in 0 until (value)) {
                    posX--
                    addPoint(posX, posY)
                }
            }
        }
    }
    posX = 0
    posY = 0
    second.forEach {
        val value = it.substring(1, it.length).toInt()
        when (it.substring(0, 1)) {
            "R" -> {
                for (i in 0 until (value)) {
                    posX++
                    checkMax(posX, posY)
                    checkMinSteps(posX, posY)
                }
            }
            "U" -> {
                for (i in 0 until (value)) {
                    posY++
                    checkMax(posX, posY)
                    checkMinSteps(posX, posY)
                }
            }
            "D" -> {
                for (i in 0 until (value)) {
                    posY--
                    checkMax(posX, posY)
                    checkMinSteps(posX, posY)
                }
            }
            "L" -> {
                for (i in 0 until (value)) {
                    posX--
                    checkMax(posX, posY)
                    checkMinSteps(posX, posY)
                }
            }
        }
    }
    println(min)
    println(minSteps)
}

var min = Int.MAX_VALUE
var minSteps = Int.MAX_VALUE

fun checkMax(posX: Int, posY: Int) {
    if (points[posX]?.contains(posY) == true) {
        min = min(min, abs(posX) + abs(posY))
    }
}

fun checkMinSteps(posX: Int, posY: Int) {
    steps2++
    if (points[posX]?.contains(posY) == true) {
        minSteps = min(minSteps, steps2 + points[posX]?.get(posY)!!)
    }
}

fun addPoint(posX: Int, posY: Int) {
    steps++
    if (!points.contains(posX)) {
        points[posX] = mutableMapOf()
    }
    points[posX]?.put(posY, points[posX]?.getOrDefault(posY, steps) ?: steps)
}
