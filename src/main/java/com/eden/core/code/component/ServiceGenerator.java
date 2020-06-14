package com.eden.core.code.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/17
 */
public class ServiceGenerator extends AbstractCodeGenerator {
    @Override
    public List<String> buildContent(Class clazz, String dir, String prefix, String suffix) {
        List<String> result = new ArrayList<>();

        String packageName = clazz.getPackage().getName();
        packageName = packageName.substring(0, packageName.lastIndexOf("."));
        String moduleName = getModuleName(clazz);
        String aPackage = "package " + packageName + dir.replace("/", ".") + "." + moduleName + ";\n";
        result.add(aPackage);

        String importPath = getImportPath(clazz);
        result.add(importPath);
        int index = importPath.indexOf("param");
        result.add(importPath.substring(0, index) + "result" + importPath.substring(index + 5));
        result.add("import java.util.List;\n");
        String upperModuleName = moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1);
        result.add("public interface " + upperModuleName + "Service {\n");

        result.add("\tLong add(Add" + upperModuleName + "Param param);\n");
        result.add("\tboolean delete(Long id);\n");
        result.add("\tboolean modify(Modify" + upperModuleName + "Param param);\n");
        result.add("\t" + upperModuleName + "Result queryById(Long id);\n");
        result.add("\tList<" + upperModuleName + "Result> queryList(Query" + upperModuleName + "Param param);\n");

        result.add("}");
        return result;
    }

    private String getImportPath(Class clazz) {
        String replace = clazz.getName().replace(".entity.", ".domain.param.");
        return "import " + replace.substring(0, replace.lastIndexOf(".")) + "." + getModuleName(clazz) + ".*;";
    }

    @Override
    public String getProjectPath(Class clazz) {
        return getApiProjectPath(clazz);
    }
}
