package uk.carwynellis.raytracing

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

// TODO - consider using matchers - something like hamkrest?
class Vec3Test {

    @Test
    fun `unary minus should return current Vec3 with values negated`() {
        assertEquals(Vec3(-1.0, -2.0, -3.0), -Vec3(1.0, 2.0, 3.0))
    }

    @Test
    fun `unary plus should return Vec3 unchanged`() {
        assertEquals(Vec3(1.0, 2.0, 3.0), +Vec3(1.0, 2.0, 3.0))
    }

    @Test
    fun `squaredLength should compute a valid result for a given Vec3`() {
        assertEquals(14.0, Vec3(1.0, 2.0, 3.0).squaredLength())
    }

    @Test
    fun `length should compute a valid result for a given Vec3`() {
       assertEquals(sqrt(14.0), Vec3(1.0, 2.0, 3.0).length())
    }

    @Test
    fun `plus operator should return a new Vec3 containing the sum of each component value`() {
        assertEquals(Vec3(3.0, 3.0, 3.0), Vec3(1.0, 1.0, 1.0) + Vec3(2.0, 2.0, 2.0))
    }

    @Test
    fun `minus operator should return a new Vec3 containing the difference between each component value`() {
        assertEquals(Vec3(2.0, 2.0, 2.0), Vec3(3.0, 3.0, 3.0) - Vec3(1.0, 1.0, 1.0))
    }

    @Test
    fun `times operator should return a new Vec3 containing the product of each component value`() {
        assertEquals(Vec3(9.0, 9.0, 9.0), Vec3(3.0, 3.0, 3.0) * Vec3(3.0, 3.0, 3.0))
    }

    @Test
    fun `div operator should return a new Vec3 containing the quotient of each component value`() {
        assertEquals(Vec3(3.0, 3.0, 3.0), Vec3(9.0, 9.0, 9.0) / Vec3(3.0, 3.0, 3.0))
    }

    @Test
    fun `times n operator should return a new Vec3 with each component multiplied by n`() {
        assertEquals(Vec3(9.0, 9.0, 9.0), Vec3(3.0, 3.0, 3.0) * 3.0)
    }

    @Test
    fun `div n operator should return a new Vec3 with each component divided by n`() {
        assertEquals(Vec3(3.0, 3.0, 3.0), Vec3(9.0, 9.0, 9.0) / 3.0)
    }

}