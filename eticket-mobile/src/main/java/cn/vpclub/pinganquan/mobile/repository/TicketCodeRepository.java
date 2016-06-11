package cn.vpclub.pinganquan.mobile.repository;

import cn.vpclub.pinganquan.mobile.domain.TicketCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/5/3.
 */
@Repository
public interface TicketCodeRepository extends CrudRepository<TicketCode, String> {

    TicketCode findByTicketTypeIdAndTicketNoAndDelFlag(String ticketTypeId, String ticketNo, int delFlag);

    TicketCode findFirstByTicketTypeIdAndDelFlag(String ticketTypeId, int delFlag);


}
