package adventofcode.problem1

import kotlin.math.floor

fun main() {
    println(input1a.split("\n").map { it.toLong() }.map { floor(it.toDouble() / 3f) - 2 }.sum())

    println(input1a.split("\n").map { it.toLong() }.map {
        var sum = 0L
        var fuel = it
        while (fuel > 0) {
            fuel = fuel.calculateFuel()
            if (fuel > 0) {
                sum += fuel
            }
        }
        sum
    }.sum())
}

fun Long.calculateFuel(): Long {
    return (floor(this.toDouble() / 3f) - 2).toLong()
}

// 4882038