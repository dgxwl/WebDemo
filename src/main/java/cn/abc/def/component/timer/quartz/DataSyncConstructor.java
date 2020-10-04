package cn.abc.def.component.timer.quartz;

import cn.abc.def.util.QuartzManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Quartz定时器任务启动类
 */
@Component
public class DataSyncConstructor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        try {
            QuartzManager.addJob2("dataSync", new DataSyncTask(), "0,20,40 * * * * ?");
        } catch (Exception e) {
            logger.error("同步数据定时器异常", e);
        }
    }
}
