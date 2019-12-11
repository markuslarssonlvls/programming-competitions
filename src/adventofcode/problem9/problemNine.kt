package adventofcode.problem9

fun main() {
    a()
}

fun a() {

    val computer1 = LongComputer(1)
    println(computer1.run(1))

    val computer2 = LongComputer(2)
    println(computer2.run(2))
}


class LongComputer(val setting: Long) {
    val instructions: Array<Long>
    var done: Boolean = false
    var output: Long = 0L
    var usedSetting = false
    var currentIndex = 0
    var relativeBase = 0

    init {
        instructions = input9
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
                    storeValue(instructionAtPlace1, currentIndex + 1, if (usedSetting) input else setting)
                    usedSetting = true
                    currentIndex += 2
                }
                4 -> {
                    val a = getValue(instructionAtPlace1, currentIndex + 1)
                    output = a
                    println(output)
                    currentIndex += 2
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
