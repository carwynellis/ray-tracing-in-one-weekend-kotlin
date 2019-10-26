package uk.carwynellis.raytracing.math

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SolveQuadraticTest {

    @Test
    fun `should solve equation with two real roots`() {
        // 6 + 5x + x^2 = 0
        assertEquals(listOf(-2.0, -3.0), SolveQuadratic(6.0, 5.0, 1.0))
    }

    @Test
    fun `should solve equation with one real root`() {
        // x^2 = 0 has a single root, 0
        assertEquals(listOf(-0.0), SolveQuadratic(0.0, 0.0, 1.0))
    }

    @Test
    fun `should return an empty list given an equation with no real roots`() {
        // 4 -3x + x^2 = 0 has no real roots
        assertEquals(emptyList(), SolveQuadratic(4.0, -3.0, 1.0))
    }

}