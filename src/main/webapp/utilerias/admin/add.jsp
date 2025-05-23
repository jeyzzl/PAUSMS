<%@ include file="../../con_enoc.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../academico.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />


<script src="typeahead/jquery.js"></script>
<script src="typeahead/bootstrap-transition.js"></script>
<script src="typeahead/bootstrap-modal.js"></script>
<script src="typeahead/bootstrap-typeahead.js"></script>

  	
<link rel="stylesheet" href="css/TextboxList.css" media="screen"/>
<link rel="stylesheet" href="css/TextboxList.Autocomplete.css" media="screen"/>  
<script src="js/TextboxList/SuggestInput.js"></script>
<script src="js/TextboxList/GrowingInput.js"></script>
<script src="js/TextboxList/TextboxList.js"></script>    
<script src="js/TextboxList/TextboxList.Autocomplete.js"></script>
<script src="js/TextboxList/TextboxList.Autocomplete.Binary.js"></script>
<script src="js/jquery.uniform.min.js" type="text/javascript"></script>
 
 
<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="ckeditor/adapters/jquery.js"></script>
<script src="ckeditor/sample.js" type="text/javascript"></script>
 
 
<jsp:useBean id="AdminTareas" scope="page" class="aca.util.AdminTareas"/>
<jsp:useBean id="AdminTareasU" scope="page" class="aca.util.AdminTareasUtil"/>
<script>
	function grabar(){
		var maxlong = 2000;
		var valor = (maxlong-jQuery('#Descripcion').val().length);
		if(valor>=0){
			document.forma.Descripcion.value = jQuery('#Descripcion').val()
			document.forma.Accion.value = "1";
			document.forma.submit();
		}else{
			alert("La descripción tiene demaciados caracteres");
		}
	}
</script>

<head>
<%
	String accion 	 = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String resultado = "";

	String maximo = AdminTareas.maximoReg(conEnoc);
	String folio	= request.getParameter("folio")==null?maximo:request.getParameter("folio");
	
	
	if(accion.equals("1")){
		AdminTareas.mapeaRegId(conEnoc, request.getParameter("Folio"));
		
		AdminTareas.setFolio(request.getParameter("Folio"));
		AdminTareas.setNombre(request.getParameter("Nombre"));
		AdminTareas.setDescripcion(request.getParameter("Descripcion"));
		
		AdminTareas.setDesarrollador(request.getParameter("Desarrollador"));
		AdminTareas.setCliente(request.getParameter("Cliente"));
		AdminTareas.setModulo(request.getParameter("Sistema"));
		
		String estado = request.getParameter("Estado");
		AdminTareas.setEstado(estado);
		
		if(!AdminTareas.existeReg(conEnoc)){
			AdminTareas.setfCrea(aca.util.Fecha.getHoy());
		}
		
		if(estado.equals("A")){
			if(!AdminTareas.existeReg(conEnoc)){
				AdminTareas.setfCrea(aca.util.Fecha.getHoy());
			}
			AdminTareas.setfInicio(aca.util.Fecha.getHoy());
		}else if(estado.equals("T")){
			AdminTareas.setfTerminado(aca.util.Fecha.getHoy());
		}
		
		if(AdminTareas.existeReg(conEnoc)){
			if(AdminTareas.updateReg(conEnoc)){
				resultado = "<font color=blue>Modificado</font>";
			}else{
				resultado = "<font color=red>No Modificó</font>";
			}
		}else{
			if(AdminTareas.insertReg(conEnoc)){
				resultado = "<font color=blue>Grabado</font>";
			}else{
				resultado = "<font color=red>No Grabó</font>";
			}
		}
		
	}
		
	if(!folio.equals("")){
		AdminTareas.mapeaRegId(conEnoc, folio);
	}


	String source = " ";
	
	ArrayList<aca.util.AdminTareas> tareas = AdminTareasU.getListAll(conEnoc, "ORDER BY CLIENTE");
	ArrayList<String> tmp = new ArrayList<String>();
	
	
	for(aca.util.AdminTareas tarea: tareas){
		
		if(!tmp.contains(tarea.getCliente())){
			source = source + " \""+tarea.getCliente()+"\",";
		}
		
		tmp.add(tarea.getCliente());
	}
	
	source = source.substring(0,source.length()-1);
	
	String sis = AdminTareas.getModulo().equals("")?"academico":AdminTareas.getModulo();
%>
</head>
<body>
<form name="forma" method="post" action="add?folio=<%=folio%>">
<input type="hidden" name="Accion">
<table style="width:80%; margin:0 auto;">
	<tr>
		<td align="center">
			<h2>Añadir Tarea</h2>
			<a class="btn btn-primary" href="tareas?sistema=<%=sis%>"><spring:message code="aca.Regresar"/></a>
			<a class="btn btn-primary" href="add"><spring:message code='aca.Nuevo'/></a>
		</td>
	</tr>
	<%if(!resultado.equals("")){ %>
	<tr>
		<td colspan="2" style="text-align:center;"><%=resultado %></td>
	</tr>
	<%} %>
</table>
<br>
<table class="table table-condensed table-nohover"  align="center">
	<tr>
		<td><h5><spring:message code='aca.Folio'/>:</h5><input name="Folio" type="text" value="<%=folio%>" readonly></td>
		<td style="text-align:right;"><h5>Sistema:</h5>
			<select name="Sistema">
				<option value="academico" <%if(AdminTareas.getModulo().equals("academico"))out.print("selected");%> ><spring:message code="aca.Academico"/></option>
				<option value="escuelas" <%if(AdminTareas.getModulo().equals("escuelas"))out.print("selected");%> >Escuelas</option>
			</selcet>
		</td>
	</tr>
	<tr>
		<td>
			<h5>Cliente:</h5>
			<input value="<%=AdminTareas.getCliente()%>" type="text" name="Cliente" class="span3" autocomplete="off" style="margin: 0 auto;" data-provide="typeahead" data-items="4" data-source='[<%=source%>]'>
		</td>
		<td style="text-align:right;"><h5><spring:message code="aca.Estado"/>:</h5>
			<select name="Estado">
				<option value="I" <%if(AdminTareas.getEstado().equals("I"))out.print("selected");%> ><spring:message code='aca.Inactivo'/></option>
				<option value="A" <%if(AdminTareas.getEstado().equals("A"))out.print("selected");%> ><spring:message code='aca.Activo'/></option>
				<option value="T" <%if(AdminTareas.getEstado().equals("T"))out.print("selected");%> >Terminado</option>
			</selcet>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<h5><spring:message code="aca.Nombre"/>:</h5>
			<input value="<%=AdminTareas.getNombre()%>" type="text" name="Nombre" maxlength="120" style="width:100%;">
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<h5>Desarrolador(es):</h5>
			<input name="Desarrollador" type="text" name="alumnos" id="alumnos" placeholder="Nombre(s) de autor(es)">
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align:center;">
			<textarea class="ckeditor" id="Descripcion" name="Descripcion" ><%=AdminTareas.getDescripcion()%></textarea>
			Caracteres: <span id="cont">2000</span>
		</td>
	</tr>
	<tr>
		<td colspan="4" style="text-align:center;"><a class="btn btn-primary" onclick="grabar();"><spring:message code="aca.Grabar"/></a></td>
	</tr>
</table>
</form>

<script>

jQuery( 'textarea.ckeditor' ).ckeditor();

function maximaLongitud(){
	var texto   = jQuery('#Descripcion');
	var maxlong = 2000;
	
	var valor = (maxlong-texto.val().length);
	cont.innerHTML = valor;
	
	if(valor<0){
		$(cont).css('color', 'red');
	}else{
		$(cont).css('color', 'black');
	}
	
	return true;
}

$(document).ready(function() {
	  
	  var data = [['Jazer',"Jazer",null,"Jazer"],['Jared',"Jared",null,"Jared"],['Sem',"Sem",null,"Sem"],['Ery',"Ery",null,"Ery"]]

	  var autocomplete = new $.TextboxList('#alumnos', {unique: true, plugins: {autocomplete: {onlyFromValues:true,inlineSuggest:false}}});
	  autocomplete.plugins['autocomplete'].setValues(data);
	 
<%
	  if(!folio.equals("") &&  AdminTareas.getDesarrollador()!=null){
		  String[] desarrolladores = AdminTareas.getDesarrollador().split(",");
		  
		  for(String str: desarrolladores){
			  if(str.equals(""))continue;
%>
			autocomplete.add('<%=str%>');
<%
		  }
	  }
%>

});

</script>
</body>
<%@ include file="../../cierra_enoc.jsp"%>