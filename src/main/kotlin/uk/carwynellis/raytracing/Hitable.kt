package uk.carwynellis.raytracing

interface Hitable {
    fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord?
}