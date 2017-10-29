import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class TreePrimer<T> extends AbstractPrimer<T> {
    public TreePrimer(Object structureInstance, Class<?> contentsClass) throws IllegalAccessException {
        this(structureInstance, contentsClass, structureInstance.getClass());
    }

    public TreePrimer(Object structureInstance, Class<?> contentsClass, Class<?> inclusiveParent) throws IllegalAccessException {
        super(structureInstance, contentsClass, inclusiveParent);
        if (structureClass.getTypeParameters().length != 1) throw new IllegalArgumentException("Class must have one type parameter");
        if (fieldPrimer.getConnectedNodeFields().size() < 2) throw new IllegalArgumentException("Class must have one or two connecting fields");
    }

    @Override
    public Collection<Object> getList() throws IllegalAccessException {
        return null;
    }
}
