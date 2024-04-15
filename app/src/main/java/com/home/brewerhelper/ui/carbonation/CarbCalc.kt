package com.home.brewerhelper.ui.carbonation

import android.icu.math.BigDecimal

class CarbCalc {
    private val base: BigDecimal = BigDecimal("-16.6999")
    private val constantInEquationPartOne: BigDecimal = BigDecimal("0.0101059")
    private val constantInEquationPartTwo: BigDecimal = BigDecimal("0.00116512")
    private val constantInEquationPartThree: BigDecimal = BigDecimal("0.173354")
    private val constantInEquationPartFour: BigDecimal = BigDecimal("4.24267")
    private val constantInEquationPartFive: BigDecimal = BigDecimal("0.0684226")
    private val onePsiInBar: BigDecimal = BigDecimal("0.0689475729317831")
    private val multiplyConstInConverter: BigDecimal = BigDecimal("1.8")
    private val addConstInCelsiusConverter: BigDecimal = BigDecimal("32")

    fun calculator(tempC: Int, desiredCarb: Double): BigDecimal? {
        val pressureInBar = computeCarbonation(tempC, desiredCarb)
        return pressureInBar
    }

    fun computeCarbonation(tempC: Int, desiredCarb: Double): BigDecimal? {
        val desiredCarbBig = BigDecimal(desiredCarb)
        val temperatureFBig: BigDecimal = convertCtoFahrenheit(tempC)

        //original formula:
        // P = -16.6999 || - 0.0101059 T || + 0.00116512 T^2 || + 0.173354 T V || + 4.24267 V || - 0.0684226 V^2
        //      base    ||       I       ||         II       ||        III     ||       IV    ||        V
        val requiredPressureInPsiEquation: BigDecimal? = base
            .subtract(equationPartOne(temperatureFBig))
            .add(equationPartTwo(temperatureFBig))
            .add(equationPartThree(temperatureFBig, desiredCarbBig))
            .add(equationPartFour(desiredCarbBig))
            .subtract(equationPartFive(desiredCarbBig))
        return requiredPressureInPsiEquation?.let { convertPSItoBar(it).setScale(2) }
    }

    private fun equationPartOne(temperatureInFahrenheit: BigDecimal): BigDecimal {
        return constantInEquationPartOne.multiply(temperatureInFahrenheit)
    }

    private fun equationPartTwo(temperatureInFahrenheit: BigDecimal): BigDecimal {
        return constantInEquationPartTwo.multiply(temperatureInFahrenheit.pow(BigDecimal(2)))
    }

    private fun equationPartThree(
        temperatureInFahrenheit: BigDecimal,
        desiredCarbonation: BigDecimal
    ): BigDecimal {
        return constantInEquationPartThree.multiply(temperatureInFahrenheit)
            .multiply(desiredCarbonation)
    }

    private fun equationPartFour(desiredCarbonation: BigDecimal): BigDecimal {
        return constantInEquationPartFour.multiply(desiredCarbonation)
    }

    private fun equationPartFive(desiredCarbonation: BigDecimal): BigDecimal {
        return constantInEquationPartFive.multiply(desiredCarbonation.pow(BigDecimal(2)))
    }

    private fun convertPSItoBar(pressureInBar: BigDecimal): BigDecimal {
        return pressureInBar.multiply(onePsiInBar)
    }

    private fun convertCtoFahrenheit(temperatureInC: Int): BigDecimal {
        val bigDecimalTemp = BigDecimal(temperatureInC)
        return bigDecimalTemp.multiply(multiplyConstInConverter).add(addConstInCelsiusConverter)
    }
}