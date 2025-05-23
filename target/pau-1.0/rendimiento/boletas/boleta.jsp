<%@ page import="java.text.*" %>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	ResultSet rset				= null;
	Statement stmt	 			= conEnoc.createStatement();
	String COMANDO				= "";
	
	String s_ccosto			= "1.01";
	String s_ejercicio		= "001-2016";
	
	DecimalFormat getformato= new DecimalFormat("###,##0.00");
	String s_inicio			= request.getParameter("f_inicial");
	String s_fin			= request.getParameter("f_final");
	String s_carga			= request.getParameter("f_carga");
	String s_carrera		= request.getParameter("f_carrera");
	String s_matricula 		= "X";
	String s_nombre			= "X";
	String s_nombre_carrera	= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,s_carrera);
	String s_deudor			= "";
	String s_periodo		= "X";
	String s_fecha_i		= "X";
	String s_fecha_f		= "X";
	String s_fecha_hoy		= "X";
	String s_año_ejercicio	= "X";
	
	String s_nota			= "";
	String s_extra			= "";
	String s_anotacion		= "";
	
	double n_creditos		= 0;
	double n_cargos			= 0;
	double n_saldo			= 0;
	double n_temp			= 0;
	String s_creditos		= "";
	String s_cargos 		= "";
	String s_saldo			= "";
	String s_naturaleza		= "";
	
	String s_tutor			= "";
	String s_direccion		= "";
	String s_colonia		= "";
	String s_pais			= "";
	String s_estado			= "";
	String s_ciudad			= "";
	String s_codigo_postal	= "";

	int total_row			= 52;
	int n_cont				= 0;
	int n_row				= 0;
	int n_row_disponibles	= 0;
	int n_mov				= 0;
	int n_print				= 0;
		
	Integer salto			= new Integer(100);
	
	java.util.ArrayList<String> lisAlumnos	= new java.util.ArrayList<String>();
	String linea = "";
	
	// INFORMACIÓN DE LA CARGA
	COMANDO = 	"SELECT "+
				"TO_CHAR(now(),'DD/MM/YYYY') AS HOY, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
				"CASE SUBSTR(TO_CHAR(F_INICIO,'DD/MM/YYYY'),4,2) WHEN '01' THEN 'enero' WHEN '02' THEN 'febrero' WHEN '03' THEN 'marzo' WHEN '04' THEN 'abril' WHEN '05' THEN 'mayo' WHEN '06' THEN 'junio' WHEN '07' THEN 'julio' WHEN '08' THEN 'agosto' WHEN '09' THEN 'septiembre' WHEN '10' THEN 'octubre' WHEN '11' THEN 'noviembre' WHEN '12' THEN 'diciembre' ELSE 'enero' END AS MES_INI, "+
				"CASE SUBSTR(TO_CHAR(F_INICIO,'DD/MM/YYYY'),4,2) WHEN '01' THEN 'enero' WHEN '02' THEN 'febrero' WHEN '03' THEN 'marzo' WHEN '04' THEN 'abril' WHEN '05' THEN 'mayo' WHEN '06' THEN 'junio' WHEN '07' THEN 'julio' WHEN '08' THEN 'agosto' WHEN '09' THEN 'septiembre' WHEN '10' THEN 'octubre' WHEN '11' THEN 'noviembre' WHEN '12' THEN 'diciembre' ELSE 'enero' END  AS MES_FIN "+
				"FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID = '"+s_carga+"' ";
	rset = stmt.executeQuery(COMANDO);
	if(rset.next()){
		s_periodo 	= rset.getString("MES_INI")+" a "+rset.getString("MES_FIN");
		s_fecha_i	= rset.getString("F_INICIO");
		s_fecha_f	= rset.getString("F_FINAL");
		s_fecha_hoy = rset.getString("HOY");
		s_año_ejercicio	= "2016"; //s_fecha_hoy.substring(6,10);
	}else{
		s_periodo	= "vacio";
		s_año_ejercicio	= "2016"; //s_fecha_hoy.substring(6,10);
	}
	
	// Select que llena el listor con los alumnos inscritos en la carga seleccionada
	COMANDO = 	"SELECT "+
				"DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "+
				"ENOC.ALUM_NOMBRE(CODIGO_PERSONAL) AS NOMBRE_ALUMNO, "+				
				"ENOC.ES_DEUDOR(CODIGO_PERSONAL, '"+s_ejercicio+"') AS DEUDOR "+
				"FROM ENOC.ESTADISTICA "+
				"WHERE CARGA_ID = '"+s_carga+"' "+
				"AND CARRERA_ID= '"+s_carrera+"' "+
				"AND CODIGO_PERSONAL BETWEEN '"+s_inicio+"' and '"+s_fin+"' "+
				"ORDER BY CODIGO_PERSONAL";	
	rset = stmt.executeQuery(COMANDO);	
	while(rset.next()){	
		s_matricula 	= rset.getString("CODIGO_PERSONAL");
		s_nombre		= rset.getString("NOMBRE_ALUMNO");		
		s_deudor 		= rset.getString("DEUDOR");

		linea =	s_matricula+"~"+
					s_nombre+"~"+					
					s_deudor;
		lisAlumnos.add(linea);
	}

	// inicia ciclo principal
	//System.out.println("El tamaño del listor es de: "+lisAlumnos.size());
	for (int i=0; i<lisAlumnos.size();i++){

		java.util.StringTokenizer s_token = new java.util.StringTokenizer((String) lisAlumnos.get(i), "~");

        s_matricula				= s_token.nextToken();
		s_nombre				= s_token.nextToken();
		s_deudor 				= s_token.nextToken();
%>
<style type="text/css">
<!--
body {
	background-image: url();
}
-->
</style><body bgcolor="#FFFFFF">
<div align="center" title="ejemplo">
<table style="width:100%;margin:0 auto" class="tabla">
<tr>
    <td width="112" align="left" valign="TOP" style="font-size: 7pt;">
	  <div align="left"><a href="javascript:window.print()">
        <img src="../../imagenes/logo.jpg"  height="100" alt="Imprimir" width="100" > 
	  </a></div>	
        <br>
        <br>
        <strong>Dirección de Admisiones y Registro</strong><br>
        Apdo. 16-5 C.P. 67530<br>
        Montemorelos, NL, <br>
		M&eacute;xico<br>
        <br>
        <strong><spring:message code="aca.Tel"/>efonos:</strong><br>
        Directo(826) 263-0908<br>
        Conmutador 263-0900<br>
        Ext. 119,120,121 <br>
        Fax (826) 263-0979<br>
        <br><br>
		<b><spring:message code="aca.Creada"/></b> por el Gobierno<br>
		del estado de Nuevo<br>
		León, México, mediante<br>
		Resolución Oficial<br>
		publicada el 5 de mayo<br>
		de 1973.<br><br>
		<b>Clave de la Institución</b><br>
		ante la SEP y Dirección<br>
		General de EStadística<br>
		19MSU1017U<br> 
	</td>		
	<td width="6" align="CENTER" valign="TOP" style="font-size: 5pt;">
		<img src="../../images/linea.jpg"  width=2 height=450 alt="">
	</td>
	<td width="631" colspan="2" align="JUSTIFY" valign="top">		
        <table style="width:100%">
		  <tr> 
            <td height="10" colspan="6" align="center" valign="top">
			  <font size="1" face="Arial">
			    <strong>U N I V E R S I D A D &nbsp; D E &nbsp; M O N T E M O R E L O S ,  A . C .</strong>
			  </font>			  
			</td>
          </tr>
		  <tr> 
            <td height="10" colspan="6" align="center" valign="top">
			  <font size="1" face="Arial">
			    <strong>BOLETA OFICIAL DE CALIFICACIONES</strong>
			  </font>			  
			</td>
          </tr>
		  <tr> 
            <td height="10" colspan="6" align="center" valign="top">
			  <font size="1" face="Arial">
			    <strong>Curso escolar 20<%=s_carga.substring(0,2)%> - 20<%=s_carga.substring(2,4)%></strong>
			  </font>			  
			</td>
          </tr>
          <tr> 
            <td height="10" colspan="6" align="center" valign="top">
			  <font size="1" face="Arial"><strong>Período académico de <%=s_periodo%></strong></font>			  
			</td>
          </tr>
          <tr><td height="10" colspan="6" align="center" valign="top">&nbsp;</td></tr>
          <tr> 
            <td height="10" colspan="6" align="left">
			<font size="1" face="Arial">
			  Nombre: [ <b><%=s_nombre%></b> ] 
			  &nbsp;&nbsp;&nbsp;Matricula: [ <b><%=s_matricula%></b> ]
			  &nbsp;&nbsp;&nbsp;Programa: [ <b><%=s_nombre_carrera%></b> ]
			</font></td>
          </tr>		  
          <tr bordercolor="#CC0000"> 
            <th width="3%" height="10" align="center"><font size="1" face="Arial"><spring:message code="aca.Numero"/></font></th>
            <th width="60%" height="10" align="left"><font size="1" face="Arial">&nbsp;Clave y Nombre de la Materia</font></th>
            <th width="3%" height="10" align="center"><font size="1" face="Arial">Crd.</font></th>			
            <th width="4%" height="10" align="right"><font size="1" face="Arial">Calif.</font></th>
			<th width="4%" height="10" align="right"><font size="1" face="Arial">Extra.</font></th>
            <th width="26%" height="10" align="left"><font size="1" face="Arial">Anotaciones</font></th>
          </tr>
<% 	
	n_row = 5;	n_cont = 0;
	COMANDO = 	"SELECT "+
				"SUBSTR(CURSO_ID,9,7)||' '||SUBSTR(NOMBRE_CURSO,1,50) AS MATERIA, "+
				"ENOC.MODALIDAD_NOMBRE(MODALIDAD_ID) AS MODALIDAD, "+
				"CREDITOS2 AS CREDITOS_MAT, "+
				"COALESCE(TO_CHAR(NOTA,'999'),'-') AS NOTA, "+
				"COALESCE(TO_CHAR(NOTA_EXTRA,'999'),'-') AS NOTA_EXTRA, "+
				"CASE TIPOCAL_ID WHEN '1' THEN 'ACREDITADO' WHEN '2' THEN 'NO ACREDITADO' WHEN '3' THEN 'BAJA' "+
				"WHEN '4' THEN 'REPROBADO X AUSENCIAS' WHEN '5' THEN 'CALIF. PENDIENTE' WHEN '6' THEN 'CALIF. DIFERIDA' "+
				"WHEN 'I' THEN 'SIN EVALUAR' END AS ANOTACION "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = '"+s_matricula+"' "+
				"AND CARGA_ID = '"+s_carga+"' "+
				"AND TIPOCAL_ID NOT IN ('M')";
	rset = stmt.executeQuery(COMANDO);
	while(rset.next()){ n_cont++;
	// CANCELA RESTRICCION DE VER NOTAS
	/*
		if (s_deudor.equals("S")){
			s_nota = "**";
			s_extra = "**";
			s_anotacion = "Alumno con Deuda";
		}else{
			s_nota = rset.getString("NOTA");
			s_extra = rset.getString("NOTA_EXTRA");
			s_anotacion = rset.getString("ANOTACION");
		}
	*/
		s_nota = rset.getString("NOTA");
		s_extra = rset.getString("NOTA_EXTRA");
		s_anotacion = rset.getString("ANOTACION");
	
%>
          <tr> 
            <td width="3%" height="10" align="center"><font size="1" face="Arial"><%=n_cont%></font></td>
            <td width="60%" height="10">
			  <font size="1" face="Arial">			  
			    <%=rset.getString("MATERIA")%>&nbsp;
			    <font color="#0000CC">[<%=rset.getString("modalidad")%>]</font>			  
			  </font>
			</td>
            <td width="3%" height="10" align="center"><font size="1" face="Arial"><%=rset.getString("CREDITOS_MAT")%></font></td>
            <td width="4%" height="10" align="right"><font size="1" face="Arial"><%=s_nota%></font></td>
			<td width="5%" height="10" align="right"><font size="1" face="Arial"><%=s_extra%></font></td>
            <td width="26%" height="10">&nbsp;<font size="1" face="Arial"><%=s_anotacion%></font></td>
          </tr>
<% 	} %>
          <tr> 
            <td height="10" colspan="6" align="center">_________________________________________________________________</td>
          </tr>
          <tr> 
            <td height="10" colspan="6" align="center">
			<font size="1" face="Arial">
			  Fin de la boleta que incluye<b>[ <%=n_cont%> ]</b> materia(s) matriculada(s)
			</font>
			</td>
          </tr>
        </table>
<%
		n_row = n_row + n_cont+2;
		COMANDO = 	"SELECT ID_EJERCICIO, ID_CCOSTO FROM MATEO.CONT_RELACION "+
					"WHERE ID_AUXILIAR = '"+s_matricula+"' "+
					"AND ID_CTAMAYOR = '1.1.04.01' "+
					"AND SUBSTR(ID_EJERCICIO,5,4) = '"+s_año_ejercicio+"' "+
					"AND STATUS = 'A'";
		rset = stmt.executeQuery(COMANDO);
		if(rset.next()){
			s_ccosto 		= rset.getString("ID_CCOSTO");
			s_ejercicio 	= rset.getString("ID_EJERCICIO");
		}
%>		
      <table style="width:100%" >
        <tr bgcolor="#333333">
          <td height="10" colspan="7" align="center">
		    <font size="1" face="Arial" color="#CCCCCC">
			<strong>E s t a d o &nbsp; d e &nbsp; C u e n t a</strong>
			</font>
		  </td>
        </tr>
        <tr> 
          <td height="10" colspan="7" align="center">
		  <font size="1" face="Arial">
		    <strong>Fecha Inicial: </strong><%=s_fecha_i%> &nbsp; &nbsp; &nbsp; 
		    <strong><spring:message code="aca.FechaFinal"/>: </strong><%=s_fecha_hoy%>
		  </font>	
		  </td>
        </tr>
        <tr bordercolor="#000000"> 
          <th width="3%" height="10" align="center"><font size="1" face="Arial">St</font></th>
          <th width="10%" height="10" align="center"><font size="1" face="Arial">P&oacute;liza</font></th>
          <th width="10%" height="10" align="center"><font size="1" face="Arial"><spring:message code="aca.Fecha"/></font></th>
          <th width="40%" height="10" align="center"><font size="1" face="Arial">Descripci&oacute;n</font></th>
          <th width="10%" height="10" align="center"><font size="1" face="Arial">Cargos</font></th>
          <th width="10%" height="10" align="center"><font size="1" face="Arial">Cr&eacute;ditos</font></th>
          <th width="10%" height="10" align="center"><font size="1" face="Arial"><spring:message code="aca.Saldo"/></font></th>
        </tr>
<%		
		n_row = n_row + 4;
		n_saldo = 0; n_creditos = 0; n_cargos = 0; n_temp=0;
		
		// CALCULA Y DESPLIEGA EL SALDO ANTERIOR
		COMANDO = 	"SELECT SUM(IMPORTE * CASE NATURALEZA WHEN 'D' THEN -1 ELSE 1 END) AS SALDO "+
					"FROM MATEO.CONT_MOVIMIENTO "+
					"WHERE ID_AUXILIARM = '"+s_matricula+"' "+
					"AND ID_CTAMAYORM = '1.1.04.01' "+
					"AND ID_EJERCICIO = '"+s_ejercicio+"' "+
					"AND ( FECHA < TO_DATE('"+s_fecha_i+"','DD/MM/YYYY') OR FOLIO='00000')";
		rset = stmt.executeQuery(COMANDO);
		if(rset.next()){ n_saldo = rset.getDouble("SALDO"); }else{ n_saldo = 0;}		
		
		if (n_saldo < 0){	
			n_cargos = n_saldo * -1;
			s_cargos = getformato.format(n_cargos);
			s_creditos = " ";
		}else{
			n_creditos = n_saldo;
			s_creditos = getformato.format(n_creditos);
			s_cargos = " ";
		}

		// se invierte el signo porque la cuenta es de naturaleza deudora
		n_saldo = n_saldo * -1;
		s_saldo = getformato.format(n_saldo);
%>		
        <tr> 
          <td height="10" colspan="4" align="center"><font size="1" face="Arial">S A L D O &nbsp; A N T E R I O R</font></td>		  
          <td height="10" align="right"><font size="1" face="Arial"><%=s_cargos%></font></td>
          <td height="10" align="right"><font size="1" face="Arial"><%=s_creditos%></font></td>
          <td height="10" align="right"><font size="1" face="Arial"><%=s_saldo%></font></td>
        </tr>
<%		
		n_row_disponibles = total_row-n_row;		
		n_mov = 0;
		// Cuenta la cantidad de Movimientos del alumno
		COMANDO = 	"SELECT COUNT(*) AS MOV "+
					"FROM MATEO.CONT_MOVIMIENTO CM, MATEO.CONT_POLIZA CP "+
					"WHERE CM.ID_AUXILIARM = '"+s_matricula+"' AND CM.ID_CTAMAYORM = '1.1.04.01' "+
					"AND CM.ID_EJERCICIO = '"+s_ejercicio+"' AND CM.FECHA >= TO_DATE('"+s_fecha_i+"','DD/MM/YYYY') "+
					"AND CP.ID_LIBRO||CP.ID_CCOSTO||CP.FOLIO = CM.ID_LIBRO||CM.ID_CCOSTO||CM.FOLIO "+
					"AND CP.ID_EJERCICIO = CM.ID_EJERCICIO "+
					"AND CM.FOLIO != '00000' "+
					"ORDER BY CM.FECHA";
		rset = stmt.executeQuery(COMANDO);
		if(rset.next()){
			n_mov = rset.getInt("mov");
			if (n_mov > n_row_disponibles){
				n_print = n_mov - n_row_disponibles;				
			}else{
				n_print = 0;
			}
		}else{
			n_mov = 0;
		}

		n_cont = 0; //n_row = n_row + 1; 
		// DESPLIEGA LOS MOVIMIENTOS
		COMANDO = 	"SELECT CP.STATUS AS ESTADO, "+
					"CM.ID_LIBRO||CM.FOLIO AS POLIZA, "+
					"TO_CHAR(CM.FECHA,'DD/MM/YYYY') AS FECHA_MOV, "+
					"SUBSTR(CM.DESCRIPCION,1,38) AS DESCRIPCION, "+
					"CM.IMPORTE AS IMPORTE, "+
					"CM.NATURALEZA AS NATURALEZA "+
					"FROM MATEO.CONT_MOVIMIENTO CM, MATEO.CONT_POLIZA CP "+
					"WHERE CM.ID_AUXILIARM = '"+s_matricula+"' "+
					"AND CM.ID_CTAMAYORM = '1.1.04.01' "+
					"AND CM.ID_EJERCICIO = '"+s_ejercicio+"' "+
					"AND CM.FECHA >= TO_DATE('"+s_fecha_i+"','DD/MM/YYYY') "+
					"AND CP.ID_LIBRO||CP.ID_CCOSTO||CP.FOLIO = CM.ID_LIBRO||CM.ID_CCOSTO||CM.FOLIO "+
					"AND CP.ID_EJERCICIO = CM.ID_EJERCICIO "+
					"AND CM.FOLIO != '00000' "+
					"ORDER BY CM.FECHA";
		rset = stmt.executeQuery(COMANDO);
		while(rset.next()){
			n_cont++;
			n_cargos = 0; n_creditos = 0; n_temp = 0;
			s_naturaleza = rset.getString("naturaleza");

			if (s_naturaleza.equals("D")){
				n_cargos = rset.getDouble("importe");
				s_cargos = getformato.format(n_cargos); s_creditos = "-";
				n_saldo = n_saldo + n_cargos;
			}else{
				n_creditos = rset.getDouble("importe");
				s_creditos = getformato.format(n_creditos); s_cargos = "-";
				n_saldo = n_saldo - n_creditos;
			}			
			s_saldo = getformato.format(n_saldo);			
			if (n_cont > n_print ){ 
%>
		<tr>
		  <td height="10" align="center"><font size="1" face="Arial"><%=rset.getString("estado")%></font></td>
          <td height="10" align="center"><font size="1" face="Arial"><%=rset.getString("poliza")%></font></td>
          <td height="10" align="center"><font size="1" face="Arial"><%=rset.getString("fecha_mov")%></font></td>
		  <td height="10" align="left"><font size="1" face="Arial"><%=rset.getString("descripcion")%></font></td>
		  <td height="10" align="right"><font size="1" face="Arial"><%=s_cargos%></font></td>
		  <td height="10" align="right"><font size="1" face="Arial"><%=s_creditos%></font></td>
		  <td height="10" align="right"><font size="1" face="Arial"><%=s_saldo%></font></td>
		</tr>
<%
			}
		}
		if (n_mov > n_row_disponibles){ 
			n_row = n_row + n_row_disponibles;
		}else{
			n_row = n_row + n_cont;
		}
		n_cont = n_row;
%>		
      </table>
	  <table style="width:100%">
<%		while (n_cont < total_row){ n_cont++; %>
		<tr><td height="10">-<%//n_cont%></td></tr>
<%		}%>
	  </table>
<%		
		COMANDO ="SELECT "+
				"T_NOMBRE, "+
				"T_DIRECCION, "+
				"T_COLONIA, "+
				"NOMBRE_CIUDAD(T_PAIS,T_ESTADO,T_CIUDAD) AS CIUDAD_NAME, "+
				"ENOC.NOMBRE_ESTADO(T_PAIS,T_ESTADO)  AS ESTADO_NAME, "+
				"ENOC.NOMBRE_PAIS(T_PAIS) AS PAIS_NAME, "+
				"'C.P. '||T_CODIGO AS CODIGO_P "+
				"FROM ENOC.ALUM_UBICACION "+ 
				"Where CODIGO_PERSONAL = '"+s_matricula+"'";
		rset = stmt.executeQuery(COMANDO);		
		if (rset.next()){
			s_tutor 		= rset.getString("T_NOMBRE");
			s_direccion		= rset.getString("T_DIRECCION");
			s_colonia		= rset.getString("T_COLONIA");
			s_pais 			= rset.getString("PAIS_NAME");
			s_estado 		= rset.getString("ESTADO_NAME");
			s_ciudad 		= rset.getString("CIUDAD_NAME");
			s_codigo_postal	= rset.getString("CODIGO_P");
		}else{
			s_tutor 		= "--";
			s_direccion		= "--";
			s_colonia		= "--";
			s_pais 			= "--";
			s_estado 		= "--";
			s_ciudad 		= "--";
			s_codigo_postal	= "--";
		} 
%>
<!--
		<table style="width:100%"  bordercolor="#000000">
		  <tr>
          <td width="56%" valign="top">
            <table style="width:100%" >
              <tr><td height="10"><font size="1" face="Arial"><%=s_tutor%></font></td></tr>
			  <tr><td height="10"><font size="1" face="Arial"><%=s_direccion%></font></td></tr>
			  <tr><td height="10"><font size="1" face="Arial">Colonia &nbsp;<%=s_colonia%></font></td></tr>
			  <tr><td height="10"><font size="1" face="Arial"><%=s_ciudad%>, <%=s_estado%>, <%=s_codigo_postal%></font></td></tr>
			  <tr><td height="10"><font size="1" face="Arial"><%=s_pais%>.</font></td></tr>			  
            </table> 
		  </td>
		  <td width="44%" align="center" valign="top">
		    <table style="width:100%" >
              <tr><td height="10">&nbsp;</td></tr>
			  <tr><td height="10">&nbsp;</td></tr>
			  <tr><td height="10" align="center"><font size="1" face="Arial">______________________________</font></td></tr>
			  <tr><td height="10" align="center"><font size="1" face="Arial">Dra. Ruth Hernández V.</font></td></tr>
			  <tr><td height="10" align="center"><font size="1" face="Arial">Directora de Registro</font></td></tr>
            </table>
          </td>
		  </tr>  
		</table>
-->				
	</td>
	</tr>
</table>
<br>
</div>
<%	
	} // fin del ciclo principal
	lisAlumnos= null;
%>

<!-- fin de estructura -->
<%
	if (rset!=null){ rset.close(); }
	if (stmt!=null){ stmt.close(); }
%>---
<%@ include file = "../../cierra_enoc.jsp"%>