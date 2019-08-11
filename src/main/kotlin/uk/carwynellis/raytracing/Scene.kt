package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.material.Dielectric
import uk.carwynellis.raytracing.material.Lambertian
import uk.carwynellis.raytracing.material.Metal
import kotlin.math.PI
import kotlin.math.cos

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

    private val r = cos(PI / 4.0)
    val positionableCameraScene = HitableList(listOf(
        Sphere(Vec3(-r, 0.0, -1.0), r, Lambertian(Vec3(0.0, 0.0, 1.0))),
        Sphere(Vec3(r, 0.0, -1.0), r, Lambertian(Vec3(1.0, 0.0, 0.0)))
    ))
}