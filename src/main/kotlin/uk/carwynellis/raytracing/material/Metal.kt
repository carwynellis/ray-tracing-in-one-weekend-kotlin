package uk.carwynellis.raytracing.material

import uk.carwynellis.raytracing.HitRecord
import uk.carwynellis.raytracing.Ray
import uk.carwynellis.raytracing.hitable.Sphere
import uk.carwynellis.raytracing.Vec3
import uk.carwynellis.raytracing.Vec3.Operators.times

class Metal(albedo: Vec3, val fuzziness: Double) : Material(albedo) {
    override fun scatter(rayIn: Ray, record: HitRecord): Ray {
        val reflected = reflect(rayIn.direction.unitVector(), record.normal)
        return Ray(record.p, reflected + (fuzziness * Sphere.randomPointInUnitSphere()))
    }
}