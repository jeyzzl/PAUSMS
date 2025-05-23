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
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	function borrar(movimientoId){
		if (confirm("Are you sure to delete this movement?")){
			document.location.href = "quitarMovimiento?MovimientoId=" + movimientoId;
		}
	}

	function quitarConfirmacion(codigoAlumno){
		var cargaId 	= document.getElementById("CargaId").value; 
		var bloqueId 	= document.getElementById("BloqueId").value; 
		
		if (confirm("Are you sure to remove confirmation?")){
			document.location.href = "quitarConfirmacion?CodigoPersonal="+codigoAlumno+"&CargaId="+cargaId+"&BloqueId="+bloqueId;
		}
	}
	
	function grabarPagare(){
		document.frmCosto.submit();
	}	
	
	function grabarDescuento(){
		document.getElementById("formDescuentos").submit(); 
	}	
</script>
</head>
<body>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");
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
	HashMap<String,String> mapaMateriaCosto				= (HashMap<String,String>)request.getAttribute("mapaMateriaCosto");
	HashMap<String,CalConcepto> mapaConceptos			= (HashMap<String,CalConcepto>)request.getAttribute("mapaConceptos");
	HashMap<String,String> mapaConceptoMovimientos 		= (HashMap<String,String>)request.getAttribute("mapaConceptoMovimientos");
	
	float costoEns 		= 0;
	float costoMat   	= 0;
	float costoInt   	= 0;
	float costoExt   	= 0;
	float pagoInicial	= 0;
	
	for(KrdxCursoAct kardex : lisCurso){		
		for (MapaCurso curso : lisMaterias){	
			if (curso.getCursoId().equals(kardex.getCursoId())){			
				//System.out.println("Materia:"+curso.getCursoId());
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
									costo 		= mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getAcfe();
									porcentaje 	= mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getPorCredito();
									precio 		= Float.valueOf(costo) * Float.valueOf(curso.getCreditos()) * Float.valueOf(porcentaje);
									costoEns 	+= precio;
									//System.out.println("ASD:"+costo+":"+porcentaje+":"+precio);
								}else{
									
									costo 		= mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getNoAcfe();
									porcentaje 	= mapaCostos.get(carga.getCargaId()+prefijoCarrera+carga.getCarreraId()+carga.getModalidadId()).getPorCredito();
									precio 		= Float.valueOf(costo) * Float.valueOf(curso.getCreditos()) * Float.valueOf(porcentaje);
									costoEns 	+= precio;
									//System.out.println("No ACFE:"+costo+":"+porcentaje+":"+precio);
								}
							}
						}else{
							// Busca el precio de las materias del conservatorio
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
%>
<div class="container-fluid">
	<h3>Subject Loading<small class="text-muted fs-6"> ( <b><%=codigoAlumno%></b> - <%=nombreAlumno%> | <b>Load:</b>
	<%=cargaId%> - <b>Block:</b> <%=bloqueId%> - <%=bloqueNombre%> - <%=mapaPlan.getPlanId()%> - <%=ingreso.equals("N")?"FY":"RE"%>)</small>
	</h3>
	<form id="frmCosto" name="frmCosto" action="grabarPagare" method="post">
		<input type="hidden" id="CargaId" name="CargaId" value="<%=cargaId%>">
		<input type="hidden" id="BloqueId" name="BloqueId" value="<%=bloqueId%>">
		<input type="hidden" id="TotMat" name="TotMat" value="<%=totMatricula%>">
		<input type="hidden" id="TotEns" name="TotEns" value="<%=costoEns%>">
		<input type="hidden" id="TotDes" name="TotDes" value="<%=totDescuentos%>">
		<input type="hidden" id="CobroMatricula" name="CobroMatricula" value="N">
				
		<div class="d-flex align-items-center alert <%=existePagare?"alert-info":"alert-danger"%>">
			<a class="btn btn-primary btn-sm" href="materias"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
<!-- 			Enrollment Fee:  -->
<!-- 			<select id="CobroMatricula" name="CobroMatricula" class="form-select" style="width:80px;"> -->
<%-- 				<option value="S" <%=calAlumno.getCobroMatricula().equals("S")?"selected":""%>>YES</option> --%>
<%-- 				<option value="N" <%=calAlumno.getCobroMatricula().equals("N")?"selected":""%>>NO</option> --%>
<!-- 			</select> -->
			&nbsp;&nbsp;	
			Number of Payments:&nbsp;	
			<select id="NumPagare" name="NumPagare" class="form-select" style="width:170px;">
<% 			int num=1;
			for (CalPagare pago : lisPagares){
				if(num == 1){%>
				<option value="1" <%=calAlumno.getNumPagare().equals("1")?"selected":""%>>One-time payment</option>
<%				}else{ %>
				<option value="<%=num%>" <%=calAlumno.getNumPagare().equals(String.valueOf(num))?"selected":""%>><%=num%> Installments</option>
<%				}
				num++;
			} %>															
			</select>&nbsp;&nbsp;
			Balance:
			<input type="text" class="form-control" style="width: 100px;" id="Saldo" name="Saldo" value="<%=calAlumno.getSaldo()%>">&nbsp;&nbsp;
			Percentage:
			<input type="text" class="form-control" style="width: 70px;" id="Porcentaje" name="Porcentaje" value="<%=calAlumno.getPorcentaje()%>">&nbsp;&nbsp;
			<a onclick="grabarPagare()" class="btn btn-primary btn-sm"><i class="fas fa-save"></i></a>&nbsp;&nbsp;
<% 		if(calAlumno.getConfirmar().equals("S")){%>
			<a onclick="quitarConfirmacion('<%=calAlumno.getCodigoPersonal()%>')" class="btn btn-warning btn-sm" title="Delete confirmation"><i class="fas fa-times"></i> Confirmation</a>
<% 		}%>
		</div>	
	</form>
<% 	if(mensaje.equals("1")){%>
	<div class="d-flex align-items-center alert alert-success">
		Saved
	</div>
<% 	}else if(mensaje.equals("2")){%>
	<div class="d-flex align-items-center alert alert-danger">
	Error saving
	</div>
<% 	}%>
	<div class="row">
		<div class="col-md-6">
			<h4> Concepts </h4>							
			<table class="table table-sm table-bordered">
				<thead class="table-info">
					<tr>
						<th style="text-align:center">#</th>
						<th>Name</th>
						<th style="text-align:center">Debit</th>
						<th style="text-align:center">Credit</th>
						<th>Comment</th>
					</tr>
				</thead>
				<tbody>		
<%	
		int row = 0;
		for (CalCosto costo : lisCostos){			
			row++;
			
			float credito 	= 0;
			float cargo 	= 0; 
			if (costo.getTipo().equals("D")) cargo 		= Float.valueOf(costo.getImporte());
			if (costo.getTipo().equals("C")) credito 	= Float.valueOf(costo.getImporte());
			
			String conceptoNombre = "-";
			if (mapaConceptos.containsKey(costo.getConceptoId())){
				conceptoNombre = mapaConceptos.get(costo.getConceptoId()).getConceptoNombre();
			}
		
			boolean envioConcepto =  false;
			if (mapaConceptoMovimientos.containsKey(costo.getConceptoId())){
				envioConcepto = true;
			}
			
			if(!envioConcepto){%>
					<tr>
						<td>
							<%=row%>
<%						if(existePagare && !envioConcepto){%>
							<a href="grabarMovimiento?ConceptoId=<%=costo.getConceptoId()%>&Importe=<%=costo.getImporte()%>&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>&Tipo=<%=costo.getTipo()%>" class="btn btn-primary btn-sm" title="Add Movement"><i class="fas fa-plus"></i></a>
<%						}%>
						</td>
						<td><%=conceptoNombre%></td>				
						<td><%=formato.format(cargo)%></td>
						<td><%=formato.format(credito)%></td>
						<td><%=costo.getComentario()%></td>
					</tr>
<%			}	
		} %>	
				</tbody>		
			</table>
			<br>
			<form id="formDescuentos" name="formDescuentos" action="grabarDescuento" method="post">
				<input type="hidden" name="CargaId" value="<%=cargaId%>">
				<input type="hidden" name="BloqueId" value="<%=bloqueId%>">
				<input type="hidden" name="Tipo" value="C">		
				<h4>Scholarships and Discounts</h4>
				<div class="alert alert-info d-flex align-items-center">
					<select name="ConceptoId" class="form-select" style="width:170px;">
				<% 	int numero=1;
					for (CalConcepto descuento : lisDescuentos){
						numero++;
				%>
						<option value="<%=descuento.getConceptoId()%>"><%=descuento.getConceptoNombre()%></option>
				<%	} %>															
					</select>&nbsp;&nbsp;
					<label>Amount</label>&nbsp;
					<input type="text" style="width:120px;" class="form-control" name="Importe">&nbsp;&nbsp;
					<a onclick="grabarDescuento()" class="btn btn-primary btn-sm"><i class="fas fa-save"></i></a>
				</div>
			</form>
			<br>
			<h4> Transactions </h4>
			<table class="table table-sm table-bordered">
				<thead class="table-info">
					<tr>
						<th width="5%" class="text-center">&nbsp;</th>
						<th width="5%" class="text-center">#</th>
						<th width="70%">Concept</th>
						<th width="10%" class="text-end">Debit</th>
						<th width="10%" class="text-end">Credit</th>
					</tr>
				</thead>
				<tbody>		
<%	
		row = 0;
		for (CalMovimiento movimiento : listaMovimientos){
			row++;
			
			String conceptoNombre = "-";
			if (mapaConceptos.containsKey(movimiento.getConceptoId())){
				conceptoNombre = mapaConceptos.get(movimiento.getConceptoId()).getConceptoNombre();
			}		
			
			float movCargo 		= 0;
			float movCredito 	= 0;
			if (movimiento.getTipo().equals("D")){ 
				movCargo 		= Float.valueOf(movimiento.getImporte());				 
			}
			if (movimiento.getTipo().equals("C")) movCredito 	= Float.valueOf(movimiento.getImporte());	
			
%>
					<tr>
						<td class="text-center">					
							<a onclick="borrar('<%=movimiento.getMovimientoId()%>')" title="Delete movement"><i class="fas fa-times-circle" style="color:red"></i></a>				
						</td>
						<td class="text-center"><%=row%></td>
						<td><%=conceptoNombre%></td>				
						<td class="text-end"><%=formato.format(movCargo)%></td>
						<td class="text-end"><%=formato.format(movCredito)%></td>
					</tr>	
<%		} %>	
				</tbody>		
			</table>			
		</div>	
		<div class="col-md-6">
			<h4> Movement Concept</h4>
			<table class="table table-sm table-bordered">
				<thead class="table-info">
					<tr>
						<th width="10%" class="text-center">#</th>
						<th width="70%">Concept</th>
						<th width="10%" class="text-end">Debit</th>
						<th width="10%" class="text-end">Credit</th>
					</tr>
				</thead>
				<tbody>			
<% 		
		float sumaCreditos=0; float sumaCargos = 0;
		int rowCostos = 0;
// 		if(calAlumno.getCobroMatricula().equals("S")){
// 			rowCostos++;
// 			sumaCargos += costoMat;
%>			
<!-- 					<tr> -->
<%-- 						<td class="text-center"><%=rowCostos%></td> --%>
<!-- 						<td>Enrollment</td> -->
<%-- 						<td class="text-end"><%=formato.format(costoMat)%></td> --%>
<!-- 						<td class="text-end">&nbsp;</td> -->
<!-- 					</tr> -->
<%		
// 		}
%>		
<%		
// 		rowCostos++;
// 		sumaCargos += costoEns;
%>			
<!-- 					<tr> -->
<%-- 						<td class="text-center"><%=rowCostos%></td> --%>
<!-- 						<td>Tuition</td> -->
<%-- 						<td class="text-end"><%=formato.format(costoEns)%></td> --%>
<!-- 						<td class="text-end">&nbsp;</td> -->
<!-- 					</tr> -->
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
				pagoInicial		+= movCargo;
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
		if (ingreso.equals("R")){
			pagoInicial = 0;			
		}	
%>	
					<tr class="table-secondary">
						<td class="text-end" colspan="2"><b>Sub total</b></td>								
						<td class="text-end"><%=formato.format(sumaCargos)%></td>
						<td class="text-end"><%=formato.format(sumaCreditos)%></td>
					</tr>
					<tr class="table-info">
						<td class="text-end" colspan="2"><b>Total Balance Due</b></td>								
						<td class="text-end"><%=saldoCargo==0?"":formato.format(saldoCargo)%></td>
						<td class="text-end"><%=saldoCredito==0?"":formato.format(saldoCredito)%></td>
					</tr>
				</tbody>
			</table>
			<h4> Installments </h4>
		<form name="formModificarPagare" action="modificarPagare" method="post">
			<input type="hidden" id="CargaId" name="CargaId" value="<%=cargaId%>">
			<input type="hidden" id="BloqueId" name="BloqueId" value="<%=bloqueId%>">			
			<table class="table table-sm table-bordered">
				<thead class="table-info">
					<tr>
						<th width="10%" class="text-center">#</th>
						<th width="70%">Installment</th>
						<th width="10%" class="text-end">Date</th>
						<th width="10%" class="text-end">Deposit</th>								
					</tr>
				</thead>
				<tbody>
<% 			row = 1;
			for(CalPagareAlumno pagareAlumno : listaParageAlumno){
%>
					<tr> 
						<td><%=row%></td>
						<td><%=pagareAlumno.getPagareNombre()%></td>
						<td>
							<input id="Fecha" data-date-format="dd/mm/yyyy" class="form-control" type="text" style="width: 120px;" name="Fecha<%=pagareAlumno.getPagareId()%>" value="<%=pagareAlumno.getFecha()%>">
						</td>
						<td align="right">
							<input class="form-control" type="text" style="width: 120px;" name="<%=pagareAlumno.getPagareId()%>" value="<%=pagareAlumno.getImporte()%>">
						</td>
					</tr>
<% 				
				row++;
			}%>
				</tbody>
			</table>
<% 		if(listaParageAlumno.size() >= 1){%>	
			<button type="submit" class="btn btn-primary btn-sm" title="Grabar"><i class="fas fa-save"></i></button><br>	
<% 		}%>	<br>	
		</form>
		</div>
	</div>	
</div>
</body>
<script>
jQuery('#Fecha').datepicker();
</script>
</html>