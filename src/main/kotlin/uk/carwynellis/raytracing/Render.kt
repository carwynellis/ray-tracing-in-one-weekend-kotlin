package uk.carwynellis.raytracing

/**
 * Main entrypoint that will render a scene and write it to a file.
 */
fun main() {
    val width = 800
    val height = 400

    val lowerLeftCorner = Vec3(-2.0, -1.0, -1.0)
    val horizontal = Vec3(4.0, 0.0, 0.0)
    val vertical = Vec3(0.0, 2.0, 0.0)
    val origin = Vec3(0.0, 0.0, 0.0)

    val imageWriter = ImageWriter(width, height, "image.ppm")
    imageWriter.writeHeader()

    val world = HitableList(listOf(
        Sphere(Vec3(0.0, 0.0, -1.0), 0.5),
        Sphere(Vec3(0.0, -100.5, -1.0), 100.0)
    ))

    // Generate PPM image data
    for (j in height downTo 1) {
        for (i in 1..width) {
            val u = i.toDouble() / width.toDouble()
            val v = j.toDouble() / height.toDouble()
            val ray = Ray(
                origin = origin,
                direction = lowerLeftCorner + (u * horizontal) + (v * vertical)
            )

            val colour = colour(ray, world)

            val ir = (255 * colour.r).toInt()
            val ig = (255 * colour.g).toInt()
            val ib = (255 * colour.b).toInt()

            imageWriter.writePixel(ir, ig, ib)
        }
    }

    imageWriter.close()
}

fun colour(ray: Ray, world: Hitable): Vec3 {
    val hitResult = world.hit(ray, 0.0, Double.MAX_VALUE)
    return if (hitResult != null) {
        0.5 * Vec3(
            x = hitResult.normal.x + 1.0,
            y = hitResult.normal.y + 1.0,
            z = hitResult.normal.z + 1.0
        )
    }
    else {
        val unitDirection = ray.direction.unitVector()
        val u = 0.5 * (unitDirection.y + 1)
        (1.0 - u) * Vec3(1.0, 1.0, 1.0) + u * Vec3(0.5, 0.7, 1.0)
    }
}
