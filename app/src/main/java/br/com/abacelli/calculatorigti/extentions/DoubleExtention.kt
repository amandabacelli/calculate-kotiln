package br.com.abacelli.calculatorigti.extentions

fun Double.roundTwoDigits(): Double {
    return Math.round(this * 100.0) / 100.0
}