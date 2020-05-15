package org.springblade.common.vo;

import lombok.Data;

@Data
public class DataModel {

  private String records;
  private Long total;
  private Integer current;
  private Integer size;
  private String[] orders;
  private Integer pages;
}
