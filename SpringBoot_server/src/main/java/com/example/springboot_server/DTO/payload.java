package com.example.springboot_server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dylan
 * @version 1.0
 * @date 2022/11/18 22:57
 * @description TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class payload {
    private String action;
    private String socketType;
    private String chartName;
    private String value;
}
