package uk.carwynellis.raytracing.hitable

import uk.carwynellis.raytracing.HitRecord
import uk.carwynellis.raytracing.Ray
import uk.carwynellis.raytracing.Vec3
import uk.carwynellis.raytracing.material.Material
import kotlin.math.sqrt

class Torus(
    private val centre: Vec3,
    private val innerRadius: Double, // major radius
    private val crossSectionRadius: Double, // minor radius
    private val material: Material
) : Hitable {

    private val radius = innerRadius

    // TODO - this computes a sphere - temporary to allow a valid scene to be set up
    override fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord? {
        val oc = r.origin - centre

        val a = r.direction.dot(r.direction)
        val b = oc.dot(r.direction)
        val c = oc.dot(oc) - (radius * radius)

        val discriminant = (b * b) - (a * c)

        if (discriminant > 0) {
            val discriminantRoot = sqrt(discriminant)

            val x = (-b - discriminantRoot) / a
            if (x < tMax && x > tMin) {
                return HitRecord(
                    t = x,
                    p = r.pointAtParameter(x),
                    normal = (r.pointAtParameter(x) - centre) / radius,
                    material = material
                )
            }

            val y = (-b + discriminantRoot) / a
            if (y < tMax && y > tMin) {
                return HitRecord(
                    t = y,
                    p = r.pointAtParameter(y),
                    normal = (r.pointAtParameter(y) - centre) / radius,
                    material = material
                )
            }
        }

        return null
    }

}