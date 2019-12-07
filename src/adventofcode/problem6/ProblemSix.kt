package adventofcode.problem6

import kotlin.math.min

private fun readLn() = readLine()!! // string line


var orbits: MutableMap<String, MutableSet<String>> = mutableMapOf()
var directOrbits: MutableMap<String, MutableSet<String>> = mutableMapOf()

fun main() {
    solve()
}

fun solve() {
    orbits = mutableMapOf()
    while (true) {
        val line1 = readLn()
        if (line1 == "") {
            calcOrbits()
            tryFindSanta()
            return
        }
        val line = line1.split(")")
        val first = line[1]
        val second = line[0]
        if (!orbits.contains(first)) {
            orbits[first] = mutableSetOf()
            directOrbits[first] = mutableSetOf()
        }
        directOrbits[first]?.add(second)
        addChildren(first, second)
        orbits.filter { it.value.contains(first) }.forEach {
            val cur = it.key
            orbits.get(first)?.forEach {
                addChildren(cur, it)
            }
        }
    }

}

var bestSteps = Int.MAX_VALUE

fun tryFindSanta() {
    val san = "SAN"
    val you = "YOU"
    println("b: " + (tryFindSanta(san, you, 0) - 2))
}

val santaVisited: MutableMap<String, Int> = mutableMapOf()
val youVisited: MutableMap<String, Int> = mutableMapOf()

fun tryFindSanta(curSanta: String, curYou: String, steps: Int): Int {
    if (steps > bestSteps) {
        return steps
    }
    if (curYou == curSanta) {
        bestSteps = min(bestSteps, steps)
        return steps
    }
    directOrbits[curSanta]?.forEach {
        val santa = it
        directOrbits[curYou]?.forEach {
            if (santaVisited[santa] ?: Int.MAX_VALUE > steps) {
                santaVisited[santa] = steps + 1
                tryFindSanta(santa, curYou, steps + 1)
            }
            if (youVisited[santa] ?: Int.MAX_VALUE > steps) {
                youVisited[it] = steps + 1
                tryFindSanta(curSanta, it, steps + 1)
            }
        }
    }
    return bestSteps
}

fun addChildren(first: String, children: String) {
    if (orbits[first]?.contains(children) == false) {
        orbits[first]?.add(children)
        orbits[children]?.forEach {
            addChildren(first, it)
        }
    }
}

fun calcOrbits() {
    var sum = 0
    orbits.forEach {
        sum += it.value.size
    }
    println("a: " + sum)
}

