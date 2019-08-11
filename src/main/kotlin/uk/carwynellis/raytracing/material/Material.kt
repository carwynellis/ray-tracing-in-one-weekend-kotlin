package uk.carwynellis.raytracing.material

import uk.carwynellis.raytracing.HitRecord
import uk.carwynellis.raytracing.Ray
import uk.carwynellis.raytracing.Vec3
import uk.carwynellis.raytracing.Vec3.Operators.times

// TODO - do we need albedo to be passed in like this?
abstract class Material(val albedo: Vec3) {
    abstract fun scatter(rayIn: Ray, record: HitRecord): Ray

    companion object {
        fun reflect(v: Vec3, n: Vec3): Vec3 = v - ( 2.0 * (v dot n) * n )
    }
}
