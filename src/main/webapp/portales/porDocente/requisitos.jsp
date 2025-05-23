<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%@ include file= "menuPortal.jsp" %>

<jsp:useBean id="PorCategoriaU" scope="page" class="aca.por.PorCategoriaUtil"/>
<jsp:useBean id="PorCategoria" scope="page" class="aca.por.PorCategoria"/>
<jsp:useBean id="PorSeccion" scope="page" class="aca.por.PorSeccion"/>
<jsp:useBean id="PorRequisitoU" scope="page" class="aca.por.PorRequisitoUtil"/>
<jsp:useBean id="PorRequisitoEmp" scope="page" class="aca.por.PorRequisitoEmp"/>

<script type="text/javascript">
	function Refrescar(){		
		document.frmRequisitos.submit();
	}	
	
	function GrabarRequisitos(){
		document.frmRequisitos.Accion.value="1";
		document.frmRequisitos.submit();
	}
	
	function multiplicar(){
		var id = parseInt(document.getElementById('id').innerHTML); //toma el valor del ciclo de requisitos 
		var cant = parseInt(document.getElementById('cant').value); //toma el valor del select 
		var valor = parseInt(document.getElementById('valor').value); //toma el valor de cada requisito
		
		var resultado;
		/* document.write(id); */
		var i;
			resultado = valor * cant;
			document.getElementById('puntos').value = resultado; //pasar la variable de js a html
	}
	
	function sumar (valor) {
	    var total = 0;	
	    valor = parseInt(valor); // Convertir el valor a un entero (número).
		
	    total = document.getElementById('total').value;
		
	    // Aquí valido si hay un valor previo, si no hay datos, le pongo un cero "0".
	    total = (total == null || total == undefined || total == "") ? 0 : total;
		
	    // Esta es la suma. 
	    total = (parseInt(total) + valor);
		
	    // Colocar el resultado de la suma en el control "span".
	    document.getElementById('total').value = total;
	    total = 0; 
	}
	       
</script>
<%	
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String codigoPersonal 		= session.getAttribute("codigoPersonal").toString();
	String codigoEmpleado		= session.getAttribute("codigoEmpleado").toString();
	
	// Tomar el codigo del empleado cuendo es remplazdo por un usuario con derechos de administrador 
	if (!codigoPersonal.equals(codigoEmpleado)) codigoPersonal = codigoEmpleado;
	
	// Trae el valor del portafolio en sesion
	String portafolioSession	= session.getAttribute("portafolioId")==null?"0":session.getAttribute("portafolioId").toString();
	
	// Trae el valor del portafolio como parametro
	String portafolioId			= request.getParameter("PortafolioId")==null?"0":request.getParameter("PortafolioId");
	String categoriaId			= request.getParameter("CategoriaId")==null?"0":request.getParameter("CategoriaId");
	String mensaje				= "";
	
	// Si el portafolio como parametro tiene un nuevo valor
	if ( !portafolioId.equals("0") ){
		session.setAttribute("portafolioId",portafolioId);
	}else{
		portafolioId = portafolioSession;
	}
	
	// Lista de categorias de maestros 
	ArrayList<aca.por.PorCategoria> lisCategoria = PorCategoriaU.getListAll(conEnoc, " ORDER BY CATEGORIA_ID");
	
	// lista de requisitos en una categoria
	ArrayList<aca.por.PorRequisito> lisRequisitos = PorRequisitoU.getListCategoria(conEnoc, categoriaId, " ORDER BY DESCRIPCION");
	
	//checkbox
	if (accion.equals("1")){
		
		int row = 0;
		for (aca.por.PorRequisito requisito : lisRequisitos){
			row ++;
			
			PorRequisitoEmp.setRequisitoId(request.getParameter("check"+row));
			PorRequisitoEmp.setEmpleadoId(codigoPersonal);
			PorRequisitoEmp.setPortafolioId(portafolioId);
			
			if (request.getParameter("check"+row) != null){
				//Grabar
				if(!PorRequisitoEmp.existeReg(conEnoc)){
					if (PorRequisitoEmp.insertReg(conEnoc)){
						mensaje = "Guardado";
					}
				}
				//elimina de la lista los elementos que son deseleccionados
			}else {
				PorRequisitoEmp.setRequisitoId(requisito.getRequisitoId());
				if(PorRequisitoEmp.existeReg(conEnoc)){
					PorRequisitoEmp.deleteReg(conEnoc);
					mensaje = "Guardado";
				}
			}		
		}
	}
%>
<div class="container-fluid">
	<h2><spring:message code="aca.Requisitos"/><small class="text-muted fs-4">( <%= aca.por.PorDocumento.getNombre(conEnoc, portafolioId) %> )</small></h2>
	<form name="frmRequisitos" action="requisitos.jsp" method="post"> 
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a href="portafolio" class="btn btn-primary"><icon class="icon-arrow-left icon-white"></icon> Regresar</a>&nbsp;&nbsp;
		<select name="CategoriaId" onchange="javascript:Refrescar();">
			<option value="0">Seleccionar</option>
<%
		for (aca.por.PorCategoria categoria : lisCategoria){
%>		
			<option value="<%=categoria.getCategoriaId()%>" <%=categoriaId.equals(categoria.getCategoriaId())?"selected":""%> >
				<%=categoria.getCategoriaNombre()%>
			</option>
<% 		}%>			
		</select>
 		<a href="https://drive.google.com/open?id=0Bxv7euucB5bURjNjYU5Ickg3LXM" target="_blank" class="btn btn-info pull-right" title="Descargar"><i class="icon-white icon-arrow-down"></i> Reglamento</a>
	</div>
<%	
	if (Integer.parseInt(categoriaId) > 1){
%>
		<h3>Recuerde que para permanecer dentro del rango debe obtener como mínimo 3 puntos</h3>
<%
	}
	if (mensaje != ""){
%>
		<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se guardó correctamente</div>
<%
	}
%>
	<table class="table">
		<tr>
			<th>#</th>
			<th colspan="2">Requisito</th>
			<th>Cantidad</th>
			<th>Valor</th>
			<th>Puntos</th>
		</tr>
<%
	int row = 0;
	for (aca.por.PorRequisito requisito : lisRequisitos){
		row++;
		
		PorRequisitoEmp.setRequisitoId(requisito.getRequisitoId());
		PorRequisitoEmp.setEmpleadoId(codigoPersonal);
		PorRequisitoEmp.setPortafolioId(portafolioId);
%>
		<tr>
<%
		if (PorRequisitoEmp.existeReg(conEnoc)){
%>			<!--deja marcados elementos que ya estan en la lista-->
			<td><input type="checkbox" value="<%=requisito.getRequisitoId()%>" name="check<%=row%>" checked> <%=row%></td>
<%
		}else {
%>
			<td><input type="checkbox" value="<%=requisito.getRequisitoId()%>" name="check<%=row%>"> <%=row%></td>
<%		
		}
%>			<td style="text-align:right;"><%=aca.por.PorSeccion.getTitulo(conEnoc, portafolioId, requisito.getSeccionId())%></td>
			<td><%=requisito.getDescripcion()%></td>
			<td> 
			<select class="form-control" style="width:55px" onchange="multiplicar()" id="cant">
				<option value="0">0</option>
				<option value="1">1</option>
			 	<option value="2">2</option>
			  	<option value="3">3</option>
			  	<option value="4">4</option>
			  	<option value="5">5</option>
			</select>
			</td>
			<td><input value="<%=requisito.getValor()%>" style="width:35px; border:none; text-align:center;" id="valor"/></td>
			<td><input type="text" style="width:35px; text-align:center;" name="valor"  id="puntos"  onchange="sumar(this.value);"/></td> <!-- onchange="sumar(this.value);" -->
		</tr>
<%	
	} 
	int cant = row;
%>
	<tr>
			<td><span id="id"><%=cant%></span> </td>
			<td></td>
			<td></td>
			<td></td>
			<td><h4>Total</h4></td>
			<td><input type="text" style="width:35px; text-align:center;" id="total"/></td>		
		</tr>
	</table>
<%	
	if (categoriaId.equals("2")){
%>
		<h4 style="color:red;">Para poder ascender al siguiente rango debe obtener 6 puntos</h4><br>
<%
	} else if(categoriaId.equals("3")){
%>
		<h4 style="color:red;">Para poder ascender al siguiente rango debe obtener 9 puntos</h4><br>
<%
	}
%>
		<button onclick="javascript:GrabarRequisitos()" class="btn btn-success">Guardar</button>
	</form>	
</div>
<script>
	jQuery('.requisitos').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp" %>