<%@ page import="java.text.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.util.Fecha" %>
<%@ page import="aca.carga.spring.Carga" %>
<%@ page import="aca.alumno.spring.AlumPersonal" %>
<%@ page import="aca.vista.spring.AlumnoCurso" %>
<%@ page import="aca.catalogo.spring.CatTipoCal" %>

<%@ include file= "id.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../../bootstrap/css/bootstrap-responsive.min.css" type="text/css" media="screen" />
<script src='../../bootstrap/js/bootstrap.min.js' type='text/javascript'></script>

<!-- inicio de estructura -->
<%
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	String cargaId 			= request.getParameter("CargaId")==null?(String)session.getAttribute("cargaId"):request.getParameter("CargaId");
	String fechaHoy 		= aca.util.Fecha.getHoy();
	
	AlumPersonal alumno		= (AlumPersonal) request.getAttribute("alumno");
	Carga carga				= (Carga) request.getAttribute("carga");
	String carreraNombre	= (String) request.getAttribute("carreraNombre");
	Fecha fecha				= new Fecha();
	String mesIni 			= fecha.getMesNombre(carga.getFInicio());
	String mesFin 			= fecha.getMesNombre(carga.getFFinal());
	
	// Lista de cursos del alumno
	List<AlumnoCurso> lisCursos 			= (List<AlumnoCurso>) request.getAttribute("lisCursos");
	HashMap<String,CatTipoCal> mapaTipos 	= (HashMap<String,CatTipoCal>) request.getAttribute("mapaTipos");
%>
<div class="container-fluid">
	<table style="width:100%; margin:0 auto">
	<tr>
    	<td width="140" align="left" valign="TOP" style="font-size: 8pt;">
	  		<div align="left">
	  		<a href="javascript:window.print()">
        		<img src="../../imagenes/logo.jpg"  height="100" alt="Imprimir" width="100" > 
	  		</a>
	  		</div>	
        	<br>
        	<br>
        	<strong>Dirección de Admisiones y Registro</strong><br>
	        Apdo. 16-5 C.P. 67530<br>
	        Montemorelos, NL, <br>
			M&eacute;xico<br>
	        <br>
	        <strong><spring:message code="aca.Tel"/>efonos:</strong><br>
	        Directo(826) 263-0908<br>
	        Conmutador 263-0900<br>
	        Ext. 119,120,121 <br>
	        Fax (826) 263-0979<br>
	        <br><br>
			<b><spring:message code="aca.Creada"/></b> por el Gobierno<br>
			del estado de Nuevo<br>
			León, México, mediante<br>
			Resolución Oficial<br>
			publicada el 5 de mayo<br>
			de 1973.<br><br>
			<b>Clave de la Institución</b><br>
			ante la SEP y Dirección<br>
			General de EStadística<br>
			19MSU1017U<br> 
		</td>
		<td width="6" align="CENTER" valign="TOP" style="font-size: 5pt;">
			<img src="../../images/linea.jpg"  width=2 height=777 alt="">
		</td>		
		<td width="631" colspan="2" align="justify" valign="top">
        	<table style="width:100%" >
			<tr> 
            	<td height="10" colspan="6" style="text-align:center" valign="top">			  
			    	<h3>U N I V E R S I D A D &nbsp; D E &nbsp; M O N T E M O R E L O S,  A . C .</h3>			  			  
				</td>
          	</tr>
		  	<tr> 
            	<td height="10" colspan="6" style="text-align:center" valign="top">
			    	<h4>BOLETA OFICIAL DE CALIFICACIONES</h4>			  			  
				</td>
          	</tr>
		  	<tr>
            	<td height="10" colspan="6" style="text-align:center" valign="top">		  
					<h5>Curso escolar 20<%=cargaId.substring(0,2)%>-20<%=cargaId.substring(2,4)%>,&nbsp;
					período académico de <%=mesIni+" a "+mesFin %>
					</h5>			  
				</td>
          	</tr>
          	<tr><td height="10" colspan="6" align="center" valign="top">&nbsp;</td></tr>
          	<tr> 
            	<td height="10" colspan="6" align="left">
            	<h5>&nbsp;			
			  	Nombre: [ <b><%=alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()%></b> ]
			  	&nbsp;&nbsp;&nbsp;Matricula: [ <b><%=matricula%></b> ]
			  	&nbsp;&nbsp;&nbsp;Programa: [ <b><%=carreraNombre%></b> ]
				</h5>  
				</td>
          	</tr>		  
          	<tr> 
            	<th width="3%" style="text-align:center"><h4><spring:message code="aca.Numero"/></h4></th>
            	<th width="69%" style="text-align:left"><h4>Clave y Nombre de la Materia</h4></th>
            	<th width="4%" style="text-align:right"><h4>Crd.</h4></th>			
            	<th width="5%" style="text-align:right"><h4>Calif.</h4></th>
				<th width="5%" style="text-align:right"><h4>Extra.</h4></th>
            	<th width="17%" style="text-align:center"><h4>Anotaciones</h4></th>
          	</tr>
<% 		
	
	int con = 0;
	for(AlumnoCurso alum : lisCursos){
		con++;
		String tipoCalNombre  = "-";
		if (mapaTipos.containsKey(alum.getTipoCalId())){
			tipoCalNombre = mapaTipos.get(alum.getTipoCalId()).getNombreCorto();
		}
		
%>
			<tr> 
				<td width="3%" height="10" style="text-align:center"><h5><strong><%=con%></strong></h5></td>
	        	<td width="60%" height="10">
					<h5><%=alum.getNombreCurso()%>&nbsp;</h5>		    
				</td>
		        <td width="3%" height="10" style="text-align:right"><h5><%=alum.getCreditos()%></h5></td>
	    	    <td width="4%" height="10" style="text-align:right"><h5><%=alum.getNota()%></h5></td>
				<td width="5%" height="10" style="text-align:right"><h5><%=alum.getNotaExtra()%></h5></td>
	        	<td width="26%" height="10" style="text-align:center"><h5><%=tipoCalNombre%></h5></td>
			</tr>
<% 	} %>
			<tr> 
            	<td height="10" colspan="6" style="text-align:center">_____________________________________________________________________________</td>
          	</tr>
          	<tr> 
            	<td height="10" colspan="6" style="text-align:center">
				<h5>
			  Fin de la boleta que incluye<b>[ <%=con%> ]</b> materia(s) matriculada(s)
				</h5>
				</td>
          	</tr>
        	</table>
     	</td>   	
	</tr>
	</table>       
</div>