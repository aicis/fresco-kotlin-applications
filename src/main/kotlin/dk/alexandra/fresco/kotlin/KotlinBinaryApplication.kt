package dk.alexandra.fresco.kotlin

import dk.alexandra.fresco.framework.DRes
import dk.alexandra.fresco.framework.builder.binary.ProtocolBuilderBinary
import dk.alexandra.fresco.framework.value.SBool

abstract class KotlinBinaryApplication<OutputT>(builder: ProtocolBuilderBinary){

    private val bin = builder.binary()
    private val adv = builder.advancedBinary()

    infix fun DRes<SBool>.xor(x: DRes<SBool>) : DRes<SBool> {
        return bin.xor(this, x)
    }

    infix fun DRes<SBool>.and(x: DRes<SBool>) : DRes<SBool> {
        return bin.and(this, x)
    }

    infix fun DRes<SBool>.and(x: Boolean) : DRes<SBool> {
        return adv.and(this, x)
    }

    infix fun DRes<SBool>.or(x: DRes<SBool>) : DRes<SBool> {
        return adv.or(this, x)
    }

    infix fun DRes<SBool>.or(x: Boolean) : DRes<SBool> {
        return adv.or(this, x)
    }

    infix fun DRes<SBool>.nand(x: DRes<SBool>) : DRes<SBool> {
        return adv.nand(this, x)
    }

    infix fun DRes<SBool>.nand(x: Boolean) : DRes<SBool> {
        return adv.nand(this, x)
    }

    infix fun DRes<SBool>.xnor(x: DRes<SBool>) : DRes<SBool> {
        return adv.xnor(this, x)
    }

    infix fun DRes<SBool>.xnor(x: Boolean) : DRes<SBool> {
        return adv.xnor(this, x)
    }

    operator fun DRes<SBool>.not() : DRes<SBool> {
        return bin.not(this);
    }

    fun input(value: Boolean, inputParty: Int) : DRes<SBool>{
        return bin.input(value, inputParty);
    }

    fun open(value: DRes<SBool>) : DRes<Boolean> {
        return bin.open(value)
    }

    fun open(vararg value: DRes<SBool>) : List<DRes<Boolean>> {
        var res = ArrayList<DRes<Boolean>>()
        value.forEach { res.add(bin.open(it)) }
        return res
    }

    abstract fun buildKotlinApplication() : DRes<OutputT>
}