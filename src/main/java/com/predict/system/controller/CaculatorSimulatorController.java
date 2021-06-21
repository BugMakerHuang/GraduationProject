package com.predict.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.predict.system.entity.Country;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author hp
 */
@RestController
public class CaculatorSimulatorController {
    @RequestMapping("/caculator/{name}")
    public String caculator(@PathVariable String name) throws IOException {
         //初始环境容量
         Integer k = 0;
         //初始确诊容量
         Integer p = 0;
         //初始增长率
         Double r = 0.8;
         //初始时间
         Double t = 1.0;
         String str = "";
         //导入population.json
        File file = new File("E:\\GraduationProject-web\\GraduationProject\\vue-cli\\public\\js\\json\\population.json");
        FileReader fileReader = new FileReader(file);
        Reader reader = new InputStreamReader(new FileInputStream(file), "utf-8");
        int ch = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while ((ch = reader.read()) !=-1){
             stringBuffer.append((char) ch);
        }
        fileReader.close();
        reader.close();
        str = stringBuffer.toString();
        ArrayList arrayList = JSON.parseObject(str, ArrayList.class);

        for(int i = 0;i<arrayList.size();i++){
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(arrayList.get(i)));
            if(name.toLowerCase().equals(jsonObject.get("name").toString().toLowerCase())){
                k = (Integer) jsonObject.get("value");
                p = (Integer) jsonObject.get("confirmed");
                Double e = Math.pow(Math.E,r*t);
                double v = p * e;
                double newConfirm = (k * v) / ((k + v) - p);
                System.out.println(newConfirm);
                jsonObject.put("confirmed",(int)newConfirm);
                String news = jsonObject.toJSONString();
                arrayList.set(i,news);
            }
        }
        String s = arrayList.toString();
        FileWriter fw;
        fw = new FileWriter("E:\\GraduationProject-web\\GraduationProject\\vue-cli\\public\\js\\json\\population.json");
        PrintWriter printWriter = new PrintWriter(fw);
        printWriter.write(s);
        printWriter.println();
        fw.close();
        printWriter.close();
        return "error";
    }
}
