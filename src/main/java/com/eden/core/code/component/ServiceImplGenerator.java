package com.eden.core.code.component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/17
 */
public class ServiceImplGenerator extends AbstractCodeGenerator {
    @Override
    public List<String> buildContent(Class clazz, String dir, String prefix, String suffix) {
        List<String> result = new ArrayList<>();

        String packageName = clazz.getPackage().getName();
        String moduleName = getModuleName(clazz);
        String upperModuleName = moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1);
        packageName = packageName.substring(0, packageName.lastIndexOf("."));

        String aPackage = "package " + packageName + dir.replace("/", ".") + "." + moduleName + ";\n";
        result.add(aPackage);

        String importPath = getImportPath(clazz);
        result.add(importPath);
        int index = importPath.indexOf("param");
        result.add(importPath.substring(0, index) + "result" + importPath.substring(index + 5));
        result.add("import java.util.List;");
        result.add("import org.apache.dubbo.config.annotation.Service;");
        result.add("import org.springframework.beans.BeanUtils;");
        result.add("import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;");
        result.add("import java.sql.Timestamp;");
        result.add("import java.time.LocalDateTime;");
        result.add("import java.util.stream.Collectors;");
        result.add("import " + getPackagePath(clazz).replace("/", ".") + ".entity." + clazz.getSimpleName() + ";");
        result.add("import " + getPackagePath(clazz).replace("/", ".") + ".intf." + moduleName + "." + upperModuleName + "Service;\n");

        result.add("@Service(protocol = \"dubbo\")");
        result.add("public class " + upperModuleName + "ServiceImpl implements " + upperModuleName + "Service {\n");
        buildAddMethod(clazz, result, upperModuleName);

        buildDeleteMethod(clazz, result);

        buildModifyMethod(clazz, result, upperModuleName);

        buildQueryMethod(clazz, result, upperModuleName);

        buildQueryListResp(clazz, result, upperModuleName);

        result.add("}");
        return result;
    }

    private void buildQueryListResp(Class clazz, List<String> result, String upperModuleName) {
        result.add("\t@Override");
        result.add("\tpublic List<" + upperModuleName + "Result> queryList(Query" + upperModuleName + "Param param) {");
        result.add("\t\tQueryWrapper<" + clazz.getSimpleName() + "> queryWrapper = new QueryWrapper<>();");
        result.add("\t\t" + clazz.getSimpleName() + " entity = new " + clazz.getSimpleName() + "();");
        result.add("\t\tList<" + clazz.getSimpleName() + "> list = entity.selectList(queryWrapper);");
        result.add("\t\treturn list.stream().map(el -> {");
        result.add("\t\t\t" + upperModuleName + "Result result = new " + upperModuleName + "Result();");
        result.add("\t\t\tBeanUtils.copyProperties(el, result);");
        result.add("\t\t\treturn result;");
        result.add("\t\t}).collect(Collectors.toList());");
        result.add("\t}\n");
    }

    private void buildQueryMethod(Class clazz, List<String> result, String upperModuleName) {
        result.add("\t@Override");
        result.add("\tpublic " + upperModuleName + "Result queryById(Long id) {");
        result.add("\t\t" + clazz.getSimpleName() + " entity = new " + clazz.getSimpleName() + "();");
        result.add("\t\tentity.setId(id);");
        result.add("\t\t" + upperModuleName + "Result result = new " + upperModuleName + "Result();");
        result.add("\t\tBeanUtils.copyProperties(entity.selectById(), result);");
        result.add("\t\treturn result;");
        result.add("\t}\n");
    }

    private void buildModifyMethod(Class clazz, List<String> result, String upperModuleName) {
        result.add("\t@Override");
        result.add("\tpublic boolean modify(Modify" + upperModuleName + "Param param) {");
        result.add("\t\t" + clazz.getSimpleName() + " entity = new " + clazz.getSimpleName() + "();");
        result.add("\t\tBeanUtils.copyProperties(param, entity);");
        result.add("\t\tentity.setUpdatedTime(Timestamp.valueOf(LocalDateTime.now()));");
        result.add("\t\treturn entity.updateById();");
        result.add("\t}\n");
    }

    private void buildDeleteMethod(Class clazz, List<String> result) {
        result.add("\t@Override");
        result.add("\tpublic boolean delete(Long id) {");
        result.add("\t\t" + clazz.getSimpleName() + " entity = new " + clazz.getSimpleName() + "();");
        result.add("\t\tentity.setId(id);");
        result.add("\t\treturn entity.deleteById();");
        result.add("\t}\n");
    }

    private void buildAddMethod(Class clazz, List<String> result, String upperModuleName) {
        result.add("\t" + "@Override");
        result.add("\t" + "public Long add(Add" + upperModuleName + "Param param) {");
        result.add("\t\t" + clazz.getSimpleName() + " entity = new " + clazz.getSimpleName() + "();");
        result.add("\t\t" + "BeanUtils.copyProperties(param, entity);");
        result.add("\t\t" + "entity.insert();");
        result.add("\t\t" + "return entity.getId();");
        result.add("\t}\n");
    }

    private String getImportPath(Class clazz) {
        String replace = clazz.getName().replace(".entity.", ".domain.param.");
        return "import " + replace.substring(0, replace.lastIndexOf(".")) + "." + getModuleName(clazz) + ".*;";
    }

    @Override
    public String getProjectPath(Class clazz) {
        return getServiceProjectPath(clazz);
    }
}
