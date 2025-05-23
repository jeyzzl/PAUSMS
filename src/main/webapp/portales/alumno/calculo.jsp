<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.vista.spring.FinTabla"%>
<%@page import="aca.plan.spring.MapaCursoPre"%>
<%@page import="aca.calcula.spring.CalCosto"%>
<%@page import="aca.calcula.spring.CalPagare"%>
<%@page import="aca.calcula.spring.CalConcepto"%>
<%@page import="aca.calcula.spring.CalMovimiento"%>
<%@page import="aca.calcula.spring.CalAlumno"%>
<%@page import="aca.calcula.spring.CalPagareAlumno"%>
<%@page import="aca.financiero.spring.FinSaldo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<script type="text/javascript">
	function borrar(movimientoId){
		if (confirm("¿Estás seguro de borrar este movimiento?")){
			document.location.href = "quitarMovimiento?MovimientoId=" + movimientoId;
		}
	}	
	
	function grabarPagare(){		 
		document.frmCosto.submit(); 
	}	
	
	function grabarDescuento(){
		document.getElementById("formDescuentos").submit(); 
	}	

	function Confirmar(cargaId, bloqueId){
		if (confirm("¿Estás de acuerdo en hacer el cambio?")){
			document.location.href = "cambiarS?CargaId=" + cargaId + "&BloqueId=" + bloqueId +"&Confirmar=S";
		}
	}
</script>
</head>
<body>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");
	String cargaId	 			= (String)request.getAttribute("cargaId");
	String bloqueId 			= (String)request.getAttribute("bloqueId");
	String cargaNombre			= (String)request.getAttribute("cargaNombre");
	String bloqueNombre			= (String)request.getAttribute("bloqueNombre");
	String tipoAlumno			= (String)request.getAttribute("tipoAlumno");
	String mensaje				= (String)request.getAttribute("mensaje");
	boolean existePagare		= (boolean)request.getAttribute("existePagare");
	String ingreso				= (String)request.getAttribute("ingreso");
	
	AlumPersonal personal		= (AlumPersonal)request.getAttribute("personal");
	AlumAcademico academico		= (AlumAcademico)request.getAttribute("academico");
	MapaPlan mapaPlan			= (MapaPlan)request.getAttribute("mapaPlan");
	String prefijoCarrera		= mapaPlan.getVersionId().equals("3")?"PD":"";
	CalAlumno calAlumno			= (CalAlumno)request.getAttribute("calAlumno");
	FinSaldo finSaldo 			= (FinSaldo) request.getAttribute("finSaldo");
		
	List<MapaCurso> lisMaterias					= (List<MapaCurso>)request.getAttribute("lisMaterias");
	List<CargaAcademica> lisOferta				= (List<CargaAcademica>)request.getAttribute("lisOferta");
	List<KrdxCursoAct> lisCurso					= (List<KrdxCursoAct>)request.getAttribute("lisCurso");
	List<CalCosto> lisCostos					= (List<CalCosto>)request.getAttribute("lisCostos");
	List<CalPagare> lisPagares					= (List<CalPagare>)request.getAttribute("lisPagares");
	List<CalMovimiento> listaMovimientos		= (List<CalMovimiento>)request.getAttribute("listaMovimientos");
	List<CalConcepto> lisDescuentos				= (List<CalConcepto>)request.getAttribute("lisDescuentos");
	List<CalPagareAlumno> listaParageAlumno		= (List<CalPagareAlumno>)request.getAttribute("listaParageAlumno");
	
	HashMap<String,FinTabla> mapaCostos 				= (HashMap<String,FinTabla>)request.getAttribute("mapaCostos");
	HashMap<String,KrdxCursoAct> mapaKardexCurso 		= (HashMap<String,KrdxCursoAct>)request.getAttribute("mapaKardexCurso");
	HashMap<String,String> mapaMateriaCosto				= (HashMap<String,String>)request.getAttribute("mapaMateriaCosto");
	HashMap<String,CalConcepto> mapaConceptos			= (HashMap<String,CalConcepto>)request.getAttribute("mapaConceptos");
	HashMap<String,String> mapaConceptoMovimientos 		= (HashMap<String,String>)request.getAttribute("mapaConceptoMovimientos");
	
	float costoEns 	= 0;
	float costoMat   	= 0;
	float costoInt   	= 0;
	float costoExt   	= 0;
	
	for(KrdxCursoAct kardex : lisCurso){
		for (MapaCurso curso : lisMaterias){
			if (curso.getCursoId().equals(kardex.getCursoId())){
				for (CargaAcademica carga : lisOferta){
					if(carga.getCursoCargaId().equals(kardex.getCursoCargaId())){
						// Precio
						String costo 		= "0";
						String porcentaje 	= "0";
						float precio 		= 0;
						if (mapaPlan.getPrecio().equals("N")){
							if (mapaCostos.containsKey(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId())){
								costoMat = Float.valueOf(mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getMatricula());
								costoInt = Float.valueOf(mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getInternado());
								costoExt = Float.valueOf(mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getLegales());
								if(academico.getClasFin().equals("1")){
									costo = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getAcfe();
									porcentaje = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getPorCredito();
									precio = Float.valueOf(costo) * Float.valueOf(curso.getCreditos()) * Float.valueOf(porcentaje);
									costoEns += precio;
								}else{
									costo = mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getNoAcfe();
									precio = Float.valueOf(costo) * Float.valueOf(curso.getCreditos());
									costoEns += precio;
								}
							}
						}else{
							// Busaca el precio de las materias del conservatorio
							if (mapaMateriaCosto.containsKey(carga.getCursoCargaId())){
								precio = Float.valueOf(mapaMateriaCosto.get(carga.getCursoCargaId())) * Float.valueOf(kardex.getCantidad());
								costoEns += precio;
							} 
						}	
					}
				}
			}
		}
	}
	
	float totMatricula 	= costoMat;
	float totDescuentos = 0;		
	for (CalMovimiento mov : listaMovimientos){
		if (mov.getTipo().equals("D")){
			totMatricula += Float.valueOf(mov.getImporte()); 
		}else{
			totDescuentos += Float.valueOf(mov.getImporte()); 
		}
	}
	
	// Saldo actual del estudiante
	float saldoFinanciero = Float.parseFloat(finSaldo.getSaldoSP()) * -1;
	if (existePagare==false || ( calAlumno.getSaldo().equals("0") && Float.parseFloat(calAlumno.getSaldo()) != saldoFinanciero)){
		calAlumno.setSaldo(String.valueOf(saldoFinanciero));
	}
	
	int row = 0;
%>
<div class="container-fluid">
	<h3>Carga de Materias<small class="text-muted fs-6"> ( <b><%=codigoAlumno%></b> - <%=nombreAlumno%> | <b>Carga:</b>
	<%=cargaId%> - <b>Bloque:</b> <%=bloqueId%> - <%=bloqueNombre%> - <%=mapaPlan.getPlanId()%> - Ingreso: <%=ingreso.equals("R")?"RE":"NI"%> )</small>
	<% if (calAlumno.getConfirmar().equals("S") && codigoPersonal.equals(codigoAlumno)){
	out.print("<div class='alert alert-info alert-danger'><h6> El número de pagaré, ya esta confirmado!</h6>");
}%>
	</h3>
	<form id="frmCosto" name="frmCosto" action="grabarPagare" method="post">
		<input type="hidden" name="CargaId" value="<%=cargaId%>">
		<input type="hidden" name="BloqueId" value="<%=bloqueId%>">
		<input type="hidden" name="CobroMatricula" value="<%=calAlumno.getCobroMatricula()%>">
		<input type="hidden" id="TotMat" name="TotMat" value="<%=totMatricula%>">
		<input type="hidden" id="TotEns" name="TotEns" value="<%=costoEns%>">
		<input type="hidden" id="TotDes" name="TotDes" value="<%=totDescuentos%>">
				
		<div class="d-flex align-items-center alert <%=existePagare?"alert-info":"alert-danger"%>">
			<a class="btn btn-primary btn-sm" href="verMaterias?CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;&nbsp;
			Cobra Matrícula: <%=calAlumno.getCobroMatricula().equals("S")?"SI":"NO"%>			
			&nbsp;&nbsp;	
			Número de Pagos:&nbsp;
			<select name="NumPagare" class="form-select" style="width:170px;">
<% 		int num=1;
		for (CalPagare pago : lisPagares){
			if(num == 1){
%>			
				<option value="1" <%=calAlumno.getNumPagare().equals("1")?"selected":""%>>Pago contado</option>
<%			}else{%>
				<option value="<%=num%>" <%=calAlumno.getNumPagare().equals(String.valueOf(num))?"selected":""%>><%=num%> pagos</option>
<%			}
			num++;
		} %>															
			</select>&nbsp;&nbsp;
			Saldo:
			<input type="text" class="form-control" style="width: 100px;" name="Saldo" value="<%=calAlumno.getSaldo()%>">&nbsp;&nbsp;
<% 		if(calAlumno.getConfirmar().equals("N")){%>
			<a onclick="grabarPagare()" class="btn btn-primary btn-sm"><i class="fas fa-save"></i></a>	
<%}%>
		</div>	
	</form>
<% 	if(mensaje.equals("1")){%>
	<div class="d-flex align-items-center alert alert-success">
		Grabado
	</div>
<% 	}else if(mensaje.equals("2")){%>
	<div class="d-flex align-items-center alert alert-danger">
		No grabo
	</div>
<% 	}%>
	<div class="row">
		<div class="col-md-6">
			<h4> Concentrado de movimientos</h4>
			<table class="table table-sm table-bordered">
				<thead class="table-info">
					<tr>
						<th width="10%" class="text-center">#</th>
						<th width="70%">Concepto</th>
						<th width="10%" class="text-end">Cargo</th>
						<th width="10%" class="text-end">Abono</th>
					</tr>
				</thead>
				<tbody>			
<% 		
		float sumaCreditos=0; float sumaCargos = 0;
		int rowCostos = 0;
		if(calAlumno.getCobroMatricula().equals("S")){
			rowCostos++;
			sumaCargos += costoMat;
%>			
					<tr>
						<td class="text-center"><%=rowCostos%></td>
						<td>Matrícula</td>
						<td class="text-end"><%=formato.format(costoMat)%></td>
						<td class="text-end">&nbsp;</td>
					</tr>
<%		}%>		
<%		
		rowCostos++;
		sumaCargos += costoEns;
%>			
					<tr>
						<td class="text-center"><%=rowCostos%></td>
						<td>Enseñanza</td>
						<td class="text-end"><%=formato.format(costoEns)%></td>
						<td class="text-end">&nbsp;</td>
					</tr>
<%			
		row = rowCostos;
		for (CalMovimiento movimiento : listaMovimientos){			
			row++;
			
			String conceptoNombre = "-";
			if (mapaConceptos.containsKey(movimiento.getConceptoId())){
				conceptoNombre = mapaConceptos.get(movimiento.getConceptoId()).getConceptoNombre();
			}		
			
			float movCargo 		= 0;
			float movCredito 	= 0;
			if (movimiento.getTipo().equals("D")){
				movCargo 		= Float.parseFloat(movimiento.getImporte());
				sumaCargos 		+= Float.parseFloat(movimiento.getImporte());
			}
			if (movimiento.getTipo().equals("C")){
				movCredito 		= Float.parseFloat(movimiento.getImporte());
				sumaCreditos 	+= Float.parseFloat(movimiento.getImporte());
			}
%>
					<tr>
						<td class="text-center"><%=row%></td>
						<td><%=conceptoNombre%></td>				
						<td class="text-end"><%=formato.format(movCargo)%></td>
						<td class="text-end"><%=formato.format(movCredito)%></td>
					</tr>	
<%		
		}
		float saldoCargo 	= sumaCargos>sumaCreditos?sumaCargos-sumaCreditos:0;
		float saldoCredito 	= sumaCreditos>sumaCargos?sumaCreditos-sumaCargos:0;
%>	
					<tr class="table-secondary">
						<td class="text-end" colspan="2"><b>Subtotales</b></td>								
						<td class="text-end"><%=formato.format(sumaCargos)%></td>
						<td class="text-end"><%=formato.format(sumaCreditos)%></td>
					</tr>
					<tr class="table-info">
						<td class="text-end" colspan="2"><b>Saldo total</b></td>								
						<td class="text-end"><%=saldoCargo==0?"":formato.format(saldoCargo)%></td>
						<td class="text-end"><%=saldoCredito==0?"":formato.format(saldoCredito)%></td>
					</tr>
				</tbody>
			</table>
			<h4>Pagos</h4>
			<form name="formModificarPagare" action="modificarPagare" method="post">				
				<table class="table table-sm table-bordered">
					<thead class="table-info">
						<tr>
							<th width="10%" class="text-center">#</th>
							<th width="70%">Pago</th>
							<th width="10%" class="text-end">Fecha</th>
							<th width="10%" class="text-end">Importe</th>								
						</tr>
					</thead>
					<tbody>
<% 				row = 1;
				for(CalPagareAlumno pagareAlumno : listaParageAlumno){
%>
						<tr>
							<td><%=row%></td>
							<td><%=pagareAlumno.getPagareNombre()%></td>
							<td><%=pagareAlumno.getFecha()%></td>
							<td align="right"><%=formato.format(Float.parseFloat(pagareAlumno.getImporte()))%></td>
						</tr>
<% 				
					row++;
				}%>
					</tbody>
				</table>	
<% 		if(calAlumno.getConfirmar().equals("N") && codigoPersonal.equals(codigoAlumno)){%>
				<a class="btn btn-primary" onclick="javascript:Confirmar('<%=cargaId%>','<%=bloqueId%>');">Confirmar</a>
<% 		}%>
			</form><br>
		</div>
	</div>	
</div>
</body>
</html>