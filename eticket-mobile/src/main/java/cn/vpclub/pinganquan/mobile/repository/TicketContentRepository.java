package cn.vpclub.pinganquan.mobile.repository;

import cn.vpclub.pinganquan.mobile.domain.TicketContent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
@Repository
public interface TicketContentRepository extends CrudRepository<TicketContent, String> {

    List<TicketContent> findByTicketTypeId(String ticketTypeId);

}
