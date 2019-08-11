package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.Vec3.Operators.times
import kotlin.math.PI
import kotlin.math.tan

class Camera(
    private val origin: Vec3,
    target: Vec3,
    upVector: Vec3,
    verticalFieldOfView: Double,
    aspectRatio: Double
) {
    private val theta = verticalFieldOfView * (PI/180)
    private val halfHeight = tan(theta/2)
    private val halfWidth = aspectRatio * halfHeight

    private val w = (origin - target).unitVector()
    private val u = (upVector cross w).unitVector()
    private val v = w cross u

    private val lowerLeftCorner = origin - (halfWidth * u) - (halfHeight * v) - w
    private val horizontal = 2.0 * halfWidth * u
    private val vertical = 2.0 * halfHeight * v

    fun getRay(s: Double, t: Double) = Ray(
        origin = origin,
        direction = lowerLeftCorner + (s * horizontal) + (t * vertical) - origin
    )
}