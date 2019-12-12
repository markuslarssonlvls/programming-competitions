package adventofcode.problem12

fun main() {
    a()
}

lateinit var moons: MutableList<Moon>

var startMoon1 = Moon(0, 12, 0, -15)
var startMoon2 = Moon(1, -8, -5, -10)
var startMoon3 = Moon(2, 7, -17, 1)
var startMoon4 = Moon(3, 2, -11, -6)
val startMoons = mutableListOf<Moon>()

var stepsXRepeated: Long? = null
var stepsYRepeated: Long? = null
var stepsZRepeated: Long? = null

fun a() {
    startMoons.add(startMoon1)
    startMoons.add(startMoon2)
    startMoons.add(startMoon3)
    startMoons.add(startMoon4)

    moons = startMoons.map { it.copie() }.toMutableList()

    var steps = 0L
    while (true) {
        moons.forEach { it.countVelocity() }
        moons.forEach { it.move() }

        steps++

        if (checkIfCoordinatesIsFromBeginning(steps)) {
            calculateLCM()
            return
        }

        if (steps == 1000L) {
            var totalEnergy = 0L
            moons.forEach { totalEnergy += it.countEnergy() }
            println(totalEnergy)
        }
    }
}

fun calculateLCM() {

    var stepX = stepsXRepeated!!
    var stepY = stepsYRepeated!!
    var stepZ = stepsZRepeated!!

    while (stepX != stepY || stepX != stepZ) {
        if (stepX < stepY || stepX < stepZ) {
            stepX += stepsXRepeated!!
        } else if (stepY < stepX || stepY < stepZ) {
            stepY += stepsYRepeated!!
        } else if (stepZ < stepX || stepZ < stepY) {
            stepZ += stepsZRepeated!!
        }
    }
    println(stepX)
}

fun checkIfCoordinatesIsFromBeginning(steps: Long): Boolean {

    if (stepsXRepeated == null) {
        if (startMoons.filter {
                    val moon = it
                    val otherMoon = moons.first { it.id == moon.id }
                    otherMoon.x == moon.x && otherMoon.velocity.x == moon.velocity.x
                }.size == startMoons.size) {
            stepsXRepeated = steps
        }
    }
    if (stepsYRepeated == null) {
        if (startMoons.filter {
                    val moon = it
                    val otherMoon = moons.first { it.id == moon.id }
                    otherMoon.y == moon.y && otherMoon.velocity.y == moon.velocity.y
                }.size == startMoons.size) {
            stepsYRepeated = steps
        }
    }
    if (stepsZRepeated == null) {
        if (startMoons.filter {
                    val moon = it
                    val otherMoon = moons.first { it.id == moon.id }
                    otherMoon.z == moon.z && otherMoon.velocity.z == moon.velocity.z
                }.size == startMoons.size) {
            stepsZRepeated = steps
        }
    }
    if (stepsXRepeated != null && stepsYRepeated != null && stepsZRepeated != null) {
        println("x: $stepsXRepeated")
        println("y: $stepsYRepeated")
        println("z: $stepsZRepeated")

        return true
    }


    return false
}

class Moon(val id: Int, var x: Long, var y: Long, var z: Long) {
    var velocity: Velocity = Velocity(0, 0, 0)
    var steps: Long? = null
    fun countVelocity() {
        moons.forEach {
            if (it.id != id) {
                velocity.change(it.x - x, it.y - y, it.z - z)
            }
        }

    }

    fun move() {
        x += velocity.x
        y += velocity.y
        z += velocity.z
    }

    fun countEnergy(): Long {
        var energy = Math.abs(velocity.x) +
                Math.abs(velocity.y) +
                Math.abs(velocity.z)
        energy *= Math.abs(x) +
                Math.abs(y) +
                Math.abs(z)
        return energy
    }

    fun copie(): Moon {
        val curVel = velocity
        return Moon(id, x, y, z).apply {
            this.velocity.apply {
                x = curVel.x
                y = curVel.y
                z = curVel.z
            }
        }
    }

    data class Velocity(var x: Long, var y: Long, var z: Long) {
        fun change(x: Long, y: Long, z: Long) {
            if (x > 0) {
                this.x++
            } else if (x < 0) {
                this.x--
            }
            if (y > 0) {
                this.y++
            } else if (y < 0) {
                this.y--
            }
            if (z > 0) {
                this.z++
            } else if (z < 0) {
                this.z--
            }
        }
    }
}
