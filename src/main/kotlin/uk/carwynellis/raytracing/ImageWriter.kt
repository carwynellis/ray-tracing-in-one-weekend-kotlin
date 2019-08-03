package uk.carwynellis.raytracing

import java.io.File
import java.io.PrintWriter

/**
 * A basic PPM image writer modelled after the Scala equivalent in ray-tracing-in-one-weekend.
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
