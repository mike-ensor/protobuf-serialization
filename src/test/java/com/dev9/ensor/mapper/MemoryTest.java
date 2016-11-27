package com.dev9.ensor.mapper;

public interface MemoryTest {
    long MEGABYTE = 1024L * 1024L;

    default long bytesToMegabytes(long bytes) {
        return bytes;
//        return bytes / MEGABYTE;
    }
}
