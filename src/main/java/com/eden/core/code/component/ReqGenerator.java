package com.eden.core.code.component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/17
 */
public class ReqGenerator extends AbstractCodeGenerator {

    @Override
    public List<String> buildContent(Class clazz, String dir, String prefix, String suffix) {
        ArrayList<String> result = new ArrayList<>();
        String packageName = clazz.getPackage().getName();
        packageName = packageName.substring(0, packageName.lastIndexOf("."));
        String moduleName = getModuleName(clazz);
        String aPackage = "package " + packageName + dir.replace("/", ".") + "." + moduleName + ";";

        result.add(aPackage);
        result.add("import lombok.Data;");
        result.add("import java.io.Serializable;");
        result.add("@Data");
        result.add("public class " + getSimpleFileName(prefix, moduleName, suffix, null) + " implements Serializable {");
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals("version") || field.getName().equals("id") || field.getName().equals("updatedBy") || field.getType().getSimpleName().equals("Timestamp")) {
                continue;
            }
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
