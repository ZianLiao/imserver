/*
package com.css;


import com.css.im.mbg.mapper.ImOAppmsgSendMapper;
import com.css.im.mbg.model.ImOAppmsgSend;
import com.css.im.mbg.model.ImOAppmsgSendExample;
import com.css.im.sys.mapper.SysUserMapper;
import com.css.im.sys.model.SysUser;
import com.css.utils.DateTimeUtils;
import com.css.utils.UUIDUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

*/
/**
 * Create by wx on 2020-08-26
 *//*

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class DAOTest {

    @Autowired
    SysUserMapper sysUserMapper;

    @Test
    @Rollback(value = false)
    public void userAdd(){

        SysUser sysUser = new SysUser();
        sysUser.setUsername("test");
        sysUser.setPassword("123456");
        sysUser.setUserId(UUIDUtils.generateUUID());

        sysUserMapper.insert(sysUser);

    }

}
*/
