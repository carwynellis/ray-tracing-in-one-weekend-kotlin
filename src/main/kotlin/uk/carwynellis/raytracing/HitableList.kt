package uk.carwynellis.raytracing

class HitableList(private val hitables: List<Hitable>) : Hitable {

    override fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord? =
        hitables.fold<Hitable, HitRecord?>(null, { acc: HitRecord?, h: Hitable ->
            val closest: Double = acc?.t ?: tMax
            when(val hitResult = h.hit(r, tMin, closest)) {
                is HitRecord -> hitResult
                else -> acc
            }
        })

}