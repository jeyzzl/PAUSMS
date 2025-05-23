<%@page import="java.util.List"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<% 
	List<aca.Mapa> lista = (List<aca.Mapa>) request.getAttribute("lista");

	boolean borro = (boolean) request.getAttribute("borro");

	int cont = 1;
	String[] array = new String[3];
%>
<div class="container-fluid">
	<h2>SE Statistics &nbsp; <a class="btn btn-success btn-sm" href="cargarArchivo">Upload</a></h2>
	<hr>
<% 	if(borro){%>
	<div id="divBorro" class="alert alert-success" role="alert">Succesfully Erased !!!</div>
	<script type="text/javascript">
		setTimeout(function(){
		  document.getElementById("divBorro").style.display = "none"; 
		 }, 1500);
	</script>
<%  }%>
	<table class="table table-sm table-bordered">
	<thead>
		<tr>
			<th width="10%" scope="col">No.</th>
			<th width="45%" scope="col">Date</th>
			<th width="45%" scope="col">Registers</th>
			<th width="45%" scope="col">Options</th>
		</tr>
	</thead>
	<tbody>
<%	for(aca.Mapa info : lista){
		array = info.getLlave().substring(0,10).split("/");%>
		<tr>
			<td><%=cont++%></td>
			<td><%=array[2]+"/"+array[1]+"/"+array[0]%></td>
			<td><a href="lista?Fecha=<%=info.getLlave()%>"><%=info.getValor()%></a></td>
			<td><a class="btn btn-danger btn-sm" href="javaScript:borrar('<%=info.getLlave()%>');">Borrar</a></td>
		</tr>
<%	}%>
	</tbody>
	</table>
</div>
<script>
	function borrar(fecha){
		var fec = fecha.substring(0, 10);
		if (window.confirm("Are you sure you want to erase the registers from "+fec+" ?")) { 
			document.location.href="borrarRegistros?Fecha="+fec;
		}
	}
</script>