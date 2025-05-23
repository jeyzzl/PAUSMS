<%@ page import="java.text.*" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.acceso.spring.Acceso"%>

<script type="text/javascript" src="../../js/prototype-1.6.js"></script>
<script>
	function cambiaCarga(){
		document.forma.submit();				
	}	
</script>
<%
	DecimalFormat getformato	= new DecimalFormat("###,##0.00;(###,##0.00)");	
	
	String codigoPersonal		= (String) session.getAttribute("codigoAlumno");
	String planAlumno			= (String) request.getAttribute("planAlumno");
	String carreraId			= (String) request.getAttribute("carreraId");
	String nombrePais			= (String) request.getAttribute("nombrePais");
	String nombreTipo			= (String) request.getAttribute("nombreTipo");
	String carreraNombre		= (String) request.getAttribute("carreraNombre");
	String fechaVenceFm3		= (String) request.getAttribute("fechaVenceFm3");
	AlumPersonal alumPersonal	= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumPlan alumPlan			= (AlumPlan) request.getAttribute("alumPlan");
	Acceso acceso 				= (Acceso) request.getAttribute("acceso");
	boolean existe				= (boolean) request.getAttribute("existe");

	if (existe){
%>
<div class="container-fluid">
	<h1><spring:message code='aca.DatosPersonales'/></h1>
	<div class="alert alert-info"></div>
	<form name="forma" action="mentor_opciones.jsp" target="_self">
	<input type="hidden" name="nAccion">
	<input type="hidden" name="Accion" value="1" />
	<input type="hidden" name="matricula" value="<%=codigoPersonal %>" />
	<table id="table" class="table table-sm table-bordered">
  	<tr>
    <td align="center" colspan="6">
      <table style="width:17%" align="left">
      <tr>
        <td align= "center">
          <img width="97" src="../../foto?Codigo=<%=codigoPersonal%>&Tipo=O" />
        </td>  
      </tr>
      </table>    
	  <table style="width:77%">
	  <tr>
	    <td><b><spring:message code="aca.Nombre"/>:</b></td>
	    <td align="left" colspan="3"><u><%=alumPersonal.getNombre()%></u></td>
	    <td><b>ID:</b></td>
	    <td align="left" class="ayuda alumno <%=codigoPersonal %>"><u><%= codigoPersonal%></u></td>
	  </tr>
	  <tr>
	    <td><b><spring:message code='aca.Carrera'/>:</b></td>
	    <td align="left" colspan="3"><u><%=carreraNombre %></u></td>
	    <td><b>Year:</b>&nbsp;<u><%= alumPlan.getGrado()%></u></td>
	    <td><b>Semester:</b> <u><%= alumPlan.getCiclo()%></u></td>
	  </tr>
	  <tr>
	    <td><b><spring:message code="aca.Nacionalidad"/>:</b></td>
	    <td align="left" colspan="3">
	      <u><%= nombrePais%></u> &nbsp;
	      <% if (!alumPersonal.getNacionalidad().equals("153")) out.println("<b>[ FM3 expires: "+fechaVenceFm3+" ]</b>");%>
	    </td>
	    <td><b>Student type:</b></td>
	    <td align="left"><u><%= nombreTipo%></u></td>
	  </tr>	 
	  </table>
	</td>
	</tr>
<%	
	if(acceso.getAccesos().contains(carreraId) || acceso.getAdministrador().equals("S")){%>
  		<tr><td colspan="6" style="text-align:center;"><input class="btn btn-primary" type="button" value="Update data" onclick="document.location.href='../../portales/mentor/mentor_opciones?matricula=<%=codigoPersonal%>'"/></td></tr>
<% 	} %>    
	</table>
	</form>
<%	}else{%>
		<table style="margin: 0 auto;"><tr><td><h1>Must select a student</h1></td></tr></table>
<%
	} %>
</div>