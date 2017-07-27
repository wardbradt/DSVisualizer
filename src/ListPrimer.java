import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wardbradt on 7/3/17.
 * Later: for list structures that inherit fields from a hierarchy, base the technique for retrieving fields off:
 * https://stackoverflow.com/questions/16966629/what-is-the-difference-between-getfields-and-getdeclaredfields-in-java-reflectio
 *
 * TODO make user pick which fields are which/ which to disregard?
 * Sources used:
 * https://stackoverflow.com/questions/13400075/reflection-generic-get-field-value
 * https://stackoverflow.com/questions/17105167/create-array-of-objects-at-run-time-using-class-name-in-java
 * http://blog.xebia.com/acessing-generic-types-at-runtime-in-java/
 */
public class ListPrimer<T> {
    // the class of structureInstance
    private Class<?> structureClass;
    private Class<?> contentsClass;
    private Object structureInstance;
    private ListFieldPrimer fieldPrimer;
    // todo later: set the contents and next in the constructor for easier getList() function and other efficiency
    private Object contents;

    public ListPrimer(Object structureInstance, Class<?> contentsClass) throws IllegalAccessException {
        this(structureInstance, contentsClass, structureInstance.getClass());
    }

    public ListPrimer(Object structureInstance, Class<?> contentsClass, Class<?> inclusiveParent) throws IllegalAccessException {
        structureClass = structureInstance.getClass();
        if (structureClass.getTypeParameters().length != 1) throw new IllegalArgumentException("Class must have one type parameter");
        this.contentsClass = contentsClass;

        this.structureInstance = structureInstance;
        this.fieldPrimer = new ListFieldPrimer(structureClass, contentsClass, inclusiveParent);
        // connecting fields are object(s) whose class as structureInstance typically named "next" (and "previous" if there are two)
        if (fieldPrimer.getConnectedNodeFields().size() > 2 || fieldPrimer.getConnectedNodeFields().size() < 1) throw new IllegalArgumentException("Class must have one or two connecting fields");
        contents = fieldPrimer.getContentField().get(structureInstance);
    }

    /**
     * Be careful using this constructor
     * @param copyList
     * @param structureInstance
     */
    private ListPrimer(ListPrimer<T> copyList, T structureInstance) {
        structureClass = structureInstance.getClass();

        this.structureInstance = structureInstance;
        this.contentsClass = copyList.contentsClass;
        this.fieldPrimer = copyList.fieldPrimer;
    }

    public List<Object> getList() throws IllegalAccessException {
        List<Object> result = new ArrayList<>();
        ListPrimer<T> iterativeCopy = this;

        // while the connected node does not equal null (just in case
        while (iterativeCopy.getNext() != null) {
            result.add(iterativeCopy.getContents());
            iterativeCopy = new ListPrimer<T>(this, iterativeCopy.getNext());
            // if we have iterated through/ looped around
            if (iterativeCopy.getNext() == this.getNext()) break;
        }

        return result;
    }

    /**
     * TODO make this more efficient by removing the first for loop and setting the fields accessible in each if statement
     * @return
     * @throws IllegalAccessException
     */
    public T getNext() throws IllegalAccessException {
        List<Field> connectedFields = fieldPrimer.getConnectedNodeFields();
        return (T)connectedFields.get(0).get(structureInstance);
//        if (connectedFields.size() == 1) {
//            connectedFields.get(0).setAccessible(true);
//            return (T)connectedFields.get(0).get(structureInstance);
//        }
//        if (connectedFields.size() == 2) {
//            for (int i = 0; i < connectedFields.size(); i++) {
//                connectedFields.get(i).setAccessible(true);
//            }
//        }
//        throw new IllegalAccessException("Can only have two connecting fields: one for the next node and for an optional previous node");
//
//        if (connectedFields.size() == 1) {
//            return (T)connectedFields.get(0).get(structureInstance);
//        }
//        // if arrives here, connectedFields.size() == 2. else a fatal error has occured.
//        else {
//            for (int i = 0; i < connectedFields.size(); i++) {
//                T fieldValueAtI = (T)connectedFields.get(i).get(structureInstance);
//                if (contentsArrayList.contains(fieldValueAtI)) {
//
//                }
//            }
//        }
//        return null;
    }

    public Object getContents() throws IllegalAccessException {
        Field contentField = fieldPrimer.getContentField();
        return contentField.get(structureInstance);
    }
}
