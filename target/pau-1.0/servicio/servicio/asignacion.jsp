<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.ssoc.RequisitoVO"%>
<%@ page import= "aca.ssoc.AsignacionVO"%>
<%@ page import= "aca.ssoc.DocAlumVO"%>

<jsp:useBean id="bDocumentos"  class="aca.ssoc.Documentos" scope="page"/>
<jsp:useBean id="bDocAlumno"  class="aca.ssoc.DocAlumno" scope="page"/>
<jsp:useBean id="bAsignacion"  class="aca.ssoc.Asignacion" scope="page"/>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="planU" scope= "page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="inicio" scope= "page" class="aca.ssoc.Inicio"/>

<%	
	String matricula 	= (String) session.getAttribute("codigoAlumno");

	int folio			= request.getParameter("folio")==null?0:Integer.parseInt(request.getParameter("folio"));
	int accion			= request.getParameter("accion")==null?0:Integer.parseInt(request.getParameter("accion"));
	int documentoId		= request.getParameter("documentoId")==null?0:Integer.parseInt(request.getParameter("documentoId"));
	int asignacionId	= request.getParameter("asignacionId")==null?0:Integer.parseInt(request.getParameter("asignacionId"));
	int numHoras		= request.getParameter("numHoras")==null?0:Integer.parseInt(request.getParameter("numHoras"));
	String id				= request.getParameter("id")==null?"":request.getParameter("id");
	int i				= 0;
	
	String cat			= request.getParameter("cat")==null?"":request.getParameter("cat");
	String fecha		= request.getParameter("fecha")==null?"":request.getParameter("fecha");
	String comentario	= request.getParameter("comentario")==null?"":request.getParameter("comentario");
	String entregado	= request.getParameter("entregado")==null?"N":request.getParameter("entregado");
	String espera		= request.getParameter("espera")==null?"":request.getParameter("espera");		
	String dependencia	= request.getParameter("dependencia")==null?"":request.getParameter("dependencia");	
	String direccion	= request.getParameter("direccion")==null?"":request.getParameter("direccion");	
	String telefono		= request.getParameter("telefono")==null?"":request.getParameter("telefono");	
	String responsable	= request.getParameter("responsable")==null?"":request.getParameter("responsable");	
	String fechaInicio	= request.getParameter("fechaInicio")==null?"":request.getParameter("fechaInicio");	
	String strCambia 	= request.getParameter("cambia")==null?"0":request.getParameter("cambia");
	String mat			= "";
	
	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);
	plan.mapeaRegId(conEnoc,matricula);
	String	sPlan		= request.getParameter("PlanId")==null?plan.getPlanId():request.getParameter("PlanId");	
	ArrayList<Integer> mostrados	= new ArrayList<Integer>();
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");	
%>
<script>
	function cambiaPlan(Plan){
		document.location.href='social?PlanId='+Plan;
	}

	function presDocumento(){
		if(window.event.keyCode==13)
			GuardaDocumento();
		
	}
	
	function presAsignacion(){
		if(window.event.keyCode==13)
			GuardaAsignacion();
		
	}
	
	function GuardaDocumento( ){	
		if(document.formDocumento.fecha.value == ""){
			alert("Escriba la fecha...");
		}else{				
			document.formDocumento.submit();
		}		
	}
	
	function GuardaAsignacion(){		
		alert("antes de submit");
		document.formAsignacion.submit();
		alert("Pase submit");
	}

	function borrar(f,id,Plan){
		if(f=="documentos"){
			if (confirm("¿Esta seguro que desea borrar el documento?")){
				document.location.href='social?accion=3&folio='+id+'&cat='+f+'&PlanId='+Plan;
			}
		}
		if(f=="asignaciones"){
			if (confirm("¿Esta seguro que desea borrar la asignacion?")){
				document.location.href='social?accion=3&id='+id+'&cat='+f+'&PlanId='+Plan;
			}
		}
	}
	
	function modificar(f,id,p){
		if(f=="documentos"){
			document.location.href='social?accion=4&PlanId='+p+'&folio='+id+'&cat='+f
		}
		if(f=="asignaciones"){
			document.location.href='social?accion=4&PlanId='+p+'&folio='+id+'&cat='+f
		}
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
	
	function abrir(pag){
		abrirVentana("servicio",800,600,10,10,"no","yes","yes","no","no",pag,false);
	}
	
	function abrirDocs(pag){
		abrirVentana("documentos",350,350,150,350,"no","yes","yes","no","no",pag,false);
	}
	
	function cambiaDatos(Plan,PlanNew){
		document.location.href='social?cambia=2&PlanId='+Plan+'&PlanNuevo='+PlanNew;
	}
	
</script>
<%
	String avance="";
	boolean tblInicio = false; 
	conEnoc.setAutoCommit(false);
	if (strCambia.equals("2")){		
		String planNuevo = request.getParameter("PlanNuevo");
		
		// Habilita el nuevo plan
		inicio.setCodigoPersonal(matricula);
		inicio.setPlanId(planNuevo);
		if (inicio.existeReg(conEnoc)){
			inicio.mapeaRegId(conEnoc, matricula, planNuevo);
			tblInicio = true;
		}else{
			inicio.setFecha(aca.util.Fecha.getHoy());
			avance = aca.alumno.PlanUtil.getAlumPromCreditos(conEnoc,matricula,planNuevo);
			if (avance.indexOf(".")!= -1) avance = avance.substring(0,avance.indexOf("."));				
			inicio.setPorcentaje(String.valueOf(avance));
			inicio.setSemestre(aca.alumno.PlanUtil.getSem(conEnoc, matricula, planNuevo));
			//System.out.println("Valores:"+ inicio.getCodigoPersonal()+":"+inicio.getPlanId()+":"+inicio.getPorcentaje()+":"+inicio.getSemestre()+":"+inicio.getFecha());
			if (inicio.insertReg(conEnoc)){
				tblInicio = true;
			}
		}
		
		// modifica el plan en DocAlumno
		if (tblInicio){
			if ( bDocAlumno.modificaPlan(conEnoc,matricula, sPlan, planNuevo)){
				conEnoc.commit();
				inicio.setCodigoPersonal(matricula);
				inicio.setPlanId(sPlan);
				if (inicio.existeReg(conEnoc)){
					if (inicio.deleteReg(conEnoc)){
						conEnoc.commit();
						sPlan = planNuevo;
					}
				}
			}else{
				conEnoc.rollback();
			}
		}else{
			conEnoc.rollback();
		}
	}
	conEnoc.setAutoCommit(true);
	
%>
<div class="container-fluid">
	<form name="formDocumento" method='post' action="social?cat=documentos&PlanId=<%=sPlan%>">
	<h2 style="display:inline; line-height: 1px">Servicio social
		<small class="text-muted fs-4">( <%=matricula%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>)</small>
	</h2>	
	<div class="alert alert-info">
		<p style="margin-top:10px">			 
	  		[<%=sPlan%>] [<%= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, aca.plan.PlanUtil.getCarreraId(conEnoc,sPlan)) %>] 
	  		[ Avance: <%= aca.alumno.PlanUtil.getAlumPromCreditos(conEnoc, matricula, sPlan) %> % ]
	  		[ Fecha de Inicio: <%= aca.ssoc.Inicio.getFecha(conEnoc, matricula, sPlan) %> ] 
		</p>	
		<font size='2'><strong>Plan:&nbsp; </strong></font>
		<select name="PlanId" id="PlanId" onChange="javascript:cambiaPlan(this.options[this.selectedIndex].value);">
<%	
	ArrayList<aca.alumno.AlumPlan> lisPlan = planU.getLista(conEnoc, matricula, "ORDER BY 4");
	for ( i=0; i<lisPlan.size(); i++){
		plan = (aca.alumno.AlumPlan)lisPlan.get(i);
		if(sPlan.equals(plan.getPlanId()))
			out.print("<option value='"+plan.getPlanId()+"'Selected>"+plan.getPlanId()+"</option>");
		else
			out.print("<option value='"+plan.getPlanId()+"'>"+plan.getPlanId()+"</option>");
	}	
	%>	
		 </select>	
	<%	
	if ( (strCambia.equals("0")||strCambia.equals("2")) && aca.ssoc.DocAlumno.getTieneDoctos(conEnoc,matricula,sPlan) ){
	%>	   
		  &nbsp; &nbsp;<a href="social?cambia=1&PlanId=<%=sPlan%>"> Mover datos </a> &nbsp; &nbsp;
	<%
	}
	if (strCambia.equals("1")){
	%>
		&nbsp; &nbsp; Mover a: &nbsp; &nbsp;
		<select name="PlanNuevo" id="PlanNuevo" onchange="javascript:cambiaDatos('<%=sPlan %>',this.options[this.selectedIndex].value)">
<%			
		for ( i=0; i<lisPlan.size(); i++){
			plan = (aca.alumno.AlumPlan)lisPlan.get(i);
			if(sPlan.equals(plan.getPlanId()))
				out.print("<option value='"+plan.getPlanId()+"'Selected>"+plan.getPlanId()+"</option>");
			else
				out.print("<option value='"+plan.getPlanId()+"'>"+plan.getPlanId()+"</option>");
		}
	%>
		</select>
	<%
	}
	%>
		<a class="btn btn-primary" href='social?accion=1&cat=documentos&PlanId=<%=sPlan%>'><b>Agregar Documento</b></a>
		<font><a class="btn btn-primary" href="javascript:abrirDocs('documentos?PlanId=<%=sPlan%>')"><b> Documentos</b></a></font> 
	
<%	if((accion==1||accion==4)&&cat.equals("documentos")){	%>
			&nbsp; | <a class="btn btn-primary" class="btn btn-danger" href='social?PlanId=<%=sPlan%>'><b>Cancelar</b></a>
<%	}%>
	&nbsp;&nbsp;
	<a href="javascript:abrir('reglamento')"><b>(Reglamento del Servicio Social)</b></a>
	&nbsp;		
	<a href="javascript:abrir('requisitos')"><b>(Requisitos para el Servicio Social)</b></a>
	</div>
	<%
	if (accion==6){		
		inicio.mapeaRegId(conEnoc,matricula, sPlan);
		inicio.setFecha(aca.ssoc.Inicio.getMinFecha(conEnoc, matricula, sPlan));
		if (inicio.updateReg(conEnoc)){
		}
	}
	%>
	<a class="btn btn-primary" href="social?PlanId=<%= sPlan %>&accion=6"> &nbsp; <b>Sincronizar</b></a>
	  
<%	
	if(( accion==1 || accion==4) && cat.equals("documentos")){
		
		aca.ssoc.DocAlumVO doc = null;
		if (aca.ssoc.DocAlumno.existeReg(conEnoc, matricula, String.valueOf(folio) )){
			doc = bDocAlumno.getDocumento(conEnoc, matricula, folio);
		}else{
			doc = new aca.ssoc.DocAlumVO();
		}	
%>
	
	<div class="row">
		<div class="span3">
			<label for="entregado">Entregado:</label>
			<input type='checkbox' name="entregado" value="S" <%if(doc.getEntregado().equals("S")) out.print("checked");%> />
			<br><br>
			<label for="documentoId">Documento:</label>	
			<select name="documentoId">
<%			ArrayList<aca.ssoc.RequisitoVO> documentos = bDocumentos.getDocumentos(conEnoc);
			RequisitoVO documento = null;
			for(i=0;i<documentos.size();i++){
						documento = (RequisitoVO)documentos.get(i);
%>				<option value='<%=documento.getId()%>' <%if(doc.getDocumentoId()==documento.getId())out.print("Selected");%>><%=documento.getNombre()%></option>
<%			}	%>
		  	</select>
			<br><br>
			<label for="asignacionId">Asignacion:</label>			
			<select name="asignacionId">
				<option value='0'></option>
<%				ArrayList<aca.ssoc.AsignacionVO> asignaciones = bAsignacion.getAsignaciones(conEnoc,matricula);
				AsignacionVO asignacion=null;
				for(i=0;i<asignaciones.size();i++){
					asignacion=(AsignacionVO)asignaciones.get(i);
%>				<option value='<%=asignacion.getAsignacionId()%>' <%if(doc.getAsignacionId()==Integer.parseInt(asignacion.getAsignacionId()))out.print("Selected");%>><%=asignacion.getDependencia()%></option>
<%				}	%>
		  	</select>
			<br><br>
			<label for="fecha"><spring:message code="aca.Fecha"/>: </label>					
			<input name="fecha" type="text" class="text" value="<%=doc.getFecha()%>" size='10'/>(DD/MM/AAAA)
			<br><br>
			<label for="numHoras">Horas:</label>
			<input name="numHoras" type="text" class="text" size='5' value='<%=doc.getNumHoras()%>' />
			<br><br>
			<label for="comentario">Comentario:</label>					
			<input name="comentario" type="text" class="text" size="40" value="<%if(doc.getComentario()==null)out.print("");else out.print(doc.getComentario());%>" />
		  	<input type='hidden' name='accion' value='2'/>		  	  	
		  	<input type='hidden' name='folio' value='<%=folio%>'/>
		</div>
	</div>
	<br>
	<div class="alert alert-info">
		<a href='javascript:GuardaDocumento()' class="btn btn-primary"><spring:message code="aca.Guardar"/>:</a>
	</div>	

	</form>
	<script type='javascript'>document.forma.fecha.focus();</script>

<%	}

	// Guardar el documento
	if(accion==2&&cat.equals("documentos")){
		
		java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		DocAlumVO documento = new DocAlumVO();
		documento.setCodigoPersonal(matricula);
		documento.setFolio(folio);
		documento.setPlanId(sPlan);
		documento.setDocumentoId(documentoId);
		documento.setAsignacionId(asignacionId);			
		documento.setFecha(fecha);
		documento.setNumHoras(numHoras);
		documento.setComentario(comentario);
		documento.setEntregado(entregado);
		// Modificar el registro
		if (aca.ssoc.DocAlumno.existeReg(conEnoc, matricula, String.valueOf(folio))){
			bDocAlumno.modificaDocumento(conEnoc,documento);		
		// Insertar el registro 	
		}else{			
			bDocAlumno.guardaDocumento(conEnoc,documento);
		}
			
	}
	
	// Borrar un documento
	conEnoc.setAutoCommit(false);
	if(accion==3&&cat.equals("documentos")){
		bDocAlumno.eliminaDocumento(conEnoc,matricula,folio);
		if (bDocAlumno.numDocAlum(conEnoc,matricula,sPlan)==0){
			inicio.setCodigoPersonal(matricula);
			inicio.setPlanId(sPlan);
			if (inicio.existeReg(conEnoc)){
				if (inicio.deleteReg(conEnoc)){
					conEnoc.commit();
				}else{
					conEnoc.rollback();
				}
			}
		}else{
			conEnoc.commit();
		}
	}
	conEnoc.setAutoCommit(true);
%>
	
	<table style="margin-top:5px; margin-bottom:0; "class="table table-fullcondensed">
		<tr>
			<th width="6%" align='center'><spring:message code="aca.Operacion"/></th>
			<th width="3%" align='center'><spring:message code="aca.Numero"/></th>
			<th width="20%" align='center'><spring:message code="aca.Documento"/></th>
			<th width="10%" align='center'><spring:message code="aca.Fecha"/></th>
			<th width="10%" align='center'>Horas</th>
			<th width="20%" align='center'><spring:message code="aca.Comentario"/></th>
		</tr>
	<%	
		ArrayList<aca.ssoc.DocAlumVO> lisDocumentos	= bDocAlumno.getDocPlan(conEnoc, matricula, sPlan);
		int horas = 0;
		for( i=0; i < lisDocumentos.size(); i++){
			aca.ssoc.DocAlumVO doc = new aca.ssoc.DocAlumVO();
			doc =(aca.ssoc.DocAlumVO)lisDocumentos.get(i);
	%>
		
		<tr valign='top'>
			<td align='center'>
			  <img onclick="modificar('documentos',<%=doc.getFolio()%>,'<%=sPlan%>');" class="button" alt='Modificar'  src='../../imagenes/editar.gif'></img>
			  <img onclick="borrar('documentos',<%=doc.getFolio()%>,'<%=sPlan%>');" class="button" alt='Eliminar'  src='../../imagenes/no.png'></img>
			</td>
			<td align='center'><%=doc.getDocumentoId()%></td>
			<td><%if(doc.getEntregado().equals("N"))out.print("<font color=red>");%> <%=bDocAlumno.getNombreDocumento(conEnoc,doc.getDocumentoId())%></td>
			<td align='center'><%=doc.getFecha()%></td>
			<td align='center'><%if(doc.getNumHoras()==0)out.print("");else{out.print(doc.getNumHoras());horas+=doc.getNumHoras();}%></td>
			<td><%if(doc.getComentario()==null)out.print("&nbsp;");else out.print(doc.getComentario());%></td>
		</tr>
	<%		Integer docu = new Integer(doc.getAsignacionId());
			if(doc.getAsignacionId()!=0&&!mostrados.contains(docu)){
				AsignacionVO asignacion=bAsignacion.getAsignacion(conEnoc,matricula,doc.getAsignacionId());
				mostrados.add(docu);
	%>
			<tr>
				<td><td><td colspan='5'><b>Dependencia: </b><%=asignacion.getDependencia()%></td>
			</tr>
			<tr>
				<td><td><td colspan='5'><b>Dirección: </b><%=asignacion.getDireccion()%></td>
			</tr>
			<tr>
				<td><td><td colspan='5'><b><spring:message code="aca.Tel"/>éfono: </b><%=asignacion.getTelefono()%></td>
			</tr>
			<tr>
				<td><td><td colspan='5'><b>Responsable: </b><%=asignacion.getResponsable()%></td>
			</tr>
	<%		}%>
	<%	}
		if(lisDocumentos.size()==0){
	%>
			<tr>
				<td colspan='6'>Sin documentos...</td>
			</tr>	
	<%	}%>
			<tr>
				<td colspan='4' align='right'><b>Horas Totales: </b></td>
				<td align='center'><%=horas%></td>
				<td align='center'>&nbsp;</td>
			</tr>			
			
	<%	
		String faltante = "";
		ArrayList<String> lisFaltantes = bDocAlumno.getDocumentosFaltantes(conEnoc,matricula,sPlan);
		if(lisFaltantes.size()> 0){
	%>
			<tr>
				<td colspan='6'><br><b>Te faltan: </b></td>
			</tr>	
	
	<%	}
		for(i=0;i<lisFaltantes.size();i++){
			faltante = (String)lisFaltantes.get(i);
			String[] datosServ = faltante.split("&&");
	%>		
		<tr valign='top'>
		  <td>&nbsp;</td>
		  <td colspan="6"><%=bDocAlumno.getNombreDocumento(conEnoc,Integer.parseInt(datosServ[0]))%></td>		
		</tr>
	<%}
	%>
		<tr>
			<td colspan="6"><b>Horas Faltantes: </b><%if(horas>500)out.print("0");else out.print(500-horas);%></td>
		</tr>		
	</table>
<!----------------------- ASIGNACIONES -------------------------------->
	<form name="formAsignacion" action="social?cat=asignaciones" method="post">
	<div name="asignaciones"></div>
	<br>
	<a class="btn btn-primary" href='social?accion=1&cat=asignaciones&PlanId='<%=sPlan%>'><b>Agregar asignacion</b></a>
	<table style="margin-top:5px; width='90%' "class="table table-fullcondensed">
	<tr>
		<td>
		<%if((accion==1||accion==4)&&cat.equals("asignaciones")){%>| <a class="btn btn-danger" href='social'><b>Cancelar</b></a></td><%}%>
		</td>
	</tr>
<%
		if(accion==1&&cat.equals("asignaciones")){
%>
		<tr><td colspan='6'><table onkeypress='javascript:presAsignacion()'>
			<tr>
				<td>Dependencia: </td>
				<td><input name="dependencia" type="text" class="text" size='30'/></td>
			</tr>
			<tr>
				<td>Direccion: </td>
				<td><input name="direccion" type="text" class="text" size='30'/></td>
			</tr>
			<tr>
				<td><spring:message code="aca.Tel"/>efono: </td>
				<td><input name="telefono" type="text" class="text" size='15'/></td>
			</tr>
			<tr>
				<td>Responsable: </td>
				<td><input name="responsable" type="text" class="text" size='30'/></td>
			</tr>
			<tr>
				<td>Fecha Inicio: </td>
				<td><input name="fechaInicio" type="text" class="text" size='15'/>
				(DD/MM/AAAA)**
				 <tr>
				   <td></br><a href='javascript:GuardaAsignacion()' class="btn btn-primary"><spring:message code="aca.Guardar"/>:</a></td>
				 </tr>
				<input type='hidden' name='accion' value='2'/>
				</td>
			</tr>
		</table></td></tr>
		<script type='javascript'>
		document.location.href='#asignaciones';
		document.forma[1].dependencia.focus();
		</script>
<%
		}
		if(accion==2&&cat.equals("asignaciones")){
			java.text.DateFormat df = java.text.DateFormat.getDateInstance();
			AsignacionVO asignacion = new AsignacionVO();
			asignacion.setCodigoPersonal(matricula);
			asignacion.setAsignacionId(id);
			asignacion.setDependencia(dependencia);
			asignacion.setDireccion(direccion);
			asignacion.setTelefono(telefono);
			asignacion.setResponsable(responsable);
			asignacion.setFechaInicio(fechaInicio);
			bAsignacion.guardaAsignacion(conEnoc,asignacion);
		}
		if(accion==3&&cat.equals("asignaciones")){
			mat=bAsignacion.eliminaAsignacion(conEnoc,matricula,id);
			if(!mat.equals("")){
				out.print("<tr><td colspan='6'><font color=red><b>"+
				"NO se pudo eliminar la asignación, el alumno "+mat+" la tiene registrada."+
				"</b></font><script>document.location.href='#asignaciones';</script></tr></td>");
			}
		}
		if(accion==4&&cat.equals("asignaciones")){
		AsignacionVO asignacion=bAsignacion.getAsignacion(conEnoc,matricula,folio);
%>
		<tr><td colspan='6'><table onkeypress='pres(1)'>
			<tr>
				<td>Dependencia: </td>
				<td><input name="dependencia" value='<%=asignacion.getDependencia()%>' type="text" class="text" size='30'/></td>
			</tr>
			<tr>
				<td>Direccion: </td>
				<td><input name="direccion" value='<%=asignacion.getDireccion()%>' type="text" class="text" size='30'/></td>
			</tr>
			<tr>
				<td><spring:message code="aca.Tel"/>efono: </td>
				<td><input name="telefono" value='<%=asignacion.getTelefono()%>' type="text" class="text" size='15'/></td>
			</tr>
				<td>Responsable: </td>
				<td><input name="responsable" value='<%=asignacion.getResponsable()%>' type="text" class="text" size='30'/></td>
			</tr>
			<tr>
				<td>Fecha Inicio: </td>
				<td>
				  <input name="fechaInicio" value='<%=asignacion.getFechaInicio()%>' type="text" class="text" size='15'/> (DD/MM/AAAA) *
				  <a href='javascript:GuardaAsignacion()'><img alt='Guardar'  src='../../imagenes/filesave.png'></img></a>
				  <input type='hidden' name='id' value='<%=id%>'/>
				  <input type='hidden' name='accion' value='5'/>
				</td>
			</tr>
		</table>
	</td>
</tr>
		<script type='javascript'>
			document.location.href='#asignaciones';
			document.forma[1].dependencia.focus();
		</script>

<%		}
		if(accion==5&&cat.equals("asignaciones")){
			java.text.DateFormat df = java.text.DateFormat.getDateInstance();
			AsignacionVO asignacion = new AsignacionVO();
			asignacion.setCodigoPersonal(matricula);
			asignacion.setAsignacionId(id);
			asignacion.setDependencia(dependencia);
			asignacion.setDireccion(direccion);
			asignacion.setTelefono(telefono);
			asignacion.setResponsable(responsable);
			asignacion.setFechaInicio(fechaInicio);
			bAsignacion.modificaAsignacion(conEnoc,asignacion);
		}
%>
	<tr>
		<th width="6%" align='center'><spring:message code="aca.Operacion"/></th>
		<th width="3%" align='center'><spring:message code="aca.Numero"/></th>
		<th width="20%" align='center'>Dependencia</th>
		<th width="20%" align='center'><spring:message code='aca.Direccion'/></th>
		<th width="15%" align='center'><spring:message code="aca.Tel"/>èfono</th>
		<th width="20%" align='center'>Responsable</th>
	</tr>
<%	ArrayList asignaciones = new ArrayList();
	AsignacionVO asignacion = null;
	asignaciones = bAsignacion.getAsignaciones(conEnoc,matricula);
	for(i=0;i<asignaciones.size();i++){
		asignacion = new AsignacionVO();
		asignacion =(AsignacionVO)asignaciones.get(i);
%>	
	<tr valign='top'>
		<td align='center'>
		  <a class="fas fa-edit" href="javascript:modificar('asignaciones',<%=asignacion.getAsignacionId()%>,'<%=sPlan%>');"></a>
		  <a class="fas fa-trash-alt" href="javascript:borrar('asignaciones',<%=asignacion.getAsignacionId()%>,'<%=sPlan%>');"></a>
		</td>
		<td align='center'><%=asignacion.getAsignacionId()%></td>
		<td><%=asignacion.getDependencia()%></td>
		<td><%=asignacion.getDireccion()%></td>
		<td><%=asignacion.getTelefono()%></td>
		<td><%=asignacion.getResponsable()%></td>
	</tr>
<%	}
	if(asignaciones.size() == 0){
%>
		<tr>
			<td colspan='6'>Sin asignaciones...</td>
		</tr>	
<%	}%>
</table>
</form>
</div>
<%if(espera.equals("1")){%>
	<META http-equiv="Refresh" content="1;url=social">
<%}%>
<%@ include file= "../../cierra_enoc.jsp" %>