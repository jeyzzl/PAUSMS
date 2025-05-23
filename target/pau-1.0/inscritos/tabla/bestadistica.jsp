<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.parametros.spring.Parametros"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>

<script type="text/javascript">
	function cambiaCarga(){
  		document.location.href="bestadistica?cargaId="+document.forma.carga.value;
	}
	function Mostrar(){		
		document.forma.submit();
	}
</script>
<head>		
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>
<%
	String cargas		= (String) request.getAttribute("cargas");
	String modalidades	= (String) request.getAttribute("modalidades");

	Parametros parametros = (Parametros) request.getAttribute("parametros");
	
	if(cargas.equals(""))cargas="''";
	if(modalidades.equals(""))modalidades="''";
	
	String fechaIni			= (String) request.getAttribute("fechaIni");
	String fechaFin			= (String) request.getAttribute("fechaFin");
	String accion 			= (String) request.getAttribute("accion");
	
	List<String> lista 								= (List<String>) request.getAttribute("lista");
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");

// 	Carga carga 			= (Carga)request.getAttribute("carga");

	//Quitar repetidos--------------->
	ArrayList<String> listTemp = new ArrayList<String>();
	for(String est1 : lista){				
		boolean encontrado = false;
		for(String est2: listTemp){
			if( est1.split("@")[10].equals(est2.split("@")[10]) ){
				encontrado = true;
				break;
			}
		}
		
		if(!encontrado){
			listTemp.add(est1);
		}
	}
	lista = null;
	lista = listTemp;
	//--------------------------------->
	String lisModo[] 		= modalidades.replace("'", "").split(",");	
%>
<style>
	.totales td{
		font-weight: 800;
		border-top: 2px solid gray;
		font-size: 14px;
		background:#F2F2F2;
	}
	.table tr td{
		text-align:right;
	}
</style>
<div class="container-fluid">
	<h2>Detailed Statistics</h2>
	<form id="forma" name="forma" action="bestadistica?Accion=1" method="post">
		<div class="alert alert-info">
			<b>Loads: </b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
			<b>Modalities: </b>
<%
			for(String mod:lisModo){				
				String nombreModalidad = "-";
				if (mapaModalidades.containsKey(mod)){
					nombreModalidad = mapaModalidades.get(mod).getNombreModalidad();
				}
				out.print("["+nombreModalidad+"] ");	
			}		
%>
			<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;		
		</div>
		<div class="alert alert-info">		 
			Start Date: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			End Date: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;" />&nbsp;&nbsp;
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>		 
		</div>	
		<table class="table table-sm table-striped table-bordered table-fontsmall" width="99%" align="right">
			<tr>
				<th width="38%">School/Course</th>
				<th width="4%"><spring:message code="aca.Inscritos"/></th>
				<th width="4%">Credits</th>
				<th width="1px"></th>
				<th width="4%">Boarding</th>
				<th width="5%" title="Day Student">D.S</th>
				<th width="1px"></th>
				<th width="4%"><spring:message code="aca.Hombres"/></th>
				<th width="4%"><spring:message code="aca.Mujeres"/></th>
				<th width="1px"></th>
				<th width="4%">National</th>
				<th width="4%">Foreign</th>
				<th width="1px"></th>
				<th width="4%">SDA</th>
				<th width="4%">NSDA</th>
				<th width="1px"></th>
				<th width="5%" title="Face to Face">FTF</th>
				<th width="4%">Distance</th>
			</tr>
		<% 
			String facultadTmp 	= ""; 
			String carreraTmp 	= "";
			
			int [] totalGeneral		 = {0,0,0,0,0,0,0,0,0,0,0,0};
			int [] totalFacultades 	 = {0,0,0,0,0,0,0,0,0,0,0,0};

			int cont = 0;
			for(String obj: lista){
				cont++;
				
				String facultadId = obj.split("@")[0];
				String carreraId  = obj.split("@")[1];
				
				if(!facultadTmp.equals(facultadId)){
					if(cont!=1){		
		%>
						<tr class="totales">
							<td style="text-align:left;">Totals:</td>
							<td><%=totalFacultades[0] %></td>
							<td><%=totalFacultades[1] %></td>
							<td style="border-top:0;"></td>
							<td><%=totalFacultades[2] %></td>
							<td><%=totalFacultades[3] %></td>
							<td style="border-top:0;"></td>
							<td><%=totalFacultades[4] %></td>
							<td><%=totalFacultades[5] %></td>
							<td style="border-top:0;"></td>
							<td><%=totalFacultades[6] %></td>
							<td><%=totalFacultades[7] %></td>
							<td style="border-top:0;"></td>
							<td><%=totalFacultades[8] %></td>
							<td><%=totalFacultades[9] %></td>
							<td style="border-top:0;"></td>
							<td><%=totalFacultades[10] %></td>
							<td><%=totalFacultades[11] %></td>
						</tr>
		<%
					}
					String facultadNombre = "-";
					if (mapaFacultades.containsKey(facultadId)){
						facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad();
					}
		%>
					<tr>
						<td colspan="18" style="text-align:left;border:1px solid black;background:#D8D8D8;"><h5><%=facultadId%>-<%=facultadNombre%></h5></td>
					</tr>	
		<%			
					facultadTmp = facultadId;
		
					for(int i=0; i<totalFacultades.length; i++){//Poner en 0 el contador de total por facultades
						totalFacultades[i]=0;
					}
				}
				
				if(carreraTmp.equals(carreraId))continue;
				
				int inscritos 	= 0;
				int creditos  	= 0;
				int internos  	= 0;
				int externos  	= 0;
				int hombres	  	= 0;
				int mujeres	  	= 0;
				int mexicanos 	= 0;
				int extranjeros = 0;
				int acfe	 	= 0;
				int noacfe		= 0;
				int presencial  = 0;
				int distancia	= 0;
				
				for(String objeto: lista){
					if(objeto.split("@")[1].equals(carreraId)){
						
						inscritos++;
						
						creditos += Integer.parseInt(objeto.split("@")[8]);
						
						if(objeto.split("@")[7].equals("I")){
							internos++;
						}else{
							externos++;
						}
						
						if(objeto.split("@")[2].equals("M")){
							hombres++;
						}else{
							mujeres++;
						}
						
						if(objeto.split("@")[3].equals(parametros.getPaisId())){
							mexicanos++;
						}else{
							extranjeros++;
						}
						
						if(objeto.split("@")[4].equals("1")){
							acfe++;
						}else{
							noacfe++;
						}
						
						if(objeto.split("@")[5].equals("1") || objeto.split("@")[5].equals("4")){
							presencial++;
						}else{
							distancia++;
						}
						
					}
				}
				
				totalFacultades[0]+=inscritos;
				totalFacultades[1]+=creditos;
				totalFacultades[2]+=internos;
				totalFacultades[3]+=externos;
				totalFacultades[4]+=hombres;
				totalFacultades[5]+=mujeres;
				totalFacultades[6]+=mexicanos;
				totalFacultades[7]+=extranjeros;
				totalFacultades[8]+=acfe;
				totalFacultades[9]+=noacfe;
				totalFacultades[10]+=presencial;
				totalFacultades[11]+=distancia;
				
				totalGeneral[0]+=inscritos;
				totalGeneral[1]+=creditos;
				totalGeneral[2]+=internos;
				totalGeneral[3]+=externos;
				totalGeneral[4]+=hombres;
				totalGeneral[5]+=mujeres;
				totalGeneral[6]+=mexicanos;
				totalGeneral[7]+=extranjeros;
				totalGeneral[8]+=acfe;
				totalGeneral[9]+=noacfe;
				totalGeneral[10]+=presencial;
				totalGeneral[11]+=distancia;
				
				String carreraNombre = "";
				if (mapaCarreras.containsKey(carreraId)){
					carreraNombre = mapaCarreras.get(carreraId).getNombreCarrera();
				}				
		%>
				<tr>
					<td style="text-align:left;"><%=carreraNombre%></td>
					<td><%=inscritos %></td>
					<td><%=creditos %></td>
					<td></td>
					<td><%=internos %></td>
					<td><%=externos %></td>
					<td></td>
					<td><%=hombres %></td>
					<td><%=mujeres %></td>
					<td></td>
					<td><%=mexicanos %></td>
					<td><%=extranjeros %></td>
					<td></td>
					<td><%=acfe %></td>
					<td><%=noacfe %></td>
					<td></td>
					<td><%=presencial %></td>
					<td><%=distancia %></td>
				</tr>
		<%
				carreraTmp = carreraId;
			}
		%>
			<tr class="totales">
				<td style="text-align:left;">Totals:</td>
				<td><%=totalFacultades[0] %></td>
				<td><%=totalFacultades[1] %></td>
				<td style="border-top:0;"></td>
				<td><%=totalFacultades[2] %></td>
				<td><%=totalFacultades[3] %></td>
				<td style="border-top:0;"></td>			
				<td><%=totalFacultades[4] %></td>
				<td><%=totalFacultades[5] %></td>
				<td style="border-top:0;"></td>
				<td><%=totalFacultades[6] %></td>
				<td><%=totalFacultades[7] %></td>
				<td style="border-top:0;"></td>
				<td><%=totalFacultades[8] %></td>
				<td><%=totalFacultades[9] %></td>
				<td style="border-top:0;"></td>
				<td><%=totalFacultades[10] %></td>
				<td><%=totalFacultades[11] %></td>
			</tr>
		</table>
		<table class="table table-sm table-bordered">
			<tr>
				<th colspan="17">Grand Total</th>
			</tr>
			<tr>
				<td><h5><spring:message code="aca.Inscritos"/></h5></td>
				<td><%=totalGeneral[0] %></td>
				<td width="1px"></td>
				<td><h5>Boarding</h5></td>
				<td><%=totalGeneral[2] %></td>
				<td width="1px"></td>
				<td><h5><spring:message code="aca.Hombres"/></h5></td>
				<td><%=totalGeneral[4] %></td>
				<td width="1px"></td>
				<td><h5>National</h5></td>
				<td><%=totalGeneral[6] %></td>
				<td width="1px"></td>
				<td><h5>SDA</h5></td>
				<td><%=totalGeneral[8] %></td>
				<td width="1px"></td>
				<td><h5><spring:message code="aca.Presencial"/></h5></td>
				<td><%=totalGeneral[10] %></td>
			</tr>
			<tr>
				<td><h5>Credits</h5></td>
				<td><%=totalGeneral[1] %></td>
				<td width="1px"></td>		
				<td><h5>Off-Campus</h5></td>
				<td><%=totalGeneral[3] %></td>
				<td width="1px"></td>
				<td><h5><spring:message code="aca.Mujeres"/></h5></td>
				<td><%=totalGeneral[5] %></td>
				<td width="1px"></td>
				<td><h5>Foreigners</h5></td>
				<td><%=totalGeneral[7] %></td>
				<td width="1px"></td>
				<td><h5>NSDA</h5></td>
				<td><%=totalGeneral[9] %></td>
				<td width="1px"></td>
				<td><h5>Distance</h5></td>
				<td><%=totalGeneral[11] %></td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>