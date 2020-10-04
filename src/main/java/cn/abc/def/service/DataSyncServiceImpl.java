package cn.abc.def.service;

import org.springframework.stereotype.Service;

@Service
public class DataSyncServiceImpl implements IDataSyncService {
    @Override
    public void initialSynchronize() {
        //TODO 定时执行逻辑
        System.out.println("called by quartz.");
    }
}
