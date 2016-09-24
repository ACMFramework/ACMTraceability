package utilities;

/**
 * Class representing a relationship between two artefact elements
 *
 */
public class Relation
{
	/**
	 * The unique id of a relation
	 */
	private String relationId = "";
	
	/**
	 * The source element of the relation
	 */
	private Element sourceElement = null;
	
	/**
	 * The target element of the relation
	 */
	private Element targetElement = null;
	
	/**
	 * The unique id of the source element
	 */
	private String sourceId = "";
	
	/**
	 * The target id of the source element
	 */
	private String targetId = "";

	/**
	 * @return Returns the unique id the relation
	 */
	public String getRelationId() 
	{
		return relationId;
	}
	
	public void setRelationId(String relationId) 
	{
		this.relationId = relationId;
	}
	
	/**
	 * @return Returns the source element of the relation
	 */
	public Element getSourceElement() 
	{
		return sourceElement;
	}
	
	public void setSourceElement(Element element) 
	{
		this.sourceElement = element;
	}
	
	/**
	 * @return Returns the target element of the relation
	 */
	public Element getTargetElement() 
	{
		return targetElement;
	}
	
	public void setTargetElement(Element targetElement) 
	{
		this.targetElement = targetElement;
	}
	
	/**
	 * @return Returns the id of the source element
	 */
	public String getSourceId() 
	{
		return sourceId;
	}
	
	public void setSourceId(String sourceId) 
	{
		this.sourceId = sourceId;
	}
	
	/**
	 * @return Returns the id of the target element
	 */
	public String getTargetId() 
	{
		return targetId;
	}
	
	public void setTargetId(String targetId) 
	{
		this.targetId = targetId;
	}

	public Relation() 
	{
	}

	public Relation(Element el1, Element el2) 
	{
		this.sourceElement = el1;
		this.targetElement = el2;
	}

	public Relation(String source, String target) 
	{
		this.sourceId = source;
		this.targetId = target;
	}

	public Relation(String id, String source, String target) 
	{
		this.relationId = id;
		this.sourceId = source;
		this.targetId = target;
	}

	public Relation(String id, String source, String target, Element sourceElement) 
	{
		this.relationId = id;
		this.sourceId = source;
		this.targetId = target;
		this.sourceElement = sourceElement;
	}

	public Relation(String id, String source, String target, Element sourceElement, Element targetElement) 
	{
		this.relationId = id;
		this.sourceId = source;
		this.targetId = target;
		this.sourceElement = sourceElement;
		this.targetElement = targetElement;
	}

	/**
	 *Overriden toString() method to supply required details
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Relation id:" + getRelationId());
		sb.append(", ");
		sb.append("Source element:" + getSourceElement());
		sb.append(", ");
		sb.append("Target element:" + getTargetElement());
		sb.append(", ");
		sb.append("Source id:" + getSourceId());
		sb.append(", ");
		sb.append("Target id:" + getTargetId());
		return sb.toString();
	}
}
