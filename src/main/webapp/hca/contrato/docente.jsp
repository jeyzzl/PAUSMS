<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.catalogo.spring.CatNivel"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.hca.spring.HcaMaestro"%>
<%@page import="aca.hca.spring.HcaTipo"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.hca.spring.HcaActividad"%>
<%@page import="aca.hca.spring.HcaMaestroActividad"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="java.text.*"%>

<%		
	String codigoEmpleado 		= (String) session.getAttribute("codigoEmpleado");	 
	String cargaId				= (String) request.getAttribute("cargaId");	 
	String muestraCargas		= (String) request.getAttribute("muestraCargas");
	
	String accion				= (String) request.getAttribute("accion");		
	String actividad			= (String) request.getAttribute("actividad");		
	String horas				= (String) request.getAttribute("horas");
	
	String modificable 			= (String) request.getAttribute("modificable");	
	String semanas				= (String) request.getAttribute("semanas");	

	String nombreMaestro		= (String) request.getAttribute("nombreMaestro");	
	
	double totalSemestral		= 0;
	double totalSemestralFinal	= 0;
	double promSemanal			= 0;	
	
	DecimalFormat getformato	= new DecimalFormat("#####0.00;(#####0.00)");
	
	List<Carga> lisCargasActivas		= (List<Carga>) request.getAttribute("lisCargasActivas");
	List<Carga> lisCargasInactivas		= (List<Carga>) request.getAttribute("lisCargasInactivas");
	List<CargaAcademica> lisCursos 		= (List<CargaAcademica>) request.getAttribute("lisCursos");	
	List<HcaActividad> lisActividad 	= (List<HcaActividad>) request.getAttribute("lisActividad");	
	List<HcaMaestroActividad> lisMA 	= (List<HcaMaestroActividad>) request.getAttribute("lisMA");	
	
	HashMap<String,String> mapaValor 				= (HashMap<String,String>) request.getAttribute("mapaValor");	
	HashMap<String,Integer> mapaFs 					= (HashMap<String,Integer>) request.getAttribute("mapaFs");	
	HashMap<String,String> mapaCarreraNivel 		= (HashMap<String,String>) request.getAttribute("mapaCarreraNivel");	
	HashMap<String,CatCarrera> mapaCarrera 			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarrera");	
	HashMap<String,CatModalidad> mapaModalidad 		= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidad");	
	HashMap<String,Integer> mapaCursoCargaSize 		= (HashMap<String,Integer>) request.getAttribute("mapaCursoCargaSize");	
	HashMap<String,HcaTipo> mapaTipos 				= (HashMap<String,HcaTipo>) request.getAttribute("mapaTipos");	
	HashMap<String, HcaActividad> mapaActividades 	= (HashMap<String,HcaActividad>) request.getAttribute("mapaActividades");
	HashMap<String, CatNivel> mapNivel 				= (HashMap<String, CatNivel>) request.getAttribute("mapNivel");
	
	boolean existeLaCarga 				= false;
	boolean esSupervisor 				= (boolean) request.getAttribute("esSupervisor");	
	boolean esAdministrador 			= (boolean) request.getAttribute("esAdministrador");	
	boolean insertoMaestroActividad 	= (boolean) request.getAttribute("insertoMaestroActividad");	
	boolean updateMaestroActividad 		= (boolean) request.getAttribute("updateMaestroActividad");	
	boolean deleteMaestroActividad 		= (boolean) request.getAttribute("deleteMaestroActividad");	
	boolean updateMaestroEstado 		= (boolean) request.getAttribute("updateMaestroEstado");	
	boolean insertMaestroEstado 		= (boolean) request.getAttribute("insertMaestroEstado");	
	boolean cerrado 					= (boolean) request.getAttribute("cerrado");	

	HcaMaestro hcaMaestro						= (HcaMaestro) request.getAttribute("hcaMaestro");	
	HcaMaestroActividad hcaMaestroActividad		= (HcaMaestroActividad) request.getAttribute("hcaMaestroActividad");	
	HcaActividad hcaActividad					= (HcaActividad) request.getAttribute("hcaActividad");	
	Acceso acceso								= (Acceso) request.getAttribute("acceso");
	
	int error = (int) request.getAttribute("error");	
%>
<head>
<script type="text/javascript">			
	function buscar(pag){
		abrirVentana("bem",400,400,200,500,"no","yes","yes","no","no",pag,false);
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
		
	function guardaDocencia(){
		document.forma1.action += "?Accion=1";
		document.forma1.submit();
	}
		
	function revisaActividad(){
		if(document.forma2.semanas.value != ""){        // Si es modificar ligalo a modificar, si no ligalo a grabar
			document.forma2.action += "&Accion=<%=accion.equals("3")?"4":"2"%><%=accion.equals("3")?("&actividad="+actividad):"" %>&cambiaHora=<%=accion.equals("3")?"s":"n"%>";
			return true;
		}else{
			alert("Las semanas son requeridas para poder guardar");
		}
		return false;
	}
		
	function modificaActividad(actividad){
		document.forma3.action += "?Accion=3&CargaId=<%=cargaId%>&actividad="+actividad;
		document.forma3.submit();
	}
	
	function eliminaActividad(actividad){
		if(confirm("¿Está seguro que desea borrar la actividad de este maestro?")){
			document.forma3.action += "?Accion=5&CargaId=<%=cargaId %>&actividad="+actividad;
			document.forma3.submit();
		}
	}
		
	function cerrarCarga(tSemanal, tSemestral, Reg, FacultadId){
		if(confirm("¿Está seguro que desea cerrar la carga?")){
			document.location.href = "docente?CargaId=<%=cargaId %>&Accion=6&tSemanal="+tSemanal+"&tSemestral="+tSemestral+"&Reg="+Reg+"&FacultadId="+FacultadId;
		}
	}
		
	function abrirCarga(Reg, FacultadId){
		document.location.href = "docente?CargaId=<%=cargaId %>&Accion=7&Reg="+Reg+"&FacultadId="+FacultadId;
	}
			
	function esModificable(){		
		document.forma2.submit();
	}		
</script>
</head>
<div class="container-fluid">
	<h2>Contrato Docente <small class="text-muted fs-4">( <%=codigoEmpleado %> - <%=nombreMaestro%>)</small></h2>
	
	<form id="forma1" name="forma1" action="docente" method="post">	
	<div class="alert alert-info d-flex text-align-center">		
		<a class="btn btn-primary" href="javascript:buscar('buscar')"><b>Empleados</b></a>
		&nbsp;&nbsp;		
		<select name="MuestraCargas" class="form-select" onchange="document.forma1.submit();" style="width:150px;">
			<option value="N" <%=muestraCargas.equals("N")?"selected":""%>>Con materias</option>
			<option value="S" <%=muestraCargas.equals("S")?"selected":""%>>Todas</option>
		</select>
		&nbsp;&nbsp;
		<select id="CargaId" name="CargaId" onchange="document.forma1.submit();" class="form-select" style="width:350px;">
			<optgroup label="Cargas en las que TIENE materias">
<%		for(Carga cargaActiva : lisCargasActivas){ %>
				<option value="<%=cargaActiva.getCargaId()%>" <%=cargaActiva.getCargaId().equals(cargaId)?" Selected":"" %>><%=cargaActiva.getCargaId()%>-<%=cargaActiva.getNombreCarga() %></option>
<%		} %>
			</optgroup>
<%
		if(muestraCargas.equals("S")){
%>
			<optgroup label="Cargas en las que NO TIENE materias">
<%
			for(Carga cargaInactiva : lisCargasInactivas){				
				if(cargaInactiva.getCargaId().equals(cargaId))
					existeLaCarga = true;
%>
			<option value="<%=cargaInactiva.getCargaId() %>"<%=cargaInactiva.getCargaId().equals(cargaId)?" Selected":"" %>><%=cargaInactiva.getCargaId()%>-<%=cargaInactiva.getNombreCarga() %></option>
<%			} %>
			</optgroup>
<%
		}
%>
		</select>				
	</div>
<%	String reg1 = "-1";
	if(request.getParameter("Reg")!=null&&request.getParameter("Reg").equals("1")){ reg1 = "1"; %>
		<table><tr><td></td></tr></table>
<%	}%>

<% 
	//System.out.println("Datos:"+hcaMaestro.getCarreraId()+":"+acceso.getAccesos());
	if((acceso.getAccesos().indexOf(hcaMaestro.getCarreraId()) != -1 && !hcaMaestro.getCarreraId().equals("")) || Boolean.parseBoolean(session.getAttribute("admin")+"") || esSupervisor){	
		if(accion.equals("1")){	// Guardar Docencia
			if(error > 0){ %>	
		<div class="alert">
			<font size="3" color="red">ERROR!!!!</font> Al guardar <%=error %> materia(s). Int&eacute;ntelo de nuevo
		</div>
<%			}else{%>
		<div class="alert">
			<font size="3" color="blue">Docencia se guard&oacute; correctamente!!!</font>
		</div>
<%			}
		}else if(accion.equals("2")){	// Guarda Actividad
			if(insertoMaestroActividad){
%>
		<div class="alert">
			<font size="3" color="blue">La Actividad se guard&oacute; correctamente!!!</font>	
		</div>
<%
			}else{
%>
		<div class="alert">
			<font size="3" color="red">ERROR!!!!</font> Al guardar la actividad. Int&eacute;ntelo de nuevo</td>
		</div>
<%
			}
		}else if(accion.equals("4")){	// Modificar la Actividad
			if(updateMaestroActividad){
%>
		<div class="alert">
			<font size="3" color="blue">La Actividad se modific&oacute; correctamente!!!</font></td>
		</div>
<%
			}else{
%>
		<div class="alert">
			<font size="3" color="red">ERROR!!!!</font> Al modificar la actividad. Int&eacute;ntelo de nuevo
		</div>
<%
			}
		}else if(accion.equals("5")){
			if(deleteMaestroActividad){
%>
		<div class="alert">
			<font size="3" color="blue">La Actividad se borr&oacute; correctamente!!!</font>
		</div>
<%
			}else{
%>
		<div class="alert">
			<font size="3" color="red">ERROR!!!!</font> Al borrar la actividad. Int&eacute;ntelo de nuevo
		</div>
<%
			}
		}else if(accion.equals("6")){
				if(updateMaestroEstado){
%>
		<div class="alert">
			<font size="3" color="blue">La Carga Docente fu&eacute; cerrada correctamente!!!</font>
		</div>
<%
				}else{
%>
		<div class="alert">
			<font size="3" color="red">ERROR!!!!</font> Al cerrar la Carga Docente. Int&eacute;ntelo de nuevo
		</div>
<%
				}
				if(insertMaestroEstado){
%>
		<div class="alert">
			<font size="3" color="blue">La Carga Docente fu&eacute; cerrada correctamente!!!</font>
		</div>
<%
				}else{
%>
		<div class="alert">
			<font size="3" color="red">ERROR!!!!</font> Al cerrar la Carga Docente. Int&eacute;ntelo de nuevo
		</div>
<%
				}
		}else if(accion.equals("7")){
			if(updateMaestroEstado){
%>
		<div class="alert">
			<font size="3" color="blue">La Carga Docente fu&eacute; abierta correctamente!!!</font>
		</div>
<%
			}else{
%>
		<div class="alert">
			<font size="3" color="red">ERROR!!!!</font> Al abrir la Carga Docente. Int&eacute;ntelo de nuevo
		</div>
<%
			}
		}		
%>		
	<table class="table table-sm table-bordered">
		<tr>
			<th colspan="11" style="text-align:center">
				<h4 Style="display:inline;">Docencia&nbsp;
<%		if(!cerrado){ %>
				<a href="javascript:guardaDocencia()" class="btn btn-primary btn-sm">Grabar</i></a> 
<%		}%>
				</h4>
				<h4 Style="display:inline; margin-left:10px;">
				<a href="rango">Tabla de Valores</a> 
				</h4>
			</th>
		</tr>
		<tr>
			<th align="center" width="10%"><spring:message code="aca.Plan"/></th>
			<th align="center" width="35%"><spring:message code="aca.Nombre"/> de la materia</th>
			<th align="center" width="15%">Nivel</th>
			<th align="center" width="7%" class="ayuda <%=idJsp %> 002">¿Vale?</th>
			<th align="center" width="7%"><spring:message code="aca.Modalidad"/></th>
			<th align="center" width="7%">N° Alum</th>
			<th align="center" width="5%">Valor</th>
			<th align="center" width="5%"class="ayuda <%=idJsp %> 004">FS</th>
			<th align="center" width="7%">Semanas</th>
			<th align="center" width="8%">Tot.Semana</th>
			<th align="center" width="8%">Tot.Carga</th>
		</tr>
<%  
		int contador = 0;
		for(int i = 0; i < lisCursos.size(); i++){
			CargaAcademica cargaAcademica = lisCursos.get(i);
			
			// La tabla de rabgo de valores está capturada para las modalidades 1:presencial, 4:Nocturna y 5:distancia. Por lo tanto son las unicas modalidades validas 
			String modalidadMateria = "0";
			if (!cargaAcademica.getModalidadId().equals("1")&&!cargaAcademica.getModalidadId().equals("4")) {
				modalidadMateria = "5";
			}else{
				modalidadMateria = cargaAcademica.getModalidadId();
			}
			
			String valor = "0";
			if(mapaValor.containsKey(cargaAcademica.getCarreraId()+modalidadMateria+cargaAcademica.getNumAlum())){
				valor = mapaValor.get(cargaAcademica.getCarreraId()+modalidadMateria+cargaAcademica.getNumAlum());
			}
			
			int fs = 0;
			if(mapaFs.containsKey(cargaAcademica.getCursoId())){
				fs = mapaFs.get(cargaAcademica.getCursoId());
			}
			
			if(cargaAcademica.getValeucas().equals("S")){
				totalSemestral += ((Double.valueOf(valor) * fs) * Double.valueOf(cargaAcademica.getSemanas()));
				if((Double.valueOf(valor) * fs) > 0){
					contador++;
				}
			}
			String nivel = "-";
			String niv 	 = "-";
			
			if(mapaCarreraNivel.containsKey(cargaAcademica.getCarreraId())){
				nivel = mapaCarreraNivel.get(cargaAcademica.getCarreraId());
			}
			
			if(mapNivel.containsKey(nivel)){
				nivel	= mapNivel.get(nivel).getNombreNivel();				
			}	
			
			String nombreCorto = "";
			if(mapaCarrera.containsKey(cargaAcademica.getCarreraId())){
				nombreCorto	= mapaCarrera.get(cargaAcademica.getCarreraId()).getNombreCorto();				
			}	
			
			String nombreModalidad = "";
			if(mapaModalidad.containsKey(cargaAcademica.getModalidadId())){
				nombreModalidad	= mapaModalidad.get(cargaAcademica.getModalidadId()).getNombreModalidad();				
			}	
			
			int sise = 0;
			if(mapaCursoCargaSize.containsKey(cargaAcademica.getCursoCargaId())){
				sise	= mapaCursoCargaSize.get(cargaAcademica.getCursoCargaId());				
			}	
			
%>
		<tr>
			<td><%=cargaAcademica.getPlanId()%></td>
			<td title="<%=cargaAcademica.getCursoCargaId()%>"><%=cargaAcademica.getNombreCurso()%></td>
			<td><%=nivel+ " ("+nombreCorto +")"%></td>
			<td align="center">
				<input type="checkbox" class="checkbox" id="vale<%=cargaAcademica.getCursoCargaId() %>" name="vale<%=cargaAcademica.getCursoCargaId() %>"<%=cargaAcademica.getValeucas().equals("S")?" Checked":"" %><%=cerrado?" Disabled":"" %> />
			</td>
			<td align="center"><%=nombreModalidad%></td>
			<td<%=cargaAcademica.getValeucas().equals("N")?" class='ayuda "+idJsp+" 001'":"" %>>
				<input type="text" style="text-align:right" class="input input-moremini" id="numAlum<%=cargaAcademica.getCursoCargaId() %>" name="numAlum<%=cargaAcademica.getCursoCargaId() %>" value="<%=cargaAcademica.getValeucas().equals("S")?cargaAcademica.getNumAlum(): sise %>" size="3" maxlength="3"<%=cerrado?" Disabled":"" %> /> 
			</td>
			<td align="center"><%=valor %></td>
			<td align="center"><%=fs %></td>
			<td<%=cargaAcademica.getValeucas().equals("N")?" class='ayuda "+idJsp+" 001'":"" %>>
				<input type="text" style="text-align:right" class="input input-moremini" id="semanas<%=cargaAcademica.getCursoCargaId() %>" name="semanas<%=cargaAcademica.getCursoCargaId() %>" value="<%=cargaAcademica.getValeucas().equals("S")?cargaAcademica.getSemanas():"16" %>" size="3" maxlength="3"<%=cerrado?" Disabled":"" %> />
			</td>
			<td align="center"><%=cargaAcademica.getValeucas().equals("S")?(Double.valueOf(valor) * fs):"-" %></td>
			<td align="center"><%=cargaAcademica.getValeucas().equals("S")?((Double.valueOf(valor) * fs) * Double.valueOf(cargaAcademica.getSemanas())):"-" %></td>
		</tr>
<%
		}
%>
		<tr>
			<td colspan="9" align="right"><b><spring:message code="aca.Total"/></b></td>		
			<td align="center"><%=getformato.format(totalSemestral)%></td>
		</tr>
<%
		totalSemestralFinal += totalSemestral;				
		totalSemestral = 0;
%>
	</table>
	</form>
<%
		if(!cerrado){
%>
	<form id="forma2" name="forma2" action="docente?carga=<%=cargaId%><%=muestraCargas!=null?"&muestraCargas=S":""%>" method="post">
	<div class="alert alert-secondary d-flex align-items-center">
		<b>Actividad</b>&nbsp;
<%			if(accion.equals("3")){%>
				<input name="actividad" type="hidden" value="<%=actividad%>">
				<%=hcaActividad.getActividadNombre() %>
<%			}else{ %>
		<select id="actividad" name="actividad" class="form-select" onchange="javascript:esModificable()" style="width:600px;">
			<optgroup label="">
<%
				String tipoId 	= "";
				HcaTipo hcaTipo = new HcaTipo();
				for(HcaActividad hcaActi : lisActividad){					
					if(!tipoId.equals(hcaActi.getTipoId())){
						tipoId = hcaActi.getTipoId();
						if(mapaTipos.containsKey(tipoId)){
							hcaTipo = mapaTipos.get(tipoId);
						}
%>
			</optgroup>
			<optgroup label="<%=hcaTipo.getTipoNombre() %>">
<%					}  %>
				<option value="<%=hcaActi.getActividadId() %>"<%=actividad.equals(hcaActi.getActividadId())?" Selected":"" %>><%=hcaActi.getActividadNombre() %> [<%=hcaActi.getValor() %> horas]</option>
<%				} %>
			</optgroup>
		</select>
<%			}%>
		&nbsp;<b>Semanas:</b>&nbsp;
		<input type="text" class="form-control" id="semanas" name="semanas" value="<%=hcaMaestroActividad.getSemanas() %>" maxlength="2" size="2" style="width:70px;"/>		
<% 			if (modificable.equals("S")){%>			    
		&nbsp;<b>Frecuencia:</b>
		<input type="text" class="form-control" id="horas" name="horas" value="<%=hcaMaestroActividad.getHoras()%>" maxlength="2" size="2" style="width:70px;"/>
<%			} %>
		&nbsp;
		<input type="submit" class="btn btn-primary btn-sm" value="Grabar" onclick="return revisaActividad();"/>
	</div>	
	</form>
<%
		}
%>
	<table style="width:97%" class="table table-condensed" align="center">
	<tr> 
		<th style="text-align:center">Actividades del Docente</th>
	</tr>	
	</table>
	<form id="forma3" name="forma3" action="docente<%=muestraCargas.equals("S")?"?muestraCargas=S" : "" %>" method="post">
	
	<table class="table table-bordered" style="margin: 0 auto;" style="width:70%">
<% 
		String tipoId 			= "";
		double totActividad 	= 0;		
		double promHoras		= 0;		
		
		for(int i = 0; i < lisMA.size(); i++){
			hcaMaestroActividad = (HcaMaestroActividad) lisMA.get(i);
			HcaActividad hcaActi = new HcaActividad();
			HcaTipo hcaTipo = new HcaTipo();
			
			if(mapaActividades.containsKey(hcaMaestroActividad.getActividadId())){
				hcaActi = mapaActividades.get(hcaMaestroActividad.getActividadId());				
			}
			if(!tipoId.equals(hcaActi.getTipoId())){
				tipoId = hcaActi.getTipoId();
				if(mapaTipos.containsKey(tipoId)){
					hcaTipo = mapaTipos.get(tipoId);				
				}
				if(i != 0){
					promHoras = totalSemestral/Double.valueOf(semanas);
%>
			<tr><td colspan="6" style="border-bottom:solid 1px">&nbsp;</td></tr>
			<tr>
				<td colspan="2" align="right">&nbsp;</td>
			  	<td align="center"><b>Semanal</b></td>
			  	<td align="center"><%=getformato.format(promHoras) %></td>
			  	<td align="center"><b><spring:message code="aca.Total"/></b></td>
			  	<td align="center"><%=getformato.format(totalSemestral) %></td>
			</tr>
<%
					totalSemestralFinal += totalSemestral;
					totalSemestral = 0;
				}
				contador = 0;
%>					
			<tr>
				<td colspan="6" class="table-dark"><b><%=hcaTipo.getTipoNombre() %></b></td>
			</tr>
			<tr>
				<th width="10%">&nbsp;</th>
				<th align="text-start" width="50%"><spring:message code="aca.Nombre"/></th>
				<th class="text-center" width="10%">Valor</th>
				<th class="text-center" width="10%"><spring:message code="aca.Frec"/></th>											
				<th class="text-center" width="10%">Semanas</th>						
				<th class="text-end" width="10%">Tot. Hrs.</th>
			</tr>
<%
			}			
			contador++;
			
			totActividad 	= Double.valueOf(hcaActi.getValor()) * Double.valueOf(hcaMaestroActividad.getSemanas()) * Double.valueOf(hcaMaestroActividad.getHoras());			
			// Suma las horas en el semestre
			totalSemestral 	+= totActividad;	
			
%>
					<tr>
						<td align="center">
<%
			if(!cerrado){
%>
							<img src="../../imagenes/edit.gif" title="Modificar" onclick="modificaActividad('<%=hcaActi.getActividadId() %>');" style="cursor: pointer;" />
							<img src="../../imagenes/no.png" title="Borrar" onclick="eliminaActividad('<%=hcaActi.getActividadId() %>');" style="cursor: pointer;" />
<%
			}else{
%>
							&nbsp;
<%
			}
%>
						</td>
						<td><%=hcaActi.getActividadNombre() %></td>
						<td class="text-center"><%=hcaActi.getValor() %></td>
						<td class="text-center"><%=hcaMaestroActividad.getHoras() %></td>
						<td class="text-center"><%=hcaMaestroActividad.getSemanas() %></td>
						<td class="text-end"><%=getformato.format(totActividad) %></td>
					</tr>
<%			
			
		}
		
		promHoras = totalSemestral/Double.valueOf(semanas);
		
%>					
					<tr class="table-secondary">
						<td colspan="1" align="right">&nbsp;</td>
						<td align="center"><b>Semanal</b></td>
						<td class="text-center"><%=getformato.format(promHoras) %></td>
						<td class="center">&nbsp;</td>
						<td class="text-center"><b><spring:message code="aca.Total"/></b></td>
						<td class="text-end"><%=getformato.format(totalSemestral) %></td>
					</tr>
<%
		totalSemestralFinal += totalSemestral;
		promSemanal = totalSemestralFinal / Double.valueOf(semanas);		
%>					
					<tr>
					  <td style="font-size:11pt;"colspan="6" align="center" class="ayuda mensaje Horas= <%=getformato.format(totalSemestralFinal)%>  Semanas= <%=semanas%>">
						<b>Prom. hrs. Semana(Horas / semanas): </b><%=getformato.format(promSemanal) %>&nbsp;&nbsp;&nbsp;&nbsp;
						<b>Total de Horas:</b> <%=getformato.format(totalSemestralFinal) %>
					  </td>	
					</tr>
			</table>
		<div class="alert alert-info">		
<%		if(!cerrado){ %>
			<input type="button" class="btn btn-danger" value="Cerrar Carga Docente" onclick="cerrarCarga('<%=getformato.format(promSemanal) %>', '<%=getformato.format(totalSemestralFinal) %>', '<%=reg1%>', '<%=request.getParameter("FacultadId")%>');" />						
<%
		}else{
			if(esAdministrador){
%>
			<input type="button" class="btn btn-success" value="Abrir Carga Docente" onclick="abrirCarga('<%=reg1%>', '<%=request.getParameter("FacultadId")%>');" />			
<%
			}
		}		
%>
			<td colspan="3" align="center"><input class="btn btn-success" type="button" value="Formato" onclick="location='../reporte/anual'" /></td>
		</div>				
	</form>
<%	}else{ %>
	<div class="alert alert-info">
		<b>No tienes acceso para ver al empleado <%=codigoEmpleado %></b>&nbsp;  <a href="javascript:buscar('buscar')" class="btn btn-success">Buscar Otro Empleado ]</a>		
	</div>	
<%
	}
%>
</div>