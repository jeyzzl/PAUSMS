<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.catalogo.CatAsociacion"%>
<%@ page import= "aca.catalogo.AsociacionUtil"%>

<script type="text/javascript">
	function Borrar( DivisionId, UnionId, AsociacionId ){
		if(confirm("Estas seguro de eliminar el registro: "+DivisionId+UnionId+AsociacionId)==true){
	  		document.location="accion_a?Accion=4&DivisionId="+DivisionId+"&UnionId="+UnionId+"&AsociacionId="+AsociacionId;
	  	}
	}
</script>

<%
	String sDivision					= request.getParameter("DivisionId");
	String sUnion						= request.getParameter("UnionId");
	ArrayList lisAsociacion				= new ArrayList();	
	AsociacionUtil asociacionU			= new AsociacionUtil();			
	lisAsociacion	 					= asociacionU.getLista(conEnoc, sDivision, sUnion, "ORDER BY 4");
	String sFax						="";
	String sEmail						="";
%>
<div class="container-fluid">
<h1><spring:message code="catalogos.division.Titulo3"/></h1>
<div class="alert alert-info">
	<a class="btn btn-primary" href="union?DivisionId=<%=sDivision%>&UnionId=<%=sUnion%>"><i class="fas fa-arrow-left"></i></a>
	<a class="btn btn-primary" href="accion_a?Accion=1&DivisionId=<%=sDivision%>&UnionId=<%=sUnion%>"><spring:message code='aca.Añadir'/></a>
</div>
<table class="table table-sm table-bordered">  
  <tr>
    <th width="5%"><spring:message code="aca.Operacion"/></th>
    <th width="3%"><spring:message code="aca.Numero"/></th>
    <th width="10%"><spring:message code="aca.Asociacion"/></th>
    <th width="20%"><spring:message code='aca.Direccion'/></th>
    <th width="20%"><spring:message code="aca.Telefono"/></th>
  </tr>
  <%
	java.util.Iterator i = lisAsociacion.iterator();
  	while (i.hasNext()){
		CatAsociacion asociacion = (CatAsociacion) i.next();
		if(asociacion.getDivisionId().equals(sDivision) && asociacion.getUnionId().equals(sUnion)){
%>
  <tr> 
    <td align="center"> <a href="accion_a?Accion=5&AsociacionId=<%=asociacion.getAsociacionId()%>&UnionId=<%=asociacion.getUnionId()%>&DivisionId=<%=asociacion.getDivisionId()%>"> 
      <img title="Editar" src="../../imagenes/editar.gif" alt="Modificar" > 
      </a> <a href="javascript:Borrar ('<%=asociacion.getDivisionId()%>','<%=asociacion.getUnionId()%>','<%=asociacion.getAsociacionId()%>')"> 
      <img src="../../imagenes/no.png" alt="Eliminar" > 
      </a> </td>
    <td align="center"><%=asociacion.getAsociacionId()%></td>
    <td><%=asociacion.getNombreAsociacion()%></td>
    <td><%=asociacion.getDireccion()%></td>
    <td><%=asociacion.getTelefono()%></td>
  </tr>
<%
	}
}	
%>
  <tr> 
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td><div align="right"></div></td>
  </tr>
<%
	asociacionU	 		= null;
	lisAsociacion		= null;
%>
</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %> 