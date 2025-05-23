<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.text.DecimalFormat"%>

<jsp:useBean id="AlumFamiliaUtil" scope="page" class="aca.alumno.AlumFamiliaUtil"/>
<jsp:useBean id="AlumHermanosUtil" scope="page" class="aca.alumno.AlumHermanosUtil"/>
<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="Inscritos" scope="page" class="aca.vista.Inscritos"/>
<jsp:useBean id="InscritosU" scope="page" class="aca.vista.InscritosUtil"/>
<jsp:useBean id="CatCarreraUtil" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="FesCCMovimientoUtil" scope="page" class="aca.financiero.FesCCMovimientoUtil"/>

<html>
	<head>
		<style>
			.border-arriba td{
				border-top: 1px solid;
			}
		</style>
	</head>
<%
	DecimalFormat dmf 	= new DecimalFormat("###,##0.00;(###,##0.00)");
	String filtro 		= request.getParameter("Filtro")==null?"A":request.getParameter("Filtro");
	ArrayList<aca.alumno.AlumFamilia> listaFamilias = AlumFamiliaUtil.getListAll(conEnoc, "WHERE ESTADO IN ('"+filtro+"') ORDER BY 1");	
	HashMap<String, aca.catalogo.CatCarrera> mapaCarreras = CatCarreraUtil.getMapAll(conEnoc, "");	
	boolean autorizados = filtro.equals("F");
%>
	<body>
	<div class="container-fluid">
		<div class="alert alert-info d-flex align-items-center">
			<a href="hermanos?Filtro=<%=filtro%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		</div>
		<br>
		<table style="margin: 0 auto;">
			<tr><td align="center"><font size="5">Alumnos <%=autorizados?"autorizados":"pendientes"%></font></td></tr>
		</table>
		<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">	 
			<tr>
				<th width="1%">#</th>
				<th width="1%"><spring:message code="aca.Matricula"/></th>
				<th><spring:message code="aca.Nombre"/></th>
				<th><spring:message code="aca.Carrera"/></th>
				<th>Enseñanza</th>
			<%	if(!autorizados){%>
					<th width="1%"><spring:message code="aca.Inscrito"/></th>
			<%	}%>
				<th width="1%" class="ayuda mensaje Descuento">Dto.</th>
			</tr>
		</thead>
		<%	
			int cont 	= 1;
			int familia = 0;
			for(aca.alumno.AlumFamilia alumFamilia : listaFamilias){
				String familiaId = alumFamilia.getFamiliaId();
				familia++;
				ArrayList<aca.alumno.AlumHermanos> listaHermanos = AlumHermanosUtil.getListAll(conEnoc, "WHERE FAMILIA_ID='"+familiaId+"' AND ESTADO IN('"+filtro+"')");
				
				if(listaHermanos.size()==1) continue;
				
				boolean cambiaFamilia = true;
				
				String descuento = listaHermanos.size()==2?"5%":"10%";
				out.println("<tr><td colspan='6'><b>Familia # "+familia+"</b></td></tr>");
				
				for(aca.alumno.AlumHermanos alumHermano : listaHermanos){
					Inscritos = InscritosU.mapeaRegId(conEnoc, alumHermano.getCodigoPersonal());
					AlumPersonal = AlumUtil.mapeaRegId(conEnoc, alumHermano.getCodigoPersonal());
					
					ArrayList<aca.financiero.FesCCMovimiento> listaEnsenanza = FesCCMovimientoUtil.getMovimientos(conEnoc, alumHermano.getCodigoPersonal(), Inscritos.getCargaId(), Inscritos.getBloqueId(), "AND TIPOMOV='02'");
					
					boolean inscrito = aca.alumno.AlumUtil.esInscrito(conEnoc, alumHermano.getCodigoPersonal());
					if(!inscrito && autorizados) continue;
				%>
					<tr <%=cambiaFamilia?"class='border-arriba'":""%>>
						<td style="text-align:center;font-size:9pt;"><%=cont%></td>
						<td class="mat" style="text-align:center;font-size:9pt;">
							<%=alumHermano.getCodigoPersonal()%>
						</td>
						<td class="ayuda alumno <%=alumHermano.getCodigoPersonal() %>" style="font-size:9pt;"><%=AlumPersonal.getNombreLegal()%></td>
						<td style="font-size:9pt;"><%=inscrito?mapaCarreras.get(Inscritos.getCarreraId()).getNombreCarrera():"-"%></td>
						<td style="text-align:right;font-size:9pt;"><b>$ <%=listaEnsenanza.isEmpty()?"-":dmf.format(Double.parseDouble(listaEnsenanza.get(0).getImporte()))%></b></td>
					<%	if(!autorizados){%>
							<td style="text-align:center;font-size:9pt;"><b><%=!inscrito?"<font color='#AE2113'><spring:message code='aca.No'/></font>":"<font color='green'><spring:message code='aca.Si'/></font>"%></b></td>
					<%	}%>
						<td style="text-align:center;font-size:9pt;"><b><%=descuento%></b></td>
					</tr>
				<%	cambiaFamilia = false;
					cont++;
				}
			}%>
		</table>
		</div>
	</body>
</html>

<%@ include file= "../../cierra_enoc.jsp" %>