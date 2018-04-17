package dk.alexandra.fresco.kotlin

import dk.alexandra.fresco.framework.DRes
import dk.alexandra.fresco.framework.builder.numeric.ProtocolBuilderNumeric
import dk.alexandra.fresco.framework.value.SInt
import dk.alexandra.fresco.lib.real.SReal
import java.math.BigInteger
import java.math.BigDecimal

/**
 * This class eases the writing of applications in FRESCO. Create a new instance of this class,
 * and all the methods and overloaded operators are available to you within the buildKotlinApplication method.
 *
 * Currently supported for DRes<SReal> is:
 * - standard operators: +, -, *, /
 * - new operators: leq, gt
 * - methods: input, open
 */
abstract class KotlinFixedPointApplication<OutputT>(builder: ProtocolBuilderNumeric) {

	private val num = builder.realNumeric()

	//SReal operation overloads

	operator fun DRes<SReal>.plus(add: DRes<SReal>): DRes<SReal> {
		return num.add(add, this)
	}

	operator fun DRes<SReal>.plus(add: BigDecimal): DRes<SReal> {
		return num.add(add, this)
	}

	operator fun DRes<SReal>.plus(add: Double): DRes<SReal> {
		return num.add(BigDecimal.valueOf(add), this)
	}

	operator fun DRes<SReal>.minus(sub: DRes<SReal>): DRes<SReal> {
		return num.sub(this, sub)
	}

	operator fun DRes<SReal>.minus(sub: BigDecimal): DRes<SReal> {
		return num.sub(this, sub)
	}

	operator fun DRes<SReal>.minus(sub: Double): DRes<SReal> {
		return num.sub(this, BigDecimal.valueOf(sub))
	}

	operator fun DRes<SReal>.times(multiplier: DRes<SReal>): DRes<SReal> {
		return num.mult(multiplier, this)
	}

	operator fun DRes<SReal>.times(multiplier: BigDecimal): DRes<SReal> {
		return num.mult(multiplier, this)
	}

	operator fun DRes<SReal>.times(multiplier: Double): DRes<SReal> {
		return num.mult(BigDecimal.valueOf(multiplier), this)
	}

	operator fun DRes<SReal>.div(divisor: DRes<SReal>): DRes<SReal> {
		return num.div(this, divisor)
	}

	operator fun DRes<SReal>.div(divisor: BigDecimal): DRes<SReal> {
		return num.div(this, divisor)
	}

	operator fun DRes<SReal>.div(divisor: Double): DRes<SReal> {
		return num.div(this, BigDecimal.valueOf(divisor))
	}

	//Custom operators for comparison.

	infix fun DRes<SReal>.lteq(toComp: DRes<SReal>): DRes<SInt> {
		return num.leq(this, toComp)
	}

	infix fun DRes<SReal>.gt(toComp: DRes<SReal>): DRes<SInt> {
		return num.leq(toComp, this)
	}

	//List operation overloads

	operator fun List<DRes<SReal>>.plus(add: List<DRes<SReal>>): List<DRes<SReal>> {
		if (this.size != add.size) {
			throw IllegalArgumentException("List sizes must match")
		}
		var res = ArrayList<DRes<SReal>>()
		for (i in this.indices) {
			res.add(num.add(this[i], add[i]))
		}
		return res
	}

	operator fun List<DRes<SReal>>.minus(add: List<DRes<SReal>>): List<DRes<SReal>> {
		if (this.size != add.size) {
			throw IllegalArgumentException("List sizes must match")
		}
		var res = ArrayList<DRes<SReal>>()
		for (i in this.indices) {
			res.add(num.sub(this[i], add[i]))
		}
		return res
	}

	operator fun List<DRes<SReal>>.times(add: List<DRes<SReal>>): List<DRes<SReal>> {
		if (this.size != add.size) {
			throw IllegalArgumentException("List sizes must match")
		}
		var res = ArrayList<DRes<SReal>>()
		for (i in this.indices) {
			res.add(num.mult(this[i], add[i]))
		}
		return res
	}

	//Ease of use functionality such as input and open values.

	fun input(value: Double, inputParty: Int): DRes<SReal> {
		return num.input(BigDecimal.valueOf(value), inputParty)
	}

	fun input(values: List<Double>, inputParty: Int): List<DRes<SReal>> {
		return values.map { num.input(BigDecimal.valueOf(it), inputParty) }
	}

	fun open(value: DRes<SReal>): DRes<BigDecimal> {
		return num.open(value)
	}

	fun open(values: List<DRes<SReal>>): List<DRes<BigDecimal>> {
		return values.map { num.open(it) }
	}

	abstract fun buildKotlinApplication(): DRes<OutputT>
}