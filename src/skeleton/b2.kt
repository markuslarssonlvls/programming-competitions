package skeleton

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets.UTF_8
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close
import com.sun.tools.javac.tree.TreeInfo.args
import java.io.FileInputStream



private fun readLn() = readLine()!! // string line
private fun readInt() = readLn().toInt() // single int
private fun readLong() = readLn().toLong() // single long
private fun readFloat() = readLn().toFloat() // single float

// Read line split by space
private fun readStrings() = readLn().split(" ") // list of strings

private fun readInts() = readStrings().map { it.toInt() } // list of ints
private fun readLongs() = readStrings().map { it.toLong() } // list of longs
private fun readFloats() = readStrings().map { it.toFloat() } // list of floats

// val (n, k) = readInts() - destructuring declaration
// println(a.joinToString("\n"))

fun main() {

    for(i in 1 until 8) {

        gzipInput(i)
    }

    val file = File("a3.gz")
    val content1 = file.readBytes()
    println(ungzip(content1))

}

fun gzipInput(compNbr: Int) {

    val file = File("src/skeleton/dict.txt")
    val content = file.useLines { it.toList() }


    val result = mutableListOf<String>()
    var preThreeChar = ""
    val saved = mutableListOf<String>()
    content.forEach {
        val charsToCheck = it.substring(0, kotlin.math.min(compNbr, it.length))
        if (preThreeChar == charsToCheck) {

        } else {
            if (saved.size > 1) {
                result.add("" + saved.size + " " + charsToCheck)
                saved.forEach {
                    if (it.length == preThreeChar.length) {
                        result.add("")
                    } else {
                        result.add(it.substring(preThreeChar.length))
                    }

                }

            } else {
                if (saved.size == 1) {
                    result.add(saved[0])
                }
            }
            saved.clear()
        }
        preThreeChar = charsToCheck
        saved.add(it)
    }
    gzip(result.joinToString("\n"), compNbr)
}


private fun printOutput() {

}

private fun gzip(content: String, nbr: Int) {
    val sChunk = 8192
    val bos = FileOutputStream("a"+nbr+".gz")
    val gzip = GZIPOutputStream(bos);
    gzip.write(content.toByteArray(Charsets.US_ASCII));
    gzip.close();
    bos.close();
}

private fun ungzip(content: ByteArray): String = GZIPInputStream(content.inputStream()).bufferedReader(UTF_8).use { it.readText() }