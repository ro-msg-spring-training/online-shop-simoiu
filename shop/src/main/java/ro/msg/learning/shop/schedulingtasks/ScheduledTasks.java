package ro.msg.learning.shop.schedulingtasks;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;
import ro.msg.learning.shop.config.SchedulerConfiguration;
import ro.msg.learning.shop.service.RevenueService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);
    private final RevenueService revenueService;
    private final SchedulerConfiguration schedulerConfiguration;

    @PostConstruct
    public void init() {
        var cronTrigger = CronExpression.parse(schedulerConfiguration.getSaveRevenuesCron());
        var next = cronTrigger.next(LocalDateTime.now());
        LOG.info("Next aggregation of all the sales revenues for each location starts at {}", next);
    }

    @Scheduled(cron = "#{@schedulerConfiguration.saveRevenuesCron}")
    public void saveTodayRevenues() {
        LOG.info("Starting saving all revenues for today");
        revenueService.saveAllRevenuesForToday();
        LOG.info("Revenues saved!");
    }
}
