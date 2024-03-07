package banquemisr.challenge05.utils

fun Double.formatToOneDecimalPlace(): Double {
    return String.format("%.1f", this).toDouble()
}
