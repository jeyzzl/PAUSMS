<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.catalogo.CatUnion"%>
<%@ page import= "aca.catalogo.UnionUtil"%>

<jsp:useBean id="UnionU" scope="page" class="aca.catalogo.UnionUtil"/>

<script type="text/javascript">
	function Borrar( UnionId, DivisionId ){
		if(confirm("Estas seguro de eliminar el registro: "+UnionId)==true){
	  		document.location="accion_u?Accion=4&UnionId="+UnionId+"&DivisionId="+DivisionId;
	  	}
	}
</script>

<%
	String sDivision			= request.getParameter("DivisionId");
	String sEmail				= "";

	// Lista de uniones
	ArrayList<aca.catalogo.CatUnion> lisUnion			= UnionU.getLista(conEnoc, sDivision, "ORDER BY 3");
	
%>
<div class="container-fluid">
<h1><spring:message code="catalogos.division.TItulo4"/></h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="division"><i class="fas fa-arrow-left"></i></a>
	<a class="btn btn-primary" href="accion_u?Accion=1&DivisionId=<%=sDivision%>"><spring:message code='aca.Añadir'/></a>
</div>
<table class="table" width="80%">   
  <tr> 
    <th width="8%"><spring:message code="aca.Operacion"/></th>
    <th width="3%"><spring:message code="aca.Numero"/></th>
    <th width="15%"><spring:message code="aca.Union"/></th>
    <th width="20%"><spring:message code='aca.Direccion'/></th>
    <th width="20%"><spring:message code="aca.Telefono"/></th>
  </tr>
  <%
	java.util.Iterator i = lisUnion.iterator();	
 	 while (i.hasNext()){
		CatUnion union = (CatUnion) i.next();
		if(union.getDivisionId().equals(sDivision)){
%>
  <tr> 
    <td align="center"> <a href="accion_u?Accion=5&UnionId=<%=union.getUnionId()%>&DivisionId=<%=union.getDivisionId()%>"> 
      <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" > 
      </a> <a href="javascript:Borrar('<%=union.getUnionId()%>','<%=union.getDivisionId()%>')"> 
      <img src="../../imagenes/no.png" alt="Eliminar" > 
      </a> </td>
    <td align="center"><%=union.getUnionId()%></td>
    <td><a href="asociacion?DivisionId=<%=union.getDivisionId()%>&UnionId=<%=union.getUnionId()%>"> 
      <%=union.getNombreUnion()%></a> </td>
    <td><%=union.getDireccion()%></td>
    <td><%=union.getTelefono()%></td>
  </tr>
    <%
	}
}		
	lisUnion			= null;
%>
</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %> 