package com.bewantbe.maidenvoyage.domain;

import lombok.Data;

/**
  * 城市实体类
  *
  * Created by bysocket on 07/02/2017.
  */
@Data
public class City {
    private Long id;
    private Long provinceId;
    private String cityName;
    private String description;
}
