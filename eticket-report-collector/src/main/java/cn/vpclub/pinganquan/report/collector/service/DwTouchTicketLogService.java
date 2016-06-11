package cn.vpclub.pinganquan.report.collector.service;

import cn.vpclub.pinganquan.report.collector.domain.DwTouchTicketLog;
import cn.vpclub.pinganquan.report.collector.repository.DwTouchTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
public class DwTouchTicketLogService {

    @Autowired
    private DwTouchTicketRepository repository;


    public boolean add(DwTouchTicketLog entity) {
        DwTouchTicketLog result = repository.save(entity);
        return result != null ? true : false;
    }


}
