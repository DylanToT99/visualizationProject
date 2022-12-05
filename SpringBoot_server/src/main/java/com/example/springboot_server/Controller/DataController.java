package com.example.springboot_server.Controller;

import com.example.springboot_server.DTO.Result;
import com.example.springboot_server.Service.DataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dylan
 * @version 1.0
 * @date 2022/11/17 15:12
 * @description TODO
 **/
@RestController()
@RequestMapping("/api")
public class DataController {
    @Resource
    private DataService dataService;

    @GetMapping("/**")
    @CrossOrigin(origins = "*")
    public Result pathResolve(HttpServletRequest request,HttpServletResponse response){
        long start = System.currentTimeMillis();
        //   /api/seller
        String requestURI = request.getRequestURI();
        //拿到请求路径中的文件名:
        String fileName = requestURI.substring(5);
        System.out.println(fileName);
        try {
            long end = System.currentTimeMillis();
            response.setHeader("X-Response-Time",String.valueOf(end-start));
            return dataService.pathResolve(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("读取文件失败");
        }

    }

}
