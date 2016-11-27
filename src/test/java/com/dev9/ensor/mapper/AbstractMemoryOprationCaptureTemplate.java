package com.dev9.ensor.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractMemoryOprationCaptureTemplate implements MemoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractMemoryOprationCaptureTemplate.class);

    private Runtime runtime;

    AbstractMemoryOprationCaptureTemplate() {
        this.runtime = Runtime.getRuntime();
    }

    protected void logMemoryUsage(long memory, long after, final String Message) {
        LOG.error(Message + " = {} bytes", bytesToMegabytes((after - memory)));
    }

    protected long getMemoryAfterOperation() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    protected long getInitialMemory() {
        runtime.gc();
        return runtime.totalMemory() - runtime.freeMemory();
    }

}
