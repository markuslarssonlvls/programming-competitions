package adventofcode.problem2

var instructions: MutableList<Int> = mutableListOf()

fun main() {
    for (noun in 0..99) {
        for (verb in 0..99) {
            instructions = input2a.split(",").map { it.toInt() }.toMutableList()

            instructions[1] = noun
            instructions[2] = verb
            var currentIndex = 0

            loop@ while (true) {
                when (instructions[currentIndex]) {
                    1 -> {
                        adds(currentIndex + 1, currentIndex + 2, currentIndex + 3)
                    }

                    2 -> {
                        multiply(currentIndex + 1, currentIndex + 2, currentIndex + 3)
                    }
                    99 -> {
                        if (instructions[0] == 19690720) {
                            println(100 * noun + verb)
                            return
                        }
                        break@loop
                    }
                }
                currentIndex += 4
            }
        }
    }

}

fun adds(first: Int, second: Int, atLocation: Int) {
    instructions[instructions[atLocation]] = instructions[instructions[first]] + instructions[instructions[second]]
}

fun multiply(first: Int, second: Int, atLocation: Int) {
    instructions[instructions[atLocation]] = instructions[instructions[first]] * instructions[instructions[second]]
}

