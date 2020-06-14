package com.eden.core.code.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/17
 */
public class MapperGenerator extends AbstractCodeGenerator {

    @Override
    public List<String> buildContent(Class clazz, String dir, String prefix, String suffix) {
        ArrayList<String> result = new ArrayList<>();
        String packageName = clazz.getPackage().getName();
        packageName = packageName.substring(0, packageName.lastIndexOf("."));
        String moduleName = getModuleName(clazz);
        String aPackage = "package " + packageName + dir.replace("/", ".") + "." + moduleName + ";\n";

        result.add(aPackage);
        result.add("import com.baomidou.mybatisplus.core.mapper.BaseMapper;");
        result.add("import " + clazz.getName() + ";\n");
        result.add("public interface " + getSimpleFileName(prefix, moduleName, suffix, null) + " extends BaseMapper<" + clazz.getSimpleName() + "> {");
        result.add("}");
        return result;
    }

    @Override
    public String getProjectPath(Class clazz) {
        return getServiceProjectPath(clazz);
    }
}
