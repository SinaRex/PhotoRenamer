/**
 * 
 */
package backend;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A unit test for PhotoManager class.
 *
 */
public class PhotoManagerTest {
	
	/**
	 * The instance of photoManager to be used for testing
	 */
	private PhotoManager photoManager;


	/** The directory in which the test files fill will be created. */
	private final static String TEST_PATH = System.getProperty("user.dir") + File.separator + "src";
	
	/** 
	 * The instance of the first photo to be tested.
	 */
	private Photo testPhoto1;
	
	/** 
	 * The instance of the second photo to be tested.
	 */
	private Photo testPhoto2;
	
	/** 
	 * The instance of the third photo to be tested.
	 */
	private Photo testPhoto3;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String serFilePath = System.getProperty("user.dir") + File.separator + 
			    "bin" + File.separator + "data" + File.separator + "data.ser";
	    File serFile = new File(serFilePath);
	    serFile.delete();
	    
		new File(TEST_PATH, "testPhoto1.jpeg").createNewFile();

		File file = new File(TEST_PATH + File.separator + "testPhoto1.jpeg");
		testPhoto1 = new Photo(file, "testPhoto1", "jpeg");
		
		new File(TEST_PATH, "testPhoto2.jpeg").createNewFile();
		file = new File(TEST_PATH + File.separator + "testPhoto2.jpeg");
		testPhoto2 = new Photo(file, "testPhoto2", "jpeg");
		  
		new File(TEST_PATH, "testPhoto3.jpeg").createNewFile();
		file = new File(TEST_PATH + File.separator + "testPhoto3.jpeg");
		testPhoto3 = new Photo(file, "testPhoto3", "jpeg");
		
		File directoryFile = new File(TEST_PATH);
		photoManager = new PhotoManager(directoryFile);
		photoManager.openDirectory();
		
	}

	/**
	 * After the test is done delete the ser and photo file.
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		File photoFile1 = new File(TEST_PATH + File.separator + testPhoto1.getTaggedName() + ".jpeg");
		File photoFile2 = new File(TEST_PATH + File.separator + testPhoto2.getTaggedName() + ".jpeg");
		File photoFile3 = new File(TEST_PATH + File.separator + testPhoto3.getTaggedName() + ".jpeg");
		photoFile1.delete();
		photoFile2.delete();
		photoFile3.delete();
		
		String serFilePath = System.getProperty("user.dir") + File.separator + 
			    "bin" + File.separator + "data" + File.separator + "data.ser";
		File serFile = new File(serFilePath);
		serFile.delete();
		
		
	}

	/**
	 * Test method for {@link backend.PhotoManager#getAllCurrentTags()} where we test
     * if the method returns the right list.
	 */
	@Test
	public void testGetAllCurrentTags() {
		testPhoto1 = photoManager.lookForPhoto("testPhoto1");
		testPhoto1.add("a");
		testPhoto1.add("b");
		
		testPhoto2 = photoManager.lookForPhoto("testPhoto2");
		testPhoto2.add("b");
		testPhoto2.add("c");
		photoManager.saveToFile();
		
		ArrayList<String> actualResult = photoManager.getAllCurrentTags();
		String[] expectedList = {"a", "b", "c"};
		boolean[] expected_ = {false, false, false};
		System.out.println(photoManager.directoryToPhotos);
		assertEquals("the size should be 3", expectedList.length, actualResult.size());
		  
		for (int i = 0; i < expectedList.length; i ++) {
		    boolean result = actualResult.contains(expectedList[i]);
		    expected_[i] = result;
		}
		  
		for (boolean res : expected_) {
			assertEquals("doesn't contain all the tags", true, res);
		}
		
	}

	/**
	 * Test method for {@link backend.PhotoManager#getMostCommonTag()} where we test
     * if we are returning the right tag.
	 */
	@Test
	public void testGetMostCommonTag() {
		testPhoto1 = photoManager.lookForPhoto("testPhoto1");
		testPhoto1.add("a");
		testPhoto1.add("b");
		
		testPhoto2 = photoManager.lookForPhoto("testPhoto2");
		testPhoto2.add("b");
		testPhoto2.add("c");
		
		testPhoto3 = photoManager.lookForPhoto("testPhoto3");
		testPhoto3.add("b");
		testPhoto2.add("gg");
		photoManager.saveToFile();
		
		String actual = photoManager.getMostCommonTag();
		assertEquals("did not get the right most common tag", "b", actual);
	}

	/**
	 * Test method for {@link backend.PhotoManager#getMostTaggedPhoto()} where we test
     * if we are returning the right photo.
	 */
	@Test
	public void testGetMostTaggedPhoto() {
		testPhoto1 = photoManager.lookForPhoto("testPhoto1");
		testPhoto1.add("a");
		testPhoto1.add("b");
		testPhoto1.add("c");
		
		testPhoto2 = photoManager.lookForPhoto("testPhoto2");
		testPhoto2.add("b");
		testPhoto2.add("c");
		
		testPhoto3 = photoManager.lookForPhoto("testPhoto3");
		testPhoto3.add("b");
		photoManager.saveToFile();
		
		String actual = photoManager.getMostTaggedPhoto().getTaggedName();
		assertEquals("did not get the right photo", "testPhoto1 @a @b @c", actual);
		
	}

}
