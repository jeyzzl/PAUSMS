<%@page import="java.text.*" %>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.financiero.spring.ContEjercicio"%>
<%@page import="aca.financiero.spring.ContCcosto"%>
<%@page import="aca.bec.spring.BecPuesto"%>
<%@page import="aca.bec.spring.BecPuestoAlumno"%>
<%@page import="aca.bec.spring.BecCategoria"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
	java.text.DecimalFormat getFormato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	//Lista de ejercicios
	List<ContEjercicio> listaEjercicios 	= (List<ContEjercicio>)request.getAttribute("listaEjercicios");		
	//Variables
	String ejercicioId 		= request.getParameter("ejercicioId")==null?listaEjercicios.get(0).getIdEjercicio():request.getParameter("ejercicioId");	
	String fechaHoy 		= aca.util.Fecha.getHoy();
	String fecha 			= request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");
	
	String cat				= "";
	char inicial			= '\0';
	double tbbasica			= 0;
	double tadicional		= 0;
	double tacuerdo			= 0;
	double ttotal			= 0;
	double tdiezmo			= 0;
	double ttneto			= 0;
	
	//Nombres de centros de costo
	List<ContCcosto> listaCentroCostos	 		= (List<ContCcosto>)request.getAttribute("listaCentroCostos");	
	//Lista de puestos
	List<BecPuesto> listaPuestos				= (List<BecPuesto>)request.getAttribute("listaPuestos");	
	//Alumnos en los puestos
	List<BecPuestoAlumno> listaAlumnos			= (List<BecPuestoAlumno>)request.getAttribute("listaAlumnos");	
	
	String tipoPlaza	= "";
	
	HashMap<String,String> mapaCategorias		= (HashMap<String,String>)request.getAttribute("mapaCategorias");
	HashMap<String,String> mapaTotalBeca		= (HashMap<String,String>)request.getAttribute("mapaTotalBeca");
	HashMap<String,String> mapaBecDiezmo 		= (HashMap<String,String>)request.getAttribute("mapaBecDiezmo");
	HashMap<String,String> mapaBasicas			= (HashMap<String,String>)request.getAttribute("mapaBasicas");
	HashMap<String,String> mapaAdicionales		= (HashMap<String,String>)request.getAttribute("mapaAdicionales");
	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>)request.getAttribute("mapaAlumnos");	
%>
<div class="container-fluid">
	<h1>Listado de puestos</h1>
	<form name="frmPuestos" id="frmPuestos" method="post" action="reporte_puestos">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>			
		<div style="float:right">
			Fecha de revisión <input type="text" data-date-format="dd/mm/yyyy" id="fechaParametro" name="fechaParametro" value="<%=fecha%>"/>
			<a class="btn btn-success" onclick="javascript:document.frmPuestos.submit();">Cargar fecha</a>
			<select name="ejercicioId" id="ejercicioId" style="width:150px;" onchange="document.frmPuestos.submit()">
		<%
			for(ContEjercicio ejercicio: listaEjercicios){	
		%>
				<option value="<%=ejercicio.getIdEjercicio()%>" <%=ejercicioId.equals(ejercicio.getIdEjercicio())?"selected":""%>><%=ejercicio.getIdEjercicio()%></option>
		<%
			}
		%>
		</select>		
		</div>
	</div>
<%
	for(ContCcosto  depto: listaCentroCostos){%>
		<div class="alert alert-info" style="margin-bottom:3px;"><h3><%=depto.getIdCcosto() +" | "+depto.getNombre()%></h3></div> 
<%
		for(BecPuesto bp : listaPuestos){			
		
			if (bp.getIdCcosto().equals(depto.getIdCcosto())){						
				String catId = bp.getCategoriaId();
				if(mapaCategorias.containsKey(catId)){
					cat = mapaCategorias.get(catId);
					inicial = cat.charAt(0);
%>					
		<div class="alert" style="margin-bottom:0px;">
			<span style="font-size:15px;">
				<b><%=inicial+cat.substring(1).toLowerCase()%></b> &nbsp; 
				<i>Justificación:</i> <%= bp.getJustificacion() %> &nbsp; 
				<i>Función:</i> <%= bp.getFuncion() %> &nbsp; 
				<i>Competencias:</i> <%= bp.getCompetencias() %>
			</span> 
		</div>
<%				}%>					
		<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">
			<tr>
				<th style="width:3%;">#</th>
				<th style="width:7%;">Puesto Id</th>
				<th style="width:7%;">Categoría</th>
				<th style="width:3%;"><spring:message code="aca.Matricula"/></th>
				<th style="width:20%;"><spring:message code="aca.Alumno"/></th>
				<th style="width:5%;"><spring:message code="aca.FechaInicio"/></th>
				<th style="width:5%;">Fecha Fin</th>
				<th style="width:5%;"><spring:message code="aca.Tipo"/></th>
				<th style="width:5%;"><spring:message code="aca.Estado"/></th>
				<th style="width:3%;">Beca Básica</th>
				<th style="width:3%;">Adicional</th>
				<th style="width:3%;">Acuerdo</th>
				<th style="width:3%;"><spring:message code="aca.Total"/></th>
				<th style="width:3%;">Diezmo</th>
				<th style="width:3%;">Total Neto</th>
			</tr>
		</thead>
<%			
				int con = 1;
				for(BecPuestoAlumno obj : listaAlumnos){
					if (obj.getCategoriaId().equals(bp.getCategoriaId())){								
						String alum = "-";
						if (mapaAlumnos.containsKey(obj.getCodigoPersonal())){
							alum = mapaAlumnos.get(obj.getCodigoPersonal());
						}				
%>
			<tr>
<%									
						String fechaIni = obj.getFechaIni();
						if(fechaIni==null){
							fechaIni = "01/01/1900";
						}
						String fechaFin = obj.getFechaFin();
						if(fechaFin==null){
							fechaFin = "01/01/1901";
						}
						String fechaActual = aca.util.Fecha.getHoy();
						String fechaParam = fecha;
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						
						java.util.Date dateIni  = sdf.parse(fechaIni, new ParsePosition(0));
						java.util.Date dateFin  = sdf.parse(fechaFin, new ParsePosition(0));
						java.util.Date dateAct  = sdf.parse(fechaParam, new ParsePosition(0));									
						if(dateIni.after(dateFin) || dateIni.equals(dateFin)){//Fecha inicial mayor que fecha final
%>
								
<%						}
						String total 	= "";
						double nTotal 	= 0;
						if ( mapaTotalBeca.containsKey(obj.getCodigoPersonal()+obj.getPuestoId()) ) {
							String stotal = mapaTotalBeca.get(obj.getCodigoPersonal()+obj.getPuestoId());
							//convierto a double
							nTotal 		= Double.parseDouble(stotal);
							//paso el nuevo formato a un string vacio
							total 		= getFormato.format(nTotal);
						} else {
							total = getFormato.format(Double.parseDouble("0"));
						} 
							
						
						String diezmo 	= "";
						double nDiezmo 	= 0;
						if ( mapaBecDiezmo.containsKey(obj.getCodigoPersonal()+obj.getPuestoId()) ) {
							String stotal = mapaBecDiezmo.get(obj.getCodigoPersonal()+obj.getPuestoId());
							//convierto a double
							nDiezmo = Double.parseDouble(stotal);
							//paso el nuevo formato a un string vacio
							diezmo = getFormato.format(nDiezmo);
						} else {
							diezmo = getFormato.format(Double.parseDouble("0"));
						}  
						
						String basica 	= "";
						double nBasica 	= 0;
						if ( mapaBasicas.containsKey(obj.getCodigoPersonal()+obj.getPuestoId()) ) {
							String stotal = mapaBasicas.get(obj.getCodigoPersonal()+obj.getPuestoId());
							//convierto a double
							nBasica = Double.parseDouble(stotal);
							//paso el nuevo formato a un string vacio
							basica = getFormato.format(nBasica);
						} else {
							basica = getFormato.format(Double.parseDouble("0"));
						} 
						
						String adicional 	= "";
						double nAdicional 	= 0;
						if ( mapaAdicionales.containsKey(obj.getCodigoPersonal()+obj.getPuestoId()) ) {
							String stotal = mapaAdicionales.get(obj.getCodigoPersonal()+obj.getPuestoId());
							//convierto a double
							nAdicional = Double.parseDouble(stotal);
							//paso el nuevo formato a un string vacio
							adicional = getFormato.format(nAdicional);
						} else {
							adicional = getFormato.format(Double.parseDouble("0"));
						}
						
						if (obj.getTipo().equals("B")) tipoPlaza = "Básica";
						if (obj.getTipo().equals("P")) tipoPlaza = "Preind.";
						if (obj.getTipo().equals("I")) tipoPlaza = "Institucional";
						if (obj.getTipo().equals("M")) tipoPlaza = "Posgrado";
						if (obj.getTipo().equals("T")) tipoPlaza = "Temp.";
%>
				<td><%=con%></td>
				<td><%=obj.getPuestoId()%></td>
				<td><%=inicial+cat.substring(1).toLowerCase()%></td>
				<td><%=obj.getCodigoPersonal()%></td>
				<td><%=alum%></td>
				<td><%=fechaIni=="01/01/1900"?"-":fechaIni%></td>
				<td><%=fechaFin=="01/01/1901"?"-":fechaFin%></td>
				<td><%=tipoPlaza%></td>
				<td>
					<%if(obj.getEstado().equals("P")){
						out.print("Precontratado");
					}else if(obj.getEstado().equals("C")){
						out.print("Contratado");
					} else{
						out.print("Inactivo");
					}%>
				</td>
				<td style="text-align:right;"><%=basica %></td>
				<td style="text-align:right;"><%=adicional %></td>
				<td style="text-align:right;"><%= getFormato.format( nTotal-nBasica-nAdicional ) %></td>
				<td style="text-align:right;"><%= total %></td>
				<td style="text-align:right;"><%=diezmo %></td>
				<td style="text-align:right;"><%= getFormato.format( nTotal-nDiezmo ) %></td>
			</tr>
<%						
						con++;
						 
						tbbasica+=nBasica;
						tadicional+=nAdicional;
						tacuerdo+=( nTotal-nBasica-nAdicional );
						ttotal+=nTotal;
						tdiezmo+=nDiezmo;
						ttneto+=( nTotal-nDiezmo);

					}// if (obj.getCategoriaId().equals(bp.getCategoriaId())){
				} // for(BecPuestoAlumno obj
%>
			</table>
			</td>							
		</tr>
<%			} // if depto.equals			
		} //for puestos
%>
			</table>
			<div class="alert alert-info" style="margin-bottom:15px;">				
				<h4 style="text-align:center">TOTALES DEL DEPARTAMENTO: 1. Básica = <%=getFormato.format(tbbasica)%> &nbsp;&nbsp;&nbsp; 2. Adicional = <%=getFormato.format(tadicional)%> &nbsp;&nbsp;&nbsp; 3. Acuerdo = <%=getFormato.format(tacuerdo)%> &nbsp;&nbsp;&nbsp;
				    Suma(1+2+3) = <%=getFormato.format(ttotal)%> &nbsp;&nbsp;&nbsp; Diezmo = <%=getFormato.format(tdiezmo)%> &nbsp;&nbsp;&nbsp; Neto = <%=getFormato.format(ttneto)%></h4>
			</div>
<%		tbbasica=0; tadicional=0; tacuerdo=0; ttotal=0; tdiezmo=0; ttneto=0;
	} // for deptos
%> 
	</form>
</div>

<script>
	jQuery('#fechaParametro').datepicker();
</script>
 <style>
 	body{
 		background : white;
 	}
 </style>