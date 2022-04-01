package webserver;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ViewResolver {

    private static final String LIST_VIEW_TAG_START = "{{^%s}}";
    private static final String LIST_VIEW_TAG_END = "{{/%s}}";
    private static final String VALUE_TAG = "{{%s}}";

    private final String viewResourcePath;

    public ViewResolver(String viewResourcePath) {
        this.viewResourcePath = viewResourcePath;
    }

    public String resolve(String viewName, Map<String, Object> model) throws IOException {
        String file = Files.readString(Path.of(viewResourcePath + viewName));

        for (String name : model.keySet()) {
            Object value = model.get(name);
            if (value instanceof List) {
                file = resolveList(file, name, (List)value);
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

    private String parseLoopTarget(String file, String name) {
        String startTag = String.format(LIST_VIEW_TAG_START, name);
        int startIndex = file.indexOf(startTag);
        int endIndex = file.indexOf(String.format(LIST_VIEW_TAG_END, name));

        return file.substring(startIndex + startTag.length(), endIndex);
    }

    private String resolveObjectFields(String loopTarget, Field[] declaredFields, Object object) {
        try {
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Object value = declaredField.get(object);

                String replaceTarget = String.format(VALUE_TAG, declaredField.getName());
                if (loopTarget.contains(replaceTarget) && value != null) {
                    loopTarget = loopTarget.replace(replaceTarget, value.toString());
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return loopTarget;
    }

    private String removeListViewTags(String file, String name) {
        file = file.replace(String.format(LIST_VIEW_TAG_START, name), "");
        return file.replace(String.format(LIST_VIEW_TAG_END, name), "");
    }
}
