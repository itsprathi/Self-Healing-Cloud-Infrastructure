package com.prathi.selfhealingcloud.scheduler;


import com.prathi.selfhealingcloud.decision.Decision;
import com.prathi.selfhealingcloud.decision.DecisionEngineService;
import com.prathi.selfhealingcloud.monitoring.SystemMonitoringService;
import com.prathi.selfhealingcloud.monitoring.SystemMetrics;
import com.prathi.selfhealingcloud.detection.FailureDetectionService;
import com.prathi.selfhealingcloud.detection.FailureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SelfHealingScheduler {

    private static final Logger logger = LoggerFactory.getLogger(SelfHealingScheduler.class);

    private final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private final SystemMonitoringService monitoringService =
            new SystemMonitoringService();

    private final FailureDetectionService detectionService =
            new FailureDetectionService();

    private final DecisionEngineService decisionEngine =
            new DecisionEngineService();

    public void startMonitoring() {

        logger.info("Starting Monitoring Scheduler...");

        scheduler.scheduleAtFixedRate(() -> {

            SystemMetrics metrics = monitoringService.collectMetrics();
            logger.info("System Metrics -> {}", metrics);

            FailureType failure = detectionService.detectFailure(metrics);

	    if (failure != FailureType.NONE) {

                logger.warn("Failure Detected: {}", failure);

                Decision decision = decisionEngine.makeDecision(failure, metrics);

                if (decision != null) {
                    logger.warn("Decision Made -> {}", decision);
                }
            }

        }, 0, 10, TimeUnit.SECONDS);
    }
}
