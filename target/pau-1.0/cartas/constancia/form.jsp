<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.util.Fecha"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="aca.parametros.spring.Parametros"%>
<%@page import="java.util.Calendar"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function recarga(){
		document.forma.submit();
	}
</script>
<%
	String codigoPersonal 	= (String)session.getAttribute("codigoAlumno");
	String planCombo	 	= (String)request.getAttribute("planCombo");
	String planId 			= (String)request.getAttribute("planId");	
	String nombreAlumno 	= (String)request.getAttribute("nombreAlumno");
	String nombreFacultad	= (String)request.getAttribute("nombreFacultad");
	String nombreCarrera	= (String)request.getAttribute("nombreCarrera");
	int nacionalidad		= (int)request.getAttribute("nacionalidad");
	String carreraId 		= (String)request.getAttribute("carreraId");
	String nivelId			= (String)request.getAttribute("nivelId");
	int numSem				= (int)request.getAttribute("numSem");	
	String nacion 			= (String)request.getAttribute("nacion");	
	
	String periodo			= (String)request.getAttribute("periodo");
	String curso			= (String)request.getAttribute("curso");
	String semestre			= "1";
	String periodoTipo		= "";	
	
	boolean	tieneFM3 		= (boolean)request.getAttribute("tieneFM3");    
	Parametros parametros 	= (Parametros)request.getAttribute("parametros");    
    boolean cambioCarrera	= (boolean)request.getAttribute("cambioCarrera");
    
    List<String> listaPlanes				= (List<String>)request.getAttribute("listaPlanes");    
    HashMap<String,String> mapCarreraPlan 	= (HashMap<String,String>)request.getAttribute("mapCarreraPlan");
    HashMap<String,String> mapNombrePlan 	= (HashMap<String,String>)request.getAttribute("mapNombrePlan");
    
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
	
	String facultad = (String)request.getAttribute("facultad");
	
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
%>
<div class="container-fluid">
	<h2>Constancia de Estudios</h2>
	<form name="forma" action="form" method='post'>
	<div class="alert alert-info">
		<select name="plan" onchange='javascript:recarga()' style="width:350px;">
	<%	for(String plan : listaPlanes){	
			String carrera = "";
			if(mapCarreraPlan.containsKey(plan)){
				carrera = mapCarreraPlan.get(plan);
			}
			String nombrePlan = "";
			if(mapNombrePlan.containsKey(plan)){
				nombrePlan = mapNombrePlan.get(plan);
			}
	%>
	  			<option value='<%=plan%>'  <%if(planCombo.equals(plan))out.print("Selected");%>>
	  			 <%=carrera%> - <%=nombrePlan%>
	  			</option>
	<%	}	%>
	  		</select>
	</div>
	</form>
<%  if (nacionalidad == 91 || tieneFM3 && cambioCarrera==false){
%>
	<form name="datos1" action="show" method="POST"> 		
	<table class="table table-sm">
	<tr>
		<th> 
        	<div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2"> 
          		<input type="text" class="input-xxlarge" name="f_destinatario" size="80" maxlength="110" value="Instituto Nacional de Migraci&oacute;n &lt;br&gt;Delegaci&oacute;n Monterrey &lt;br&gt;Monterrey N.L. &lt;br&gt;&lt;br&gt;&lt;br&gt;A QUIEN CORRESPONDA:">
          		</font></b>
          	</div>
    	</th>
	</tr>
	<tr>		
      	<td> <font face="Arial, Helvetica, sans-serif" size="2">La que suscribe, 
        	<%=parametros.getConstancias()%>, Vicerrectora Académica Asociada de la Universidad 
        	De Montemorelos, por este medio <b>HACE CONSTAR que</b>:</font><br>
		</td>
	</tr>
	<tr>
		<td><b><%=nombreAlumno%></b><br><br></td>
	</tr>
<%
if(nivelId.equals("2")||nivelId.equals("3")||nivelId.equals("4")){ %>
<tr>		
      <td> 
        <textarea name="f_comentario" cols="60" rows="8" class="textarea span7">
con número de matrícula <b><% out.print(codigoPersonal); %></b>, es alumno(a) de la <b><%=nombreFacultad%></b> dependiente de esta Universidad, en la carrera de <b><%=nombreCarrera%>. </b>
Durante el período académico de <%=periodo%> del curso escolar <%=curso%>, se inscribió en el <%=semestre%> <%=periodoTipo%> de su programa académico.</textarea>
      </td>
</tr>
<%}
if(nivelId.equals("1")){%>
<tr>		
      <td align="CENTER"> 
        <textarea name="f_comentario" cols="60" rows="8" class="textarea span7">
con número de matrícula <b><% out.print(codigoPersonal); %></b>, es alumno(a) de la <b>Escuela Preparatoria</b> dependiente de esta Universidad, en el plan de estudios de <b><%=nombreCarrera%>. </b><br><br>
Durante el período académico de <%=periodo%> del curso escolar <%=curso%>, se inscribió en el <%=semestre%> <%=periodoTipo%> de su programa académico.</textarea>
      </td>
</tr>
<%}
if(nivelId.equals("5")){%>
<tr>		
      <td align="CENTER"> 
        <textarea name="f_comentario" cols="60" rows="8" class="textarea span7">
con número de matrícula <b><% out.print(codigoPersonal); %></b>, es alumno(a) de <b>Educación Continua</b> dependiente de esta Universidad, en <b><%=nombreCarrera%>. </b>
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
        <input type="text" class="input input-moremini" name="f_dia" size="3" maxlength="3" value="<%=numDia%>">
        </font></b>d&iacute;as del mes de<b><font face="Arial, Helvetica, sans-serif" size="2"> 
        <b><font face="Arial, Helvetica, sans-serif" size="2"> <b><font face="Arial, Helvetica, sans-serif" size="2"><b><font face="Arial, Helvetica, sans-serif" size="2">
        <input type="text" class="input input-small" name="f_mes" size="10" maxlength="10" value="<%=strMes%>">
        </font></b></font></b></font></b></font></b>del a&ntilde;o <b><font face="Arial, Helvetica, sans-serif" size="2"> 
        <input type="text" class="input input-mini" name="f_year" size="4" maxlength="4" value="<%=Integer.toString(anio)%>">
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
			<br><input type="Submit" class="btn btn-primary" value="Enviar">
		</td>
</tr>
</table>
</form>
<%	
	} 
	//System.out.println("Datos:"+nacionalidad+":"+tieneFM3+":"+cambioCarrera);
	if(nacionalidad != 91){
		
		if(tieneFM3 == false || cambioCarrera){
%>
<form name="datos1" action="show" method="POST"> 		
<table   class="tabla">
<tr>
	  <th align="CENTER"> 
        <div align="left"><b><font face="Arial, Helvetica, sans-serif" size="2"> 
          <input type="text" class="text" name="f_destinatario" size="80" maxlength="110" value="Instituto Nacional de Migraci&oacute;n &lt;br&gt;Delegaci&oacute;n Monterrey &lt;br&gt;Monterrey N.L. &lt;br&gt;&lt;br&gt;&lt;br&gt;A QUIEN CORRESPONDA:">
          </font></b></div>
      </th>
</tr>
<tr>		
      <td> <font face="Arial, Helvetica, sans-serif" size="2">La que suscribe, 
        <%=parametros.getConstancias()%>, Directora de Gestión Académica y Registro Escolar de la Universidad 
        De Montemorelos, por este medio <b>HACE CONSTAR que</b>:</font><br>
		</td>
</tr>
<tr>		
      <td align="CENTER"> 
        <textarea name="f_comentario" cols="60" rows="8"><b><%=nombreAlumno%></b> de nacionalidad <%=nacion%>, está inscrito de manera <b>CONDICIONADA</b>, entre tanto regulariza su situación migratoria, en el <%=semestre%> <%=periodoTipo%> de la carrera de <b><%=nombreCarrera%>,
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
		<td style="text-align:center;">
			<br><input type="Submit" class="btn btn-primary" value="Enviar">
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
</div>