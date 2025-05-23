<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<%@page import="aca.util.Fecha"%>
<jsp:useBean id="mapaPlan" scope="page" class="aca.plan.MapaPlan"/>
<jsp:useBean id="MapaPlanU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="alumUtil" scope="page" class="aca.alumno.AlumUtil"/>

<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<%	
	String codigoPersonal 	= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno 	= "";
	String facultad			= "";
	String carrera			= "";	
	String planId			= "";
	
	String periodo			= Fecha.getPeriodoMes(Fecha.getHoy());	
	String curso			= aca.util.Fecha.getPeriodoActual();
	String semestre			= "1";
	int numSem				= 1;

	mapaPlan = MapaPlanU.mapeaRegId(conEnoc, alumUtil.getPlanActivo(conEnoc, codigoPersonal));
	Parametros.mapeaRegId(conEnoc, "1");
%>
<br>
<%
	// Varaible Date
	java.util.Date fechaHoy		= new java.util.Date();
	
	// Obtiene el dia en dos caracteres '00'
	int dia				= fechaHoy.getDate();
	String strDia		= "X";
	if (dia<10){
		strDia = '0' + Integer.toString(dia); 
	}else{ 
		strDia = Integer.toString(dia);
	}	
	
	// Nombre del mes
	int mes						= fechaHoy.getMonth()+1;
	String strMes = aca.util.Fecha.getMesNombre(mes).toLowerCase();
	
	// Año actual
	int anio				= fechaHoy.getYear() + 1900;

	planId 			= aca.alumno.PlanUtil.getPlanActual(conEnoc,codigoPersonal);
	nombreAlumno 	= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,codigoPersonal,"NOMBRE");		
	numSem 			= Integer.parseInt(aca.alumno.PlanUtil.getSem(conEnoc,codigoPersonal,planId));
	
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
	
	// Codigos de facultad y carrera del alumno
	carrera 	= aca.plan.PlanUtil.getCarreraId(conEnoc,planId);	
	facultad 	= aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc,carrera);
		
	// Periodos de prepa	
	if ( facultad.equals("107") ){
		
		if (mes >= 8 && mes <=10){
			periodo="agosto-noviembre";			
		}else if (mes==11){
			if (dia<15) periodo = "agosto-noviembre";
			if (dia>=15) periodo = "noviembre-marzo";			
		}else if (mes==12||mes<=3){
			periodo = "noviembre-marzo";			
		}else if (mes>=4 && mes<=7){
			periodo = "marzo-Junio";			
		}		
		
	}
	
	// Nombres de Facultad y Carrera
	facultad 	= aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,facultad);
	carrera		= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,carrera);

%>
<form name="datos1" action="show" method="POST"> 		
<table   align="center">
<tr>
  <td align="center" colspan="10"><br>Carga:
	<select name="CargaId" onchange="location='form?CargaId='+this.options[this.selectedIndex].value;">
<%
			String cargaId		= request.getParameter("CargaId");
			if (cargaId==null) cargaId = aca.carga.CargaUtil.getMejorCarga(conEnoc,codigoPersonal);
			aca.carga.CargaUtil cargaU = new aca.carga.CargaUtil();
			ArrayList lisCarga 	= cargaU.getListAlumno(conEnoc, codigoPersonal);
			String sCargaId="";
			int i=0;
			for( i=0;i<lisCarga.size();i++){
				aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);
				if (i==0) sCargaId = carga.getCargaId();
				if (carga.getCargaId().equals(cargaId)){
					out.print(" <option value='"+carga.getCargaId()+"' Selected>"+ carga.getNombreCarga()+"</option>");					
				}else{
					out.print(" <option value='"+carga.getCargaId()+"'>"+ carga.getNombreCarga()+"</option>");
				}				
			}
			if (lisCarga.size()==1){
				cargaId = sCargaId;
			}
			lisCarga 	= null;
			cargaU		= null;			
 %>
	</select>
  </td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
  <th align="CENTER"> 
    <div align="left">
    <b><font face="Arial, Helvetica, sans-serif" size="2"> 
    <input type="text" class="text" name="f_destinatario" size="80" maxlength="100" value="A QUIEN CORRESPONDA:">
    </font></b>
    </div>
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
  <textarea name="f_comentario" cols="60" rows="8"><b><%=nombreAlumno%></b>, con número de matrícula <b><% out.print(codigoPersonal); %></b>, es alumno(a) de la <b><%=facultad%></b>, con RVOE <b><%=mapaPlan.getRvoe() %></b>, dependiente de esta Universidad, en la carrera de <b><%=carrera%>. </b>
Durante el período académico de <%=periodo%> del curso escolar <%=curso%>, inscribió y cursó las siguientes materias obteniendo las calificaciones como se establecen a continuación:</textarea>
  </td>
</tr>
<tr>
  <td> <font face="Arial, Helvetica, sans-serif" size="2">A petici&oacute;n de quien 
    lo solicita y para los fines y usos que convengan, se le extiende la 
    presente <b>CONSTANCIA</b>, <br>
    en la ciudad de Montemorelos, Nuevo Le&oacute;n, M&eacute;xico, a los 
    <b><font face="Arial, Helvetica, sans-serif" size="2"> 
    <input type="text" class="text" name="f_dia" size="3" maxlength="3" value="<%=strDia%>">
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
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>