package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.Vec3.Operators.times
import kotlin.math.PI
import kotlin.math.tan

class Camera(
    verticalFieldOfView: Double,
    aspectRatio: Double
) {
    private val theta = verticalFieldOfView * (PI/180)
    private val halfHeight = tan(theta/2)
    private val halfWidth = aspectRatio * halfHeight
    private val lowerLeftCorner = Vec3(-halfWidth, -halfHeight, -1.0)
    private val horizontal = Vec3(2 * halfWidth, 0.0, 0.0)
    private val vertical = Vec3(0.0, 2 * halfHeight, 0.0)
    private val origin = Vec3(0.0, 0.0, 0.0)

    fun getRay(u: Double, v: Double) = Ray(
        origin = origin,
        direction = lowerLeftCorner + (u * horizontal) + (v * vertical) - origin
    )
}