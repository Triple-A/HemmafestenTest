package se.chalmers.hemmafesten.test;

import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParseObject;

import se.chalmers.hemmafesten.model.Song;
import junit.framework.TestCase;
import static android.test.MoreAsserts.*;

public class SongTest extends TestCase {
	public SongTest() {
		super();
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
		
		songParseObject.put("spotifyURI", "spotify:track:2dCOZVuajBifNSWezNsuf3");
		assertEquals("spotify:track:2dCOZVuajBifNSWezNsuf3", song.getSpotifyURI());
	}
	
	// Equality of two songs are determined by their Spotify URI.
	public void testInequality() {
		Song songA = new Song();
		Song songB = new Song();
		
		// Should NOT be equal as both objects’ spotifyURI is null.
		songA.setName("First Name");
		songB.setName("First Name");
		assertNotEqual(songA, songB);
		
		// Should NOT be equal as one of the objects’ spotifyURI is null.
		songA.setSpotifyURI("spotify:track:2dCOZVuajBifNSWezNsuf3");
		assertNotEqual(songA, songB);
	}
	
	public void testEquals() {
		Song songA = new Song();
		Song songB = new Song();
		
		songA.setName("First Name");
		songB.setName("Second Name");
		
		// Should be equal as both objects’s spotifyURI is "spotify:track:2dCOZVuajBifNSWezNsuf3"
		// even though their name’s are different.
		songA.setSpotifyURI("spotify:track:2dCOZVuajBifNSWezNsuf3");
		songB.setSpotifyURI("spotify:track:2dCOZVuajBifNSWezNsuf3");
		assertEquals(songA, songB);
	}
	
	public void testHashCode() {
		Song songA = new Song();
		Song songB = new Song();
		Song songC = new Song();
		
		songA.setName("First Name");
		songB.setName("Second Name");
		songC.setName("First Name");
		
		songA.setSpotifyURI("spotify:track:2dCOZVuajBifNSWezNsuf3");
		songB.setSpotifyURI("spotify:track:2dCOZVuajBifNSWezNsuf3");
		songC.setSpotifyURI("spotify:track:3YrIjupEa0IWX0LZcqdEaU");
		
		// Song A and B should have same hash as they are the same song.
		// Even though they have different names, they still point to the
		// same song on Spotify.
		assertEquals(songA.hashCode(), songB.hashCode());
		
		// Song A and C should not have the same hash as they point to
		// different Spotify songs.
		assertNotEqual(songA.hashCode(), songC.hashCode());
	}
	
	public void testUpdateWithSpotifyJSONObject() {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject("{\"album\": {\"released\": \"2010\", \"href\": \"spotify:album:5AN6A9IR1g1xRgY0RoKOsT\", \"name\": \"Hjerteknuser\", \"availability\": {\"territories\": \"NO\"}}, \"name\": \"Hjerteknuser\", \"popularity\": \"0.63105\", \"external-ids\": [{\"type\": \"isrc\", \"id\": \"NOHDL1002070\"}], \"length\": 199.407, \"href\": \"spotify:track:6dKWi7apHjn2W7Ojncv4Wu\", \"artists\": [{\"href\": \"spotify:artist:1s1DnVoBDfp3jxjjew8cBR\", \"name\": \"Kaizers Orchestra\"}, {\"href\": \"spotify:artist:2rvUlmaAfN7eKSVAcaRj9t\", \"name\": \"Vinni\"}], \"track-number\": \"1\"}");
		} catch (JSONException e) { e.printStackTrace(); }
		// Make sure we don’t fail before our real tests.
		assertNotNull(jsonObject);
		
		try {
			Song song = new Song();
			song.updateWithSpotifyJSONObject(jsonObject);
			
			assertEquals("Hjerteknuser", song.getName());
			assertEquals("Hjerteknuser", song.getAlbumName());
			assertEquals("Kaizers Orchestra, Vinni", song.getArtistName());
			
			assertEquals("spotify:track:6dKWi7apHjn2W7Ojncv4Wu", song.getSpotifyURI());
			assertEquals("spotify:album:5AN6A9IR1g1xRgY0RoKOsT", song.getAlbumURI());
			
			assertEquals(199.407, song.getLength());
			assertEquals(0.63105, song.getPopularity());
		} catch (JSONException e) {
			fail("updateWithSpotifyJSONObject() should not throw any exception for a valid JSON object, got " + e.getMessage());
		}
	}
}
