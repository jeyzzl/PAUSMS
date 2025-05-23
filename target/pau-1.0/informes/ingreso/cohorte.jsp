<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.plan.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%	
	String facultad 		= request.getParameter("facultad")==null?"0":request.getParameter("facultad");
	String cargaId     		= (String)request.getAttribute("cargaId");
	String facultadNombre	= (String)request.getAttribute("facultadNombre");
	 
	List<String> lisCargas 					= (List<String>)request.getAttribute("lisCargas");	
	List<String> lisCarreras				= (List<String>)request.getAttribute("lisCarreras");
	List<aca.Mapa> lisPlanes				= (List<aca.Mapa>)request.getAttribute("lisPlanes");
	
	HashMap<String,CatCarrera> mapCarrera 	= (HashMap<String,CatCarrera>)request.getAttribute("mapCarrera");
	
	String sBgcolor		= "";
	int cont			= 0;
%>
<div class="container-fluid">
	<h1>Planes de <%=facultadNombre%></h1>
	<form name="frmIngreso" style="margin:0;">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="facultad"><i class="fas fa-arrow-left"></i></a>
		<strong>Carga:</strong>
		<select name="CargaId" id="CargaId" style="width:100px;" onchange="javascript:refrescar('<%=facultad%>')">
	<%
		for(String cargas: lisCargas){			
	%>
			<option value="<%=cargas%>" <%if(cargas.equals(cargaId))out.print("selected"); %>><%=cargas%></option>
	<%
		}
	%>
		</select>&nbsp;
	</div>
	</form>
	<table class="table table-bordered">
	<thead class="table-info"> 	
	<tr>
		<th width="5%"><h3><spring:message code="aca.Numero"/></h3></th>
		<th width="10%"><h3><spring:message code="aca.Clv"/></h3></th>
		<th width="30%"><h3><spring:message code="aca.Nombre"/></h3></th>
		<th width="55%"><h3><spring:message code="aca.Planes"/></h3></th>
	</tr>	
	</thead>	
<%
	for (String carrera : lisCarreras){
		cont++;		
		String nombreCarrera = "";
		if (mapCarrera.containsKey(carrera)) nombreCarrera 	= mapCarrera.get(carrera).getNombreCarrera();
%>  
		<tr class="tr2" <%=sBgcolor%>>
			<td align="center"><strong><%=cont%></strong></td>
			<td align="center"><strong><%=carrera%></strong></td>
			<td><strong><%=nombreCarrera%></strong></td>
			<td>
<%
			for (aca.Mapa mapa : lisPlanes){								
				if (mapa.getValor().equals(carrera)){%>
				<a href="promedio?facultad=<%=facultad%>&planId=<%=mapa.getLlave()%>&CargaId=<%=cargaId%>"><%=mapa.getLlave()%></a>&nbsp;&nbsp;&nbsp;
<%			}
		}
%>  
			</td>				    
		</tr>
<%	} %>
	</table>
</div>
<script type="text/javascript">

	function refrescar(fac){
		document.location.href="cohorte?CargaId="+document.frmIngreso.CargaId.value+"&facultad="+fac;
	}

 </script>