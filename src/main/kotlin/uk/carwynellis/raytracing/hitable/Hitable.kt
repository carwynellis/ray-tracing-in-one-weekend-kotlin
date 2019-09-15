package uk.carwynellis.raytracing.hitable

import uk.carwynellis.raytracing.HitRecord
import uk.carwynellis.raytracing.Ray

interface Hitable {
    fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord?
}