package com.css.im.info.service;

import com.css.im.info.model.InfoFrequentConnect;

import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 9/14/2020
 */
public interface InfoFrequentConnectService {
    boolean addInfoFrequentConnect(InfoFrequentConnect infoFrequentConnect);

    boolean updateInfoFrequentConnect(InfoFrequentConnect infoFrequentConnect);

    InfoFrequentConnect getByInfoId(String infoId);

    List<InfoFrequentConnect> getListByUser(String userId);

    boolean delInfoFrequentConnect(String infoId);

    //for 1.0
    boolean delIFCmember(String delUserId, String infoId);
}
