<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.bec.spring.BecTipo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ page import= "java.util.* "%>

<script>

		function elegirTodos(){
			var elegir = jQuery("#elegir");
			if (elegir.html()=="Todos"){
				jQuery(".mes").attr("checked",true);
				elegir.html("Ninguno");
			}else{				
				jQuery(".mes").attr("checked",false);
				elegir.html("Todos");
			}	
		}

		function modificarMeses(){
			document.frmMeses.submit();			
		}
		
</script>
<style>
 	body{
 		background : white;
 	}
 </style>

<%
	String ejercicioId								= request.getParameter("EjercicioId");
	String tipo 									= request.getParameter("Tipo");	
	String opcion									= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
	BecTipo becTipo 								= (BecTipo)request.getAttribute("becTipo");
	
	HashMap <Integer, String> mapaMeses			= new HashMap <Integer, String>();
	mapaMeses.put(1,"Enero");
	mapaMeses.put(2,"Febrero");
	mapaMeses.put(3,"Marzo");
	mapaMeses.put(4,"Abril");
	mapaMeses.put(5,"Mayo");
	mapaMeses.put(6,"Junio");
	mapaMeses.put(7,"Julio");
	mapaMeses.put(8,"Agosto");
	mapaMeses.put(9,"Septiembre");
	mapaMeses.put(10,"Octubre");
	mapaMeses.put(11,"Noviembre");
	mapaMeses.put(12,"Diciembre");
	
	
	

	String [] arreglo 								= becTipo.getMeses().split("-");
	ArrayList <String> listaMesesSeleccionados		= new ArrayList<String>();
	for(int i=0; i < arreglo.length;i++){
		if(!arreglo[i].equals("")){
			listaMesesSeleccionados.add(arreglo[i]);	
		}
	}
	
%>

<html>
<head>
	<style>
		.empleados:hover{
			font-weight: bold;
		}
	</style>
	<body>
<div class="container-fluid">
<h2>Meses <small class="text-muted fs-4">( Para <%=becTipo.getNombre()%>)</small></h2>
<div class="alert alert-info">
	<a href="becas?ejercicioId=<%=ejercicioId%>" style="text-align:left" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>			
	<a class="btn btn-success" style="text-align:left" onclick="modificarMeses();" ><i class="fas fa-check"></i> Guardar meses</a>
	<a class="btn btn-primary" style="text-align:left" href="editarmeses?EjercicioId=<%=ejercicioId%>&Tipo=<%=tipo%>&Opcion=<%=opcion.equals("0")?"1":"0"%>">
	  <%=opcion.equals("0")?"Mostrar Todos":"Mostrar elegidos"%>
	</a>
</div>
	<br>
	<form name="frmMeses" action="grabarmeses" method="post">
		<input type="hidden" name="EjercicioId" value="<%=ejercicioId%>">
		<input type="hidden" name="Tipo" value="<%=tipo%>">
		
		<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">	 
				<tr>
					<th width="10%" align="center"><a id="elegir" class="btn btn-small btn-info" onclick="javascript:elegirTodos();" style="cursor:pointer; font-size:12px;">Todos</a></th>					
					<th width="15%"><spring:message code='aca.Mes'/></th>
					<th width="50%"><spring:message code="aca.Nombre"/></th>
				</tr>
			</thead>
			<tbody>
					
<%		
for(int i=1; i <= 12; i++){
	if(!listaMesesSeleccionados.contains(String.valueOf(i)) && opcion.equals("0")){
			continue;
		}
%>
			<tr>					
				<td style="text-align:center;"><input type="checkbox" class="mes" id="<%=i%>" name="<%=i%>" value="S" <%if(listaMesesSeleccionados.contains(String.valueOf(i))){%>checked<%} %> /></td>					
				<td align="center"><%=i%></td>
				<td align="center"><%=mapaMeses.get(i)%></td>
			</tr>				
<% 		
}
%>
			</tbody>
		</table>
	</form>
</div>
	</body>
</html>
