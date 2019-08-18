package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.Vec3.Operators.times
import kotlin.random.Random
import kotlin.streams.toList

class Renderer(private val camera: Camera,
               private val scene: Hitable,
               private val width: Int,
               private val height: Int,
               private val samples: Int) {

    // When rendering some rays may may include a floating point error preventing them from being treated as 0. We
    // increase the minimum value we accept slightly which yields a smoother image without visible noise.
    private val imageSmoothingLimit = 0.0001
    // Max number of times we recurse in the colour function.
    private val maxRecursionDepth = 50

    // Generates a gradated background colour where no object has been 'hit' by a ray.
    private fun backgroundColour(ray: Ray): Vec3 {
        val unitDirection = ray.direction.unitVector()
        val u = 0.5 * (unitDirection.y + 1)
        return (1.0 - u) * Vec3(1.0, 1.0, 1.0) + u * Vec3(0.5, 0.7, 1.0)
    }

    private fun colour(ray: Ray, world: Hitable, depth: Int): Vec3 {
        val hitResult = world.hit(ray, imageSmoothingLimit, Double.MAX_VALUE)

        return hitResult?.let {
            if (depth <  maxRecursionDepth) {
                val scattered = hitResult.material.scatter(ray, hitResult)
                hitResult.material.albedo * colour(scattered, world, depth + 1)
            }
            // Max recursion limit exceeded so we effectively return a no-op here.
            else Vec3(0.0, 0.0, 0.0)
        } ?: backgroundColour(ray)
    }

    private fun samplePixel(x: Double, y: Double): Vec3 {
        val u = (x + Random.nextDouble()) / width.toDouble()
        val v = (y + Random.nextDouble()) / height.toDouble()
        val ray = camera.getRay(u, v)
        return colour(ray, scene, 0)
    }

    private fun renderPixel(x: Int, y: Int): Vec3 {
        val sum = (1..samples).map {
            samplePixel(x.toDouble(), y.toDouble())
        }.reduce { acc: Vec3, v: Vec3 -> acc + v }
        return sum / samples.toDouble()
    }

    /**
     * Single threaded scene renderer with progress indication.
     */
    fun renderScene(): List<Vec3> = (height downTo 1).flatMap { j ->
        val row = (1..width).map { i -> renderPixel(i, j) }
        val percentComplete = ((height - j).toDouble() / (height-1).toDouble()) * 100
        print("\rRendering scene - %3d%s complete".format(percentComplete.toInt(), "%"))
        row
    }

    /**
     * Parallelized renderer using a parallelStream.
     *
     * This is quite lazy but probably good enough here.
     *
     * TODO - try this using co-routines for comparison.
     */
    fun renderScenePar(): List<Vec3> = (height downTo 1).toList().parallelStream().map { j ->
        (1..width).map { i -> renderPixel(i, j) }
    }.toList().flatten()
}