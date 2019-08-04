package uk.carwynellis.raytracing

/**
 * Main entrypoint that will render a scene and write it to a file.
 */
fun main() {
    val width = 200
    val height = 100

    val lowerLeftCorner = Vec3(-2.0, -1.0, -1.0)
    val horizontal = Vec3(4.0, 0.0, 0.0)
    val vertical = Vec3(0.0, 2.0, 0.0)
    val origin = Vec3(0.0, 0.0, 0.0)

    val imageWriter = ImageWriter(width, height, "image.ppm")
    imageWriter.writeHeader()

    // Generate PPM image data
    for (j in height downTo 1) {
        for (i in 1..width) {
            val u = i.toDouble() / width.toDouble()
            val v = j.toDouble() / height.toDouble()
            val ray = Ray(
                origin = origin,
                direction = lowerLeftCorner + (u * horizontal) + (v * vertical)
            )
            val colour = colour(ray)

            val ir = (255 * colour.r).toInt()
            val ig = (255 * colour.g).toInt()
            val ib = (255 * colour.b).toInt()

            imageWriter.writePixel(ir, ig, ib)
        }
    }

    imageWriter.close()
}

fun colour(ray: Ray): Vec3 {
    val unitDirection = ray.direction.unitVector()
    val t = 0.5 * (unitDirection.y + 1)
    return (1.0 - t) * Vec3(1.0, 1.0, 1.0) + t * Vec3(0.5, 0.7, 1.0)
}