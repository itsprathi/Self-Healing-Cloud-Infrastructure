package com.prathi.selfhealingcloud.monitoring;

public class SystemMetrics {

    private double cpuUsage;
    private double memoryUsage;
    private double diskUsage;

    public SystemMetrics(double cpuUsage, double memoryUsage, double diskUsage) {
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.diskUsage = diskUsage;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public double getDiskUsage() {
        return diskUsage;
    }

    @Override
    public String toString() {
        return String.format(
        "CPU: %.2f%% | Memory: %.2f%% | Disk: %.2f%%",
        cpuUsage, memoryUsage, diskUsage
);
    }
}
