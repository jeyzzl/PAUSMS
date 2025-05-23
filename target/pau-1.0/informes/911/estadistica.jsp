<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="SepAlumnoU" scope="page" class="aca.sep.SepAlumnoUtil"/>

<script type="text/javascript">
	
	function Mostrar(){	
		document.forma.submit();
	}

</script>

<%
	String planSep 		= request.getParameter("PlanSep")==null?"-":request.getParameter("PlanSep");
	String planUm 		= request.getParameter("PlanUm")==null?"-":request.getParameter("PlanUm");
	String fecha 		= request.getParameter("Fecha")==null?"29/09/2017":request.getParameter("Fecha");
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String hombres		= "0";
	String mujeres		= "0";
	int totalAlumnos	= 0;
	int totGenero		= 0;
	String ingresoNL	= "0";
	String ingresoMexico= "0";
	String ingresoUSA	= "0";
	String otrosPaises	= "0";
	String totalNL		= "0";
	String totalMexico	= "0";
	String totalUSA		= "0";
	
	ArrayList<String> lisPrepa	 	= null;
	ArrayList<String> lisPaises 	= null;	
	ArrayList<String> lisTotPaises 	= null;
	
	if (accion.equals("1")){	
		totalAlumnos	= SepAlumnoU.getTotal(conEnoc, fecha, planSep, planUm);
		mujeres 		= SepAlumnoU.getGenero(conEnoc, "M", fecha, planSep, planUm);
		hombres 		= SepAlumnoU.getGenero(conEnoc, "H", fecha, planSep, planUm);
		totGenero 		= Integer.parseInt(hombres) + Integer.parseInt(mujeres);
		
		lisPrepa		= SepAlumnoU.lisPrepaLugar(conEnoc, fecha, planSep, planUm);
		
		ingresoNL		= SepAlumnoU.getPorEstado(conEnoc, fecha, planSep, planUm, "91", "19");		
		ingresoMexico	= SepAlumnoU.getPorPais(conEnoc, fecha, planSep, planUm, "91", "19");
		ingresoUSA		= SepAlumnoU.getPorPais(conEnoc, fecha, planSep, planUm, "47", "0");
		
		lisPaises		= SepAlumnoU.getOtrosPaises(conEnoc, fecha, planSep, planUm, "91,47");
		
		totalNL			= SepAlumnoU.totalPorEstado(conEnoc, fecha, planSep, planUm, "91", "19");
		totalMexico		= SepAlumnoU.totalPorPais(conEnoc, fecha, planSep, planUm, "91", "19");
		totalUSA		= SepAlumnoU.totalPorPais(conEnoc, fecha, planSep, planUm, "47", "0");
		
		lisTotPaises	= SepAlumnoU.totalOtrosPaises(conEnoc, fecha, planSep, planUm, "91");
	}
	
	// Lista de fechas a desplegar
	ArrayList<String> listFechas = SepAlumnoU.listFechas(conEnoc);
	
	// Mapa de alumnos por grado
	java.util.HashMap<String,String> mapaGrado = SepAlumnoU.mapGrado(conEnoc, fecha, planSep, planUm);		
	
	// Mapa de alumnos por edad
	java.util.HashMap<String,String> mapaEdadGradoGenero = SepAlumnoU.mapEdadGradoyGenero(conEnoc, fecha, planSep, planUm);
	
	// Edad de 30-40
	java.util.HashMap<String,String> mapaEdad30 = SepAlumnoU.mapEdadyGradoRango(conEnoc, fecha, planSep, planUm,30,34);
	
	// Edad de 30-40
	java.util.HashMap<String,String> mapaEdad35 = SepAlumnoU.mapEdadyGradoRango(conEnoc, fecha, planSep, planUm,35,39);
	
	// Edad de 30-40
	java.util.HashMap<String,String> mapaEdad40 = SepAlumnoU.mapEdadyGradoRango(conEnoc, fecha, planSep, planUm,40,100);
	
	// Mapa de alumnos por edad y genero de primer ingreso
	java.util.HashMap<String,String> mapaGenero = SepAlumnoU.mapEdadyGenero(conEnoc, fecha, planSep, planUm,"1");
	
	// Edad de 30-40
	java.util.HashMap<String,String> mapaGenero30 = SepAlumnoU.mapEdadyGeneroRango(conEnoc, fecha, planSep, planUm,30,34,"1");
	
	// Edad de 35-39
	java.util.HashMap<String,String> mapaGenero35 = SepAlumnoU.mapEdadyGeneroRango(conEnoc, fecha, planSep, planUm,35,39,"1");
	
	// Edad mayor o igual de 40
	java.util.HashMap<String,String> mapaGenero40 = SepAlumnoU.mapEdadyGeneroRango(conEnoc, fecha, planSep, planUm,40,100,"1");
	
	// Mapa de alumnos por edad y genero de primer ingreso
	java.util.HashMap<String,String> mapaGeneroReingreso = SepAlumnoU.mapEdadyGenero(conEnoc, fecha, planSep, planUm,"2,3,4,5,6,7,8,9,10,11,12");
	
	// Edad de 30-34
	java.util.HashMap<String,String> mapaGeneroRe30 = SepAlumnoU.mapEdadyGeneroRango(conEnoc, fecha, planSep, planUm,30,34,"2,3,4,5,6,7,8,9,10,11,12");
	
	// Edad de 35-39
	java.util.HashMap<String,String> mapaGeneroRe35 = SepAlumnoU.mapEdadyGeneroRango(conEnoc, fecha, planSep, planUm,35,39,"2,3,4,5,6,7,8,9,10,11,12");
	
	// Edad mayor o igual de 40
	java.util.HashMap<String,String> mapaGeneroRe40 = SepAlumnoU.mapEdadyGeneroRango(conEnoc, fecha, planSep, planUm,40,100,"2,3,4,5,6,7,8,9,10,11,12");
%>
<body>
<div class="container-fluid">
	<form id="forma" name="forma" action="estadistica?Accion=1" method="post">
	<h1>Estadística 911</h1>
	<div class="alert alert-info">
		Plan SEP: <input type="text" class="text" id="PlanSep" name="PlanSep" value="<%=planSep%>" maxlength="100" size="30" />&nbsp;&nbsp;
		Plan UM: <input type="text" class="text" id="PlanUm" name="PlanUm" value="<%=planUm%>" maxlength="100" size="30" />&nbsp;&nbsp;
		<select name="Fecha">
<% 		for(String fec : listFechas){%>
			<option  <%=fecha.equals(fec)?"Selected":""%> value="<%=fec%>"><%=fec%></option>
<% 		}%>
		</select>&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
		&nbsp;&nbsp;&nbsp;
		Total = <span class="badge bg-success"><%=totalAlumnos%></span>
	</div>
	</form>
	<table class="table table-bordered">
		<tr class="alert alert-success">
			<th colspan="6">Primer ingreso por genero</th>						
		</tr>
		<tr>
			<td width="16%">Hombres:</td>
			<td width="16%"><%=hombres%></td>		
			<td width="16%">Mujeres:</td>
			<td width="16%"><%=mujeres%></td>	
			<td width="16%">Total:</td>
			<td width="16%"><%=totGenero%></td>
		</tr>
	</table>
	<table class="table table-bordered">
		<tr class="alert alert-success">
			<th colspan="3" class="alert alert-info">Primer ingreso por lugar de estudios de preparatoria</th>
		</tr>
	<% 
	if (lisPrepa!=null && lisPrepa.size()>0){	
		for (String prepa : lisPrepa){
	%>
		<tr>
			<td width="20%"><%=prepa.substring(0,prepa.indexOf("="))%></td>
			<td width="20%"><%=prepa.substring(prepa.indexOf("=")+1,prepa.length())%></td>
			<td width="60%">&nbsp;</td>
		</tr>
	<%
		}
	} 
	%>	
	</table>
	<table class="table table-bordered">			
		<tr class="alert alert-success">
			<th colspan="6" class="alert alert-info">Primer ingreso por lugar de nacimiento</th>
		</tr>
		<tr>
			<td width="15%">Nuevo Leon:</td>
			<td width="15%"><%=ingresoNL%></td>		
			<td width="15%">México(menos NL):</td>
			<td width="15%"><%=ingresoMexico%></td>	
			<td width="15%">EUA:</td>
			<td width="15%"><%=ingresoUSA%></td>		
		</tr>
	</table>
	<table class="table table-bordered">
		<tr class="alert alert-success">
			<th colspan="3" class="alert alert-info">Primer ingreso de otros Paises</th>						
		</tr>
	<% 
	if (lisPaises!=null && lisPaises.size()>0){
		for (String otros : lisPaises){
	%>
		<tr>
			<td width="20%"><%=otros.substring(0,otros.indexOf("="))%></td>			
			<td width="20%"><%=otros.substring(otros.indexOf("=")+1,otros.length())%></td>
			<td width="60%">&nbsp;</td>
		</tr>		
	<%
		}
	} 
	%>	
	</table>
	
	<table class="table table-bordered">
		<tr class="alert alert-success">
			<th colspan="4">Total de alumnos inscritos por grados</th>						
		</tr>
		<tr>
			<th>Grado</th>
			<th>Hombres</th>
			<th>Mujeres</th>
			<th>Total</th>
		</tr>
	<% 
	int total = 0;
	int sumHombre=0, sumMujer = 0;
	for (int grado=1; grado <= 6; grado++){
		
		String hombre = "0";
		if (mapaGrado.containsKey(grado+"H")){
			hombre = mapaGrado.get(grado+"H");
			sumHombre+= Integer.parseInt(hombre);
		}
		String mujer = "0";
		if (mapaGrado.containsKey(grado+"M")){
			mujer = mapaGrado.get(grado+"M");
			sumMujer+= Integer.parseInt(mujer);
		}
		total = Integer.parseInt(hombre)+Integer.parseInt(mujer);
	%>
		<tr>
			<td width="10%"><%=grado%></td>			
			<td width="20%"><%=hombre%></td>
			<td width="20%"><%=mujer%></td>
			<td width="50%"><%=total%></td>
		</tr>			
	<%		
	} 
	%>	
		<tr>
			<th width="10%">Total</th>			
			<th width="20%"><%=sumHombre%></th>
			<th width="20%"><%=sumMujer%></th>
			<th width="50%"><%=sumHombre+sumMujer%></th>
		</tr>
	</table>
	
	<table class="table table-bordered">				
		<tr class="alert alert-success">
			<th colspan="6">Total de alumnos por lugar de nacimiento</th>
		</tr>
		<tr>
			<td width="16%">Nuevo Leon:</td>
			<td width="16%"><%=totalNL%></td>		
			<td width="16%">México(menos NL):</td>
			<td width="16%"><%=totalMexico%></td>	
			<td width="16%">EUA:</td>
			<td width="16%"><%=totalUSA%></td>
		</tr>
	</table>
	
	<table class="table table-bordered">
		<tr class="alert alert-success">
			<th colspan="3">Total de alumnos de otros Paises</th>						
		</tr>
	<% 
	if (lisTotPaises!=null && lisTotPaises.size()>0){
		for (String otros : lisTotPaises){
	%>
		<tr>
			<td width="20%"><%=otros.substring(0,otros.indexOf("="))%></td>			
			<td width="20%"><%=otros.substring(otros.indexOf("=")+1,otros.length())%></td>
			<td width="60%">&nbsp;</td>
		</tr>		
	<%
		}
	} 
	%>	
	</table>
	
	<table class="table table-bordered">
		<tr class="alert alert-success">
			<th colspan="15">Inscritos por edad, grado y genero</th>		
		</tr>
		<tr>
			<th width="16%">Edad</th>						
			<th colspan="2" width="12%">Grado 1</th>
			<th colspan="2" width="12%">Grado 2</th>
			<th colspan="2" width="12%">Grado 3</th>
			<th colspan="2" width="12%">Grado 4</th>
			<th colspan="2" width="12%">Grado 5</th>
			<th colspan="2" width="12%">Grado 6</th>
			<th colspan="2" width="12%">Total</th>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<th>H</th>						
			<th>M</th>			
			<th>H</th>						
			<th>M</th>
			<th>H</th>						
			<th>M</th>
			<th>H</th>						
			<th>M</th>
			<th>H</th>						
			<th>M</th>
			<th>H</th>						
			<th>M</th>
			<th>H</th>						
			<th>M</th>
<!-- 			<th>&nbsp;</th> -->
		</tr>
<%
	// Buscar el minimo de años
	String minEdad = SepAlumnoU.minEdad(conEnoc, fecha, planSep, planUm);	
	int tot1H=0,tot1M=0,tot2H=0,tot2M=0,tot3H=0,tot3M=0,tot4H=0,tot4M=0,tot5H=0,tot5M=0,tot6H=0,tot6M=0;
	int sumaTotH=0, sumaTotM=0; 
	for (int edad=Integer.parseInt(minEdad); edad<=29; edad++){		
%>
		<tr>
			<td><%=edad%></td>
<%	
		sumaTotH = 0;
		sumaTotM = 0;
		for(int grado=1;grado<=6;grado++){
			
			String hombre 	= "0";			
			if (mapaEdadGradoGenero.containsKey(String.valueOf(edad)+String.valueOf(grado)+"H") ){
				hombre 	= mapaEdadGradoGenero.get(String.valueOf(edad)+String.valueOf(grado)+"H");				
			}
			String mujer	= "0";
			if (mapaEdadGradoGenero.containsKey(String.valueOf(edad)+String.valueOf(grado)+"M") ){
				mujer	= mapaEdadGradoGenero.get(String.valueOf(edad)+String.valueOf(grado)+"M");	
			}
			sumaTotH += Integer.parseInt(hombre);
			sumaTotM += Integer.parseInt(mujer);
			
			if (grado==1){ tot1H += Integer.parseInt(hombre); tot1M+=Integer.parseInt(mujer);}
			if (grado==2){ tot2H += Integer.parseInt(hombre); tot2M+=Integer.parseInt(mujer);}
			if (grado==3){ tot3H += Integer.parseInt(hombre); tot3M+=Integer.parseInt(mujer);}
			if (grado==4){ tot4H += Integer.parseInt(hombre); tot4M+=Integer.parseInt(mujer);}
			if (grado==5){ tot5H += Integer.parseInt(hombre); tot5M+=Integer.parseInt(mujer);}
			if (grado==6){ tot6H += Integer.parseInt(hombre); tot6M+=Integer.parseInt(mujer);}			
%>
			<td><%=hombre%></td>
			<td><%=mujer%></td>				
<%		
		}	
%>				
			<td><%=sumaTotH%></td>
			<td><%=sumaTotM%></td>
		</tr>
	<%		
	} 
%>			
	</table>
	<table class="table table-bordered">		
<%
	int g1H=0,g1M=0,g2H=0,g2M=0,g3H=0,g3M=0,g4H=0,g4M=0,g5H=0,g5M=0,g6H=0,g6M=0;	
	if (mapaEdad30.containsKey("1H")) g1H = Integer.parseInt(mapaEdad30.get("1H"));
	if (mapaEdad30.containsKey("2H")) g2H = Integer.parseInt(mapaEdad30.get("2H"));
	if (mapaEdad30.containsKey("3H")) g3H = Integer.parseInt(mapaEdad30.get("3H"));
	if (mapaEdad30.containsKey("4H")) g4H = Integer.parseInt(mapaEdad30.get("4H"));	
	if (mapaEdad30.containsKey("5H")) g5H = Integer.parseInt(mapaEdad30.get("5H"));
	if (mapaEdad30.containsKey("6H")) g6H = Integer.parseInt(mapaEdad30.get("6H"));
	if (mapaEdad30.containsKey("1M")) g1M = Integer.parseInt(mapaEdad30.get("1M"));
	if (mapaEdad30.containsKey("2M")) g2M = Integer.parseInt(mapaEdad30.get("2M"));
	if (mapaEdad30.containsKey("3M")) g3M = Integer.parseInt(mapaEdad30.get("3M"));
	if (mapaEdad30.containsKey("4M")) g4M = Integer.parseInt(mapaEdad30.get("4M"));
	if (mapaEdad30.containsKey("5M")) g5M = Integer.parseInt(mapaEdad30.get("5M"));
	if (mapaEdad30.containsKey("6M")) g6M = Integer.parseInt(mapaEdad30.get("6M"));
	
	tot1H += g1H;	tot1M += g1M;
	tot2H += g2H;	tot2M += g2M;
	tot3H += g3H;	tot3M += g3M;
	tot4H += g4H;	tot4M += g4M;
	tot5H += g5H;	tot5M += g5M;
	tot6H += g6H;	tot6M += g6M;
%>		
		<tr>
			<td width="16%">Edad 30-34</td>
			<td width="6%"><%=g1H%></td>
			<td width="6%"><%=g1M%></td>
			<td width="6%"><%=g2H%></td>
			<td width="6%"><%=g2M%></td>
			<td width="6%"><%=g3H%></td>
			<td width="6%"><%=g3M%></td>
			<td width="6%"><%=g4H%></td>
			<td width="6%"><%=g4M%></td>
			<td width="6%"><%=g5H%></td>
			<td width="6%"><%=g5M%></td>
			<td width="6%"><%=g6H%></td>
			<td width="6%"><%=g6M%></td>
			<td width="6%"><%=g1H+g2H+g3H+g4H+g5H+g6H%></td>
			<td width="6%"><%=g1M+g2M+g3M+g4M+g5M+g6M%></td>
		</tr>
	</table>
	
	<table class="table table-bordered">		
<%
	g1H=0;g1M=0;g2H=0;g2M=0;g3H=0;g3M=0;g4H=0;g4M=0;g5H=0;g5M=0;g6H=0;g6M=0;
	if (mapaEdad35.containsKey("1H")) g1H = Integer.parseInt(mapaEdad35.get("1H"));
	if (mapaEdad35.containsKey("2H")) g2H = Integer.parseInt(mapaEdad35.get("2H"));
	if (mapaEdad35.containsKey("3H")) g3H = Integer.parseInt(mapaEdad35.get("3H"));
	if (mapaEdad35.containsKey("4H")) g4H = Integer.parseInt(mapaEdad35.get("4H"));
	if (mapaEdad35.containsKey("5H")) g5H = Integer.parseInt(mapaEdad35.get("5H"));
	if (mapaEdad35.containsKey("6H")) g6H = Integer.parseInt(mapaEdad35.get("6H"));
	
	if (mapaEdad35.containsKey("1M")) g1M = Integer.parseInt(mapaEdad35.get("1M"));	
	if (mapaEdad35.containsKey("2M")) g2M = Integer.parseInt(mapaEdad35.get("2M"));
	if (mapaEdad35.containsKey("3M")) g3M = Integer.parseInt(mapaEdad35.get("3M"));
	if (mapaEdad35.containsKey("4M")) g4M = Integer.parseInt(mapaEdad35.get("4M"));
	if (mapaEdad35.containsKey("5M")) g5M = Integer.parseInt(mapaEdad35.get("5M"));
	if (mapaEdad35.containsKey("6M")) g6M = Integer.parseInt(mapaEdad35.get("6M"));
	
	tot1H += g1H;	tot1M += g1M;
	tot2H += g2H;	tot2M += g2M;
	tot3H += g3H;	tot3M += g3M;
	tot4H += g4H;	tot4M += g4M;
	tot5H += g5H;	tot5M += g5M;
	tot6H += g6H;	tot6M += g6M;	
%>		
		<tr>
			<td width="16%">Edad 35-39</td>
			<td width="6%"><%=g1H%></td>
			<td width="6%"><%=g1M%></td>
			<td width="6%"><%=g2H%></td>
			<td width="6%"><%=g2M%></td>
			<td width="6%"><%=g3H%></td>
			<td width="6%"><%=g3M%></td>
			<td width="6%"><%=g4H%></td>
			<td width="6%"><%=g4M%></td>
			<td width="6%"><%=g5H%></td>
			<td width="6%"><%=g5M%></td>
			<td width="6%"><%=g6H%></td>
			<td width="6%"><%=g6M%></td>
			<td width="6%"><%=g1H+g2H+g3H+g4H+g5H+g6H%></td>
			<td width="6%"><%=g1M+g2M+g3M+g4M+g5M+g6M%></td>
		</tr>
	</table>
	
	<table class="table table-bordered">		
<%	
	g1H=0;g1M=0;g2H=0;g2M=0;g3H=0;g3M=0;g4H=0;g4M=0;g5H=0;g5M=0;g6H=0;g6M=0;
	if (mapaEdad40.containsKey("1H")) g1H = Integer.parseInt(mapaEdad40.get("1H"));
	if (mapaEdad40.containsKey("2H")) g2H = Integer.parseInt(mapaEdad40.get("2H"));
	if (mapaEdad40.containsKey("3H")) g3H = Integer.parseInt(mapaEdad40.get("3H"));
	if (mapaEdad40.containsKey("4H")) g4H = Integer.parseInt(mapaEdad40.get("4H"));
	if (mapaEdad40.containsKey("5H")) g5H = Integer.parseInt(mapaEdad40.get("5H"));
	if (mapaEdad40.containsKey("6H")) g6H = Integer.parseInt(mapaEdad40.get("6H"));
	
	if (mapaEdad40.containsKey("1M")) g1M = Integer.parseInt(mapaEdad40.get("1M"));
	if (mapaEdad40.containsKey("2M")) g2M = Integer.parseInt(mapaEdad40.get("2M"));
	if (mapaEdad40.containsKey("3M")) g3M = Integer.parseInt(mapaEdad40.get("3M"));
	if (mapaEdad40.containsKey("4M")) g4M = Integer.parseInt(mapaEdad40.get("4M"));
	if (mapaEdad40.containsKey("5M")) g5M = Integer.parseInt(mapaEdad40.get("5M"));
	if (mapaEdad40.containsKey("6M")) g6M = Integer.parseInt(mapaEdad40.get("6M"));
	
	tot1H += g1H;	tot1M += g1M;
	tot2H += g2H;	tot2M += g2M;
	tot3H += g3H;	tot3M += g3M;
	tot4H += g4H;	tot4M += g4M;
	tot5H += g5H;	tot5M += g5M;
	tot6H += g6H;	tot6M += g6M;	
%>		
		<tr>
			<td width="16%">Edad > 40</td>
			<td width="6%"><%=g1H%></td>
			<td width="6%"><%=g1M%></td>
			<td width="6%"><%=g2H%></td>
			<td width="6%"><%=g2M%></td>
			<td width="6%"><%=g3H%></td>
			<td width="6%"><%=g3M%></td>
			<td width="6%"><%=g4H%></td>
			<td width="6%"><%=g4M%></td>
			<td width="6%"><%=g5H%></td>
			<td width="6%"><%=g5M%></td>
			<td width="6%"><%=g6H%></td>
			<td width="6%"><%=g6M%></td>
			<td width="6%"><%=g1H+g2H+g3H+g4H+g5H+g6H%></td>
			<td width="6%"><%=g1M+g2M+g3M+g4M+g5M+g6M%></td>
		</tr>		
		<tr>
			<th width="16%">TOTALES</th>
			<th width="6%"><%=tot1H%></th>
			<th width="6%"><%=tot1M%></th>
			<th width="6%"><%=tot2H%></th>
			<th width="6%"><%=tot2M%></th>
			<th width="6%"><%=tot3H%></th>
			<th width="6%"><%=tot3M%></th>
			<th width="6%"><%=tot4H%></th>
			<th width="6%"><%=tot4M%></th>
			<th width="6%"><%=tot5H%></th>
			<th width="6%"><%=tot5M%></th>
			<th width="6%"><%=tot6H%></th>
			<th width="6%"><%=tot6M%></th>
			<th width="6%"><%=tot1H+tot2H+tot3H+tot4H+tot5H+tot6H%></th>
			<th width="6%"><%=tot1M+tot2M+tot3M+tot4M+tot5M+tot6M%></th>
		</tr>
	</table>
	<table class="table table-bordered">
		<tr class="alert alert-success">
			<th colspan="4">Inscritos por edad y genero de primer grado</th>
		</tr>
		<tr>
			<th width="25%">Edad</th>						
			<th width="25%">Hombre</th>
			<th width="25%">Mujer</th>
			<th width="25%">Total</th>			
		</tr>
<%
	sumHombre = 0; sumMujer=0;	
	for (int edad=Integer.parseInt(minEdad); edad<=29; edad++){
%>
		<tr>
			<td><%=edad%></td>	
	<%			
			String datoH = "0";
			if (mapaGenero.containsKey(String.valueOf(edad)+"H") ){
				datoH = mapaGenero.get(String.valueOf(edad)+"H");
				sumHombre+=Integer.parseInt(datoH);
			}
			String datoM = "0";
			if (mapaGenero.containsKey(String.valueOf(edad)+"M") ){
				datoM = mapaGenero.get(String.valueOf(edad)+"M");
				sumMujer+=Integer.parseInt(datoM);
			}
	%>		
			<td><%=datoH%></td>
			<td><%=datoM%></td>
			<td><%=Integer.parseInt(datoH)+Integer.parseInt(datoM)%></td>
		</tr>			
	<%		
	} 
	%>	
		
	</table>
	
	<table class="table table-bordered">		
		<tr>
			<td width="25%">Edad 30-34</td>	
	<%			
			String datoH = "0";
			if (mapaGenero30.containsKey("H") ){
				datoH = mapaGenero30.get("H");
				sumHombre += Integer.parseInt(datoH);
			}
			String datoM = "0";
			if (mapaGenero30.containsKey("M") ){
				datoM 	= mapaGenero30.get("M");
				sumMujer += Integer.parseInt(datoM);
			}
	%>		
			<td width="25%"><%=datoH%></td>
			<td width="25%"><%=datoM%></td>
			<td width="25%">&nbsp;</td>
		</tr>		
	</table>
	
	<table class="table table-bordered">		
		<tr>
			<td width="25%">Edad 35-39</td>	
	<%			
			datoH = "0";
			if (mapaGenero35.containsKey("H") ){
				datoH = mapaGenero35.get("H");
				sumHombre += Integer.parseInt(datoH);
			}
			datoM = "0";
			if (mapaGenero35.containsKey("M") ){
				datoM = mapaGenero35.get("M");
				sumMujer += Integer.parseInt(datoM);
			}
	%>		
			<td width="25%"><%=datoH%></td>
			<td width="25%"><%=datoM%></td>
			<td width="25%">&nbsp;</td>
		</tr>		
	</table>
	
	<table class="table table-bordered">		
		<tr>
			<td width="25%">Edad > 40</td>	
	<%			
			datoH = "0";
			if (mapaGenero40.containsKey("H") ){
				datoH = mapaGenero40.get("H");
				sumHombre += Integer.parseInt(datoH);
			}
			datoM = "0";
			if (mapaGenero40.containsKey("M") ){
				datoM = mapaGenero40.get("M");
				sumMujer += Integer.parseInt(datoM);
			}
	%>		
			<td width="25%"><%=datoH%></td>
			<td width="25%"><%=datoM%></td>
			<td width="25%">&nbsp;</td>
		</tr>
		<tr>
			<th>Total</th>						
			<th><%=sumHombre%></th>
			<th><%=sumMujer%></th>			
			<th><%=sumHombre+sumMujer%></th>
		</tr>		
	</table>
	
	<table class="table table-bordered">
		<tr class="alert alert-success">
			<th colspan="4">Inscritos por edad y genero de reingreso</th>
		</tr>
		<tr>
			<th width="25%">Edad</th>						
			<th width="25%">Hombre</th>
			<th width="25%">Mujer</th>			
			<th width="25%">Total</th>
		</tr>
<%
	// Buscar el minimo de años
	sumHombre = 0; sumMujer=0;
	for (int edad=Integer.parseInt(minEdad); edad<=29; edad++){
%>
		<tr>
			<td><%=edad%></td>	
	<%			
			datoH = "0";
			if (mapaGeneroReingreso.containsKey(String.valueOf(edad)+"H") ){
				datoH = mapaGeneroReingreso.get(String.valueOf(edad)+"H");
				sumHombre += Integer.parseInt(datoH);
			}
			datoM = "0";
			if (mapaGeneroReingreso.containsKey(String.valueOf(edad)+"M") ){
				datoM = mapaGeneroReingreso.get(String.valueOf(edad)+"M");
				sumMujer += Integer.parseInt(datoM);
			}
	%>		
			<td><%=datoH%></td>
			<td><%=datoM%></td>
			<td><%=Integer.parseInt(datoH)+Integer.parseInt(datoM)%></td>
		</tr>			
	<%		
	} 
	%>		
	</table>
	
	<table class="table table-bordered">		
		<tr>
			<td width="25%">Edad 30-34</td>	
	<%			
			datoH = "0";
			if (mapaGeneroRe30.containsKey("H") ){
				datoH = mapaGeneroRe30.get("H");
				sumHombre += Integer.parseInt(datoH);
			}
			datoM = "0";
			if (mapaGeneroRe30.containsKey("M") ){
				datoM = mapaGeneroRe30.get("M");
				sumMujer += Integer.parseInt(datoM);
			}
	%>		
			<td width="25%"><%=datoH%></td>
			<td width="25%"><%=datoM%></td>
			<td width="25%">&nbsp;</td>
		</tr>		
	</table>
	
	<table class="table table-bordered">		
		<tr>
			<td width="25%">Edad 35-39</td>	
	<%			
			datoH = "0";
			if (mapaGeneroRe35.containsKey("H") ){
				datoH = mapaGeneroRe35.get("H");
				sumHombre += Integer.parseInt(datoH);
			}
			datoM = "0";
			if (mapaGeneroRe35.containsKey("M") ){
				datoM = mapaGeneroRe35.get("M");
				sumMujer += Integer.parseInt(datoM);
			}
	%>		
			<td width="25%"><%=datoH%></td>
			<td width="25%"><%=datoM%></td>
			<td width="25%">&nbsp;</td>
		</tr>		
	</table>
	
	<table class="table table-bordered">		
		<tr>
			<td width="25%">Edad > 40</td>	
	<%			
			datoH = "0";
			if (mapaGeneroRe40.containsKey("H") ){
				datoH = mapaGeneroRe40.get("H");
				sumHombre += Integer.parseInt(datoH);
			}
			datoM = "0";
			if (mapaGeneroRe40.containsKey("M") ){
				datoM = mapaGeneroRe40.get("M");
				sumMujer += Integer.parseInt(datoM);
			}
	%>		
			<td width="25%"><%=datoH%></td>
			<td width="25%"><%=datoM%></td>
			<td width="25%">&nbsp;</td>
		</tr>
		<tr>
			<th>Total</th>						
			<th><%=sumHombre%></th>
			<th><%=sumMujer%></th>			
			<th><%=sumHombre+sumMujer%></th>
		</tr>	
	</table>
</div>
</body>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	jQuery('#Fecha').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>