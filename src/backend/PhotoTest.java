/**
 * 
 */
package backend;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 *A test for Photo class.
 *
 */
public class PhotoTest {
	
	/**
	 * The file type of the photo to use the paths and other attributes.
	 */
    public File file;
    
    /** The directory in which the test fill will be created. */
	private final static String TEST_PATH = System.getProperty("user.dir") + File.separator + "src";
	
	/** 
	 * The instance of photo to be tested.
	 */
	private Photo testPhoto;


	/**
	 * Make a new file photo for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception{
		new File(TEST_PATH, "testPhoto.png").createNewFile();
		file = new File(TEST_PATH + File.separator + "testPhoto.png");
		testPhoto = new Photo(file, "testPhoto", "png");
	}

	/**
	 * Terminate or delete the created photo file after each test.
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		File photoFile = new File(TEST_PATH + File.separator + testPhoto.getTaggedName() + ".png");
		photoFile.delete();
	}
	

	/**
	 * Test add method for {@link backend.Photo#add(java.lang.String)} which only one tag will be added.
	 */
	@Test
	public void testAddForOneTag() {
		testPhoto.add("a");
		String expectedName = "testPhoto @a.png";
		String actualName = testPhoto.getTaggedName() + ".png";
		assertEquals("The names don't match", expectedName, actualName);
		
		int actualSizeOfTags = testPhoto.getTags().size();
		assertEquals("The size of the tags don't match", 1, actualSizeOfTags);
	}
	
	/**
	 * Test adding the same tag more than one time for add method {@link backend.Photo#add(java.lang.String)}.
	 */
	@Test
	public void testAddForSameTag() {
		testPhoto.add("a");
		testPhoto.add("a");
		String expectedName1 = "testPhoto @a.png";
		String actualName1 = testPhoto.getTaggedName() + ".png";
		assertEquals("The names don't match", expectedName1, actualName1);
		
		testPhoto.add("a");
		
		String expectedName2 = expectedName1;
		String actualName2 = testPhoto.getTaggedName() + ".png";
		assertEquals("The anmes don't match", expectedName2, actualName2);
		
		int actualSizeOfTags = testPhoto.getTags().size();
		assertEquals("The size of the tags don't match", 1, actualSizeOfTags);
	}
	
	/**
	 * Test adding multiple tags to a photo for add method {@link backend.Photo#add(java.lang.String)}.
	 */
	@Test
	public void testAddForMultipleTags() {
		testPhoto.add("a");
		testPhoto.add("b");
		testPhoto.add("c");
		String expectedName = "testPhoto @a @b @c.png";
		String actualName = testPhoto.getTaggedName() + ".png";
		assertEquals("The names don't match", expectedName, actualName);
		
		int actualSizeOfTags = testPhoto.getTags().size();
		assertEquals("The size of the tags don't match", 3, actualSizeOfTags);
	}

	/**
	 * Test removing a tag from a non-tagged photo for remove method {@link backend.Photo#remove(java.lang.String)}.
	 */
	@Test
	public void testRemoveForNoTags() {
		testPhoto.remove("a");
		String expectedName = "testPhoto.png";
		String actualName = testPhoto.getTaggedName() + ".png";
		assertEquals("The names don't match after removing "
				+ "from a non-tagged photo", expectedName, actualName);
		
		int actualSizeOfTags = testPhoto.getTags().size();
		assertEquals("The size of the tags don't match", 0, actualSizeOfTags);
		
	}
	
	/**
	 * Test removing a tag from a tagged photo but the tags don't match. A test for remove method
	 * {@link backend.Photo#remove(java.lang.String)}.
	 */
	@Test
	public void testRemoveForNonMatchingTags() {
		testPhoto.add("a");
		testPhoto.remove("b");
		String expectedName = "testPhoto @a.png";
		String actualName = testPhoto.getTaggedName() + ".png";
		assertEquals("The names don't match", expectedName, actualName);
		
		int actualSizeOfTags = testPhoto.getTags().size();
		assertEquals("The size of the tags don't match", 1, actualSizeOfTags);	
	}
	
	/**
	 * Test removing a tag from a tagged photo for remove method {@link backend.Photo#remove(java.lang.String)}.
	 */
	@Test
	public void testRemoveForOneTag() {
		testPhoto.add("a");
		testPhoto.remove("a");
		String expectedName = "testPhoto.png";
		String actualName = testPhoto.getTaggedName() + ".png";
		assertEquals("The names don't match", expectedName, actualName);
		
		int actualSizeOfTags = testPhoto.getTags().size();
		assertEquals("The size of the tags don't match", 0, actualSizeOfTags);
			
	}
	
	/**
	 * Test removing multiple tags from a photo for remove method {@link backend.Photo#remove(java.lang.String)}.
	 */
	@Test
	public void testRemoveForMultipleTags() {
		testPhoto.add("a");
		testPhoto.add("b");
		testPhoto.add("c");
		testPhoto.remove("a");
		testPhoto.remove("c");
		testPhoto.remove("b");
		String expectedName = "testPhoto.png";
		String actualName = testPhoto.getTaggedName() + ".png";
		assertEquals("The names don't match", expectedName, actualName);
		
		int actualSizeOfTags = testPhoto.getTags().size();
		assertEquals("The size of the tags don't match", 0, actualSizeOfTags);
			
	}

	/**
	 * Test method for {@link backend.Photo#revertToDate(java.util.Date)}.
	 */
	@Test
	public void testRevertToDate() {
		testPhoto.add("a");
		testPhoto.add("b");
		testPhoto.add("c");
		testPhoto.revertToDate(testPhoto.getDatesSorted().get(0));
		String expectedName = "testPhoto @a.png";
		String actualName = testPhoto.getTaggedName() + ".png";
		assertEquals("The names don't match", expectedName, actualName);
		
		int actualSizeOfTags = testPhoto.getTags().size();
		assertEquals("The size of the tags don't match", 1, actualSizeOfTags);
			
		
	}

}
