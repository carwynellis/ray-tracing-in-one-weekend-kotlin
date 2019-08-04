package uk.carwynellis.raytracing

import kotlin.math.sqrt

class Sphere(val centre: Vec3, val radius: Double) : Hitable {
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
}