package cn.abc.def.util;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

/**
 * @Title:Quartz管理类 
 *  
 * @Description: 
 *  
 * @Copyright:  
 * @author zz  2008-10-8 14:19:01 
 * @version 1.00.000 
 * 
 */  
public class QuartzManager {  
   private static SchedulerFactory sf = new StdSchedulerFactory();
   private static String JOB_GROUP_NAME = "dataSync";  
   private static String TRIGGER_GROUP_NAME = "dataSyncTrigger";  
   
     
   /** *//** 
    *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
    * @param jobName 任务名 
    * @param job     任务 
    * @param time    时间设置，参考quartz说明文档 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void addJob(String jobName, Job job, String time)
                               throws SchedulerException, ParseException{
       Scheduler sched = sf.getScheduler();
       JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//任务名，任务组，任务执行类

       //触发器  
       CronTrigger  trigger =   
            new CronTrigger(jobName, TRIGGER_GROUP_NAME);//触发器名,触发器组  
       trigger.setCronExpression(time);//触发器时间设定  
       sched.scheduleJob(jobDetail,trigger);  
       //启动  
       if(!sched.isShutdown())  
          sched.start();  
   }  
     
   /** *//** 
    * 添加一个定时任务 
    * @param jobName 任务名 
    * @param jobGroupName 任务组名 
    * @param triggerName  触发器名 
    * @param triggerGroupName 触发器组名 
    * @param job     任务 
    * @param time    时间设置，参考quartz说明文档 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void addJob(String jobName,String jobGroupName,  
                             String triggerName,String triggerGroupName,  
                             Job job,String time)   
                               throws SchedulerException, ParseException {
       Scheduler sched = sf.getScheduler();  
       JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//任务名，任务组，任务执行类  
       //触发器  
       CronTrigger trigger =
            new CronTrigger(triggerName, triggerGroupName);//触发器名,触发器组  
       trigger.setCronExpression(time);//触发器时间设定  
       sched.scheduleJob(jobDetail,trigger);  
       if(!sched.isShutdown())  
          sched.start();  
   }  
     
   /** *//** 
    * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
    * @param jobName 
    * @param time 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void modifyJobTime(String jobName,String time)   
                                  throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       Trigger trigger =  sched.getTrigger(jobName,TRIGGER_GROUP_NAME);
       if(trigger != null){  
           CronTrigger  ct = (CronTrigger)trigger;  
           ct.setCronExpression(time);  
           sched.resumeTrigger(jobName,TRIGGER_GROUP_NAME);  
       }  
   }
   
   /** *//** 
    *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
    * @param jobName 任务名 
    * @param job     任务 
    * @param time    时间设置，参考quartz说明文档 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void addJob2(String jobName,Job job,String time)   
                               throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       String groupName = jobName+"Group";
       JobDetail jobDetail = new JobDetail(jobName, groupName, job.getClass());//任务名，任务组，任务执行类  
       String triggerGroupName = jobName+"TriggerGroup";
       //触发器  
       CronTrigger  trigger =   
            new CronTrigger(jobName, triggerGroupName);//触发器名,触发器组  
       //错过就忽略之前的任务,等待下次到点再执行
//       trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
       trigger.setCronExpression(time);//触发器时间设定  
       sched.scheduleJob(jobDetail,trigger);  
       //启动  
       if(!sched.isShutdown())  
          sched.start();  
   }  
   
   
   /**
    *  暂停一个定时任务
    * @param jobName 任务名 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
   public static void pauseJob(String jobName) throws SchedulerException, ParseException{  
       Scheduler sched = sf.getScheduler();  
       if(sched.isStarted()){
    	   sched.pauseJob(jobName, jobName+"Group");
       }

   }  
   /**
    * 恢复一个任务
    * @param jobName
    * @throws SchedulerException
    * @throws ParseException
    */
   public static void resumeJob(String jobName)throws SchedulerException, ParseException{
	   Scheduler sched = sf.getScheduler();
	   sched.resumeJob(jobName, jobName+"Group");
   }
   
   /**
    * 修改任务定时时间
    * @param jobName
    * @param cron
    * @throws SchedulerException 
    * @throws ParseException 
    */
   public static void modifyJobTime2(String jobName,String cron) throws SchedulerException, ParseException {
//	   jobName, groupName+"Trigger"
       Scheduler sched = sf.getScheduler();  
       String triggerGroup = jobName+"TriggerGroup";
       Trigger trigger =  sched.getTrigger(jobName,triggerGroup);  
       if(trigger != null){  
           CronTrigger  ct = (CronTrigger)trigger;  
           ct.setCronExpression(cron); 
           //错过就忽略之前的任务,等待下次到点再执行
//           ct.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
           sched.rescheduleJob(jobName, triggerGroup, ct);
       }else{
    	   throw new SchedulerException("找不到"+jobName+"的trigger");
       }  
   }
}