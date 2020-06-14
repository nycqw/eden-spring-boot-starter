package com.eden.core.code.component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/17
 */
public class RespGenerator extends AbstractCodeGenerator {

    @Override
    public List<String> buildContent(Class clazz, String dir, String prefix, String suffix) {
        ArrayList<String> result = new ArrayList<>();
        String packageName = clazz.getPackage().getName();
        packageName = packageName.substring(0, packageName.lastIndexOf("."));
        String moduleName = getModuleName(clazz);
        String aPackage = "package " + packageName + dir.replace("/", ".") + "." + moduleName + ";\n";

        result.add(aPackage);
        result.add("import lombok.Data;");
        result.add("import java.io.Serializable;");
        result.add("import java.sql.Timestamp;\n");
        result.add("@Data");
        result.add("public class " + getSimpleFileName(prefix, moduleName, suffix, null) + " implements Serializable {\n");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            result.add("    private " + field.getType().getSimpleName() + " " + field.getName() + ";");
        }
        result.add("}");
        return result;
    }

    @Override
    public String getProjectPath(Class clazz) {
        return getApiProjectPath(clazz);
    }
}
