package com.idanchuang.cms.server.infrastructureNew.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Map;

/**
 * @author: jww <jiweiwei@accesscorporate.com.cn>
 * @Date: 2022-03-09 17:23
 * @Desc: 模板动态构建工具
 * @Copyright VTN Limited. All rights reserved.
 */
@Slf4j
public class GetTemplateUtils {

    public static String mergeTemplateContent(Map<String, Object> values, String template) throws IOException {
        log.info("mergeTemplateContent:{}", template);
        Resource resource = new ClassPathResource("config/" + template);
        Reader fileReader = new InputStreamReader(resource.getInputStream());
        //定义字节数组输出流
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        //这是配置的核心类
        Configuration config = new Configuration();
        config.setDefaultEncoding("utf-8");
        //使用FileTemplateLoader
        try {
            Template tp = new Template(template, fileReader, config);
            //将变量与模版合并,并将合并后的内容给输出流
            tp.process(values, new OutputStreamWriter(output));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回输出流的字符串形式
        return output.toString();
    }
}
