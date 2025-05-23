<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.plan.spring.MapaCredito"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.parametros.spring.Parametros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function recarga(){
		document.forma.submit();
	}
</script>
<%	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.0;(###,##0.0)");
	java.text.NumberFormat nf 	= java.text.NumberFormat.getInstance();
	
	nf.setMinimumFractionDigits(2); 
	nf.setMaximumFractionDigits(2);
	
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	String institucion 		= (String)session.getAttribute("institucion");
	String firma		 	= request.getParameter("firma")==null?"con":request.getParameter("firma");

	String planId  			= (String) request.getAttribute("planId");
	String alumnoNombre 	= (String) request.getAttribute("alumnoNombre");
	String carreraNombre 	= (String) request.getAttribute("carreraNombre");
	AlumPlan alumPlan 		= (AlumPlan) request.getAttribute("alumPlan");
	Parametros parametros	= (Parametros) request.getAttribute("parametros");
	boolean cancelaEstudio	= (boolean) request.getAttribute("cancelaEstudio");
	String cancelaComentario= (String) request.getAttribute("cancelaComentario");
	
	int totalMaterias 		= (int) request.getAttribute("totalMaterias");
	int totalObligatorios	= (int) request.getAttribute("totalObligatorios");
	int totalCreditos		= (int) request.getAttribute("totalCreditos");
	
	List<String> lisPlanes				= (List<String>)request.getAttribute("lisPlanes");	
	List<AlumnoCurso> lisCursosAlumno 	= (List<AlumnoCurso>)request.getAttribute("lisCursosAlumno");
	List<MapaCurso> lisCursosCarrera 	= (List<MapaCurso>) request.getAttribute("lisCursosCarrera");
	
	HashMap<String,MapaPlan> mapaPlanes 	= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,MapaCurso> mapaCursos 	= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursos");
	HashMap<String,MapaCredito> mapaCreditos= (HashMap<String,MapaCredito>) request.getAttribute("mapaCreditos");
	
	String escala 			= alumPlan.getEscala();
	
	if(!cancelaEstudio){
	
		String colorNota 	= "";		
		String iniEm		= "";
		String finEm		= "";
		String matConv		= "";
		String strFecha		= "";	
		MapaCurso curso 	= new MapaCurso();
%>
<head><link href="../../academico.css" rel="STYLESHEET" type="text/css">
  
</head>
<STYLE TYPE="text/css">
.tabbox
	{
		font-family: Arial, Helvetica, sans-serif;
		background: #eeeeee;
	}
.Estilo1 {font-size: 7pt}
.Estilo3 {color: #000000}
</STYLE>
<div class="oculto">
<table style='margin:0 auto;'    >
  	<form name="forma" action="promediof" method='post'>
  		<select name="PlanId" onchange='javascript:recarga()'>
<%		
		for(String plan : lisPlanes){
			String planNombre = "-";
			if (mapaPlanes.containsKey(plan)){
				planNombre = mapaPlanes.get(plan).getNombrePlan();
			}
%>
  			<option value='<%=plan%>' <%=planId.equals(plan)?"Selected":""%> ><%=planNombre%></option>
<%		}%>
  		</select> &nbsp; 
  		<select name="firma" onchange='javascript:recarga()'>
          <option value="con" <%if(firma.equals("con"))out.print("Selected");%>> Con Firma </option>
          <option value="sin" <%if(firma.equals("sin"))out.print("Selected");%>> Sin Firma </option>
        </select>
  		<font size="2" color="#3A1354">
		Nota: * Las materias convalidadas no se toman en cuenta en el cálculo del promedio.
		</font>
  	</form>
</table>
<br>
</div>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" background="../../imagenes/back.gif">
<table class="tabbox" style="background:white;" width="100%" height="99%" cellspacing="5"  align="center">
  <tr valign="top">
    <td width="107" style="font-size: 8pt;" align="left">
      <img src='../../imagenes/logo.jpg' width="99" height="105" onclick="window.print();" style="cursor:pointer;"/><br>
        <br>
        <br>
        <span class="Estilo1"><strong>Dirección de Registro</strong><br>
      Apdo. 16-5 C.P. 67530<br>
      Montemorelos, NL, <br>
      M&eacute;xico<br>
      <br>
      <strong><spring:message code="aca.Tel"/>éfonos:</strong><br>
      Directo(826) 263-0908<br>
      Conmutador 263-0900<br>
      Ext. 119,120,121 <br>
      Fax (826) 263-0979<br>
      <br>
      <b><spring:message code="aca.Creada"/></b> por el Gobierno<br>
      del estado de Nuevo<br>
      León, México, mediante<br>
      Resolución Oficial<br>
      publicada el 5 de mayo<br>
      de 1973.<br>
      <br>
      <b>Clave de la Institución</b><br>
      ante la SEP y Dirección<br>
      General de Estadística<br>
      19MSU1017U </span><br>
    </td>
    <td width="10"><img src='../../imagenes/linea.jpg'/></td>
    <td width="800">
      <table style='margin:0 auto;'   cellspacing='1' >
        <tr>
          <td align="center" colspan="10"><font size="5"><b><%=institucion.toUpperCase()%></b></font></td>
        </tr>
        <tr>
          <td align="center" colspan="10"><font size="4">Registro Académico<br></font></td>
        </tr>      
      	<tr><td colspan='10' align='center'><font size="2"><b>&nbsp;</b></font></td></tr>
      	<tr>
      	  <td colspan='10' align='center'>
      		<font size="2">
      			<b>Matrícula:</b> <%=matricula%>  <b><spring:message code="aca.Nombre"/>:</b> <%=alumnoNombre%>
      			<br><b>Plan Académico:</b> <%=alumPlan.getPlanId()%> <b><spring:message code='aca.Carrera'/>:</b> <%=carreraNombre%>
      		</font>
      	  </td>
      	</tr>
<%		int i,j,contCurso=0,contSem=0;
		String semestre="0";
		boolean nuevaFila=false,OKc,OKe,OKd,Salir=false;
		
		if(lisCursosCarrera.size()>0) curso = (MapaCurso) lisCursosCarrera.get(contCurso);
		AlumnoCurso alumnoCurso = new AlumnoCurso();
		if(lisCursosAlumno.size()>0){
			alumnoCurso = (AlumnoCurso) lisCursosAlumno.get(contCurso);
		}	
		semestre 				= alumnoCurso.getCiclo();		
		double sumaNota[] 		= new double[20];
		double sumaCreditos[] 	= new double[20];
		
		int numCursos=0,cursosAprobados=0,cursosDesaprobados=0,cursosRetirados=0;
		float creditosAprobados=0,creditosDesaprobados=0;
		while (!Salir && lisCursosAlumno.size()>0){
%>
        <tr valign="top">
<%			nuevaFila=false;
			while (!nuevaFila){

				String tituloCiclo	= "-";
				if (mapaCreditos.containsKey(planId+semestre)){
					tituloCiclo = mapaCreditos.get(planId+semestre).getTitulo();
				}
%>
          <td width="53%" colspan="3">
            <table style="width:100%"   border="0" class="tabla" style="border:1px solid gray;">
              <tr>
                <th><%=tituloCiclo%></th>
                <th width="8%"><span class="Estilo3"><spring:message code="aca.Nota"/></span></th>
                <th width="8%">Cr.</th>
                <th width="8%"><spring:message code="aca.Fecha"/></th>
                <th width="8%"><spring:message code="aca.Extra"/></th>
              </tr>              
<%
				while (semestre.equals(alumnoCurso.getCiclo().trim())){
					OKc=OKe=false;numCursos++; iniEm = ""; finEm=""; matConv = "";
					
					String tipoCursoId = "0";
					if (mapaCursos.containsKey(alumnoCurso.getCursoId())){
						tipoCursoId = mapaCursos.get(alumnoCurso.getCursoId()).getTipoCursoId();
					}				

					if (!alumnoCurso.getNotaExtra().equals("0") && alumnoCurso.getNotaExtra()!=null && !alumnoCurso.getNotaExtra().equals("null")) OKe=true;
					if (!alumnoCurso.getNota().equals("0") || alumnoCurso.getConvalidacion().equals("S") || tipoCursoId.equals("8")) OKc=true;
					
					if (  (tipoCursoId.equals("1")||tipoCursoId.equals("2")||tipoCursoId.equals("7")||tipoCursoId.equals("9")) && 
							(alumnoCurso.getConvalidacion().equals("N") || alumnoCurso.getConvalidacion().equals("I")) ){
						
						if (OKe){
							
							sumaNota[Integer.parseInt(semestre)]+=Integer.parseInt(alumnoCurso.getNotaExtra())*Float.parseFloat(alumnoCurso.getCreditos());
							sumaCreditos[Integer.parseInt(semestre)]+=Float.parseFloat(alumnoCurso.getCreditos());
						}else{
							
							sumaNota[Integer.parseInt(semestre)]+=Integer.parseInt(alumnoCurso.getNota().trim())*Float.parseFloat(alumnoCurso.getCreditos().trim());
							
							sumaCreditos[Integer.parseInt(semestre)]+=Float.parseFloat(alumnoCurso.getCreditos());
							
						}
					}else{
						iniEm = "<em>"; finEm="</em>"; matConv = "*";
					}	
					
					if (alumnoCurso.getTipoCalId().equals("1")){
						cursosAprobados++;
						creditosAprobados+=Float.parseFloat(alumnoCurso.getCreditos());
					}else if (alumnoCurso.getTipoCalId().equals("2")){
						cursosDesaprobados++;
						creditosDesaprobados+=Float.parseFloat(alumnoCurso.getCreditos());
					}else if (alumnoCurso.getTipoCalId().equals("2")) cursosRetirados++;
					
					if (alumnoCurso.getEstado().equals("I")){ 
						colorNota = "color='#000000'"; // #66CC00
					}else{ 
						colorNota = "color='#0000FF'";
					}					
					if (alumnoCurso.getTitulo().equals("S")){
						strFecha = alumnoCurso.getFTitulo();
					}else if ( OKc==true ){
						strFecha = alumnoCurso.getFEvaluacion();
					}else{
						strFecha = alumnoCurso.getFExtra();
					}
					
					String nota 	   	= "";
					double notaDouble	= 0;
%>						
              <tr class="tr2">
                <td>
<%					if(curso.getEstado().equals("0")){%>
                	<strong>
<%					}%>
                	<%=matConv%> <%=iniEm%><%=alumnoCurso.getNombreCurso()%><%=finEm%>
<%					if(curso.getEstado().equals("0")){%>
                	</strong>
<%					}%>
                </td>
                <td align="center">
                  <font size="1" <%=colorNota%>>
                  <%if(tipoCursoId.equals("8")){
                	  if(alumnoCurso.getTipoCalId().equals("1")){
                		  nota="AC";
                	  }else if (alumnoCurso.getTipoCalId().equals("I")){
                		  nota="Inscrito";
                		  strFecha = " ";
                	  }else{
                		  nota="NA";
                	  } 
                  }else if(alumnoCurso.getConvalidacion().equals("S")){
                	  	  nota="AC";
                  }else{
                	  if (escala.equals("10")){                		  
                		  nota =  getFormato.format( Double.valueOf(String.valueOf(Float.parseFloat(alumnoCurso.getNota())/10)) );     		
                	  }else{
                	  	nota = alumnoCurso.getNota(); 
                	  }
                	  
                  }
                  
                  %>
                  <%if(OKc)out.print(nota); else out.print("&nbsp;");%>
                  </font>
                </td>
                <td align="center"><%=alumnoCurso.getCreditos()%></td>
                <td align="center"><%= strFecha %></td>
                <td align="center"><%if(OKe){
                						if(escala.equals("10")){
						              		out.print(Float.parseFloat(alumnoCurso.getNotaExtra())/10);
                						}
                						else{
                							out.print(alumnoCurso.getNotaExtra());
                						}
					              	}
               						else out.print("&nbsp;");%></td>
              </tr>
<%					if (++contCurso < lisCursosAlumno.size()){
						alumnoCurso = (AlumnoCurso) lisCursosAlumno.get(contCurso);
					}else{
						nuevaFila=true;
						Salir=true;
						break;
					}
				} // Mientras sea el mismo cilco 
				contSem++;
				double promMes = sumaNota[Integer.parseInt(semestre)]/sumaCreditos[Integer.parseInt(semestre)];
%>
              <tr valign="">
                <td colspan="5"><b>Promedio:
<%
				if (sumaCreditos[Integer.parseInt(semestre)]>0){
					if (escala.equals("10")){
						out.print(nf.format(promMes/10));
					}
					else{
						out.print(nf.format(promMes));
					}
				}else out.print("0.00");
%>
                </b> </td>
              </tr>
          </table></td>
<%
				semestre = alumnoCurso.getCiclo().trim();
				if (contSem%2==0) nuevaFila=true;								
			} // While nueva fila						
%>
        </tr>
<%		}  //While principal que recorre el listor %> 
        <tr>
          <td height="18" colspan="10">&nbsp;</td>
        </tr>
        <tr align="center" valign="top">
          <td align="center" colspan="10">
            <table style="width:100%">
              <tr>
                <td width="40%" align="center">
                  <table     class="tabla" style="border:1px solid gray;">
                    <tr>
                      <th colspan="2">Resumen</th>
                    </tr>                    
                    <tr>
                      <td><strong>Ponderado Global:&nbsp;&nbsp;</strong></td>
                      <td align="right">
                      <strong>
<%						for (int x=1;x<20;x++) sumaNota[0]+=sumaNota[x];
							for (int x=1;x<20;x++) sumaCreditos[0]+=sumaCreditos[x];
							if (escala.equals("10")){
								out.print(nf.format((sumaNota[0]/sumaCreditos[0])/10));
							}
							else{
								out.print(nf.format(sumaNota[0]/sumaCreditos[0]));
							}
%>
					  </strong>
					 </td>
                    </tr>
                    <tr>
                      <td>Total de Cursos:</td>
                      <td align="right"><%=totalMaterias%></td>
                    </tr>
                    <tr>
                      <td>Aprobados:</td>
                      <td align="right"><%=cursosAprobados%></td>
                    </tr>  
                    <tr>
                      <td>Pendientes:</td>
                      <td align="right"><% if (numCursos <= totalMaterias) out.print(totalMaterias-numCursos); else out.print("0");%></td>
                    </tr>
                    <tr>
                      <td>Creditos AC:</td>
                      <td align="right"><%=creditosAprobados%></td>
                    </tr>  
                    <tr>
                      <td>Creditos Pendientes:</td>
                      <td align="right"><% if (creditosAprobados <= totalCreditos) out.print(totalCreditos -creditosAprobados); else out.print("0"); %></td>
                    </tr>
                </table></td>
              </tr>
          </table></td>
        </tr>
        <%if(firma.equals("sin")){ %>
           <table>
            <br>
            <br>
            <br>
            <br>
            <br>
            </table>
            <%} %> 
       	<table style="margin: 0 auto;  width:193" border="0">
	            <%if(firma.equals("con")){ %>
	              <tr>
	              <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	              	<td>
						<div align="center" style="WIDTH: 100px; HEIGHT: 65px">&nbsp;<img src="../../imagenes/firma.png" alt="Imprimir" width="172" height="90" border="0" >              	
	              	</td>
	              </tr>
	            <%} %>
	        <tr>
	          <td align="center" colspan="10">
	          	  
	              <font size="2">_________________________________________</font></td>
	        </tr>
	        <tr>
	          <td align="center" colspan="10"><font size="2"><%=parametros.getCardex()%></font></td>
	        </tr>
	        <tr>
	          <td align="center" colspan="10"><font size="2">Directora de Gestión Académica y Registro Escolar</font></td>
	        </tr>
	 	 </table>	
      </table>
    </td>    
  </tr>
</table>
<%

}else{
%>
<table style="width:100%; margin:0 auto">
<tr><td align="center"><br><br><br><font size="3">
Tus estudios han sido cancelados por el siguiente motivo:<br><%=cancelaComentario%></font>
</td></tr>
</table>
<%
} %>
</body>