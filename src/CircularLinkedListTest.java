import org.junit.Test;

import static org.junit.Assert.*;

public class CircularLinkedListTest {
    @Test
    public void testAdd() {
        CircularLinkedList<Integer> foo = new CircularLinkedList<>(5);
        assertEquals(5, (int)foo.getContents());
        foo.add(6);
        assertEquals(6, (int)foo.getNext().getContents());
        assertEquals(5, (int)foo.getNext().getNext().getContents());
    }
}