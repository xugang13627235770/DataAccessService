package org.springblade.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermissionDetailModel implements Serializable {

    private String functionCode;

    private List<String> actionCodeList;
}
