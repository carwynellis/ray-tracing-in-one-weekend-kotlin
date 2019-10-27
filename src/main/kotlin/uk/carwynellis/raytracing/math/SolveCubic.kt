package uk.carwynellis.raytracing.math

import java.lang.Math.cbrt
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sqrt

/**
 * Port of the graphics gems code to solve cubic equations.
 *
 * This is required to support SolveQuartic, which itself is needed to solve the intersection of a ray with a torus.
 *
 * See https://github.com/erich666/GraphicsGems/blob/master/gems/Roots3And4.c
 */
object SolveCubic {
   /**
    * Takes 4 coeefficients and returns a list of real roots where an empty list indicates no real routes were found.
    *
    * The coeffients are specified as follows
    *
    *    a + bx + cx^2 + dx^3 = 0
    *
    */
   operator fun invoke(a: Double, b: Double, c: Double, d: Double): List<Double> {
      // Normal form form: x^3 + Ax^2 + Bx + C = 0
      val A = c / d
      val B = b / d
      val C = a / d

      // substitute x = y - A/3 to eliminate quadratic term giving x^3 +px + q = 0
      // and use Cardano's formula to find the roots
      // see https://www.encyclopediaofmath.org/index.php/Cardano_formula
      val ASquared = A * A
      val p = 1.0 / 3.0 * (-1.0/3.0 * ASquared + B)
      val q = 1.0 / 2.0 * (2.0/27.0 * A * ASquared - 1.0/3.0 * A * B + C)

      val pCubed = p * p * p
      val discriminant = q * q + pCubed

      val roots: List<Double> = if (discriminant == 0.0) {
         if (q == 0.0) listOf(0.0) // All solutions are zero
         else { // A single and repeated root
            val u = cbrt(-q)
            listOf(
               2.0 * u,
               -u
            )
         }
      }
      else if (discriminant < 0) { // Three real solutions
         val phi = 1.0/3.0 * acos(-q / sqrt(-pCubed))
         val t = 2.0 * sqrt(-p)
         listOf(
            t * cos(phi),
            -t * cos(phi + PI / 3.0),
            -t * cos(phi - PI / 3.0)
         )
      }
      else { // One real solution
         val discriminantRoot = sqrt(discriminant)
         val u = cbrt(discriminantRoot - q)
         val v = -cbrt(discriminantRoot + q)
         listOf(u + v)
      }

       // Resubstitute to obtain the final roots.
      val substitution = 1.0/3.0 * A

      return roots.map { it - substitution }
   }
}