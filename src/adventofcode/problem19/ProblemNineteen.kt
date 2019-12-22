package adventofcode.problem19

fun main() {
    a()
}

val map = mutableMapOf<Long, MutableMap<Long, Boolean>>()

fun a() {

    var startY = 1620L
    var endY = 1720L
    var dy = 0L
    while (true) {
        var sum = 0
        var minX = 0L
        var maxX = Long.MAX_VALUE
        var row = 0
        // 11431637 is to big
        // 11371628 is to big

        for (y in (startY + dy) until (endY + dy)) {
            var rowSum = 0L
            var curMinX = Long.MAX_VALUE
            var curMaxX = Long.MIN_VALUE
            for (x in 1120 until 1240L) {
                val computer = LongComputer(true)
                val output = computer.run(listOf(x, y))
                if (output == 1L) {
                    curMinX = Math.min(x, curMinX)
                    curMaxX = Math.max(x, curMaxX)
                    rowSum++
                    sum++
                    //print("#")
                } else {
                    // print(".")
                }
            }

            minX = Math.max(minX, curMinX)
            maxX = Math.min(maxX, curMaxX)

            //println("curRow: $row")
            //println("x1: $minX x2: $maxX  (${maxX - minX})")
            //println()
            row++
        }

        println(maxX - minX)
        println("Min X: $minX")
        println("Min Y: ${startY + dy}")

        if (maxX - minX == 99L) {
            println("Min X: $minX")
            println("Min Y: ${startY + dy}")
            return
        }
        dy++

    }

}

fun putValue(x: Long, y: Long, value: Boolean) {
    if (!map.containsKey(x)) {
        map.put(x, mutableMapOf())
    }
    map[x]!!.put(y, value)
}

fun getValue(x: Long, y: Long): Boolean {
    return map.get(x)?.get(y) ?: false
}

class LongComputer(val returnOnOutput: Boolean) {
    var instructions: Array<Long>
    var done: Boolean = false
    var output: Long = 0L
    var currentIndex = 0
    var relativeBase = 0

    fun copy(): LongComputer {
        val computer = LongComputer(returnOnOutput)
        computer.done = done
        computer.output = output
        computer.currentIndex = currentIndex
        computer.relativeBase = relativeBase
        computer.instructions = instructions.map { it }.toTypedArray()
        return computer
    }

    init {
        instructions = input19
            .plus("," + Array(10000) { 0 }.joinToString(","))
            .split(",").map { it.toLong() }
            .toTypedArray()
    }

    fun run(input: List<Long>): Long {
        var curInputIndex = 0
        if (done) {
            return output
        }
        loop@ while (true) {

            val instruction = instructions[currentIndex].toString()
            val instructionLength = instruction.length
            val lastDigit = instruction.substring(instructionLength - 1, instructionLength)
            val lastDigits = if (instructionLength > 1) {
                (instruction.substring(instructionLength - 2, instructionLength - 1) + lastDigit).toInt()
            } else {
                lastDigit.toInt()
            }

            val instructionAtPlace1 = if (instructionLength > 2) {
                instruction.substring(instructionLength - 3, instructionLength - 2).toInt()
            } else {
                0
            }
            val instructionAtPlace2 = if (instructionLength > 3) {
                instruction.substring(instructionLength - 4, instructionLength - 3).toInt()
            } else {
                0
            }
            val instructionAtPlace3 = if (instructionLength > 4) {
                instruction.substring(instructionLength - 5, instructionLength - 4).toInt()
            } else {
                0
            }
            when (lastDigits) {
                99 -> {
                    done = true
                    return output
                }
                9 -> {
                    val a = getValue(instructionAtPlace1, currentIndex + 1)
                    relativeBase += a.toInt()
                    currentIndex += 2
                }
                1 -> {
                    val a = getValue(instructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructionAtPlace2, currentIndex + 2)

                    storeValue(instructionAtPlace3, currentIndex + 3, a + b)

                    currentIndex += 4
                }
                2 -> {
                    val a = getValue(instructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructionAtPlace2, currentIndex + 2)
                    storeValue(instructionAtPlace3, currentIndex + 3, a * b)
                    currentIndex += 4

                }
                3 -> {
                    storeValue(instructionAtPlace1, currentIndex + 1, input[curInputIndex])
                    curInputIndex++
                    currentIndex += 2
                }
                4 -> {
                    val a = getValue(instructionAtPlace1, currentIndex + 1)
                    output = a
                    currentIndex += 2

                    //println(output)
                    if (returnOnOutput) {
                        return output
                    }
                }
                5 -> {
                    val a = getValue(instructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructionAtPlace2, currentIndex + 2)

                    if (a != 0L) {
                        currentIndex = b.toInt()
                    } else {
                        currentIndex += 3
                    }
                }
                6 -> {
                    val a = getValue(instructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructionAtPlace2, currentIndex + 2)

                    if (a == 0L) {
                        currentIndex = b.toInt()
                    } else {
                        currentIndex += 3
                    }
                }
                7 -> {
                    val a = getValue(instructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructionAtPlace2, currentIndex + 2)
                    storeValue(instructionAtPlace3, currentIndex + 3, if (a < b) 1L else 0L)

                    currentIndex += 4
                }
                8 -> {
                    val a = getValue(instructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructionAtPlace2, currentIndex + 2)

                    storeValue(instructionAtPlace3, currentIndex + 3, if (a == b) 1L else 0L)
                    currentIndex += 4
                }
            }
        }
    }

    private fun getValue(instructionType: Int, index: Int): Long {
        return when (instructionType) {
            1 -> instructions[index]
            2 -> instructions[relativeBase + instructions[index].toInt()]
            else -> instructions[instructions[index].toInt()]
        }
    }

    private fun storeValue(instructionType: Int, index: Int, value: Long) {
        return when (instructionType) {
            1 -> instructions[index] = value
            2 -> instructions[relativeBase + instructions[index].toInt()] = value
            else -> instructions[instructions[index].toInt()] = value
        }
    }
}
