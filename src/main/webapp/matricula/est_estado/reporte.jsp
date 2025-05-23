<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="estadosU" scope="page" class="aca.catalogo.EstadoUtil"/>
<jsp:useBean id="estados" scope="page" class="aca.catalogo.CatEstado"/>
<jsp:useBean id="carrerasU" scope="page" class="aca.catalogo.CarreraUtil"/>
<jsp:useBean id="carreras" scope="page" class="aca.catalogo.CatCarrera"/>
<jsp:useBean id="inscritosU" scope="page" class="aca.vista.InscritosUtil"/>
<jsp:useBean id="inscritos" scope="page" class="aca.vista.Inscritos"/>

<br>
<table style="width:90%" border="1" align="center"   bordercolor="#000000">
  	<tr>
    	<th align="center"><font size="2">Estadistica por Estado</font></th>
  	</tr>
	<tr>
		<td>
			<table style="width:90%" border="1" align="center"   bordercolor="#000000">
				<tr>
					<td>&nbsp;</td>
<%
	ArrayList lisEstados = estadosU.getLista(conEnoc, "91", "ORDER BY NOMBRE_ESTADO");
	for(int k = 0; k < lisEstados.size(); k++){
		estados = (aca.catalogo.CatEstado) lisEstados.get(k);
%>
					<td><b><%=estados.getNombreEstado() %></b></td>
<%
	}
%>
					<td><b><spring:message code="aca.Total"/></b></td>
				</tr>
<%
	ArrayList lisCarreras = carrerasU.getListAll(conEnoc, "ORDER BY NOMBRE_CARRERA");
	ArrayList lisInscritos = inscritosU.getListAll(conEnoc, "WHERE PAIS_ID = '91' ");
	for(int i = 0; i < lisCarreras.size(); i++){
		carreras = (aca.catalogo.CatCarrera) lisCarreras.get(i);
%>
				<tr>
					<td><b><%=carreras.getNombreCarrera() %></b></td>
<%
		int sumaCarreras = 0;
		for(int k = 0; k < lisEstados.size(); k++){
			estados = (aca.catalogo.CatEstado) lisEstados.get(k);
			int carreraEstado = 0;
			for(int j = 0; j < lisInscritos.size(); j++){
				inscritos = (aca.vista.Inscritos) lisInscritos.get(j);
				if(carreras.getCarreraId().equals(inscritos.getCarreraId()) & inscritos.getEstadoId().equals(estados.getEstadoId())){
					carreraEstado++;
				}
				if(estados.getEstadoId().equals("1") & inscritos.getEstadoId().equals("0") & carreras.getCarreraId().equals(inscritos.getCarreraId())){
					carreraEstado++;
				}
			}
%>
					<td align="center"><%if(carreraEstado != 0) out.print(carreraEstado); else out.print("&nbsp;"); %></td>
<%
			sumaCarreras += carreraEstado;
		}
%>
					<td align="center"><b><%=sumaCarreras %></b></td>
				</tr>
<%
	}
%>
				<tr>
					<td align="center"><b><spring:message code="aca.Total"/></b></td>
<%
	int totalEstados = 0;
	for(int k = 0; k < lisEstados.size(); k++){
		estados = (aca.catalogo.CatEstado) lisEstados.get(k);
		int sumaEstados = 0;
		for(int j = 0; j < lisInscritos.size(); j++){
			inscritos = (aca.vista.Inscritos) lisInscritos.get(j);
			if(inscritos.getEstadoId().equals(estados.getEstadoId())){
				sumaEstados++;
			}
			if(estados.getEstadoId().equals("1") & inscritos.getEstadoId().equals("0"))
				sumaEstados++;
		}
%>
					<td align="center"><b><%=sumaEstados %></b></td>
<%
		totalEstados += sumaEstados;
	}
%>
					<td align="center"><b><%=lisInscritos.size() %> - <%=totalEstados %></b></td>
				</tr>
			</table>
		</td>
	</tr>
		
</table>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>