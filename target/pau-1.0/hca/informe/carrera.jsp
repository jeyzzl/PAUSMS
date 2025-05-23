<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="aca.hca.spring.HcaTipo"%>
<%@page import="aca.hca.spring.HcaMaestro"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.hca.spring.HcaMaestroEstado"%>
<%@page import="aca.catalogo.spring.CatCarrera"%> 
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
	<head>
		<style>
			.border-arriba td{
				border-top: 2px solid;
			}
		</style>
	</head>
<%
	DecimalFormat getformato = new DecimalFormat("###,##0.00;-###,##0.00");

	String facultadId 	= request.getParameter("FacultadId");
	String cargaId 		= request.getParameter("CargaId");
	String carreraId = "";

	Carga carga 								= (Carga)request.getAttribute("carga");
	List<aca.Mapa>  lisMaestros					= (List<aca.Mapa>) request.getAttribute("lisMaestros");	
	List<HcaTipo>  lisTipos						= (List<HcaTipo>) request.getAttribute("lisTipos");	
	HashMap<String, String> mapaTotalHoras 		= (HashMap<String,String>)request.getAttribute("mapaTotalHoras");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,Maestros> mapaMaestros		= (HashMap<String,Maestros>)request.getAttribute("mapaMaestros");	
	
	HashMap<String, HcaMaestroEstado> mapaMaestroEstado 			 = (HashMap<String, HcaMaestroEstado>)request.getAttribute("mapaMaestroEstado");

	Integer[] participantes = new Integer[lisTipos.size()];
	Float[] horas = new Float[lisTipos.size()];
	int D = 0;
	float horasD = 0;
	
	for(int i=0; i<participantes.length; i++){
		participantes[i]=0;
		horas[i]=(float)0;
	}
	
	if(request.getParameter("Accion")!=null){
		session.setAttribute("codigoEmpleado", request.getParameter("CodigoPersonal"));
		//response.sendRedirect("../contrato/docente?FacultadId="+facultadId+"&carga="+cargaId+"&Reg=1");
		out.print("<div class='alert alert-success'><a class='btn btn-primary' href='../contrato/docente?FacultadId="+facultadId+"&carga="+cargaId+"&Reg=1'>Regresar</a></div>");
	}
	else{
%>
		<body>
			<div class="container-fluid">
			<h2>Informe de facultad<small class="text-muted fs-4"> ( <%=carga.getNombreCarga()%> )</small></h2><br>
			<div class="alert alert-info">
				<a class="btn btn-primary" href="elige?CargaId=<%=cargaId%>"><spring:message code="aca.Regresar"/></a>
			</div>
			<table style="width:95%" class="table table-fullcondensed">
				<tr>
					<th width="3%"><spring:message code="aca.Numero"/></th>
					<th width="1%">Nómina</th>
					<th><spring:message code="aca.Maestro"/></th>
					<th>#Mat.</th>
					<th width="1%"><spring:message code="aca.Estado"/></th>
					<th width="1%" class="ayuda mensaje Docencia">D.</th>
				<%	for(HcaTipo hcaTipo : lisTipos){
						String tipoNombre = hcaTipo.getTipoNombre();
						String [] arr = tipoNombre.split(" ");
						String abrevia = "";
						for(String x : arr){
							if(Character.isUpperCase(x.charAt(0))){
								abrevia+=x.substring(0,1)+".";
							}
						}%>
						<th width="1%" class="ayuda mensaje <%=tipoNombre%>"><%=abrevia%></th>
				<%	}%>
					<th width="1%" class="ayuda mensaje Total semanales">T.Semanal</th>
					<th width="1%" class="ayuda mensaje Total semestrales">T.Semestral</th>
				</tr>
			<%	int cont = 0;
				for(aca.Mapa maestro : lisMaestros){					
					String maestroNombre = "";
					if (mapaMaestros.containsKey( maestro.getLlave())){
						maestroNombre =  mapaMaestros.get( maestro.getLlave()).getNombre()+" "+mapaMaestros.get( maestro.getLlave()).getApellidoPaterno()+" "+mapaMaestros.get( maestro.getLlave()).getApellidoMaterno();
					}
					
					String carreraNombre = "-";					
					boolean distinto = false;					
					
					float tSemestral = 0;
					boolean abierto = true;
					if(mapaMaestroEstado.containsKey(maestro.getLlave())){
						tSemestral = Float.parseFloat(mapaMaestroEstado.get(maestro.getLlave()).getSemestral());
						abierto = false;
					}
			%>
					<tr class="<%=distinto&&cont!=0?"border-arriba":""%> button">
						<td style="text-align:center;"><%=cont+1%></td>
						<td><%=maestro.getLlave()%></td>
						<td style="padding-left:10;"><b><%=maestroNombre%></b></td>
						<td style="padding-left:10;"><%=maestro.getValor()%></td>
					<%	if(abierto){%>
							<td style="padding-left:10;"><font color="#AE2113"><b>Abierto</b></font></td>
					<%	}
						else{%>
							<td style="padding-left:10;"><font color="green"><b>Cerrado</b></font></td>
					<%	}%>
						<td style="text-align:center;" id="td<%=maestro.getLlave()%>">-</td>
					<%	float total = 0;
						int i = 0;
						for(HcaTipo hcaTipo : lisTipos){
						%>
						<%	if(mapaTotalHoras.containsKey(maestro.getLlave()+cargaId+hcaTipo.getTipoId())){
								participantes[i] = participantes[i]+1;
								
								float num = Float.parseFloat(mapaTotalHoras.get(maestro.getLlave()+cargaId+hcaTipo.getTipoId()));
								total+=num;
								
								horas[i] = horas[i]+num;
								%>
								<td style="text-align:right;"><b><%=getformato.format(num)%></b></td>
						<%	}
							else{%>
								<td style="text-align:center;">-</td>
						<%	}
							i++;
						}
						if(tSemestral>0){
							D++;
							horasD+=tSemestral-total;
						%><script>jQuery("#td"+<%=maestro.getLlave()%>).html("<b><%=getformato.format(tSemestral-total)%></b>");</script><%	}%>
					<%	if(!abierto){%>
							<td style="text-align:right;"><b><%=getformato.format(Float.parseFloat(mapaMaestroEstado.get(maestro.getLlave()).getSemanal()))%></b></td>
					<%	}
						else{%>
							<td style="text-align:center;">-</td>
					<%	}%>
						<td style="text-align:right;"><b><%=getformato.format(tSemestral)%></b></td>
					</tr>
			<%		cont++;
				}
				%>
				<tr>
					<td colspan="5"><h4>Total de horas</h4></td>
					<td style="text-align:right;">&nbsp;&nbsp;<%=getformato.format(horasD) %></td>
					<%
					int i=0;
					for(HcaTipo hcaTipo : lisTipos){
					%>
						<td style="text-align:right;">&nbsp;&nbsp;<%=getformato.format(horas[i]) %></td>
					<%
						i++;
					}
					%>
				</tr>
				<tr>
					<td colspan="5"><h4>Porcentaje de maestros asistidos:</h4></td>
					<td style="text-align:right;">&nbsp;&nbsp;<%=getformato.format((float)(D*100)/(float)cont) %>%</td>
					<%
					i=0;
					for(HcaTipo hcaTipo : lisTipos){
					%>
						<td style="text-align:right;">&nbsp;&nbsp;<%=getformato.format((float)participantes[i]*100/(float)cont) %>%</td>
					<%
						i++;
					}
					%>
				</tr>
			</table>
			</div>
		</body>
<%	}%>
</html>