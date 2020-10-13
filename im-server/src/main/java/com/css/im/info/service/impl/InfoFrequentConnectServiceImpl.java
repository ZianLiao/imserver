package com.css.im.info.service.impl;

import com.css.common.BaseService;
import com.css.im.info.mapper.InfoFrequentConnectMapper;
import com.css.im.info.model.InfoFrequentConnect;
import com.css.im.info.model.InfoFrequentConnectExample;
import com.css.im.info.service.InfoFrequentConnectService;
import com.css.im.mbg.exceptions.DiyGroupException;
import com.css.utils.DateTimeUtils;
import com.css.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 9/14/2020
 */
@Service
public class InfoFrequentConnectServiceImpl extends BaseService implements InfoFrequentConnectService {
    @Autowired
    InfoFrequentConnectMapper ifcMapper;

    @Override
    public boolean addInfoFrequentConnect(InfoFrequentConnect infoFrequentConnect) {
        if (infoFrequentConnect != null && infoFrequentConnect.getConnectUids() != null) {
            infoFrequentConnect.setInfoId(UUIDUtils.generateUUID());
            infoFrequentConnect.setCreateTime(DateTimeUtils.formatCurrentTime());
        }
        int affected = ifcMapper.insert(infoFrequentConnect);
        return affected > 0;
    }

    @Override
    public boolean updateInfoFrequentConnect(InfoFrequentConnect infoFrequentConnect) {
        int affected = ifcMapper.updateByPrimaryKeyWithBLOBs(infoFrequentConnect);
        return affected > 0;
    }

    @Override
    public InfoFrequentConnect getByInfoId(String infoId) {
        return ifcMapper.selectByPrimaryKey(infoId);
    }

    @Override
    public List<InfoFrequentConnect> getListByUser(String userId) {
        InfoFrequentConnectExample ifcExample = new InfoFrequentConnectExample();
        InfoFrequentConnectExample.Criteria criteria = ifcExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return ifcMapper.selectByExampleWithBLOBs(ifcExample);
    }

    @Override
    public boolean delInfoFrequentConnect(String infoId) {
        int affected = ifcMapper.deleteByPrimaryKey(infoId);
        return affected > 0;
    }

    @Override
    public boolean delIFCmember(String delUserId, String infoId) {
        InfoFrequentConnect ifc = ifcMapper.selectByPrimaryKey(infoId);
        if (ifc != null) {
            try {
                String userCodeList = ifc.getConnectUids();
                userCodeList = userCodeList.replace(delUserId, "");
                if (userCodeList.startsWith(",")) {
                    userCodeList = userCodeList.substring(1);
                } else if (userCodeList.endsWith(",")) {
                    userCodeList = userCodeList.substring(0, userCodeList.length() - 1);
                } else {
                    userCodeList = userCodeList.replace(",,", ",");
                }
                ifc.setConnectUids(userCodeList);
                int affectedRows = ifcMapper.updateByPrimaryKeySelective(ifc);
                return affectedRows > 0;
            } catch (Exception e) {
                throw new DiyGroupException("delete user failed: " + e.getMessage());
            }
        } else {
            return false;
        }

    }
}
