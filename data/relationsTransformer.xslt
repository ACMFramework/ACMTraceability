<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="yes" />
	<xsl:template match="/">
		<Relations>
			<xsl:for-each select="dataset/body/instances/instance">
					<xsl:choose>
	    				<xsl:when test="value[17] = '1'">
	        				<Relation>
	        					<xsl:attribute name="id"><xsl:number/>
								</xsl:attribute>	
									<SourceNode><xsl:value-of select="value[1]"/></SourceNode>
									<TargetNode><xsl:value-of select="value[2]"/></TargetNode>
							</Relation>	
	    				</xsl:when>
					</xsl:choose>	
			</xsl:for-each>
		</Relations>
	</xsl:template>
</xsl:stylesheet>