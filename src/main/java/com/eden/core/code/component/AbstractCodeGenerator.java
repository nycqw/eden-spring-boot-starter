package com.eden.core.code.component;

import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;

/**
 * @author chenqw
 * @version 1.0
 * @since 2020/5/17
 */
public abstract class AbstractCodeGenerator {

    public void create(Class clazz, String dir, String prefix, String suffix, String type) throws IOException {
        File file = createFile(clazz, dir, prefix, suffix, type);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        ) {
            List<String> fileContent = buildContent(clazz, dir, prefix, suffix);
            for (String line : fileContent) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (Exception ignore) {
        }
    }

    private File createFile(Class clazz, String dir, String prefix, String suffix, String type) throws IOException {
        String moduleName = getModuleName(clazz);
        String apiProjectPath = getProjectPath(clazz);
        String fileName = getSimpleFileName(prefix, moduleName, suffix, type);

        File file = new File(apiProjectPath + dir + "/" + moduleName + "/" + fileName);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        System.out.println("File => " + file.getAbsolutePath());
        return file;
    }

    public abstract List<String> buildContent(Class clazz, String dir, String prefix, String suffix);

    public abstract String getProjectPath(Class clazz);

    static String getApiProjectPath(Class clazz) {
        String path = getPackagePath(clazz);
        String userDir = System.getProperty("user.dir").replace("\\", "/");
        String projectName = userDir.substring(userDir.lastIndexOf("/"));
        return userDir + projectName + "-api/src/main/java/" + path;
    }

    static String getServiceProjectPath(Class clazz) {
        String userDir = System.getProperty("user.dir").replace("\\", "/");
        String projectName = userDir.substring(userDir.lastIndexOf("/"));

        String path = getPackagePath(clazz);
        return userDir + projectName + "-service/src/main/java/" + path;
    }

    static String getResourcePath() {
        String userDir = System.getProperty("user.dir").replace("\\", "/");
        String projectName = userDir.substring(userDir.lastIndexOf("/"));
        return userDir + projectName + "-service/src/main/resources";
    }

    static String getPackagePath(Class clazz) {
        String packageName = clazz.getPackage().getName();
        return packageName.substring(0, packageName.lastIndexOf(".")).replace(".", "/");
    }

    static String getModuleName(Class clazz) {
        String simpleName = clazz.getSimpleName();
        String name = simpleName.substring(0, simpleName.indexOf("Entity"));
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    static String getSimpleFileName(String prefix, String moduleName, String suffix, String type) {
        StringBuilder builder = new StringBuilder();
        if (!StringUtils.isEmpty(prefix)) {
            builder.append(prefix);
        }
        builder.append(moduleName.substring(0, 1).toUpperCase()).append(moduleName.substring(1));
        if (!StringUtils.isEmpty(suffix)) {
            builder.append(suffix.substring(0, 1).toUpperCase()).append(suffix.substring(1));
        }
        if (!StringUtils.isEmpty(type)) {
            builder.append(".").append(type);
        }
        return builder.toString();
    }

}
