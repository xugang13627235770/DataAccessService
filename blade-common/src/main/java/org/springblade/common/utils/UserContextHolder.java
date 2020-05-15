package org.springblade.common.utils;
import lombok.extern.slf4j.Slf4j;
import org.springblade.common.vo.UserRightViewModel;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class UserContextHolder {

    private static UserRightViewModel userRightViewModel;

    public UserContextHolder() {

    }

    public static UserRightViewModel getUserInfo() {
        return userRightViewModel;
    }

    public static String getAuthorization() {
        HttpServletRequest req = HttpContextUtils.getHttpServletRequest();
        log.info(String.format("%s getAuthorization request to %s", req.getMethod(),
                req.getRequestURL().toString()));
        return req != null ? req.getHeader("Authorization") : null;
    }

    /**
     * 获取登录用户信息
     * @param userInfo
     * @return
     */
    public static UserRightViewModel setUserInfo(UserRightViewModel userInfo) {
        userRightViewModel = userInfo;
        return userRightViewModel;
    }
}
