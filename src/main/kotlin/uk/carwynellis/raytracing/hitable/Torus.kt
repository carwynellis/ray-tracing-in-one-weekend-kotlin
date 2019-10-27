package uk.carwynellis.raytracing.hitable

import uk.carwynellis.raytracing.HitRecord
import uk.carwynellis.raytracing.Ray
import uk.carwynellis.raytracing.Vec3
import uk.carwynellis.raytracing.material.Material
import uk.carwynellis.raytracing.math.SolveQuartic
import kotlin.math.sqrt

class Torus(
    private val centre: Vec3,
    private val innerRadius: Double, // major radius
    private val crossSectionRadius: Double, // minor radius
    private val material: Material
) : Hitable {

    override fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord? {

        // From http://www.cosinekitty.com/raytrace
        //
        // Determine the coefficients of the quartic that represents ray intersection of the torus.
        // For a torus of major radius A, minor radius B intersecting a ray of origin D with direction E and
        // intersection point u we have the following equation
        //  J^2u^4 + 2JKu^3 + (2JL + K^2 -G)u^2 + 2(KL - H)u + (L^2 - I) = 0
        // where
        //  G = 4A^2(Ex^2 + Ey^2)
        //  H = 8A^2(DxEx + DyEy)
        //  I = 4A^2(Dx^2 + Dy^2)
        //  J = |E|^2
        //  K = 2(D.E)
        //  L = |D|^2 + (A^2 - B^2)

        // Firstly compute the factors above
        val G = 4.0 * (innerRadius * innerRadius) * ((r.direction.x * r.direction.x) + (r.direction.y * r.direction.y))
        val H = 8.0 * (innerRadius * innerRadius) * ((r.direction.x * r.origin.x) + (r.direction.y * r.origin.y))
        val I = 4.0 * (innerRadius * innerRadius) * ((r.origin.x * r.origin.x) + (r.origin.y * r.origin.y))
        val J = r.direction.squaredLength()
        val K = 2 * (r.origin dot r.direction)
        val L = r.origin.squaredLength() + ((innerRadius * innerRadius) - (crossSectionRadius * crossSectionRadius))

        // Solve the quartic by specifying the coefficients as above
        val roots = SolveQuartic(
            a = (L * L) - I,
            b = (2.0 * (K * L)) - H,
            c = (2.0 * J * L) + (K * K) - G,
            d = 2.0 * J * K,
            e = J * J
        )

        // Assuming that the roots must be > tMin and < tMax
        val validRoots = roots.filter { it > tMin && it < tMax }

        return if (validRoots.size > 0) {
            // TODO - how to determine closest hit
            val t = validRoots.min()!!
            HitRecord(
                t = t,
                p = r.pointAtParameter(t),
                // TODO - this normal is for a sphere so needs fixing....
                 normal = (r.pointAtParameter(t) - centre) / innerRadius,
                material = material
            )
        }
        else null
    }

}