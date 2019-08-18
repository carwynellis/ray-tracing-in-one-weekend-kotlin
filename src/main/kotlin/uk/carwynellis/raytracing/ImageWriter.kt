package uk.carwynellis.raytracing

import java.io.File
import java.io.PrintWriter
import kotlin.math.sqrt

/**
 * A basic PPM image writer modelled after the Scala equivalent in ray-tracing-in-one-weekend.
 */
class ImageWriter(private val width: Int, private val height: Int, private val filename: String) {

    fun writeImageData(imageData: List<Vec3>) {
        val writer = PrintWriter(File(filename))

        writer.writeHeader()

        imageData.forEach {
            val pixel = it.gammaCorrected()
            val ir = (255 * pixel.r()).toInt()
            val ig = (255 * pixel.g()).toInt()
            val ib = (255 * pixel.b()).toInt()
            writer.write("$ir $ig $ib\n")
        }

        writer.close()
    }

    private fun PrintWriter.writeHeader(): Unit = this.write("""
        P3
        $width
        $height
        255
        
    """.trimIndent())

    // Apply simple gamma correction to colour values.
    private fun Vec3.gammaCorrected(): Vec3 = Vec3(
        x = sqrt(this.x),
        y = sqrt(this.y),
        z = sqrt(this.z)
    )
}
