package com.lanbing.spring.xnolscan.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author xn025665
 * @date Create on 2019/2/18 18:47
 */
@Service
public class LoginUserService extends BaseService {

    @Value("${loginUser.userName}")
    private String loginUser;

    public String getLoginUser() {
        return loginUser;
    }
}
