package org.springblade.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取相关编号设备信息
 */

@Data
public class DeviceModel {

    private String deviceNo;

    private String deviceName;

    private BigDecimal longitude;

    private BigDecimal latitude;
}
