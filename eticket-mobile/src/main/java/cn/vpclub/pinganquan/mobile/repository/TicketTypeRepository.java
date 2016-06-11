package cn.vpclub.pinganquan.mobile.repository;

import cn.vpclub.pinganquan.mobile.domain.TicketType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/5/3.
 */
@Repository
public interface TicketTypeRepository extends CrudRepository<TicketType, String> {

    TicketType findByIdAndDelFlag(String id, int delFlag);

}

