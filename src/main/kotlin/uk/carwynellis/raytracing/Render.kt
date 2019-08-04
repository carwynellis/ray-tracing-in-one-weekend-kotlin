package uk.carwynellis.raytracing

import kotlin.math.sqrt

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
    val t = hitSphere(Vec3(0.0, 0.0, -1.0), 0.5, ray)
    return if (t > 0.0) {
        val n = (ray.pointAtParameter(t) - Vec3(0.0, 0.0, -1.0)).unitVector()
        0.5 * Vec3(n.x + 1.0, n.y + 1.0, n.z + 1.0)
    }
    else {
        val unitDirection = ray.direction.unitVector()
        val u = 0.5 * (unitDirection.y + 1)
        (1.0 - u) * Vec3(1.0, 1.0, 1.0) + u * Vec3(0.5, 0.7, 1.0)
    }
}

fun hitSphere(centre: Vec3, radius: Double, r: Ray): Double {
    val oc = r.origin - centre
    val a: Double = r.direction dot r.direction
    val b = 2.0 * oc dot r.direction
    val c = (oc dot oc) - (radius * radius)
    val discriminant: Double = (b * b) - (4.0 * a * c)
    return if (discriminant < 0.0) -1.0
    else (-b - sqrt(discriminant)) / (2.0 * a)
}