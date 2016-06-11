package cn.vpclub.pinganquan.report.collector.repository;

import cn.vpclub.pinganquan.report.collector.domain.DwUserEventLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/5/10.
 */
@Repository
public interface DwUserEventLogRepository extends CrudRepository<DwUserEventLog, String> {

}
