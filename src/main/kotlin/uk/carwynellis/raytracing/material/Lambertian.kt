package uk.carwynellis.raytracing.material

import uk.carwynellis.raytracing.*

class Lambertian(albedo: Vec3) : Material(albedo) {
    override fun scatter(rayIn: Ray, record: HitRecord): Ray {
        val target = record.p + record.normal + Sphere.randomPointInUnitSphere()
        return Ray(record.p, target - record.p)
    }
}