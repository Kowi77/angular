package ru.javastudy.mvcHtml5Angular.javaconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import ru.javastudy.mvcHtml5Angular.mvc.quarts.CronQuartzTask;
import ru.javastudy.mvcHtml5Angular.mvc.quarts.QuartzTask;

@Configuration
@ComponentScan(basePackages = "ru.javastudy.mvcHtml5Angular.mvc.scheduling")
@EnableScheduling // <task:annotation-driven>
public class SchedulingConfig {

     /* Quartz scheduling configuration */

    /**
     *   <bean id="simpleTrigger" class="org.springframework.scheduling.quartz.SimpleTriggerFactoryBean">
     */
    @Bean(name = "simpleTrigger")
    public SimpleTriggerFactoryBean getSimpleTriggerFactoryBean() {
        SimpleTriggerFactoryBean stfb = new SimpleTriggerFactoryBean();

        stfb.setJobDetail(getSimpleQuartzJob().getObject());

        stfb.setRepeatInterval(1000);
        stfb.setStartDelay(1000);
        return stfb;
    }

    /**
     *  <bean id="cronTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
     */
    @Bean(name = "cronTrigger")
    public CronTriggerFactoryBean getCronTriggerFactoryBean() {
        CronTriggerFactoryBean ctfb = new CronTriggerFactoryBean();

        ctfb.setJobDetail(getQuartzCronJob().getObject());

        ctfb.setCronExpression("0/30 * * * * ?");
        return ctfb;
    }

    /**
     *  <bean id="simpleQuartzJob" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
     */
    @Bean(name = "simpleQuartzJob")
    public MethodInvokingJobDetailFactoryBean getSimpleQuartzJob() {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        factoryBean.setTargetBeanName("simpleQuartzTask");
        factoryBean.setTargetMethod("simpleTaskMethod");
        return factoryBean;
    }
    /**
     *  <bean id="quartzCronJob" class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
     */
    @Bean(name = "quartzCronJob")
    public MethodInvokingJobDetailFactoryBean getQuartzCronJob() {
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        factoryBean.setTargetBeanName("cronQuartzTask");
        factoryBean.setTargetMethod("cronTaskMethod");
        return factoryBean;
    }

    /**
     *  <bean id="simpleQuartzTask" class="ru.javastudy.mvcHtml5Angular.mvc.quartz.QuartzTask" />
     */
    @Bean(name = "simpleQuartzTask")
    public QuartzTask getSimpleQuartzTask() {
        return new QuartzTask();
    }

    /**
     *   <bean id="cronQuartzTask" class="ru.javastudy.mvcHtml5Angular.mvc.quartz.CronQuartzTask" />
     */
    @Bean(name = "cronQuartzTask")
    public CronQuartzTask getCronQuartzTask() {
        return new CronQuartzTask();
    }

    /**
     *  <bean class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
     */
    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean getSchedulerFactoryBean() {
        SchedulerFactoryBean scheduler  = new SchedulerFactoryBean();
        scheduler.setTriggers(getSimpleTriggerFactoryBean().getObject(), getCronTriggerFactoryBean().getObject());
        return scheduler ;
    }
}
