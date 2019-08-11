package uk.carwynellis.raytracing.material

import uk.carwynellis.raytracing.HitRecord
import uk.carwynellis.raytracing.Ray
import uk.carwynellis.raytracing.Vec3
import java.lang.Math.pow
import kotlin.math.sqrt
import kotlin.random.Random

import uk.carwynellis.raytracing.Vec3.Operators.times
import uk.carwynellis.raytracing.Vec3.Operators.unaryMinus

class Dielectric(private val refractiveIndex: Double) : Material(Vec3(1.0, 1.0, 1.0)) {

    override fun scatter(rayIn: Ray, record: HitRecord): Ray {
        val reflected = reflect(rayIn.direction,record.normal)

        val (outwardNormal, niOverNt, cosine) = if (rayIn.direction.dot(record.normal) > 0)
            Triple(-record.normal, refractiveIndex, refractiveIndex * rayIn.direction.dot(record.normal) / rayIn.direction.length())
        else
            Triple(record.normal, 1.0 / refractiveIndex, -rayIn.direction.dot(record.normal) / rayIn.direction.length())

        val refracted = refract(rayIn.direction, outwardNormal, niOverNt)

        val reflectionProbability =
            if (refracted == rayIn.direction) 1.0
            else schlick(cosine)

        return if (Random.nextDouble() < reflectionProbability) Ray(record.p, reflected)
        else Ray(record.p, refracted)
    }

    private fun refract(v: Vec3, n: Vec3, niOverNt: Double): Vec3 {
        val unitVectorOfV = v.unitVector()
        val dt = unitVectorOfV.dot(n)
        val discriminant = 1.0 - (niOverNt * niOverNt * (1 - (dt * dt)))

        return if (discriminant > 0) (niOverNt * (unitVectorOfV - (n * dt))) - (n * sqrt(discriminant))
        else v
    }

    // Polynomial approximation for glass reflectivity.
    private fun schlick(cosine: Double): Double {
        val r0 = (1.0 - refractiveIndex) / (1.0 + refractiveIndex)
        val r0Squared = r0 * r0
        return r0Squared + (1.0 - r0Squared) * pow(1.0 - cosine, 5.0)
    }
}