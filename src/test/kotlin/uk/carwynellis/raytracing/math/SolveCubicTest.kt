package uk.carwynellis.raytracing.math

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.round

class SolveCubicTest {

    @Test
    fun `should solve an equation with three real roots`() {
        // -6 + 11x - 6x^2 + x^3 = 0
        assertEquals(listOf(3.0, 2.0, 1.0), SolveCubic(-6.0, 11.0, -6.0, 1.0))
    }

    @Test
    fun `should solve an equation with two real roots`() {
        // -4 + 8x -5x^2 + x^3 = 0
        // Note - the solver returns a duplicate root around 2.0, one fractionally above, one fractionally below.
        // We round these in order to simplify the equality test below.
        assertEquals(listOf(2.0, 2.0, 1.0), SolveCubic(-4.0, 8.0, -5.0, 1.0).map { round(it) })
    }

    @Test
    fun `should solve an equation with one real root`() {
        // -1 + 3x -3x^2 + x^3 = 0
        assertEquals(listOf(1.0), SolveCubic(-1.0, 3.0, -3.0, 1.0))
    }

    // Note - we do not test the zero roots case since that will only occur with complex coefficients, which won't
    //        apply here (since we're only dealing with real numbers).
}