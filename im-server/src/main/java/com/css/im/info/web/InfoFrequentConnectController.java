package com.css.im.info.web;

import com.alibaba.fastjson.JSON;
import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.im.auth.SecurityFacade;
import com.css.im.info.model.InfoFrequentConnect;
import com.css.im.info.service.InfoFrequentConnectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * created by Charles Zhang
 *
 * @date 9/14/2020
 */
@Api(tags = "常用联系人分组相关接口")
@RestController
@RequestMapping("/im/api/info/frequentconnect")
public class InfoFrequentConnectController extends BaseController {
    @Autowired
    InfoFrequentConnectService ifcService;

    @ApiOperation(value = "创建常用联系人分组")
    @PostMapping(value = "/create")
    @ResponseBody
    public ReqResult createFrequentConnect(HttpServletRequest request) {
        String frequentConnectStr = get(request, "frequentConnectStr");
        InfoFrequentConnect ifc
                = JSON.parseObject(frequentConnectStr, InfoFrequentConnect.class);
        if (ifc != null) {
            if (ifcService.addInfoFrequentConnect(ifc)) {
                return ReqResult.success();
            }
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "添加常用联系人成员")
    @PostMapping(value = "/add")
    @ResponseBody
    public ReqResult addFrequentConnectMember(HttpServletRequest request) {
        String[] addUserIds = getValues(request, "addUserIds"); //添加的userId
        String infoId = get(request, "infoId");  //chat group id
        StringBuffer buffer = new StringBuffer();
        if (addUserIds != null) {
            for (String id : addUserIds) {
                buffer.append(id).append(",");
            }
        }
        InfoFrequentConnect ifc = ifcService.getByInfoId(infoId);
        ifc.setConnectUids(buffer.toString() + ifc.getConnectUids());
        if (ifcService.updateInfoFrequentConnect(ifc)) {
            return ReqResult.success();
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "删除常用联系人成员")
    @PostMapping(value = "/remove")
    @ResponseBody
    public ReqResult delFrequentConnectMember(HttpServletRequest request) {
        String[] delUserIds = getValues(request, "delUserIds");
        String infoId = get(request, "infoId");  //chat group id
        InfoFrequentConnect ifc = ifcService.getByInfoId(infoId);
        String memberIds = ifc.getConnectUids();
        for (String delId : delUserIds) {
            if (memberIds.contains(delId)) {
                memberIds.replace(delId, "");
                if (memberIds.startsWith(",")) {
                    memberIds = memberIds.substring(1);
                } else if (memberIds.endsWith(",")) {
                    memberIds = memberIds.substring(0, memberIds.length() - 1);
                } else {
                    memberIds = memberIds.replace(",,", ",");
                }
            }
        }
        ifc.setConnectUids(memberIds);
        if (ifcService.updateInfoFrequentConnect(ifc)) {
            return ReqResult.success();
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "更改常用联系人分组名称接口")
    @PostMapping(value = "/update")
    @ResponseBody
    public ReqResult update(HttpServletRequest request) throws Exception {

        String infoId = get(request, "infoId");
        String groupName = get(request, "groupName");
        if (infoId != null && groupName != null) {
            InfoFrequentConnect ifc = new InfoFrequentConnect();
            ifc.setInfoId(infoId);
            ifc.setGroupName(groupName);
            if (ifcService.updateInfoFrequentConnect(ifc)) {
                return ReqResult.success();
            }
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "删除常用联系人分组名称接口")
    @PostMapping(value = "/delete")
    @ResponseBody
    public ReqResult delete(HttpServletRequest request) throws Exception {
        String infoId = get(request, "infoId");
        if (ifcService.delInfoFrequentConnect(infoId)) {
            return ReqResult.success();
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "获取常用联系人分组列表")
    @GetMapping(value = "/ifclist")
    @ResponseBody
    public ReqResult getChatGroupList() throws Exception {
        //get the current user's id
        String userId = SecurityFacade.getCurUserId();
        List<InfoFrequentConnect> ifcList = ifcService.getListByUser(userId);
        if (ifcList != null) {
            return ReqResult.success(ifcList);
        }
        return ReqResult.failed();
    }

    @ApiOperation(value = "获取某个常用联系人分组的详细信息")
    @GetMapping(value = "/ifcdetail")
    @ResponseBody
    public ReqResult getGroupDetail(HttpServletRequest request) throws Exception {
        String infoId = get(request, "infoId");
        InfoFrequentConnect ifc = ifcService.getByInfoId(infoId);
        if (ifc != null) {
            return ReqResult.success(ifc);
        }
        return ReqResult.failed();
    }
}
