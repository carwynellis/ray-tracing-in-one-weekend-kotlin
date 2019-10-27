package uk.carwynellis.raytracing.math

import kotlin.math.sqrt

/**
 * Port of the Graphics Gems code that deals with solving quadratic equations.
 *
 * This is required to support SolveQuartic, which itself is needed to solve the intersection of a ray with a torus.
 */
object SolveQuadratic {

    /**
     * Takes 3 coefficients and returns a list of real roots, where an empty list indicates no real roots were found.
     *
     * The coefficients are specified as follows
     *
     *  a + bx + cx^2 = 0
     *
     */
    operator fun invoke(a: Double, b: Double, c: Double): List<Double> {
        // Normal form - x^2 + px + q = 0
        val p = b / (2 * c)
        val q = a / c

        val discriminant = (p * p) - q

        return when {
            discriminant == 0.0 -> listOf(-p) // One real root
            discriminant < 0 -> emptyList() // No real roots
            else -> { // Two real roots
                val rootD = sqrt(discriminant)
                listOf(
                    rootD - p,
                    -rootD - p
                )
            }
        }
    }

}