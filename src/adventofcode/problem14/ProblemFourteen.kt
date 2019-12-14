package adventofcode.problem14

import kotlin.math.ceil


fun main() {

    val a1 = a(1)
    //val a4076485 = a(4076485) //100000 => * 40.7648132391 -> 4076481 to low
    //val a200000 = a(200000) //200000 => * 20.3824384599 -> 4076487,69 to low
    //val a300000 = a(300000) //300000 => * 13.5882923413 -> 4076487.70239 to low
    //val a400000 = a(400000) //400000 => * 10.1912222656 -> 4076488.90624 to low
    //val a500000 = a(500000) //500000 => * 8.15297625422 -> 4076488.12711 to low
    //val a600000 = a(600000) //600000 => * 6.79414865387 -> 4076489.19232 is ?
    val a2000000 = a(2000000) //2000000 => * 2.03824497442 -> 4076489.94884 is ? += rounding 0.1 => 4076490

    println((1000000000000L / a2000000) * 2000000)

    //b()
}

var left = mutableMapOf<String, Long>()
var used = mutableMapOf<String, Long>()

var producing = mutableMapOf<String, Ingrediens>()

var ingredientsLeft = mutableMapOf<String, Long>()

class Ingrediens(val name: String, val amountProduced: Long, val noProducing: Boolean = false) {
    fun produce(amount: Long) {
        if (noProducing) {
            used.put(name, (used[name] ?: 0L) + amount)
            return
        }
        val remaining = left.get(name) ?: 0L
        if (remaining >= amount) {
            left.put(name, left[name]!! - amount)
            used.put(name, (used[name] ?: 0L) + amount)
        } else {
            val numberOfCreations = ceil((amount.toDouble() - remaining) / amountProduced.toDouble()).toInt()
            var i = 0
            left.put(name, (left[name] ?: 0L) + (numberOfCreations * amountProduced - (amount)))
            used.put(name, (used[name] ?: 0L) + amount)

            while (i < numberOfCreations) {
                producedBy.forEach {
                    producing.get(it.key)?.produce(it.value)
                }
                i++
            }
        }
    }

    fun createFuel() {
        if (noProducing) {
            return
        }
        var amount = Long.MAX_VALUE
        producedBy.forEach {
            if (ingredientsLeft[it.key] ?: 0 > 0) {
                val curAmount = ingredientsLeft[it.key]!! / it.value
                amount = Math.min(amount, curAmount)
            } else {
                amount = 0
            }
        }
        if (amount > 0) {
            producedBy.forEach {
                ingredientsLeft.put(it.key, ingredientsLeft[it.key]!! - amount * it.value)
            }
            ingredientsLeft.put(name, (ingredientsLeft[name] ?: 0) + amountProduced * amount)
        }
    }

    var producedBy: MutableMap<String, Long> = mutableMapOf()
}

fun a(amountFuel: Long): Long {
    left = mutableMapOf<String, Long>()
    used = mutableMapOf<String, Long>()

    producing = mutableMapOf<String, Ingrediens>()
    var current = Ingrediens("", 1L)

    producing.put("ORE", Ingrediens("ORE", 178L, true))
    input14.split("\n")
        .map { it.split(" => ") }
        .map {
            val from = it[0]
            val to = it[1]
            var producedBy = mutableMapOf<String, Long>()
            from.split(",")
                .map { it.trimStart().trimEnd() }
                .forEach {
                    val ingrediens = it.split(" ")
                    producedBy.put(ingrediens[1], ingrediens[0].toLong())
                }
            val ingrediens = to.split(" ")
            val out = Ingrediens(ingrediens[1], ingrediens[0].toLong()).apply {
                this.producedBy = producedBy
            }
            producing.put(out.name, out)
            if (out.name == "FUEL") {
                current = out
            }
        }

    current.produce(amountFuel)
    println((used["ORE"] ?: 0) + (left["ORE"] ?: 0))
    return used["ORE"]!!

}

fun b() {
    left = mutableMapOf<String, Long>()
    used = mutableMapOf<String, Long>()

    producing = mutableMapOf<String, Ingrediens>()
    var current = Ingrediens("", 1L)

    producing.put("ORE", Ingrediens("ORE", 178L, true))
    input14.split("\n")
        .map { it.split(" => ") }
        .map {
            val from = it[0]
            val to = it[1]
            var producedBy = mutableMapOf<String, Long>()
            from.split(",")
                .map { it.trimStart().trimEnd() }
                .forEach {
                    val ingrediens = it.split(" ")
                    producedBy.put(ingrediens[1], ingrediens[0].toLong())
                }
            val ingrediens = to.split(" ")
            val out = Ingrediens(ingrediens[1], ingrediens[0].toLong()).apply {
                this.producedBy = producedBy
            }
            producing.put(out.name, out)
            if (out.name == "FUEL") {
                current = out
            }
        }

    ingredientsLeft.put("ORE", 1000000000000L)
    while (true) {
        producing.forEach {
            it.value.createFuel()
        }
        if ((ingredientsLeft["FUEL"] ?: 0) > 0) {
            println(ingredientsLeft["FUEL"])
        }
    }

}