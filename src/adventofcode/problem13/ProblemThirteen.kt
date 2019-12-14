package adventofcode.problem13

fun main() {
    a()

    b()
}

val map = mutableMapOf<Long, MutableMap<Long, Long>>()


fun a() {
    val computer1 = LongComputer(true)
    while (!computer1.done) {
        val x = computer1.run(0)
        val y = computer1.run(0)
        val type = computer1.run(0)

        putValue(x, y, type)
    }

}
fun b() {
    val computer2 = LongComputer(true)
    computer2.instructions[0] = 2
    var score = 0L
    var posBallX = 0L
    var posPaddleX = 0L
    var input = 0L
    while (!computer2.done) {
        val x = computer2.run(input)
        val y = computer2.run(input)
        val type = computer2.run(input)
        if (x == -1L && y == 0L) {
            score = type
        } else {
            if (type == 4L) {
                posBallX = x
            }
            if (type == 3L) {
                posPaddleX = x
            }
        }

        if (posPaddleX < posBallX) {
            input = 1L
        }
        else if (posPaddleX > posBallX) {
            input = -1L
        }
        else {
            input = 0L
        }

    }
    println(score)

}


fun putValue(x: Long, y: Long, value: Long) {
    if (!map.containsKey(x)) {
        map.put(x, mutableMapOf())
    }

    if (map[x]!!.contains(y)) {
        if (value == 4L && map[x]!!.get(y) == 2L) {

        } else {
            map[x]!!.put(y, value)
        }
    } else {
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
        instructions = input13
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
                    countBlocks()
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

    private fun countBlocks() {
        var counter = 0
        map.forEach {
            it.value.forEach {
                if (it.value == 2L) {
                    counter++
                }
            }
        }
        println(counter)
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
