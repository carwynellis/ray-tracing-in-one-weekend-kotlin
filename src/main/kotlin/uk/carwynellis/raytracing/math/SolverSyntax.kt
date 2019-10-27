package uk.carwynellis.raytracing.math

object SolverSyntax {

    private val epsilon = 1e-9

    // Allow near zero doubles to be treated as zero
    fun Double.isZero(): Boolean = (this > -epsilon) && (this < epsilon)
}