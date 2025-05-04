package com.jz.dlyk.testUtil;

import com.alibaba.fastjson.JSON;
import com.jz.dlyk.dto.R;
import com.jz.dlyk.entity.MyUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TestResponseUtil {
    @Test
    public void testR(){
        MyUserDetail myUserDetail = new MyUserDetail();
        myUserDetail.setAccountEnabled(1);
        myUserDetail.setAccountNoLocked(1);
        myUserDetail.setCreateBy(1);
        myUserDetail.setCredentialsNoExpired(1);
        myUserDetail.setAccountNoExpired(1);
        String s = JSON.toJSONString(R.success(myUserDetail));
        log.info(s);
    }
}
