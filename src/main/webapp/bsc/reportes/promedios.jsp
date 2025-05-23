<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.HashMap"%>

<jsp:useBean id="carreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>

<style>

.cargas{
	width: 100%;
	text-align: center;
	border:1px solid rgba(0,0,0,.3);
	margin-bottom: 20px;
	
	padding: 10px;

	background: rgba(255,255,255,.7);

	-webkit-box-sizing: border-box;
	   -moz-box-sizing: border-box;
	    -ms-box-sizing: border-box;
	        box-sizing: border-box;
	        
	-webkit-box-shadow: 0 10px 7px -10px rgba(0,0,0,.5);
	   -moz-box-shadow: 0 10px 7px -10px rgba(0,0,0,.5);
	     -o-box-shadow: 0 10px 7px -10px rgba(0,0,0,.5);
	    -ms-box-shadow: 0 10px 7px -10px rgba(0,0,0,.5);
	        box-shadow: 0 10px 7px -10px rgba(0,0,0,.5);

}

.cargas form{
	margin:0;
}

</style>

<%

	ArrayList<aca.catalogo.CatPeriodo> listaPeriodos = CatPeriodoUtil.getListAll(conEnoc, "ORDER BY PERIODO_ID");	
	
	if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")){
		session.setAttribute("periodo", request.getParameter("PeriodoId"));
	}
	
	String periodoId = (String)session.getAttribute("periodo");
	String periodoActual = aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc);
	
	ArrayList<aca.carga.Carga> lisCarga = new aca.carga.CargaUtil().getListAll(conEnoc,"WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY 4,2");

	if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")&&!lisCarga.isEmpty()){
		session.setAttribute("cargaId", lisCarga.get(0).getCargaId());
	}
	else if(request.getParameter("cambioCarga")!=null&&!request.getParameter("cambioCarga").equals("")){
		session.setAttribute("cargaId", request.getParameter("CargaId"));
	}

	String cargaId = (String)session.getAttribute("cargaId");
%>
<div class="container-fluid">
<h1>Promedios por Rango</h1>
<form action="promedios" method="post" name="formMatricula" target="_self">
<div class="alert alert-info">
	<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	<input type="hidden" name="cambioPeriodo">
	<input type="hidden" name="cambioCarga">
	
		   Periodos:
		   <select onchange="cambioPeriodo.value='1';submit();" name="PeriodoId" class="input input-medium">
	<%	for(int i=0; i<listaPeriodos.size(); i++){
			aca.catalogo.CatPeriodo periodo = listaPeriodos.get(i);
	%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>	
	<%
		}
	%>
	 	    </select>
			&nbsp;&nbsp;&nbsp;
			Carga académica:
			<select onchange="cambioCarga.value='1';submit();" name="CargaId" class="input input-xlarge">
	<%	for (aca.carga.Carga carga : lisCarga){%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>"><%=carga.getNombreCarga()%></option>
	<%	}%>          	    
	 	    </select>
	
</div>
</form>
<!-- ----------------- END CARGAS & PERIODOS ----------------- -->

<%
	ArrayList<aca.catalogo.CatCarrera> carreras = carreraU.getListCarga(conEnoc, cargaId, "ORDER BY FACULTAD_ID, CARRERA_ID");

	HashMap<String, String> mapInscritos = aca.alumno.EstadoUtil.getMapInscritos(conEnoc, cargaId, "ORDER BY 1");
	HashMap<String, String> mapProm = aca.kardex.ActualUtil.getMapInscritosProm(conEnoc, cargaId, "ORDER BY 1");

%>


<%
	String facultadTmp = "";
	for(aca.catalogo.CatCarrera carrera : carreras){
		if(!carrera.getFacultadId().equals(facultadTmp)){
%>
			<table style ="align:left" style="width:80%">
				<tr>
					<td><h3><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, carrera.getFacultadId() )%></h3></td>
				</tr>
			</table>
			<table class="table table-bordered">
			<thead class="table-info">
				<tr>
					<th style="width:60%"><spring:message code="aca.Carrera"/></th>
					<th><spring:message code="aca.Total"/></th>
					<th>No Prom.</th>
					<th>0-69</th>
					<th>70-79</th>
					<th>80-89</th>
					<th>90-100</th>
				</tr>
			</thead>
<%
			facultadTmp=carrera.getFacultadId();
		}
				int total    = 0;
				int noProm   = 0;
				int menor69  = 0;
				int menor79  = 0;
				int menor89  = 0;
				int menor100 = 0;
				
				for(int i=0; i<mapInscritos.size(); i++){
					 if(mapInscritos.values().toArray()[i].equals(carrera.getCarreraId())){
						total++; 
						
						String matricula = mapInscritos.keySet().toArray()[i].toString();
						
						if(!mapProm.containsKey(matricula)){
							noProm++;
						}else{
							String prom = mapProm.get(matricula);
							Double promNum = Double.parseDouble(prom.trim()); 
							if( promNum < 70 ){
								menor69++;
							}else if( promNum >= 70 && promNum < 80 ){
								menor79++;
							}else if( promNum >= 80 && promNum < 90 ){
								menor89++;
							}else if( promNum >= 90 && promNum <= 100 ){
								menor100++;
							}else{
								System.out.println(promNum);
							}
						}
						
					 }
				}
		
%>
				<tr>
					<td><%=carrera.getNombreCarrera() %></td>
					<td style="text-align:right;"><%=total %></td>
					<td style="text-align:right;"><%=noProm %></td>
					<td style="text-align:right;"><%=menor69 %></td>
					<td style="text-align:right;"><%=menor79 %></td>
					<td style="text-align:right;"><%=menor89 %></td>
					<td style="text-align:right;"><%=menor100 %></td>
				</tr>	
<%		
	}
%>
</table>
</div>

<%@ include file= "../../cierra_enoc.jsp" %>