<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="mapaCredito" scope="page" class="aca.plan.MapaCredito"/>
<jsp:useBean id="mapaCurso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MapaCursoU" class="aca.plan.CursoUtil" scope="page"/>
<jsp:useBean id="alumnoCursoUtil" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="cursoUtil" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="tipoCal" scope="page" class="aca.catalogo.TipoCalUtil"/>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="planU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="planUtil" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="cancelaEstudio" scope="page" class="aca.alumno.CancelaEstudio"/>
<jsp:useBean id="cancelaEstudioU" scope="page" class="aca.alumno.CancelaEstudioUtil"/>  
<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<script type="text/javascript">	
	function recarga(){
		document.forma.submit();
	}
</script>
<%
	String matricula 	= (String) session.getAttribute("codigoAlumno");
	String planActual   = request.getParameter("plan");
	String firma	 	= request.getParameter("firma")==null?"con":request.getParameter("firma");
	ArrayList planes	= planU.getPlanesAlumno(conEnoc,matricula);
	if(planActual==null){
		plan.mapeaRegId(conEnoc,matricula);
		planActual=plan.getPlanId();
	}
	else {
		plan.mapeaRegId(conEnoc,matricula,planActual);
		planActual=plan.getPlanId();
	}


	

	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.0;(###,##0.0)");
	java.text.NumberFormat nf 	= java.text.NumberFormat.getInstance();
	
	nf.setMinimumFractionDigits(2); 
	nf.setMaximumFractionDigits(2);
	
	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);
	String planid			=(String)request.getParameter("plan");
	String institucion 		=(String)session.getAttribute("institucion");
	
	if(planid==null){
		plan.mapeaRegId(conEnoc,matricula);
		planid=plan.getPlanId();
	}
	else plan.mapeaRegId(conEnoc,matricula,planid);
	
	String escala 		= plan.getEscala();
	
	if(!cancelaEstudioU.existeRegId(conEnoc, matricula, plan.getPlanId())){ 
	
	ArrayList lisCursosAlumno = alumnoCursoUtil.getListAlumno(conEnoc,matricula," AND PLAN_ID='"+planid+"' AND TIPOCAL_ID IN ('1') AND CREDITOS !=0 ORDER BY CICLO, NOMBRE_CURSO");
	
	String colorNota 	= "";
	String tipoCurso 	= "";
	String iniEm		= "";
	String finEm		= "";
	String matConv		= "";
	String strFecha		= "";
	
	Parametros.mapeaRegId(conEnoc, "1");
	
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
  		<select name="plan" onchange='javascript:recarga()'>
<%		for(int i=0;i<planes.size();i++){%>
  			<option value='<%=(String)planes.get(i)%>' <%if(planActual.equals((String)planes.get(i)))out.print("Selected");%> ><%=planUtil.getNombrePlan(conEnoc,(String)planes.get(i))%></option>
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
      			<b>Matrícula:</b> <%=matricula%>  <b><spring:message code="aca.Nombre"/>:</b> <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>
      			<br><b>Plan Académico:</b> <%=plan.getPlanId()%> <b><spring:message code='aca.Carrera'/>:</b> <%=aca.plan.PlanUtil.getCarreraSe(conEnoc, plan.getPlanId())%>
      		</font>
      	  </td>
      	</tr>
<%		int i,j,contCurso=0,contSem=0;
		String semestre;
		boolean nuevaFila=false,OKc,OKe,OKd,Salir=false;
		if(lisCursosAlumno.size()>0)alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(contCurso);
		semestre 				= alumnoCurso.getCiclo().trim();
		double sumaNota[] 		= new double[20];
		double sumaCreditos[] 	= new double[20];
		int totalMaterias 		= aca.plan.CursoUtil.totalMateriasObligatorias(conEnoc, planid, "S");
		int totCursoObligatorio	= aca.plan.CursoUtil.getNumCredPlanObligatorio(conEnoc, planid, "S");
		int totalCreditos 		= Integer.parseInt(aca.plan.MapaAvanceUtil.getCreditosPlan(conEnoc, planid));
		
		int numCursos=0,cursosAprobados=0,cursosDesaprobados=0,cursosRetirados=0;
		float creditosAprobados=0,creditosDesaprobados=0;
		while (!Salir && lisCursosAlumno.size()>0){
%>
        <tr valign="top">
<%			nuevaFila=false;
			while (!nuevaFila){
%>
          <td width="53%" colspan="3">
            <table style="width:100%"    class="tabla" style="border:1px solid gray;">
              <tr>
                <th><%=aca.plan.CreditoUtil.getTitulo(conEnoc, plan.getPlanId(), semestre) %></th>
                <th width="8%"><span class="Estilo3"><spring:message code="aca.Nota"/></span></th>
                <th width="8%">Cr.</th>
                <th width="8%"><spring:message code="aca.Fecha"/></th>
                <th width="8%"><spring:message code="aca.Extra"/></th>
              </tr>              
<%				while (alumnoCurso.getCiclo().equals(semestre)){
					OKc=OKe=false;numCursos++; iniEm = ""; finEm=""; matConv = "";
					tipoCurso = aca.plan.CursoUtil.getTipoCurso(conEnoc,alumnoCurso.getCursoId());
					
					if (!alumnoCurso.getNotaExtra().equals("0") && alumnoCurso.getNotaExtra()!=null && !alumnoCurso.getNotaExtra().equals("null")) OKe=true;
					if (!alumnoCurso.getNota().equals("0") || alumnoCurso.getConvalidacion().equals("S") || tipoCurso.equals("8")) OKc=true;				
					if (  (tipoCurso.equals("1")||tipoCurso.equals("2")||tipoCurso.equals("7")||tipoCurso.equals("9")) && 
							(alumnoCurso.getConvalidacion().equals("N") || alumnoCurso.getConvalidacion().equals("I")) ){
						if (OKe){
							sumaNota[Integer.parseInt(semestre)]+=Integer.parseInt(alumnoCurso.getNotaExtra())*Float.parseFloat(alumnoCurso.getCreditos());
							sumaCreditos[Integer.parseInt(semestre)]+=Float.parseFloat(alumnoCurso.getCreditos());
						}else{
							sumaNota[Integer.parseInt(semestre)]+=Integer.parseInt(alumnoCurso.getNota())*Float.parseFloat(alumnoCurso.getCreditos());
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
					String tipoCursoId = aca.plan.CursoUtil.getTipoCurso(conEnoc, alumnoCurso.getCursoId());
					
					String nota 	   	= "";
					double notaDouble	= 0;
%>						
              <tr class="tr2">
                <td><%=matConv%> <%=iniEm%><%=alumnoCurso.getNombreCurso()%><%=finEm%></td>
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
						alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(contCurso);									
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
				semestre = alumnoCurso.getCiclo();
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
                      <td align="right"><%=totalMaterias - numCursos%></td>
                    </tr>
                    <tr>
                      <td>Creditos AC:</td>
                      <td align="right"><%=creditosAprobados%></td>
                    </tr>  
                    <tr>
                      <td>Creditos Pendientes:</td>
                      <td align="right"><%=totalCreditos - creditosAprobados%></td>
                    </tr>
                </table></td>
<%			if (cursosRetirados>0){ %>
                <td width="33%" align="center">
                  <table     width="200">
                    <tr>
                      <th colspan="2">Cursos de Baja</th>
                    </tr>
                    <tr  bgcolor="#CCCCCC">
                      <td width="20%" align="center"><b>Sem.</b></td>
                      <td><b>Curso</b></td>
                    </tr>
<%							for (int x=0;x<lisCursosAlumno.size();x++){
								alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(x);		
								if (alumnoCurso.getTipoCalId().equals("3")){
%>
                    <tr>
                      <td align="center"><font size="1"><%=alumnoCurso.getCiclo()%></font></td>
                      <td><font size="1"><%=alumnoCurso.getNombreCurso()%></font></td>
                    </tr>
<%								}
							}
%>
                </table></td>
<%			}
			if (cursosDesaprobados>0){
%>
                <td width="33%" align="center">
                  <table style="width:240"    >
                    <tr>
                      <th colspan="3">Cursos Desaprobados</th>
                    </tr>
                    <tr bgcolor="#CCCCCC">
                      <td width="26"><strong>Sem.</strong></td>
                      <td width="92"><strong>Curso</strong></td>
                      <td width="26" align="center"><strong>lises</strong></td>
                    </tr>
<%				for (int x=0;x<lisCursosAlumno.size();x++){
					alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(x);		
					if (alumnoCurso.getTipoCalId().equals("2")){
%>
                    <tr>
                      <td><font size="1"><%=alumnoCurso.getCiclo()%></font></td>
                      <td><font size="1"><%=alumnoCurso.getNombreCurso()%></font></td>
                      <td align="center"><font size="1"><%=alumnoCursoUtil.getListesTipo(conEnoc,matricula,alumnoCurso.getCursoCargaId(),"2")%></font></td>
                    </tr>
<%					}
				}
%>
                </table></td>
<%			} // if cursos desaprobados
%>
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
       	<table style="margin: 0 auto;  width:213">
	            <%if(firma.equals("con")){ %>
	              <tr>
	              <td>&nbsp;&nbsp;&nbsp;</td>
	              	<td>
						<div align="center" style="WIDTH: 100px; HEIGHT: 65px">&nbsp;<img src="../../imagenes/firmaJoseMendez.png" alt="Imprimir" width="222" height="140">              	
	              	</td>
	              </tr>
	            <%} %>
	        <tr>
	          <td class="center" colspan="10">
	          	  
	              <font size="2">_________________________________________</font></td>
	        </tr>
	        <tr>
	          <td align="center" colspan="10"><font size="2">Ing. Jose H. Mendez Willson</font></td>
	        </tr>
	        <tr>
	          <td align="center" colspan="10"><font size="2">Director de Servicios Escolares</font></td>
	        </tr>
	 	 </table>	
      </table>
    </td>    
  </tr>
</table>
<%}else{
%>
<table style="width:100%; margin:0 auto">
<tr><td align="center"><br><br><br><font size="3">
Tus estudios han sido cancelados por el siguiente motivo:<br><%cancelaEstudio.mapeaRegId(conEnoc, matricula, plan.getPlanId()); out.print(cancelaEstudio.getComentario()); %></font>
</td></tr>
</table>
<%
} %>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>