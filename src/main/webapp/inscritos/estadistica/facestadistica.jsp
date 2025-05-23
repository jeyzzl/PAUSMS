<%@ page import = "java.text.*"%>

<%@ include file= "../../con_enoc.jsp" %>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Matricula" scope="page" class="aca.matricula.MatriculaUtil"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

	String facultadId 		= request.getParameter("facultadId");
	String nombreFacultad 	= request.getParameter("nombre");
	
	double pInt=0,pH=0,pMex=0,pAcfe=0,PCV=0;
	
	int total[] = Matricula.facultadEstadistica(conEnoc,(String)session.getAttribute("cargaId"),facultadId);
	ArrayList lisCarr = Matricula.estadisticaCarrera(conEnoc,(String)session.getAttribute("cargaId"),facultadId);
	Carga = CargaU.mapeaRegId(conEnoc,(String)session.getAttribute("cargaId"));
	
	int TI = Matricula.totalInscritosFac(conEnoc,(String)session.getAttribute("cargaId"),facultadId);	
	int TCC = Matricula.totalCCobroFac(conEnoc,(String)session.getAttribute("cargaId"),facultadId);
	int TCM = Matricula.totalCargaFac(conEnoc,(String)session.getAttribute("cargaId"),facultadId);
	
	if (total[0]+total[1] > 0) pInt = total[0]*100 / (total[0]+total[1]);
	if (total[2]+total[3] > 0) pH   = total[2]*100 / (total[2]+total[3]);
	if (total[4]+total[5] > 0) pMex = total[4]*100 / (total[4]+total[5]);
	if (total[6]+total[7] > 0) pAcfe= total[6]*100 / (total[6]+total[7]);
	if (TI >0 ) PCV = total[8]/TI;
	
	String serieResidencia 	= "['Boarding S.: "+total[0]+"', "+getFormato.format(pInt)+"], ['Off-Campus: "+total[1]+"',"+getFormato.format(100-pInt)+"]";
	String serieGenero 		= "['Male: "+total[2]+"', "+getFormato.format(pH)+"], ['Female: "+total[3]+"',"+getFormato.format(100-pH)+"]";	
	String serieNacion 		= "['National.: "+total[4]+"', "+getFormato.format(pMex)+"], ['Foreign.: "+total[5]+"',"+getFormato.format(100-pMex)+"]";
	String serieClas 		= "['ACFE. "+total[6]+"', "+getFormato.format(pAcfe)+"], ['No ACFE "+total[7]+"',"+getFormato.format(100-pAcfe)+"]";
	String serieCarrera		= "";
%>
<head>
  <script type="text/javascript" src="../../js/jquery-1.4.4.min.js"></script>
  <script type="text/javascript" src="../../js/highcharts.js"></script>
  <script type="text/javascript" src="../../js/highChart/modules/exporting.js"></script>
  <script type="text/javascript" src="../../js/highChart/themes/grid.js"></script>
  <script type="text/javascript" src="../../js/highChart/pie.js"></script>  
</head>
<table style="width:90%" border="1" align="center" class="tabla" id="noayuda">
  <tr>
	   	<th align="center"><font size="2"><%=nombreFacultad%> Statistics: <%=Carga.getNombreCarga()%></font></th>
  </tr>
  <tr><td>
	  <table style="width:100%" >
          	<tr>
          	  <td width="17%" align="center"><font size="2"><strong>Total Enrolled:</strong></font></td>
          	  <td width="17%" align="center"><font size="2"><strong>Cost Calculation:</strong></font></td>
          	  <td width="17%" align="center"><font size="2"><strong><spring:message code="aca.CargaAcademica"/>:</strong></font></td>
          	  <td width="17%" align="center"><font size="2"><strong>Total Students:</strong></font></td>
          	  <td width="17%" align="center"><font size="2"><strong>Sold Credits:</strong></font></td>
          	  <td width="17%" align="center"><font size="2"><strong>Average Crd. x Alum:</strong></font></td>
			 </tr>
			 <tr>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TI%></strong></font></td>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TCC%></strong></font></td>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TCM%></strong></font></td>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TI+TCC+TCM%></strong></font></td>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=total[8]%></strong></font></td>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=PCV%></strong></font></td>
          	</tr><tr>
		</table>
		</td></tr>
		<tr><td>
		  <table style="width:100%" >
          	<tr><td bgcolor="#FFCC00" align="center"><b>Enrolled Graph</b></td></tr>
          	<tr>
          	  <td align="center">
			  	<table style="width:100%">
				  <tr>
					<td align="center">
					  <script  type="text/javascript">Pie('Residency','ChartRes',[<%=serieResidencia%>])</script>
					  <div id="ChartRes" style="width: 500px; height: 300px; margin: 0 auto"></div>									
					</td>
					<td align="center">
					  <script  type="text/javascript">Pie('Gender','ChartGenero',[<%=serieGenero%>])</script>
					  <div id="ChartGenero" style="width: 500px; height: 300px; margin: 0 auto"></div>		
					</td>
				  </tr>
				  <tr>	
					<td align="center">
					  <script  type="text/javascript">Pie('Nationality','ChartNacion',[<%=serieNacion%>])</script>
					  <div id="ChartNacion" style="width: 500px; height: 300px; margin: 0 auto"></div>			
					</td>
					<td align="center">
					  <script  type="text/javascript">Pie('Finantial Class','ChartClas',[<%=serieClas%>])</script>
					  <div id="ChartClas" style="width: 500px; height: 300px; margin: 0 auto"></div>		
					</td>
					</tr>					
				</table>
			  </td>
       	  </tr>
		 </table>
		</td></tr>
		<tr><td>
			<table style="width:100%" >
          	<tr><td bgcolor="#FFCC00" align="center" colspan="3"><strong>Graph by Course</strong></td></tr>
			<tr><td width="50%" valign="top">
				<table style="width:100%">
		          	<tr>
		          	  <th><spring:message code="aca.Numero"/></th>
		          	  <th><spring:message code="aca.Carrera"/></th>
		          	  <th>Students</th>
		          	  <th>%</th>
		          	</tr>
<%						String sFacultadId ="";
						String sFacultadNombre ="";
						String sFacultadTitulo ="";
						String sTotalFac ="";		

						String sCarreraId ="";
						String sFacultad ="";
						String sCarreraNombre ="";
						String sTotalCarr ="";						
						String sColorBk="";
						
						double porCarrera = 0;					
						
						for (int i=0; i<lisCarr.size();i++){
							java.util.StringTokenizer sToken = new java.util.StringTokenizer((String) lisCarr.get(i),"~");
							sCarreraId 			= sToken.nextToken();
							sFacultad	 		= sToken.nextToken();
							sCarreraNombre		= sToken.nextToken();
							sTotalCarr			= sToken.nextToken();
							String nombreCorto 	= ""; 
							
							// Determina el porcentaje
							porCarrera = (double)Integer.parseInt(sTotalCarr)*100/TI;
							
							// Nombre Corto de la Facultad
							nombreCorto = aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc, sCarreraId);
							
							if (i==0){
								serieCarrera += "['"+nombreCorto+"',"+getFormato.format(porCarrera)+"]";
							}else{
								serieCarrera += ",['"+nombreCorto+"',"+getFormato.format(porCarrera)+"]";
							}
							
							if(i%2==1) sColorBk=" bgcolor = '#dddddd'"; else sColorBk="";
							
%>         					<tr>
							  <td align="left" width="10%"<%=sColorBk%>><b><%= sCarreraId%></b></td>
							  <td align="left" width="70%"<%=sColorBk%>><b><%=sCarreraNombre%> (<%= nombreCorto %>)</b></td>
							  <td align="right"width="10%"<%=sColorBk%>><b><%=sTotalCarr%></b></td>
							  <td align="right"width="10%"<%=sColorBk%>><b><%=getFormato.format((double)Integer.parseInt(sTotalCarr)*100/(double)TI).replace(',','.')%>%</b></td>
							</tr>
<%						} 
						if(sColorBk.equals("")) sColorBk=" bgcolor = '#dddddd'"; else sColorBk="";
%>						<tr>
							<td align="left" width="10%"<%=sColorBk%>>&nbsp;</td>
							<td align="right" width="70%"<%=sColorBk%>><font color="#8946A1"><b>Total Enrolled Students:</b></font></td>
							<td align="right"width="10%"<%=sColorBk%>><font color="#8946A1"><b><%=TI%></b></font></td>
							<td align="right"width="10%"<%=sColorBk%>><font color="#8946A1"><b>100%</b></font></td>
						</tr>
				</table>
			</td>
			<td align="right" width="50%">
              <script  type="text/javascript">Pie('Enrolled by Course','ChartCarrera',[<%=serieCarrera%>])</script>
			   <div id="ChartCarrera" style="width: 650px; height: 350px; margin: 0 auto"></div>
			</td>
		</tr>			
      </table>
  </td></tr>
</table>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>