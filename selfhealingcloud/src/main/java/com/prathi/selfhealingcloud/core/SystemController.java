package com.prathi.selfhealingcloud.core;

import com.prathi.selfhealingcloud.scheduler.SelfHealingScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemController {

    private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

    public void start() {
        logger.info("Initializing Self-Healing Cloud System...");
        SelfHealingScheduler scheduler = new SelfHealingScheduler();
        scheduler.startMonitoring();
    }
}
