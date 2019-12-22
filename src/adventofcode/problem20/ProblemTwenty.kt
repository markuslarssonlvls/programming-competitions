package adventofcode.problem20


val map = mutableMapOf<Int, MutableMap<Int, String>>()
val steps = mutableMapOf<Int, MutableMap<Int, Long>>()

val tunnels = mutableMapOf<String, Tunnel>()

fun main() {
    a()
}

var startX = 0
var startY = 0
var goalX = 0
var goalY = 0

data class Tunnel(
    val str: String,
    val x1: Int,
    var x2: Int,
    val y1: Int,
    var y2: Int,
    val resX1: Int,
    var resX2: Int,
    val resY1: Int,
    var resY2: Int
) {
    fun getStep(x: Int, y: Int): Step {
        if (x1 == x && y1 == y) {
            return Step(resX2, resY2)
        }
        return Step(resX1, resY1)
    }
}

fun a() {

    val rows = input20.split("\n")
    var y = 0
    rows.forEach {
        var x = 0
        it.forEach {
            putValue(x, y, it)

            x++
        }
        y++;
    }

    println(findMinSteps(startX, startY, 0))

}

fun findMinSteps(x: Int, y: Int, steps: Long): Long {

    if (getSteps(x, y) < steps) {
        return Long.MAX_VALUE
    }

    if (x == goalX && y == goalY) {
        return steps
    }
    putSteps(x, y, steps)

    var okStep = checkStep(x + 1, y)
    var minSteps = Long.MAX_VALUE
    if (okStep != null) {
        minSteps = Math.min(minSteps, findMinSteps(okStep.x, okStep.y, steps + 1))
    }
    okStep = checkStep(x - 1, y)

    if (okStep != null) {
        minSteps = Math.min(minSteps, findMinSteps(okStep.x, okStep.y, steps + 1))
    }
    okStep = checkStep(x, y + 1)

    if (okStep != null) {
        minSteps = Math.min(minSteps, findMinSteps(okStep.x, okStep.y, steps + 1))
    }
    okStep = checkStep(x, y - 1)

    if (okStep != null) {
        minSteps = Math.min(minSteps, findMinSteps(okStep.x, okStep.y, steps + 1))
    }
    return minSteps
}

fun checkStep(x: Int, y: Int): Step? {
    val value = map.get(x)?.get(y)
    when (value) {
        "." -> {
            return Step(x, y)
        }
        "#" -> {
            return null
        }
        null -> {
            return null
        }
        "" -> {
            return null
        }
        " " -> {
            return null
        }
        "AA" -> {
            return null
        }
        "ZZ" -> {
            return Step(x, y)
        }
        else -> {
            return tunnels.get(value)?.getStep(x, y)
        }
    }

}

data class Step(val x: Int, val y: Int)

fun getSteps(x: Int, y: Int): Long {
    return steps.get(x)?.get(y) ?: Long.MAX_VALUE
}

fun putSteps(x: Int, y: Int, ss: Long) {
    if (!steps.containsKey(x)) {
        steps.put(x, mutableMapOf())
    }
    steps[x]!!.put(y, ss)
}

fun putValue(x: Int, y: Int, value: Char) {
    val stringValue = value.toString()
    if (!map.containsKey(x)) {
        map.put(x, mutableMapOf())
    }
    map[x]!!.put(y, stringValue)
    if (isLetter(value)) {
        val letterBefore = map.get(x - 1)?.get(y) ?: " "
        if (isLetter(letterBefore.toCharArray()[0])) {
            val res = letterBefore + stringValue

            var xx: Int
            var yy = y
            var resX: Int
            var resY = y
            if (map.get(x - 2)?.get(y) == ".") {
                map[x - 1]!!.put(y, letterBefore + stringValue)
                resX = x - 2
                xx = x - 1
            } else {
                map[x]!!.put(y, letterBefore + stringValue)
                resX = x + 1
                xx = x
            }


            if (res == "AA") {
                startX = resX
                startY = resY
            } else if (res == "ZZ") {
                goalX = resX
                goalY = resY
            } else {
                var tunnel = tunnels.get(res)
                if (tunnel != null) {
                    tunnel.x2 = xx
                    tunnel.y2 = yy
                    tunnel.resX2 = resX
                    tunnel.resY2 = resY
                } else {
                    tunnel = Tunnel(res, xx, 0, yy, 0, resX, 0, resY, 0)
                    tunnels.put(res, tunnel)
                }
            }
        }
        val letterBeforeY = map.get(x)?.get(y - 1) ?: " "
        if (isLetter(letterBeforeY.toCharArray()[0])) {
            val res = letterBeforeY + stringValue

            var xx = x
            var yy: Int
            var resX = x
            var resY: Int
            if (map.get(x)?.get(y - 2) == ".") {
                map[x]!!.put(y - 1, res)
                resY = y - 2
                yy = y - 1
            } else {
                map[x]!!.put(y, res)
                resY = y + 1
                yy = y
            }

            if (res == "AA") {
                startX = resX
                startY = resY
            } else if (res == "ZZ") {
                goalX = resX
                goalY = resY
            } else {
                var tunnel = tunnels.get(res)
                if (tunnel != null) {
                    tunnel.x2 = xx
                    tunnel.y2 = yy
                    tunnel.resX2 = resX
                    tunnel.resY2 = resY
                } else {
                    tunnel = Tunnel(res, xx, 0, yy, 0, resX, 0, resY, 0)
                    tunnels.put(res, tunnel)
                }
            }
        }
    }
}

fun isLetter(str: Char): Boolean {
    when (str) {
        in 'A'..'Z' -> {
            return true
        }
    }
    return false
}