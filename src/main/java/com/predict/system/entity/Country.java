package com.predict.system.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZhenYu.Ben
 */
@Getter
@Setter
@ToString
public class Country {
    private String id;
    private String name;
    private Double population;
    private Double confirmed;
    private Double death;
    private Integer emotion;
    private Integer gov;
    private Integer medical;
}
