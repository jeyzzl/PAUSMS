<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<%@ page import="aca.carga.spring.CargaAlumno"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.candado.spring.CandTipo"%>
<%@ page import="aca.candado.spring.CandAlumno"%>
<%@ page import="aca.candado.spring.Candado"%>
<%@ page import="aca.financiero.spring.AyudaEstudios"%>
<%@ page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.emp.spring.EmpContacto"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ include file= "../alumno/menu.jsp" %>
<head></head>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno				= (String) session.getAttribute("codigoAlumno");
	AlumPersonal alumPersonal 		= (AlumPersonal) request.getAttribute("alumPersonal");	
	AlumAcademico alumAcademico 	= (AlumAcademico) request.getAttribute("alumAcademico");	
	String planAlumno 				= (String) request.getAttribute("planAlumno");	
	String nombreNacionalidad		= (String) request.getAttribute("nombreNacionalidad");
// 	String fechaVencimiento			= (String) request.getAttribute("fechaVencimiento");
	CatCarrera carrera 					= (CatCarrera) request.getAttribute("carrera");
		
	boolean esExtranjero			= (boolean) request.getAttribute("extranjero");
	boolean inscrito				= (boolean) request.getAttribute("inscrito");
	boolean finPermiso				= (boolean) request.getAttribute("finPermiso");	
	boolean autorizado				= (boolean) request.getAttribute("autorizado");	
	
	int candadoActivo				= 0;
		
	List<CandTipo> lisTipos					= (List<CandTipo>) request.getAttribute("lisTipos");	
	List<CandAlumno> lisCandados 			= (List<CandAlumno>) request.getAttribute("lisCandados");
	List<CargaBloque> lisBloquesActivos		= (List<CargaBloque>) request.getAttribute("lisBloquesActivos");	
		
	HashMap<String,Candado> mapaCandados 	= (HashMap<String,Candado>) request.getAttribute("mapaCandados");
	HashMap<String,EmpContacto> mapaContactos 	= (HashMap<String,EmpContacto>) request.getAttribute("mapaContactos");

	session.removeAttribute("enLineaSiguiente");
		
	String opcion 					= request.getParameter("Opcion")==null?"1":request.getParameter("Opcion");
	
	boolean vencioFM3 		= false;
/*	
	if( ! alumPersonal.getNacionalidad().equals("91") ){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date hoy 			= new Date();
		String color		="",mensaje="";
		Date fechav 		= df.parse(fechaVencimiento);
		int dias 			= new Long((fechav.getTime()-hoy.getTime())/1000/60/60/24).intValue();
		if(dias>=30 && dias<61){
			color="#F39603";
			mensaje="TU FM3 VENCER� EN "+dias+" DIAS, Est�s a tiempo de arreglar tu situaci&oacute;n migratoria.";
		}
		else if(dias<30){
			color="#CE0000";
			if(dias>=1) mensaje="TU FM3 VENCER� EN "+dias+" DIAS, Es urgente que arregles tu situaci&oacute;n migratoria.";
			if(dias<=0){
				mensaje=" TU FM3 VENCI� HACE "+(dias*-1)+" DIAS, Es urgente que arregles tu situaci&oacute;n migratoria.";
				vencioFM3 = true;
			}
		}else{
			color="";
			mensaje=dias + " dias restantes.";
		}							
	}
*/
	boolean validaCurp = aca.alumno.AlumUtil.validarCurp(alumPersonal.getCurp());
%>
<body>
<div class="container-fluid mt-1">
	<div class="alert alert-success">
		<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">1C</span> <spring:message code="portal.alumno.candados.Candados"/>		
		<small class="text-muted"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> ) </small>
	</div>	
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
		<tr>
			<th colspan="2"><spring:message code="portal.alumno.candados.MensajeUno"/></th>
			<th><spring:message code="portal.alumno.candados.Persona"/></th>
			<th><spring:message code="portal.alumno.candados.Correo"/></th>
			<th><spring:message code="portal.alumno.candados.Celular"/></th>
		</tr>
	</thead>	
<%	
	EmpContacto contacto = new EmpContacto();	
	for(CandTipo candTipo : lisTipos){
		boolean tieneAutorizado = true;
		String nombreCandado = "";
		boolean tiene06 = false;

		if (mapaContactos.containsKey(carrera.getCodigoPersonal())){
			contacto = mapaContactos.get(carrera.getCodigoPersonal());
		}
		
		if(candTipo.getEstado().equals("A")){
			for(int i=0; i<lisCandados.size();i++){
				CandAlumno candado = lisCandados.get(i);
					
				String tipoTemp = candado.getCandadoId().substring(0,2);			
				if(tipoTemp.equals("06")){ tiene06 = true; }
					
				String textoPermiso = "";
				if(tipoTemp.equals("04")  && finPermiso == true ){
					textoPermiso = "Financial Permit";
				}
					
				if(candTipo.getTipoId().equals(tipoTemp)){
					tieneAutorizado = false;
					candadoActivo++;
					nombreCandado = "";
					if(mapaCandados.containsKey(candado.getCandadoId()) ){
						nombreCandado = mapaCandados.get(candado.getCandadoId()).getNombreCandado();
					}				
%>
		<tr>	
			<td class="center" width="5%">
<%				if (textoPermiso.equals("")){%>
				<img align="absmiddle" src="../../imagenes/candado.png"  />
<%				}else{ %>					
				<img align="absmiddle" src="../../imagenes/activa.png" width="15px"/>
<%				} %>
			</td>
			<td>
				&nbsp; <%=candTipo.getLugar()%>&nbsp;- <b><font color="#2E2EFE"><%=textoPermiso.equals("")?nombreCandado+" "+candTipo.getTelefono():textoPermiso%></font></b>
			</td>
			<td>
				<%=candTipo.getPersona()%>
			</td>
			<td>
				<%=candTipo.getCorreo()%>
			</td>
			<td>
				<%=candTipo.getCelular()%>
			</td>
		</tr>	
<% 
				}
			}
			if (tieneAutorizado){
%>		<tr>	
			<td class="center" width="5%">
				<img align="absmiddle" src="../../imagenes/activa.png" width="15px"/>
			</td>
			<td>
				<%=candTipo.getLugar()%>&nbsp; <b><font color="#2E2EFE"><%=nombreCandado%></font>
			</td>
			<td>
				<%=candTipo.getPersona()%>
			</td>
			<td>
				<%=candTipo.getCorreo()%>
			</td>
			<td>
				<%=candTipo.getCelular()%>
			</td>
		</tr>	
<%					
			}
			if(candTipo.getTipoId().equals("06") && vencioFM3){
				tieneAutorizado = false;
%>	
		<tr>		
			<td class="center"><img align="absmiddle" src="../../imagenes/candado.png"  /></td>
			<td>
				<%=candTipo.getLugar()%>&nbsp;-&nbsp;<b><font color="#AE2113"><spring:message code="portal.alumno.candados.FM3Vencido"/></font></b>
				- <b><font color="#2E2EFE"><%=candTipo.getTelefono()%></font></b>
			</td>
			<td>
				<%=candTipo.getPersona()%>
			</td>
			<td>
				<%=candTipo.getCorreo()%>
			</td>
			<td>
				<%=candTipo.getCelular()%>
			</td>
		</tr>		
		<tr>		
			<td class="center"><img align="absmiddle" src="../../imagenes/candado.png"  /></td>
			<td>
				<%=candTipo.getLugar()%>&nbsp;-&nbsp;<b><font color="#AE2113"><spring:message code="portal.alumno.candados.FM3Vencido"/></font></b>
				- <b><font color="#2E2EFE"><%=candTipo.getTelefono()%></font></b>
			</td>
			<td>
				<%=candTipo.getPersona()%>
			</td>
			<td>
				<%=candTipo.getCorreo()%>
			</td>
			<td>
				<%=candTipo.getCelular()%>
			</td>
		</tr>		
<% 			}
		}
	}
		if(!autorizado){

			%> 
		<tr>
			<td class="center"><img align="absmiddle" src="../../imagenes/candado.png"/></td>
			<td>
			<spring:message code="portal.alumno.candados.VRADocumentos"/>&nbsp;<b><font color="#AE2113"> - <spring:message code="portal.alumno.candados.CertificacionArchivo"/></font>
			</td>
			<td>
				Advisor
			</td>
			<td>
				<%=contacto.getCorreo()%>
			</td>
			<td>
				<%=contacto.getTelefono()%>
			</td>
		</tr>
<%		}else{%>
		<tr>
			<td class="center"><img align="absmiddle" src="../../imagenes/activa.png" width="15px"/></td>
			<td>
				<spring:message code="portal.alumno.candados.VRADocumentos"/>
			</td>
			<td>
				-
			</td>
			<td>
				-
			</td>
			<td>
				-
			</td>
		</tr>
<%		}%>
	</table>
<%
	if(candadoActivo > 0 || !autorizado || vencioFM3){
		session.removeAttribute("enLineaActivo");
%>
	<br>
	<table class="table table-condensed">	
	<tr>
		<td align="center" colspan="2">
			<b>
				<spring:message code="portal.alumno.candados.MensajeDos"/>
         	</b>
	   	</td> 
	</tr>
	</table>
	<br>
	<table>
	  <tr><td align="center"><font size="3" color="#AE2113"><b>You are not yet ready to register online.</b></font></td></tr>
	</table>
	<br>
<%	}else if(planAlumno.equals("x")){%>
	<br>
	<table>
	  <tr><td align="center"><font size="3" color="#AE2113"><b>You are not yet ready to register online,<br>you must have a study plan assigned</b></font></td></tr>
	</table>
<%	}else if(lisBloquesActivos.size()==0){%>
	<br>
	<table>
	  <tr><td align="center"><font size="3" color="green"><b><spring:message code="portal.alumno.candados.MensajeTres"/></b></font></td></tr>
	</table>
<%	}else{
		session.setAttribute("enLineaActivo", "1");		
%>		
	<div class="alert alert-success">
		You are ready to register online&nbsp;&nbsp;&nbsp;	  
	</div>	
<%	}%>
</div>	
</body>
<script>
	$('.nav-tabs').find('.inscripcion').addClass('active');
</script>