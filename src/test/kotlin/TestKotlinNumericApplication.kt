import dk.alexandra.fresco.framework.Application
import dk.alexandra.fresco.framework.DRes
import dk.alexandra.fresco.framework.TestThreadRunner
import dk.alexandra.fresco.framework.builder.numeric.ProtocolBuilderNumeric
import dk.alexandra.fresco.framework.sce.evaluator.EvaluationStrategy
import dk.alexandra.fresco.framework.sce.resources.ResourcePool
import dk.alexandra.fresco.framework.value.SInt
import dk.alexandra.fresco.kotlin.KotlinNumericApplication
import dk.alexandra.fresco.suite.dummy.arithmetic.AbstractDummyArithmeticTest
import dk.alexandra.fresco.suite.dummy.arithmetic.DummyArithmeticResourcePool
import org.junit.Assert
import org.junit.Test
import java.lang.Exception
import java.math.BigInteger

class TestKotlinNumericApplication : AbstractDummyArithmeticTest(){

    @Test
    fun testKotlinApp() {
        runTest(TestNumeric<DummyArithmeticResourcePool>(), EvaluationStrategy.SEQUENTIAL, 2)
    }
}

class TestNumeric<ResourcePoolT : ResourcePool> : TestThreadRunner.TestThreadFactory<ResourcePoolT, ProtocolBuilderNumeric>(){

    override fun next() : TestThreadRunner.TestThread<ResourcePoolT, ProtocolBuilderNumeric> {
        return object : TestThreadRunner.TestThread<ResourcePoolT, ProtocolBuilderNumeric>() {
            @Throws(Exception::class)
            override fun test() {
                val app = Application<BigInteger, ProtocolBuilderNumeric> {
                    object : KotlinNumericApplication<BigInteger>(it) {
                        override fun buildKotlinApplication(): DRes<BigInteger> {
                            val x = input(2, 1)
                            val y = input(3, 1)

                            val l1 = ArrayList<DRes<SInt>>()
                            l1.add(x)
                            val l2 = ArrayList<DRes<SInt>>()
                            l2.add(y)
                            val l = l1+l2

                            var z = l[0]
                            z *= z
                            z /= 2
                            z -= 4

                            return open(z)
                        }
                    }.buildKotlinApplication()
                }
                val output = runApplication(app)

                var res = 2+3
                res *= res
                res /= 2
                res -= 4
                Assert.assertEquals(BigInteger.valueOf(res.toLong()), output)
            }
        }
    }
}