package webserver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ViewResolver {

    private final String viewResourcePath;
    private static final String listViewTagStart = "{{^%s}}";
    private static final String listViewTagEnd = "{{/%s}}";

    public ViewResolver(String viewResourcePath) {
        this.viewResourcePath = viewResourcePath;
    }

    public String resolve(String viewName, Map<String, Object> model) throws IOException {
        String file = Files.readString(Path.of(viewResourcePath + viewName));

        for (String name : model.keySet()) {
            Object value = model.get(name);

            if (value instanceof List) {
                file = resolveList(file, name, (List) value);
                continue;
            }
        }

        return file;
    }

    private String resolveList(String file, String name, List list) {
        String loopTarget = parseLoopTarget(file, name);
        StringBuilder loopResolvedResult = new StringBuilder();

        for (Object value : list) {
            Class<?> objClass = value.getClass();
            loopResolvedResult.append(resolveObjectFields(loopTarget, objClass.getDeclaredFields(), value));
        }

        file = removeListViewTags(file, name);

        return file.replace(loopTarget, loopResolvedResult.toString());
    }

    private String resolveObjectFields(String loopTarget, Field[] declaredFields, Object object) {
        String valueTag = "{{%s}}";

        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Object value = declaredField.get(object);

                String replaceTarget = String.format(valueTag, declaredField.getName());
                if (loopTarget.contains(replaceTarget) && value != null) {
                    loopTarget = loopTarget.replace(replaceTarget, value.toString());
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return loopTarget;
    }

    private String parseLoopTarget(String file, String name) {
        String startTag = String.format(listViewTagStart, name);
        int startIndex = file.indexOf(startTag);
        int endIndex = file.indexOf(String.format(listViewTagEnd, name));

        return file.substring(startIndex + startTag.length(), endIndex);
    }

    private String removeListViewTags(String file, String name) {
        file = file.replace(String.format(listViewTagStart, name), "");
        return file.replace(String.format(listViewTagEnd, name), "");

    }
}
