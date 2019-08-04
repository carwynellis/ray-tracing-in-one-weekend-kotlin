package uk.carwynellis.raytracing

import kotlin.random.Random

/**
 * Main entrypoint that will render a scene and write it to a file.
 */
// TODO - introduce a renderer class that encapsulates most of this
fun main() {
    val width = 800
    val height = 400
    val samples = 100

    val world = HitableList(listOf(
        Sphere(Vec3(0.0, 0.0, -1.0), 0.5),
        Sphere(Vec3(0.0, -100.5, -1.0), 100.0)
    ))

    val camera = Camera(
        lowerLeftCorner = Vec3(-2.0, -1.0, -1.0),
        horizontal = Vec3(4.0, 0.0, 0.0),
        vertical = Vec3(0.0, 2.0, 0.0),
        origin = Vec3(0.0, 0.0, 0.0)
    )

    fun samplePixel(x: Double, y: Double): Vec3 {
        val u = (x + Random.nextDouble()) / width.toDouble()
        val v = (y + Random.nextDouble()) / height.toDouble()
        val ray = camera.getRay(u, v)
        return colour(ray, world)
    }

    fun renderPixel(x: Int, y: Int): Vec3 {
        val sum = (1..samples).map {
            samplePixel(x.toDouble(), y.toDouble())
        }.reduce { acc: Vec3, v: Vec3 -> acc + v }
        return sum / samples.toDouble()
    }

    val imageWriter = ImageWriter(width, height, "image.ppm")

    imageWriter.writeHeader()

    // Generate PPM image data
    for (j in height downTo 1) {
        for (i in 1..width) {
            val pixel = renderPixel(i, j)
            val ir = (255 * pixel.r).toInt()
            val ig = (255 * pixel.g).toInt()
            val ib = (255 * pixel.b).toInt()
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
