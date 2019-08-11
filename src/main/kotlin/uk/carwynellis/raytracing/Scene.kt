package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.material.Dielectric
import uk.carwynellis.raytracing.material.Lambertian
import uk.carwynellis.raytracing.material.Metal
import kotlin.math.PI
import kotlin.math.cos
import kotlin.random.Random

object Scene {
    val twoGreySpheres = HitableList(listOf(
        Sphere(Vec3(0.0, 0.0, -1.0), 0.5, Lambertian(Vec3(0.5, 0.5, 0.5))),
        Sphere(Vec3(0.0, -100.5, -1.0), 100.0, Lambertian(Vec3(0.5, 0.5, 0.5)))
    ))

    val materialSpheres = HitableList(listOf(
        Sphere(Vec3(0.0, 0.0, -1.0), 0.5, Lambertian(Vec3(0.1, 0.2, 0.5))),
        Sphere(Vec3(0.0, -100.5, -1.0), 100.0, Lambertian(Vec3(0.8, 0.8, 0.0))),
        Sphere(Vec3(1.0, 0.0, -1.0), 0.5, Metal(Vec3(0.8, 0.6, 0.2), 0.3)),
        Sphere(Vec3(-1.0, 0.0, -1.0), 0.5, Dielectric(1.5))
    ))

    val positionableCameraScene by lazy {
        val r = cos(PI / 4.0)
        HitableList(listOf(
            Sphere(Vec3(-r, 0.0, -1.0), r, Lambertian(Vec3(0.0, 0.0, 1.0))),
            Sphere(Vec3(r, 0.0, -1.0), r, Lambertian(Vec3(1.0, 0.0, 0.0)))
        ))
    }

    val finalScene by lazy {
        val lowerBound = -11
        val upperBound = 11

        val range = lowerBound until upperBound

        // TODO - refactor - this is a rough port of the C++ code
        fun generateSphere(c: Vec3, m: Double): Sphere? = if ((c - Vec3(4.0, 0.2, 0.0)).length() > 0.9) {
            when {
                m < 0.8 -> Sphere(c, 0.2, Lambertian(Vec3(Random.nextDouble(), Random.nextDouble(), Random.nextDouble())))
                m < 0.95 -> {
                    fun randomColor() = 0.5 * (1 + Random.nextDouble())
                    Sphere(c, 0.2, Metal(
                        Vec3(randomColor(), randomColor(), randomColor()),
                        0.5 * Random.nextDouble()
                    ))
                }
                else -> Sphere(c, 0.2, Dielectric(1.5))
            }
        }
        else null

        // TODO - refactor - this is a rough port of the C++ code
        fun generateSpheres(): List<Sphere> = range.flatMap { a: Int ->
            range.flatMap { b: Int ->
                val materialSelector = Random.nextDouble()

                val centre = Vec3(
                    x = a + 0.9 * Random.nextDouble(),
                    y = 0.2,
                    z = b + 0.9 * Random.nextDouble()
                )
                listOfNotNull(generateSphere(centre, materialSelector))
            }
        }.toList()

        val scene = listOf(
            Sphere(Vec3(0.0, -1000.0, 0.0), 1000.0, Lambertian(Vec3(0.5, 0.5, 0.5))),
            Sphere(Vec3(0.0, 1.0, 0.0), 1.0, Dielectric(1.5)),
            Sphere(Vec3(-4.0, 1.0, 0.0), 1.0, Lambertian(Vec3(0.4, 0.2, 0.1))),
            Sphere(Vec3(4.0, 1.0, 0.0), 1.0, Metal(Vec3(0.7, 0.6, 0.5), 0.0))
        ) + generateSpheres()

        HitableList(scene)
    }
}