package adventofcode.problem17

fun main() {
    a()
}

val map = mutableMapOf<Long, MutableMap<Long, Boolean>>()

fun a() {
    val computer = LongComputer(true)

    var y = 0L
    var x = 0L
    var maxX = 0L
    var maxY = 0L
    while (!computer.done) {
        val output = computer.run(0)
        when (output) {
            35L -> {
                putValue(x, y, true)
                print("#")
                x++
            }
            46L -> {
                x++
                print(".")
            }
            10L -> {
                println()
                y++
                x = 0
            }
            60L -> {
                x++
                print("<")
            }
            62L -> {
                x++
                print(">")
            }
            94L -> {
                x++
                print("x")
            }
        }
        maxX = Math.max(maxX, x)
        maxY = Math.max(maxY, y)
    }

    var sum = 0L
    var intersections = 0L
    for (y in 0..maxY) {
        for (x in 0..maxX) {
            val self = if (getValue(x, y)) 1 else 0
            if (self == 1) {
                val left = if (getValue(x - 1L, y)) 1 else 0
                val right = if (getValue(x + 1L, y)) 1 else 0
                val up = if (getValue(x, y - 1)) 1 else 0
                val down = if (getValue(x, y + 1)) 1 else 0
                if (left + right + up + down > 2) {
                    sum += (x * y)
                    intersections++
                }
            }
        }
    }
    println(sum)
    println(intersections)

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
        instructions = input17
            .plus("," + Array(10000) { 0 }.joinToString(","))
            .split(",").map { it.toLong() }
            .toTypedArray()
    }

    fun run(input: Long): Long {

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
                    storeValue(instructionAtPlace1, currentIndex + 1, input)
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
