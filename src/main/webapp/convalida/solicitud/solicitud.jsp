<%@page import="com.itextpdf.text.Document"%>
<%@ page import= "aca.conva.spring.ConvMateria"%>
<%@ page import= "aca.conva.spring.ConvEvento"%>
<%@ page import= "aca.conva.spring.ConvPeriodo"%>
<%@ page import= "aca.kardex.spring.KrdxCursoAct"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.candado.spring.CandAlumno"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<script>
	function borrar(mat,id){
		if (confirm("¿Esta seguro que desea borrar el registro?")){
			document.frmimportcalif.convalidacionId.value=id
			document.frmimportcalif.Accion.value=1
			document.frmimportcalif.submit();
		}
	}
		
	function nuevo(){
		document.frmimportcalif.Accion.value=2
		document.frmimportcalif.submit();
	}
	
	function nuevoPorCiclo(){
		document.frmimportcalif.Accion.value=8
		document.frmimportcalif.submit();
	}
		
	function grabaCom(convalidacionId){
		document.frmimportcalif.convalidacionId.value=convalidacionId
		document.frmimportcalif.Accion.value=3
		document.frmimportcalif.numeroCom.value=convalidacionId
		document.frmimportcalif.submit();
	}
		
	function cerrar(convalidacionId){
		document.frmimportcalif.convalidacionId.value=convalidacionId
		document.frmimportcalif.Accion.value=4
		document.frmimportcalif.submit();
	}
	
	function borraMateria(convalidacionId,cursoId){
		if (confirm("¿Esta seguro que desea borrar la materia?")){
			document.frmimportcalif.convalidacionId.value=convalidacionId;
			document.frmimportcalif.cursoId.value=cursoId;
			document.frmimportcalif.Accion.value=5;
			document.frmimportcalif.submit();
		}
	}
	
	function guarda(convId,estado){
		document.frmimportcalif.Accion.value = 6;
		document.frmimportcalif.convalidacionId.value = convId;
		document.frmimportcalif.estado.value = estado;
		document.frmimportcalif.submit();
	}
	
	function borrarCandado(mat,id){
		if (confirm("¿Esta seguro que desea remover candado?")){
			
			document.frmimportcalif.Accion.value=7;
			document.frmimportcalif.submit();
		}
	}
	
	function eligeAccion(tipo,planId,convId){
		if(tipo == "M"){
			location.href ="accion?PlanId="+planId+"&convalidacionId="+convId;
		}else{
			location.href ="accionCiclo?PlanId="+planId+"&convalidacionId="+convId;
		}
	}
	
	function borraConvaYMaterias(mat,id){
		if (confirm("¿Esta seguro que desea borrar la convalidacion y todas sus materias ?")){
			document.frmimportcalif.convalidacionId.value=id
			document.frmimportcalif.Accion.value=9;
			document.frmimportcalif.submit();
		}
	}
	
</script>
<%
//variables
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");
	String nombreCarrera	= (String) request.getAttribute("nombreCarrera");
	String carreraId		= (String) request.getAttribute("carreraId");
	String periodoId		= (String) request.getAttribute("periodoId");
	String planId			= (String) request.getAttribute("planId");
	String escribir			= (String) request.getAttribute("escribir");
	String comentario		= (String) request.getAttribute("comentario");
	String plan				= (String) request.getAttribute("plan");
	String nombreModalidad	= (String) request.getAttribute("nombreModalidad");

	if(comentario == null || comentario.equals("null")){
		comentario = "";
	}
	int matAceptadas=0, matRechazadas=0;
	String proceso 			= "";
	boolean esAdmin			= (boolean) request.getAttribute("esAdmin");	
	boolean esConvalidador	= (boolean) request.getAttribute("esConvalidador");
	
	int Accion			= 0;
	
	Acceso acceso				= (Acceso) request.getAttribute("acceso");
	AlumAcademico alumAcademico	= (AlumAcademico) request.getAttribute("alumAcademico");
	ConvPeriodo convPeriodo		= (ConvPeriodo) request.getAttribute("convPeriodo");
	ConvMateria convMateria 	= new ConvMateria();
	ConvEvento convEvento		= new ConvEvento();
	
	List<CandAlumno> lisCandados	= (List <CandAlumno>) request.getAttribute("lisCandados");
	List<ConvMateria> lisMaterias	= new ArrayList<ConvMateria>();
	List<ConvEvento> lisConv		= (List <ConvEvento>) request.getAttribute("lisConv");
	List<KrdxCursoAct> lisActual	= (List <KrdxCursoAct>) request.getAttribute("lisActual");
	
	HashMap<String,String> mapCarreras 					= (HashMap<String,String>) request.getAttribute("mapCarreras");
	HashMap<String,Float> mapaCreditos 					= (HashMap<String,Float>) request.getAttribute("mapaCreditos");
	HashMap<String,List<ConvMateria>> mapaLisMaterias 	= (HashMap<String,List<ConvMateria>>) request.getAttribute("mapaLisMaterias");
	HashMap<String,String> mapaNombreMaterias			= (HashMap<String,String>) request.getAttribute("mapaNombreMaterias");
	HashMap<String,String> mapaTotalMaterias			= (HashMap<String,String>) request.getAttribute("mapaTotalMaterias");
%>

<%@page import="aca.catalogo.CatModalidad"%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
.Estilo1 {font-size: 9px}
.Estilo3 {color: #993300}
-->
</style>
<div class="container-fluid">
<div>
	<h3>Convalidaciones Externas<small class="text-muted fs-6">&nbsp;( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=plan %> - <%=nombreCarrera%> )</small></h3>
	<div class="alert alert-info">
<% 	
	
	if (!periodoId.equals("X")){		
		if(!mapCarreras.containsKey(carreraId)){
			out.print("¡La carrera de este alumno no está habilitada en este periodo para registrar convalidaciones!");
		}else{		
%>	
		<a onclick="nuevoPorCiclo();" class="btn btn-primary">Por ciclo</a>&nbsp;&nbsp;
		<a onclick="nuevo();" class="btn btn-primary">Por materia</a> Periodo : <%=convPeriodo.getPeriodoNombre()%> (<%=convPeriodo.getFechaIni()+" a "+convPeriodo.getFechaFin()%>)
<% 	
		}
	}else{
		out.print("¡No está habilitado el periodo para registrar convalidaciones!");
	}%>	
<% 	if (!codigoAlumno.substring(0,1).equals("9") ){
		if(!alumAcademico.getModalidadId().trim().equals(acceso.getModalidad()) && !acceso.getModalidad().equals("0")){
%>
		<font size="2" color="blue">El alumno es de modalidad <b><%=nombreModalidad%></b> y no puedes solicitarle una convalidaci&oacute;n</font>
<%
		}
	}else{
	    out.print("¡ Busca la matricula de un alumno !");
	}%>
	</div>  
<% if(lisCandados.size()>0){ %>
	<div class="alert alert-info">		
		<table>
			<tr>
				<th>#</th>
				<th>Candado</th>
				<th><spring:message code='aca.Usuario'/></th>
				<th>estado</th>
			</tr>
<% 
		int row=0;
		for (CandAlumno candado : lisCandados){ 
			row++;
%>
			<tr>
				<td><%=row%></td>
				<td>
<% 		if (esAdmin||codigoPersonal.equals("9800015")){%>
					<a href="javascript:borrarCandado('<%=codigoAlumno%>','<%=convEvento.getConvalidacionId()%>')" title="Remover candado."><%= candado.getComentario() %></a>
<% 		}else{
				out.print(candado.getComentario());
		}
%>
				</td>
				<td><%= candado.getUsAlta() %></td>
				<td><%= candado.getEstado() %></td>
			</tr>
<%
		}
%>		
		</table>		
	</div>	
  <!-- Fin de Mostrar candado-->	
<%} %>
	<div align="left"> 
<%	if(!escribir.equals("")){%>
	  <%=escribir%>
<%	}%>
	</div>
</div>
	<div class="alert alert-warning"> 
		<strong>Pasos para convalidar:</strong>&nbsp;&nbsp;
      	<span class="badge bg-info">1</span> Agregar una solicitud.&nbsp;&nbsp;&nbsp;&nbsp;
      	<span class="badge bg-info">2</span> Elegir las materias y grabarlas.&nbsp;&nbsp;&nbsp;&nbsp;    
      	<span class="badge bg-info">3</span> Confirmar la solicitud.&nbsp;&nbsp;&nbsp;&nbsp;
<!--       	<span class="badge bg-info">4</span> Llenar e imprimir el Formulario.&nbsp;&nbsp;&nbsp;&nbsp; -->
<!--       	<span class="badge bg-info">5</span> Anexar la documentación necesaria al formulario y enviarla. -->
    </div>		
	<form name="frmimportcalif" method="post" action="solicitud">
	<input name="Accion" type="hidden" value="">
	<input name="codigoPersonal" type="hidden" value="">
	<input name="convalidacionId" type="hidden" value="">
	<input name="numeroCom" type="hidden" value="">
	<input name="cursoId" type="hidden" value="">
	<input type="hidden" name="estado" value="">

	<table class="table table-sm table-bordered">  
						
<% 
	for(int i=0; i < lisConv.size(); i++){
		convEvento = lisConv.get(i);
		if(convEvento.getEstado().equals("S")) proceso = "SOLICITUD";
		if(convEvento.getEstado().equals("P")) proceso = "PREDICTAMEN";
		if(convEvento.getEstado().equals("C")) proceso = "CONFIRMADO";
		if(convEvento.getEstado().equals("G")) proceso = "GRADUAL";
		if(convEvento.getEstado().equals("F")) proceso = "SIN DOCUMENTO";
		if(convEvento.getEstado().equals("D")) proceso = "SIN PAGO";
		if(convEvento.getEstado().equals("A")) proceso = "TRAMITANDO";
		if(convEvento.getEstado().equals("X")) proceso = "CANCELADO";
		if(convEvento.getEstado().equals("T")) proceso = "TERMINADO";
		if(convEvento.getEstado().equals("R")) proceso = "REGISTRADO";
		String totalMat = "0";
		if (mapaTotalMaterias.containsKey(convEvento.getConvalidacionId())){
			totalMat = mapaTotalMaterias.get(convEvento.getConvalidacionId());
		}
%>  
		<tr class="table-success">
<%		convMateria.setConvalidacionId(convEvento.getConvalidacionId());%>			
			<td align="left" colspan="7">
<%		if(totalMat.equals("0") && convEvento.getEstado().equals("S")){%>
				<i class="fas fa-trash-alt" onclick="borraConvaYMaterias('<%=codigoAlumno%>','<%=convEvento.getConvalidacionId()%>');"  alt="Eliminar" style="color:red"></i>&nbsp;	
<%		}%>		
				<a href="editar?ConvalidacionId=<%=convEvento.getConvalidacionId()%>"><i class="far fa-file-alt" title="Editar convalidación"></i></a>&nbsp;
<%	    if(convEvento.getEstado().equals("S")){ %>					
				<i class="fas fa-edit" onclick="eligeAccion('<%=convEvento.getTipoConv()%>','<%=planId%>','<%=convEvento.getConvalidacionId()%>')" title='Agregar materias' ></i>&nbsp;
<%		}%>	
<%		if(Integer.parseInt(totalMat) >= 1 && acceso.getModalidad().equals("0")){%>
			<i onclick="document.location.href='reporte?ConvalidacionId=<%=convEvento.getConvalidacionId()%>&Estado=<%=convEvento.getEstado()%>'" class="fas fa-print"></i>&nbsp;
<%		}%>	
<%	    if(convEvento.getEstado().equals("S")){ %>	
				<%=convEvento.getUsuario() %> - <%=convEvento.getFecha() %> - <%=convEvento.getPlanId() %> - <%=convEvento.getDictamen()%> -&nbsp;
				<b>
				<%
			if(esConvalidador){	
				if (!convEvento.getEstado().equals("R")){%>
				<select name="Estado" id="Estado" onchange="javascript:guarda('<%=convEvento.getConvalidacionId() %>',this.options[this.selectedIndex].value)">
					<option value="S" <%if(convEvento.getEstado().equals("S")) out.print("selected");%>>Solicitud (S)</option>
				  	<option value="P" <%if(convEvento.getEstado().equals("P")) out.print("selected");%>>Predictamen (P)</option>
				  	<option value="C" <%if(convEvento.getEstado().equals("C")) out.print("selected");%>>Confirmada (C)</option>
				  	<option value="G" <%if(convEvento.getEstado().equals("G")) out.print("selected");%>>Gradual (G)</option>
				  	<option value="F" <%if(convEvento.getEstado().equals("F")) out.print("selected");%>>Sin Documento (F)</option>
					<option value="D" <%if(convEvento.getEstado().equals("D")) out.print("selected");%>>Sin Pago (D)</option>
				  	<option value="A" <%if(convEvento.getEstado().equals("A")) out.print("selected");%>>Tramite (A)</option>
				  	<option value="T" <%if(convEvento.getEstado().equals("T")) out.print("selected");%>>Terminada (T)</option>
				  	<option value="X" <%if(convEvento.getEstado().equals("X")) out.print("selected");%>>Cancelada (X)</option>
				</select>
<%				}
			}else{
				out.print(proceso);
			}%>
							</b>
				Comentario:
				<input type=text name="comentario<%=convEvento.getConvalidacionId()%>" value="<%if(convEvento.getComentario() == null) out.print(""); else out.print(convEvento.getComentario());%>"/>
				<input class="btn btn-primary" type=button value="Grabar" onclick="grabaCom(<%=convEvento.getConvalidacionId()%>);"/>
<%			boolean vacias = false;
			if(mapaLisMaterias.containsKey(convEvento.getConvalidacionId())){
				lisMaterias = mapaLisMaterias.get(convEvento.getConvalidacionId());
			}

			for(int j=0; j < lisMaterias.size(); j++){ 
				convMateria = (ConvMateria) lisMaterias.get(j);
				if(convMateria.getMateria_O() == null || convMateria.getCreditos_O() == null || convMateria.getNota_O() == null || convMateria.getfNota() == null || 
				   convEvento.getInstitucion().equals("X") || convEvento.getPrograma().equals("X") || convEvento.getPeriodo().equals("-")) {
					vacias = true;
					break;
				}
			}
			
			if(!vacias){%>
				<input class="btn btn-primary"type=button value="Confirmar" onclick="cerrar(<%=convEvento.getConvalidacionId()%>);"/>
<%			}
		}else{
			if(esConvalidador){	
				if(convEvento.getEstado().equals("C")){ %>
			  	<i onclick="document.location.href='dictamen?ConvalidacionId=<%=convEvento.getConvalidacionId()%>'" class="fas fa-list"></i>
<%				} %>
			  	<i onclick="document.location.href='historial?ConvalidacionId=<%=convEvento.getConvalidacionId()%>'" class="fas fa-search"></i>
<%			} %>
					
				<%=convEvento.getUsuario() %> - <%=convEvento.getFecha() %> - <%=convEvento.getPlanId() %> - <%=convEvento.getDictamen()%> -&nbsp; 
				<b>
				<%
			if(esConvalidador){	
				if (!convEvento.getEstado().equals("R")){%>
				<select name="Estado" id="Estado" onchange="javascript:guarda('<%=convEvento.getConvalidacionId() %>',this.options[this.selectedIndex].value)">
				  	<option value="S" <%if(convEvento.getEstado().equals("S")) out.print("selected");%>>Solicitud (S)</option>
				  	<option value="P" <%if(convEvento.getEstado().equals("P")) out.print("selected");%>>Predictamen (P)</option>
				  	<option value="C" <%if(convEvento.getEstado().equals("C")) out.print("selected");%>>Confirmada (C)</option>
				  	<option value="G" <%if(convEvento.getEstado().equals("G")) out.print("selected");%>>Gradual (G)</option>
				  	<option value="F" <%if(convEvento.getEstado().equals("F")) out.print("selected");%>>Sin Documento (F)</option>
					<option value="D" <%if(convEvento.getEstado().equals("D")) out.print("selected");%>>Sin Pago (D)</option>
				  	<option value="A" <%if(convEvento.getEstado().equals("A")) out.print("selected");%>>Tramite (A)</option>
				  	<option value="T" <%if(convEvento.getEstado().equals("T")) out.print("selected");%>>Terminada (T)</option>
				  	<option value="X" <%if(convEvento.getEstado().equals("X")) out.print("selected");%>>Cancelada (X)</option>
				</select>
<%				}
			}else{
				out.print(proceso);
			}%>
				</b>
				&nbsp; <strong>Por <%=convEvento.getTipoConv().equals("M")?" Materia":"Ciclo"%></strong> &nbsp;Comentario: <%if(convEvento.getComentario()== null) out.print(""); else out.print(convEvento.getComentario());%>  
<%		}	%>
			</td>
		</tr>
		<tr>  	
			<td colspan="7">
				<table class="table table-sm" style="width:100%">
					<tr> 
			            <th><spring:message code="aca.Operacion"/></th>
			            <th width="15%">Semestre y clave</th>
			            <th width="42%"><spring:message code="aca.Materia"/></th>
			            <th width="4%" class="right">Créd.Conv.</th>
			            <th width="4%" class="right">Créd.Plan</th>
			            <th width="4%"><spring:message code="aca.Nota"/></th>
			            <th width="13%"><spring:message code="aca.Fecha"/> Nota</th>
			            <th width="15%">Edo.</th>
	          		</tr>
<%			
		matAceptadas = 0; matRechazadas=0;
		int totCredOrigen 		= 0;
		float credPlan			= 0;
		int totCredPlan 		= 0;
		int totCredAceptados	= 0;		
		if(mapaLisMaterias.containsKey(convEvento.getConvalidacionId())){
			lisMaterias = mapaLisMaterias.get(convEvento.getConvalidacionId());
		}
		
		for(int j=0; j < lisMaterias.size(); j++){ 
			convMateria = (ConvMateria) lisMaterias.get(j);
			if (convMateria.getEstado().equals("S")) {matAceptadas++;}
			if (convMateria.getEstado().equals("N")) {matRechazadas++;}
			
			if(mapaCreditos.containsKey(convMateria.getCursoId())){
				credPlan = mapaCreditos.get(convMateria.getCursoId());
			}

			String nombreCurso = "-";
			if(mapaNombreMaterias.containsKey(convMateria.getCursoId())){
				nombreCurso = mapaNombreMaterias.get(convMateria.getCursoId());
			}
			
			totCredPlan += credPlan;
			if (convMateria.getEstado().equals("S")){
				totCredAceptados += credPlan;
			}
			
			if (convMateria.getCreditos_O()!=null && !convMateria.getCreditos_O().equals("")){
				totCredOrigen += Integer.parseInt(convMateria.getCreditos_O());																
			}			
%>
					<tr class="tr2"> 
<%			if(convEvento.getEstado().equals("S")){ %>
						<td align="right" width="5%"><img onclick="borraMateria('<%=convEvento.getConvalidacionId()%>' , '<%=convMateria.getCursoId()%>');" class="button" alt='Eliminar Materia'  src='../../imagenes/no.png'></img></td>
<%			}else{ %>
			   			<td align="right" width="9%"><img src="../../imagenes/g1.gif" /></td>
<%			} %>
				   		<td>&nbsp;<%=convMateria.getConvalidacionId()/*No es convalidacionId. Es el semestre y el cursoId*/%></td>
				   		<td><%=nombreCurso%></td>
				   		<td align="center"><%=convMateria.getCreditos_O() %></td>
				   		<td align="center"><%=credPlan%></td>
				  		<td align="center"><%=convMateria.getNota_O() %></td>
				   		<td align="center"><%=convMateria.getfNota()%></td>
				   		<td width="16%" align="center"><%=convMateria.getEstado()%></td>
					</tr>
<%			}%>
			   		<tr> 
			      		<th colspan="9"> 
			       			Materias: <%=lisMaterias.size()%> &nbsp; &nbsp;  Aceptadas: <%=matAceptadas%> &nbsp; &nbsp; Rechazadas: <%=matRechazadas%> 
			       			&nbsp; &nbsp; Cred.Origen: <%=totCredOrigen%> &nbsp; &nbsp; Cred.Plan: <%=totCredPlan%> &nbsp; &nbsp; Cred. Aceptados: <%=totCredAceptados%>
			       		</th>
		          	</tr>
				  	<tr><td colspan="9">&nbsp;</td></tr>
				</table>
			</td>
		</tr>
<%			if(i==lisConv.size()-1)continue; %>
		<tr>
			<td style="background: #BDBDBD;">&nbsp;</td>
		</tr>
<% 
	}
%>					
	</table>
	</form>
</div>