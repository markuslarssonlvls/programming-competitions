package adventofcode.problem16

fun main() {
    var input = input16
    a(input)
    var inputB = ""
    for (i in 0 until 10000) {
        inputB += input16
    }
    val offset = input16.substring(0, 7).toInt()
    a(inputB, offset)

}

fun a(input: String, outputOffset: Int = 0) {
    var input2 = input
    var shift = 0
    while (shift < 100) {
        input2 = calculate(input2)
        shift++
    }
    println(input2.subSequence(outputOffset, outputOffset + 8))
}

fun calculate(input: String): String {

    val list = listOf(0, 1, 0, -1)
    val map = input.mapIndexed { index, char ->
        val repeating = index + 1
        val resList = mutableListOf<Int>()
        list.forEach {
            for (i in 0 until repeating) {
                resList.add(it)
            }
        }
        var index = 0

        resList.removeAt(0)
        resList.add(0)
        while (resList.size < input.length) {
            resList.add(resList.get(index))
            index++
        }

        var totSum = 0
        var index2 = 0
        input.forEach {
            val number = it.toString().toInt()
            val otherNumber = resList.get(index2)
            totSum += number * otherNumber
            index2++
        }
        (Math.abs(totSum) % 10).toString()
    }
    return map.joinToString("")
}
