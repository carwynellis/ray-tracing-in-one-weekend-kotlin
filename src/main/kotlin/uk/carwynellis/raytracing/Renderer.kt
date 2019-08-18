package uk.carwynellis.raytracing

import kotlin.random.Random

class Renderer(private val camera: Camera,
               private val scene: Hitable,
               private val width: Int,
               private val height: Int,
               private val samples: Int) {

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

    fun renderScene(): List<Vec3> {
        return (height downTo 1).flatMap { j ->
            val row = (1..width).map { i -> renderPixel(i, j) }
            val percentComplete = ((height - j).toDouble() / (height-1).toDouble()) * 100
            print("\rRendering scene - %3d%s complete".format(percentComplete.toInt(), "%"))
            row
        }
    }
}