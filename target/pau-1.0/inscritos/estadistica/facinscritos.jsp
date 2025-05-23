<%@ page import = "java.text.*"%>

<%@ include file= "../../con_enoc.jsp" %>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Matricula" scope="page" class="aca.matricula.TotalMatriculaUtil"/>
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

	String cargas 			= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	
	String fechaIni		= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin		= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	String facultadId 		= request.getParameter("facultadId");
	String nombreFacultad 	= request.getParameter("nombre");	
	
	double pInt=0,pH=0,pMex=0,pAcfe=0,PCV=0;
	
	int total[] 			= Matricula.facultadEstadisticaPorCarga(conEnoc,facultadId, cargas, modalidades, fechaIni, fechaFin);	
	ArrayList lisCarr 		= Matricula.estadisticaCarreraPorCarga(conEnoc,facultadId, cargas, modalidades, fechaIni, fechaFin);	
	
	
	int TI 		= Matricula.totalInscritosFacPorCarga(conEnoc,facultadId, cargas, modalidades, fechaIni, fechaFin);
	int TCC		= Matricula.totalCCobroFac(conEnoc,cargas, modalidades, facultadId);
	int TCM		= Matricula.totalCargaFac(conEnoc,cargas, modalidades, facultadId);
	
	if (total[0]+total[1]>0) pInt 	= Double.valueOf( (double) total[0]*100 / (double)(total[0]+total[1]) ); else pInt 	= 0;
	if (total[2]+total[3]>0) pH  	= Double.valueOf( (double) total[2]*100 / (double)(total[2]+total[3]) ); else pH	= 0;
	if (total[4]+total[5]>0) pMex 	= Double.valueOf( (double) total[4]*100 / (double)(total[4]+total[5]) ); else pMex	= 0;
	if (total[6]+total[7]>0) pAcfe	= Double.valueOf( (double) total[6]*100 / (double)(total[6]+total[7]) ); else pAcfe	= 0;
	
	if ( TI>0 ) PCV	= Double.valueOf( (double) total[8] /(double)TI); else PCV = 0;	
	
	String serieResidencia 	= "['Boarding: "+total[0]+"', "+getFormato.format(pInt).replaceAll(",",".")+"], ['Off-Campus: "+total[1]+"',"+getFormato.format(100-pInt).replaceAll(",",".")+"]";
	String serieGenero 		= "['Male: "+total[2]+"', "+getFormato.format(pH).replaceAll(",",".")+"], ['Female: "+total[3]+"',"+getFormato.format(100-pH).replaceAll(",",".")+"]";	
	String serieNacion 		= "['Nat.: "+total[4]+"', "+getFormato.format(pMex).replaceAll(",",".")+"], ['Foreign.: "+total[5]+"',"+getFormato.format(100-pMex).replaceAll(",",".")+"]";
	String serieClas 		= "['ACFE. "+total[6]+"', "+getFormato.format(pAcfe).replaceAll(",",".")+"], ['No ACFE "+total[7]+"',"+getFormato.format(100-pAcfe).replaceAll(",",".")+"]";
	String serieFacultad	= "";
	
%>
<head>  
  <script type="text/javascript" src="../../js/highcharts.js"></script>
  <script type="text/javascript" src="../../js/highChart/modules/exporting.js"></script>
  <script type="text/javascript" src="../../js/highChart/themes/grid.js"></script>
  <script type="text/javascript" src="../../js/highChart/pie.js"></script>  
</head>
<div class="container-fluid">
	<h2>Statistics by School <small class="text-muted fs-4">( <%=nombreFacultad %> )</small></h2>
	<div class="alert alert-info">
		<a href="inscritos?Accion=1" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
	<table style="width:90%" border="1" align="center" class="tabla" id="noayuda">
	<tr><td>
		<table style="width:100%">
        <tr>
        	<td width="17%" align="center"><font size="2"><strong>Total Enrolled:</strong></font></td>
          	<td width="17%" align="center"><font size="2"><strong>Tot. Cost. Calc.:</strong></font></td>
          	<td width="17%" align="center"><font size="2"><strong>Tot. Cost. Course:</strong></font></td>
          	<td width="17%" align="center"><font size="2"><strong>Sold Credits:</strong></font></td>
          	<td width="17%" align="center"><font size="2"><strong>Avrg. Crd. x Student:</strong></font></td>
		</tr>
		<tr>
        	<td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TI%></strong></font></td>
          	<td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TCC%></strong></font></td>
          	<td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TCM%></strong></font></td>
          	<td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=total[8]%></strong></font></td>
          	<td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=getFormato.format(PCV)%></strong></font></td>
        </tr><tr>
		</table>
		</td></tr>
		<tr><td>
		<table style="width:100%" >
          	<tr><td bgcolor="#FFCC00" align="center"><b>Enrolled Graph</b></td></tr>
          	<tr>
          	  <td align="center">
			  	<table >
					<tr>
						<td align="center" style="font-size:8pt;">
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
          	<tr><td bgcolor="#FFCC00" align="center" colspan="3"><strong>Graph by Courses</strong></td></tr>
			<tr><td width="40%" >
				<table style="width:100%">
		          	<tr><th><spring:message code="aca.Carrera"/></th><th>Students</th><th>%</th></tr>
<%						String sFacultadId ="";
						String sFacultadNombre ="";
						String sFacultadTitulo ="";
						String sTotalFac ="";		

						String sCarreraId ="";
						String sFacultad ="";
						String sCarreraNombre ="";
						String sTotalCarr ="";						
						String sColorBk="";
						
						String nombreCorto = "";
						double porFac = 0;

						DecimalFormat getformato= new DecimalFormat("##0.00;(##0.00)");
						for (int i=0; i<lisCarr.size();i++){
							java.util.StringTokenizer sToken = new java.util.StringTokenizer((String) lisCarr.get(i),"~");
							sCarreraId 			= sToken.nextToken();
							sFacultad	 		= sToken.nextToken();
							sCarreraNombre		= sToken.nextToken();
							sTotalCarr			= sToken.nextToken();
							
							// Determina el porcentaje
							porFac = (double)Integer.parseInt(sTotalCarr)*100/TI;
							
							// Nombre Corto de la Facultad
							nombreCorto = aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc, sCarreraId);
							
							if (i==0){
								serieFacultad += "['"+nombreCorto+"',"+getFormato.format(porFac).replaceAll(",",".")+"]";
							}else{
								serieFacultad += ",['"+nombreCorto+"',"+getFormato.format(porFac).replaceAll(",",".")+"]";
							}
							
							if(i%2==1) sColorBk=" bgcolor = '#dddddd'"; else sColorBk="";
%>         					<tr>
								<td align="left" width="90%"<%=sColorBk%>><b><b><%=sCarreraNombre%>&nbsp;(<%= nombreCorto %>)<b></b></td>
								<td align="center"width="10%"<%=sColorBk%>><b><%=sTotalCarr%></b></td>
								<td align="right"width="10%"<%=sColorBk%>><b><%=getformato.format((double)Integer.parseInt(sTotalCarr)*100/(double)TI).replace(',','.')%>%</b></td>
							</tr>
<%						} 
						if(sColorBk.equals("")) sColorBk=" bgcolor = '#dddddd'"; else sColorBk="";
%>						<tr>
							<td align="right" width="90%"<%=sColorBk%>><font color="#8946A1"><b>Total Enrolled Students:</b></font></td>
							<td align="right"width="10%"<%=sColorBk%>><font color="#8946A1"><b><%=TI%></b></font></td>
						</tr>
				</table>
			</td>
			<td align="right" width="40%">
              <script  type="text/javascript">Pie('Inscritos por Carrera','ChartFacultad',[<%=serieFacultad%>])</script>
			   <div id="ChartFacultad" style="width: 700px; height: 400px; margin: 0 auto"></div>
			</td>
		</tr>			
      </table>
  </td></tr>
</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>