package uk.carwynellis.raytracing

/**
 * Main entrypoint that will render a scene and write it to a file.
 */
fun main() {
    val width = 200
    val height = 100

    val imageWriter = ImageWriter(width, height, "image.ppm")
    imageWriter.writeHeader()

    // Generate PPM image data
    for (j in height downTo 1) {
        for (i in 1..width) {
            val pixel = Vec3(
                x = i.toDouble() / width.toDouble(),
                y = j.toDouble() / height.toDouble(),
                z = 0.2
            )

            val ir = (255 * pixel.r).toInt()
            val ig = (255 * pixel.g).toInt()
            val ib = (255 * pixel.b).toInt()

            imageWriter.writePixel(ir, ig, ib)
        }
    }

    imageWriter.close()
}