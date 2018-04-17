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
import dk.alexandra.fresco.lib.real.SReal
import java.math.BigDecimal
import dk.alexandra.fresco.kotlin.KotlinFixedPointApplication

class TestKotlinFixedPointApplication : AbstractDummyArithmeticTest(){

    @Test
    fun testKotlinApp() {
        runTest(TestFixedPoint<DummyArithmeticResourcePool>(), EvaluationStrategy.SEQUENTIAL, 2)
    }
}

class TestFixedPoint<ResourcePoolT : ResourcePool> : TestThreadRunner.TestThreadFactory<ResourcePoolT, ProtocolBuilderNumeric>(){

    override fun next() : TestThreadRunner.TestThread<ResourcePoolT, ProtocolBuilderNumeric> {
        return object : TestThreadRunner.TestThread<ResourcePoolT, ProtocolBuilderNumeric>() {
            @Throws(Exception::class)
            override fun test() {
                val app = Application<BigDecimal, ProtocolBuilderNumeric> {
                    object : KotlinFixedPointApplication<BigDecimal>(it) {
                        override fun buildKotlinApplication(): DRes<BigDecimal> {
							
                            val x = input(2.2, 1)
                            val y = input(3.1, 2)

                            var z = x*y + 1.1

                            return open(z)
							
                        }
                    }.buildKotlinApplication()
                }
                val output = runApplication(app)

                var res = 2.2*3.1+1.1
                Assert.assertTrue(Math.abs(res - output.toDouble()) < 0.01)
            }
        }
    }
}