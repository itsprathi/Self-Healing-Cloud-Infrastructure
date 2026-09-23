package com.prathi.selfhealingcloud.decision;

import com.prathi.selfhealingcloud.detection.FailureType;
import com.prathi.selfhealingcloud.monitoring.SystemMetrics;

public class DecisionEngineService {

    public Decision makeDecision(FailureType failure, SystemMetrics metrics) {

        switch (failure) {

            case HIGH_CPU:
                return handleCpu(metrics);

            case HIGH_MEMORY:
                return handleMemory(metrics);

            case HIGH_DISK:
                return handleDisk(metrics);

            default:
                return null;
        }
    }

    private Decision handleCpu(SystemMetrics metrics) {

        double cpu = metrics.getCpuUsage();

        if (cpu > 95) {
            return new Decision(
                    "Critical sustained CPU overload",
                    "Scale down intensive process or throttle workload",
                    90.0
            );
        }

        return new Decision(
                "High CPU usage detected",
                "Restart suspected service or clear background tasks",
                75.0
        );
    }

    private Decision handleMemory(SystemMetrics metrics) {

        return new Decision(
                "Memory pressure detected",
                "Clear cache or restart memory-heavy service",
                80.0
        );
    }

    private Decision handleDisk(SystemMetrics metrics) {

        return new Decision(
                "Disk nearing full capacity",
                "Clean temporary files and logs",
                85.0
        );
    }
}
