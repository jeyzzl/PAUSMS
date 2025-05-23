<%@page import="aca.util.Fecha"%>
<%@ page import="java.util.HashMap"%>
<%@page import="aca.carga.Carga"%>
<%@page import="aca.catalogo.CatModalidad"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>


<jsp:useBean id="carga" scope="page" class="aca.carga.Carga" />
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil" />
<jsp:useBean id="catModalidad" scope="page" class="aca.catalogo.CatModalidad" />
<jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />
<jsp:useBean id="CatCarreraU" scope="page" class="aca.catalogo.CatCarreraUtil" />
<jsp:useBean id="CatFacultadU" scope="page" class="aca.catalogo.CatFacultadUtil" />
<jsp:useBean id="inscritosU" scope="page" class="aca.vista.InscritosUtil"/>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>

<head>	
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
	<style>
		h4{padding:0;margin:0;}
		.totales td{
			border:1px solid black;
		}
	</style>
</head>
<%	
	String cargas			= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	String cargaId 			= "";
	String fInscripcion		= request.getParameter("fecha")==null?Fecha.getHoy():request.getParameter("fecha");
	
	if ((String)session.getAttribute("cargas") == null){
		session.setAttribute("cargas", aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()));
	}
	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String periodoId 		= aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc);
	
	if (accion.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	} 
	
	ArrayList<CatModalidad> lisModalidad	= catModalidadU.getListAll(conEnoc, "ORDER BY MODALIDAD_ID");	
	
	//Map de Carreras
	java.util.HashMap <String, aca.catalogo.CatCarrera> mapCarrera		= CatCarreraU.getMapAll(conEnoc, "");
	
	//Map de Facultad
	java.util.HashMap <String, aca.catalogo.CatFacultad> mapFacultad	= CatFacultadU.getMapFacultad(conEnoc, "");	
	
	if(modalidades.equals(""))modalidades="'0'";
	if(cargas.equals(""))cargas="' '";	
	
	ArrayList<aca.vista.Inscritos> inscritos = inscritosU.getListAll(conEnoc, " WHERE CARGA_ID IN ("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"+
											" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"+
											" ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID");
	
	HashMap<String, aca.alumno.AlumPlan> alumnos =aca.alumno.PlanUtil.getMapInscritos(conEnoc, "");
	
	String lisModo[] 		= modalidades.replace("'", "").split(",");
%>

<body>
<div class="container-fluid">
<h2>Primeros Años</h2>
	<form id="forma" name="forma" action="grados?Accion=1" method="post">
	<div class="alert alert-info">
			<b>Cargas:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
			<b>Modalidades:</b>
<%
			for(String mod:lisModo){
				String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
				out.print("["+nombreModalidad+"] ");	
			}		
%>
			<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;		
		</div>
		<div class="alert alert-info">		 
			Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;" />&nbsp;&nbsp;
			Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;" />&nbsp;&nbsp;
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>		 
		</div>
	<table style="width:99%" class="table table-sm table-bordered">
<%
		String facultad = "";
		String carrera  = "";
		
		int[] totales = {0,0,0,0,0,0,0,
						 0,0,0,0,0,0,0,
						 0,0,0,0,0,0,0,
						 0,0,0,0,0,0,0};
		
		int[] totalesFinales = {0,0,0,0,0,0,0,
								0,0,0,0,0,0,0,
								0,0,0,0,0,0,0,
								0,0,0,0,0,0,0};
		
		int cont = 0;
		for(aca.vista.Inscritos ins: inscritos){
			cont++;
			
			String facultadId 	 = "-";
			String nombreCarrera = "-";
			String nomFacultad	 = "-";
			if(mapCarrera.containsKey(ins.getCarreraId())){
				facultadId 		= mapCarrera.get(ins.getCarreraId()).getFacultadId();
				nombreCarrera 	= mapCarrera.get(ins.getCarreraId()).getNombreCarrera();
				
				if(mapFacultad.containsKey(facultadId)){
					nomFacultad = mapFacultad.get(facultadId).getNombreFacultad() ;
				}
			}
			
			
			if(!facultadId.equals(facultad) || cont==inscritos.size()){
				
				if(!facultad.equals("")){
%>				
				<tr class="table-info">
					<td><h5>Totales de la facultad:</h5></td>
					<td><%=totales[0] %></td>
					<td><%=totales[1] %></td>
					<td><%=totales[2] %></td>
					<td><%=totales[3] %></td>
					<td><%=totales[4] %></td>
					<td><%=totales[5] %></td>
					<td><%=totales[6] %></td>
					<td></td>
					<td><%=totales[7] %></td>
					<td><%=totales[8] %></td>
					<td><%=totales[9] %></td>
					<td><%=totales[10] %></td>
					<td><%=totales[11] %></td>
					<td><%=totales[12] %></td>
					<td><%=totales[13] %></td>
					<td></td>
					<td><%=totales[14] %></td>
					<td><%=totales[15] %></td>
					<td><%=totales[16] %></td>
					<td><%=totales[17] %></td>
					<td><%=totales[18] %></td>
					<td><%=totales[19] %></td>
					<td><%=totales[20] %></td>
					<td></td>
					<td><%=totales[21] %></td>
					<td><%=totales[22] %></td>
					<td><%=totales[23] %></td>
					<td><%=totales[24] %></td>
					<td><%=totales[25] %></td>
					<td><%=totales[26] %></td>
					<td><%=totales[27] %></td>
				</tr>
<%					
					totalesFinales[0] = totalesFinales[0]+totales[0];
					totalesFinales[1] = totalesFinales[1]+totales[1];
					totalesFinales[2] = totalesFinales[2]+totales[2];
					totalesFinales[3] = totalesFinales[3]+totales[3];
					totalesFinales[4] = totalesFinales[4]+totales[4];
					totalesFinales[5] = totalesFinales[5]+totales[5];
					totalesFinales[6] = totalesFinales[6]+totales[6];
					totalesFinales[7] = totalesFinales[7]+totales[7];
					totalesFinales[8] = totalesFinales[8]+totales[8];
					totalesFinales[9] = totalesFinales[9]+totales[9];
					totalesFinales[10] = totalesFinales[10]+totales[10];
					totalesFinales[11] = totalesFinales[11]+totales[11];
					totalesFinales[12] = totalesFinales[12]+totales[12];
					totalesFinales[13] = totalesFinales[13]+totales[13];
					totalesFinales[14] = totalesFinales[14]+totales[14];
					totalesFinales[15] = totalesFinales[15]+totales[15];
					totalesFinales[16] = totalesFinales[16]+totales[16];
					totalesFinales[17] = totalesFinales[17]+totales[17];
					totalesFinales[18] = totalesFinales[18]+totales[18];
					totalesFinales[19] = totalesFinales[19]+totales[19];
					totalesFinales[20] = totalesFinales[20]+totales[20];
					totalesFinales[21] = totalesFinales[21]+totales[21];
					totalesFinales[22] = totalesFinales[22]+totales[22];
					totalesFinales[23] = totalesFinales[23]+totales[23];
					totalesFinales[24] = totalesFinales[24]+totales[24];
					totalesFinales[25] = totalesFinales[25]+totales[25];
					totalesFinales[26] = totalesFinales[26]+totales[26];
					totalesFinales[27] = totalesFinales[27]+totales[27];


					totales[0]=0;
					totales[1]=0;
					totales[2]=0;
					totales[3]=0;
					totales[4]=0;
					totales[5]=0;
					totales[6]=0;
					totales[7]=0;
					totales[8]=0;
					totales[9]=0;
					totales[10]=0;
					totales[11]=0;
					totales[12]=0;
					totales[13]=0;
					totales[14]=0;
					totales[15]=0;
					totales[16]=0;
					totales[17]=0;
					totales[18]=0;
					totales[19]=0;
					totales[20]=0;
					totales[21]=0;
					totales[22]=0;
					totales[23]=0;
					totales[24]=0;
					totales[25]=0;
					totales[26]=0;
					totales[27]=0;

				}
	
				
			}
			
			
			if(!facultadId.equals(facultad)){
%>			
			<tr>
				<td colspan="1">
					<h3><b><%=nomFacultad %></b></h3>
				</td>
				<td style="text-align:center;" colspan="7"><h5>Semestre 1</h5></td>
				<td></td>
				<td style="text-align:center;" colspan="7"><h5>Semestre 2</h5></td>
				<td></td>
				<td style="text-align:center;" colspan="7"><h5>Semestre 3</h5></td>
				<td></td>
				<td style="text-align:center;" colspan="7"><h5>Semestre 4</h5></td>				
			</tr>
			<tr class="table-info">
				<th width="40%"><h5><spring:message code="aca.Carrera"/></h5></th>
				<th width="2%">#</th>
				<th width="2%">Mx</th>
				<th width="2%">E</th>
				<th width="2%">A</th>
				<th width="2%">NA</th>
				<th width="2%">H</th>
				<th width="2%">M</th>
				<th style='background:#E6E6E6;width:1px;' ></th>
				<th width="2%">#</th>
				<th width="2%">Mx</th>
				<th width="2%">E</th>
				<th width="2%">A</th>
				<th width="2%">NA</th>
				<th width="2%">H</th>
				<th width="2%">M</th>
				<th style='background:#E6E6E6;width:1px;'></th>
				<th width="2%">#</th>
				<th width="2%">Mx</th>
				<th width="2%">E</th>
				<th width="2%">A</th>
				<th width="2%">NA</th>
				<th width="2%">H</th>
				<th width="2%">M</th>
				<th style='background:#E6E6E6;width:1px;'></th>
				<th width="2%">#</th>
				<th width="2%">Mx</th>
				<th width="2%">E</th>
				<th width="2%">A</th>
				<th width="2%">NA</th>
				<th width="2%">H</th>
				<th width="2%">M</th>
			</tr>
<%
			}
			facultad = facultadId;

			if(!ins.getCarreraId().equals(carrera)){
				
%>
		<tr>
			<td title="<%=ins.getCarreraId()%>"><%=nombreCarrera%></td>
			
			<%
			for(int i=1; i<=4; i++){
				
				
				int inscritos1  = 0;
				int mexicano1   = 0;
				int extranjero1 = 0;
				int ASD1 	    = 0;
				int noASD1		= 0;
				int hombres1	= 0;
				int mujeres1	= 0;
				
				for(aca.vista.Inscritos insc: inscritos){
					if(insc.getCarreraId().equals(ins.getCarreraId())){
						
						if ( alumnos.containsKey(insc.getCodigoPersonal()+insc.getPlanId()) ){
							
							if(alumnos.get(insc.getCodigoPersonal()+insc.getPlanId()).getCiclo().equals(i+"")){
								inscritos1++;
								
								if(insc.getNacionalidad().equals("91")){
									mexicano1++;
								}else{
									extranjero1++;
								}
								
								if(insc.getReligionId().equals("1")){
									ASD1++;
								}else{
									noASD1++;
								}
								
								if(insc.getSexo().equals("F")){
									mujeres1++;
								}else{
									hombres1++;
								}
								
							}
						}	
						
					}
				}
				
				if(i==1){
					totales[0] = totales[0]+ inscritos1;
					totales[1] = totales[1]+ mexicano1;
					totales[2] = totales[2]+ extranjero1;
					totales[3] = totales[3]+ ASD1;
					totales[4] = totales[4]+ noASD1;
					totales[5] = totales[5]+ hombres1;
					totales[6] = totales[6]+ mujeres1;
				}else if(i==2){
					totales[7] = totales[7]+ inscritos1;
					totales[8] = totales[8]+ mexicano1;
					totales[9] = totales[9]+ extranjero1;
					totales[10] = totales[10]+ ASD1;
					totales[11] = totales[11]+ noASD1;
					totales[12] = totales[12]+ hombres1;
					totales[13] = totales[13]+ mujeres1;
				}else if(i==3){
					totales[14] = totales[14]+ inscritos1;
					totales[15] = totales[15]+ mexicano1;
					totales[16] = totales[16]+ extranjero1;
					totales[17] = totales[17]+ ASD1;
					totales[18] = totales[18]+ noASD1;
					totales[19] = totales[19]+ hombres1;
					totales[20] = totales[20]+ mujeres1;
				}else if(i==4){
					totales[21] = totales[21]+ inscritos1;
					totales[22] = totales[22]+ mexicano1;
					totales[23] = totales[23]+ extranjero1;
					totales[24] = totales[24]+ ASD1;
					totales[25] = totales[25]+ noASD1;
					totales[26] = totales[26]+ hombres1;
					totales[27] = totales[27]+ mujeres1;
				}
				
			%>
				<td><%=inscritos1 %></td>
				<td><%=mexicano1 %></td>
				<td><%=extranjero1 %></td>
				<td><%=ASD1 %></td>
				<td><%=noASD1 %></td>
				<td><%=hombres1%></td>
				<td><%=mujeres1%></td>
				<%if(i!=4)out.print("<td style='background:#E6E6E6;'></td>"); %>
			<%
			}
			%>
		</tr>
		
		
<%
			}
			carrera = ins.getCarreraId();
		}
%>
		<tr>
			<td></td>
		</tr>
		<tr class="table-dark totales">
					<td>Totales Finales:</h4></td>
					<td><%=totalesFinales[0] %></h4></td>
					<td><%=totalesFinales[1] %></h4></td>
					<td><%=totalesFinales[2] %></h4></td>
					<td><%=totalesFinales[3] %></h4></td>
					<td><%=totalesFinales[4] %></h4></td>
					<td><%=totalesFinales[5] %></h4></td>
					<td><%=totalesFinales[6] %></h4></td>
					<td></td>
					<td><%=totalesFinales[7] %></h4></td>
					<td><%=totalesFinales[8] %></h4></td>
					<td><%=totalesFinales[9] %></h4></td>
					<td><%=totalesFinales[10] %></h4></td>
					<td><%=totalesFinales[11] %></h4></td>
					<td><%=totalesFinales[12] %></h4></td>
					<td><%=totalesFinales[13] %></h4></td>
					<td></td>
					<td><%=totalesFinales[14] %></h4></td>
					<td><%=totalesFinales[15] %></h4></td>
					<td><%=totalesFinales[16] %></h4></td>
					<td><%=totalesFinales[17] %></h4></td>
					<td><%=totalesFinales[18] %></h4></td>
					<td><%=totalesFinales[19] %></h4></td>
					<td><%=totalesFinales[20] %></h4></td>
					<td></td>
					<td><%=totalesFinales[21] %></h4></td>
					<td><%=totalesFinales[22] %></h4></td>
					<td><%=totalesFinales[23] %></h4></td>
					<td><%=totalesFinales[24] %></h4></td>
					<td><%=totalesFinales[25] %></h4></td>
					<td><%=totalesFinales[26] %></h4></td>
					<td><%=totalesFinales[27] %></h4></td>
				</tr>
	</table>	
	
<%
	if(inscritos.size()==0){
%>
		<table style="width:100%">
			<tr>
				<td style="text-align:center;"><h2>No se encontraron resultados</h2></td>
			</tr>
		</table>
<%
	} 
%>
	
</form>
</div>
<style>
	.index{
		position: absolute;
		position: fixed;
		top:25px;
		right:10px;
		
		background:white;
		border: 1px solid gray;
		padding: 10px 20px;
		
  		-moz-box-shadow: 0 1px 6px 0px #6E6E6E;
   		-webkit-box-shadow: 0 1px 6px 0px #6E6E6E;
		box-shadow: 0 1px 6px 0px #6E6E6E;
	}
	span{
		color: #214C8B;
	}
</style>
<div class="index">
	<p><span>#:</span> Inscritos</p>
	<p><span>Mx:</span> Mexicanos</p>
	<p><span>E:</span> Extranjeros</p>
	<p><span>A:</span> Adventistas</p>
	<p><span>NA:</span> No Adventistas</p>
	<p><span>H:</span> Hombres</p>
	<p><span>M:</span> Mujeres</p>
</div>

</div>
</body>
<script>	
	var index = jQuery('.index');	
	jQuery('<span class="close cerrar" style=position:absolute;top:5px;right:10px;>x</span>').on('click', function(){
		index.fadeToggle();
	}).prependTo(index);
</script>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%@ include file= "../../cierra_enoc.jsp" %>