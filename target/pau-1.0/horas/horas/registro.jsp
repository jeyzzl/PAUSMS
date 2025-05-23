<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.emp.spring.EmpTipoPago"%>
<%@page import="aca.emp.spring.EmpContrato"%>
<%@page import="aca.emp.spring.EmpConfirmar"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%		
	String codigoPersonal 		= (String)session.getAttribute("codigoPersonal");
	String cargaId 				= (String)session.getAttribute("cargaId");
	String carreraId 			= (String)session.getAttribute("carreraId");
	String tipo					= (String) request.getAttribute("tipo");
	boolean periodoActivo		= (boolean)request.getAttribute("periodoActivo");
	boolean esDirector			= (boolean)request.getAttribute("esDirector");
	String nombreCarrera		= (String)request.getAttribute("nombreCarrera");
	
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<aca.emp.spring.EmpHoras> lisHoras			= (List<aca.emp.spring.EmpHoras>)request.getAttribute("listaHoras");
	HashMap<String,String> mapMaestros				= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,String> mapEstados				= (HashMap<String,String>)request.getAttribute("mapaEstados");
	HashMap<String,String> mapMaterias				= (HashMap<String,String>)request.getAttribute("mapaMaterias");
	HashMap<String,EmpTipoPago> mapaPagos 			= (HashMap<String,EmpTipoPago>)request.getAttribute("mapaPagos");
	HashMap<String,EmpContrato> mapaContratos		= (HashMap<String,EmpContrato>)request.getAttribute("mapaContratos");
	HashMap<String,EmpConfirmar> mapaConfirmados	= (HashMap<String,EmpConfirmar>)request.getAttribute("mapaConfirmados");
	
	java.text.DecimalFormat frmDecimal				= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
%>
<div class="container-fluid">
	<h2>Maestros por horas<small class="text-muted fs-5">( <%=cargaId%> - <%=nombreCarrera%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="elegir?Tipo=<%=tipo%>"><i class="fas fa-arrow-left"></i></a>&nbsp; &nbsp;
<%	if ( (periodoActivo && (esDirector || codigoPersonal.equals("9800078") || codigoPersonal.equals("9800946"))) || codigoPersonal.equals("9800660") || codigoPersonal.equals("9801085") || codigoPersonal.equals("9800308") || codigoPersonal.equals("9800889")){%>
		<a class="btn btn-primary" href="accion"><i class="icon-white icon-plus"></i> <spring:message code='aca.Añadir'/></a>&nbsp;&nbsp;
<%	}%>	
		<input type="text" class="form-control" style="width:200px" placeholder="Buscar..." id="buscar">&nbsp; &nbsp;
		<a href="javascript:document.frmHoras.submit();" class="btn btn-primary"><i class="fas fa-save"></i> VoBo</a>
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<% 	}%>	
	<form name="frmHoras" action="confirmar" method="post">
	<table class="table table-bordered table-striped" id="table">
	<thead>
		<tr class="table-info">
			<th width="3%" style="font-size:0.8rem">Op.</th>
			<th width="2%" style="font-size:0.8rem"><spring:message code="aca.Numero"/></th>
			<th width="2%" style="font-size:0.8rem"><i class="fas fa-save"></i></th>
			<th width="4%" style="font-size:0.8rem">Contrato</th>	
			<th width="3%" style="font-size:0.8rem">Codigo</th>
			<th width="7%" style="font-size:0.8rem">Maestro</th>
			<th width="15%" style="font-size:0.8rem">Materia</th>
			<th width="10%" style="font-size:0.8rem">Otra Materia</th>
			<th width="5%" style="font-size:0.8rem">Tipo</th>
			<th width="3%" style="font-size:0.8rem">Blq.</th>
			<th width="5%" style="font-size:0.8rem">Edo.</th>
			<th width="3%" style="font-size:0.8rem">Pago</th>
			<th width="4%" style="font-size:0.8rem">F. Inicio</th>
			<th width="4%" style="font-size:0.8rem">F. Fin</th>
			<th width="2%" class="text-end" style="font-size:0.8rem">F.S.</th>
			<th width="2%" class="text-end" style="font-size:0.8rem">Sem.</th>
			<th width="3%" class="text-end" style="font-size:0.8rem">Precio</th>
			<th width="4%" class="text-end" style="font-size:0.8rem">Tot/Sem.</th>
			<th width="4%" class="text-end" style="font-size:0.8rem">Tot/Per.</th>
			<th width="8%" style="font-size:0.8rem">
	<%		
		if (codigoPersonal.equals("9800660")||codigoPersonal.equals("9800308")){
	%>		
				<a href="javascript:EstadoCarrera('S')"><span class="badge bg-warning">S</span></a>&nbsp;
				<a href="javascript:EstadoCarrera('A')"><span class="badge bg-dark">A</span></a>&nbsp;
				<a href="javascript:EstadoCarrera('N')"><span class="badge bg-success">N</span></a>
	<% 	}else{
			out.print("Estados");
		}
	%>		
			</th>
		</tr>
	</thead>	
<%
	int row=0;
	for (aca.emp.spring.EmpHoras horas : lisHoras){
		row++;
		String nombreMaestro = "-";
		if (mapMaestros.containsKey(horas.getCodigoPersonal())){
			nombreMaestro = mapMaestros.get(horas.getCodigoPersonal());
		}
		
		String nombreMateria = "-";
		if (mapMaterias.containsKey(horas.getCursoId())){
			nombreMateria = mapMaterias.get(horas.getCursoId());
		}
		
		float totSemana 	= Float.valueOf(horas.getPrecio()) * Float.valueOf(horas.getFrecuencia());
		float totPeriodo 	= totSemana * Float.valueOf(horas.getSemanas());
		
		String tipoNombre 	= "-";
		if (horas.getTipo().equals("O")){
			tipoNombre = "Otoño";
		}else if (horas.getTipo().equals("V")){
			tipoNombre = "Verano";
		}else if (horas.getTipo().equals("I")){
			tipoNombre = "Invierno";
		}else if (horas.getTipo().equals("P")){
			tipoNombre = "Primavera";
		}else{
			tipoNombre = "-";
		}
		
		String tipoPago = "*";
		if (mapaPagos.containsKey(horas.getTipoPagoId())){
			tipoPago = mapaPagos.get(horas.getTipoPagoId()).getCorto();
		}
		
		String pago = "A";
		if (mapaContratos.containsKey(horas.getContratoId())){
			pago = mapaContratos.get(horas.getContratoId()).getEstado();
		}
		
		if (pago.equals("A")) pago = "<span class='badge bg-warning'>Alta</span>"; 
		else if (pago.equals("F")) pago = "<span class='badge bg-success'>Firma</span>";
		else if (pago.equals("P")) pago = "<span class='badge bg-dark'>Pagado</span>";	
		
		String confirmar 	= "N";
		String datos		= "-"; 
		if (mapaConfirmados.containsKey(horas.getCodigoPersonal()+horas.getFolio())){
			confirmar 	= "S";
			datos 		= "Usuario:"+mapaConfirmados.get(horas.getCodigoPersonal()+horas.getFolio()).getUsuario()+" Fecha:"+mapaConfirmados.get(horas.getCodigoPersonal()+horas.getFolio()).getFecha(); 
		}
		
		String estado 		= "I";		
		if (mapEstados.containsKey(horas.getCodigoPersonal())){
			estado = mapEstados.get(horas.getCodigoPersonal());			
		}
		
		String colorCodigo	= "";
		if (estado.equals("I")) colorCodigo = "<span class='badge bg-warning rounded-pill'>"+horas.getCodigoPersonal()+"<span>"; else colorCodigo = horas.getCodigoPersonal();
%>
		<tr>
			<td style="font-size:0.8rem">
<%		
		if ( ( horas.getEstado().equals("S") && (esDirector || codigoPersonal.equals("9800078") || codigoPersonal.equals("9800946"))) || codigoPersonal.equals("9800660") || codigoPersonal.equals("9801091") || codigoPersonal.equals("9800308")){
%>			
				<a class="fas fa-trash-alt" href="javascript:Borrar('<%=horas.getCodigoPersonal()%>','<%=horas.getFolio()%>')"></a>&nbsp;
				<a href="accion?CodigoEmpleado=<%=horas.getCodigoPersonal()%>&Folio=<%=horas.getFolio()%>&Accion=1" title="Modificar"><i class="fas fa-pencil-alt"></i></a>
<%		} %>				
			</td>
			<td style="font-size:0.8rem"><%=row%></td>
			<td style="font-size:0.8rem">
<%		if (!horas.getEstado().equals("S")){%>			
				<span data-bs-toggle="tooltip" data-bs-placement="right" title="<%=datos%>">
					<input name="<%=horas.getCodigoPersonal()+"-"+horas.getFolio()%>" type="checkbox" value="S" <%=confirmar.equals("S")?"checked":""%>/>
				</span>
<%		}else{%>
				<span><i class="fas fa-exclamation-circle"></i></span>
<%		} %>				
			</td>
			<td style="font-size:0.8rem"><%= horas.getContratoId()%></td>
			<td style="font-size:0.8rem"><%= colorCodigo%></td>
			<td style="font-size:0.8rem"><%= nombreMaestro%></td>
			<td style="font-size:0.8rem"><%= nombreMateria %></td>
			<td style="font-size:0.8rem"><%= horas.getMateria() %></td>
			<td style="font-size:0.8rem"><%= tipoNombre%></td>
			<td style="font-size:0.8rem"><%= horas.getBloqueId() %></td>
			<td style="font-size:0.8rem"><%= tipoPago%></td>
			<td style="font-size:0.8rem"><%= pago%></td>
			<td style="font-size:0.8rem"><%= horas.getFechaIni() %></td>
			<td style="font-size:0.8rem"><%= horas.getFechaFin() %></td>
			<td class="text-end" style="font-size:0.8rem"><%= horas.getFrecuencia() %></td>
			<td class="text-end" style="font-size:0.8rem"><%= horas.getSemanas() %></td>
			<td class="text-end" style="font-size:0.8rem"><%= horas.getPrecio() %></td>
			<td class="text-end" style="font-size:0.8rem"><%= frmDecimal.format(totSemana) %></td>
			<td class="text-end" style="font-size:0.8rem"><%= frmDecimal.format(totPeriodo) %></td>
			<td style="font-size:0.8rem">
<%		
		String etiqueta = "badge bg-dark";
		if (horas.getEstado().equals("S")) 
			etiqueta = "badge bg-warning";
		else if (horas.getEstado().equals("N")) 
			etiqueta = "badge bg-success";
%>		
		<span class= "<%=etiqueta%>"><%=horas.getEstado()%></span> &nbsp;
<%		
		if (codigoPersonal.equals("9800660")||codigoPersonal.equals("9800308")){
%>
			<a href="javascript:Estado('<%=horas.getCodigoPersonal()%>','<%=horas.getFolio()%>','S')">[S]</a>&nbsp;
			<a href="javascript:Estado('<%=horas.getCodigoPersonal()%>','<%=horas.getFolio()%>','A')">[A]</a>&nbsp;
			<a href="javascript:Estado('<%=horas.getCodigoPersonal()%>','<%=horas.getFolio()%>','N')">[N]</a>
<%		} %>									
			</td>
		</tr>
<%	} %>		
	</table>
	</form>	
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	function Borrar(codigoEmpleado, folio){
		if (confirm("¿Estás seguro de borrar este registro?")){
			document.location.href="borrar?CodigoEmpleado="+codigoEmpleado+"&Folio="+folio;
		}	
	}
	
	function Estado(codigoEmpleado, folio, estado){	
		document.location.href="estado?CodigoEmpleado="+codigoEmpleado+"&Folio="+folio+"&Estado="+estado;		
	}
	
	function EstadoCarrera(estado){	
		document.location.href="estadoCarrera?Estado="+estado;	
	}
	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>	
<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();  		
	});
</script>
