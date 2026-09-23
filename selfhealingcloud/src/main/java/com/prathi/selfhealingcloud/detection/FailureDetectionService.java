package com.prathi.selfhealingcloud.detection;

import com.prathi.selfhealingcloud.monitoring.SystemMetrics;

public class FailureDetectionService {

    private static final double CPU_THRESHOLD = 80.0;
    private static final double MEMORY_THRESHOLD = 80.0;
    private static final double DISK_THRESHOLD = 90.0;

    private static final int REQUIRED_CONSECUTIVE = 3;

    private int cpuCounter = 0;
    private int memoryCounter = 0;
    private int diskCounter = 0;

    public FailureType detectFailure(SystemMetrics metrics) {

        // CPU detection
        if (metrics.getCpuUsage() > CPU_THRESHOLD) {
            cpuCounter++;
        } else {
            cpuCounter = 0;
        }

        // Memory detection
        if (metrics.getMemoryUsage() > MEMORY_THRESHOLD) {
            memoryCounter++;
        } else {
            memoryCounter = 0;
        }

        // Disk detection
        if (metrics.getDiskUsage() > DISK_THRESHOLD) {
            diskCounter++;
        } else {
            diskCounter = 0;
        }

        if (cpuCounter >= REQUIRED_CONSECUTIVE) {
            cpuCounter = 0;
            return FailureType.HIGH_CPU;
        }

        if (memoryCounter >= REQUIRED_CONSECUTIVE) {
            memoryCounter = 0;
            return FailureType.HIGH_MEMORY;
        }

        if (diskCounter >= REQUIRED_CONSECUTIVE) {
            diskCounter = 0;
            return FailureType.HIGH_DISK;
        }

        return FailureType.NONE;
    }
}
