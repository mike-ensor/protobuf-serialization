package com.dev9.benchmark;

import org.openjdk.jmh.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JSONSerializationBenchmark {

    private static final Logger LOG = LoggerFactory.getLogger(JSONSerializationBenchmark.class);

    @Setup(Level.Trial)
    public void setup() {
        LOG.error("Setting up the code");
    }

    @Benchmark
    public void squareEachNumberOver100kIntegers() {
        LongStream.range(1, 100).forEach(i -> Math.multiplyExact(i, i));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public int simpleMathSummation() {
        int a = 1;
        int b = 2;
        int sum = a + b;

        return sum;
    }

}
