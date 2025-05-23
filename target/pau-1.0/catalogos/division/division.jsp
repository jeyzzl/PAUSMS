<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.catalogo.CatDivision"%>
<%@ page import= "aca.catalogo.DivisionUtil"%>

<script type="text/javascript">
	function Borrar( DivisionId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>: "+DivisionId)==true){
	  		document.location="accion_d?Accion=4&DivisionId="+DivisionId;
	  	}
	}
</script>

<%
	ArrayList lisDivision				= new ArrayList();	
	DivisionUtil divisionU				= new DivisionUtil();			
	lisDivision	 						= divisionU.getListAll(conEnoc,"ORDER BY 2");
	String sColonia						="";
	String sEmail						="";
%>
<div class="container-fluid">
<h1><spring:message code="catalogos.division.Titulo"/></h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="accion_d?Accion=1"><spring:message code="aca.Anadir"/></a>
</div>
<table class="table table-sm table-bordered">   
  <tr> 
    <th width="5%"><spring:message code="aca.Operacion"/></th>
    <th width="3%"><spring:message code="aca.Numero"/></th>
    <th width="10%"><spring:message code="catalogos.division.Division"/></th>
    <th width="5%"><spring:message code="aca.NombreCorto"/></th>
    <th width="20%"><spring:message code="catalogos.division.Direccion"/></th>
    <th width="20%"><spring:message code="aca.Telefono"/></th>
   </tr>
  <%
	java.util.Iterator i = lisDivision.iterator();
  	while (i.hasNext()){
		CatDivision division = (CatDivision) i.next();
%>
  <tr class="button"> 
    <td align="center">      
      <img title="Editar" src="../../imagenes/editar.gif" onclick="location.href='accion_d?Accion=5&DivisionId=<%=division.getDivisionId()%>';" title="<spring:message code="aca.Modificar"/>" class="button"> 
      <img src="../../imagenes/no.png" onclick="Borrar('<%=division.getDivisionId()%>');" title="<spring:message code="aca.Eliminar"/>" class="button"> 
    </td>
    <td align="center"><%=division.getDivisionId()%></td>
    <td><a href="union?DivisionId=<%=division.getDivisionId()%>"><%=division.getNombreDivision()%></a></td>
    <td><%=division.getNombreCorto()%></td>
    <td><a href="union?DivisionId=<%=division.getDivisionId()%>"><%=division.getDireccion()%></a></td>
    <td><a href="union?DivisionId=<%=division.getDivisionId()%>"><%=division.getTelefono()%></a></td>
  </tr>
  <%
	}	
	divisionU		 	= null;
	lisDivision			= null;
%>
</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %> 