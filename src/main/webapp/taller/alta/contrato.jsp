<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "java.text.DecimalFormat"%>
<%@ page import= "aca.financiero.spring.ContCcosto"%>
<%@ page import= "aca.afe.spring.FesCcAfeAcuerdos"%>
<%@ page import= "aca.bec.spring.BecTipo"%>
<%@ page import= "aca.bec.spring.BecAcuerdo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
</head>
<%
	DecimalFormat getformato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	String idEjercicio 			= (String) session.getAttribute("ejercicioId");
	String codigoPersonal 		= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno			= (String) request.getAttribute("nombreAlumno");
	boolean inscrito    		= (boolean) request.getAttribute("inscrito");
	
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String puestoId 			= request.getParameter("PuestoId")==null?"":request.getParameter("PuestoId");
	String horas  				= request.getParameter("Horas")==null?"":request.getParameter("Horas");
	
	double matricula 			= 0.0;
	double ensenanza 			= 0.0;
	double internado 			= 0.0;
	double total 				= 0.0;	
	//BecAcuerdoU.maximoReg(conEnoc, codigoPersonal);
	
	List<BecAcuerdo> lisAcuerdos 					= (List<BecAcuerdo>) request.getAttribute("lisAcuerdos");
	HashMap<String,ContCcosto> mapaCentroCostos 	= (HashMap<String,ContCcosto>) request.getAttribute("mapaCentroCostos");
	HashMap<String,FesCcAfeAcuerdos> mapaAcuerdos	= (HashMap<String,FesCcAfeAcuerdos>) request.getAttribute("mapaAcuerdos");
	HashMap<String,BecTipo> mapaTipos				= (HashMap<String,BecTipo>) request.getAttribute("mapaTipos");	
%>
<style>
 	body{
 		background : white;
 	}
 </style>
<body>
<div class="container-fluid">		
	<h2>Actualización de Contratos <small class="text-muted fs-4"><%=codigoPersonal%> | <%=nombreAlumno%></small></h2>
<%	if(!inscrito){ %>
		<div class="alert alert-danger">
			Este Alumno: <%=codigoPersonal %> no está inscrito
		</div>
<%
	}else{//SI ESTA INSCRITO ENTONCES MOSTRAR INFORMACION DE SUS ACUERDOS O CONTRATOS		
%>
		<hr />
		
		<table class="table table-condensed table-fontsmall">
			<tr>
				<th colspan="11" style="border-color:#bce8f1;background:#d9edf7;text-align:center;">A c a d e m i c o</th>
				<th colspan="2" style="border-color:#fbeed5;background:#fcf8e3;text-align:center;"></th>
				<th colspan="5" style="border-color:#bce8f1;background:#d9edf7;text-align:center;">F i n a n c i e r o</th>
			</tr>
			<tr>
				<th>#</th>
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
				
				<th style="border-left: 1px solid gray;">Mateo</th>
				<th style="border-right: 1px solid gray;">Alta</th>
				
				<th><spring:message code="aca.Matricula"/></th>
				<th>Enseñanza</th>
				<th>Internado</th>
				<th>Horas</th>
				<th>Beca Neta</th>
			</tr>
	<%		
		int cont = 0;
		for(BecAcuerdo acuerdo : lisAcuerdos){
			
			cont++;
			boolean registrado 		= false;
			FesCcAfeAcuerdos afeAcuerdos = new FesCcAfeAcuerdos();
			if (mapaAcuerdos.containsKey(acuerdo.getCodigoPersonal()+acuerdo.getFolio()+acuerdo.getPuestoId())){
				registrado 			= false;
				afeAcuerdos 		= mapaAcuerdos.get(acuerdo.getCodigoPersonal()+acuerdo.getFolio()+acuerdo.getPuestoId());
			}			
			//System.out.println("Datos:"+acuerdo.getCodigoPersonal()+":"+acuerdo.getFolio()+":"+registrado);			
			
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
			
			String acuerdo2 	= acuerdo.getTipo();
			String tipo 		= "B";
			String tipoNombre 	= "-"; 
			if (mapaTipos.containsKey(acuerdo.getTipo())){
				tipo = mapaTipos.get(acuerdo.getTipo()).getAcuerdo();
				tipoNombre = mapaTipos.get(acuerdo.getTipo()).getNombre();
			}
				
		%>
			<tr>
				<td><%=cont %></td>
				<td><%=acuerdo.getFolio() %></td>
				<td><%=acuerdo.getTipo() %></td>
				<td><%=tipoNombre%></td>
				<td><%=acuerdo.getIdCcosto() %> | <%=mapaCentroCostos.get(acuerdo.getIdCcosto()).getNombre() %></td>
				<td><%=criterio %></td>
				<td><%=acuerdo.getValor().equals("C")?"Cantidad":"Porcentaje" %></td>
				<td style="text-align:right;"><%=acuerdo.getMatricula() %><%=acuerdo.getValor().equals("C")?"":"%" %></td>
				<td style="text-align:right;"><%=acuerdo.getEnsenanza() %><%=acuerdo.getValor().equals("C")?"":"%" %></td>
				<td style="text-align:right;"><%=acuerdo.getInternado() %><%=acuerdo.getValor().equals("C")?"":"%" %></td>
				<%if( tipo.equals("B") || tipo.equals("I") || tipo.equals("P") ){ %>
				<td style="text-align:right";>
				<a href="javascript:modificaHoras('<%=acuerdo.getPuestoId()%>','<%=acuerdo.getHoras()%>');"> <%=acuerdo.getHoras()%> </a>
				</td>	
				<%}else{ %>
				<td style="text-align:right;"><%=acuerdo.getHoras() %></td>	
				<%} %>			
				<td style="border-left: 1px solid gray;text-align:center;"><%= registrado==true?"<i class='icon icon-ok'>":"<i class='fas fa-trash-alt'>" %></td>
				<td style="border-right: 1px solid gray;text-align:center;">
				<%
				if(acuerdo.getPuestoId()!=null){
				%>
					<%if(!registrado){ %>
						<a href="registrar?folio=<%=acuerdo.getFolio() %>&PuestoId=<%=acuerdo.getPuestoId()%>" class="btn btn-sm btn-primary"><i class="fas fa-arrow-up"></i></a>
					<%}else{%>
						<a href="registrar?folio=<%=acuerdo.getFolio() %>&PuestoId=<%=acuerdo.getPuestoId()%>" class="btn btn-sm btn-info"><i class="fas fa-edit"></i></a>
					<%} %>
				<%
					}
				
				if (registrado){
					matricula += Double.parseDouble(afeAcuerdos.getAcuerdoImpMatricula());
					ensenanza += Double.parseDouble(afeAcuerdos.getAcuerdoImpEnsenanza());
					internado += Double.parseDouble(afeAcuerdos.getAcuerdoImpInternado());
					total     += Double.parseDouble(afeAcuerdos.getTotalBecaAdic());
				}	
				
		%>
				</td>				
				<td style="text-align:right;"><%=afeAcuerdos.getAcuerdoImpMatricula() %></td>
				<td style="text-align:right;"><%=afeAcuerdos.getAcuerdoImpEnsenanza() %></td>
				<td style="text-align:right;"><%=afeAcuerdos.getAcuerdoImpInternado() %></td>
				<td style="text-align:right;"><%=afeAcuerdos.getAcuerdoHoras() %></td>
				<td style="text-align:right;"><%=afeAcuerdos.getTotalBecaAdic() %></td>
			</tr>
		<%
		}			
		%>
			<tr>
				<td colspan="13"><center><h3>Totales</h3></center></td>
				<td style="text-align:right;"><%= getformato.format(matricula)%></td>
				<td style="text-align:right;"><%= getformato.format(ensenanza)%></td>
				<td style="text-align:right;"><%= getformato.format(internado)%></td>
				<td>&nbsp;</td>
				<td style="text-align:right;"><%= getformato.format(total)%></td>
			</tr>
		</table>
<%
	}
%>	
</div>
<script type="text/javascript">
	function modificaHoras(PuestoId, Horas){		
		if ( confirm("¿Estás seguro de sincronizar las horas de la beca dicional con las horas de la beca básica?") ){
			document.location.href = "contrato?Accion=1&PuestoId="+PuestoId+"&Horas="+Horas;
		}		
	}
</script>