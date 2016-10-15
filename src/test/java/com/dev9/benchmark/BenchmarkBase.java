package com.dev9.benchmark;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkBase {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*Benchmark.*")
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(1)
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.TEXT)
                .jvmArgs("-server")
                .build();

        new Runner(opt).run();
//        Collection<RunResult> run = new Runner(opt).run();

//        System.out.println();
//        for (RunResult runResult : run) {
//            Result r = runResult.getPrimaryResult();
//            System.out.printf("Results benchmark score [%s]: %s %s over %d iterations%n", r.getLabel(), r.getScore(), r.getScoreUnit(), r.getStatistics().getN());
//        }

    }

}
