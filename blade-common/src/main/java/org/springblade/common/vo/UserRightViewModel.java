package org.springblade.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data

//用户信息对象
public class UserRightViewModel implements Serializable {

    //用户GUID
    private String userGUID;

    //用户代码
    private String userCode;

    //用户昵称
    private String userName;

    //部门Id
    private String departmentId;

    //部门名称
    private String departmentName;

    //isAdmin
    private String isAdmin;

    private List<PermissionDetailModel> permissionList;
}
