package com.example.project

import com.github.cage.Cage
import com.github.cage.ObjectRoulette
import com.github.cage.image.EffectConfig
import com.github.cage.image.Painter
import java.awt.Font
import java.io.File
import java.io.FileOutputStream

import java.io.OutputStream
import java.nio.charset.Charset
import java.nio.file.Paths
import java.time.Instant
import java.util.*

class Captcha {
    fun casCage(): Cage {
        val effectConfig = EffectConfig(true, false, false, false, null)
        val painter = Painter(400, 140, null, Painter.Quality.MAX, effectConfig, null)
        val defFontHeight = painter.height / 2
        val fonts = ObjectRoulette(
            Random(),
            Font("SimSun", Font.PLAIN, defFontHeight),
            Font("SimHei", Font.PLAIN, defFontHeight),
        )
        val cage = Cage(painter, fonts, null, "png", 1f, null, null)
        return cage
    }

    fun captchaStr(): String {
        val ops = charArrayOf('+', '-', '*')
        val eq = '='
        val op = ops.random()
        val num1: Int = (0..9).random()
        val num2: Int = (0..9).random()
        val filename = "${num1}${op}${num2}${eq}"
        return filename
    }

    fun filename(captchaStr: String): String {
        val filepath = "./datasets/train/"
        val timestamp = Instant.now().toEpochMilli()
        val filesuffix = ".png"
        // 目录不存在则创建
        if (!File(filepath).exists()) {
            File(filepath).mkdirs()
        }
        // 为了文件名合法
        val filename = captchaStr.replace("=", "")
            .replace("+", "add")
            .replace("-", "sub")
            .replace("*", "mul")
        return "${filepath}${filename}_${timestamp}${filesuffix}"
    }

    fun writeImage(cage: Cage, captchaStr: String) {
        val realCaptcha = captchaStr
            .replace("+", arrayOf("+", "加").random())
            .replace("-", arrayOf("-", "减").random())
            .replace("*", arrayOf("×", "x", "乘").random())
            .replace("=", arrayOf("=", "等于").random())
        val os: OutputStream =
            FileOutputStream(
                Paths.get(filename(captchaStr)).toString(),
                false
            )
        cage.draw(realCaptcha, os)
    }
}
