<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Estadistica"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatTipoAlumno"%>
<%@ page import= "aca.alumno.spring.AlumPlan"%>
<%@ page import= "aca.util.Fecha"%>
<%@ page import= "java.util.Date,java.text.SimpleDateFormat"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head></head>
<body>	
<%
	String codigo				= (String) session.getAttribute("codigoPersonal");	
	String sCarga				= request.getParameter("f_carga");
	String cargas				= (String)request.getAttribute("cargas");
	String modalidades			= (String)request.getAttribute("modalidades");
	Acceso acceso				= (Acceso)request.getAttribute("acceso");
	boolean tieneAcceso			= (boolean)request.getAttribute("tieneAcceso");
	
	List<CatModalidad> lisModalidades			= (List<CatModalidad>)request.getAttribute("lisModalidades");
	List<Carga> lisCargas						= (List<Carga>)request.getAttribute("lisCargas");
	List<Estadistica> lisExtranjeros			= (List<Estadistica>)request.getAttribute("lisExtranjeros");
	
	HashMap<String,CatPais> mapaPaises				= (HashMap<String,CatPais>) request.getAttribute("mapaPaises");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,Carga> mapaCargas 				= (HashMap<String,Carga>) request.getAttribute("mapaCargas");
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,String> mapaDocumentos			= (HashMap<String,String>) request.getAttribute("mapaDocumentos");
 	HashMap<String,CatTipoAlumno> mapaTipos 		= (HashMap<String,CatTipoAlumno>) request.getAttribute("mapaTipos");
	HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String,AlumPlan> mapaPlanes				= (HashMap<String,AlumPlan>) request.getAttribute("mapaPlanes");
	
	String color			="";	
	SimpleDateFormat df 	= new SimpleDateFormat("dd/MM/yyyy");
	Date hoy 				= df.parse(aca.util.Fecha.getHoy());
	int diasFm3				= 0;
	int diasPasaporte		= 0;
		
	if(tieneAcceso==true){
		
		String fInscripcion					= request.getParameter("fecha")==null?Fecha.getHoy():request.getParameter("fecha");		
%>		
<div class="container-fluid">
	<h1>Listado de extranjeros inscritos</h1>
	<div class="alert alert-info"></div>
	<h1><form id="forma" name="forma" action="ext_inscritos?Accion=1" method="post"></h1>
	<table style="width:700px" class="table table-condensed" style="background:white;border:1px solid gray;">
	<tr>
		<td align="center">
			<table "width:100%">
			<tr>
				<th style="font-size:14px;" width="60%"><spring:message code="aca.Cargas"/></th>
				<th style="font-size:14px;"><spring:message code="aca.Modalidades"/></th>
			</tr>
			<tr>
				<td align="center" valign="top">
					<table>
					<tr><td><b><i><spring:message code="aca.Seleccionar"/>:</i></b></td></tr>
					<tr>
						<td>
							<a onclick="jQuery('.checkboxCarga').prop('checked', true)" class="btn btn-sm"><spring:message code='aca.Todos'/></a> 
							<a class="btn btn-sm" onclick="jQuery('.checkboxCarga').prop('checked', false)"><spring:message code='aca.Ninguno'/></a>
						</td>
					</tr>
<%
				String checkCarga = "";
				for(Carga carga : lisCargas){
				if ( cargas.indexOf("'"+carga.getCargaId()+"'") != -1 ) checkCarga = "checked"; else checkCarga = ""; %>										
					<tr>
						<td>
							<input class="checkboxCarga" type="checkbox" id="<%=carga.getCargaId() %>" name="<%=carga.getCargaId()%>" value="<%=carga.getCargaId() %>" <%=checkCarga%>/>
							<%=carga.getCargaId() %> | <b><i><%=carga.getNombreCarga() %></i></b> [<%=carga.getFInicio() %> - <%=carga.getFFinal() %>																					
						</td>
					</tr>
<%				} %>
					</table>
				</td>
				<td align="center" valign="top">
					<table>
					<tr><td><b><i><spring:message code="aca.Seleccionar"/>:</i></b></td></tr>
					<tr>
				  		<td>
				  			<a onclick="jQuery('.checkboxMod').prop('checked', true)" class="btn btn-sm"><spring:message code='aca.Todos'/></a> 
				  			<a class="btn btn-sm" onclick="jQuery('.checkboxMod').prop('checked', false)"><spring:message code='aca.Ninguno'/></a>
				  		</td>
					</tr>
<%
				String checkModalidad = "";
				for(CatModalidad catModalidad : lisModalidades){
					if ( modalidades.indexOf("'"+catModalidad.getModalidadId()+"'") != -1 ) checkModalidad = "checked"; else checkModalidad = ""; %>
					<tr>
						<td>
							<input class="checkboxMod" type="checkbox" id="mod-<%=catModalidad.getModalidadId()%>" name="mod-<%=catModalidad.getModalidadId() %>" value="<%=catModalidad.getModalidadId() %>" <%=checkModalidad%>/>
							<%=catModalidad.getNombreModalidad() %>														
						</td>
					</tr>
	<%			} %>
					</table>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center" style="text-align:center;">
			<input class="btn btn-primary" type="submit" value="Mostrar" onclick="document.forma.submit();"/>
		</td>
	</tr>
	</table>
<%
	String tmpFacultadId 	= "X";
	String tmpCarreraId 	= "X";
	String tmpMatricula 	= "";
	int cont = 1;
	
	for(Estadistica insc : lisExtranjeros){			
		if(acceso.getAccesos().indexOf(insc.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){
			
			String matricula = insc.getCodigoPersonal();
			String facultadId = insc.getFacultadId();
			String carreraId = insc.getCarreraId();
			String facultadNombre = "-";
			String carreraNombre = "-";
			
			
			if(!tmpFacultadId.equals(facultadId)){		
				
				if (mapaFacultades.containsKey(facultadId)){
					facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad();
				}
				if (!tmpFacultadId.equals("X")) out.print("</table>");
				
				tmpFacultadId = facultadId;
%>				
			<div class="alert alert-info"><h3><%=facultadNombre%></h3></div>
				<table class="table table-bordered table-sm" >			    	
				  	
		<%	}
						
			if(!tmpCarreraId.equals(carreraId)){
				cont = 1;
				tmpCarreraId = carreraId;	
				if (mapaCarreras.containsKey(carreraId)){
	 				carreraNombre = mapaCarreras.get(carreraId).getNombreCarrera();	
	 			}
%>					
					<tr> 
						<h1><td colspan="12" class="titulo2"><b><%=carreraNombre%></h1></font></b></td>
				  	</tr>
					<tr align="center"> 
						<th><b><spring:message code="aca.Numero"/></b></th>
					    <th><b><spring:message code="aca.Matricula"/></b></th>
					    <th width="30%"><b><spring:message code="aca.Nombre"/></b></th>
					    <th><b><spring:message code="aca.Ciclo"/></b></th>
					    <th><b><spring:message code="aca.Nacionalidad"/></b></th>
					    <th><b><spring:message code="aca.Genero"/></b></th>
					    <th><b>Pasaporte</b></th>
					    <th><b>Días</b></th>
					    <th><b>FM3</b></th>
					    <th><b>Días</b></th>
					    <th><b><spring:message code="aca.Modalidad"/></b></th>
					    <th><b><spring:message code="aca.Tipo"/></b></th>
				  	</tr>
		<%	}
			String nacionalidadNombre = "-";
			if (mapaPaises.containsKey(insc.getNacionalidad())){
				nacionalidadNombre = mapaPaises.get(insc.getNacionalidad()).getNombrePais();
			}	
			
			String tipoAlumno = "-";
			if (mapaTipos.containsKey(insc.getTipoAlumnoId())){
				tipoAlumno = mapaTipos.get(insc.getTipoAlumnoId()).getNombreTipo() ;
			}
			
			String modalidadNombre = "-";
			if (mapaModalidades.containsKey(insc.getModalidadId())){
				modalidadNombre = mapaModalidades.get(insc.getModalidadId()).getNombreModalidad();
			}
			
			String ciclo = "0";
			if (mapaPlanes.containsKey(insc.getCodigoPersonal()+insc.getPlanId())){							
				ciclo = mapaPlanes.get(insc.getCodigoPersonal()+insc.getPlanId()).getCiclo();
			}
			
			String venceFm3 = "01/01/2000";
			if (mapaDocumentos.containsKey(insc.getCodigoPersonal()+"2")){
				venceFm3 = mapaDocumentos.get(insc.getCodigoPersonal()+"2");
			}
			
			Date fechaVence = df.parse(venceFm3);
			diasFm3 = new Long((fechaVence.getTime()-hoy.getTime())/1000/60/60/24).intValue();
			
			String vencePasaporte = "01/01/2000";
			if (mapaDocumentos.containsKey(insc.getCodigoPersonal()+"3")){
				vencePasaporte = mapaDocumentos.get(insc.getCodigoPersonal()+"3");
			}					
			
			fechaVence = df.parse(vencePasaporte);
			diasPasaporte = new Long((fechaVence.getTime()-hoy.getTime())/1000/60/60/24).intValue();
		%>
				<tr> 
			    	<td style="text-align:center"><%=cont%></td>
				    <td style="text-align:center"><%=insc.getCodigoPersonal() %></td>
				    <td><font color="<%=color%>"><%=insc.getNombreLegal() %></font></td>
					<td style="text-align:center"><%=ciclo%></td>
				    <td style="text-align:center"><%=nacionalidadNombre%></td>						    
				    <td style="text-align:center"><%=insc.getSexo().equals("F")?"Mujer":"Hombre" %></td>
				    <td style="text-align:center"><%=vencePasaporte%></td>
				    <td style="text-align:center"><%=diasPasaporte%></td>
				    <td style="text-align:center"><%=venceFm3%></td>
				    <td style="text-align:center"><%=diasFm3%></td>
				    <td style="text-align:center"><%=modalidadNombre%></td>
				    <td style="text-align:center"><%=tipoAlumno%></td>
			  	</tr>
		<%		
		}
		cont++;
	}						
%>
	</table>
	</form>
</div>
<%	}else{ %>
		<br>
		<b><font color="#000099">No tiene acceso</font></b>
<%	} %>
</body>
</html>