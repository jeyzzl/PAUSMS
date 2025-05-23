<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="SalonU" scope="page" class="aca.musica.MusiSalonUtil"/>
<script type="text/javascript">
	function Borrar( SalonId ){
		if(confirm("Estas seguro de eliminar el registro: "+SalonId)==true){
	  		document.location="accion?Accion=4&SalonId="+SalonId;
	  	}
	}
</script>

<%
	ArrayList<aca.musica.MusiSalon> lisSalon 	= SalonU.getListAll(conEnoc," ORDER BY SALON_ID");
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<body>
<div class="container-fluid">
<h1>Listado de Salones</h1>
<div class="alert alert-info">
 	<a class="btn btn-primary" href="accion?Accion=1"><spring:message code='aca.Añadir'/></a>	
</div>
<table style="width:60%" >
  <tr align="center"> 
  </tr>
</table>  
<table style="width:60%" class="table table-condensed">  
  <tr> 
    <th width="10%"><spring:message code="aca.Operacion"/></th>
    <th width="10%"><spring:message code="aca.Numero"/></th>
    <th width="60%"><spring:message code='aca.Descripcion'/></th>
    <th width="20%">Cupo</th>
  </tr>
<%
	java.util.Iterator iter =  lisSalon.iterator();
	while (iter.hasNext()){
		aca.musica.MusiSalon salon = (aca.musica.MusiSalon) iter.next();
%>
  <tr class="tr2"> 
    <td align="center">
     	<a class="fas fa-edit" href="accion?Accion=5&SalonId=<%=salon.getSalonId()%>">
        </a> 
        <a class="fas fa-trash-alt" href="javascript:Borrar('<%=salon.getSalonId()%>')">
        </a>
    </td>
    <td align="center"><%=salon.getSalonId()%></td>
    <td><%= salon.getSalonNombre() %></td>
    <td align="center"><%=salon.getCupo() %></td>
  </tr>
  <%
	}	
	lisSalon		= null;
%>
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %> 