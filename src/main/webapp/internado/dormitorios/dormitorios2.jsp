<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="dormi" scope="page" class="aca.internado.Dormitorio"/>
<jsp:useBean id="dormiUtil" scope="page" class="aca.internado.DormitorioUtil"/>
<jsp:useBean id="IntAlumnoU" scope="page" class="aca.internado.IntAlumnoUtil"/>
<jsp:useBean id="inscritosUtil" scope="page" class="aca.vista.InscritosUtil"/>

<script type="text/javascript">
	function Mostrar(){		
		document.formaFecha.submit();
	}
	
	function valida(){
		if (document.forma.id.value=="" || document.forma.nombre.value=="" || document.forma.nomina.value==""){
			alert("Ponga el numero, nombre y la nomina del preceptor del dormitorio");
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
		if (confirm("¿Seguro de eliminar el dormitorio "+id+"?")){
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
	
	function cambioSession(codigo, dormiId, admin){
		document.forma.codigoId.value 	= codigo;
		document.forma.dormiId.value 	= dormiId;
		document.forma.admin.value 		= admin;
		document.forma.accion.value 	= "session";
		document.forma.submit();
	}
	
</script>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<%	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String accionFecha 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	if (accionFecha.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	}

	String accion 			= request.getParameter("accion");
	String id 				= request.getParameter("id");
	String did 				= request.getParameter("did");
	String nombre 			= request.getParameter("nombre");
	String nomina			= request.getParameter("nomina");
	String sexo	 			= request.getParameter("sexo");	
	String codigoId			= request.getParameter("codigoId");
	String dormiId			= request.getParameter("dormiId");
	String sAdmin 			= request.getParameter("admin");
	String redireccionar	= "X"; 
	
	if (id == null) id = "";
	if (nombre== null) nombre = "";
	if (nomina== null) nomina = "";
	if (accion == null) accion = "";
	if (accion.equals("guardar")){
		dormi.setDormitorioId(id);
		dormi.setNombre(nombre);
		dormi.setPreceptor(nomina);
		dormi.setSexo(sexo);
		dormiUtil.insertReg(conEnoc, dormi);
	}else if (accion.equals("cambios")){
		dormi.mapeaRegId(conEnoc,id);
		dormi.setDormitorioId(id);
		dormi.setNombre(nombre);
		dormi.setPreceptor(nomina);
		dormi.setSexo(sexo);
		dormiUtil.updateReg(conEnoc, dormi);
	}else if (accion.equals("eliminar")){
		dormi.mapeaRegId(conEnoc,did);
		dormiUtil.deleteReg(conEnoc, did);
	}else if(accion.equals("session")){
		session.setAttribute("codigoPreceptor",codigoId);
		session.setAttribute("DormitorioId",dormiId);
		session.setAttribute("Admin",sAdmin);
		redireccionar = "../../portales/preceptor/personal";
	}	
	// Map de alumnos por dormitorio
	java.util.HashMap<String,String> mapaDormi = aca.vista.InscritosUtil.mapaInscDormiEntreFechas(conEnoc, "I", fechaIni, fechaFin, "1,4");
%>
<div class="container-fluid">
	<h2>Dormitorios <small class="text-muted fs-4">( UM )</small></h2>
	<form name="formaFecha" method="post">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="javascript:nuevo()">Agregar</a>&nbsp;&nbsp;&nbsp;
			Fecha Inicio: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
	     	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
	   		 </span>
			Fecha Final: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
	     	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
	   		 </span>
			<a href="javascript:Mostrar();" class="btn btn-info btn-sm"><i class="fas fa-sync-alt"></i></a>	
		</div>	
	</form>
	
	<form name="forma" method="post">
	<input name="accion" type="hidden">
	<input name="did" type="hidden">
	<input name="codigoId" type="hidden">
	<input name="dormiId" type="hidden">
	<input name="admin" type="hidden">
	<table class="table table-condensed">
		<tr>
			<th class="th2" align='center' width='5%'><spring:message code="aca.Numero"/></th>
			<th class="th2" align='center' width='5%'><spring:message code="aca.Operacion"/></th>
			<th class="th2" align='center' width='35%'><spring:message code="aca.Nombre"/></th>
			<th class="th2" align='center' width='10%'><spring:message code="aca.Genero"/></th>
			<th class="th2" align='center' width='10%'>Preceptor</th>
			<th class="th2" align='center' width='35%'><spring:message code="aca.Nombre"/> del Preceptor</th>
			<th class="th2" align='center' width='5%'>Cupo</th>
			<th class="th2" align='center' width='5%' title="Alumnos registrados en un cuarto">Registrados</th>
			<th class="th2" align='center' width='5%' title="Total de Inscritos">Tot.Insc.</th>
			<th class="th2" align='center' width='5%' title="Inscritos Registrados">Insc.Reg.</th>
			<th class="th2" align='center' width='5%'>Faltan</th>
			<th class="th2" align='center' width='5%'>Extras</th>
		</tr>
<%	
	ArrayList<aca.internado.Dormitorio> listor = dormiUtil.getListAll(conEnoc," ORDER BY DORMITORIO_ID");

	for (int i=0;i<listor.size();i++){
		dormi = (aca.internado.Dormitorio) listor.get(i);
		
		int cupo 		= aca.internado.CuartoUtil.getCupoDormi(conEnoc, dormi.getDormitorioId(), "'A'");
		int registrados = aca.internado.AlumnoUtil.numAlumRegistrados(conEnoc, dormi.getDormitorioId(), "'A'");
		int inscritos = 0;
		if (mapaDormi.containsKey(dormi.getDormitorioId())){
			inscritos = Integer.parseInt(mapaDormi.get(dormi.getDormitorioId()));
		}
		
		ArrayList<aca.vista.Inscritos> listAsignados = inscritosUtil.getListDormitorioPorFechaInscritos(conEnoc, dormi.getDormitorioId(), fechaIni, fechaFin);
		ArrayList<aca.internado.Alumno> listOtros	 = IntAlumnoU.getListDormitorioPorFechaOtros(conEnoc, dormi.getDormitorioId(), fechaIni, fechaFin);
		
		int asignados = listAsignados.size();
		int otros	  = listOtros.size();
		
		int faltantes = inscritos - asignados;
		
		if(faltantes < 0){
			faltantes = 0;
		}
		
		if (accion.equals("modificar") && did.equals(dormi.getDormitorioId())){
%>
		<tr>
			<td><input type="text" class="text" name="id" size="1" value='<%=dormi.getDormitorioId()%>'></td>
			<td></td>
			<td><input type="text" class="text" name="nombre" size="35" value = '<%=dormi.getNombre()%>'></td>
			<td>
				<select name='sexo' style="width:125px">
					<option value = 'M' <%if (dormi.getSexo().equals("M"))out.print("selected");%>>Masculino</option>
					<option value = 'F' <%if (dormi.getSexo().equals("F"))out.print("selected");%>>Femenino</option>
				</select>
			</td>
			<td><input type="text" class="text" name="nomina" size="6" maxlength="7" value='<%=dormi.getPreceptor()%>'></td>
			<td><input type='text' name="nP" size="35" value='<%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc,dormi.getPreceptor(),"NOMBRE")%>'>&nbsp;
			<i class="icon-search" title="Buscar preceptor" type="button" name="boton" value="Buscar" class="btn btn-primary" onclick='buscar()'></i></td>
			<td colspan="7"><a href="javascript:cambios()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
			<input type='button' onclick='document.forma.submit()' class="btn btn-primary" value ='Cancelar'></td>
		</tr>
<%
		}else{
%>
		<tr class="tr2">
			<td><%=dormi.getDormitorioId()%></td>
			<td align='center'>
				<a class="fas fa-edit" title="Modificar" href="javascript:modificar('<%=dormi.getDormitorioId()%>')"></a>&nbsp;
<% 			
			if ( aca.internado.CuartoUtil.tieneCuartos(conEnoc, dormi.getDormitorioId()) == 0){%>		
				<a class="fas fa-trash-alt" title="Eliminar" href="javascript:eliminar('<%=dormi.getDormitorioId()%>')"></a>
<% 			}%>				
			</td>
			<td><a href="javascript:cambioSession('<%=dormi.getPreceptor()%>','<%=dormi.getDormitorioId()%>','Admin')"><%=dormi.getNombre()%></a></td>
			<td><%if (dormi.getSexo().equals("M"))out.print("Masculino"); else out.print("Femenino");%></td>
			<td><%=dormi.getPreceptor()%></td>
			<td><%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc,dormi.getPreceptor(),"NOMBRE")%></td>
			<td class="right"><%=cupo%></td>
			<td class="right"><%=registrados%></td>
			<td class="right"><%=inscritos%></td>
			<td class="right"><%=asignados%></td>
			<td class="right"><%=faltantes%></td>
			<td class="right"><%=otros%></td>
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
					<option value = 'M'>Masculino</option>
					<option value = 'F'>Femenino</option>
				</select>
			</td>
			<td><input type="text" class="text" name="nomina" size="6" maxlength="7"></td>
			<td><input type='text' name="nP" size="35">&nbsp;&nbsp;<i class="icon-search" title="Buscar preceptor" type="button" name="boton" value="Buscar" onclick='buscar()' class="btn btn-primary"></i></td>
			<td colspan="7"><a href="javascript:guardar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
			<input type='button' onclick='document.forma.submit()' class="btn btn-primary" value ='Cancelar'></td>
		</tr>	
<%	}
%>
	</table>
	</form>
</div>
<script>document.forma.id.focus()</script>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%	if (!redireccionar.equals("X")){%>
	<meta http-equiv="refresh" content="0;url=../../portales/preceptor/personal" />
<%	}%>
<%@ include file= "../../cierra_enoc.jsp" %>