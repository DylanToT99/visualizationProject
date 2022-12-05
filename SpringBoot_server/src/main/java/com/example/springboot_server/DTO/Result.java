package com.example.springboot_server.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Dylan
 * @version 1.0
 * @date 2022/11/17 15:12
 * @description TODO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Boolean success;
    private String errorMessage;
    private Object data;
    private Long total;

    public static Result ok(){
        return new Result(true,null,null,null);
    }

    public static Result ok(Object data){
        return new Result(true,null,data,null);
    }

    public static Result ok(List<?>data,Long total){
        return new Result(true,null,data,total);
    }

    public static Result fail(String errorMessage){
        return new Result(false,errorMessage,null,null);
    }

}
