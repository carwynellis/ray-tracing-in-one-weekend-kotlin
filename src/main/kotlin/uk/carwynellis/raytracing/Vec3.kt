package uk.carwynellis.raytracing

data class Vec3(val x: Double, val y: Double, val z: Double) {
    // Alias the x, y, z values since this class is also used to represent colour data.
    val r: Double = x
    val g: Double = y
    val b: Double = z
}

// Unary operators
operator fun Vec3.unaryMinus() = Vec3(-x, -y, -z)
operator fun Vec3.unaryPlus() = this

