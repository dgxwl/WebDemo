package cn.abc.def.component.timer.quartz;

import cn.abc.def.service.IDataSyncService;
import cn.abc.def.util.SpringUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz定时器任务实现类
 */
public class DataSyncTask implements Job {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            IDataSyncService dataSyncService = (IDataSyncService) SpringUtil.getBean("dataSyncServiceImpl");
            dataSyncService.initialSynchronize();
        } catch (Exception e) {
            logger.error("同步数据出现异常", e);
        }
    }
}