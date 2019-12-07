package adventofcode.problem4

var instructions: MutableList<Int> = mutableListOf()

fun main() {
    var count = 0
    for (number in 234208..765869) {
        var twoTheSame = false
        var numberSame = 1
        var incorrect = false
        var pre: Char? = null
        number.toString().toCharArray().forEach {
            if (it == pre) {
                numberSame++
            } else {
                if (numberSame == 2) {
                    twoTheSame = true
                }
                numberSame = 1
            }
            if (pre != null && pre!!.toInt() > it.toInt()) {
                incorrect = true
            }

            pre = it
        }

        if (numberSame == 2) {
            twoTheSame = true
        }
        if (twoTheSame && !incorrect) {
            count++
        }
    }
    println(count)

}


