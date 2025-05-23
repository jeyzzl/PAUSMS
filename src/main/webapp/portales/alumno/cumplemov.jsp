<%@ page import= "java.util.List"%>
<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.Inscritos"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ page buffer= "none" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menumov.jsp" %>
<html>
<head>
<%
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";
%>
<link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css">
<style type="text/css">
.ex1 img{
    border: 2px solid #888;
    float: left;
    margin: 15px;
    -webkit-transition: margin 0.5s ease-out;
    -moz-transition: margin 0.5s ease-out;
    -o-transition: margin 0.5s ease-out;
    border-radius:.4em;
}
 
.ex1 img:hover {
    margin-top: 2px;
}
</style>
</head>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");

	List<Inscritos> lisAlumno					= (List<Inscritos>) request.getAttribute("lisInscritos");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");	
	aca.util.Fecha fecha	= new aca.util.Fecha();	
	
	String fechaHoy 		= fecha.getFecha("1");
	String fechaTemp 		= "";
	
	String sMes				= fecha.getMes(fechaHoy);
	String sMesNombre		= fecha.getMesNombre(fechaHoy);
	String sMatTemp			= "";
	String nombreAlumno		= "";
	
	ArrayList fechas = fecha.getSemanaActual();
	
	int 	mes				= Integer.parseInt(sMes);
	int 	dia 			= Integer.parseInt(fecha.getDia(fechaHoy));
	int		i 				= 0;	
%>
<body onload='muestraPagina();'>
	<script>
		parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
	</script>
<div class="container-fluid">
	<h1><spring:message code="datosAlumno.portal.CumpleTitulo"/> <spring:message code="aca.Del"/> 
		<%=fecha.getDia((String)fechas.get(0))%> <spring:message code="aca.De"/> <%=fecha.getMesNombre((String)fechas.get(0))%>&nbsp;
	</h1>
<%-- 		//<%=fecha.getDia((String)fechas.get(6))%> <spring:message code="aca.De"/> <%=fecha.getMesNombre((String)fechas.get(6))%>	  --%>
	<table  width="70%" >  
<%	for (int j=0;j<1;j++){
		fechaTemp	= (String)fechas.get(j);
		String sDia = fecha.getDia(fechaTemp);
		int row = 0;
%>	</table>
	<table  width="70%"  class="table table-condensed">  
	<tr>
<%
		for (Inscritos alumno : lisAlumno){
			
			if ( !alumno.getCodigoPersonal().equals(sMatTemp) && alumno.getfNacimiento().substring(0,5).equals(fechaTemp.substring(0,5))){
				nombreAlumno = alumno.getNombre();
				if (nombreAlumno.length()>14) nombreAlumno = nombreAlumno.substring(0,13);
				row++;
				
				String carreraNombre 	= "";
				String facultadNombre 	= "";
				if (mapaCarreras.containsKey(alumno.getCarreraId())){
					carreraNombre 	= mapaCarreras.get(alumno.getCarreraId()).getNombreCarrera();
					facultadNombre  = mapaCarreras.get(alumno.getCarreraId()).getFacultadId();
					if (mapaFacultades.containsKey(facultadNombre)){
						facultadNombre =  mapaFacultades.get(facultadNombre).getNombreCorto();
					}
				}
%>  
<%				if ( (row % 2)== 1){ %>
  	<tr>     
<%				}%>  
    	<td align="center" valign="top">
    	<table style="width:10%"  align="left"   class="tabla">
    	<tr colspan="2">
      		<th align="center" title="<%=alumno.getNombre()%>">
      			<font size="4"><%=nombreAlumno%></font>
      		</th>
    	</tr>
    	<tr>
    		<td align="center">
    			<div class="ex1"><img src="../../foto?Codigo=<%=alumno.getCodigoPersonal()%>" width="300"></div>
    		</td>
   		<tr>
    	<tr>
      		<td align="center" title="<%=carreraNombre%>">
				<font size="4"><%=facultadNombre%></font>
	  		</td>
		<tr>
    	</table>
		</td>
<%				if ( (row%2)==0){%>	
	</tr>
<%				}%>  
<%			}
			sMatTemp = alumno.getCodigoPersonal().toUpperCase();
		}
	}
%>  
	</table>
<%	
	fecha			= null;
%>

<script>
	$('.nav-tabs').find('.cumple').addClass('active');
</script> 