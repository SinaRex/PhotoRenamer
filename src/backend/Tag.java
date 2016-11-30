package backend;

import java.io.Serializable;
import java.util.Date;

public class Tag implements Serializable{
	/**
	 * Generated serial versionUID by Eclipse.
	 */
	private static final long serialVersionUID = -3278483577725399421L;
	
	/**
	 * The name or label of this tag
	 */
	public String label;
	
	/**
	 * The time-stamp of this tag when instantiated
	 */
	public Date timestamp;
	
	/**
	 * The tag of this Photo's tags list
	 * 
	 * @param label
	 * 	the name of this tag.
	 * 
	 * @see Photo
	 */
	public Tag(String label){
		this.label = label;
		this.timestamp = new Date();
	}
	
	public Tag(String label, Date time){
		this.label = label;
		this.timestamp = time;
	}

	/**
	 * Return a user-friendly string representation of this Tag.
	 * @return a user-friendly string representation of this Tag.
	 */
	@Override
	public String toString(){
		return "@" + label;
	}

}
