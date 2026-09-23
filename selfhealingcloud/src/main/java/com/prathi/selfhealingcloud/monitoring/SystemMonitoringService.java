package com.prathi.selfhealingcloud.monitoring;

import java.io.BufferedReader;
import java.io.FileReader;

public class SystemMonitoringService {

    public SystemMetrics collectMetrics() {
        double cpu = getCpuUsage();
        double memory = getMemoryUsage();
        double disk = getDiskUsage();

        return new SystemMetrics(cpu, memory, disk);
    }

    private double getCpuUsage() {
        try {
            long[] first = readCpuStat();
            Thread.sleep(1000);
            long[] second = readCpuStat();

            long idleDiff = second[3] - first[3];
            long totalDiff = sum(second) - sum(first);

            if (totalDiff == 0) return 0.0;

            return 100.0 * (totalDiff - idleDiff) / totalDiff;

        } catch (Exception e) {
            return 0.0;
        }
    }

    private long[] readCpuStat() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("/proc/stat"));
        String line = reader.readLine();
        reader.close();

        String[] parts = line.split("\\s+");

        long[] values = new long[parts.length - 1];
        for (int i = 1; i < parts.length; i++) {
            values[i - 1] = Long.parseLong(parts[i]);
        }

        return values;
    }

    private long sum(long[] arr) {
        long total = 0;
        for (long l : arr) total += l;
        return total;
    }

    private double getMemoryUsage() {
        try {
            Process process = new ProcessBuilder("bash", "-c",
                    "free | grep Mem | awk '{print ($3/$2) * 100.0}'")
                    .start();

            BufferedReader reader =
                    new BufferedReader(new java.io.InputStreamReader(process.getInputStream()));

            String line = reader.readLine();
            return line != null ? Double.parseDouble(line.trim()) : 0.0;

        } catch (Exception e) {
            return 0.0;
        }
    }

    private double getDiskUsage() {
        try {
            Process process = new ProcessBuilder("bash", "-c",
                    "df / | tail -1 | awk '{print $5}' | sed 's/%//'")
                    .start();

            BufferedReader reader =
                    new BufferedReader(new java.io.InputStreamReader(process.getInputStream()));

            String line = reader.readLine();
            return line != null ? Double.parseDouble(line.trim()) : 0.0;

        } catch (Exception e) {
            return 0.0;
        }
    }
}
