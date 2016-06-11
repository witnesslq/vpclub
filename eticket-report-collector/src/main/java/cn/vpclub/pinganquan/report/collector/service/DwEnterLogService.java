package cn.vpclub.pinganquan.report.collector.service;


import cn.vpclub.pinganquan.report.collector.domain.DwEnterLog;
import cn.vpclub.pinganquan.report.collector.repository.DwEnterLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
public class DwEnterLogService {

    @Autowired
    private DwEnterLogRepository repository;


    public boolean add(DwEnterLog entity) {
        DwEnterLog result = repository.save(entity);
        return result != null ? true : false;
    }

    public String getIPhoneVersion(String userAgent)
    {
        int i1 = userAgent.indexOf("(");

        int i2 = userAgent.indexOf(")");

        return userAgent.substring(i1 + 1,i2).split("OS ")[1].split(" ")[0];
    }

}
