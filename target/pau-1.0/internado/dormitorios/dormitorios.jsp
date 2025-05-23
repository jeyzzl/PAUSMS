<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.internado.spring.IntDormitorio"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../js/chosen/chosen.css"/>
<script type="text/javascript">
	function Mostrar(){		
		document.formaFecha.submit();
	}
	
	function valida(){
		if (document.forma.id.value=="" || document.forma.nombre.value=="" ){
			alert("Enter an ID for the Dormitory");
			return false;
		}else
			return true;
	}
	function guardar(){
		if (valida()){
			document.forma.accion.value="guardar";
			document.forma.submit();
		}
	}
	function cambios(){
		if (valida()){
			document.forma.accion.value="cambios";
			document.forma.submit();
		}
	}
	function eliminar(id){
		if (confirm("Are you sure you want to delete this dormitory "+id+"?")){
			document.forma.accion.value="eliminar";
			document.forma.did.value=id;
			document.forma.submit();
		}
	}
	function modificar(id){
		document.forma.accion.value="modificar";
		document.forma.did.value=id;
		document.forma.submit();
	}
	function nuevo(){
		document.forma.accion.value="nuevo";
		document.forma.submit();
	}
	function buscar(){
		abrirVentana("bem",600,500,100,250,"no","yes","yes","no","no","buscar",false);
	}
	function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL,modal){
		var sF="";
		if (navigator.appName=="Microsoft Internet Explorer" && modal){
			sF+=T?'unadorned:'+T+';':'';
			sF+=TB?'help:'+TB+';':'';
			sF+=S?'status:'+S+';':'';
			sF+=SC?'scroll:'+SC+';':'';
			sF+=R?'resizable:'+R+';':'';
			sF+=iW?'dialogWidth:'+iW+'px;':'';
			sF+=iH?'dialogHeight:'+(parseInt(iH)+(S?42:0))+'px;':'';
			sF+=TOP?'dialogTop:'+TOP+'px;':'';
			sF+=LEFT?'dialogLeft:'+LEFT+'px;':'';	
			return window.showModalDialog(URL,"",sF);
		}else{
			sF+=iW?'width='+iW+',':'';
			sF+=iH?'height='+iH+',':'';
			sF+=R?'resizable='+R+',':'';
			sF+=S?'status='+S+',':'';
			sF+=SC?'scrollbars='+SC+',':'';
			sF+=T?'titlebar='+T+',':'';
			sF+=TB?'toolbar='+TB+',':'';
			sF+=TB?'menubar='+TB+',':'';
			sF+=TOP?'top='+TOP+',':'';
			sF+=LEFT?'left='+LEFT+',':'';
			return window.open(URL,strName?strName:'',sF).focus()
		}
	}
	
	function cambioSession(codigo, dormiId){
		document.forma.codigoId.value 	= codigo;
		document.forma.dormiId.value 	= dormiId;
		document.forma.accion.value 	= "session";
		document.forma.submit();
	}
	
</script>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<%	
	String fechaIni				= (String)request.getAttribute("fechaIni");
	String fechaFin				= (String)request.getAttribute("fechaFin");
	String redireccionar		= (String)request.getAttribute("redireccionar");
	String accion 				= (String)request.getAttribute("accion");
	String did	 				= (String)request.getAttribute("did");
	
	List<IntDormitorio> lisDormitorios 	= (List<IntDormitorio>)request.getAttribute("lisDormitorios");
	List<Maestros> lisMaestros 			= (List<Maestros>)request.getAttribute("lisMaestros");
	
	HashMap<String,String> mapaDormi					= (HashMap<String,String>)request.getAttribute("mapaDormi");
	HashMap<String,Integer> mapCupoDormitorios			= (HashMap<String,Integer>)request.getAttribute("mapCupoDormitorios");
	HashMap<String,Integer> mapCuartosDormitorios		= (HashMap<String,Integer>)request.getAttribute("mapCuartosDormitorios");
	HashMap<String,Integer> mapRegistradosEnDormitorios	= (HashMap<String,Integer>)request.getAttribute("mapRegistradosEnDormitorios");
	HashMap<String,Integer> mapaAsignados				= (HashMap<String,Integer>)request.getAttribute("mapaAsignados");
	HashMap<String,Integer> mapaOtros					= (HashMap<String,Integer>)request.getAttribute("mapaOtros");
	HashMap<String,Maestros> mapaMaestros				= (HashMap<String,Maestros>)request.getAttribute("mapaMaestros");
%>
<div class="container-fluid">
	<h2>Dormitory Catalog</h2>
	<form name="formaFecha" method="post">
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="javascript:nuevo()">Add</a>&nbsp;&nbsp;&nbsp;
			Start Date:&nbsp;<input class="form-control" data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			<span class="add-on">
	     	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
	   		 </span>
			End Date:&nbsp;<input class="form-control" data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			<span class="add-on">
	     	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
	   		 </span>
			<a href="javascript:Mostrar();" class="btn btn-info"><i class="fas fa-sync-alt"></i></a>	
		</div>	
	</form>
	
	<form name="forma" method="post">
		<input name="accion" type="hidden">
		<input name="did" type="hidden">
		<input name="codigoId" type="hidden">
		<input name="dormiId" type="hidden">
		<input name="admin" type="hidden">
		<table class="table table-sm table-bordered">
		<thead class="table-info">
			<tr>
				<th class="th2" align='center' width='2%'>#</th>
				<th class="th2" align='center' width='4%'>Op.</th>
				<th class="th2" align='center' width='14%'>Dormitory</th>
				<th class="th2" align='center' width='10%'>Dormitory <spring:message code="aca.Genero"/></th>
				<th class="th2" align='center' width='10%'>Dean</th>
				<th class="th2" align='center' width='15%'><spring:message code="aca.Nombre"/></th>
				<th class="th2" align='center' width='5%' title="Dormitory capacity">Capacity</th>
				<th class="th2" align='center' width='5%' title="Students registered per dorm room">Reg. Students</th>
				<th class="th2" align='center' width='5%' title="Available beds">Avil. Beds</th>
				<th class="th2" align='center' width='5%' title="Total Enrolled">Total Enr.</th>
				<th class="th2" align='center' width='5%' title="Enrolled Registered">Enr. Students</th>
				<th class="th2" align='center' width='5%' title="Enrolled without dorm room">Missing</th>
				<th class="th2" align='center' width='5%' title="Other students registered">Other</th>
			</tr>
		</thead>	
<%	
	for (IntDormitorio dormi : lisDormitorios){
		
		int cupo 		= 0;		
		if (mapCupoDormitorios.containsKey(dormi.getDormitorioId())){
			cupo = mapCupoDormitorios.get(dormi.getDormitorioId());
		}

		int registrados = 0;
		if (mapRegistradosEnDormitorios.containsKey(dormi.getDormitorioId())){
			registrados = mapRegistradosEnDormitorios.get(dormi.getDormitorioId());
		}
		
		int disponible = cupo - registrados;
		
		int inscritos = 0;
		if (mapaDormi.containsKey(dormi.getDormitorioId())){
			inscritos = Integer.parseInt(mapaDormi.get(dormi.getDormitorioId()));
		}
		
		int asignados = 0;
		if (mapaAsignados.containsKey(dormi.getDormitorioId())){
			asignados = mapaAsignados.get(dormi.getDormitorioId());
		}
		
		int otros	  = 0;
		if (mapaOtros.containsKey(dormi.getDormitorioId())){
			otros = mapaOtros.get(dormi.getDormitorioId());
		}
		
		int cuartos	  = 0;
		if (mapCuartosDormitorios.containsKey(dormi.getDormitorioId())){
			cuartos = mapCuartosDormitorios.get(dormi.getDormitorioId());
		}
		
		int faltantes = inscritos - asignados;
		
		if(faltantes < 0){
			faltantes = 0;
		}
		
		String nombreMaestro = "";
		if (mapaMaestros.containsKey(dormi.getPreceptor())){
			Maestros maestro = mapaMaestros.get(dormi.getPreceptor());
			nombreMaestro = maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno();
		}

		if (accion.equals("modificar") && did.equals(dormi.getDormitorioId())){
%>
			<tr>
				<td><input type="text" class="text" name="id" size="1" value='<%=dormi.getDormitorioId()%>'></td>
				<td></td>
				<td><input type="text" class="text" name="nombre" size="35" value = '<%=dormi.getNombre()%>'></td>
				<td>
					<select name='sexo' style="width:125px">
						<option value = 'M' <% if (dormi.getSexo().equals("M"))out.print("selected");%>>Male</option>
						<option value = 'F' <% if (dormi.getSexo().equals("F"))out.print("selected");%>>Female</option>
					</select>
				</td>
<%-- 				<td><input type="text" class="text" size="6" maxlength="7" readonly value='<%=dormi.getPreceptor()%>'></td> --%>
				<td><b>Select Employee ></b></td>
				<td>
					<select name="nomina" class="form-select chosen" style="width:350px;">
<% 		for (Maestros maestro: lisMaestros){%>				
						<option value="<%=maestro.getCodigoPersonal()%>" <%=dormi.getPreceptor().equals(maestro.getCodigoPersonal())?"selected":""%>>
							<%=maestro.getCodigoPersonal()%> - <%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%> <%=maestro.getNombre()%>
						</option>
<% 		}%>						
					</select>
				</td>
				<td colspan="7"><a href="javascript:cambios()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
				<input type='button' onclick='document.forma.submit()' class="btn btn-primary" value ='Cancel'></td>
			</tr>
<%
		}else{
%>
			<tr>
				<td><b><%=dormi.getDormitorioId()%></b></td>
				<td align='center'>
					<a title="Edit" href="javascript:modificar('<%=dormi.getDormitorioId()%>')"><i class="fas fa-edit"></i></a>&nbsp;
<% 			
			if ( cuartos == 0){%>		
					<a title="Delete" href="javascript:eliminar('<%=dormi.getDormitorioId()%>')"><i class="fas fa-trash-alt"></i></a>
<% 			}%>				
				</td>
				<td><a href="javascript:cambioSession('<%=dormi.getPreceptor()%>','<%=dormi.getDormitorioId()%>')"><%=dormi.getNombre()%></a></td>
				<td><%if (dormi.getSexo().equals("M"))out.print("Male"); else out.print("Female");%></td>
				<td><%=dormi.getPreceptor()%></td>
				<td><%=nombreMaestro%></td>
				<td class="text-end"><%=cupo%></td>
				<td class="text-end"><%=registrados%></td>
				<td class="text-end"><%=disponible%></td>
				<td class="text-end"><%=inscritos%></td>
				<td class="text-end"><%=asignados%></td>
				<td class="text-end"><%=faltantes%></td>
				<td class="text-end"><%=otros%></td>
			</tr>	
<%	
		}
	}
	if (accion.equals("nuevo")){   
%>
			<tr>
				<td><input type="text" class="text" name="id" size="1"></td>
				<td></td>
				<td><input type="text" class="text" name="nombre" size="35"></td>
				<td><select name='sexo' style="width:125px">
						<option value = 'M'>Male</option>
						<option value = 'F'>Female</option>
					</select>
				</td>
<!-- 				<td><input type="text" class="text" size="6" maxlength="7" readonly></td> -->
				<td><b>Select Employee <i class="fas fa-arrow-right"></i></b></td>
				<td>
					<select name="nomina" class="form-select chosen" style="width:350px;">
<% 		for (Maestros maestro: lisMaestros){%>				
						<option value="<%=maestro.getCodigoPersonal()%>"><%=maestro.getCodigoPersonal()%> - <%=maestro.getApellidoPaterno()%> <%=maestro.getApellidoMaterno()%> <%=maestro.getNombre()%></option>
<% 		}%>						
					</select>	
				</td>
				<td colspan="7"><a href="javascript:guardar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
				<input type='button' onclick='document.forma.submit()' class="btn btn-primary" value ='Cancel'></td>
			</tr>	
<%	}
%>
		</table>
	</form>
</div>
<script>document.forma.id.focus()</script>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery(".chosen").chosen();	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%	if (!redireccionar.equals("X")){%>
	<meta http-equiv="refresh" content="0;url=../../portales/preceptor/personal" />
<%	}%>