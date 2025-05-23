<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.financiero.spring.ContEjercicio"%>
<%@ page import="aca.financiero.spring.ContCcosto"%>
<%@ page import="aca.bec.spring.BecPuesto"%>
<%@ page import="aca.bec.spring.BecPeriodo"%>
<%@ page import="aca.bec.spring.BecPuestoAlumno"%>
<%@ page import="aca.bec.spring.BecAcuerdo"%>

<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

	String idEjercicio 			= (String)request.getAttribute("idEjercicio");
	String periodoId 			= (String)request.getAttribute("periodoId");
	String idCcosto 			= (String)request.getAttribute("idCcosto");	
	
	String cambioDeFecha		= (String)request.getAttribute("cambioDeFecha");
	String errorFecha 			= (String)request.getAttribute("errorFecha");
	String fechaPuesto 			= (String)request.getAttribute("fechaPuesto");
	
	String acceso				= (String)request.getAttribute("acceso");		
	boolean admin      			= (boolean)request.getAttribute("admin");			
	boolean encontro 			= (boolean)request.getAttribute("encontro");
	
	List<ContEjercicio> ejercicios 			= (List<ContEjercicio>)request.getAttribute("ejercicios");
	List<BecPuesto> puestos 				= (List<BecPuesto>)request.getAttribute("puestos");
	List<ContCcosto> lisDeptos 				= (List<ContCcosto>)request.getAttribute("lisDeptos");
	List<BecPeriodo> periodos 				= (List<BecPeriodo>)request.getAttribute("periodos");

	HashMap <String, String> categorias 	= (HashMap <String, String>)request.getAttribute("categorias");	
	HashMap <String, String> mapaCarreraSe 	= (HashMap <String, String>)request.getAttribute("mapaCarreraSe");

	// PARA EL COMBO DE CENTRO DE COSTOS ---------------------------------->	
	HashMap<String, String> niveles 							= (HashMap <String, String>)request.getAttribute("niveles");
	HashMap<String,List<BecPuestoAlumno>> mapaDePuestosAlumno 	= (HashMap <String, List<BecPuestoAlumno>>)request.getAttribute("mapaDePuestosAlumno");
	HashMap<String,List<BecAcuerdo>> mapaDeAcuerdosAlumno 		= (HashMap <String, List<BecAcuerdo>>)request.getAttribute("mapaDeAcuerdosAlumno");
	HashMap<String, String> mapaDefolios 						= (HashMap <String, String>)request.getAttribute("mapaDefolios");
	HashMap<String, String> mapaDefolios2 						= (HashMap <String, String>)request.getAttribute("mapaDefolios2");
	HashMap<String, String> mapaAlumnosEnPuestos 				= (HashMap <String, String>)request.getAttribute("mapaAlumnosEnPuestos");		
%>
<style>
	body{
		background:white;
	}
	.puestosAlum td, .puestosAlum th{
		background: white !important;
	}
	
	.puestosAlum th{
		color: black !important;
		border: 1px solid #DCDEDE !important;
	}
</style>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<div class="container-fluid">
	<h2>Alta de Puestos</h2>
	<%=errorFecha %>
	<form action="puesto" name="forma" method="get">
	<input type="hidden" name="cambioDeFecha" />
	<div class="alert alert-info d-flex align-items-center">
		<select name="ccosto" id="ccosto" class="chosen" style="width:500px;" onchange="document.forma.submit()">
	<%
		String group = "";		
		encontro = false;
		String centroCosto = ""; //AQUÍ IRÁ EL PRIMER CENTRO DE COSTO DE SUS PRIVILEGIOS EN CASO DE NO ENCONTRAR COINCIDENCIA CON EL QUE VENÍA ANTERIORMENTE
		for(ContCcosto ccosto : lisDeptos){
			
			//SI NO TIENE ACCESO NO LO MUESTRES
			if( !admin && !acceso.contains("-"+ccosto.getIdCcosto()+"-") ){
				continue;
			}
			if(centroCosto.length()==0 && ccosto.getDetalle().equals("S")){//Graba el primer centro de costo para cargarlo por defecto para cuando no encuentre coincidencia
				centroCosto = ccosto.getIdCcosto();
			}				
			int puntos = 0;
			
			for(int i=0; i<ccosto.getIdCcosto().length(); i++){
				char chr = ccosto.getIdCcosto().charAt(i);					
				if(chr == '.')puntos++;
			}					
			
			if(puntos == 4){//Si estamos en el ultimo nivel de los centro de costo
				
				if(idCcosto.equals("")){
					idCcosto = ccosto.getIdCcosto();
				}

				String idccosto = ccosto.getIdCcosto();					
				String [] arr 	= idccosto.split("\\.");
				String tmp 		= "";
				String groupTmp = "";
				
				int cont = 0;
				for(String str: arr){
					if(cont == arr.length-1)break;//El ultimo no se cuenta porque ya es el nombre de la ultimo nivel						
					tmp+="."+str;						
					if(tmp.charAt(0) == '.')tmp = tmp.substring(1);
					groupTmp += niveles.get(tmp)+" | "; 
					cont++;
				}
				
				if(!groupTmp.equals(group)){//Pintar la categoria de los centros de costo
					%>
						</optgroup>
						<optgroup label="<%=groupTmp.subSequence(0, groupTmp.length()-2) %>">
					<%
					group = groupTmp;
				}					
	%>
					<option value="<%=ccosto.getIdCcosto() %>" <%if(idCcosto.equals(ccosto.getIdCcosto())){out.print("selected"); encontro = true;} %>><%=ccosto.getIdCcosto() %> | <%=ccosto.getNombre() %></option>
	<%
			}
		}
	%>
		</select>	
	<%		
		if(!encontro && lisDeptos.size() >= 1){//Si no encontró ocincidencia con departamento			
			idCcosto = centroCosto;//Nuevo valor de idccosto			
		}
	%>	
		&nbsp;	
		<select name="idEjercicio" id="idEjercicio" class="form-select" style="width:120px;" onchange="document.forma.submit()">
		<%
			for(ContEjercicio ejercicio: ejercicios){	
		%>
				<option value="<%=ejercicio.getIdEjercicio() %>" <%if(idEjercicio.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
		<%
			}
		%>		
		</select>
		&nbsp;		
		<select name="periodoBecas" id="periodoBecas" class="form-select" style="width:250px;" onchange="document.forma.submit()">
		<%
			for(BecPeriodo periodo: periodos){	
		%>
				<option value="<%=periodo.getPeriodoId() %>" <%if(periodo.getPeriodoId().equals(periodoId))out.print("selected"); %>><%=periodo.getPeriodoNombre() %></option>
		<%
			}
		%>
		</select>
		&nbsp;
		<input id="fechaPuesto" name="fechaPuesto" class="form-control" style="margin:0; width:120px;" type="text" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=fechaPuesto%>"/>
		&nbsp;	
		<a onclick="cambioDeFecha();" class="btn btn-info"><i class="fas fa-calendar-alt"></i> Mostrar</a>
		&nbsp;
		<a href="accion?ccosto=<%=idCcosto %>" class="btn btn-primary"><i class="fas fa-plus"></i> Agregar Puesto</a>		
	</div>
	</form>
	<table class="table table-condensed no-hover" style="width:100%;">
	<tr>
		<th style="width:100px;">Accion</th>
		<th>Puesto</th>
		<th>Justificación</th>
		<th>Función</th>
		<th>Competencias</th>
		<th>Certificaciones</th>		
		<th><spring:message code="aca.Estado"/></th>
	</tr>
<%
	//TRAER EL TIPO DE LA BECA INDUSTRIAL BASICA
	String tipoBasicaInd 	= (String)request.getAttribute("tipoBasicaInd");

	//TRAER EL TIPO DE LA BECA BASICA
	String tipoBasica 		= (String)request.getAttribute("tipoBasica");

	//TRAER EL TIPO DE LA BECA ADICIONAL
	String tipoAdicional 	= (String)request.getAttribute("tipoAdicional");

	for(BecPuesto puesto: puestos){
		
		if(!idCcosto.equals(puesto.getIdCcosto()))continue;
		if(!periodoId.equals(puesto.getPeriodoId()))continue;
		
		if( admin || acceso.contains(puesto.getIdCcosto()) ){
			
			String categoria = "";		
			if(categorias.containsKey(puesto.getCategoriaId())){
				categoria = categorias.get(puesto.getCategoriaId());
			}
			
			String estado = "";
			
			if(puesto.getEstado()!=null){
				estado = puesto.getEstado().equals("I")?"Inactivo":"Activo";
			}
			
			List<BecPuestoAlumno> puestosAlumno = new ArrayList<BecPuestoAlumno>();
			if(mapaDePuestosAlumno.containsKey(fechaPuesto+idEjercicio+idCcosto+puesto.getCategoriaId())){
				puestosAlumno = mapaDePuestosAlumno.get(fechaPuesto+idEjercicio+idCcosto+puesto.getCategoriaId());
			}
%>
	<tr>
		<td>
			
			<a href="accion?Accion=0&EjercicioId=<%=puesto.getIdEjercicio()%>&ccosto=<%=puesto.getIdCcosto() %>&categoriaId=<%=puesto.getCategoriaId() %>" class="btn btn-info btn-sm"><i class="fas fa-pencil-alt"></i></a>
			<a class="btn btn-success btn-sm" href="puestoAlumno?idEjercicio=<%=puesto.getIdEjercicio()%>&idCcosto=<%=puesto.getIdCcosto() %>&categoriaId=<%=puesto.getCategoriaId()%>"><i class="fas fa-user"></i></a>
			
			<%if(admin){
				if(puestosAlumno.isEmpty()){
			%>
				<a onclick="eliminar('<%=puesto.getIdCcosto()%>','<%=puesto.getCategoriaId()%>','<%=puesto.getIdEjercicio()%>','<%=puesto.getPeriodoId()%>');" class="btn btn-danger btn-sm"><i class="fas fa-trash-alt icon-white"></i></a>
			<%
				}
			} %>
		</td>
		<td><%=categoria %></td>
		<td><%=puesto.getJustificacion()==null?"":puesto.getJustificacion() %></td>
		<td><%=puesto.getFuncion()==null?"":puesto.getFuncion() %></td>
		<td><%=puesto.getCompetencias()==null?"":puesto.getCompetencias() %></td>
		<td><%=puesto.getCertificaciones()==null?"":puesto.getCertificaciones() %></td>
		<td><%=estado %></td>
	</tr>
<%
			//PUESTOS DE ALUMNOS	
			if(puestosAlumno.size()==0)continue;
%>
	<tr>
		<td style="background:#FBFBFB;"></td>
		<td colspan="9" style="background:white;">
			<table class="table puestosAlum" style="margin-left:0px;margin-bottom:0;">
				<tr>
					<th>#</th>
					<th>ID</th>
					<th><spring:message code="aca.Alumno"/></th>
					<th>Plan</th>
					<th><spring:message code="aca.FechaInicio"/></th>
					<th>Fecha Fin</th>
					<th><spring:message code="aca.Tipo"/></th>
					<th><spring:message code="aca.Estado"/></th>
					<th>Precio/Horas</th>
					<th>Beca Básica</th>
					<th>Beca Adicional</th>
					<th>Criterio</th>
					<%if(admin==true){ %>
					<th>Traspaso</th>
					<%} %>
				</tr>
				<%
				int cont = 0;
				for(BecPuestoAlumno pue: puestosAlumno){
					cont++;
					
					String alumno = "";
					
					if(pue.getCodigoPersonal() != null){
						alumno = pue.getCodigoPersonal() + " | ";
						if(mapaAlumnosEnPuestos.containsKey(pue.getCodigoPersonal())){
							alumno += mapaAlumnosEnPuestos.get(pue.getCodigoPersonal());
						}
					}
					
					String edo = "";
					if( pue.getEstado().equals("I") ){
						edo = "Inactivo";
					}else if( pue.getEstado().equals("P") ){
						edo = "Precontratado";
					}else if( pue.getEstado().equals("C") ){
						edo = "Contratado";
					}
					
					BecAcuerdo becAcuerdoBasica = new BecAcuerdo();
					BecAcuerdo becAcuerdoAdicional = new BecAcuerdo();
					
					String tipoB = tipoBasica;
					if(pue.getTipo().equals("I")){
						tipoB = tipoBasicaInd;
					}
					
					String folio = "";
					if(mapaDefolios.containsKey(pue.getCodigoPersonal()+idEjercicio+tipoB+idCcosto+pue.getPuestoId())){
						folio = mapaDefolios.get(pue.getCodigoPersonal()+idEjercicio+tipoB+idCcosto+pue.getPuestoId());
					}
					
					becAcuerdoBasica.setFolio(folio);
					becAcuerdoBasica.setCodigoPersonal(pue.getCodigoPersonal());					
					
					String folio2 = "";
					if(mapaDefolios2.containsKey(pue.getCodigoPersonal()+idEjercicio+tipoB+idCcosto+pue.getPuestoId())){
						folio2 = mapaDefolios.get(pue.getCodigoPersonal()+idEjercicio+tipoB+idCcosto+pue.getPuestoId());
					}
					becAcuerdoAdicional.setFolio(folio2);
					becAcuerdoAdicional.setCodigoPersonal(pue.getCodigoPersonal());
					
					String tipo = "";
					
					if(pue.getTipo().equals("B")){
						tipo = "Básica";
					}else if(pue.getTipo().equals("I")){
						tipo = "Institucional";
					}else if(pue.getTipo().equals("T")){
						tipo = "Temporal";
					}else if(pue.getTipo().equals("P")){
						tipo = "Preindustrial";
					}else if(pue.getTipo().equals("M")){
						tipo = "Posgrado";
					}
					
					// Color de fondo en cantidad de beca basica
					String colorLetra = pue.getTipo().equals("T")?"":"color:red";
					
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
					
					String planNombre = "-";
					if (mapaCarreraSe.containsKey(pue.getPlanId())){
						planNombre = mapaCarreraSe.get(pue.getPlanId());
					}
					
					String precio 	= becAcuerdoBasica.getEnsenanza().equals("")?"0":becAcuerdoBasica.getEnsenanza();
					String horas	= becAcuerdoBasica.getHoras().equals("")?"0":becAcuerdoBasica.getHoras();
					
					//ACUERDOS DEL ALUMNO EN ESTE PUESTO (SI ES QUE TIENE)
					List<BecAcuerdo> acuerdos = new ArrayList<BecAcuerdo>();
					if(mapaDeAcuerdosAlumno.containsKey(idEjercicio+pue.getCodigoPersonal())){
						acuerdos = mapaDeAcuerdosAlumno.get(idEjercicio+pue.getCodigoPersonal());
					}
				%>				
					<tr>
						<td><%=cont%></td>
						<td>
						<% if (pue.getPeriodoId().equals(periodoId)){
							out.print("<span title='"+pue.getDescripcion()+"' class='badge bg-success'>"+pue.getPuestoId()+"</span>");
						}else{
							out.print("<span title='"+pue.getDescripcion()+"' class='badge bg-warning'>"+pue.getPuestoId()+"</span>");
						} %>
						</td>	
						<td><%=alumno%></td>
						<td title="<%=planNombre%>"><%=pue.getPlanId()%></td>
						<td><%=pue.getFechaIni()==null?"":pue.getFechaIni() %></td>
						<td><%=pue.getFechaFin()==null?"":pue.getFechaFin() %></td>
						<td><%=tipo %></td>
						<td><%=edo %></td>						
						<td><%=becAcuerdoBasica.getEnsenanza().equals("")?"&nbsp;":becAcuerdoBasica.getEnsenanza()+" | " %><%=becAcuerdoBasica.getHoras().equals("")?"&nbsp;":becAcuerdoBasica.getHoras()+" horas" %></td>
						<td style="text-align:right; <%=colorLetra%>"><%=getFormato.format(Double.parseDouble(precio)*Double.parseDouble(horas))%></td>
						<%if(becAcuerdoAdicional.getTipoadicional().equals("D")){ %>
						<td><%=becAcuerdoAdicional.getEnsenanza().equals("")?"&nbsp;":becAcuerdoAdicional.getEnsenanza()%> </td>
						<%}else{ %>
						<td><%=becAcuerdoAdicional.getEnsenanza().equals("")?"&nbsp;":becAcuerdoAdicional.getEnsenanza()+"% de "+becAcuerdoAdicional.getPromesa() %> </td>
						<%} %>
						<td><%=acuerdo %> </td>
						<%if(admin==true){ %>
						<td><a href="mover?PuestoId=<%=pue.getPuestoId()%>&PeriodoId=<%=periodoId%>" class="btn btn-primary"><i class="fas fa-random"></i></a></td>
						<%} %>
					</tr>
					
				<%
				}
				%>
			</table>
		</td>
	</tr>	
<%		
		}
	}
%>
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">

	jQuery(".chosen").chosen();
	jQuery('#fechaPuesto').datepicker();
	
	function eliminar(ccosto, categoria, ejercicioId, periodoId){
		if(confirm("¿Estás seguro que deseas eliminar este registro?")){
			document.location.href = "borrar?ccosto="+ccosto+"&categoria="+categoria+"&ejercicioId="+ejercicioId+"&periodoId="+periodoId;
		}
	}
	function cambioDeFecha(){
		document.forma.cambioDeFecha.value = "1";
		document.forma.submit();
	}
</script>