import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class AbstractFieldPrimer {
    private Class<?> structureClass;
    private Class<?> contentsClass;
    private ArrayList<Field> allFields;
    private ArrayList<Field> connectedNodeFields;
    private Field contentField;
    private ArrayList<Field> miscellaneousFields;

    public AbstractFieldPrimer(Class<?> structureClass, Class<?> contentsClass) {
        this.structureClass = structureClass;
        this.contentsClass = contentsClass;
    }

    public ArrayList<Field> getAllFields() {
        return allFields;
    }

    public ArrayList<Field> getConnectedNodeFields() {
        return connectedNodeFields;
    }

    public Field getContentField() {
        return contentField;
    }

    public ArrayList<Field> getMiscellaneousFields() {
        return miscellaneousFields;
    }
}
