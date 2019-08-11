package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.Vec3.Operators.times
import kotlin.math.PI
import kotlin.math.tan
import kotlin.random.Random

class Camera(
    private val origin: Vec3,
    target: Vec3,
    upVector: Vec3,
    verticalFieldOfView: Double,
    aspectRatio: Double,
    aperture: Double,
    focusDistance: Double
) {
    private val theta = verticalFieldOfView * (PI/180)
    private val halfHeight = tan(theta/2.0)
    private val halfWidth = aspectRatio * halfHeight

    private val w = (origin - target).unitVector()
    private val u = (upVector cross w).unitVector()
    private val v = w cross u

    private val lowerLeftCorner =
        origin - (halfWidth * focusDistance * u) - (halfHeight * focusDistance * v) - (focusDistance * w)

    private val horizontal = 2 * halfWidth * focusDistance * u
    private val vertical = 2 * halfHeight * focusDistance * v

    private val lensRadius = aperture / 2.0

    fun getRay(s: Double, t: Double): Ray {
        val rd = lensRadius * randomPointInUnitDisk()
        val offset = (u * rd.x) + (v * rd.y)

        return Ray(
            origin = origin + offset,
            direction = lowerLeftCorner + (s * horizontal) + (t * vertical) - origin - offset
        )
    }

    private tailrec fun randomPointInUnitDisk(): Vec3 {
        val randomPoint = 2.0 * Vec3(
            x = Random.nextDouble(),
            y = Random.nextDouble(),
            z = 0.0
        ) - Vec3(1.0, 1.0, 0.0)

        return if ((randomPoint dot randomPoint)  >= 1.0) randomPointInUnitDisk()
        else randomPoint
    }
}