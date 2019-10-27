package uk.carwynellis.raytracing.math

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SolveQuarticTest {

    @Test
    fun `should solve an equation with four real roots`() {
        // 360 - 42x - 41x^2 + 2x^3 + x^4 = 0
        assertEquals(listOf(5.0, 3.0, -4.0, -6.0).sorted(), SolveQuartic(360.0, -42.0, -41.0, 2.0, 1.0).sorted())
    }

    @Test
    fun `should solve an equation with three real roots`() {
        assertEquals(listOf(1.0, 0.0, -1.0), SolveQuartic(0.0, -5.0, -5.0, 5.0, 5.0))
    }
    @Test
    fun `should solve an equation with two real roots`() {
        // Fudge the comparison so we just match to 2 decimal places
        assertEquals(
            listOf("0.94", "0.18"),
            SolveQuartic(1.0, -5.0, -5.0, 5.0, 5.0).map { String.format("%.2f", it) }
        )
    }

    @Test
    fun `should solve an equation with one real root`() {
        // Assuming x^4 = 0 will yield one real root
        assertEquals(listOf(0.0), SolveQuartic(0.0, 0.0, 0.0, 0.0, 1.0))
    }

    @Test
    fun `should return an empty list for an equation with no real roots`() {
        // 10 + 8x + 6x^2 + 4x^3 + 2x^4 = 0
        assertEquals(emptyList<Double>(), SolveQuartic(10.0, 8.0, 6.0, 4.0, 2.0))
    }

}