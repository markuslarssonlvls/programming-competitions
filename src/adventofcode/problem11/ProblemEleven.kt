package adventofcode.problem11

fun main() {
    a()
}

val map = mutableMapOf<Long, MutableMap<Long, Long>>()

val WHITE = 1L
val BLACK = 0L

fun a() {
    val computer1 = LongComputer(true)
    var x = 0L
    var y = 0L
    var direction = 0L
    putValueIfEmpty(x, y, WHITE)
    while (true) {
        putValueIfEmpty(x, y, BLACK)
        val input = map[x]!!.get(y)!!
        val output1 = computer1.run(input)
        if (computer1.done) {
            printOutput()
            return
        }
        map[x]!!.put(y, output1)
        val output2 = computer1.run(input)
        if (computer1.done) {
            printOutput()
            return
        }
        if (output2 == 1L) {
            direction = (direction + 1) % 4L
        } else {
            direction = (direction + 3L) % 4L
        }
        when (direction) {
            0L -> {
                y--
            }
            1L -> {
                x++
            }
            2L -> {
                y++
            }
            3L -> {
                x--
            }
        }
    }

}

fun printOutput() {
    var count = 0
    map.forEach {
        it.value.forEach {
            count++
        }
    }
    println(count)
    var minX = Long.MAX_VALUE
    var maxX = 0L
    var minY = Long.MAX_VALUE
    var maxY = 0L
    map.forEach {
        minX = Math.min(it.key.toLong(), minX.toLong())
        maxX = Math.max(it.key.toLong(), maxX.toLong())
        it.value.forEach {
            count++
            minY = Math.min(it.key.toLong(), minY.toLong())
            maxY = Math.max(it.key.toLong(), maxY.toLong())
        }
    }
    for (y in minY until maxY + 1) {
        for (x in minX until maxX + 1) {
            putValueIfEmpty(x, y, BLACK)
            val input = map[x]!!.get(y)!!
            if (input == 0L) {
                print(" ")
            } else {
                print("x")
            }
        }
        println("")
    }
}

fun putValueIfEmpty(x: Long, y: Long, value: Long) {
    if (!map.containsKey(x)) {
        map.put(x, mutableMapOf())
    }
    if (!(map[x]!!.contains(y))) {
        map[x]!!.put(y, value)
    }
}


class LongComputer(val returnOnOutput: Boolean) {
    val instructions: Array<Long>
    var done: Boolean = false
    var output: Long = 0L
    var currentIndex = 0
    var relativeBase = 0

    init {
        instructions = input11
                .plus("," + Array(1000) { 0 }.joinToString(","))
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

                    println(output)
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
