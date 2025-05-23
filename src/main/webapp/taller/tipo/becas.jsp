<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.bec.spring.BecTipo"%>
<%@ page import="aca.financiero.spring.ContEjercicio"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head></head>
<%	
	java.text.DecimalFormat getformato			= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String idEjercicio							= (String) session.getAttribute("ejercicioId");
	String tipo 								= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");	
	String mensaje 								= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String accion 								= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	List<ContEjercicio> lisEjercicios 			= (List<ContEjercicio>) request.getAttribute("lisEjercicios");
	List<BecTipo> lisTipos						= (List<BecTipo>) request.getAttribute("lisTipos");	
	HashMap<String,String> mapaAcuerdos 		= (HashMap<String,String>)request.getAttribute("mapaAcuerdos");
	HashMap<String,String> mapaPresupuestos 	= (HashMap<String,String>)request.getAttribute("mapaPresupuestos");
	HashMap<String,String> mapaCarreras	 		= (HashMap<String,String>)request.getAttribute("mapaCarreras");
%>
<style>
 	body{
 		background : white;
 	}
</style>
<body>
<div class="container-fluid">
	<h2>Tipo de Becas</h2>
	<form name="forma" action="becas" method="post">	
	<div class="alert alert-info d-flex align-items-center">
		<a href="editar?EjercicioId=<%=idEjercicio %>" class="btn btn-primary"><i class="fas fa-plus"></i></i><spring:message code='aca.Añadir'/></a>&nbsp;
		<select id="EjercicioId" name="EjercicioId" class="form-select" onchange="document.forma.submit();" style="width:120px">
<%	for(ContEjercicio ej : lisEjercicios){%>
			<option value="<%= ej.getIdEjercicio()%>" <%=ej.getIdEjercicio().equals(idEjercicio)?"Selected":"" %>><%=ej.getIdEjercicio()%></option>
<%	} %>
		</select>	
	</div>
	</form>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th width="5%"><spring:message code="aca.Operacion"/></th>
		<th><spring:message code="aca.Tipo"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th class="text-center">Dept.</th>
		<th class="text-center">Carr.</th>
		<th><spring:message code="aca.Cuenta"/></th>
		<th>SunPlus</th>
		<th>Flag</th>
		<th class="text-end">(%)</th>
		<th>Meses</th>
		<th class="text-end">Hr.Univ.</th>
		<th class="text-end">Hr.Prepa</th>
		<th>Acuerdo</th>
		<th class="text-end">Precio</th>
		<th class="text-end">Presupuesto</th>
		<th class="text-end">Limite</th>
		<th>Tipo Al.</th>
		<th class="text-end">Tot.Ac.</th>
		<th class="text-center">Ver</th>
	</tr>
	</thead>
<%			
	for (BecTipo becas : lisTipos){
		
		String acuerdo="";
		if(becas.getAcuerdo().equals("A")){
			acuerdo = "Adicional";					
		}else if(becas.getAcuerdo().equals("B")){
			acuerdo = "Básico";						
		}else if(becas.getAcuerdo().equals("O")){
			acuerdo = "Otro";
		}else if(becas.getAcuerdo().equals("I")){
			acuerdo = "Institucional";
		}else if(becas.getAcuerdo().equals("E")){
			acuerdo = "Ind. Adicional";
		}else if(becas.getAcuerdo().equals("P")){
			acuerdo = "Preindustrial";
		}else if(becas.getAcuerdo().equals("M")){
			acuerdo = "Posgrado";
		}
			
		String depto = "";
		if (becas.getDepartamentos()==null || becas.getDepartamentos().equals("null") || becas.getDepartamentos().equals(""))
			depto = "-";
		else
			depto = becas.getDepartamentos();
		
		String total = "0";
		if (mapaAcuerdos.containsKey( becas.getTipo())){
			total = mapaAcuerdos.get( becas.getTipo());
		}			
		
		String numCarreras = "<span class='badge bg-warning rounded-pill'>0</span>";
		if (mapaCarreras.containsKey( becas.getTipo())){
			numCarreras = "<span class='badge bg-warning rounded-pill'>"+mapaCarreras.get( becas.getTipo())+"</span>";
		}
%>
	<tr>
		<td>
			<a href="editar?EjercicioId=<%=becas.getIdEjercicio()%>&Tipo=<%=becas.getTipo()%>&Ccosto=<%=becas.getDepartamentos()%>&Meses=<%=becas.getMeses()%>" class="fas fa-edit" title="Editar registro"></a>
<%		if(total.equals("0")){%>
			&nbsp;
			<a href="javascript:Borrar('<%=becas.getIdEjercicio()%>','<%=becas.getTipo()%>');" class="fas fa-trash-alt" title="Borrar registro"></a>
<%		} %>			
		</td>
		<td><%=becas.getTipo()%></td>
		<td><%=becas.getNombre()%></td>
		<td title="Modificar centros de costos" style="text-align: center">			  
		    <button class="btn btn-light" onclick="location.href='editarccosto?EjercicioId=<%=idEjercicio%>&Tipo=<%=becas.getTipo()%>&Ccostos=<%=becas.getDepartamentos() %>'"style="cursor:pointer;" onmouseover="this.style.backgroundColor='#E6E6E6';"onmouseout="this.style.backgroundColor='';" type="button"><i class="fas fa-home"></i></button>
		</td>		
		<td class="text-center">
			<a href="editarcarreras?EjercicioId=<%=becas.getIdEjercicio()%>&Tipo=<%=becas.getTipo()%>"><%=numCarreras%></a>
		</td>
		<td><%=becas.getCuenta()%></td>
		<td><%=becas.getCuentaSunplus()%></td>
		<td><%=becas.getFlag()%></td>
		<td style="text-align:right"><%=becas.getPorcentaje()%></td>
		<td title="Modificar meses" onclick="location.href='editarmeses?Tipo=<%=becas.getTipo()%>&Meses=<%=becas.getMeses()%>&Nombre=<%=becas.getNombre()%>&EjercicioId=<%=idEjercicio%>'"style="cursor:pointer;" onmouseover="this.style.backgroundColor='#E6E6E6';"onmouseout="this.style.backgroundColor='';"><%=becas.getMeses()==null?"-":becas.getMeses()%></td>
		<td style="text-align:right"><%=becas.getHoras()%></td>
		<td style="text-align:right"><%=becas.getHorasPrepa()%></td>
		<td><%=acuerdo%></td>
		<td style="text-align:right"><%=becas.getImporte()%></td>		
<%			
			String meses = becas.getMeses();
			if(meses != null && !meses.equals("null")){
				meses = meses.substring(1);
				meses = meses.replace('-', ',');
			}else{
				meses = "";
			}
			
			double presupuesto = 0;
			String arregloMeses[] = meses.split(",");
			for (String mes : arregloMeses){
				if (mapaPresupuestos.containsKey(becas.getCuenta()+mes)){
					presupuesto += Double.valueOf(mapaPresupuestos.get(becas.getCuenta()+mes));
				}				 
			}
			
			double presupuestoNeto =  presupuesto * Double.valueOf(becas.getPorcentaje()) / 100;
			
			String limite = becas.getLimite();
%>
		<td style="text-align:right"><%=getformato.format(presupuestoNeto)%></td>		
		<td style="text-align:right"><%=getformato.format(Double.parseDouble(limite))%></td>		
		<td title="Modificar tipo de alumnos" style="text-align: center">			  
		    <button class="btn btn-light" onclick="location.href='editartipo?Tipo=<%=becas.getTipo()%>&Ccostos=<%=becas.getDepartamentos() %>&Nombre=<%=becas.getNombre() %>&EjercicioId=<%=idEjercicio%>'"style="cursor:pointer;" onmouseover="this.style.backgroundColor='#E6E6E6';"onmouseout="this.style.backgroundColor='';" type="button"><i class="fas fa-user"></i></button>
		</td>
		<td class="text-end"><span class="badge bg-dark"><%=total%></span></td>
		<td class="text-center"><%=becas.getMostrar().equals("S")?"<span class='badge bg-dark rounded-pill'>SI</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>
	</tr>
<%
		}		
%>
	</table>
	</div>
</body>
<script type="text/javascript" >
	function Borrar( ejercicioId, tipo) {
		if (confirm("Estás seguro que deseas eliminar la beca?") == true) {
			document.location.href = "borrar?EjercicioId="+ejercicioId+"&Tipo="+tipo;
		}
	}
</script>
</html>