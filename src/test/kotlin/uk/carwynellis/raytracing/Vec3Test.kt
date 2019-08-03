package uk.carwynellis.raytracing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Vec3Test {

    @Test
    fun `unary minus should return current Vec3 with values negated`() {
        assertEquals(Vec3(-1.0, -2.0, -3.0), -Vec3(1.0, 2.0, 3.0))
    }

    @Test
    fun `unary plus should return Vec3 unchanged`() {
        assertEquals(Vec3(1.0, 2.0, 3.0), +Vec3(1.0, 2.0, 3.0))
    }

}