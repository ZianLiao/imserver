package com.css.im.mbg.web;

import cn.hutool.core.codec.Base64;
import com.css.common.BaseController;
import com.css.common.ReqResult;
import com.css.im.auth.SecurityFacade;
import com.css.im.config.FileConfig;
import com.css.im.sys.model.SysUser;
import com.css.im.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * created by Charles Zhang
 *
 * @date 2020/9/25
 */
@Api(tags = "头像操作接口")
@Controller
@RequestMapping("/im/api/headimage")
public class HeadImageController extends BaseController {
    private static String separator = System.getProperty("file.separator");
    @Autowired
    SysUserService userService;
    @Autowired
    FileConfig fileConfig;

    @ApiOperation(value = "获取头像接口")
    @GetMapping(value = "/download/{userId}")
    @ResponseBody
    public ReqResult getHeadImage(@PathVariable String userId) {
        if (userId == null) {
            return ReqResult.failed();
        }
        SysUser user = userService.getUserById(userId);
        if (user == null) {
            return ReqResult.failed();
        }
        JSONObject headImageJson = new JSONObject();
        File headImage = new File(fileConfig.getBasicHeadPath(),
                user.getPhoto() == null ? "default.jpg" : user.getPhoto());
        if (!headImage.exists()) {
            headImageJson.put("headImageStr", "");
        } else {
            try {
                byte[] data = FileUtils.readFileToByteArray(headImage);
                String headImageStr = Base64.encode(data);
                headImageJson.put("headImageStr", headImageStr);
            } catch (IOException e) {
                e.printStackTrace();
                return ReqResult.failed(e.getMessage());
            }
        }
        headImageJson.put("userId", userId);
        return ReqResult.success(headImageJson);
    }

    @ApiOperation(value = "上传头像接口")
    @PostMapping(value = "/update")
    @ResponseBody
    public ReqResult updateHeadImage(@RequestBody JSONObject headImageJson) {
        String headImageStr = headImageJson.getString("headImageStr");
        if (headImageStr == null) {
            return ReqResult.failed("请上传头像");
        }
        try {
            byte[] headImageData = Base64.decode(headImageStr);
            String userId = SecurityFacade.getCurUserId();
            SysUser user = new SysUser();
            user.setUserId(userId);
            String newHeadImageName = userId + ".jpg";
            File newHeadImage = new File(fileConfig.getBasicHeadPath(), newHeadImageName);
            logger.debug("上传到文件路径: " + newHeadImage.getAbsolutePath());
            FileUtils.writeByteArrayToFile(newHeadImage, headImageData);
            user.setPhoto(newHeadImageName);
            if (userService.updateUser(user)) {
                return ReqResult.success();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ReqResult.failed();
    }
}
