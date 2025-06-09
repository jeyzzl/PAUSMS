<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.admision.spring.AdmRequisito"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
</head>	
<%

	String facultad					= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
	String carrera					= request.getParameter("Carrera")==null?"0":request.getParameter("Carrera");
	String documento				= request.getParameter("Documento")==null?"0":request.getParameter("Documento");
	String facultadNombre			= (String)request.getAttribute("facultadNombre");
	String carreraNombre			= (String)request.getAttribute("carreraNombre");
	String documentoNombre			= (String)request.getAttribute("documentoNombre");
	AdmRequisito admRequisito		= (AdmRequisito)request.getAttribute("admRequisito");

	List<CatModalidad> lisModalidades 				= (List<CatModalidad>) request.getAttribute("lisModalidades");
	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	
	String [] arrRequisito = new String[0];
	if(!admRequisito.getModalidades().equals("")) arrRequisito = admRequisito.getModalidades().split("-");

	String [] arrNiveles = new String[0];
	if(!admRequisito.getNiveles().equals("")) arrNiveles = admRequisito.getNiveles().split("-");

	String [] arrTipos = new String[0];
	if(!admRequisito.getTipos().equals("")) arrTipos = admRequisito.getTipos().split("-");

	String [] arrNacionalidades = new String[0];
	if(!admRequisito.getNacionalidades().equals("")) arrNacionalidades = admRequisito.getNacionalidades().split("-");

	String [] arrEstadosCiviles = new String[0];
	if(!admRequisito.getEstadosCiviles().equals("")) arrEstadosCiviles = admRequisito.getEstadosCiviles().split("-");
	
	ArrayList<String> lisRequisito 		= new ArrayList<String>(Arrays.asList(arrRequisito));
	ArrayList<String> lisNiveles 		= new ArrayList<String>(Arrays.asList(arrNiveles));	
	ArrayList<String> lisTipos 			= new ArrayList<String>(Arrays.asList(arrTipos));
	ArrayList<String> lisNacionalidades = new ArrayList<String>(Arrays.asList(arrNacionalidades));
	ArrayList<String> lisEstadosCiviles = new ArrayList<String>(Arrays.asList(arrEstadosCiviles));

	int numReqs = lisRequisito.size();
	int numNiveles = lisNiveles.size();
	int numTipos = lisTipos.size();
	int numNacion = lisNacionalidades.size();
	int numEdoCivil = lisEstadosCiviles.size();
	// System.out.println(lisRequisito.size()+":"+lisNiveles.size()+":"+lisTipos.size()+":"+lisNacionalidades.size()+":"+lisEstadosCiviles.size());
%>
<script type="text/javascript">
	function seleccionarTodos(checkAll){
		var prefix = checkAll.getAttribute('data-target-prefix');
		var checkboxes;
		
		if(prefix === 'chkAgregar') {
			// Special handling for modalities table which has numbered checkboxes
			var size = <%=lisModalidades.size()%>;
			for(var i=0; i<size; i++){
				var chk = document.getElementById(prefix + i);
				if(chk != null) chk.checked = checkAll.checked;
			}
		} else {
			// Handle other tables
			checkboxes = document.querySelectorAll('[id^="' + prefix + '"]');
			for(var i=0; i<checkboxes.length; i++){
				checkboxes[i].checked = checkAll.checked;
			}
		}
	}
			
	function Grabar(){			
		document.frmAgregar.submit();	
	}		
</script>
<body>
<div class="container-fluid">
	<h2>Allowed Modalities <small class="text-muted fs-4">( <%=carreraNombre%> - <%=documentoNombre%> )</small></h2>
	
	<!-- Form frmAgregar -->
	<form name="frmAgregar" action="grabar?Facultad=<%=facultad%>&Carrera=<%=carrera%>&Documento=<%=documento%>" method="post" >
	<div class="alert alert-info  d-flex align-items-center" >
		<a href="documentos?Facultad=<%=facultad %>&Carrera=<%=carrera %>" class="btn btn-primary me-2"><spring:message code='aca.Regresar'/></a>
		<b class="me-1"><spring:message code='aca.AutorizadoPorCoordinador'/></b>
		<select name="Autorizar" id="Autorizar" class="form-select me-2" style="width:120px"> 
			<option <% if(admRequisito.getAutorizar().equals("A")) out.print(" Selected ");%> value="A"><spring:message code='aca.No'/></option>
			<option <% if(admRequisito.getAutorizar().equals("C")) out.print(" Selected ");%> value="C"><spring:message code='aca.Si'/></option>
		</select>		
		<b class="me-1"><spring:message code='aca.Requerido'/></b>
		<select name="Requerido" id="Requerido" class="form-select me-3" style="width:120px">
			<option <% if(admRequisito.getRequerido().equals("N")) out.print(" Selected ");%> value="N"><spring:message code='aca.No'/></option>
			<option <% if(admRequisito.getRequerido().equals("S")) out.print(" Selected ");%> value="S"><spring:message code='aca.Si'/></option>
		</select>
		<a href="javascript:Grabar();" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
<%	if(admRequisito.getAutorizar().equals("A") && admRequisito.getRequerido().equals("N") && numReqs == 0 && numNiveles == 0 && numTipos == 0 && numNacion == 0 && numEdoCivil == 0){%>
		<a href="eliminarRequisito?Facultad=<%=facultad %>&Carrera=<%=carrera %>&Documento=<%=documento%>" class="btn btn-danger ms-2"><spring:message code="aca.Borrar"/></a>
<%	}%>
	</div>	
	<table class="table" style="width:50%;">
		<tr>
			<td colspan="3">
				<h3>Modalities</h3>
			</td>
		</tr>	  		
		 <tr>
			<th width="10%"><input name="CheckAllAgregar" type="checkbox" value="S" onclick="javascript:seleccionarTodos(this)" data-target-prefix="chkAgregar"></th>
		   	<th width="10%"><spring:message code="aca.Numero"/></th>
		   	<th width="20%"><spring:message code="aca.Modalidad"/></th>
		</tr>
<%	
	int row = 0;
	for(CatModalidad modalidad : lisModalidades){
		String seleccionar = "";
		if(lisRequisito.contains(modalidad.getModalidadId())){
			seleccionar = "checked";
		}				
%>
		<tr>
			<td>
				<input type="checkbox" id="chkAgregar<%=row%>" name="chkAgregar<%=row+1%>" <%=seleccionar%>/>
		  	</td>
			<td><strong><%=row+1%></strong></td>
			<td><%=modalidad.getNombreModalidad() %></td>
		</tr>
		
<%	
		row++;
	} 
%>
	</table>
	<table class="table" style="width:50%;">
		<tr>
			<td colspan="3">
				<h3>Study Levels</h3>
			</td>
		</tr>
		 <tr>
			<th width="10%"><input name="CheckAllAgregarNiveles" type="checkbox" value="S" onclick="javascript:seleccionarTodos(this)" data-target-prefix="chkNivel"></th>
		   	<th width="10%"><spring:message code="aca.Numero"/></th>
		   	<th width="20%">Study Level</th>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkNivelU" name="chkNivelU" <%=lisNiveles.contains("U")?"checked":""%>/></td>
			<td><strong>1</strong></td>
			<td>Undergraduate</td>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkNivelG" name="chkNivelG" <%=lisNiveles.contains("P")?"checked":""%>/></td>
			<td><strong>2</strong></td>
			<td>Postgraduate</td>
		</tr>
	</table>
	<table class="table" style="width:50%;">
		<tr>
			<td colspan="3">
				<h3>Application Type</h3>
			</td>
		</tr>
		 <tr>
			<th width="10%"><input name="CheckAllAgregarTipos" type="checkbox" value="S" onclick="javascript:seleccionarTodos(this)" data-target-prefix="chkTipo"></th>
		   	<th width="10%"><spring:message code="aca.Numero"/></th>
		   	<th width="20%">App. Type</th>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkTipoS" name="chkTipoS" <%=lisTipos.contains("S")?"checked":""%>/></td>
			<td><strong>1</strong></td>
			<td>School Leaver</td>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkTipoN" name="chkTipoN" <%=lisTipos.contains("N")?"checked":""%>/></td>
			<td><strong>2</strong></td>
			<td>Non-school Leaver</td>
		</tr>
	</table>
	<table class="table" style="width:50%;">
		<tr>
			<td colspan="3">
				<h3>Origin/Nationality</h3>
			</td>
		</tr>
		 <tr>
			<th width="10%"><input name="CheckAllAgregarNacionalidades" type="checkbox" value="S" onclick="javascript:seleccionarTodos(this)" data-target-prefix="chkNac"></th>
		   	<th width="10%"><spring:message code="aca.Numero"/></th>
		   	<th width="20%">Nationality</th>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkNacN" name="chkNacN" <%=lisNacionalidades.contains("N")?"checked":""%>/></td>
			<td><strong>1</strong></td>
			<td>National</td>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkNacI" name="chkNacI" <%=lisNacionalidades.contains("I")?"checked":""%>/></td>
			<td><strong>2</strong></td>
			<td>International</td>
		</tr>
	</table>
	<table class="table" style="width:50%;">
		<tr>
			<td colspan="3">
				<h3>Marital Status</h3>
			</td>
		</tr>
		 <tr>
			<th width="10%"><input name="CheckAllAgregarEstadosCiviles" type="checkbox" value="S" onclick="javascript:seleccionarTodos(this)" data-target-prefix="chkEdo"></th>
		   	<th width="10%"><spring:message code="aca.Numero"/></th>
		   	<th width="20%">Marital Status</th>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkEdoS" name="chkEdoS" <%=lisEstadosCiviles.contains("S")?"checked":""%>/></td>
			<td><strong>1</strong></td>
			<td>Single</td>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkEdoM" name="chkEdoM" <%=lisEstadosCiviles.contains("M")?"checked":""%>/></td>
			<td><strong>2</strong></td>
			<td>Married</td>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkEdoD" name="chkEdoD" <%=lisEstadosCiviles.contains("D")?"checked":""%>/></td>
			<td><strong>3</strong></td>
			<td>Divorced</td>
		</tr>
		<tr>
			<td><input type="checkbox" id="chkEdoV" name="chkEdoV" <%=lisEstadosCiviles.contains("V")?"checked":""%>/></td>
			<td><strong>4</strong></td>
			<td>Widow</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>