package uk.carwynellis.raytracing

import kotlin.math.sqrt
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
            val pixel = renderPixel(i, j).gammaCorrected()
            val ir = (255 * pixel.r).toInt()
            val ig = (255 * pixel.g).toInt()
            val ib = (255 * pixel.b).toInt()
            imageWriter.writePixel(ir, ig, ib)
        }
    }

    imageWriter.close()
}

// Apply simple gamma correction to colour values.
// TODO - introduce separate pixel class?
fun Vec3.gammaCorrected(): Vec3 = Vec3(
    x = sqrt(this.x),
    y = sqrt(this.y),
    z = sqrt(this.z)
)


// When rendering some rays may may include a floating point error preventing them from being treated as 0. We increase
// the minimum value we accept slightly which yields a smoother image without visible noise.
const val IMAGE_SMOOTHING_LIMIT = 0.0001

fun colour(ray: Ray, world: Hitable): Vec3 {
    fun backgroundColour(): Vec3 {
        val unitDirection = ray.direction.unitVector()
        val u = 0.5 * (unitDirection.y + 1)
        return (1.0 - u) * Vec3(1.0, 1.0, 1.0) + u * Vec3(0.5, 0.7, 1.0)
    }

    val hitResult = world.hit(ray, IMAGE_SMOOTHING_LIMIT, Double.MAX_VALUE)

    return hitResult?.let {
        val target = hitResult.p + hitResult.normal + Sphere.randomPointInUnitSphere()
        0.5 * colour(Ray(hitResult.p, target - hitResult.p), world)
    } ?: backgroundColour()
}
