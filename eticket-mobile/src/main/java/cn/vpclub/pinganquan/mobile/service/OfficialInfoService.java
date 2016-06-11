package cn.vpclub.pinganquan.mobile.service;

import cn.vpclub.pinganquan.mobile.domain.OfficialInfo;
import cn.vpclub.pinganquan.mobile.repository.OfficialInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/3.
 */
@Service
public class OfficialInfoService {

    @Autowired
    private OfficialInfoRepository officialInfoRepository;


    public OfficialInfo add(OfficialInfo entity) {
        return officialInfoRepository.save(entity);
    }


    @Cacheable(value = "OfficialInfoService", keyGenerator = "wiselyKeyGenerator")
    public List<OfficialInfo> findByActivityIdAndOfficialType(String activityId, int officialType) {
        return officialInfoRepository.findByActivityIdAndOfficialTypeAndDelFlag(activityId, officialType, 0);
    }


    /**
     * 获取中奖的文案
     *
     * @param activityId
     * @return
     */
    public String getWinningCopyWriting(String activityId) {
        List<OfficialInfo> list = this.findByActivityIdAndOfficialType(activityId, 1);
        return getCopyWritingFromList(list);
    }


    /**
     * 获取未中奖的文案
     *
     * @param activityId
     * @return
     */
    public String getNotWinningCopyWriting(String activityId) {
        List<OfficialInfo> list = this.findByActivityIdAndOfficialType(activityId, 2);
        return getCopyWritingFromList(list);
    }


    private String getCopyWritingFromList(List<OfficialInfo> list) {
        if (list.size() == 0) {
            return "";
        } else {
            Random random = new Random();
            int index = random.nextInt(list.size());
            OfficialInfo officialInfo = list.get(index);
            return officialInfo.getOfficialCo();
        }
    }


}
