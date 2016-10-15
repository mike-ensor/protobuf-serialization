package com.dev9.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JSONSerializationBenchmark {

    @Setup
    public void setup() {

    }

    @Benchmark
    public void squareEachNumberOver100kIntegers() {
        LongStream.range(1, 100).forEach(i -> Math.multiplyExact(i, i));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void simpleMathSummation() {
        int a = 1;
        int b = 2;
        int sum = a + b;
    }

}
