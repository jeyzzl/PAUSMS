<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatSalon"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar( salonId, edificioId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
			document.location="borrarSalon?SalonId="+salonId+"&EdificioId="+edificioId;
		}
	}
</script>
<%
	String edificioId						= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
	List<CatSalon> lisSalones				= (List<CatSalon>) request.getAttribute("lisSalones"); 
	HashMap<String,String> mapaSalones		= (HashMap<String,String>) request.getAttribute("mapaSalones");
%>
<html>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.edificio.Titulo3"/></h1>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="edificios?EdificioId=<%=edificioId%>"><i class="fas fa-arrow-left"></i></a>&nbsp;
    	<a class="btn btn-primary" href="editarSalon?EdificioId=<%=edificioId%>"><spring:message code="aca.Anadir"/></a>&nbsp;&nbsp;
    	<spring:message code="aca.Filtro"/>:&nbsp; <input type="text" class="form-control search-query" placeholder='<spring:message code='aca.Buscar'/>...' id="buscar" style="width:210px;">	
	</div>
	<table id="tabla" class="table table-sm table-bordered">  
	<thead>
  	<tr>  	
  		<th width="5%"></th>
  		<th width="5%"><spring:message code="aca.Numero"/></th>
  		<th width="5%"><spring:message code="aca.Clave"/></th>
   		<th width="65%"><spring:message code="aca.Descripcion"/></th>
   		<th width="10%" class="right"><spring:message code="aca.Cupo"/></th>
    	<th width="10%" class="right"><spring:message code="aca.Status"/></th>
	</tr>
	</thead>
<%
	int row=0;
	for (CatSalon salon : lisSalones){  		
		if(salon.getEdificioId().equals(edificioId)){
			row++;
			boolean existe = mapaSalones.containsKey(salon.getSalonId());
%>
	<tr class="tr2"> 
  		<td style="text-align: center">
  			<a href="editarSalon?EdificioId=<%=edificioId%>&SalonId=<%=salon.getSalonId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>
 <%			if(existe==false){%>    
      		</a>&nbsp; <a href="javascript:Borrar('<%=salon.getSalonId()%>','<%=salon.getEdificioId()%>')" title="<spring:message code="aca.Eliminar"/>"> <i class="fas fa-trash-alt"></i></a>
 <%			}%>
 			</a>
 		</td>
    	<td class="center"><%=row%></td>
    	<td class="center"><%=salon.getSalonId()%></td>
    	<td><a href="materiasporsalon?EdificioId=<%=edificioId%>&salonId=<%=salon.getSalonId()%>"><%=salon.getNombreSalon()%></a></td>
    	<td class="right"><%=salon.getNumAlumnos()%></td>
    	<td class="right"><%=salon.getEstado().equals("A")?"Active":"Inactive"%></td>
	</tr>
  <%
		}	
	}
%> 
	</table>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#tabla")});
</script>
</body>
</html> 