import com.sun.istack.internal.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFieldPrimer {
    private Class<?> structureClass;
    private Class<?> contentsClass;
    private List<Field> allFields;
    private List<Field> connectedNodeFields;
    private Field contentField;

    public ListFieldPrimer(Class<?> structureClass, Class<?> contentsClass) {
        this(structureClass, contentsClass, structureClass);
    }

    public ListFieldPrimer(Class<?> structureClass, Class<?> contentsClass, Class<?> inclusiveParent) {
        this.structureClass = structureClass;
        this.contentsClass = contentsClass;
        init(inclusiveParent);
    }

    private void init(Class<?> inclusiveParent) {
        Iterable<Field> allFieldsIterable = getFieldsUpTo(structureClass, inclusiveParent);
        allFields = new ArrayList<>();
        connectedNodeFields = new ArrayList<>();
        for (Field field : allFieldsIterable) {
            field.setAccessible(true);
            allFields.add(field);
            Class<?> fieldType = field.getType();
            if (fieldType.isAssignableFrom(contentsClass)) {
                if (contentField != null)
                    throw new IllegalArgumentException("Class argument structureClass has more than one content field");
                contentField = field;
            }
            else if (fieldType.isAssignableFrom(structureClass)) {
                connectedNodeFields.add(field);
            }
        }
    }

    private static Iterable<Field> getAllFieldsFromHierarchyOf(Class<?> startClass) {
        return getFieldsUpTo(startClass, null);
    }

    /**
     * Gets all fields in a specified range of a <code>class</code>'s hierarchy
     *
     * @param startClass the lowest class in the class hierarchy to get fields from
     * @param inclusiveParent the class in the hierarchy that the method will recur to
     * @return all fields in a specified range of a class hierarchy
     */
    private static Iterable<Field> getFieldsUpTo(Class<?> startClass,
                                                 @Nullable Class<?> inclusiveParent) {
        Field[] startClassFields = startClass.getDeclaredFields();

        List<Field> currentClassFields = new ArrayList<Field>(Arrays.asList(startClassFields));

        Class<?> parentClass = startClass.getSuperclass();
        if (parentClass == null) return currentClassFields;
        if (parentClass.equals(inclusiveParent)) {
            currentClassFields.addAll(Arrays.asList(parentClass.getDeclaredFields()));
            return currentClassFields;
        }
        List<Field> parentClassFields =
                (List<Field>) getFieldsUpTo(parentClass, inclusiveParent);
        currentClassFields.addAll(parentClassFields);

        return currentClassFields;
    }


    public List<Field> getAllFields() {
        return allFields;
    }

    public List<Field> getConnectedNodeFields() {
        return connectedNodeFields;
    }

    public Field getContentField() {
        return contentField;
    }
}
