package adventofcode.problem15

fun main() {
    a()

}

val map = mutableMapOf<Long, MutableMap<Long, Long>>()
val stepTakers = mutableListOf<LongComputerStep>()
val fillStepTakers = mutableListOf<FillStepTakers>()

val filledMap = mutableMapOf<Long, MutableMap<Long, Boolean>>()

var OX = 0L
var OY = 0L
var minutes = 0L

fun a() {
    var x = 0L
    var y = 0L
    putValue(x, y, 1)
    val computer1 = LongComputer(true)

    takeSteps(x, y, computer1, 1L)
    while (!stepTakers.isEmpty()) {
        val longComputerStep = stepTakers.first()
        stepTakers.removeAt(0)
        longComputerStep.takeStep()
    }

    putFilledValue(OX, OY)
    takeFillSteps(OX, OY, 1)

    while (!fillStepTakers.isEmpty()) {
        val fillStepper = fillStepTakers.first()
        fillStepTakers.removeAt(0)
        fillStepper.takeStep()
    }
    println("Minutes: $minutes")
}

fun putFilledValue(x: Long, y: Long) {
    if (!filledMap.containsKey(x)) {
        filledMap.put(x, mutableMapOf())
    }
    filledMap[x]!!.put(y, true)
}

fun takeFillSteps(x: Long, y: Long, steps: Long) {
    if (!hasValue(x + 1, y) && isEmpty(x + 1, y)) {
        fillStepTakers.add(FillStepTakers(x + 1, y, steps))
    }
    if (!hasValue(x, y + 1) && isEmpty(x, y + 1)) {
        fillStepTakers.add(FillStepTakers(x, y + 1, steps))
    }
    if (!hasValue(x, y - 1) && isEmpty(x, y - 1)) {
        fillStepTakers.add(FillStepTakers(x, y - 1, steps))
    }
    if (!hasValue(x - 1, y) && isEmpty(x - 1, y)) {
        fillStepTakers.add(FillStepTakers(x - 1, y, steps))
    }
}

fun hasValue(x: Long, y: Long): Boolean {
    return filledMap.get(x)?.get(y) ?: false
}

fun takeSteps(x: Long, y: Long, computer: LongComputer, steps: Long) {
    if (!hasLowerValue(x + 1, y, steps)) {
        stepTakers.add(LongComputerStep(computer.copy(), 4, x + 1, y, steps))
    }
    if (!hasLowerValue(x, y + 1, steps)) {
        stepTakers.add(LongComputerStep(computer.copy(), 2, x, y + 1, steps))
    }
    if (!hasLowerValue(x, y - 1, steps)) {
        stepTakers.add(LongComputerStep(computer.copy(), 1, x, y - 1, steps))
    }
    if (!hasLowerValue(x - 1, y, steps)) {
        stepTakers.add(LongComputerStep(computer.copy(), 3, x - 1, y, steps))
    }
}

fun hasLowerValue(x: Long, y: Long, steps: Long): Boolean {
    val value = map.get(x)?.get(y) ?: 0L
    if (value != 0L) {
        if (value == -1L) {
            return true
        }
        if (value < steps) {
            return true
        }
        if (value > steps) {
            return false
        }
    }
    return false
}

fun isEmpty(x: Long, y: Long): Boolean {
    val value = map.get(x)?.get(y) ?: -1

    if (value == -1L) {
        return false
    }
    return true
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

class FillStepTakers(val x: Long, val y: Long, val steps: Long) {
    fun takeStep() {
        putFilledValue(x, y)
        minutes = Math.max(steps, minutes)
        takeFillSteps(x, y, steps + 1)
    }
}

class LongComputerStep(val computer: LongComputer, val direction: Long, val x: Long, val y: Long, val steps: Long) {
    fun takeStep() {
        val output = computer.run(direction)
        when (output) {
            0L -> {
                putValue(x, y, -1)
            }
            1L -> {
                putValue(x, y, steps)
                takeSteps(x, y, computer, steps + 1)
            }
            2L -> {
                putValue(x, y, steps)
                OX = x
                OY = y
                println("O at: ($x, $y) steps $steps")
            }
        }

    }

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
        instructions = input15
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
