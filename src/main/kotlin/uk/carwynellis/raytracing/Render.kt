package uk.carwynellis.raytracing

import kotlin.math.sqrt
import kotlin.random.Random
import uk.carwynellis.raytracing.Vec3.Operators.times

/**
 * Main entrypoint that will render a scene and write it to a file.
 */
// TODO - introduce a renderer class that encapsulates most of this
fun main() {
    val width = 1200
    val height = 800
    val samples = 10

    val scene = Scene.finalScene

    val origin = Vec3(13.0, 2.0, 3.0)
    val target = Vec3(0.0, 0.0, 0.0)

    val camera = Camera(
        origin = origin,
        target = target,
        upVector = Vec3(0.0, 1.0, 0.0),
        verticalFieldOfView = 20.0,
        aspectRatio = width.toDouble() / height.toDouble(),
        aperture = 0.1,
        focusDistance = 10.0
    )

    val renderer = Renderer(camera, scene, width, height, samples)

    print("Rendering scene -   0% complete")

    val imageData = renderer.renderScene()

    println("Writing image...")

    val imageWriter = ImageWriter(width, height, "image.ppm")

    imageWriter.writeHeader()

    imageData.forEach {
        // TODO - tidy this up? extension method & pixel class?
        val pixel = it.gammaCorrected()
        val ir = (255 * pixel.r()).toInt()
        val ig = (255 * pixel.g()).toInt()
        val ib = (255 * pixel.b()).toInt()
        imageWriter.writePixel(ir, ig, ib)
    }

    imageWriter.close()

    println("Done.")
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
// Max number of times we recurse in the colour function.
const val MAX_RECURSION_DEPTH = 50

// TODO - can this be expressed as a tailrec function?
fun colour(ray: Ray, world: Hitable, depth: Int): Vec3 {
    fun backgroundColour(): Vec3 {
        val unitDirection = ray.direction.unitVector()
        val u = 0.5 * (unitDirection.y + 1)
        return (1.0 - u) * Vec3(1.0, 1.0, 1.0) + u * Vec3(0.5, 0.7, 1.0)
    }

    val hitResult = world.hit(ray, IMAGE_SMOOTHING_LIMIT, Double.MAX_VALUE)

    return hitResult?.let {
        if (depth <  MAX_RECURSION_DEPTH) {
            val scattered = hitResult.material.scatter(ray, hitResult)
            hitResult.material.albedo * colour(scattered, world, depth + 1)
        }
        // Max recursion limit exceeded so we effectively return a no-op here.
        else Vec3(0.0, 0.0, 0.0)
    } ?: backgroundColour()
}
