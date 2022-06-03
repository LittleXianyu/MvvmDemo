package com.example.mvvmdemo.kotlin

import android.util.Log
import kotlinx.coroutines.*
import kotlin.reflect.KProperty
import kotlin.system.measureTimeMillis

/*
 * https://www.kotlincn.net/docs/reference/coroutines/coroutines-guide.html
* 协程是一种并发设计模式，线程框架
* 目的是让异步代码可以像同步代码一样书写。用到的关键字：
* delay，await，async，runBlocking，launch
 */

/**
 * delay 是非阻塞的，
 * runBlocking是阻塞的（阻塞线程运行），用于测试,桥接阻塞代码与挂起代码之前的桥梁，其函数本身是阻塞的
 * join也是阻塞的，
 * coroutineScope 创建独立作用域，（类似runBlocking，不阻塞线程）
 * launch 开启一个协程
 */
fun demo1() {
    GlobalScope.launch { // 在后台启动一个新的协程并继续
        delay(1000L) // 非阻塞的等待 1 秒钟（默认时间单位是毫秒）
        println("xianyu World!") // 在延迟后打印输出
    }
    println("xianyu Hello,") // 协程已在等待时主线程还在继续
}

fun demo2() = runBlocking<Unit> { // 开始执行主协程
    launch { // 在后台启动一个新的协程并继续
        delay(1000L)
        println("World!")
    }
    println("Hello,") // 主协程在这里会立即执行
    delay(2000L) // 延迟 2 秒来保证 JVM 存活
}

suspend fun demo3() = runBlocking {
    val job = launch { // 启动一个新协程并保持对这个作业的引用
        delay(1000L)
        Log.d("xianyu", "before delay!")
    }
    Log.d("xianyu", "after delay !")
    job.join() // 等待直到子协程执行结束
}

fun demoFor3() = runBlocking {
    launch { // 启动一个新协程并保持对这个作业的引用
        demo3()
        Log.d("xianyu", "demo3 end !")
    }
}

fun demo4() = runBlocking { // this: CoroutineScope
    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    coroutineScope { // 创建一个协程作用域
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
    }

    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
}

/**
 * 协程可以被取消
 */
fun demo5() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
        }
    }
    delay(1300L) // 延迟一段时间
    println("main: I'm tired of waiting!")
    job.cancel() // 取消该作业
    job.join() // 等待作业执行结束
    println("main: Now I can quit.")
}

/**
 * 协程中的计算使用isActive才能被取消
 * withTimeout，超时后取消
 */
fun demo6() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // 可以被取消的计算循环
            // 每秒打印消息两次
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(1300L) // 等待一段时间
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // 取消该作业并等待它结束
    println("main: Now I can quit.")
}
suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了一些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}
fun demo7() = runBlocking {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}
// async 风格的函数
// somethingUsefulOneAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

// somethingUsefulTwoAsync 函数的返回值类型是 Deferred<Int>
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}
// 注意，在这个示例中我们在 `demo8` 函数的右边没有加上 `runBlocking`
fun demo8() {
    val time = measureTimeMillis {
        // 我们可以在协程外面启动异步执行
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // 但是等待结果必须调用其它的挂起或者阻塞
        // 当我们等待结果的时候，这里我们使用 `runBlocking { …… }` 来阻塞主线程
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

// 使用 async 的结构化并发, 一损俱损，协程作用域内的子协程有一个异常了，整个协程所有子协程都取消
fun demo9() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async<Int> {
        try {
            delay(Long.MAX_VALUE) // 模拟一个长时间的运算
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}
fun println(str: String) {
    Log.d("xianyu", str)
}

class RetryIntercepter(i: String) {
    var mm: String
    init {
        mm = i
    }
    fun print1() {
        Log.d("xianyu", mm)
    }
}

// 扩展函数
fun String.printFunctionType() { System.out.println("Extension function") }
// 带有接收者的函数
val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }

// 函数对象
fun b(i: Int): String {
    return "100" + i
}
fun useFun(fun1: (Int) -> String, s: String) {
    System.out.println(fun1(1))
}
val useFun1 = { x: Int -> "1" + 1 }

/**
 * 类委托： 一个类的方法不在该类中定义，而是直接委托给另一个对象来处理。
属性委托： 一个类的属性不在该类中定义，而是直接委托给另一个对象来处理。
局部变量委托： 一个局部变量不在该方法中定义，而是直接委托给另一个对象来处理。
 */
interface Base {
    fun call()
}
class ABase() : Base {
    override fun call() {
        System.out.println("call ABase")
    }
}
class BBase(b: Base) : Base by b

// 属性委托
class Example {
    var p: String by Delegate()
}

class Delegate {
    operator fun getValue(thisRef: Example, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Example, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}
fun main(args: Array<String>) {
//    val m1 = RetryIntercepter("100")
//    m1.print1()
//    println("Hello World!")
//    useBbb(::bbb, 1)

//    val s = "int"
//    s.printFunctionType()
//    System.out.println(s.repeatFun(2))
//    useFun(::b, "ss") // ::函数对象
//    useFun({ i -> "100" }, "ss")
//    useFun1(1)
//    val aBase = ABase()
//    BBase(aBase).call()
//
//    val e = Example()
//    System.out.println(e.p)
    val m: () -> String = {
        "ss"
    }
    m.invoke()
}
