import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ListPrimerTest {
    @Test
    public void testGetNormalLinkedList() throws IllegalAccessException {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        ListPrimer<LinkedList> test = new ListPrimer<LinkedList>(list, Integer.class);
        List<Object> x = test.getList();
        for (int i = 0; i < x.size(); i++) {
            assertEquals(i, x.get(i));
        }
    }

    @Test
    public void testGetCircularLinkedList() throws IllegalAccessException {
        CircularLinkedList<Integer> foo = new CircularLinkedList<>(0);
        for (int i = 1; i < 10; i++) {
            foo.add(i);
        }

        ListPrimer<LinkedList> test = new ListPrimer<LinkedList>(foo, Integer.class, null);
        List<Object> listOne = test.getList();
        for (int i = 0; i < listOne.size(); i++) {
            assertEquals(i, listOne.get(i));
        }
    }
}