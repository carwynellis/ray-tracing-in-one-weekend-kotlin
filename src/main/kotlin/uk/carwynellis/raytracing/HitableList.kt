package uk.carwynellis.raytracing

class HitableList(val hitables: List<Hitable>) : Hitable {

    override fun hit(r: Ray, tMin: Double, tMax: Double): HitRecord? {
        tailrec fun loop(hs: List<Hitable>, closest: Double , hitAnything: Boolean, record: HitRecord?): HitRecord? {
            return if (hs.isNotEmpty()) {
                when(val hitResult = hs.first().hit(r, tMin, closest)) {
                    is HitRecord -> loop(hs.subList(1, hs.size), hitResult.t, true, hitResult)
                    else -> loop(hs.subList(1, hs.size), closest, hitAnything, record)
                }
            }
            else record
        }
        return loop(hitables, tMax, false, null)
    }

}