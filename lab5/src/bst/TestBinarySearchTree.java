package bst;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestBinarySearchTree {
	BinarySearchTree<Integer> bst;

	@BeforeEach
	void setUp() throws Exception {
		bst = new BinarySearchTree<Integer>();
	}

	@AfterEach
	void tearDown() throws Exception {
		bst = null;
	}

	@Test
	void testAdd() {
		assertTrue("Returnerar false vid insättning av nytt element, förväntar true", bst.add(10));
		assertTrue("Returnerar false vid insättning av nytt element, förväntar true", bst.add(20));
		assertFalse("Returnerar true vid insättnig av dubblett, förväntar false", bst.add(10));
	}

	@Test
	void testHeight() {
		assertTrue("Fel höjd", bst.height() == 0);
		bst.add(10);
		bst.add(15);
		bst.add(5);
		assertTrue("Fel höjd", bst.height() == 2);
		bst.add(20);
		bst.add(18);
		assertTrue("Fel höjd", bst.height() == 4);
	}

	@Test
	void testSize() {
		bst = new BinarySearchTree<Integer>();
		assertTrue("Fel storlek på träd", bst.size == 0);
		bst.add(10);
		bst.add(10);
		assertTrue("Fel storlek på träd", bst.size == 1);
		
	}
}
