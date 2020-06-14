package com.eden.core.code.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/17
 */
public class XmlGenerator extends AbstractCodeGenerator {
    @Override
    public List<String> buildContent(Class clazz, String dir, String prefix, String suffix) {
        ArrayList<String> result = new ArrayList<>();
        result.add("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        result.add("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >");

        String moduleName = getModuleName(clazz);
        String mapperName = getPackagePath(clazz).replace("/", ".") + ".mapper." + moduleName + "." + moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1) + "Mapper";
        result.add("<mapper namespace=\"" + mapperName + "\">");
        result.add("</mapper>");
        return result;
    }

    @Override
    public String getProjectPath(Class clazz) {
        return getResourcePath();
    }
}
