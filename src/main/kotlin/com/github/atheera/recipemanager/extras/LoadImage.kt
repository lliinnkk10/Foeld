package com.github.atheera.recipemanager.extras

import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO

class LoadImage {

    fun loadImage(imageName: String) : BufferedImage? {
        val url = (this.javaClass.getResource("/images/$imageName"))
        try {
            return ImageIO.read(url)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

}