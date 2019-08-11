package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.Vec3.Operators.times

/**
 * Class representing a ray, or line in three dimensional space defined by an origin and direction.
 */
class Ray(val origin: Vec3, val direction: Vec3) {
    // Compute a point along the line described by this ray instance.
    fun pointAtParameter(t: Double): Vec3 = origin + (t * direction)
}