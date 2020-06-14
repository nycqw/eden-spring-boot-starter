package com.eden.core.code;

import com.eden.core.code.component.*;

import java.io.IOException;

/**
 * 代码生成器
 *
 * @author chenqw
 * @version 1.0
 * @since 2020/5/17
 */
public class CodeGenerator {

    public static void generate(Class clazz) throws IOException {
        ReqGenerator reqGenerator = new ReqGenerator();
        reqGenerator.create(clazz, "/domain/param", "Add", "Param", "java");
        reqGenerator.create(clazz, "/domain/param", "Modify", "Param", "java");
        reqGenerator.create(clazz, "/domain/param", "Query", "Param", "java");

        RespGenerator respGenerator = new RespGenerator();
        respGenerator.create(clazz, "/domain/result", "", "Result", "java");

        MapperGenerator mapperGenerator = new MapperGenerator();
        mapperGenerator.create(clazz, "/mapper", "", "mapper", "java");

        XmlGenerator xmlGenerator = new XmlGenerator();
        xmlGenerator.create(clazz, "/mapper", "", "Mapper", "xml");

        ServiceGenerator serviceGenerator = new ServiceGenerator();
        serviceGenerator.create(clazz, "/intf", "", "Service", "java");

        ServiceImplGenerator serviceImplGenerator = new ServiceImplGenerator();
        serviceImplGenerator.create(clazz, "/service", "", "ServiceImpl", "java");
    }

}
