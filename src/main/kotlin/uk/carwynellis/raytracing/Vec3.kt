package uk.carwynellis.raytracing

import kotlin.math.sqrt

data class Vec3(val x: Double, val y: Double, val z: Double) {
    // Alias the x, y, z values since this class is also used to represent colour data.
    val r: Double = x
    val g: Double = y
    val b: Double = z

    // Operator overloads for basic mathematical operators.
    operator fun plus(that: Vec3): Vec3 =   Vec3(x + that.x, y + that.y, z + that.z)
    operator fun minus(that: Vec3): Vec3 =  Vec3(x - that.x, y - that.y, z - that.z)
    operator fun times(that: Vec3): Vec3 =  Vec3(x * that.x, y * that.y, z * that.z)
    operator fun div(that: Vec3): Vec3 =    Vec3(x / that.x, y / that.y, z / that.z)
    // Additional times and div operators that take a double.
    operator fun times(n: Double) = Vec3(x * n, y * n, z * n)
    operator fun div(n: Double) =   Vec3(x / n, y / n, z / n)

    /**
     * Computes the sum of each of the x, y, z components squared
     */
    fun squaredLength(): Double = (x * x) + (y * y) + (z * z)

    /**
     * Computes the vector length defined as the square root of x^2 + y^2 + z^2
     */
    fun length(): Double = sqrt(squaredLength())
}

// Unary operators
operator fun Vec3.unaryMinus() = Vec3(-x, -y, -z)
operator fun Vec3.unaryPlus() = this
// Additional times overload to support commutativity e.g. Vec3 * n = n * Vec3
operator fun Double.times(v: Vec3): Vec3 = Vec3(v.x * this, v.y * this, v.z * this)

