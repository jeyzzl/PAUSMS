<%@ page import= "java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.catalogo.spring.CatEstrategia"%>
<%@page import="aca.catalogo.spring.CatTipoCal"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.carga.spring.CargaGrupoEvaluacion"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.kardex.spring.KrdxAlumnoEval"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.parametros.spring.Parametros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<link href="../../print.css" rel="stylesheet" type="text/css" media="print">
</head>
<style type="text/css">
	.style2 {
		font-size: 10px;
		font-family: Arial, Helvetica, sans-serif;
	}
	.style3 {
		font-family: Arial, Helvetica, sans-serif;
		font-weight: bold;
		font-size: 10px;
	}
	.style4 {
		font-family: Arial, Helvetica, sans-serif;
		font-weight: bold;
		font-size: 20px;
	}
	TH  {
		border-width : 1px;
		border-color : #000000;
		border-style : solid;
		padding : 1px;
		font-family: Arial, Helvetica, sans-serif;
		font-weight: bold;
		font-size: 10px;
		color: black;
	}
	TD  {
		border-color : #000000;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 9px;
	}

</style>
<body bgcolor="#FFFFFF">
<%
	DecimalFormat df = new DecimalFormat("###,##0.0;-###,##0.0");

	String cursoCargaId		= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
	String imp				= request.getParameter("imp");
	String institucion 		= (String)session.getAttribute("institucion");
	
	CargaGrupo cargaGrupo 	= (CargaGrupo) request.getAttribute("cargaGrupo");
	MapaCurso mapaCurso 	= (MapaCurso) request.getAttribute("mapaCurso");
	String estadoMateria	= (String) request.getAttribute("estado");
	String maestroNombre	= (String) request.getAttribute("maestroNombre");
	String facultadNombre	= (String) request.getAttribute("facultadNombre");
	String carreraNombre	= (String) request.getAttribute("carreraNombre");
	String coordinador 		= (String) request.getAttribute("coordinador");
	
	float notaAprobatoria	= 0;	
	String notaEvaluacion	= "";
	String promedio			= "";

	Parametros parametros = (Parametros)request.getAttribute("parametros");
	
	List<CargaGrupoEvaluacion> lisEvaluaciones 			= (List<CargaGrupoEvaluacion>) request.getAttribute("lisEvaluaciones");
	List<AlumnoCurso> lisAlumnos			 			= (List<AlumnoCurso>) request.getAttribute("lisAlumnos");
	List<String> lisMeses 								= (List<String>)request.getAttribute("lisMeses");
	HashMap<String,CatEstrategia> mapaEstrategias 		= (HashMap<String,CatEstrategia>) request.getAttribute("mapaEstrategias");
	HashMap<String,CatTipoCal> mapaTipos		 		= (HashMap<String,CatTipoCal>) request.getAttribute("mapaTipoCal");
	HashMap<String,KrdxAlumnoEval> mapaEvaluaciones 	= (HashMap<String,KrdxAlumnoEval>) request.getAttribute("mapaEvaluaciones");
	HashMap<String,AlumPlan> mapaPlanes 				= (HashMap<String,AlumPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,KrdxCursoAct> mapaNotas				= (HashMap<String,KrdxCursoAct>) request.getAttribute("mapaNotas");
	HashMap<String,String> mapaEvaluadas				= (HashMap<String,String>) request.getAttribute("mapaEvaluadas");
	HashMap<String,String> mapaExtras					= (HashMap<String,String>) request.getAttribute("mapaExtras");
	HashMap<String,String> mapaAlumnoPuntos				= (HashMap<String,String>) request.getAttribute("mapaAlumnoPuntos");
	HashMap<String,String> mapaAlumnoExtras				= (HashMap<String,String>) request.getAttribute("mapaAlumnoExtras");
	HashMap<String,String> mapaAlumnos					= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaPromediosPorMes			= (HashMap<String,String>) request.getAttribute("mapaPromediosPorMes");
		
	int i=0,j=0, row=0;	

	String codigoPostal = "";
	String ubicacion = "";
	String pais = "";
	String telefono = "";
	if(parametros.getInstitucion().equals("Pacific Adventist University")){	
		codigoPostal = "Private Mail Bag, Boroko 111,";
		ubicacion = "National Capital District,";
		pais = "Papua New Guinea";
		telefono = "+675 7411 1300";
	}
	if(parametros.getInstitucion().equals("Sonoma")){
		codigoPostal = "P. O. BOX 360,";
		ubicacion = "Kokopo 613, East New Britain";
		pais = "Papue New Guinea";
		telefono = "+675 982 1782";
	}
	if(parametros.getInstitucion().equals("Fulton")){
		codigoPostal = "Sabeto Rd,";
		ubicacion = "Nadi,";
		pais = "Fiji";
		telefono = "+679 999 3118";
	}
%>
<table cellspacing="2" style="float:left">
	<tr>
	  	<td width="120" align="left" valign="top">
			<a href="javascript:window.print()"><img src="../../imagenes/logo.jpg"  height="100" width="98" alt="Imprimir"></a><br><br>
			<span class="style2"><br>
				<b><%=institucion%></b><br>
				<br>
				<%=codigoPostal%><br> <!-- Postal Code -->
				<%=ubicacion%><br> <!-- Location -->
				<%=pais%><br> <!-- Country -->
				<br>
				<strong>Phone:</strong><br>
				<%=telefono%><br> <!-- Phone -->
				<%-- Commutator 263-0900<br>
				Ext. 119,120,121 <br>
				Fax (826) 263-0979<br> --%>
				<br>
				<b>Generated by</b> <br>
				<%=institucion%><br>
				<br>
				<%-- <b>Institution Code</b><br>
				before the SEP and the<br>
				General Directorate of Statistics<br>
				19MSU1017U --%>
			</span>		
		</td>
		<td width="100%" valign="top" bordercolor="0" >	  
			<table style="width:100%" cellpadding="2" border="2" bordercolor="#777777">
			  <tr>
				<td width="50%" align="center">
					<span class="style3">
<%						if (estadoMateria.equals("2")){
%>							This is NOT an official document.
<%						}else if (estadoMateria.equals("3")){
%>							Official Document (Ordinary)
<%						}else if (estadoMateria.equals("4") || estadoMateria.equals("5")){
%>							Official Document ( Ordinary - Extraordinary )
<%						}
%>					</span></td>
				<td width="50%" align="right"><span class="style4">#<%=cursoCargaId%></span></td>
			  </tr>
			</table>
			<table style="width:100%" cellpadding="2" >
			  <tr>
				<td width="50%" align="center">
				<table style="width:100%" cellpadding="2" cellspacing="2">
                  <tr>
                    <th colspan="2" align="left"><%=facultadNombre%></th>
                  </tr>
                  <tr>
                    <th colspan="2" align="left"><%=carreraNombre%></th>
                  </tr>
                  <tr>
                    <th width="22%" align="left">Course:</th>
                    <td width="78%"><%=mapaCurso.getNombreCurso()%></td>
                  </tr>
                  <tr>
                    <th align="left">Semester:</th>
                    <td><%=mapaCurso.getCiclo()%></td>
                  </tr>
                  <tr>
                    <th align="left">Credits:</th>
                    <td><%=mapaCurso.getCreditos()%></td>
                  </tr>
                  <tr>
<%	
	String sHp = mapaCurso.getHp();
	String sHt = mapaCurso.getHt();
	if (sHp.equals("")) sHp ="0";
	int hp = Integer.parseInt(sHp);
	if (sHt.equals("")) sHt ="0";
	int ht = Integer.parseInt(sHt);
%>
                    <th align="left">Hours:</th>
                    <td><%=ht+hp%></td>
                  </tr>			  
                  <tr>
                    <th align="left">Lecturer:</th>
                    <td><%=maestroNombre%></td>
                  </tr>
                  <tr>
                    <td colspan="2">.
<%
	// ArrayList<aca.carga.CargaGrupoEvaluacion> lisEvaluacion = evaluacionU.getLista(conEnoc, cursoCargaId, "ORDER BY TO_CHAR(ENOC.CARGA_GRUPO_EVALUACION.FECHA,'YYYY/MM/DD'), EVALUACION_ID");
	
	String meses[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Agu","Sep","Oct","Nov","Dec"};
	double suma=0;
	for (CargaGrupoEvaluacion cargaGrupoEvaluacion : lisEvaluaciones){
		suma+= Double.valueOf(cargaGrupoEvaluacion.getValor()).doubleValue();
%>							
<%	}	%>											
					</td>
                  </tr>
                </table>
				</td>
				<td width="50%" align="left">					
					<table style="width:100%" height="100%" cellpadding="2"  border="2" bordercolor="#777777">
						<tr><td width="100%" align="center">
							
							<b>Observations:</b> The minimum passing grade is <%=Float.parseFloat(mapaCurso.getNotaAprobatoria())/10%>.<br>							  
							<%=institucion%><br>
							<%=(cargaGrupo.getEstado().equals("4") || cargaGrupo.getEstado().equals("5"))?cargaGrupo.getfEvaluacion():aca.util.Fecha.getHoy() %><%/*java.text.DateFormat.getDateInstance(1).format(new java.util.Date())*/%><br><br>
							_______________________________________<br>
							Deean: <b><%=coordinador%></b><br><br>
							_______________________________________<br>
							Lecturer: <b><%=maestroNombre%></b><br>

						</td></tr>
					</table>
				</td>
			  </tr>
			  <tr>
			  	<td colspan="2">
					<table style="width:100%" cellspacing="2">
					  <tr>
						<th><spring:message code="aca.Numero"/></th>
						<th><spring:message code="aca.Plan"/></th>
						<th>Student ID</th>
						<th>Name</th>
<%		for (i=0;i<lisMeses.size();i++){ %>
						<th width="25"><%=meses[Integer.parseInt((String)lisMeses.get(i))-1]%></th>
<%		}%>
						<th>Points</th>
						<th>Total</th>
						<th>Ex.P.</th>
						<th>Avg.</th>
						<th>Ex.G.</th>
						<th>Status</th>
					  </tr>
<%	  
	//aca.vista.AlumnoCursoUtil acu	= new aca.vista.AlumnoCursoUtil();
	//ArrayList<aca.vista.AlumnoCurso> lisAlumnos 						= acu.getListCurso(conEnoc, cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
		
	for (AlumnoCurso ac : lisAlumnos){
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(ac.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(ac.getCodigoPersonal());
		}
		
		String escala = "10";
		AlumPlan plan = new AlumPlan();
		if (mapaPlanes.containsKey(ac.getCodigoPersonal()+ac.getCursoId().substring(0,8))){
			plan = mapaPlanes.get(ac.getCodigoPersonal()+ac.getCursoId().substring(0,8));
			escala = plan.getEscala();
		}
		
		if (!ac.getTipoCalId().equals("M")){ row++;
%>
					  <tr>
						<td align="center" height="15"><%=row%></td>
						<td align="center"><%=ac.getCursoId().substring(0,8)%></td>
						<td align="center"><%=ac.getCodigoPersonal()%></td>	
						<td align="left"><%=alumnoNombre%></td>
<%
			for (j=0;j<lisMeses.size();j++){
				
				notaEvaluacion = "0";
				if (mapaPromediosPorMes.containsKey(ac.getCodigoPersonal()+(String) lisMeses.get(j))){
					notaEvaluacion = mapaPromediosPorMes.get(ac.getCodigoPersonal()+(String) lisMeses.get(j));
				}
				//krdxEvaluacionUtil.getAlumnoPromedio(conEnoc, ac.getCursoCargaId(), ac.getCodigoPersonal(),(String) lisMeses.get(j));
				if (notaEvaluacion==null || notaEvaluacion.equals("")) notaEvaluacion="0";
				if(escala.equals("10")){%> 
					<td width="25" align="center"><%=df.format(Double.parseDouble(notaEvaluacion)/10).equals("10.0") ? "10" : df.format(Double.parseDouble(notaEvaluacion)/10)%>&nbsp;</td>
			<%	} 	
				else {%> 
					<td width="25" align="center"><%=(int)(Float.parseFloat(notaEvaluacion))%>&nbsp;</td>
					
			<%	}			
			}

			KrdxCursoAct kardex = new KrdxCursoAct();
			if (mapaNotas.containsKey(ac.getCodigoPersonal())){
				kardex 		= mapaNotas.get(ac.getCodigoPersonal());				
			}

			if(estadoMateria.equals("2")||estadoMateria.equals("3") || estadoMateria.equals("4") || estadoMateria.equals("5")){				
				promedio = kardex.getNota();
			}else{
				promedio = "0";
			}				
			if (promedio==null) promedio="0";
			
			promedio = String.valueOf((long)Math.floor(Double.valueOf(promedio).doubleValue() + 0.5d));
			
			if (kardex.getTitulo().equals("S")){ 
				String nota = kardex.getNota();;
				if(nota==null){
					nota="0";
				}
				promedio = nota;
			}
			
			String puntosEvaluados	= "0";
			if (mapaEvaluadas.containsKey(ac.getCodigoPersonal())){
				puntosEvaluados = mapaEvaluadas.get(ac.getCodigoPersonal());
			}
			
			String puntosAlumno	= "0";
			if (mapaAlumnoPuntos.containsKey(ac.getCodigoPersonal())){
				puntosAlumno 	= mapaAlumnoPuntos.get(ac.getCodigoPersonal());
			}
			
			String puntosExtras 	= "0"; 
			if (mapaAlumnoExtras.containsKey(ac.getCodigoPersonal())){
				puntosExtras 	= mapaAlumnoExtras.get(ac.getCodigoPersonal());
			}			
			if (puntosExtras==null || puntosExtras.equals("0")) puntosExtras="";
%>						
						<td align="center"><% 
							if(escala.equals("10")){%> 
								<b><%=df.format(Float.parseFloat(puntosAlumno)/10).equals("10.0") ? "10" : df.format(Float.parseFloat(puntosAlumno)/10)%></b>
						<%	}
							else{%>
								<b><%=(int)(Float.parseFloat(puntosAlumno))%></b>
						<% 	}%>								
						</td>
						
						<td align="center"><%
							if(escala.equals("10")){%>
								<b><%=df.format(Float.parseFloat(puntosEvaluados)/10).equals("10.0") ? "10" : df.format(Float.parseFloat(puntosEvaluados)/10)%></b>
						<%	}
							else{%>
								<b><%=(int)(Float.parseFloat(puntosEvaluados))%></b></td>
						<% 	}%>
						<td align="center"><b><%=puntosExtras%></b></td>
						<td align="center"><b><%=escala.equals("10") ? (df.format(Float.parseFloat(promedio)/10).equals("10.0") ? "10" : df.format(Float.parseFloat(promedio)/10)) : (int)(Float.parseFloat(promedio))%></b></td>
						<td align="center"><b>
<%			if (kardex.getNotaExtra()==null || kardex.getNotaExtra().equals("0")){
				if (kardex.getTitulo().equals("S")) out.println("Titulo"); else out.print("&nbsp;");
			}else{
				if ( Integer.parseInt(kardex.getNotaExtra()) > 0 ){
					out.print( escala.equals("10") ? (df.format(Float.parseFloat(kardex.getNotaExtra())/10).equals("10.0") ? "10" : df.format(Float.parseFloat(kardex.getNotaExtra())/10)) : (int)(Float.parseFloat(kardex.getNotaExtra())));
				}
				
			}

			String tipoNombre = "";
			if (mapaTipos.containsKey(kardex.getTipoCalId())){
				tipoNombre = mapaTipos.get(kardex.getTipoCalId()).getNombreTipoCal();
			}
%>						</b></td>
						<td align="center"><%=tipoNombre%></td>
					 </tr>
<%		} // termina if tipoCal != 'M'

	} //fin de ciclo lisAlumnos
%>
					<tr>
						<th colspan="30">End of Student Listing &nbsp; &nbsp; &nbsp; &nbsp;   #<%=cursoCargaId%></th>
					  </tr>
				  </table>
				</td>
			  </tr>
			</table>
			
		</td>
	</tr>
</table>
</body>