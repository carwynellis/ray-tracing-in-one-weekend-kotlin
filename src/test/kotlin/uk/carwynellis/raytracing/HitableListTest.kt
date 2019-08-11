package uk.carwynellis.raytracing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import uk.carwynellis.raytracing.material.Lambertian

class HitableListTest {
    private val underTest = HitableList(listOf(
        Sphere(Vec3(0.0, 0.0, 0.0), 1.0, Lambertian(Vec3(1.0, 1.0, 1.0))),
        Sphere(Vec3(10.0, 10.0, 10.0), 1.0, Lambertian(Vec3(1.0, 1.0, 1.0)))
    ))

    @Test
    fun `hit should return a hitRecord for a ray that intersects the sphere`() {
        // Ray with direction towards origin which will intersect the sphere centred at the origin.
        val ray = Ray(origin = Vec3(2.0, 2.0, 2.0), direction = Vec3(-2.0, -2.0, -2.0))
        val result = underTest.hit(ray, 0.0, Double.MAX_VALUE)
        assertNotNull(result)
    }

    @Test
    fun `hit should return null for a ray that does not intersect the sphere`() {
        // Ray with direction moving away from origin which will not intersect the sphere centred at the origin.
        val ray = Ray(origin = Vec3(2.0, 2.0, 2.0), direction = Vec3(20.0, 20.0, -20.0))
        val result = underTest.hit(ray, 0.0, Double.MAX_VALUE)
        assertNull(result)
    }
}