package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.Vec3.Operators.times
import kotlin.random.Random
import kotlin.streams.toList
import kotlinx.coroutines.*
import uk.carwynellis.raytracing.hitable.Hitable

class Renderer(private val camera: Camera,
               private val scene: Hitable,
               private val width: Int,
               private val height: Int,
               private val samples: Int) {

    // When rendering some rays may may include a floating point error preventing them from being treated as 0. We
    // increase the minimum value we accept slightly which yields a smoother image without visible noise.
    private val imageSmoothingLimit = 0.0001
    // Max number of times we can bounce a ray off objects in the colour function.
    private val maxRecursionDepth = 50

    // Generates a gradated background colour where no object has been 'hit' by a ray.
    private fun backgroundColour(ray: Ray): Vec3 {
        val unitDirection = ray.direction.unitVector()
        val u = 0.5 * (unitDirection.y + 1)
        return (1.0 - u) * Vec3(1.0, 1.0, 1.0) + u * Vec3(0.5, 0.7, 1.0)
    }

    /**
     * Renders a pixel for a given ray.
     *
     * If the ray hits an object, it is bounced in random directions until no further objects are hit, or the maximum
     * allowed number of recursions has been reached.
     *
     * This implementation is tail recursive and thus, stack safe.
     */
    private tailrec fun colour(ray: Ray, world: Hitable, accumulator: Vec3 = backgroundColour(ray), depth: Int = 0): Vec3 {
        val hitResult = world.hit(ray, imageSmoothingLimit, Double.MAX_VALUE)

        return if ((hitResult != null) && (depth < maxRecursionDepth)) {
            val scattered = hitResult.material.scatter(ray, hitResult)
            colour(scattered, world, hitResult.material.albedo * accumulator, depth + 1)
        }
        else accumulator
    }

    private fun samplePixel(x: Double, y: Double): Vec3 {
        val u = (x + Random.nextDouble()) / width.toDouble()
        val v = (y + Random.nextDouble()) / height.toDouble()
        val ray = camera.getRay(u, v)
        return colour(ray, scene)
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
        showProgress(percentComplete)
        row
    }

    /**
     * Parallelized renderer using a parallelStream.
     *
     * Note - this approach seems to come with more overhead than using co-routines, so the co-routine renderer is
     *        actually faster than this method.
     */
    fun renderScenePar(): List<Vec3> {
        var pos = 0
        return (height downTo 1).toList().parallelStream().map { j ->
            val row = (1..width).map { i -> renderPixel(i, j) }
            val percentComplete = (pos++.toDouble() / (height-1).toDouble()) * 100
            showProgress(percentComplete)
            row
        }.toList().flatten()
    }

    /**
     * An alternative parallel implementation that uses co-routines.
     *
     * Each row is parcelled up into an async job which is then executed in parallel.
     *
     * awaitAll is used on the resulting list of Deferred results to block until all jobs have completed.
     */
    fun renderSceneParCoroutines(): List<Vec3> = runBlocking {
        var pos = 0
        (height downTo 1).map { j ->
            async(Dispatchers.Default) {
                val row = (1..width).map { i -> renderPixel(i, j) }
                val percentComplete = (pos++.toDouble() / (height-1).toDouble()) * 100
                showProgress(percentComplete)
                row
            }
        }.awaitAll().flatten()
    }

    private fun showProgress(percentComplete: Double) =
        print("\rRendering scene - %3d%s complete".format(percentComplete.toInt(), "%"))
}