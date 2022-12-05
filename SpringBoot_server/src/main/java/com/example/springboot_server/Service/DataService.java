package com.example.springboot_server.Service;

import com.alibaba.fastjson.JSON;

import com.example.springboot_server.DTO.Result;
import com.example.springboot_server.DTO.payload;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;


/**
 * @author Dylan
 * @version 1.0
 * @date 2022/11/17 15:18
 * @description TODO
 **/
@Service
public class DataService {

    public Result pathResolve(String fileName) throws IOException {
        //获取类路径下的Json文件内容,写入到输入流中
        InputStream resource = getClass().getResourceAsStream("/static/DataFile/" + fileName + ".json");
        StringBuilder stringBuilder=new StringBuilder();
        if (resource==null){
            return Result.fail("文件名出错");
        }
        //缓冲读取器
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource));
        String readLine=null;
        while((readLine=bufferedReader.readLine())!=null){
            stringBuilder.append(readLine);
        }
        //返回Json对象
        return Result.ok(JSON.parse(stringBuilder.toString()));
    }

    public void pathResolve(payload payload1) throws IOException {
        //获取类路径下的Json文件内容,写入到输入流中
        InputStream resource = getClass().getResourceAsStream("/static/DataFile/" + payload1.getChartName() + ".json");
        StringBuilder stringBuilder=new StringBuilder();
        if (resource==null){
            return ;
        }
        //缓冲读取器
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource));
        String readLine=null;
        while((readLine=bufferedReader.readLine())!=null){
            stringBuilder.append(readLine);
        }
        //返回Json对象
        payload1.setValue(stringBuilder.toString());
    }
}
