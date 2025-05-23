<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ page import= "aca.musica.MusiInstitucion"%>
<%@ page import= "aca.musica.MusiInstitucionUtil"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( InstitucionId ){
		if(confirm("Estas seguro de eliminar el registro: "+InstitucionId)==true){
	  		document.location="accion?Accion=4&InstitucionId="+InstitucionId;
	  	}
	}
</script>

<%
	ArrayList lisInstitucion 			= new ArrayList();	
	MusiInstitucionUtil institucionU	= new MusiInstitucionUtil();
	lisInstitucion						= institucionU.getListAll(conEnoc,"ORDER BY 1");	 
%>

<html>
<head>
<div class="container-fluid">
<h1>Listado de Instituciones</h1>
<div class="alert alert-info">
 	<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>	
</div>
<title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<body>
<table style="width:60%"  class="table table-condensed">  
  <tr> 
    <th width="17%"><spring:message code="aca.Operacion"/></th>
    <th width="15%"><spring:message code="aca.Numero"/></th>
    <th width="68%"><spring:message code='aca.Descripcion'/></th>
  </tr>
<%
	java.util.Iterator iter =  lisInstitucion.iterator();
	while (iter.hasNext()){
	MusiInstitucion institucion = (MusiInstitucion) iter.next();	
%>
  <tr class="tr2"> 
    <td align="center">
      <a class="fas fa-edit" href="accion?Accion=5&InstitucionId=<%=institucion.getInstitucionId()%>">
      </a> <a class="fas fa-trash-alt" href="javascript:Borrar('<%=institucion.getInstitucionId()%>')">
      </a> </td>
    <td align="center"><%=institucion.getInstitucionId()%></td>
    <td><%=institucion.getInstitucionNombre()%></td>
  </tr>
  <%
	}	
	institucionU 		= null;
	lisInstitucion		= null;
%>
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>