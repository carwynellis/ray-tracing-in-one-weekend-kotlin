package uk.carwynellis.raytracing.math

import kotlin.math.sqrt
import uk.carwynellis.raytracing.math.SolverSyntax.isZero

/**
 * Port of the Graphics Gems code that deals with solving quartic equations.
 *
 * This is required to solve the intersection of a ray with a torus.
 *
 * See https://github.com/erich666/GraphicsGems/blob/master/gems/Roots3And4.c for the original
 * Graphics Gems code.
 */
object SolveQuartic {
    /**
     * Takes 5 coefficients and returns a list of real roots, where an empty list indicates no real roots were found.
     *
     * The coefficients are specified as follows
     *
     *  a + bx + cx^2 + dx^3 + ex^4 = 0
     */
    operator fun invoke(a: Double, b: Double, c: Double, d: Double, e: Double): List<Double> {
        // normal form: x^4 + Ax^3 + Bx^2 + Cx + D = 0
        val A = d / e
        val B = c / e
        val C = b / e
        val D = a / e

        // substitute x = y - A/4 to eliminate cubic term giving x^4 + px^2 + qx + r = 0
        val ASquared = A * A
        val p = -3.0 / 8.0 * ASquared + B
        val q = 1.0 / 8.0 * ASquared * A - 1.0 / 2.0 * A * B + C
        val r = -3.0 / 256.0 * ASquared * ASquared + 1.0 / 16.0 * ASquared * B - 1.0 / 4.0 * A * C + D

        val roots = if (r.isZero()) {
            // No absolute term y(y^3 + py + q = 0)
            // Solve the roots of the cubic
            SolveCubic(q, p, 0.0, 1.0)
        }
        else {
            // Solve the resolvent cubic...
            val cubicRoots = SolveCubic(
                a = 1.0 / 2.0 * r * p - 1.0 / 8.0 * q * q,
                b = -r,
                c = -1.0 / 2.0 * p,
                d = 1.0
            )

            // ...and take the one real solution...
            val z = cubicRoots.first()

            // ...to build two quadratic equations
            val u by lazy {
                val _u = z * z - r
                if (_u.isZero()) 0.0 else sqrt(_u)
            }
            val v by lazy {
                val _v = 2 * z - p
                if (_v.isZero()) 0.0 else sqrt(_v)
            }

            if (u >= 0.0 && v >= 0.0) {
                // Solve both equations...
                val roots1 = SolveQuadratic(
                    a = z - u,
                    b = if (q < 0) -v else v,
                    c = 1.0
                )
                val roots2 = SolveQuadratic(
                    a = z + u,
                    b = if (q < 0) v else -v,
                    c = 1.0
                )
                roots1 + roots2
            }
            else emptyList()
        }

        // Resubstitute...
        val substitution = 1.0 / 4.0 * A

        return roots.map { it - substitution }
    }
}