<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Inscritos" class="aca.vista.Inscritos" scope="page"/>
<jsp:useBean id="BecAcuerdoU" class="aca.bec.BecAcuerdoUtil" scope="page"/>
<jsp:useBean id="BecAcuerdo" class="aca.bec.BecAcuerdo" scope="page"/>
<jsp:useBean id="AfeAcuerdos" class="aca.afe.FesCcAfeAcuerdos" scope="page"/>
<jsp:useBean id="AfeAcuerdosU" class="aca.afe.FesCcAfeAcuerdosUtil" scope="page"/>
<jsp:useBean id="becAcuerdoAdicional" class="aca.bec.BecAcuerdo" scope="page"/>
<jsp:useBean id="becAcuerdoU"  class="aca.bec.BecAcuerdoUtil" scope="page"/>
<jsp:useBean id="becTipoU"  class="aca.bec.BecTipoUtil" scope="page"/>
<jsp:useBean id="becAccesoU"  class="aca.bec.BecAccesoUtil" scope="page"/>
<jsp:useBean id="becTipo" class="aca.bec.BecTipo" scope="page"/>

<head>
</head>
<%
	String idEjercicio 		= (String) session.getAttribute("ejercicioId");
	String codigo			= (String)session.getAttribute("codigoPersonal");
	boolean admin		= aca.acceso.AccesoUtil.getBecas(conEnoc, codigo);
	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String puestoId 		= request.getParameter("PuestoId")==null?"":request.getParameter("PuestoId");
	String horas  			= request.getParameter("Horas")==null?"":request.getParameter("Horas");	
	boolean inscrito    	= false;
	java.text.DecimalFormat getformato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	ArrayList<aca.bec.BecTipo> tipos = becTipoU.getListAcuerdo(conEnoc, idEjercicio, " 'O','M','P', 'E' ", "");
	ArrayList<aca.bec.BecAcceso> acceso = becAccesoU.getListUsuario(conEnoc, codigo, "");
	
	String fallback 		= "";
	boolean mostrar = false;
	loop:
		for(aca.bec.BecTipo tipo: tipos){
			for(aca.bec.BecAcceso acces: acceso){
				if(tipo.getDepartamentos()==null)continue;
				
				if( tipo.getDepartamentos().contains("-"+acces.getIdCcosto())){
					if(fallback.equals("")){
						fallback = tipo.getTipo();
					}
					mostrar = true;
					break loop;
				}
			}
		}
		
		if(admin && tipos.size()>0){
			mostrar = true;
			fallback = tipos.get(0).getTipo();
		}
		
		String tipoId = request.getParameter("tipo")==null?fallback:request.getParameter("tipo");
	
	// Actualizar el número de horas	
	java.util.HashMap<String, aca.financiero.ContCcosto> centroCostos = aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, idEjercicio);
	ArrayList<aca.bec.BecAcuerdo> listAlumno = becAcuerdoU.getContratoAlumnosVigentes(conEnoc, tipoId, idEjercicio, "ORDER BY CODIGO_PERSONAL");	
%>
<style>
 	body{
 		background : white;
 	}
 </style>
<body>
<div class="container-fluid">
	<h2>Tipos de contratos</h2>
	<form action="contratoTipos" name="forma" method="post">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a> &nbsp;&nbsp;&nbsp;
	<%if(mostrar){ %>			
		<select name="tipo" id="tipo" style="width:300px;" onchange="document.forma.submit()">
	<%
				for(aca.bec.BecTipo tipo: tipos){
					if(tipo.getDepartamentos()==null)continue;
					boolean permiso = false;
					for(aca.bec.BecAcceso acces: acceso){
						if( tipo.getDepartamentos().contains("-"+acces.getIdCcosto()) ){
							permiso = true;
							break;
						}
					}
					
					if(permiso == false && admin != true)continue;
					
	%>
				<option value="<%=tipo.getTipo() %>" <%if(tipoId.equals(tipo.getTipo()))out.print("selected"); %>><%=tipo.getNombre() %></option>
	<%
			}
	%>
		</select>&nbsp; &nbsp;
			
			<%} %>		
	</div>
	</form>		
	<hr/>		
	<table id="table" class="table table-sm table-bordered">
	<thead>
		<tr>
			<th colspan="13" style="border-color:#bce8f1;background:#d9edf7;text-align:center;">A c a d e m i c o</th>
			<th colspan="2" style="border-color:#fbeed5;background:#fcf8e3;text-align:center;"></th>
			<th colspan="5" style="border-color:#bce8f1;background:#d9edf7;text-align:center;">F i n a n c i e r o</th>
		</tr>
	</thead>
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>
			<th><spring:message code="aca.Folio"/></th>
			<th><spring:message code="aca.Tipo"/></th>
			<th>Tipo Nombre</th>
			<th>Ccosto</th>
			<th>Criterio</th>
			<th>Valor</th>
			<th><spring:message code="aca.Matricula"/></th>
			<th>Enseñanza</th>
			<th>Internado</th>
			<th>Horas</th>			
			<th colspan ="2" style="border-left: 2px solid gray;"></th>				
			<th><spring:message code="aca.Matricula"/></th>
			<th>Enseñanza</th>
			<th>Internado</th>
			<th>Horas</th>
			<th>Beca Neta</th>
		</tr>
	</thead>
	<%
			
			int cont = 0;
			for(aca.bec.BecAcuerdo acuerdo: listAlumno){
				cont++;
				
				boolean registrado = aca.bec.BecAcuerdoUtil.existeAcuerdoMateo(conEnoc, acuerdo.getCodigoPersonal(), acuerdo.getFolio());
				
				AfeAcuerdos = new aca.afe.FesCcAfeAcuerdos();
				AfeAcuerdos.setMatricula(acuerdo.getCodigoPersonal());
				AfeAcuerdos.setAcuerdoFolio(acuerdo.getFolio());
				acuerdo.getFolio();
				AfeAcuerdosU.mapeaRegId(conEnoc);
				
				String criterio="";
				if(acuerdo.getTipoadicional().equals("A")){
					criterio = "Solo Depósitos de Colportaje";					
				}else if(acuerdo.getTipoadicional().equals("B")){
					criterio = "Depósitos de Colportaje y Familia";						
				}else if(acuerdo.getTipoadicional().equals("C")){
					criterio = "Solo Depósitos de Familia";
				}else if(acuerdo.getTipoadicional().equals("D")){
					criterio = "Cantidad Fija";
				}
				String acuerdo2 = acuerdo.getTipo();
				String tipo =  aca.bec.BecTipoUtil.getAcuerdo(conEnoc, idEjercicio, acuerdo2);
				String impMatricula = "0";
				String impEnsenanza = "0";
				String impInternado = "0";
				String TotBecAdic 	= "0";
				impMatricula 	= AfeAcuerdos.getAcuerdoImpMatricula();
				impInternado 	= AfeAcuerdos.getAcuerdoImpInternado();
				impEnsenanza 	= AfeAcuerdos.getAcuerdoImpEnsenanza();
				TotBecAdic		= AfeAcuerdos.getTotalBecaAdic();
				if(impMatricula == null || impMatricula.equals("")){
					impMatricula = "0";
				}
				if(impInternado == null || impInternado.equals("")){
					impInternado = "0";
				}
				if(impEnsenanza == null || impEnsenanza.equals("")){
					impEnsenanza = "0";
				}
				if(TotBecAdic == null || TotBecAdic.equals("")){
					TotBecAdic = "0";
				}
		%>
			<tr>
				<td><%=cont %></td>
				<td><%=acuerdo.getCodigoPersonal()%></td>
				<td><%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, acuerdo.getCodigoPersonal(), "APELLIDO")%></td>
				<td><%=acuerdo.getFolio() %></td>
				<td><%=acuerdo.getTipo() %></td>
				<td><%=aca.bec.BecTipoUtil.getTipoNombre(conEnoc, acuerdo.getTipo(), idEjercicio)  %></td>
				<td><%=acuerdo.getIdCcosto() %> | <%=centroCostos.get(acuerdo.getIdCcosto()).getNombre() %></td>
				<td><%=criterio %></td>
				<td><%=acuerdo.getValor().equals("C")?"Cantidad":"Porcentaje" %></td>
				<td style="text-align:right;"><%=acuerdo.getMatricula() %><%=acuerdo.getValor().equals("C")?"":"%" %></td>
				<td style="text-align:right;"><%=acuerdo.getEnsenanza() %><%=acuerdo.getValor().equals("C")?"":"%" %></td>
				<td style="text-align:right;"><%=acuerdo.getInternado() %><%=acuerdo.getValor().equals("C")?"":"%" %></td>
				<%if((tipo.equals("B")) || (tipo.equals("P"))){ %>
				<td style="text-align:right";><%=acuerdo.getHoras()%></td>	
				<%}else{ %>
				<td style="text-align:right;"><%=acuerdo.getHoras() %></td>	
				<%} %>			
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td style="text-align:right;"><%=getformato.format(Double.parseDouble(impMatricula)) %></td>
				<td style="text-align:right;"><%=getformato.format(Double.parseDouble(impEnsenanza)) %></td>
				<td style="text-align:right;"><%=getformato.format(Double.parseDouble(impInternado)) %></td>
				<td style="text-align:right;"><%=AfeAcuerdos.getAcuerdoHoras() %></td>
				<td style="text-align:right;"><%=getformato.format(Double.parseDouble(TotBecAdic)) %></td>
			</tr>
		<%
			}
		%>
		</table>
</div>
<script type="text/javascript">

	function modificaHoras(PuestoId, Horas){	
		if ( confirm("¿Estás seguro de sincronizar las horas de la beca dicional con las horas de la beca básica?") ){
			document.location.href = "contrato?Accion=1&PuestoId="+PuestoId+"&Horas="+Horas;
		}		
	}
</script>
<%@ include file="../../cierra_enoc.jsp"%>