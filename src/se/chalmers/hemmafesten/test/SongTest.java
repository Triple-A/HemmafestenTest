package se.chalmers.hemmafesten.test;

import com.parse.ParseObject;

import se.chalmers.hemmafesten.model.Song;
import junit.framework.TestCase;

public class SongTest extends TestCase {
	public SongTest() {
		super();
	}

	public void setUp() {
		
	}
	
	public void testParseIntegration() {
		Song song = new Song();
		assertEquals("Song", song.getParseObject().getClassName());
		
		ParseObject songParseObject = song.getParseObject();
		
		songParseObject.put("name", "The Song Name");
		assertEquals("The Song Name", song.getName());
		
		songParseObject.put("album", "The Album Name");
		assertEquals("The Album Name", song.getAlbumName());
		
		songParseObject.put("artist", "The Artist Name");
		assertEquals("The Artist Name", song.getArtistName());
	}
	
	
}
