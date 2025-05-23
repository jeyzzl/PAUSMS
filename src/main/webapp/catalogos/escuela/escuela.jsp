<%@ page import= "aca.catalogo.spring.CatEscuela"%>
<%@ page import= "java.util.ArrayList"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( EscuelaId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="accion_e?Accion=4&EscuelaId="+EscuelaId;
	  	}
	}
</script>

<%	
	ArrayList<CatEscuela> lisEscuela		= (ArrayList<CatEscuela>) request.getAttribute("lista");
%>
<div class="container-fluid">
<h1><spring:message code="catalogos.escuelas.Titulo"/></h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="accion_e?Accion=1"><spring:message code="aca.Anadir"/></a>
</div>
<table style="width:50%"  id="noayuda">
  <tr align="center"> 
    <td colspan="5"><font size="3"></font></td>
  </tr>
   <tr align="right"> 
    <td colspan="5"></td>
  </tr>
</table>
<table class="table" width="50%" >  
  <tr> 
    <th width="16%"><spring:message code="aca.Operacion"/></th>
    <th width="12%"><spring:message code="aca.Numero"/></th>
    <th width="72%"><spring:message code="catalogos.escuelas.Escuelas"/></th>
  </tr>
<%
	for(CatEscuela escuela:lisEscuela){	
%>
  <tr> 
    <td style="text-align: left"> 
      <a title="<spring:message code="aca.Modificar"/>" href="accion_e?Accion=5&EscuelaId=<%=escuela.getEscuelaId()%>"><i class="fas fa-edit"></i></a>
      &nbsp;<a title="<spring:message code="aca.Eliminar"/>" href="javascript:Borrar('<%=escuela.getEscuelaId()%>')"><i class="fas fa-trash-alt"></i></a> 
    </td>
    <td align="left"><%=escuela.getEscuelaId()%></td>
    <td><%=escuela.getNombreEscuela()%></td>
  </tr>
  <%
	}
%>
</table>
</div>