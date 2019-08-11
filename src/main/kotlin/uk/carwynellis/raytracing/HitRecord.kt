package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.material.Material

data class HitRecord(
    val t: Double,
    val p: Vec3,
    val normal: Vec3,
    val material: Material
)