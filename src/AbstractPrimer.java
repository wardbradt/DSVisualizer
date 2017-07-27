import java.util.Collection;

public abstract class AbstractPrimer<T> {
    Class<?> structureClass;
    Class<?> contentsClass;
    Object structureInstance;
    ListFieldPrimer fieldPrimer;
    private Object contents;

    public AbstractPrimer(Object structureInstance, Class<?> contentsClass) throws IllegalAccessException {
        this(structureInstance, contentsClass, structureInstance.getClass());
    }

    public AbstractPrimer(Object structureInstance, Class<?> contentsClass, Class<?> inclusiveParent) throws IllegalAccessException {
        structureClass = structureInstance.getClass();
        this.contentsClass = contentsClass;
        this.structureInstance = structureInstance;
        this.fieldPrimer = new ListFieldPrimer(structureClass, contentsClass, inclusiveParent);
    }

    public Class<?> getStructureClass() {
        return structureClass;
    }

    public Class<?> getContentsClass() {
        return contentsClass;
    }

    public abstract Collection<Object> getList() throws IllegalAccessException;
}
