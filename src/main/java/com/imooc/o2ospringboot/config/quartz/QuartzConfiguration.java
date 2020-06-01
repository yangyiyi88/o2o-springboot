package com.imooc.o2ospringboot.config.quartz;

import com.imooc.o2ospringboot.service.ProductSellDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;

@Configuration
public class QuartzConfiguration {
    //定期统计消费记录的service
    @Resource
    private ProductSellDailyService productSellDailyService;
    @Autowired
    private MethodInvokingJobDetailFactoryBean jobDetailFactory;
    @Autowired
    private CronTriggerFactoryBean triggerFactory;

    /**
     * 创建jobDetailFactory并返回
     *
     * @return
     */
    @Bean(name = "jobDetailFactory")
    public MethodInvokingJobDetailFactoryBean createJobDetailFactory() {
        //new出JobDetailFactory对象,此工厂主要用来制作一个jobDetail，即制作一个任务。
        //由于我们所做的定时任务根本上讲其实就是执行一个方法。所以用这个工厂比较方便。
        MethodInvokingJobDetailFactoryBean jobDetailFactory = new MethodInvokingJobDetailFactoryBean();
        //设置jobDetailFactory的名字
        jobDetailFactory.setName("product_sell_daily_job");
        //设置jobDetailFactory的组名
        jobDetailFactory.setGroup("product_sell_daily_group");
        //对于相同的JobDetail，当指定多个Trigger时, 很可能第一个job完成之前，第二个job就开始了。
        //指定concurrent设为false，多个job不会并发运行，第二个job将不会在第一个job完成之前开始。
        jobDetailFactory.setConcurrent(false);
        //指定运行任务的类
        jobDetailFactory.setTargetObject(productSellDailyService);
        //指定运行任务的方法
        jobDetailFactory.setTargetMethod("dailyCalculate");
        return jobDetailFactory;
    }

    /**
     * 创建triggerFactory并返回
     *
     * @return
     */
    @Bean(name = "triggerFactory")
    public CronTriggerFactoryBean createTriggerFactory() {
        //创建triggerFactory实例，用来创建trigger
        CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
        //设置triggerFactory的名字
        triggerFactory.setName("product_sell_daily_trigger");
        //设置triggerFactory的组名
        triggerFactory.setGroup("product_sell_daily_group");
        //绑定jobDetail
        triggerFactory.setJobDetail(jobDetailFactory.getObject());
        //设置cron表达式，请访问:http://cron.qqe2.com/在线表达式生成器
        triggerFactory.setCronExpression("0 0 0 * * ? *");
        return triggerFactory;
    }

    /**
     * 创建调度工厂并返回
     *
     * @return
     */
    @Bean(name = "schedulerFactory")
    public SchedulerFactoryBean createSchedulerFactory() {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setTriggers(triggerFactory.getObject());
        return schedulerFactory;
    }
}
