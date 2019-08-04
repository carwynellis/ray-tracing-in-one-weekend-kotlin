package uk.carwynellis.raytracing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RayTest {

    private val underTest = Ray(Vec3(0.0, 0.0, 0.0), Vec3(1.0, 0.0, 0.0))

    @Test
    fun `pointAtParameter should compute a point along the ray line`() {
        assertEquals(Vec3(10.0, 0.0, 0.0), underTest.pointAtParameter(10.0))
    }
}