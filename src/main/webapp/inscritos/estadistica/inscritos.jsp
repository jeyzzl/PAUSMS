<%-- <%@ include file= "../../con_enoc.jsp" %> --%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%-- <jsp:useBean id="Matricula" scope="page" class="aca.matricula.TotalMatriculaUtil"/> --%>
<%-- <jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/> --%>
<%-- <jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/> --%>
<%-- <jsp:useBean id="catModalidad" scope="page" class="aca.catalogo.CatModalidad"/> --%>
<%-- <jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil"/> --%>
<%-- <jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil" /> --%>
<%-- <jsp:useBean id="cargaElige" scope="page" class="aca.carga.Carga" /> --%>

<script type="text/javascript">
	
	function Mostrar(){	
		document.forma.Muestra = 1;
		document.forma.submit();
	}

</script>
<%	int num = 100;
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0,00;(###,##0,00)");
	java.text.DecimalFormat getFormato2	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");
	java.text.DecimalFormat getFormato3	= new java.text.DecimalFormat("###,##0.0;(###,##0.0)");

	System.out.println("PASO 1");	

	String cargas 		= (String)request.getAttribute("cargas");
	String modalidades 	= (String)request.getAttribute("modalidades");
	double pInt 		= (double)request.getAttribute("pInt");
	double pH 			= (double)request.getAttribute("pH");
	double pMex 		= (double)request.getAttribute("pMex");
	double pAcfe 		= (double)request.getAttribute("pAcfe");
	double PCV 			= (double)request.getAttribute("PCV");
	int total[] 		= (int[])request.getAttribute("total");
	int TI 				= (int)request.getAttribute("TI");
	int TCM 			= (int)request.getAttribute("TCM");
	int TCC 			= (int)request.getAttribute("TCC");
	String muestraGra 	= (String)request.getAttribute("muestraGra");
	String fechaIni 	= (String)request.getAttribute("fechaIni");
	String fechaFin 	= (String)request.getAttribute("fechaFin");
	
	List<CatModalidad> lisModalidad 	= (List<CatModalidad>)request.getAttribute("lisModalidad");
	HashMap<String, String> lisFac 		= (HashMap<String, String>)request.getAttribute("lisFacInsc");
	List<CatFacultad> lisFacultades 	= (List<CatFacultad>)request.getAttribute("lisFacultades");
	List<Carga> lisCargas 				= (List<Carga>)request.getAttribute("lisCargas");
	
	HashMap<String, String> mapCarga = (HashMap<String, String>)request.getAttribute("mapCarga");
	
	String serieResidencia 	= "['Boarding: "+total[0]+"', "+getFormato.format(pInt).replaceAll(",",".")+"], ['Day Students: "+total[1]+"',"+getFormato.format(100-pInt).replaceAll(",",".")+"]";
	String serieGenero 		= "['Male: "+total[2]+"', "+getFormato.format(pH).replaceAll(",",".")+"], ['Female: "+total[3]+"',"+getFormato.format(100-pH).replaceAll(",",".")+"]";	
	String serieNacion 		= "['National: "+total[4]+"', "+getFormato.format(pMex).replaceAll(",",".")+"], ['Foreign: "+total[5]+"',"+getFormato.format(100-pMex).replaceAll(",",".")+"]";
	String serieClas 		= "['ACFE. "+total[6]+"', "+getFormato.format(pAcfe).replaceAll(",",".")+"], ['No ACFE "+total[7]+"',"+getFormato.format(100-pAcfe).replaceAll(",",".")+"]";
	String serieFacultad	= "";
	String serieCarga		= "";
	String serieModalidad	= "";
	
	String sColorBk="";	
	String nombresModalidades[] = (String[])request.getAttribute("nombresModalidades");
	String lisModo[] 			= modalidades.replace("'", "").split(",");	
	
	System.out.println("PASO 2");
%>
<head>
  <link rel="stylesheet" href="../../js/chosen/chosen.css" />
  <link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
  <script src="https://code.highcharts.com/highcharts.js"></script>  
  <script type="text/javascript" src="../../js/highcharts.js"></script>
  <script type="text/javascript" src="../../js/highChart/modules/exporting.js"></script>
  <script type="text/javascript" src="../../js/highChart/themes/grid.js"></script>
  <script type="text/javascript" src="../../js/highChart/pie.js"></script>    
</head>
<div class="container-fluid">
	<h2>Statistics by Enrolled Students</h2>
	<div class="alert alert-info">
		<b>Loads:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
		<b>Modalities:</b>
<%
		System.out.println("PASO 3");
		for(String mod : nombresModalidades){
// 			String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
			out.print("["+mod+"] ");
			System.out.println("PASO 4");
		}
		
%>
	<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>
	</div>
	<form id="forma" name="forma" action="inscritos?Accion=1&Muestra=1" method="post">
	<div class="alert alert-info d-flex align-items-center">		 
		Start Date: <input id="FechaIni" name="FechaIni" type="text" class="form-control" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		End Date: <input id="FechaFin" name="FechaFin" type="text" class="form-control" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync"></i></a>		 
	</div>
	</form>		
<%
	System.out.println(muestraGra);
	if(!muestraGra.equals("0")){
		System.out.println("PASO 5");
%>
	<input name="Accion" type="hidden">
	<table style="width:90%" border="1" class="tabla" id="noayuda">
	<tr>
    	<th align="center"><font size="2">Total Enrolled Students Statistics</font></th>
  	</tr>
  	<tr>
  		<td>
  			<table style="width:100%">
  			<tr>
          	  <td width="17%" align="center"><font size="2"><strong>Total Enrolled:</strong></font></td>
          	  <td width="17%" align="center"><font size="2"><strong>Total. Cost Calculation.</strong></font></td>
          	  <td width="17%" align="center"><font size="2"><strong>Tot. Cost Subjects:</strong></font></td>
          	  <td width="17%" align="center"><font size="2"><strong>Sold Credits:</strong></font></td>
          	  <td width="17%" align="center"><font size="2"><strong>Avrg. Credits x Students:</strong></font></td>
			 </tr>
			 <tr>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TI%></strong></font></td>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TCC %></strong></font></td>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=TCM %></strong></font></td>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=total[8]%></strong></font></td>
          	  <td width="17%" align="center"><font size="2" color="#8946A1"><strong><%=getFormato2.format(PCV)%></strong></font></td>
          	</tr>
  			</table>
  		</td>
  	</tr>
  	<tr>
  		<td>
			<table style="width:100%" >
          	<tr><td class="th2" align="center" colspan="2"><b>Enrolled Graphs</b></td></tr>
          	<tr>
          	  	<td align="center" style="font-size:8pt;" width="50%">
					<script  type="text/javascript">Pie('Residency','ChartRes',[<%=serieResidencia%>])</script>
				 	<div id="ChartRes" style="width: 500px; height: 300px; margin: 0 auto"></div>
				</td>
				<td align="center" width="50%">
					<script  type="text/javascript">Pie('Gender','ChartGenero',[<%=serieGenero%>])</script>
					<div id="ChartGenero" style="width: 500px; height: 300px; margin: 0 auto"></div>										
				</td>
			</tr>	
			<tr>	
				<td align="center" width="50%">
					<script  type="text/javascript">Pie('Nationality','ChartNacion',[<%=serieNacion%>])</script>
					<div id="ChartNacion" style="width: 500px; height: 300px; margin: 0 auto"></div>
				</td>
				<td align="center" width="50%">
					<script  type="text/javascript">Pie('Finantial Class','ChartClas',[<%=serieClas%>])</script>
					<div id="ChartClas" style="width: 500px; height: 300px; margin: 0 auto"></div>
				</td>
			</tr>
			</table>			
		</td>
	</tr>
  	<tr>
  		<td>
			<table style="width:100%" >
          	<tr><td class="th2" align="center" colspan="2"><b>Graphs by Load</b></td></tr>
          	<tr>          	
          	  <td valign="top" width="50%">
          	    <table style="width:100%" >
          	      <tr>
          	        <th width="10%">Id</th>
          	        <th width="80%"><spring:message code="aca.Carga"/></th>
          	        <th width="10%">Students</th>
          	        <th width="10%">&nbsp;&nbsp;%</th>
          	      </tr>
<%
			// Map de alumnos por carga
// 			java.util.HashMap<String,String> mapCarga = Matricula.totalInscritosCarga(conEnoc, cargas, modalidades, fechaIni, fechaFin);
			int z=0;
			for (Carga carga : lisCargas){
				if(z%2==1) sColorBk=" bgcolor = '#dddddd'"; else sColorBk="";
				String numAlumnos = "0";
				if (mapCarga.containsKey(carga.getCargaId())){		
					numAlumnos = mapCarga.get(carga.getCargaId());
				}
				double porCarga = 0;
				if (TI>0) porCarga = (double)Double.parseDouble(numAlumnos)*100/TI;
%>
				  <tr>
				    <td <%=sColorBk%>><%=carga.getNombreCarga() %></td>
				    <td <%=sColorBk%>><%=carga.getCargaId()%></td>
				    <td align="right" <%=sColorBk%>><%=numAlumnos%></td>
				    <td align="right" <%=sColorBk%>>&nbsp;<%=getFormato3.format(porCarga)%>%</td>
				  </tr>
<% 				z++;
			}%>					
          	    </table>          	    
          	  </td>			 
          	  <td align="center" valign="top" width="50%">  	      
<%
			z=0;
			for (Carga carga : lisCargas){
				String numAlumnos = "0";
				if (mapCarga.containsKey(carga.getCargaId())){
					numAlumnos = mapCarga.get(carga.getCargaId());
				}		
				
				double porCarga = 0;
				if (TI>0) porCarga = (double)Double.parseDouble(numAlumnos)*100/TI;			
				
				if (z==0){
					serieCarga += "['"+carga.getNombreCarga()+"',"+Double.valueOf(getFormato2.format(porCarga))+"]";
				}else{
					serieCarga += ",['"+carga.getNombreCarga()+"',"+Double.valueOf(getFormato2.format(porCarga))+"]";
				}				
				z++;				
			}			
%>
				<script  type="text/javascript">Pie('Enrolled by Load','carga',[<%=serieCarga%>])</script>
			    <div id="carga" style="width: 700px; height: 400px; margin: 0 auto"></div>        
          	  </td>
          	</tr>  
		  </table>
		</td></tr>		
		<tr>
			<td>
				<table style="width:100%">
					<tr><td class="th2" align="center" colspan="2"><b>Graphs by Modality</b></td></tr>
					<tr>
						<td width="50%" valign="top">
							<table style="width:100%">
								<tr>
									<th width="80%"><spring:message code="aca.Modalidad"/></th>
									<th width="10%">Students</th>
									<th width="10%">%</th>
								</tr>
<%			
			int j = 0;
			for(CatModalidad catModalidad : lisModalidad){

				if(j%2==1) sColorBk=" bgcolor = '#dddddd'"; else sColorBk="";
				double porcentaje = 0;
				if (TI>0) porcentaje = Double.parseDouble(catModalidad.getEnLinea()) * 100/TI;				

%>
								<tr>
									<td <%=sColorBk%>><%=catModalidad.getNombreModalidad()%></td>
									<td align="right" <%=sColorBk%>><%=catModalidad.getEnLinea() %></td>									
									<td align="right" <%=sColorBk%>><%=getFormato2.format(porcentaje)%>%</td>
								</tr>
<%
				j++;
			}
%>
							</table>
						</td>
						<td align="center" width="50%">
<%
			int k = 0;
			for(CatModalidad catModalidad : lisModalidad){				
				int modalidad= Integer.parseInt(catModalidad.getEnLinea());	
				double porcentaje = 0;
				if (TI>0) porcentaje = Double.valueOf( (double)modalidad*100/TI);				
				if (k==0){
					serieModalidad += "['"+catModalidad.getNombreModalidad()+"',"+Double.valueOf(getFormato2.format(porcentaje).replace(",",""))+"]";
				}else{
					serieModalidad += ",['"+catModalidad.getNombreModalidad()+"',"+Double.valueOf(getFormato2.format(porcentaje).replace(",",""))+"]";
				}	
				k++;
			}			
%>
							<script  type="text/javascript">Pie('Enrolled by Modality','mod',[<%=serieModalidad%>])</script>
						    <div id="mod" style="width: 700px; height: 400px; margin: 0 auto"></div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td>
			<table style="width:100%" >
          	<tr><td class="th2" align="center" colspan="3"><strong>Graphs by School</strong></td></tr>
			<tr><td width="50%" valign="Top">
				<table style="width:100%">
		          	<tr><th><spring:message code="aca.Facultad"/></th><th>Students</th><th>%</th></tr>
<%	
						String sFacultadId ="";
						String sFacultadNombre ="";
						String sFacultadTitulo ="";
						String sTotalFac ="";	
											
						String sFacultad ="";
						String sCarreraNombre ="";
						String sTotalCarr ="";						
						String nombreCorto = "";						
						double por 	  = 0;
						System.out.println("PASO 6 " + lisFac.size());
						
						int i = 0;
						for (CatFacultad fac : lisFacultades){
							sFacultadNombre 		= fac.getNombreFacultad();
							sFacultadTitulo 		= fac.getTitulo();
							sFacultadId 			= fac.getFacultadId();
							nombreCorto 			= fac.getNombreCorto();
							
							if(lisFac.containsKey(fac.getFacultadId())){
								System.out.println("PASO 7");
// 								java.util.StringTokenizer sToken = new java.util.StringTokenizer((String) lisFac.get(i),"~");
	// 							sFacultadNombre 		= sToken.nextToken();
	// 							sFacultadTitulo 		= sToken.nextToken();
	// 							sFacultadId 			= sToken.nextToken();
	// 							sTotalFac 				= sToken.nextToken();
	// 							nombreCorto 			= sToken.nextToken();
								sTotalFac 				= lisFac.get(fac.getFacultadId());
							}else{
								sTotalFac				= "0";
							}
							
							// Determina el porcentaje
							double porFac = 0;
							if (sTotalFac.equals("0") || TI == 0)
								porFac = 0;
							else{								
								porFac = (double)Integer.parseInt(sTotalFac)*100/TI;							
							}
							
							por = Double.valueOf(getFormato2.format(porFac).replace(",",""));							
							String tmp = getFormato2.format(por).replace(",","");
							
							por = Double.valueOf(tmp);
							// Nombre Corto de la Facultad
// 							nombreCorto = aca.catalogo.CatFacultadUtil.getNombreCorto(conEnoc, sFacultadId);
							
							if (i==0){
								serieFacultad += "['"+nombreCorto+"',"+Double.valueOf(getFormato2.format(porFac).replaceAll(",",""))+"]";
							}else{
								serieFacultad += ",['"+nombreCorto+"',"+Double.valueOf(getFormato2.format(porFac).replaceAll(",",""))+"]";
							}
							
						 	if(i%2==1) sColorBk=" bgcolor = '#dddddd'"; else sColorBk=""; cargas = cargas.replaceAll("'","/");
							
%>         					<tr>
								<td align="left" width="90%"<%=sColorBk%>><b><a href="facinscritos?facultadId=<%=sFacultadId%>&nombre=<%=sFacultadNombre%>"><font color='#000000'><b>&nbsp;&nbsp;<%=sFacultadNombre%>&nbsp;(<%= nombreCorto %>)<b></font></a></b></td>
								<td align="center"width="10%"<%=sColorBk%>><b><%=sTotalFac%></b></td>
								<td align="right"width="10%"<%=sColorBk%>><b><%=por%>%</b></td>
							</tr>
<%			
							i++;
						}
						
						if(sColorBk.equals("")) sColorBk=" bgcolor = '#dddddd'"; else sColorBk="";
%>						<tr>
							<td align="right" width="90%"<%=sColorBk%>><font color="#8946A1"><b>Total Enrolled Students:</b></font></td>
							<td align="right"width="10%"<%=sColorBk%>><font color="#8946A1"><b><%=TI%></b></font></td>
						</tr>
				</table>	
			</td>
			<td align="right" width="50%">
              	<script  type="text/javascript">Pie('Enrolled by School','fac',[<%=serieFacultad%>])</script>
			    <div id="fac" style="width: 700px; height: 400px; margin: 0 auto"></div>
			</td>
		</tr>			
      </table>
  </td></tr>
</table>
<%	} %>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
	jQuery(".chosen").chosen();
</script>
<%-- <%@ include file= "../../cierra_enoc.jsp" %> --%>
