package uk.carwynellis.raytracing

import uk.carwynellis.raytracing.Vec3.Operators.times

/**
 * Main entrypoint that will render a scene and write it to a file.
 */
fun main() {
    val width = 1200
    val height = 800
    val samples = 10

    val scene = Scene.finalScene

    val origin = Vec3(13.0, 2.0, 3.0)
    val target = Vec3(0.0, 0.0, 0.0)

    val camera = Camera(
        origin = origin,
        target = target,
        upVector = Vec3(0.0, 1.0, 0.0),
        verticalFieldOfView = 20.0,
        aspectRatio = width.toDouble() / height.toDouble(),
        aperture = 0.1,
        focusDistance = 10.0
    )

    val filename = "image.ppm"

    val renderer = Renderer(camera, scene, width, height, samples)

    print("Rendering scene -   0% complete")

    val imageData = renderer.renderScenePar()

    println("Writing file: $filename...")

    ImageWriter(width, height, filename).writeImageData(imageData)

    println("Done.")
}
