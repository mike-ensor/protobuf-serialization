package com.dev9.benchmark;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@SpringBootApplication
public class BenchmarkBase {

    public static void main(String[] args) throws RunnerException, IOException {

        Properties properties = PropertiesLoaderUtils.loadAllProperties("benchmark.properties");

        int warmup = Integer.parseInt(properties.getProperty("benchmark.warmup.iterations", "5"));
        int iterations = Integer.parseInt(properties.getProperty("benchmark.test.iterations", "5"));
        int forks = Integer.parseInt(properties.getProperty("benchmark.test.forks", "1"));
        int threads = Integer.parseInt(properties.getProperty("benchmark.test.threads", "1"));

        Options opt = new OptionsBuilder()
                .include(".*Benchmark.*")
                .warmupIterations(warmup)
                .measurementIterations(iterations)
                .forks(forks)
                .threads(threads)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                .result(getFilename())
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(opt).run();
    }

    private static String getFilename() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm-dd-yyyy-hh-mm-ss");
        return "target/jhm-" + date.format(formatter) + ".json";
    }

}
