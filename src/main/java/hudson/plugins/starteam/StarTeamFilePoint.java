package hudson.plugins.starteam;

import java.io.Serializable;
import java.io.File;

/**
 * Stores a reference to the file at the particular revision.
 */
public class StarTeamFilePoint implements Serializable, Comparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fullfilepath;
	private int revisionnumber;

	public StarTeamFilePoint() {
		super();
	}

	public StarTeamFilePoint(com.starteam.File f) {
		this(f.getFullName(),f.getRevisionNumber());
	}

	public StarTeamFilePoint(String fullFilePath, int revisionNumber) {
		this.fullfilepath = fullFilePath;
		this.revisionnumber = revisionNumber;
	}

	public String getFullfilepath() {
		return fullfilepath;
	}

	public File getFile() {
		return new File(getFullfilepath());
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StarTeamFilePoint that = (StarTeamFilePoint) o;

		if (fullfilepath != null ? !fullfilepath.equals(that.fullfilepath) : that.fullfilepath != null) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (fullfilepath != null ? fullfilepath.hashCode() : 0);
		return result;
	}

	public int compareTo(Object o) {
		return fullfilepath.toLowerCase().compareTo(((StarTeamFilePoint)o).fullfilepath.toLowerCase());
	}

	public int getRevisionNumber() {
		return revisionnumber;
	}

	@Override
	public String toString() {
	    final StringBuffer buffer = new StringBuffer();
	    buffer.append( "file: " ).append( fullfilepath );
	    buffer.append( " revision: " ).append( revisionnumber );
	    return buffer.toString();
	}

}
