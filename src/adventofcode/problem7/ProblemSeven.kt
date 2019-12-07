package adventofcode.problem7

fun main() {
   // a()
    b()
}

fun b() {
    var max = 0
    for (a in 5 until 10) {
        for (b in 5 until 10) {
            if (b != a) {
                for (c in 5 until 10) {
                    if (c != a && c != b) {
                        for (d in 5 until 10) {
                            if (d != a && d != b && d != c) {
                                for (e in 5 until 10) {
                                    if (e != a && e != b && e != c && e != d) {

                                        val amplifierA = Amplifier(a)
                                        val amplifierB = Amplifier(b)
                                        val amplifierC = Amplifier(c)
                                        val amplifierD = Amplifier(d)
                                        val amplifierE = Amplifier(e)
                                        var output = 0
                                        while (!amplifierE.done) {
                                            output = amplifierA.run(output)
                                            output = amplifierB.run(output)
                                            output = amplifierC.run(output)
                                            output = amplifierD.run(output)
                                            output = amplifierE.run(output)
                                        }
                                        max = kotlin.math.max(max, output)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    println(max)
}

fun a() {
    var max = 0
    for (a in 0 until 5) {
        val outputA = Amplifier(a).run(0)
        for (b in 0 until 5) {
            if (b != a) {
                val outputB = Amplifier(b).run(outputA)
                for (c in 0 until 5) {
                    if (c != a && c != b) {
                        val outputC = Amplifier(c).run(outputB)
                        for (d in 0 until 5) {
                            if (d != a && d != b && d != c) {
                                val outputD = Amplifier(d).run(outputC)
                                for (e in 0 until 5) {
                                    if (e != a && e != b && e != c && e != d) {
                                        val outputE = Amplifier(e).run(outputD)
                                        println(outputE)
                                        max = kotlin.math.max(max, outputE)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    println(max)
}

class Amplifier(val setting: Int) {
    val instructions: Array<Int>
    var done: Boolean = false
    var output: Int = 0
    var usedSetting = false
    var currentIndex = 0

    init {
        instructions = input7.split(",").map { it.toInt() }.toTypedArray()
    }

    fun run(input: Int): Int {

        if (done) {
            return output
        }
        loop@ while (true) {

            val instruction = instructions[currentIndex].toString()
            val instructionLength = instruction.length
            val lastDigit = instruction.substring(instructionLength - 1, instructionLength).toInt()

            val intructionAtPlace1 = if (instructionLength > 2) {
                instruction.substring(instructionLength - 3, instructionLength - 2).toInt()
            } else {
                0
            }
            val intructionAtPlace2 = if (instructionLength > 3) {
                instruction.substring(instructionLength - 4, instructionLength - 3).toInt()
            } else {
                0
            }
            when (lastDigit) {
                9 -> {
                    done = true
                    return output
                }
                1 -> {
                    val a = getValue(instructions, intructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructions, intructionAtPlace2, currentIndex + 2)
                    instructions[instructions[currentIndex + 3]] = a + b

                    currentIndex += 4
                }
                2 -> {
                    val a = getValue(instructions, intructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructions, intructionAtPlace2, currentIndex + 2)
                    instructions[instructions[currentIndex + 3]] = a * b
                    currentIndex += 4

                }
                3 -> {
                    instructions[instructions[currentIndex + 1]] = if (usedSetting) input else setting
                    usedSetting = true
                    currentIndex += 2
                }
                4 -> {
                    val toOutput = instructions[currentIndex + 1]
                    output = instructions[toOutput]
                    currentIndex += 2
                    return output
                }
                5 -> {
                    val a = getValue(instructions, intructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructions, intructionAtPlace2, currentIndex + 2)

                    if (a != 0) {
                        currentIndex = b
                    } else {
                        currentIndex += 3
                    }
                }
                6 -> {
                    val a = getValue(instructions, intructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructions, intructionAtPlace2, currentIndex + 2)

                    if (a == 0) {
                        currentIndex = b
                    } else {
                        currentIndex += 3
                    }
                }
                7 -> {
                    val a = getValue(instructions, intructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructions, intructionAtPlace2, currentIndex + 2)

                    if (a < b) {
                        instructions[instructions[currentIndex + 3]] = 1
                    } else {
                        instructions[instructions[currentIndex + 3]] = 0
                    }
                    currentIndex += 4
                }
                8 -> {
                    val a = getValue(instructions, intructionAtPlace1, currentIndex + 1)
                    val b = getValue(instructions, intructionAtPlace2, currentIndex + 2)

                    if (a == b) {
                        instructions[instructions[currentIndex + 3]] = 1
                    } else {
                        instructions[instructions[currentIndex + 3]] = 0
                    }
                    currentIndex += 4
                }


            }
        }
    }
}

fun getValue(instructions: Array<Int>, instrutionType: Int, index: Int): Int {
    return if (instrutionType == 1) {
        instructions[index]
    } else {
        instructions[instructions[index]]
    }
}
