package cn.vpclub.pinganquan.report.collector.service;

import cn.vpclub.pinganquan.report.collector.domain.DwUserEventLog;
import cn.vpclub.pinganquan.report.collector.repository.DwUserEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
public class DwUserEventLogService {

    @Autowired
    private DwUserEventLogRepository repository;


    public boolean add(DwUserEventLog entity) {
        DwUserEventLog result = repository.save(entity);
        return result != null ? true : false;
    }


}
