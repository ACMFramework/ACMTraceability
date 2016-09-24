package utilities;

/**
 * Class representing an element (each artefact consists of elements of different types)
 */
public class Element 
{
	/**
	 * Name of the element
	 */
	private String name;
	
	
	/**
	 * Id of the element
	 */
	private String id;
	
	
	/**
	 * Type of the element
	 */
	private String type;
	
	public Element() 
	{
		this.name = "Unknown";
		this.id = "Unknown";
		this.type = "Unknown";
	}

	public Element(String id) 
	{
		this.name = "Unknown";
		this.id = id;
		this.type = "Unknown";
	}

	public Element(String name, String id) 
	{
		this.name = name;
		this.id = id;
		this.type = "Unknown";
	}
	
	public Element(String name, String id, String type) 
	{
		this.name = name;
		this.id = id;
		this.type = type;
	}

	/**
	 * @return Returns the name
	 */
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * @return Returns the id
	 */
	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	/**
	 * @return Returns the type
	 */
	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}
	
	/**
	 *Overriden toString() method to supply required details
	 */
	public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Element name:" + getName());
		sb.append(", ");
		sb.append("Element id:" + getId());
		sb.append(", ");
		sb.append("Element type:" + getType());
		return sb.toString();
	}
}
