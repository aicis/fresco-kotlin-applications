import dk.alexandra.fresco.framework.Application
import dk.alexandra.fresco.framework.DRes
import dk.alexandra.fresco.framework.TestThreadRunner
import dk.alexandra.fresco.framework.builder.binary.ProtocolBuilderBinary
import dk.alexandra.fresco.framework.sce.evaluator.EvaluationStrategy
import dk.alexandra.fresco.framework.sce.resources.ResourcePool
import dk.alexandra.fresco.framework.sce.resources.ResourcePoolImpl
import dk.alexandra.fresco.framework.value.SBool
import dk.alexandra.fresco.kotlin.KotlinBinaryApplication
import dk.alexandra.fresco.suite.dummy.bool.AbstractDummyBooleanTest
import org.junit.Assert
import org.junit.Test
import java.lang.Exception
import kotlin.collections.ArrayList

class TestKotlinBinaryApplication : AbstractDummyBooleanTest(){

    @Test
    fun testKotlinApp() {
        runTest(TestBinary<ResourcePoolImpl>(), EvaluationStrategy.SEQUENTIAL)
    }
}

class TestBinary<ResourcePoolT : ResourcePool> : TestThreadRunner.TestThreadFactory<ResourcePoolT, ProtocolBuilderBinary>(){

    override fun next() : TestThreadRunner.TestThread<ResourcePoolT, ProtocolBuilderBinary> {
        return object : TestThreadRunner.TestThread<ResourcePoolT, ProtocolBuilderBinary>() {
            @Throws(Exception::class)
            override fun test() {
                val app = Application<List<Boolean>, ProtocolBuilderBinary> {
                        it.seq({var res = object : KotlinBinaryApplication<List<DRes<SBool>>>(it) {
                            override fun buildKotlinApplication(): DRes<List<DRes<SBool>>> {
                                val x = input(true, 1)
                                val y = input(false, 1)
                                var z = x xor y //true
                                var k = x and y // false

                                z = z xnor k //false
                                z = z and true // false
                                z = !z // true

                                val res = ArrayList<DRes<SBool>>()
                                res.add(k)
                                res.add(z)
                                return DRes {
                                    res
                                }
                            }
                        }.buildKotlinApplication()
                        res}).seq({b, t ->
                            val res = object : KotlinBinaryApplication<List<DRes<Boolean>>>(b) {
                                override fun buildKotlinApplication(): DRes<List<DRes<Boolean>>> {
                                    val opened = t.map { open(it) }
                                    return DRes{opened}
                                }
                            }
                            res.buildKotlinApplication()
                        }).seq({_, opened ->
                            DRes {opened.map { it.out() }}
                        })
                }
                val output = runApplication(app)
                println(output);

                Assert.assertFalse(output[0])
                Assert.assertTrue(output[1])
            }
        }
    }
}