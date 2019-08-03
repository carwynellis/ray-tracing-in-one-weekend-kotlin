package uk.carwynellis.raytracing

import java.io.File
import java.io.PrintWriter

/**
 * Initial image writer example from chapter 1 - Output an image
 */
fun main(args: Array<String>) {
    val width = 200
    val height = 100

    val imageWriter = ImageWriter(width, height, "image.ppm")
    imageWriter.writeHeader()

    // Generate PPM image data
    for (j in height downTo 1) {
        for (i in 1..width) {
            val r = i.toDouble() / width.toDouble()
            val g = j.toDouble() / height.toDouble()
            val b = 0.2

            val ir = (255*r).toInt()
            val ig = (255*g).toInt()
            val ib = (255*b).toInt()

            imageWriter.writePixel(ir, ig, ib)
        }
    }

    imageWriter.close()
}

/**
 * A basic PPM image writer modelled after the Scala equivalent.
 */
class ImageWriter(private val width: Int, private val height: Int, private val filename: String) {
    private val writer = PrintWriter(File(filename))

    fun writeHeader(): Unit = writer.write("""
        P3
        $width
        $height
        255
        
    """.trimIndent())

    fun writePixel(r: Int, g: Int, b: Int): Unit = writer.write("$r $g $b\n")

    fun close(): Unit = writer.close()
}
