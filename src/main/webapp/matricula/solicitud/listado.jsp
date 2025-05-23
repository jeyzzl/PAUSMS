<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaEnLinea"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String cargaId 				= (String) request.getAttribute("cargaId");
	String bloques 				= (String) session.getAttribute("bloques");	
	String fechaHoy 			= aca.util.Fecha.getHoy();
	
	List<CargaEnLinea> lisCargasActivas 			= (List<CargaEnLinea>) request.getAttribute("lisCargasActivas");
	List<CatFacultad> lisFacultades					= (List<CatFacultad>) request.getAttribute("lisFacultades");
	
	HashMap<String,String> mapaAlumnosEnfacultad	= (HashMap<String,String>) request.getAttribute("mapaAlumnosEnfacultad");
	HashMap<String,String> mapaAlumnosEnCarga		= (HashMap<String,String>) request.getAttribute("mapaAlumnosEnCarga");
	HashMap<String,String> mapaAlumnosEnCalculo		= (HashMap<String,String>) request.getAttribute("mapaAlumnosEnCalculo");
	HashMap<String,String> mapaCargasConfirmadas	= (HashMap<String,String>) request.getAttribute("mapaCargasConfirmadas");
// 	HashMap<String,String> mapaCargasPagadas		= (HashMap<String,String>) request.getAttribute("mapaCargasPagadas");
	HashMap<String,String> mapaInscritos			= (HashMap<String,String>) request.getAttribute("mapaInscritos");
	HashMap<String,CargaBloque> mapaBloques			= (HashMap<String,CargaBloque>) request.getAttribute("mapaBloques");	
// 	HashMap<String,String> mapaPracticas			= (HashMap<String,String>) request.getAttribute("mapaPracticas");
	
	String enviaBloques = "'"+bloques+"'";
	
	if(enviaBloques.contains(",")){
		enviaBloques = enviaBloques.replace(",", "','");
	}
	
	String arrBloques[] = bloques.split(",");
	
	int cont = 0;
%>
<body>
<div class="container-fluid">
	<h2>Online Enrollment Progress</h2>
	<form name="frmProceso" action="listado" method="post">	
	<div class="alert alert-info d-flex align-items-center">
		Load: &nbsp;		
		<select id="CargaId" name="CargaId" class="form-select" style="width: 500px;" onchange="javascript:ActualizaCarga()">
<%	for(CargaEnLinea carga : lisCargasActivas){%>
			<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?"selected":""%>><%=carga.getCargaId()%> - <%=carga.getNombre()%></option>
<% 	}%>
		</select>
		&nbsp;&nbsp;
		<button class="btn btn-primary" type="submit"><i class="fas fa-sync-alt"></i></button>
		&nbsp;&nbsp;&nbsp;
<%-- 		<a href="bloques?CargaId=<%=cargaId%>" class="btn btn-primary"><i class="fas fa-pencil-alt"></i></a>&nbsp; --%>
		Blocks[&nbsp; 
<%
		for(String blo : arrBloques){
			if(mapaBloques.containsKey(cargaId+blo)){
%>
				<span class="badge bg-success"><%=blo%></span>
<%
			}else{
%>
				<span class="badge bg-warning"><%=blo%></span>
<%
			}
			cont++;
			if(cont != arrBloques.length){
%>
				,
<%
			}
		}
%> 
			&nbsp;]&nbsp;&nbsp;
		<a href="bloques?CargaId=<%=cargaId%>" class="badge bg-primary"><i class="fas fa-pencil-alt"></i></a>
		&nbsp;&nbsp;&nbsp;
<% 	if (codigoPersonal.equals("9800308")){%>		
		&nbsp;&nbsp;
<%-- 		<a class="btn btn-info" href="listaAlumnos?CargaId=<%=cargaId%>&Bloques=<%=enviaBloques%>">Practicum Students</a> --%>
<%	} %>		    
	</div>	
	</form>
	<table style="width:100%" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr class="noHover">
			<th width="5%">#</th>
			<th width="25%">School</th>		
			<th class="text-center" width="10%">Sub. Load</th>
<!-- 			<th class="text-center" width="10%">Practicum</th> -->
			<th class="text-center" width="10%">Tuition Breakdown</th>
			<th class="text-center" width="10%">Confirmed Load</th>
<!-- 			<th class="text-center" width="10%">Paid</th> -->
			<th class="text-center" width="10%">Enrolled</th>
			<th class="text-center" width="10%">Total</th>
		</tr>
	</thead>
	<tbody>	
<% 		
	int row = 0;
	int totalMat 	= 0;
// 	int totalPra 	= 0;
	int totalCal 	= 0;
	int totalCon 	= 0;
// 	int totalPag 	= 0;
	int totalIns 	= 0;
	int totalTotal 	= 0;
	
	for(CatFacultad facultad : lisFacultades){
		row++;
		
		int mat = 0;
		String materias 	= "<span class='badge bg-secondary'>0</span>";		
		if(mapaAlumnosEnCarga.containsKey(facultad.getFacultadId())){
			materias 		= "<span class='badge bg-dark'>"+mapaAlumnosEnCarga.get(facultad.getFacultadId())+"</span>";
			mat = Integer.parseInt(mapaAlumnosEnCarga.get(facultad.getFacultadId()));
		}
		
// 		int pra = 0;
// 		String practicas 	= "<span class='badge bg-secondary'>0</span>";
// 		if(mapaPracticas.containsKey(facultad.getFacultadId())){
// 			pra 		= Integer.parseInt(mapaPracticas.get(facultad.getFacultadId()));
// 			practicas 	= "<span class='badge bg-dark'>"+String.valueOf(pra)+"</span>";			
// 		}
		
		int cal = 0;
		String calculos		= "<span class='badge bg-secondary'>0</span>";		
		if(mapaAlumnosEnCalculo.containsKey(facultad.getFacultadId())){
			calculos 		= "<span class='badge bg-dark'>"+mapaAlumnosEnCalculo.get(facultad.getFacultadId())+"</span>";
			cal = Integer.parseInt(mapaAlumnosEnCalculo.get(facultad.getFacultadId()));
		}
		
		int con = 0;
		String confirmadas 	= "<span class='badge bg-secondary'>0</span>";
		if(mapaCargasConfirmadas.containsKey(facultad.getFacultadId())){
			confirmadas	 	= "<span class='badge bg-dark'>"+mapaCargasConfirmadas.get(facultad.getFacultadId())+"</span>";
			con = Integer.parseInt(mapaCargasConfirmadas.get(facultad.getFacultadId()));
		}
		
// 		int pag = 0;
// 		String pagadas 		= "<span class='badge bg-secondary'>0</span>";
// 		if(mapaCargasPagadas.containsKey(facultad.getFacultadId())){
// 			pagadas	 		= "<span class='badge bg-dark'>"+mapaCargasPagadas.get(facultad.getFacultadId())+"</span>";
// 			pag = Integer.parseInt(mapaCargasPagadas.get(facultad.getFacultadId()));
// 		}	 
		
		int ins = 0;
		String inscritos 	= "<span class='badge bg-secondary'>0</span>";
		if(mapaInscritos.containsKey(facultad.getFacultadId())){
			inscritos	 	= "<span class='badge bg-dark'>"+mapaInscritos.get(facultad.getFacultadId())+"</span>";
			ins = Integer.parseInt(mapaInscritos.get(facultad.getFacultadId()));
		}		
		
		int total = mat+con+ins;
		
		totalMat = totalMat+mat;
// 		totalPra = totalPra+pra;
		totalCal = totalCal+cal;
		totalCon = totalCon+con;
// 		totalPag = totalPag+pag;
		totalIns = totalIns+ins;
		totalTotal = totalTotal+total;				
%>
		<tr>
			<td><%=row%></td>
			<td><%=facultad.getNombreFacultad()%></td>			
			<td class="text-center"><a href="materias?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>&Bloques=<%=enviaBloques%>"><%=materias%></a></td>			
<%-- 			<td class="text-center"><a href="practicas?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>&Bloques=<%=enviaBloques%>"><%=practicas%></a></td> --%>
			<td class="text-center"><a href="calculos?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>&Bloques=<%=enviaBloques%>"><%=calculos%></a></td>
			<td class="text-center"><a href="confirmados?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>&Bloques=<%=enviaBloques%>"><%=confirmadas%></a></td>
<%-- 			<td class="text-center"><a href="pagos?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>&Bloques=<%=enviaBloques%>"><%=pagadas%></a></td>			 --%>
			<td class="text-center"><a href="inscritos?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>&Bloques=<%=enviaBloques%>"><%=inscritos%></a></td>
			<td class="text-center" style="background-color: #E6E6E6;">
				<strong>
<% 				if(total > 0){%>
					<a href="total?CargaId=<%=cargaId%>&FacultadId=<%=facultad.getFacultadId()%>&Bloques=<%=enviaBloques%>"><span class='badge bg-dark'><%=total%></span></a>
<% 				}else{%>
					<%=total%>
<% 				}%>
				</strong>
			</td>
		</tr>
<% 		}%>	
	</tbody>
	</table>
	<table class="table">
	<thead>
		<tr style="background-color: #E6E6E6;">
			<th colspan="3" class="text-center" style="font-weight: bold;" width="30%" >Total</th>
			<th class="text-center" style="font-weight: bold;" width="10%"><a href="cargaAcademica?CargaId=<%=cargaId%>&Bloques=<%=enviaBloques%>"><span class="badge bg-success"><%=totalMat%></span></a></th>
<%-- 			<th class="text-center" style="font-weight: bold;" width="10%"><a href="listaPracticas?CargaId=<%=cargaId%>&Bloques=<%=enviaBloques%>"><span class="badge bg-success"><%=totalPra%></span></a></th> --%>
			<th class="text-center" style="font-weight: bold;" width="10%"><a href="listaCalculos?CargaId=<%=cargaId%>&Bloques=<%=enviaBloques%>"><span class="badge bg-success"><%=totalCal%></span></a></th>
			<th class="text-center" style="font-weight: bold;" width="10%"><a href="listaConfirmados?CargaId=<%=cargaId%>&Bloques=<%=enviaBloques%>"><span class="badge bg-success"><%=totalCon%></span></a></th>
<%-- 			<th class="text-center" style="font-weight: bold;" width="10%"><a href="listaPagos?CargaId=<%=cargaId%>&Bloques=<%=enviaBloques%>"><span class="badge bg-success"><%=totalPag%></span></a></th> --%>
			<th class="text-center" style="font-weight: bold;" width="10%"><a href="listaInscritos?CargaId=<%=cargaId%>&Bloques=<%=enviaBloques%>"><span class="badge bg-success"><%=totalIns%></span></a></th>
			<th class="text-center" style="font-weight: bold;" width="10%"><a href="listaTotal?CargaId=<%=cargaId%>&Bloques=<%=enviaBloques%>"><span class="badge bg-dark"><%=totalTotal%></span></a></th>
		</tr>
	</thead>
	</table>	
</div>
</body>
<script type="text/javascript">
	function ActualizaCarga(){		
		document.frmProceso.submit();		
	}
</script>
</html>
