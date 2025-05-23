<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");	
	String periodoId				= (String) request.getAttribute("periodoId");
	String cargaId					= (String) request.getAttribute("cargaId");
	String tipo						= (String) request.getAttribute("tipo");
	String filtro					= request.getParameter("Filtro")==null?"I":request.getParameter("Filtro");
	
	boolean periodoActivo			= (boolean) request.getAttribute("periodoActivo");
	
	List<CatPeriodo> lisPeriodos 	= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");
	List<CatCarrera> lisCarreras	= (List<CatCarrera>) request.getAttribute("lisCarreras");
	
	HashMap<String, CatFacultad> mapFacultades 		= (HashMap<String, CatFacultad>) request.getAttribute("mapFacultades");
	HashMap<String, CatCarrera> mapCarreras 		= (HashMap<String, CatCarrera>) request.getAttribute("mapCarreras");
	HashMap<String, String> mapEstados 				= (HashMap<String, String>) request.getAttribute("mapEstados");
	HashMap<String, String> mapPresupuestoCarrera	= (HashMap<String, String>) request.getAttribute("mapPresupuestoCarrera");
	HashMap<String, String> mapEnContrato			= (HashMap<String, String>) request.getAttribute("mapEnContrato");
	HashMap<String, String> mapSinContrato 			= (HashMap<String, String>) request.getAttribute("mapSinContrato");
	HashMap<String, String> mapConfirmadas 			= (HashMap<String, String>) request.getAttribute("mapConfirmadas");
	
	boolean encuentraCarga			= false;
%>
<div class="container-fluid">
	<h2>Periodos/facultades y carreras</h2>
	<form name="forma" action="elegir" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<select name="PeriodoId" class="form-select" style="width:140px" onchange="javascript:document.forma.submit();">
	<%	for(CatPeriodo periodo : lisPeriodos){%>
		<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%	}%>
    	</select>
		&nbsp;&nbsp;
		<select name="CargaId" class="form-select" style="width:350px" onchange="javascript:document.forma.submit();">
			<option <%=cargaId.equals("000000")?" Selected":""%> value="000000">[000000] Elige una carga</option>
	<%	for(Carga carga : lisCargas){
			if (carga.getCargaId().equals(cargaId)) encuentraCarga = true;
	%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
	<%	}%>
		</select>
		&nbsp;&nbsp;
		<select name="Filtro"  class="form-select" style="width:140px"  onchange='javascript:document.forma.submit();'>
			<option value="I" <%=filtro.equals("I")?" selected":""%>>Informadas</option>
			<option value="T" <%=filtro.equals("T")?" selected":""%>>Todas</option>
    	</select>
		&nbsp;&nbsp;
		<select name="Tipo" id="Tipo" class="form-select" style="width:120px" onchange="javascript:document.forma.submit();">			
			<option value="O" <%=tipo.equals("O")?"selected":""%>>Otoño</option>
			<option value="I" <%=tipo.equals("I")?"selected":""%>>Invierno</option>
			<option value="P" <%=tipo.equals("P")?"selected":""%>>Primavera</option>
			<option value="V" <%=tipo.equals("V")?"selected":""%>>Verano</option>
		</select>
		&nbsp;&nbsp;
		<input type="text" class="form-control" style="width:200px" placeholder="Buscar..." id="buscar">
<%	if (codigoPersonal.equals("9800308") || codigoPersonal.equals("9800660") || codigoPersonal.equals("9800146")){%>
		&nbsp; &nbsp;&nbsp; &nbsp;
		<a href="presupuesto?CargaId=<%=cargaId%>&Tipo=<%=tipo%>"> <img src="../../imagenes/presupuesto.jpg" width="25px"> Presupuesto</a>
<%	}%>		
	</div>
	<%if (periodoActivo==false){%>
	<div class="alert alert-info">¡ El periodo de registro de horas no está activo !</div>
	<%}%>
	</form>
	<table class="table table-sm table-bordered text-sm" id="table">
<%	
	int row = 0;
	String facTemp 		= "X";
	double gastoSol 	= 0;
	double gastoAut 	= 0;
	double gastoNom		= 0;
	double gastoFac		= 0;
	double sumaSol		= 0;
	double sumaAut		= 0;
	double sumaNom		= 0;
	boolean mostrarTotal = false;
	for (CatCarrera carrera : lisCarreras){
		String nombreFacultad	= "-";		
		if (!facTemp.equals(carrera.getFacultadId())){
			row=0;
			if (mapFacultades.containsKey(carrera.getFacultadId())){
				nombreFacultad = mapFacultades.get(carrera.getFacultadId()).getNombreFacultad();
				facTemp = carrera.getFacultadId();
			}
			if(mostrarTotal){
%>
	<tr >
		<td colspan="5"><b>Total</b></td>
		<td class="text-end"><b><%=formato.format(gastoSol)%></b></td>
		<td>&nbsp;</td>
		<td class="text-end"><b><%=formato.format(gastoAut)%></b></td>
		<td>&nbsp;</td>
		<td class="text-end"><b><%=formato.format(gastoNom)%></b></td>
		<td>&nbsp;</td>		
		<td class="text-end"><b><%=formato.format(gastoSol+gastoAut+gastoNom)%></b></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
<%
				gastoSol 	= 0;
				gastoAut	= 0;
				gastoNom	= 0;
				gastoFac	= 0;
			}else{
				mostrarTotal = true;
			}
%>
	<tr>
		<td colspan="14"><h3><%=carrera.getFacultadId()%>:<%=nombreFacultad%></h3></td>
	</tr>
	<tr class="table-info"> 
		<th width="2%">#</th>		
		<th width="3%"><spring:message code="aca.Clave"/></th>
		<th width="30%"><spring:message code="aca.Carrera"/></th>
		<th width="10%">Departamento</th>
		<th width="5%" class="text-end">Sol.</th>
		<th width="5%" class="text-end">$Sol.</th>
		<th width="5%" class="text-end">Aut.</th>
		<th width="5%" class="text-end">$Aut.</th>
		<th width="5%" class="text-end">Nóm.</th>
		<th width="5%" class="text-end">$Nóm.</th>
		<th width="5%" class="text-end">Total</th>
		<th width="5%" class="text-end">$Total</th>
		<th width="5%" class="text-end">Vo.Bo.</th>
		<th width="5%" class="text-end">#Cont.</th>
		<th width="5%" class="text-end">#Pend.</th>
	</tr>
<%			
		}		
		row++;
		
		String departamento = "-";
		if (mapCarreras.containsKey(carrera.getCarreraId())){
			departamento = mapCarreras.get(carrera.getCarreraId()).getCcostoId();
		}
		
		String etiquetaSol = "badge bg-dark";
		int totSol = 0;
		if (mapEstados.containsKey(carrera.getCarreraId()+"S")){
			 totSol = Integer.parseInt(mapEstados.get(carrera.getCarreraId()+"S"));
			 if (totSol > 0) etiquetaSol = "label-warning";
		}
		
		int totAut = 0;
		if (mapEstados.containsKey(carrera.getCarreraId()+"A")){
			 totAut = Integer.parseInt(mapEstados.get(carrera.getCarreraId()+"A"));			 
		}
		
		int totNom = 0;
		if (mapEstados.containsKey(carrera.getCarreraId()+"N")){
			 totNom = Integer.parseInt(mapEstados.get(carrera.getCarreraId()+"N"));			 
		}
		
		String presSol = "0";
		if(mapPresupuestoCarrera.containsKey(carrera.getCarreraId()+"S")){
			presSol = mapPresupuestoCarrera.get(carrera.getCarreraId()+"S");
		}
		
		String presAut = "0";
		if(mapPresupuestoCarrera.containsKey(carrera.getCarreraId()+"A")){
			presAut = mapPresupuestoCarrera.get(carrera.getCarreraId()+"A");
		}
		
		String presNom = "0";
		if(mapPresupuestoCarrera.containsKey(carrera.getCarreraId()+"N")){
			presNom = mapPresupuestoCarrera.get(carrera.getCarreraId()+"N");
		}
		
		int total = totSol+totAut+totNom;
		
		String etiquetaAut = " badge bg-warning";
		String etiquetaTot = "badge bg-dark";
		String etiquetaNom = "badge bg-warning";
		
		if (totAut>0) etiquetaAut 	=  " badge bg-info";
		if (totNom>0) etiquetaNom 	=  " badge bg-info";
		if (total>0) etiquetaTot 	=  " badge bg-dark";
		
		gastoSol	+= Double.parseDouble(presSol);
		gastoAut	+= Double.parseDouble(presAut);
		gastoNom	+= Double.parseDouble(presNom);		
		gastoFac	+= gastoSol+gastoAut+gastoNom;
		
		sumaSol 		+= Double.parseDouble(presSol);
		sumaAut 		+= Double.parseDouble(presAut);
		sumaNom 		+= Double.parseDouble(presNom);
		
		String totContrato = "0";
		if (mapEnContrato.containsKey(carrera.getCarreraId())){
			totContrato	= mapEnContrato.get(carrera.getCarreraId());
		}
		
		String totPendiente = "0";
		if (mapSinContrato.containsKey(carrera.getCarreraId())){
			totPendiente	= mapSinContrato.get(carrera.getCarreraId());
		}
		
		String totConfirmadas = "0";
		if (mapConfirmadas.containsKey(carrera.getCarreraId())){
			totConfirmadas = mapConfirmadas.get(carrera.getCarreraId());
		}
%>
	<tr>
		<td><%=row%></td>
		<td><%=carrera.getCarreraId()%></td>
		<td>
	<%	if (encuentraCarga /*&& periodoActivo*/){%>
		  <a href="cargar?CarreraId=<%=carrera.getCarreraId()%>&Tipo=<%=tipo%>"><%=carrera.getNombreCarrera()%></a>
	<%	}else{
			out.print(carrera.getNombreCarrera());
		}
	%>
		</td>
		<td><%=departamento%></td>
		<td class="text-end"><span class="label label-pill <%=etiquetaSol%>"><%=totSol%></span></td>
		<td class="text-end"><span class="label label-pill <%=etiquetaSol%>"><%=formato.format(Double.parseDouble(presSol))%></span></td>
		<td class="text-end"><span class="label label-pill <%=etiquetaAut%>"><%=totAut%></span></td>
		<td class="text-end"><span class="label label-pill <%=etiquetaAut%>"><%=formato.format(Double.parseDouble(presAut))%></span></td>
		<td class="text-end"><span class="label label-pill <%=etiquetaNom%>"><%=totNom%></span></td>
		<td class="text-end"><span class="label label-pill <%=etiquetaNom%>"><%=formato.format(Double.parseDouble(presNom))%></span></td>
		<td class="text-end"><span class="label label-pill <%=etiquetaTot%>"><%=total%></span></td>		
		<td class="text-end"><span class="label label-pill"><%=formato.format(gastoSol+gastoAut+gastoNom)%></span></td>
		<td class="text-end"><%=totConfirmadas%></td>
		<td class="text-end"><%=totContrato%></td>
		<td class="text-end"><%=totPendiente%></td>
	</tr>
<%		
	}
%>
	<tr>
		<td colspan="5"><b>Total</b></td>
		<td class="text-end"><b><%=formato.format(gastoSol)%></b></td>
		<td>&nbsp;</td>
		<td class="text-end"><b><%=formato.format(gastoAut)%></b></td>
		<td>&nbsp;</td>
		<td class="text-end"><b><%=formato.format(gastoNom)%></b></td>
		<td>&nbsp;</td>
		<td class="text-end"><b><%=formato.format(gastoSol+gastoAut+gastoNom)%></b></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="14">&nbsp;</td>
	</tr>		
	<tr>
		<td colspan="5"><b>Total del periodo:</b></td>
		<td class="text-end"><b><%=formato.format(sumaSol)%></b></td>
		<td>&nbsp;</td>
		<td class="text-end"><b><%=formato.format(sumaAut)%></b></td>
		<td>&nbsp;</td>
		<td class="text-end"><b><%=formato.format(sumaNom)%></b></td>
		<td>&nbsp;</td>
		<td class="text-end"><b><%=formato.format(sumaSol+sumaAut+sumaNom)%></b></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	</table>
<%	
%>	
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>