<%@ include	file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="padreU" scope="page" class="aca.padre.PadreAlumnoUtil"/>
 
<%-- <jsp:include page="../menuPadre.jsp" /> --%>
<%@ include file= "../menuPadre.jsp" %>
<%	
	String codigoPadre	= (String) session.getAttribute("codigoPersonal");

	// Lista de hijos
	ArrayList<aca.padre.PadreAlumno> lisPadre  = padreU.getLista(conEnoc, codigoPadre, "ORDER BY 2");
	
%>
<div class="container-fluid">
	<h2>
		Padre:  <%=aca.padre.PadrePersonal.getNombrePadre(conEnoc, codigoPadre, "")%>
	</h2>

	<table style="width:75%"  class="table">
		<tr> 
	    	<th width="9%"><spring:message code="aca.Numero"/></th>
	   		<th width="23%"><spring:message code="aca.Matricula"/></th>
	    	<th width="68%"><spring:message code="aca.Alumno"/></th>
	  	</tr>
<%	for(int i=0; i<lisPadre.size(); i++){
		aca.padre.PadreAlumno padre = (aca.padre.PadreAlumno) lisPadre.get(i);
%>
		<tr> 
    		<td><%= i+1%></td>    		
    		<td><a href="javascript:elegirAlumno('<%=padre.getCodigoPersonal()%>')"><%=padre.getCodigoPersonal()%></a></td>
    		<td>
    			<a href="javascript:elegirAlumno('<%=padre.getCodigoPersonal()%>')">
    		 	<%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, padre.getCodigoPersonal(),"NOMBRE")%>
    		 	</a>
    		</td>
  		</tr>
  <%	}	%>
	</table>
</div>
<script>
	function elegirAlumno(hijo){		
		location.href = "datos?Hijo="+hijo;
	}
</script>
<script>
	$('.nav-tabs').find('.Hijos').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp"%>