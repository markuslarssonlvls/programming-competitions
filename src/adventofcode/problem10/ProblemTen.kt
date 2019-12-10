package adventofcode.problem10

import kotlin.math.atan2

private fun readLn() = readLine()!! // string line

fun main() {
    //a()
    b()
}

var asteroids = mutableSetOf<Asteroid>()

fun a() {
    asteroids = mutableSetOf<Asteroid>()

    var y = 0.toDouble()
    while (true) {
        var x = 0.toDouble()

        val line = readLn()
        if (line == "1") {
            break;
        }
        line.forEach {
            if (it.toString() == "#".toString()) {
                asteroids.add(Asteroid(x, y))
            }
            x++
        }
        y++
    }
    asteroids.forEach {
        it.calcAngles()
    }
    var best = 0
    asteroids.forEach {
        val number = it.calcNumber()
        best = Math.max(number, best)
    }
    println(best)
}

fun b() {
    asteroids = mutableSetOf<Asteroid>()

    var y = 0.toDouble()
    while (true) {
        var x: Double = 0.toDouble()

        val line = readLn()
        if (line == "1") {
            break;
        }
        line.forEach {
            if (it.toString() == "#".toString()) {
                asteroids.add(Asteroid(x, y))
            }
            x++
        }
        y++
    }
    asteroids.forEach {
        it.calcAnglesWithDistance()
    }
    var best = 0
    var bestAsteroid: Asteroid = Asteroid(1.toDouble(), 1.toDouble())
    asteroids.forEach {
        val number = it.calcNumber()
        if (number > best) {
            best = Math.max(number, best)
            bestAsteroid = it
        }
    }
    bestAsteroid.beam()
    println(best)
}

class Asteroid(val x: Double, val y: Double) {
    val otherAsteroids = mutableSetOf<Double>()
    val asteroidsWithDistances = mutableMapOf<Double, MutableList<Point>>()

    fun calcAngles() {
        asteroids.forEach {
            if (x != it.x || y != it.y) {
                val angle = getAngle(x, it.x, y, it.y)
                otherAsteroids.add(angle)
            }
        }

    }

    fun calcAnglesWithDistance() {
        asteroids.forEach {
            if (x != it.x || y != it.y) {
                val angle = getAngle(x, it.x, y, it.y)
                val list = asteroidsWithDistances.get(angle) ?: mutableListOf()
                list.add(Point(it.x, it.y))
                asteroidsWithDistances.put(angle, list)
            }
        }
    }

    fun calcNumber(): Int {
        return asteroidsWithDistances.size
    }

    fun getAngle(x1: Double, x2: Double, y1: Double, y2: Double): Double {
        var angle = Math.toDegrees(atan2(y1 - y2, x1 - x2))

        if (angle < 0) {
            angle += 360f
        }

        return angle
    }

    fun beam() {


    }
}

class Point(val x: Double, val y: Double) {

}
