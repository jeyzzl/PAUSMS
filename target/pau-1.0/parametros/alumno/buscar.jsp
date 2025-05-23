<%@ page import= "java.util.List"%>
<%@ page import= "aca.vista.spring.Usuarios"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script  type="text/javascript">

		function Completo(){
			document.frmBuscar.Accion.value = "1";
			document.frmBuscar.Completo.value = "S";
			document.frmBuscar.submit();
		}

		function Consultar(){
			document.frmBuscar.Accion.value="1";
			document.frmBuscar.Completo.value = "N";
			document.frmBuscar.submit();
		}
		
		function BuscarNombre(opcion){
			if(document.frmBuscar.NombreCompleto.value != ""){					
				document.frmBuscar.Opcion.value=opcion;			
				document.frmBuscar.Completo.value = "N";
				document.frmBuscar.Accion.value="1";
				document.frmBuscar.submit();
			}else{
				alert("Ingresa un dato para la busqueda...");
				document.frmBuscar.NombreCompleto.focus();
			}				
		}	

		function BuscarCodigo( ){
			if(document.frmBuscar.CodigoPersonal.value != ""){
				document.frmBuscar.Accion.value="2";
				document.frmBuscar.Completo.value = "N";
				document.frmBuscar.submit();
			}else{
				alert("Ingrese el código ");
				document.frmBuscar.CodigoPersonal.focus();
			}
		}

		function SubirCodigo( codigo){			
	  		document.location.href="buscar?Accion=3&CodigoPersonal="+codigo;
		}
		
		
</script>

<%		
	Usuarios usuario			= (Usuarios) request.getAttribute("usuario");
	String mensaje				= (String) request.getAttribute("mensaje");
	List<Usuarios> lisUsuarios 	= (List<Usuarios>) request.getAttribute("lisUsuarios");	
	
	String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String opcion			= request.getParameter("Opcion")==null?"Alumno":request.getParameter("Opcion");	
	int numAccion 			= Integer.parseInt(accion);			
	String nombreCompleto	= request.getParameter("NombreCompleto")==null ? "" : request.getParameter("NombreCompleto");
%>
<body>
<div class="container-fluid">
	<h2>Select Student or Employees</h2>
	<form name="frmBuscar" action="buscar" method="post">
	<input name="Accion" type="hidden">
	<input name="Completo" type="hidden">
	<div class="alert alert-info d-flex align-items-center">Filter search by:&nbsp;
		<select name="Opcion"  class="form-select" onchange="javascript:Consultar();" style="width:170px;">
			<option value="Alumno" <% if(opcion.equals("Alumno")) out.println("Selected");%>>Student</option>
			<option value="Empleado" <% if(opcion.equals("Empleado")) out.println("Selected");%>>Employee</option>	
		</select>    			
	</div>	
	<div class="d-flex flex-row">
	<div class="card" style="width: 21rem;">
  		<div class="card-header bg-dark text-white ">
    		Search by first and/or last name
  		</div>
  		<div class="card-body">
    		<input type="Text" class="form-control" name="NombreCompleto" id="NombreCompleto" value="<%=nombreCompleto.replaceAll("%"," ")%>" size="30"/> <a class="btn btn-primary mt-1"
							href="javascript:BuscarNombre('<%=opcion%>');">Search</a>
					</div>
	</div>	
	&nbsp;
	<div class="card" style="width: 21rem;">
  		<div class="card-header bg-dark text-white">
    		Search by key
  		</div>
  		<div class="card-body">
  			<input type="Text" class="form-control" name="CodigoPersonal" id="CodigoPersonal" size="8" maxlength="7"/>
			<a class="btn btn-primary mt-1" href="javascript:BuscarCodigo()">Search</a>		
  		</div>
	</div>
	</div>
	<table  class="table table-sm table-bordered mt-2">		
		<tr class="table-info">
  			<th width="7%" align="center"><spring:message code="aca.Numero"/></th>
  			<th width="18%" align="center">Key</th>
  			<th width="75%" align="center"><spring:message code="aca.Nombre"/></th>
		</tr>
	<%	
	switch (numAccion){
		case 1:{
			int row = 0;
			for(Usuarios usuarios : lisUsuarios){
				row++;
	%>				
  		<tr>
			<td align="center"><%=row%></td>
			<td align="center"><%=usuarios.getCodigoPersonal()%></td>
			<td>
				<div class="ayuda alumno <%=usuarios.getCodigoPersonal() %>">
					<a href="javascript:SubirCodigo('<%=usuarios.getCodigoPersonal()%>')">
						<%=usuarios.getNombre()%>&nbsp;<%=usuarios.getApellidoPaterno()%>&nbsp;<%=usuarios.getApellidoMaterno()%>	
					</a>
				</div>
			</td>
		</tr>
		<%				
			}
			break;
		}
		case 2:{			
		%>
 		<tr class="tr2">
   			<td align="center">1</td>
   			<td align="center"><%=usuario.getCodigoPersonal()%></td>
   			<td>
  				<a href="javascript:SubirCodigo('<%=usuario.getCodigoPersonal()%>')">
  					<%=usuario.getNombre()%>&nbsp;<%=usuario.getApellidoPaterno()%>&nbsp;<%=usuario.getApellidoMaterno()%>
  				</a>
			</td>
 		</tr>
		<%			
			break;
		}
	}
	%>
	</table>
	</form>
</div>
</body>