package uk.carwynellis.raytracing.math

/**
 * Port of the graphics gems code to solve cubic equations.
 *
 * This is required to support SolveQuartic, which itself is needed to solve the intersection of a ray with a torus.
 *
 * See https://github.com/erich666/GraphicsGems/blob/master/gems/Roots3And4.c
 */
object SolveCubic {
   operator fun invoke(a: Double, b: Double, c: Double, d: Double): List<Double> {
      // TODO
      return emptyList()
   }
}