package adventofcode.problem9

fun main() {
    a()
}

fun a() {

    val amplifierA = Amplifier(1)
    println(amplifierA.run(1))

    val amplifierB = Amplifier(2)
    println(amplifierB.run(2))
}


class Amplifier(val setting: Long) {
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
            val lastDigit = instruction.substring(instructionLength - 1, instructionLength).toLong()
            val nextLastDigit = if (instructionLength > 1) {
                instruction.substring(instructionLength - 2, instructionLength - 1).toLong()
            } else {
                0
            }

            val LongructionAtPlace1 = if (instructionLength > 2) {
                instruction.substring(instructionLength - 3, instructionLength - 2).toLong()
            } else {
                0
            }
            val LongructionAtPlace2 = if (instructionLength > 3) {
                instruction.substring(instructionLength - 4, instructionLength - 3).toLong()
            } else {
                0
            }
            val instructionAtPlace3 = if (instructionLength > 4) {
                instruction.substring(instructionLength - 5, instructionLength - 4).toLong()
            } else {
                0
            }
            when (lastDigit) {
                9L -> {
                    if (nextLastDigit == 9L) {
                        done = true
                        return output
                    } else {
                        val a = getValue(LongructionAtPlace1, currentIndex + 1L)
                        relativeBase += a.toInt()
                        currentIndex += 2
                    }
                }
                1L -> {
                    val a = getValue(LongructionAtPlace1, currentIndex + 1L)
                    val b = getValue(LongructionAtPlace2, currentIndex + 2L)

                    storeValue(instructionAtPlace3, currentIndex + 3L, a + b)

                    currentIndex += 4
                }
                2L -> {
                    val a = getValue(LongructionAtPlace1, currentIndex + 1L)
                    val b = getValue(LongructionAtPlace2, currentIndex + 2L)
                    storeValue(instructionAtPlace3, currentIndex + 3L, a * b)
                    currentIndex += 4

                }
                3L -> {
                    storeValue(LongructionAtPlace1, currentIndex + 1L, if (usedSetting) input else setting)
                    usedSetting = true
                    currentIndex += 2
                }
                4L -> {
                    val a = getValue(LongructionAtPlace1, currentIndex + 1L)
                    output = a
                    println(output)
                    currentIndex += 2
                }
                5L -> {
                    val a = getValue(LongructionAtPlace1, currentIndex + 1L)
                    val b = getValue(LongructionAtPlace2, currentIndex + 2L)

                    if (a != 0L) {
                        currentIndex = b.toInt()
                    } else {
                        currentIndex += 3
                    }
                }
                6L -> {
                    val a = getValue(LongructionAtPlace1, currentIndex + 1L)
                    val b = getValue(LongructionAtPlace2, currentIndex + 2L)

                    if (a == 0L) {
                        currentIndex = b.toInt()
                    } else {
                        currentIndex += 3
                    }
                }
                7L -> {
                    val a = getValue(LongructionAtPlace1, currentIndex + 1L)
                    val b = getValue(LongructionAtPlace2, currentIndex + 2L)

                    if (a < b) {
                        storeValue(instructionAtPlace3, currentIndex + 3L, 1L)
                    } else {
                        storeValue(instructionAtPlace3, currentIndex + 3L, 0L)
                    }
                    currentIndex += 4
                }
                8L -> {
                    val a = getValue(LongructionAtPlace1, currentIndex + 1L)
                    val b = getValue(LongructionAtPlace2, currentIndex + 2L)

                    if (a == b) {
                        storeValue(instructionAtPlace3, currentIndex + 3L, 1L)
                    } else {
                        storeValue(instructionAtPlace3, currentIndex + 3L, 0L)
                    }
                    currentIndex += 4
                }


            }
        }
    }

    fun getValue(instrutionType: Long, index: Long): Long {
        return if (instrutionType == 1L) {
            instructions[index.toInt()]
        } else if (instrutionType == 2L) {
            instructions[relativeBase + instructions[index.toInt()].toInt()]
        } else {
            instructions[instructions[index.toInt()].toInt()]
        }
    }

    fun storeValue(instrutionType: Long, index: Long, value: Long) {
        return if (instrutionType == 1L) {
            instructions[index.toInt()] = value
        } else if (instrutionType == 2L) {
            instructions[relativeBase + instructions[index.toInt()].toInt()] = value
        } else {
            instructions[instructions[index.toInt()].toInt()] = value
        }
    }
}
