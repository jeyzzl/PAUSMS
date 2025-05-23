<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bec.BecPuestoAlumnoUtil"%>
<%@page import="aca.bec.spring.BecPuestoAlumno"%>
<%@page import="aca.bec.spring.BecPlazas"%>
<%@page import="aca.bec.spring.BecContrato"%>
<%@page import="aca.bec.spring.BecCategoria"%>
<%@page import="aca.bec.spring.BecAcuerdo"%>
<%@page import="aca.bec.spring.BecInforme"%>
<%@page import="aca.bec.spring.BecInformeAlumno"%>
<%@page import="aca.financiero.spring.ContCcosto"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

	if( (String) session.getAttribute("fechaPuesto") == null ){
		session.setAttribute("fechaPuesto", aca.util.Fecha.getHoy());
	}
	String fechaPuesto 		= (String) session.getAttribute("fechaPuesto");

	String codigo			= (String)session.getAttribute("codigoPersonal");
	String codigoAlumno		= "";
	
	String idEjercicio 		= request.getParameter("idEjercicio");
	String periodoId 		= (String) session.getAttribute("periodoBecas");
	String periodoNombre 	= (String)request.getAttribute("periodoNombre");
	String idCcosto    		= request.getParameter("idCcosto");
	String categoriaId 		= request.getParameter("categoriaId");
	String fecha			= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
	
	List<BecPuestoAlumno> lisPuestosAlumno 			= (List<BecPuestoAlumno>)request.getAttribute("lisPuestosAlumno");
	List<BecAcuerdo> lisAsociados 					= (List<BecAcuerdo>)request.getAttribute("lisAsociados");
	List<BecInformeAlumno> lisInformes 				= (List<BecInformeAlumno>)request.getAttribute("lisInformes");	
	HashMap<String,BecContrato> mapaBecContrato		= (HashMap<String,BecContrato>)request.getAttribute("mapaBecContrato");

	if( idEjercicio == null || idCcosto == null || categoriaId == null ){
		response.sendRedirect("puesto");
	}
	
	boolean adminBecas       	= (boolean)request.getAttribute("adminBecas");	
	BecPlazas becPlazas 	= (BecPlazas)request.getAttribute("becPlazas");
	
	int cantidadB 			= (int)request.getAttribute("cantidadB");
	int cantidadT 			= (int)request.getAttribute("cantidadT");
	int cantidadI 			= (int)request.getAttribute("cantidadI");	
	int cantidadP 			= (int)request.getAttribute("cantidadP");
	int cantidadM 			= (int)request.getAttribute("cantidadM");
	
	HashMap<String,ContCcosto> mapaCostos 			= (HashMap<String,ContCcosto>)request.getAttribute("mapaCostos");	
	HashMap<String,BecCategoria> mapaCategorias	 	= (HashMap<String,BecCategoria>)request.getAttribute("mapaCategorias");	
	String categoria = "";		
	if(mapaCategorias.containsKey(categoriaId)){
		categoria = mapaCategorias.get(categoriaId).getCategoriaNombre();
	}	
	HashMap<String,String> mapaAlumnos 				= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaInscritos 			= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	HashMap<String,String> mapaFoliosDeAcuerdos		= (HashMap<String,String>)request.getAttribute("mapaFoliosDeAcuerdos");
	HashMap<String,BecAcuerdo> mapaAcuerdos			= (HashMap<String,BecAcuerdo>)request.getAttribute("mapaAcuerdos");
	HashMap<String,String> mapaFinancieros			= (HashMap<String,String>)request.getAttribute("mapaFinancieros");
	HashMap<String,String> mapaAsociados			= (HashMap<String,String>)request.getAttribute("mapaAsociados");
	HashMap<String,String> mapaVigentes				= (HashMap<String,String>)request.getAttribute("mapaVigentes");
	HashMap<String,BecInforme> mapaInformes			= (HashMap<String,BecInforme>)request.getAttribute("mapaInformes");
	HashMap<String,String> mapaEvaluaciones			= (HashMap<String,String>)request.getAttribute("mapaEvaluaciones");
		
	//TRAER EL TIPO DE LA BECA BASICA
	String tipoBasica 			= (String)request.getAttribute("tipoBasica");	

	// PRESUPUESTO DE LA BASICA
	String presBasica 			= (String)request.getAttribute("presBasica");	
	
	//TRAER EL TIPO DE LA BECA BASICA ADICIONAL
	String tipoAdicional		= (String)request.getAttribute("tipoAdicional");	

	// PRESUPUESTO BASICA ADICIONAL
	String presAdicional 		= (String)request.getAttribute("presAdicional");
	
	//TRAER EL TIPO DE LA BECA INSTITUCIONAL
	String tipoInstBasica		= (String)request.getAttribute("tipoInstBasica");
	
	// PRESUPUESTO BASICA ADICIONAL
	String presInstBasica 		= (String)request.getAttribute("presInstBasica");	
	
	String usadoBasica 			= (String)request.getAttribute("usadoBasica");
	String usadoAdicional 		= (String)request.getAttribute("usadoAdicional");
	String usadoInstitucional	= (String)request.getAttribute("usadoInstitucional");

	double totBasica 			= Double.parseDouble(presBasica)-Double.parseDouble(usadoBasica);
	double totAdicional			= Double.parseDouble(presAdicional)-Double.parseDouble(usadoAdicional);
	double totInstitucional		= Double.parseDouble(presInstBasica)-Double.parseDouble(usadoInstitucional);
%>
<style>
	body{
		background:white;
	}
	table.right tr td{
		text-align:right;
	}
	table.right tr td:first-child{
		text-align:left;
	}
	
	.right{
		text-align: left !important;
	}
</style>

<div class="container-fluid">
<h2>Lista de Alumnos<small class="text-muted fs-5">( <%=idEjercicio %> - <%=periodoNombre%> - <%=fechaPuesto %> )</small></h2>
<div class="row">
	<div class="span7">
		<!-- 
		Plazas Basicas Disponibles: <%= Integer.parseInt(becPlazas.getNumPlazas())-cantidadB %> <br />
		Plazas Institucionales Disponibles: <%=  Integer.parseInt(becPlazas.getNumIndustriales())-cantidadI %> <br />
		Plazas Pre-Institucionales Disponibles: <%=  Integer.parseInt(becPlazas.getNumPreIndustriales())-cantidadP %> <br />
		Plazas Temporales Disponibles: <%=  Integer.parseInt(becPlazas.getNumTemporales())-cantidadT %> <br />
		Plazas de Posgrado Disponibles: <%=  Integer.parseInt(becPlazas.getNumPosgrado())-cantidadM %> <br />
		 -->
	<form name="frmPuesto" action="puestoAlumno">
	<input type="hidden" name="idEjercicio" value="<%=idEjercicio%>">
	<input type="hidden" name="idCcosto" value="<%=idCcosto%>">
	<input type="hidden" name="categoriaId" value="<%=categoriaId%>">
	<div class="alert alert-info">
		<a href="puesto" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i> Puestos</a>
<%		boolean catActiva = false;
		
		if(mapaCategorias.containsKey(categoriaId)){
			if(mapaCategorias.get(categoriaId).getEstado().equals("A")){ 
				catActiva = true;
			}
		}
		if(catActiva){//HABILITAR OPCION TEMPORALMENTE A TODOS %>
			<a href="addPuestoAlumno?idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>" class="btn btn-primary"><i class="icon-plus icon-white"></i><spring:message code='aca.Añadir'/></a>
		<%} %>
	</div>
	<div class="alert alert-info" class="span7">
		<table class="table table-condensed alert alert-info table-nohover right" style="padding:5px;">
			<tr>
				<th><spring:message code="aca.Tipo"/></th>
				<th style="text-align:right">Pres.</th>
				<th style="text-align:right">Usado</th>
				<th style="text-align:right">Disponible</th>
			</tr>
			<tr>
				<td>Básica</td>
				<td><%=getFormato.format(Double.parseDouble(presBasica)) %></td>
				<td><%=getFormato.format(Double.parseDouble(usadoBasica)) %></td>
				<td><%= getFormato.format(totBasica) %></td>
			</tr>			
			<tr>
				<td>Adicional</td>
				<td><%=getFormato.format(Double.parseDouble(presAdicional)) %></td>
				<td><%=getFormato.format(Double.parseDouble(usadoAdicional)) %></td>
				<td><%= getFormato.format(totAdicional) %></td>
			</tr>
			<tr>
				<td><spring:message code="aca.Institucion"/>al</td>
				<td><%=getFormato.format(Double.parseDouble(presInstBasica)) %></td>
				<td><%=getFormato.format(Double.parseDouble(usadoInstitucional)) %></td>
				<td><%= getFormato.format(totInstitucional) %></td>
			</tr>
		</table>		
	</div>
	</form>
	</div>
	<div class="span5">
		<%
			int nBasicas 			= Integer.parseInt(becPlazas.getNumPlazas());
			int nIndustriales 		= Integer.parseInt(becPlazas.getNumIndustriales());
			int nPreIndustriales 	= Integer.parseInt(becPlazas.getNumPreIndustriales());
			int nTemp 				= Integer.parseInt(becPlazas.getNumTemporales());
			int nPostgrado 			= Integer.parseInt(becPlazas.getNumPosgrado());
			
			int dBasicas 			= Integer.parseInt(becPlazas.getNumPlazas())-cantidadB;
			int dIndustriales 		= Integer.parseInt(becPlazas.getNumIndustriales())-cantidadI;
			int dPreIndustriales 	= Integer.parseInt(becPlazas.getNumPreIndustriales())-cantidadP;
			int dTemp 				= Integer.parseInt(becPlazas.getNumTemporales())-cantidadT;
			int dPostgrado 			= Integer.parseInt(becPlazas.getNumPosgrado())-cantidadM;
		%>
		<table class="table table-condensed alert alert-info table-nohover right" style="padding:10px;">			
			<tr>
				<th><spring:message code="aca.Tipo"/></th>
				<th style="text-align:right">Número</th>
				<th style="text-align:right">Usadas</th>
				<th style="text-align:right">Disponibles</th>
			</tr>
			<tr>
				<td>Plazas Básicas</td>
				<td><%= nBasicas %></td>
				<td><%= cantidadB %></td>
				<td><%= dBasicas %></td>
			</tr>
			<tr>
				<td>Plazas Industriales</td>
				<td><%=  nIndustriales %></td>
				<td><%=  cantidadI %></td>
				<td><%=  dIndustriales %></td>
			</tr>
			<tr>
				<td>Plazas Preindustriales</td>
				<td><%=  nPreIndustriales %></td>
				<td><%=  cantidadP %></td>
				<td><%=  dPreIndustriales %></td>
			</tr>
			<tr>
				<td>Plazas Temporales</td>
				<td><%=  nTemp %></td>
				<td><%=  cantidadT %></td>
				<td><%=  dTemp %></td>
			</tr>
			<tr>
				<td>Plazas de Posgrado</td>
				<td><%=  nPostgrado %></td>
				<td><%=  cantidadM %></td>
				<td><%=  dPostgrado %></td>
			</tr>
			<tr>
				<td><strong>TOTAL</strong></td>
				<td><strong><%= nBasicas+nIndustriales+nPreIndustriales+nTemp+nPostgrado %></strong></td>
				<td><strong><%= cantidadB+cantidadI+cantidadP+cantidadT+cantidadM %></strong></td>
				<td><strong><%= dBasicas+dIndustriales+dPreIndustriales+dTemp+dPostgrado %></strong></td>
			</tr>
		</table>
	</div>
</div>
	
	<table class="table">
		<tr><td colspan="12"><%= mapaCostos.get(idCcosto)!=null?mapaCostos.get(idCcosto).getNombre():idCcosto  %> - <%=categoria %> </td></tr>
		<tr>		
			<th>#</th>
			<th><spring:message code="aca.Operacion"/></th>
			<th>Beca Básica</th>
			<th>Beca Adicional</th>
			<th>Criterio</th>
			<th>Acuerdo</th>
			<th>ID</th>
			<th><spring:message code="aca.Alumno"/></th>
			<th><spring:message code="aca.FechaInicio"/></th>
			<th>Fecha Fin</th>
			<th><spring:message code="aca.Tipo"/></th>
			<th><spring:message code="aca.Estado"/></th>
		</tr>
<%			
		BecAcuerdo becAcuerdoBasica 		= new BecAcuerdo();
		BecAcuerdo becAcuerdoAdicional 		= new BecAcuerdo();

		int cont = 0;
		for(BecPuestoAlumno puestos: lisPuestosAlumno){
			cont++;
			
			String alumno = "";
			codigoAlumno = puestos.getCodigoPersonal();
			String alumnoNombre = "-";
			if (mapaAlumnos.containsKey(codigoAlumno)){
				alumnoNombre =  mapaAlumnos.get(codigoAlumno);
			}
			if(puestos.getCodigoPersonal() != null){
				alumno = puestos.getCodigoPersonal() + " | " + alumnoNombre;
			}			
			
			String estado = "";
			if( puestos.getEstado().equals("I") ){
				estado = "Inactivo";
			}else if( puestos.getEstado().equals("P") ){
				estado = "Precontratado";
			}else if( puestos.getEstado().equals("C") ){
				estado = "Contratado";
			}
			
			becAcuerdoBasica 			= new BecAcuerdo();
			becAcuerdoAdicional 		= new BecAcuerdo();
			
			String tipoB = tipoBasica;
			if(puestos.getTipo().equals("I")){
				tipoB = tipoInstBasica;
			}
			
			boolean inscrito	= false;
			if (mapaInscritos.containsKey(puestos.getCodigoPersonal())){
				inscrito = true;
			}
			
			String folio = "0";
			if (mapaFoliosDeAcuerdos.containsKey(puestos.getCodigoPersonal()+puestos.getPuestoId()+tipoB)){				
				folio = mapaFoliosDeAcuerdos.get(puestos.getCodigoPersonal()+puestos.getPuestoId()+tipoB);				
				becAcuerdoBasica = mapaAcuerdos.get( puestos.getCodigoPersonal()+folio);				
			}			
			
			String folio2 = "0";
			if (mapaFoliosDeAcuerdos.containsKey(puestos.getCodigoPersonal()+puestos.getPuestoId()+tipoAdicional)){ 
				folio2 					= mapaFoliosDeAcuerdos.get(puestos.getCodigoPersonal()+puestos.getPuestoId()+tipoAdicional);				
				becAcuerdoAdicional 	= mapaAcuerdos.get( puestos.getCodigoPersonal()+folio2);				
			}
			
			String tipo = "";			
			if(puestos.getTipo().equals("B")){
				tipo = "Básica";
			}else if(puestos.getTipo().equals("I")){
				tipo = "Institucional";
			}else if(puestos.getTipo().equals("T")){
				tipo = "Temporal";
			}else if(puestos.getTipo().equals("P")){
				tipo = "Preindustrial";
			}else if(puestos.getTipo().equals("M")){
				tipo = "Posgrado";
			}
			
			String becas = "N";
			if(!becAcuerdoBasica.getEnsenanza().equals("") || !becAcuerdoAdicional.getEnsenanza().equals("")){
				becas = "S";
			}			
			
			String acuerdo="";
			if(becAcuerdoAdicional.getTipoadicional().equals("A")){
				acuerdo = "Solo Depósitos de Colportaje";					
			}else if(becAcuerdoAdicional.getTipoadicional().equals("B")){
				acuerdo = "Depósitos de Colportaje y Familia";
			}else if(becAcuerdoAdicional.getTipoadicional().equals("C")){
				acuerdo = "Solo Depósitos de Familia";
			}else if(becAcuerdoAdicional.getTipoadicional().equals("D")){
				acuerdo = "Cantidad Fija";
			}	
			
			int totAsociados = 0;
			if (mapaAsociados.containsKey(puestos.getCodigoPersonal()+puestos.getPuestoId())){
				totAsociados 	= Integer.parseInt( mapaAsociados.get(puestos.getCodigoPersonal()+puestos.getPuestoId()));
			}
			
			int totVigentes = 0;
			if ( mapaVigentes.containsKey(puestos.getCodigoPersonal()) ){
				totVigentes 	= Integer.parseInt( mapaVigentes.get(puestos.getCodigoPersonal()) );
			}			
			
			String resultado = "No Tiene";
			if (totAsociados>0){
				resultado = "Asociado:"+totAsociados;
			}else if(totVigentes>0){
				resultado = "Vigente:"+totVigentes;
			}	
%>
		<tr>
			<td><%=cont%></td>
			<td>
		<%
			if(!puestos.getEstado().equals("C")){
		%>
				<a href="addPuestoAlumno?becas=<%=becas%>&idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>&puestoId=<%=puestos.getPuestoId() %>" class="btn btn-info btn-sm"><i class="fas fa-pencil-alt"></i></a>
		<%
			}
		
			boolean show = false;			
			if(becAcuerdoBasica.getTipo().equals(tipoB)){				
				show = true;						
				
				for(BecAcuerdo ac: lisAsociados){
					if ( ac.getCodigoPersonal().equals(puestos.getCodigoPersonal()) && ac.getPuestoId().equals(puestos.getPuestoId()) ){
						int cantidad = 0;
						if (mapaFinancieros.containsKey(puestos.getCodigoPersonal()+puestos.getPuestoId()+ac.getTipo())){
							cantidad = Integer.parseInt(mapaFinancieros.get(puestos.getCodigoPersonal()+puestos.getPuestoId()+ac.getTipo()));
						}						
						//SI EL ALUMNO TIENE ACUERDOS PERO AUN NO GENERA SU CARGA DE MATERIAS, POR LO QUE NO ESTA EN EL FINANCIERO ENTONCES NO PUEDE IMPRIMIR SU CONVENIO						
						if(cantidad==0){						
							show = false;					
							break;
						}				
					}
				}				
				if( show && puestos.getEstado().equals("C") && !mapaBecContrato.containsKey(puestos.getCodigoPersonal()+puestos.getPuestoId())){
		%>
		   		<a href="contratoPDF?idEjercicio=<%=idEjercicio%>&CodigoAlumno=<%=puestos.getCodigoPersonal()%>&Puesto=<%=puestos.getPuestoId()%>&EjercicioId=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>" class="btn btn-warning btn-sm"> 
			  		<i class="fas fa-save"></i>
				</a>		
		<%	
				}
				
				if(mapaBecContrato.containsKey(puestos.getCodigoPersonal()+puestos.getPuestoId())){
		%>
		   		<a onclick="eliminarPdf('<%=idEjercicio%>', '<%=idCcosto%>', '<%=categoriaId%>', '<%=puestos.getPuestoId() %>', '<%=puestos.getCodigoPersonal() %>');" class="btn btn-danger btn-sm"> 
			  		<i class="fas fa-times"></i>
				</a>		
		   		<a href="descargarContrato?CodigoAlumno=<%=puestos.getCodigoPersonal()%>&Puesto=<%=puestos.getPuestoId()%>" class="btn btn-success btn-sm"> 
			  		<i class="fas fa-download"></i>
				</a>		
		<%	
				}
			}			
		%>
				
				<p style="margin-bottom:4px;"></p>
				<%
				
				if(!alumno.equals("") && !puestos.getEstado().equals("C")){
				%>
					<a href="addBecas?puestoId=<%=puestos.getPuestoId()%>&idEjercicio=<%=idEjercicio%>&idCcosto=<%=idCcosto%>&categoriaId=<%=categoriaId%>" class="btn btn-primary btn-sm"><i class="icon-book icon-white"></i> Becas</a>
				<%} %>				
				<%						
				if(adminBecas || true){
					//HABILITAR OPCION TEMPORALMENTE PARA TODOS %>
					<%if(folio.equals("0") && folio2.equals("0") && totAsociados==0){ %>
					<a class="btn btn-danger btn-sm" onclick="javascript:eliminar('<%=idEjercicio%>', '<%=idCcosto%>', '<%=categoriaId%>', '<%=puestos.getPuestoId() %>');"><i class="fas fa-trash-alt icon-white"></i></a>
					<%} %>
				<%} %>			
<%
				
				String horasTotal = becAcuerdoBasica.getHoras();				
				int horasconv = 0;				
				
				String strHoras = "";
				for(BecInformeAlumno informe: lisInformes){
					if (informe.getCodigoPersonal().equals(puestos.getCodigoPersonal()) && informe.getPuestoId().equals(puestos.getPuestoId())){
						
						String ordenInforme = "-";
						if ( mapaInformes.containsKey(informe.getInformeId()) ){
							ordenInforme = mapaInformes.get(informe.getInformeId()).getOrden();
						}
						String mes		= "";
						if ("123456789101112".contains(ordenInforme))
							mes = aca.util.Fecha.getMesNombreCorto( Integer.parseInt(ordenInforme));
						else
							mes = "-";						
						
						String horas 	= informe.getHoras();
						String edo 		= informe.getEstado();
						String color    = "red";
						
						if(edo.equals("1")){
							color = "#3A9727";
						}else if(edo.equals("2")){
							color = "#0055cc";
						}else if(edo.equals("3")){
							color = "black";
						}
						
						strHoras += "<span style='color:"+color+";'>"+mes+" [ "+horas+" ]</span>&nbsp;";
						horasconv+=Integer.parseInt(horas);					
					}	
				}
				
				
				String strEvaluacion = "";
				for(BecInformeAlumno informe: lisInformes){
					if (informe.getCodigoPersonal().equals(puestos.getCodigoPersonal()) && informe.getPuestoId().equals(puestos.getPuestoId())){
						String ordenInforme = "-";
						if ( mapaInformes.containsKey(informe.getInformeId()) ){
							ordenInforme = mapaInformes.get(informe.getInformeId()).getOrden();
						}
						
						String mes		= "";
						if ("123456789101112".contains(ordenInforme))
							mes = aca.util.Fecha.getMesNombreCorto( Integer.parseInt(ordenInforme));
						else
							mes = "-";
						String nota	= "";
						if (mapaEvaluaciones.containsKey(puestos.getCodigoPersonal()+puestos.getPuestoId()+informe.getInformeId())){
							nota = mapaEvaluaciones.get(puestos.getCodigoPersonal()+puestos.getPuestoId()+informe.getInformeId());
							strEvaluacion += mes+" [ "+nota+" ]&nbsp;&nbsp;";
						}					
					}
				}
%>	
				
				<p style="margin-bottom:4px;"></p>
				
				<button 
					data-animation="false" 
					class="btn btn-sm info" 
					data-toggle="popover" 
					title="" 
					data-content="
						<b>Horas Convenio</b> <br> 
						<%=horasTotal%> <br>
						<b>Horas Informadas</b> <br> 
						<%=strHoras%> <br>
						<b>Horas Faltantes</b> <br>
						<%=(Integer.parseInt(horasTotal)-horasconv)%> <br>
						<b>Evaluación</b> <br>
						<%=strEvaluacion %> <br>
						<br>							
						<div style='width:10px;height:10px;background:red;display:inline-block;'></div> Sin Estado
						&nbsp;&nbsp;
						<div style='width:10px;height:10px;background:#3A9727;display:inline-block;'></div> Enviado
						<br>
						<div style='width:10px;height:10px;background:#0055cc;display:inline-block;'></div> Autorizado
						&nbsp;&nbsp;
						<div style='width:10px;height:10px;background:black;display:inline-block;'></div> Contabilizado									
					" 
					data-original-title="Informes"> <i class="icon-tasks"></i> 
				</button>
				
			</td>
			<td title="<%=becAcuerdoBasica.getTipo() %>"><%=becAcuerdoBasica.getEnsenanza().equals("")?"&nbsp;":becAcuerdoBasica.getEnsenanza()+" | " %><%=becAcuerdoBasica.getHoras().equals("")?"&nbsp;":becAcuerdoBasica.getHoras()+" horas" %></td>
			<%if(becAcuerdoAdicional.getTipoadicional().equals("D")){ %>
			<td><%=becAcuerdoAdicional.getEnsenanza().equals("")?"&nbsp;":becAcuerdoAdicional.getEnsenanza()%> </td>
			<%}else{ %>
			<td><%=becAcuerdoAdicional.getEnsenanza().equals("")?"&nbsp;":becAcuerdoAdicional.getEnsenanza()+"% de "+becAcuerdoAdicional.getPromesa() %> </td>
			<%} %>
			<td><%=acuerdo%> </td>
			<td><%=resultado%></td>
			<td><%=puestos.getPuestoId()%></td>
			<td><%=alumno%></td>
			<td><%=puestos.getFechaIni()==null?"":puestos.getFechaIni()%></td>
			<td><%=puestos.getFechaFin()==null?"":puestos.getFechaFin()%></td>
			<td><%=tipo %></td>
			<td>
			<%=estado %>
			<br />
			<%						
				if(show && !puestos.getEstado().equals("C")){
			%>
					<a class="btn btn-success btn-sm" onclick="javascript:contratar('<%=idEjercicio%>', '<%=idCcosto%>', '<%=categoriaId%>', '<%=puestos.getPuestoId()%>','<%=puestos.getCodigoPersonal()%>');"> Contratar</a>
					
			<%
				}else if(show && !inscrito){
														
			%>
					<a class="btn btn-danger btn-sm" onclick="javascript:despedir('<%=idEjercicio%>', '<%=idCcosto%>', '<%=categoriaId%>', '<%=puestos.getPuestoId()%>','<%=puestos.getCodigoPersonal()%>');"> Cancelar</a>
			<%
				}else if(show && adminBecas){
			%>
					<a class="btn btn-danger btn-sm" onclick="javascript:despedir('<%=idEjercicio%>', '<%=idCcosto%>', '<%=categoriaId%>', '<%=puestos.getPuestoId()%>','<%=puestos.getCodigoPersonal()%>');"> Cancelar</a>
			<%
				}
				// El acuerdo en finanzas no se genera porque por algúna razón no se graba el departamento del acuerdo temporal(promocional,vocación docente, etc)
				if (show==false){
					out.print("<i class='fas fa-exclamation-triangle' title='No existe el acuerdo en finanzas' style='color:red'></i>");
				}
			%>
			</td>			
		</tr>
<%		} %>
	</table>	
	<script>
	
		function eliminar(idEjercicio, idCcosto, categoriaId, puestoId){
			if( confirm("¿Estás Seguro que Deseas Eliminar este Registro?") ){
				document.location.href = "borrarPuestoAlumno?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId+"&puestoId="+puestoId;
			}
		}
	
		function eliminarPdf(idEjercicio, idCcosto, categoriaId, puestoId, codigoPersonal){
			if( confirm("¿Estás Seguro que Deseas Eliminar este PDF?") ){
				document.location.href = "eliminarPdf?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId+"&Puesto="+puestoId+"&CodigoAlumno="+codigoPersonal;
			}
		}
		
		function contratar(idEjercicio, idCcosto, categoriaId, puestoId, codigoAlumno){
			if( confirm("¿Estás Seguro que Deseas Contratar a este Alumno?") ){
				document.location.href = "contratarAlumno?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId+"&puestoId="+puestoId+"&codigoAlumno="+codigoAlumno;
			}
		}
		
		function despedir(idEjercicio, idCcosto, categoriaId, puestoId, codigoAlumno){
			if( confirm("¿Estás Seguro que Deseas Cancelar el Contrato de este Alumno?") ){
				document.location.href = "despedirAlumno?idEjercicio="+idEjercicio+"&idCcosto="+idCcosto+"&categoriaId="+categoriaId+"&puestoId="+puestoId+"&codigoAlumno="+codigoAlumno;
			}
		}
		
		jQuery('.info').popover();
	</script>	
</div>