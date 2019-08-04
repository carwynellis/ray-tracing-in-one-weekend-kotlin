package uk.carwynellis.raytracing

class Camera(
    private val lowerLeftCorner: Vec3,
    private val horizontal: Vec3,
    private val vertical: Vec3,
    private val origin: Vec3
) {
    fun getRay(u: Double, v: Double) = Ray(
        origin = origin,
        direction = lowerLeftCorner + (u * horizontal) + (v * vertical) - origin
    )
}