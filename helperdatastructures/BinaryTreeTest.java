import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    @Test
    public void testisEmpty() {
      BinaryTree<Integer> test = new BinaryTree<Integer>();
      assertTrue(test.isEmpty());
      test.add(0);
      assertFalse(test.isEmpty());
    }
  
    @Test
    public void testAdd() {
        BinaryTree<String> breadthTester = new BinaryTree<String>();
        // add method needs to work before this can work.
        breadthTester.add("foobar");
        assertEquals("foobar", breadthTester.getContents());
        breadthTester.add("foobar2");
        assertEquals("foobar2", breadthTester.getLeft().getContents());

        breadthTester.add("foobar3");
        assertEquals("foobar3", breadthTester.getRight().getContents());
        breadthTester.add("foobar4");
        assertEquals("foobar4", breadthTester.getLeft().getLeft().getContents());
        breadthTester.add("foobar5");
        assertEquals("foobar5", breadthTester.getLeft().getRight().getContents());
    }

    @Test
    public void testContains() {
        BinaryTree<Integer> test = new BinaryTree<>(3);
        assertTrue(test.contains(3));
        assertFalse(test.contains(4));
        test.add(5);
        assertTrue(test.contains(5));
    }

    @Test
    public void testRemove() {
        BinaryTree<String> removeTester = new BinaryTree<>();
        removeTester.add("foobar");
        assertTrue(removeTester.contains("foobar"));

        removeTester.remove("foobar");
        try {
            removeTester.remove("foobar");
            assertTrue(false);
        } catch (NoSuchElementException expected) {
        }

        removeTester.add("foobar2");
        try {
            removeTester.remove("foobar");
            assertTrue(false);
        } catch (NoSuchElementException expected) {
        }

        // Test if it removes the first breadth first traversal.
        removeTester.clear();
        assertTrue(removeTester.isEmpty());
        removeTester.add("foobar1");
        removeTester.add("foobar2");
        removeTester.add("foobar3");
        removeTester.add("foobar4");
        removeTester.add("foobar5");
        removeTester.add("foobar6");
        removeTester.add("foobar7");
        removeTester.add("foobar8");
        removeTester.remove("foobar7");

        // The array is of length 8 because the root BinaryTree (removeTester)'s contents == null.
        assertEquals(8, removeTester.breadthFirst().size());
        String[] testArr = new String[removeTester.breadthFirst().size()];
        int count = 0;
        for (String i : removeTester.breadthFirst()) {
            testArr[count] = i;
            count++;
        }

        assertNotEquals("foobar1", testArr[1]);
        assertNotEquals("foobarG", testArr[6]);
        assertEquals("foobar4", testArr[3]);

        removeTester.remove("foobar4");
        testArr = new String[removeTester.breadthFirst().size()];
        count = 0;
        for (String i : removeTester.breadthFirst()) {
            testArr[count] = i;
            count++;
        }
        assertNotEquals("foobar4", testArr[3]);
    }

//    @Test
//    public void testGet() {
//        BinaryTree<String> getTester = new BinaryTree<>();
//        getTester.add("Hello world!");
//        try {
//            getTester.get("Hello");
//            assertTrue(false);
//        } catch (NullPointerException expected){
//        }
//        // getTester.remove("Hello world!");
//        try {
//            getTester.get("Hello world!");
//        } catch (NullPointerException expected) {
//        }
//    }

    @Test
    public void testBreadthFirst() {
        BinaryTree<String> breadthTester = new BinaryTree<String>();
        // add method needs to work before this can work.
        breadthTester.add("foobar1");
        breadthTester.add("foobar2");
        breadthTester.add("foobar3");
        breadthTester.add("foobar4");
        breadthTester.add("foobar5");

        int index = 1;
        for (String i: breadthTester.breadthFirst()) {
            assertEquals("foobar" + index, i);
            index++;
        }
    }

    @Test
    public void testDepthFirst() {
       	BinaryTree<String> depthTester = new BinaryTree<String>();
       	depthTester.add("foobar");
       	depthTester.add("foobar2");
       	depthTester.add("foobar3");
        depthTester.add("foobar4");
        depthTester.add("foobar5");
        depthTester.add("foobar6");
        depthTester.add("foobar7");
        depthTester.add("foobar8");

        String[] str = new String[10];
        int count = 0;
        for (String i : depthTester.depthFirst()) {
            str[count] = i;
            count++;
           }
        assertEquals("foobar", str[0]);
        assertEquals("foobar2", str[1]);
        assertEquals("foobar4", str[2]);
        assertEquals("foobar8", str[3]);
        assertEquals("foobar5", str[4]);
        assertEquals("foobar3", str[5]);
        assertEquals("foobar6", str[6]);
        assertEquals("foobar7", str[7]);
    }
}