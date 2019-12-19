package adventofcode.problem18

fun main() {
    //a()
    b()
}

val map = mutableMapOf<Int, MutableMap<Int, Char>>()
var allKeys = mutableSetOf<Char>()

var topLeftKeys = mutableSetOf<Char>()
var topRightKeys = mutableSetOf<Char>()
var bottomLeftKeys = mutableSetOf<Char>()
var bottomRightKeys = mutableSetOf<Char>()

fun a() {
    val rows = input18.split("\n")
    var y = 0
    var startX = 0
    var startY = 0
    rows.forEach {
        var x = 0
        it.forEach {
            print(it)
            putValue(x, y, it)
            if (it == '@') {
                startX = x
                startY = y
            }

            when (it) {
                in 'a'..'z' -> {
                    allKeys.add(it)
                }
            }
            x++
        }
        println()
        y++;
    }

    // val currentMap = CurrentMap(startX, startY)
    //currentMap.allocateStep()
    // solve(currentMap, 0)
    // println(globalBest)
}

var robot1 = Robot(1, 1, 1)
var robot2 = Robot(1, 1, 1)
var robot3 = Robot(1, 1, 1)
var robot4 = Robot(1, 1, 1)

fun b() {
    val rows = input18b.split("\n")
    var y = 0
    rows.forEach {
        var x = 0
        it.forEach {
            print(it)
            putValue(x, y, it)
            if (it == '@') {
                if (x < 40 && y < 40) {
                    robot1 = Robot(0, x, y)
                }

                if (x > 40 && y < 40) {
                    robot2 = Robot(1, x, y)
                }

                if (x < 40 && y > 40) {
                    robot3 = Robot(2, x, y)
                }

                if (x > 40 && y > 40) {
                    robot4 = Robot(3, x, y)
                }
            }

            when (it) {
                in 'a'..'z' -> {
                    if (x < 40 && y < 40) {
                        topLeftKeys.add(it)
                    }

                    if (x > 40 && y < 40) {
                        topRightKeys.add(it)
                    }

                    if (x < 40 && y > 40) {
                        bottomLeftKeys.add(it)
                    }

                    if (x > 40 && y > 40) {
                        bottomRightKeys.add(it)
                    }
                    allKeys.add(it)
                }
            }
            x++
        }
        println()
        y++;
    }

    val currentMap = CurrentMap(listOf(robot1, robot2, robot3, robot4))
    //currentMap.allocateStep()
    solve(currentMap, 0)
    println(globalBest)
}

data class Robot(var pos: Int, var x: Int, var y: Int) {

}

var globalBest = 2734L
var keysMapSteps = mutableMapOf<String, MutableMap<String, Long>>()

class CurrentMap(var robots: List<Robot>) {
    //var hasBeenAt = mutableMapOf<Int, MutableMap<Int, Boolean>>()
    var hasOpenDoor = mutableSetOf<Char>()
    var keys = mutableSetOf<Char>()
    fun copy(): CurrentMap {
        val currentMap = CurrentMap(robots.map { it.copy() })
        //currentMap.hasBeenAt = hasBeenAt.toMutableMap()
        currentMap.keys = keys.toMutableSet()
        return currentMap
    }

    fun takeStep(robot: Robot, xDiff: Int, yDiff: Int, steps: Long): CurrentMap? {
        val newX = robot.x + xDiff
        val newY = robot.y + yDiff
        if (!bestStep(newX, newY, steps)) {
            return null
        }
        val curMap = copy()
        curMap.robotPos(newX, newY, robot.pos)
        if (!curMap.bestStep(steps)) {
            return null
        }
        val value = getMapValue(newX, newY)
        when (value) {
            '#' -> {
                return null
            }
            '.' -> {
                //   curMap.allocateStep()
                return curMap
            }
            in 'a'..'z' -> {
                if (!curMap.takeKey(value, steps)) {
                    return null
                }
                return curMap
            }
            in 'A'..'Z' -> {
                if (curMap.hasKey(value, steps)) {
                    return curMap
                }
                return null
            }
            '@' -> {
                //   curMap.allocateStep()
                return curMap
            }
        }
        return null
    }

    private fun robotPos(newX: Int, newY: Int, pos: Int) {
        robots.get(pos).x = newX
        robots.get(pos).y = newY
    }

    private fun bestStep(curX: Int, curY: Int, steps: Long): Boolean {
        val sortedString = keys.toSortedSet().toString()
        val sortedStringDoors = hasOpenDoor.toSortedSet().toString()
        val value = "${curX}, ${curY}"
        val bestSteps = keysMapSteps.get(value)?.get(sortedString + sortedStringDoors) ?: Long.MAX_VALUE
        if (bestSteps <= steps) {
            return false
        } else {
            keysMapSteps.getOrPut(value) { mutableMapOf() }.put(sortedString + sortedStringDoors, steps)
        }
        return true
    }

    private fun bestStep(steps: Long): Boolean {
        val sortedString = keys.toSortedSet().toString()
        val sortedStringDoors = hasOpenDoor.toSortedSet().toString()
        var value = ""
        robots.forEach { value += "${it.x},${it.y}," }
        val bestSteps = keysMapSteps.get(value)?.get(sortedString + sortedStringDoors) ?: Long.MAX_VALUE
        if (bestSteps <= steps) {
            return false
        } else {
            keysMapSteps.getOrPut(value) { mutableMapOf() }.put(sortedString + sortedStringDoors, steps)
        }
        return true
    }

    private fun hasKey(value: Char, steps: Long): Boolean {

        if (keys.contains(value.toLowerCase())) {
            if (hasOpenDoor.contains(value)) {

            } else {
                hasOpenDoor.add(value)
                //        hasBeenAt.clear()
            }
            //   allocateStep()
            return true
        }
        // allocateStep()
        return false
    }

    private fun takeKey(value: Char, steps: Long): Boolean {

        if (keys.contains(value)) {
            return true
        } else {
            keys.add(value)
            //     hasBeenAt.clear()
        }
        // allocateStep()
        return true
    }

    /*
    fun allocateStep() {
        putValue(curX, curY, true)
    }

    fun putValue(x: Int, y: Int, value: Boolean) {
        if (!hasBeenAt.containsKey(x)) {
            hasBeenAt.put(x, mutableMapOf())
        }
        hasBeenAt[x]!!.put(y, value)
    }

    fun getValue(x: Int, y: Int): Boolean {
        return hasBeenAt.get(x)?.get(y) ?: false
    }
    */
}

fun solve(currentMap: CurrentMap, steps: Long): Long {
    if (allKeys.size == currentMap.keys.size) {
        println(steps)
        return steps
    }

    if (globalBest <= steps) {
        return globalBest
    }

    if ((currentMap.keys.size + 1) * 130 < steps) {
        return globalBest
    }
    var bestSteps = Long.MAX_VALUE
    var i = 0
    currentMap.robots.forEach {
        val rightStep = currentMap.takeStep(currentMap.robots.get(i), 1,0, steps + 1)
        if (rightStep != null) {
            bestSteps = solve(rightStep, steps + 1)
            globalBest = Math.min(globalBest, bestSteps)
        }
        if (globalBest < steps) {
            return globalBest
        }
        val LeftStep = currentMap.takeStep(currentMap.robots.get(i),-1, 0, steps + 1)
        if (LeftStep != null) {
            bestSteps = Math.min(solve(LeftStep, steps + 1), bestSteps)
            globalBest = Math.min(globalBest, bestSteps)
        }
        if (globalBest < steps) {
            return globalBest
        }
        val topStep = currentMap.takeStep(currentMap.robots.get(i), 0,-1, steps + 1)
        if (topStep != null) {
            bestSteps = Math.min(solve(topStep, steps + 1), bestSteps)
            globalBest = Math.min(globalBest, bestSteps)
        }
        if (globalBest < steps) {
            return globalBest
        }
        val bottomStep = currentMap.takeStep(currentMap.robots.get(i), 0,1, steps + 1)
        if (bottomStep != null) {
            bestSteps = Math.min(solve(bottomStep, steps + 1), bestSteps)
            globalBest = Math.min(globalBest, bestSteps)
        }
        i++

    }


    return bestSteps
}

fun putValue(x: Int, y: Int, value: Char) {
    if (!map.containsKey(x)) {
        map.put(x, mutableMapOf())
    }
    map[x]!!.put(y, value)
}

fun getMapValue(x: Int, y: Int): Char {
    return map.get(x)?.get(y) ?: '#'
}