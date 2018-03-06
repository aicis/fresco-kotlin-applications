package dk.alexandra.fresco.kotlin

import dk.alexandra.fresco.framework.DRes
import dk.alexandra.fresco.framework.builder.numeric.ProtocolBuilderNumeric
import dk.alexandra.fresco.framework.value.SInt
import java.math.BigInteger

/**
 * This class eases the writing of applications in FRESCO. Create a new instance of this class,
 * and all the methods and overloaded operators are available to you within the buildKotlinApplication method.
 *
 * Currently supported for DRes<SInt> is:
 * - standard operators: +, -, *, /
 * - new operators: eq, leq, gt
 * - methods: input, open
 *
 * Currently supported for List<DRes<SInt>>:
 * - Standard operators: +, -, * (vector operations)
 * - methods: input, open
 */
abstract class KotlinNumericApplication<OutputT>(private val builder: ProtocolBuilderNumeric){

    private val num = builder.numeric()

    //SInt operation overloads

    operator fun DRes<SInt>.plus(add: DRes<SInt>): DRes<SInt> {
        return num.add(add, this)
    }

    operator fun DRes<SInt>.plus(add: BigInteger): DRes<SInt> {
        return num.add(add, this)
    }

    operator fun DRes<SInt>.plus(add: Long): DRes<SInt> {
        return num.add(BigInteger.valueOf(add), this)
    }

    operator fun DRes<SInt>.minus(sub: DRes<SInt>): DRes<SInt> {
        return num.sub(this, sub)
    }

    operator fun DRes<SInt>.minus(sub: BigInteger): DRes<SInt> {
        return num.sub(this, sub)
    }

    operator fun DRes<SInt>.minus(sub: Long): DRes<SInt> {
        return num.sub(this, BigInteger.valueOf(sub))
    }

    operator fun DRes<SInt>.times(multiplier: DRes<SInt>): DRes<SInt> {
        return num.mult(multiplier, this)
    }

    operator fun DRes<SInt>.times(multiplier: BigInteger): DRes<SInt> {
        return num.mult(multiplier, this)
    }

    operator fun DRes<SInt>.times(multiplier: Long): DRes<SInt> {
        return num.mult(BigInteger.valueOf(multiplier), this)
    }

    operator fun DRes<SInt>.div(divisor: DRes<SInt>): DRes<SInt> {
        return builder.advancedNumeric().div(this, divisor)
    }

    operator fun DRes<SInt>.div(divisor: BigInteger): DRes<SInt> {
        return builder.advancedNumeric().div(this, divisor)
    }

    operator fun DRes<SInt>.div(divisor: Long): DRes<SInt> {
        return builder.advancedNumeric().div(this, BigInteger.valueOf(divisor))
    }

    //Custom operators for comparison.

    infix fun DRes<SInt>.eq(toComp: DRes<SInt>) : DRes<SInt> {
        return builder.comparison().equals(this, toComp)
    }

    infix fun DRes<SInt>.lteq(toComp: DRes<SInt>) : DRes<SInt> {
        return builder.comparison().compareLEQ(this, toComp)
    }

    infix fun DRes<SInt>.gt(toComp: DRes<SInt>) : DRes<SInt> {
        return builder.comparison().compareLEQ(toComp, this)
    }

    //List operation overloads

    operator fun List<DRes<SInt>>.plus(add: List<DRes<SInt>>): List<DRes<SInt>> {
        if(this.size != add.size){
            throw IllegalArgumentException("List sizes must match")
        }
        var res = ArrayList<DRes<SInt>>()
        for(i in this.indices) {
            res.add(num.add(this[i], add[i]))
        }
        return res
    }

    operator fun List<DRes<SInt>>.minus(add: List<DRes<SInt>>): List<DRes<SInt>> {
        if(this.size != add.size){
            throw IllegalArgumentException("List sizes must match")
        }
        var res = ArrayList<DRes<SInt>>()
        for(i in this.indices) {
            res.add(num.sub(this[i], add[i]))
        }
        return res
    }

    operator fun List<DRes<SInt>>.times(add: List<DRes<SInt>>): List<DRes<SInt>> {
        if(this.size != add.size){
            throw IllegalArgumentException("List sizes must match")
        }
        var res = ArrayList<DRes<SInt>>()
        for(i in this.indices) {
            res.add(num.mult(this[i], add[i]))
        }
        return res
    }

    //Ease of use functionality such as input and open values.

    fun input(value: Long, inputParty: Int) : DRes<SInt> {
        return num.input(BigInteger.valueOf(value), inputParty)
    }

    fun input(values: List<Long>, inputParty: Int) : List<DRes<SInt>> {
        return values.map { num.input(BigInteger.valueOf(it), inputParty) }
    }

    fun open(value: DRes<SInt>) : DRes<BigInteger> {
        return num.open(value)
    }

    fun open(values: List<DRes<SInt>>) : List<DRes<BigInteger>> {
        return values.map { num.open(it) }
    }

    abstract fun buildKotlinApplication() : DRes<OutputT>
}