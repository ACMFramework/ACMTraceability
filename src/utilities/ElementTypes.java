package utilities;

/**
 * Container class for element type constants
 */
public final class ElementTypes 
{
	private final String METHOD = "method";
	private final String FIELD = "field";
	private final String CL = "class";
	private final String INTF = "interface";
	private final String ENUMS = "enum";
	private final String ATT = "UMLAttribute";
	private final String UMLOP = "UMLOperation";
	private final String REQ = "requirement";
	private final String ARCHITECTURE_MODULE = "module";
	private final String ARCHITECTURE_COMPONENT = "Component";
	private final String SEQUENCE_DIAGRAM_USECASE = "UseCase_Object_Class";
	private final String SEQUENCE_DIAGRAM_MESSAGE = "Message";
	private final String USE_CASE_UC = "UseCase";
	
	public String getMETHOD() 
	{
		return METHOD;
	}
	public String getFIELD() 
	{
		return FIELD;
	}
	public String getCL() 
	{
		return CL;
	}
	public String getINTF() 
	{
		return INTF;
	}
	public String getENUMS() 
	{
		return ENUMS;
	}
	public String getATT() 
	{
		return ATT;
	}
	public String getUMLOP() 
	{
		return UMLOP;
	}
	public String getREQ() 
	{
		return REQ;
	}
	public String getARCHITECTURE_MODULE() 
	{
		return ARCHITECTURE_MODULE;
	}
	public String getARCHITECTURE_COMPONENT() 
	{
		return ARCHITECTURE_COMPONENT;
	}
	public String getSEQUENCE_DIAGRAM_USECASE() 
	{
		return SEQUENCE_DIAGRAM_USECASE;
	}
	public String getSEQUENCE_DIAGRAM_MESSAGE() 
	{
		return SEQUENCE_DIAGRAM_MESSAGE;
	}
	public String getUSE_CASE_UC() 
	{
		return USE_CASE_UC;
	}
}
