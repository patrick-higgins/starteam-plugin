package hudson.plugins.starteam;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.starteam.ServerInfo;

/**
 * @author John McNair <john@mcnair.org>
 */
public class StarTeamConnectionTest {

	@Test
	public void equals() {
		assertFalse("Null is not equal", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null).equals(null));
		assertFalse("Different types are not equal", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null).equals(new Object()));

		assertThat("Different hosts", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null),
				is(not(new StarTeamConnection("2", 1, "1", "1", "1", "1", "1", null))));
		assertThat("Different ports", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null),
				is(not(new StarTeamConnection("1", 2, "1", "1", "1", "1", "1", null))));
		assertThat("Different users", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null),
				is(not(new StarTeamConnection("1", 1, "2", "1", "1", "1", "1", null))));
		assertThat("Different passwords", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null),
				is(not(new StarTeamConnection("1", 1, "1", "2", "1", "1", "1", null))));
		assertThat("Different projects", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null),
				is(not(new StarTeamConnection("1", 1, "1", "1", "2", "1", "1", null))));
		assertThat("Different views", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null),
				is(not(new StarTeamConnection("1", 1, "1", "1", "1", "2", "1", null))));
		assertThat("Different folders", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null),
				is(not(new StarTeamConnection("1", 1, "1", "1", "1", "1", "2", null))));

		assertThat("Should be equal", new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null),
				is(new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null)));
		assertThat("Equality implies that hash codes should be equals",
				new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null).hashCode(),
				is(new StarTeamConnection("1", 1, "1", "1", "1", "1", "1", null).hashCode()));
	}

	@Test
	public void toStringOverride() {
		String toString = new StarTeamConnection("host", 1234, "user", "passwd", "project", "view", "folder", null).toString();
		assertThat("Missing host", toString, containsString("host: host"));
		assertThat("Missing port", toString, containsString("port: 1234"));
		assertThat("Missing user", toString, containsString("user: user"));
		assertThat("Missing passwd", toString, containsString("passwd: ******"));
		assertThat("Missing project", toString, containsString("project: project"));
		assertThat("Missing view", toString, containsString("view: view"));
		assertThat("Missing folder", toString, containsString("folder: folder"));
	}

	@Test(expected = NullPointerException.class)
	public void constructorValidationHost() {
		new StarTeamConnection(null, 1, "", "", "", "", "", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorValidationPortLow() {
		new StarTeamConnection("", -1, "", "", "", "", "", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorValidationPortHigh() {
		new StarTeamConnection("", 65536, "", "", "", "", "", null);
	}

	@Test(expected = NullPointerException.class)
	public void constructorValidationUser() {
		new StarTeamConnection("", 1, null, "", "", "", "", null);
	}

	@Test(expected = NullPointerException.class)
	public void constructorValidationPassword() {
		new StarTeamConnection("", 1, "", null, "", "", "", null);
	}

	@Test(expected = NullPointerException.class)
	public void constructorValidationProject() {
		new StarTeamConnection("", 1, "", "", null, "", "", null);
	}

	@Test(expected = NullPointerException.class)
	public void constructorValidationView() {
		new StarTeamConnection("", 1, "", "", "", null, "", null);
	}

	@Test(expected = NullPointerException.class)
	public void constructorValidationFolder() {
		new StarTeamConnection("", 1, "", "", "", "", null, null);
	}

	@Test
	public void serialization() throws Exception {
		StarTeamConnection originalConnection = new StarTeamConnection("host", 1234, "user", "passwd", "project", "view", "folder", null);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
		outputStream.writeObject(originalConnection);

		ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
		StarTeamConnection serializedConnection = (StarTeamConnection) inputStream.readObject();
		assertThat("Incorrect serialization", serializedConnection, is(originalConnection));
	}

}
