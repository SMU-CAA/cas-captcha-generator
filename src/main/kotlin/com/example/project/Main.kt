package com.example.project

fun main(args: Array<String>) {
    val num = if (args.size == 1) args[0].toInt() else 10
    val captcha = Captcha()
    for (i in 1..num) {
        captcha.writeImage(captcha.casCage(), captcha.captchaStr())
    }
}
