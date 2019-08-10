package uk.carwynellis.raytracing

import kotlin.math.sqrt
import kotlin.random.Random

class Sphere(private val centre: Vec3, private val radius: Double) : Hitable {
    override fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord? {
        val oc = r.origin - centre

        val a = r.direction.dot(r.direction)
        val b = oc.dot(r.direction)
        val c = oc.dot(oc) - (radius * radius)

        val discriminant = (b * b) - (a * c)
        val discriminantRoot = sqrt(discriminant)

        if (discriminant > 0) {
            val x = (-b - discriminantRoot) / a
            if (x < tMax && x > tMin) {
                return HitRecord(
                    t = x,
                    p = r.pointAtParameter(x),
                    normal = (r.pointAtParameter(x) - centre) / radius
                )
            }

            val y = (-b + discriminantRoot) / a
            if (y < tMax && y > tMin) {
                return HitRecord(
                    t = y,
                    p = r.pointAtParameter(y),
                    normal = (r.pointAtParameter(y) - centre) / radius
                )
            }
        }

        return null
    }

    companion object {
        tailrec fun randomPointInUnitSphere(): Vec3 {
            val randomPoint = 2.0 * Vec3(
                x = Random.nextDouble(),
                y = Random.nextDouble(),
                z = Random.nextDouble()
            )

            return if (randomPoint.squaredLength() >= 1) randomPointInUnitSphere()
            else randomPoint
        }
    }
}

