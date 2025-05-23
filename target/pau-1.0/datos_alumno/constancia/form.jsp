<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@page import="aca.util.Fecha"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>

<jsp:useBean id="extranjero" scope="page" class="aca.leg.LegExtranjero"/>
<jsp:useBean id="extranjeroU" scope="page" class="aca.leg.LegExtranjeroUtil"/>
<jsp:useBean id="alumU" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<%
	String codigoPersonal 	= (String) session.getAttribute("codigoAlumno")==null?(String) session.getAttribute("codigoPersonal"):(String) session.getAttribute("codigoAlumno");
	
	String nombreAlumno 	= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,codigoPersonal,"NOMBRE");	
	String planId			= aca.alumno.PlanUtil.getPlanActual(conEnoc,codigoPersonal);
	boolean esNuevo			= aca.alumno.AlumUtil.esNuevoIngreso(conEnoc, codigoPersonal, planId);
	int nacionalidad		= (Integer.parseInt(aca.alumno.AlumUtil.getNacionalidad(conEnoc, codigoPersonal)));
	if(String.valueOf(nacionalidad) == null) nacionalidad = 0 ;
	String carreraId		= aca.plan.PlanUtil.getCarreraId(conEnoc,planId);
	String nivelId			= aca.alumno.PlanUtil.getCarreraNivel(conEnoc,carreraId);
	int numSem				= Integer.parseInt(aca.alumno.PlanUtil.getSem(conEnoc,codigoPersonal,planId));
	
	String periodo			= Fecha.getPeriodoMes(Fecha.getHoy());
	String curso			= Fecha.getPeriodoActual();	
	String semestre			= "1";
	String periodoTipo		= "";	
	String carrera			= "";
	String facultad			= "";
	
	boolean	tieneFM3 		= false;
	
	java.text.SimpleDateFormat sdf 	= new java.text.SimpleDateFormat("dd/MM/yyyy");	
	java.util.Date fechaHoy 		= sdf.parse(aca.util.Fecha.getHoy());
	java.util.Date fechaFM3 		= sdf.parse(aca.leg.LegExtdocUtil.getFechaVenceFM3(conEnoc, codigoPersonal));
    if (fechaHoy.before(fechaFM3)) { tieneFM3 = true; }
    Parametros.mapeaRegId(conEnoc, "1");
    
    boolean cambioCarrera	= false;
    String carreraFM3		= "";
    String carreraAlum = alumU.getCarreraIdCodigo(conEnoc,codigoPersonal);
    extranjero.setCodigo(codigoPersonal);
    if(extranjeroU.existeReg(conEnoc,codigoPersonal)){
    	extranjero.mapeaRegId(conEnoc, codigoPersonal);
    	carreraFM3 = extranjero.getCarrera();
    	if(!carreraAlum.equals(carreraFM3)){
        	cambioCarrera=true;
        }   	
    }

%>
<br>
<%
	Calendar fecha = new GregorianCalendar();
	
	int numDia = fecha.get(Calendar.DAY_OF_MONTH);
	String strDia	= "X";
	if (numDia<10){
		strDia = '0' + Integer.toString(numDia);
	}else{ 
		strDia = Integer.toString(numDia);
	}
	
	int numMes			= fecha.get(Calendar.MONTH)+1;
	String strMes 	= Fecha.getMesNombre(numMes).toLowerCase();
	
	int anio		= fecha.get(Calendar.YEAR);	
	
	switch(numSem){
		case 1:  semestre = "primer"; break;
        case 2:  semestre = "segundo"; break;
        case 3:  semestre = "tercer"; break;
        case 4:  semestre = "cuarto"; break;
        case 5:  semestre = "quinto"; break;
        case 6:  semestre = "sexto"; break;
        case 7:  semestre = "séptimo"; break;
        case 8:  semestre = "octavo"; break;
        case 9:  semestre = "noveno"; break;
        case 10: semestre = "décimo"; break;
        case 11: semestre = "undécimo"; break;
	}
	
if 	( !codigoPersonal.substring(0,1).equals("9")){

	// Codigos de facultad y carrera del alumno
	carrera 	= aca.plan.PlanUtil.getCarreraId(conEnoc,planId);
	facultad 	= aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc,carrera);
		
	if ( facultad.equals("107") ){
		if (numMes >= 8 && numMes <=10){
			periodo="agosto-noviembre";
		}else if (numMes==11){
			if (numDia<15) periodo = "agosto-noviembre";
			if (numDia>=15) periodo = "noviembre-marzo";
		}else if (numMes==12||numMes<=3){
			periodo = "noviembre-marzo";
		}else if (numMes>=4 && numMes<=7){
			periodo = "marzo-Junio";
		}
		
		periodoTipo 	= "tetramestre";
	}else{
		periodoTipo 	= "semestre";
	}
	
	// Nombres de Facultad y Carrera
	facultad 	= aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,facultad);
	carrera		= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,carrera);	

%> 
<%  if (nacionalidad == 91 || tieneFM3 && cambioCarrera==false){
	
%>
<form name="datos1" action="show" method="POST"> 		
<table   align="center">
<tr>
	  <th align="CENTER"> 
        <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2"> 
          <input type="text" class="text" name="f_destinatario" size="80" maxlength="110" value="Instituto Nacional de Migraci&oacute;n &lt;br&gt;Delegaci&oacute;n Monterrey &lt;br&gt;Monterrey N.L. &lt;br&gt;&lt;br&gt;&lt;br&gt;A QUIEN CORRESPONDA:">
          </font></b></div>
      </th>
</tr>
<tr>		
      <td> <font face="Arial, Helvetica, sans-serif" size="2">La que suscribe, 
        <%=Parametros.getConstancias()%>, Directora de Gestión Académica y Registro Escolar de la Universidad 
        De Montemorelos, por este medio <b>HACE CONSTAR que</b>:</font><br>
		</td>
</tr>
<tr align="center">
	<td><b><%=nombreAlumno%></b><br><br></td>
</tr>
<%
if(nivelId.equals("2")||nivelId.equals("3")||nivelId.equals("4")){ %>
<tr>		
      <td align="CENTER"> 
        <textarea name="f_comentario" cols="60" rows="8">
con número de matrícula <b><% out.print(codigoPersonal); %></b>, es alumno(a) de la <b><%=facultad%></b> dependiente de esta Universidad, en la carrera de <b><%=carrera%>. </b>
Durante el período académico de <%=periodo%> del curso escolar <%=curso%>, se inscribió en el <%=semestre%> <%=periodoTipo%> de su programa académico.</textarea>
      </td>
</tr>
<%}
if(nivelId.equals("1")){%>
<tr>		
      <td align="CENTER"> 
        <textarea name="f_comentario" cols="60" rows="8">
con número de matrícula <b><% out.print(codigoPersonal); %></b>, es alumno(a) de la <b>Escuela Preparatoria</b> dependiente de esta Universidad, en el plan de estudios de <b><%=carrera%>. </b><br><br>
Durante el período académico de <%=periodo%> del curso escolar <%=curso%>, se inscribió en el <%=semestre%> <%=periodoTipo%> de su programa académico.</textarea>
      </td>
</tr>
<%}
if(nivelId.equals("5")){%>
<tr>		
      <td align="CENTER"> 
        <textarea name="f_comentario" cols="60" rows="8">
con número de matrícula <b><% out.print(codigoPersonal); %></b>, es alumno(a) de <b>Educación Continua</b> dependiente de esta Universidad, en <b><%=carrera%>. </b>
Está inscrito(a) en su programa durante el período académico de <%=periodo%> del curso escolar <%=curso%>.</textarea>
      </td>
</tr>
<% }%>
<tr>		
      <td> <font face="Arial, Helvetica, sans-serif" size="2">A petición de quien 
        lo solicita y para los fines y usos que convengan, se le extiende la 
        presente <b>CONSTANCIA</b>, <br>
        en la ciudad de Montemorelos, Nuevo Le&oacute;n, M&eacute;xico, a los 
        <b><font face="Arial, Helvetica, sans-serif" size="2"> 
        <input type="text" class="text" name="f_dia" size="3" maxlength="3" value="<%=numDia%>">
        </font></b>d&iacute;as del mes de<b><font face="Arial, Helvetica, sans-serif" size="2"> 
        <b><font face="Arial, Helvetica, sans-serif" size="2"> <b><font face="Arial, Helvetica, sans-serif" size="2"><b><font face="Arial, Helvetica, sans-serif" size="2">
        <input type="text" class="text" name="f_mes" size="10" maxlength="10" value="<%=strMes%>">
        </font></b></font></b></font></b></font></b>del a&ntilde;o <b><font face="Arial, Helvetica, sans-serif" size="2"> 
        <input type="text" class="text" name="f_year" size="4" maxlength="4" value="<%=Integer.toString(anio)%>">
        </font></b>.</font><br>
		</td>
</tr>
<tr>
	<td>
	  <select name="firma" id="firma">
		<option value="con" Selected> Con Firma </option>
        <option value="sin"> Sin Firma </option>          
      </select>		
    </td>
</tr>
<tr>		
		<input type='Hidden' name='f_codigo_personal' value='<%=codigoPersonal%>'>
		<td align="center">
			<br><input type="Submit" value="Enviar">
		</td>
</tr>
</table>
</form>
<%   } %>
<% 
	if(nacionalidad != 91){ 
	 if(esNuevo || tieneFM3 == false || cambioCarrera){
%>
<form name="datos1" action="show" method="POST"> 		
<table   align="center">
<tr>
	  <th align="CENTER"> 
        <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2"> 
          <input type="text" class="text" name="f_destinatario" size="80" maxlength="110" value="Instituto Nacional de Migraci&oacute;n &lt;br&gt;Delegaci&oacute;n Monterrey &lt;br&gt;Monterrey N.L. &lt;br&gt;&lt;br&gt;&lt;br&gt;A QUIEN CORRESPONDA:">
          </font></b></div>
      </th>
</tr>
<tr>		
      <td> <font face="Arial, Helvetica, sans-serif" size="2">La que suscribe, 
        <%=Parametros.getConstancias()%>, Directora de Gestión Académica y Registro Escolar de la Universidad 
        De Montemorelos, por este medio <b>HACE CONSTAR que</b>:</font><br>
		</td>
</tr>
<tr>		
      <td align="CENTER"> 
        <textarea name="f_comentario" cols="60" rows="8"><b><%=nombreAlumno%></b> de nacionalidad <%= aca.catalogo.PaisUtil.getNacionalidad(conEnoc,String.valueOf(nacionalidad))%>, está inscrito de manera <b>CONDICIONADA</b>, entre tanto regulariza su situación migratoria, en el <%=semestre%> <%=periodoTipo%> de la carrera de <b><%=carrera%>,
</b> durante el período académico de <%=periodo%> del curso escolar <%=curso%>.</textarea>
      </td>
</tr>
<tr>		
      <td> <font face="Arial, Helvetica, sans-serif" size="2">Para los fines que se estime conveniente, se
        firma y sella la presente, <br>
        en la ciudad de Montemorelos, Nuevo Le&oacute;n, M&eacute;xico, a los 
        <b><font face="Arial, Helvetica, sans-serif" size="2"> 
        <input type="text" class="text" name="f_dia" size="3" maxlength="3" value="<%=numDia%>">
        </font></b>d&iacute;as del mes de<b><font face="Arial, Helvetica, sans-serif" size="2"> 
        <b><font face="Arial, Helvetica, sans-serif" size="2"> <b><font face="Arial, Helvetica, sans-serif" size="2"><b><font face="Arial, Helvetica, sans-serif" size="2">
        <input type="text" class="text" name="f_mes" size="10" maxlength="10" value="<%=strMes%>">
        </font></b></font></b></font></b></font></b>del a&ntilde;o <b><font face="Arial, Helvetica, sans-serif" size="2"> 
        <input type="text" class="text" name="f_year" size="4" maxlength="4" value="<%=Integer.toString(anio)%>">
        </font></b>.</font><br>
		</td>
</tr>
<tr>
	<td>
	  <select name="firma" id="firma">
		<option value="con" Selected> Con Firma </option>
        <option value="sin"> Sin Firma </option>          
      </select>		
    </td>
</tr>
<tr>		
		<input type='Hidden' name='f_codigo_personal' value='<%=codigoPersonal%>'>
		<td align="center">
			<br><input type="Submit" value="Enviar">
		</td>
</tr>
</table>
</form>
<%		}
	} 
}else{
	out.print("<table style='margin:0 auto;'><tr><td><font color='black'>ELIGE A UN ALUMNO..!</font></td></tr></table>");
}
%>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>