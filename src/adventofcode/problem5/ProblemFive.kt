package adventofcode.problem5


var instructions: Array<Int> = arrayOf()

fun main() {
    a()
    b()
}

fun a() {
    find(1)
}

fun b() {
    find(5)
}

fun find(inputVal: Int) {
    instructions = input5.split(",").map { it.toInt() }.toTypedArray()
    var lastOutput = 0

    var currentIndex = 0
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
                println(lastOutput)
                return
            }
            1 -> {
                val a = getValue(intructionAtPlace1, currentIndex + 1)
                val b = getValue(intructionAtPlace2, currentIndex + 2)
                instructions[instructions[currentIndex + 3]] = a + b

                currentIndex += 4
            }
            2 -> {
                val a = getValue(intructionAtPlace1, currentIndex + 1)
                val b = getValue(intructionAtPlace2, currentIndex + 2)
                instructions[instructions[currentIndex + 3]] = a * b
                currentIndex += 4

            }
            3 -> {
                instructions[instructions[currentIndex + 1]] = inputVal
                currentIndex += 2
            }
            4 -> {

                val toOutput = instructions[currentIndex + 1]
                lastOutput = instructions[toOutput]
                currentIndex += 2
            }
            5 -> {
                val a = getValue(intructionAtPlace1, currentIndex + 1)
                val b = getValue(intructionAtPlace2, currentIndex + 2)

                if (a != 0) {
                    currentIndex = b
                } else {
                    currentIndex += 3
                }
            }
            6 -> {
                val a = getValue(intructionAtPlace1, currentIndex + 1)
                val b = getValue(intructionAtPlace2, currentIndex + 2)

                if (a == 0) {
                    currentIndex = b
                } else {
                    currentIndex += 3
                }
            }
            7 -> {
                val a = getValue(intructionAtPlace1, currentIndex + 1)
                val b = getValue(intructionAtPlace2, currentIndex + 2)

                if (a < b) {
                    instructions[instructions[currentIndex + 3]] = 1
                } else {
                    instructions[instructions[currentIndex + 3]] = 0
                }
                currentIndex += 4
            }
            8 -> {
                val a = getValue(intructionAtPlace1, currentIndex + 1)
                val b = getValue(intructionAtPlace2, currentIndex + 2)

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

fun getValue(instrutionType: Int, index: Int): Int {
    return if (instrutionType == 1) {
        instructions[index]
    } else {
        instructions[instructions[index]]

    }
}
