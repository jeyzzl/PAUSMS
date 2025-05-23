<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="curso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="alumnoCursoUtil" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="cursoUtil" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="TipoCalU" scope="page" class="aca.catalogo.TipoCalUtil"/>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="planUtil" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="mapaPlan" scope="page" class="aca.plan.MapaPlan"/>
<jsp:useBean id="MapaPlanU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>
<jsp:useBean id="cancelaEstudio" scope="page" class="aca.alumno.CancelaEstudio"/>
<jsp:useBean id="cancelaEstudioU" scope="page" class="aca.alumno.CancelaEstudioUtil"/>
<jsp:useBean id="mapaCredito" scope="page" class="aca.plan.MapaCredito"/>
<jsp:useBean id="Parametros" scope="page" class="aca.parametros.Parametros"/>

<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.0;(###,##0.0)");
	java.text.NumberFormat nf = java.text.NumberFormat.getInstance(); 
	nf.setMinimumFractionDigits(2); 
	nf.setMaximumFractionDigits(1);

	String matricula = (String) session.getAttribute("codigoAlumno");

	alumno = alumnoUtil.mapeaRegId(conEnoc,matricula);
	String planid=(String)request.getParameter("plan");
	
	if(planid==null){
		plan.mapeaRegId(conEnoc,matricula);
		planid=plan.getPlanId();
	}
	else plan.mapeaRegId(conEnoc,matricula,planid);
	
	String escala = plan.getEscala();
	
	if(!cancelaEstudioU.existeRegId(conEnoc, matricula, plan.getPlanId())){ 
	
	String planActual=plan.getPlanId();
	ArrayList lisCursosAlumno = alumnoCursoUtil.getListAlumno(conEnoc,matricula," AND PLAN_ID='"+planid+"' AND TIPOCAL_ID IN ('1','I') AND NOTA !=0 AND CREDITOS !=0 ORDER BY CICLO, NOMBRE_CURSO");
	if(lisCursosAlumno.size()>0)alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(0);
	
	ArrayList lisCursos = cursoUtil.getLista(conEnoc, planid, "AND CREDITOS != 0 ORDER BY CICLO, NOMBRE_CURSO");
	String colorNota = "";
	
	Parametros.mapeaRegId(conEnoc, "1");
	
%><head><link href="../../academico.css" rel="STYLESHEET" type="text/css">
<style type="text/css">
<!--
.Estilo4 {color: #000099}
-->
</style>
</head>
<STYLE TYPE="text/css">
.tabbox
	{
		background: #eeeeee;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
.Estilo1 {font-size: 7pt}
.Estilo3 {color: #000000}
</STYLE>
<%	
	int contCurso=0,contSem=0,totalAcreditados = 0;
	float totalCreditos = 0;
	String semestre;
	String institucion 		= (String)session.getAttribute("institucion");
	
	boolean nuevaFila=false,OKc,OKe,OKd,Salir=false;
		
	semestre = "0";
	double sumaNota[] = new double[20];
	double sumaCreditos[] = new double[20];
	double sumaCreditosAC[] = new double[20];
	double creditosNoValidos[] = new double[20];
	double creditosOptativosAC[] = new double[20];
	int numCursos=0,cursosAprobados=0,cursosDesaprobados=0,cursosRetirados=0;
	float creditosAprobados=0,creditosDesaprobados=0;
	mapaPlan = MapaPlanU.mapeaRegId(conEnoc, alumnoCurso.getPlanId());
	
%>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" background="../../imagenes/back.gif">
<img src="../../foto?Codigo=<%=matricula%>&Tipo=O" style="position: absolute; z-index: 100; left: 20px;" height="127px" />
<table class="tabbo" style="background:white;" width="100%" height="99%" cellspacing="5"  align="center" bordercolor="#CCCCCC">
  <tr valign="top">
    <td width="100%">
      <table style='margin:0 auto;'    >
        <tr>
          <td align="center" colspan="12"><font size="5"><b><%=institucion.toUpperCase()%></b></font></td>
        </tr>
        <tr>
          <td align="center" colspan="12"><font size="4">Registro de control escolar<br>
          </font></td>
        </tr>
        <tr>
          <td align="center" colspan="12"><font size="4">Programa de estudio:&nbsp;
          </font><font size="3"><%=alumnoUtil.getCarrera(conEnoc,matricula, alumnoCurso.getPlanId()) %></font></td>
        </tr>
<%		String fInicio = mapaPlan.getFInicio();
     	
     	if(fInicio == null | fInicio.trim().equals(""))
     		fInicio = "******";
     	else
     		fInicio = fecha.getFechaTexto(fInicio); %>
        <tr>
          <td align="center" colspan="12">Estudios con R.V.O.E. No. <%=mapaPlan.getRvoe() %> de fecha <%=fInicio %><br>otorgado por el Gobierno del Estado de Nuevo León<br><br>&nbsp;</td>
        </tr>
      </table>
      <table  class="fieldbox" width="100%" >
      	<tr>
      		<td width="50%" colspan='1'>
	      		<font size="2">
	      			<%String fechaIngreso = aca.alumno.AlumUtil.getFechaIngreso(conEnoc, matricula, alumnoCurso.getPlanId());
	      			  if(fechaIngreso == null)
	      			  	fechaIngreso = "******";
	      			  else
	      			  	fechaIngreso = fecha.getFechaTexto(fechaIngreso);%>
	      			<b><spring:message code="aca.Nombre"/>:</b> <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%><br> 
	      			<b>Fecha de Ingreso:</b> <%=fechaIngreso%><br>&nbsp; 
	      		</font>
      		</td>
      		<td width="50%" colspan='1'>
	      		<font size="2">
	      			<b>No. Mat.:</b> <%=matricula%> &nbsp;&nbsp;&nbsp;<b>C&oacute;digo SE:</b> <%=alumno.getCodigoSe()==null?"*******":alumno.getCodigoSe() %><br>
	      			<b>Fecha de Impresi&oacute;n: </b><%=fecha.getFechaTexto(aca.util.Fecha.getHoy()) %><br>
	      		</font>
      		</td>
      	</tr>
      	<tr valign="top"><td><table>
<%			
		boolean cursado;
		int cont = 0;
		
		for(int j = 0; j < lisCursos.size(); j++){	//Ciclo que recorre (el listor) todos los cursos de la carrera
			aca.plan.MapaCurso mapaCurso = (aca.plan.MapaCurso) lisCursos.get(j);
			nuevaFila=false;
			cursado = false;
			for(int i = 0; i < lisCursosAlumno.size(); i++){//Ciclo que ubica el curso con el mismo ya cursado por el alumno
				alumnoCurso = (aca.vista.AlumnoCurso) lisCursosAlumno.get(i);
				if(mapaCurso.getCursoId().equals(alumnoCurso.getCursoId())){
					i = lisCursos.size();
					cursado = true;
				}
			}
			
			if(!semestre.equals(mapaCurso.getCiclo())){//Condicion de cambio de ciclo
				if(!semestre.equals("0")){//Condicion para que no entre la primera vez
%>
			  <tr>
                <td colspan="24" align="center">
                	<font  color='#FF6600'>Ponderado del ciclo: 
<%
					int nSemestre = Integer.parseInt(semestre);
					double promMes = sumaNota[nSemestre]/((sumaCreditosAC[nSemestre]+creditosOptativosAC[nSemestre])-creditosNoValidos[nSemestre]);
					if (sumaCreditosAC[nSemestre] > 0 || creditosOptativosAC[nSemestre] > 0){
						out.print(nf.format(promMes));
					}else out.print("0.00");
					
					totalCreditos += Float.parseFloat(mapaCredito.getCreditos().trim())+Float.parseFloat(mapaCredito.getOptativos().trim());
					totalAcreditados += sumaCreditosAC[nSemestre] + creditosOptativosAC[nSemestre];
%>
	                &nbsp;Creditos[
	                	Tot: <%=Float.parseFloat(mapaCredito.getCreditos().trim())+Float.parseFloat(mapaCredito.getOptativos().trim()) %>; 
	                	CO: <%=mapaCredito.getCreditos() %>; CE: <%=mapaCredito.getOptativos() %>;] 
	                Faltan[
	                	CO: <%=(float)(Float.parseFloat(mapaCredito.getCreditos().trim())-sumaCreditosAC[nSemestre]) %>; 
	                	CE: <%=Float.parseFloat(mapaCredito.getOptativos().trim())-creditosOptativosAC[nSemestre] %>;
	                ]
	                [<%=((Float.parseFloat(mapaCredito.getCreditos().trim())+Float.parseFloat(mapaCredito.getOptativos().trim()))==(sumaCreditosAC[nSemestre] + creditosOptativosAC[nSemestre]))?"¡Completo!":"¡Incompleto!" %>]</font>
                </td>
              </tr>
<%					
				}//Termina if de primera vez
				mapaCredito.mapeaRegId(conEnoc, mapaCurso.getPlanId(), mapaCurso.getCiclo());
				semestre = mapaCurso.getCiclo().trim();
				cont = 0;
%>
			</table></td>
		</tr>
		<tr>
			<td width="100%" colspan="3">
				<table style="width:100%"  >
				  <tr><td colspan=2><%=aca.plan.CreditoUtil.getTitulo(conEnoc, mapaCurso.getPlanId(), mapaCurso.getCiclo()) %></td></tr>
	              <tr>
	                <th width="2%"><spring:message code="aca.Numero"/></th>
	                <th width="8%"><spring:message code="aca.Clave"/></th>
	                <th widtn="50%"><spring:message code="aca.Nombre"/> de la Materia</th>
	                <th width="3%">Cr.</th>
	                <th width="3%">HT</th>
	                <th width="3%">HP</th>
	                <th width="6%"><span class="Estilo3">Cal</span></th>
	                <th width="5%"><spring:message code="aca.Fecha"/></th>
	                <th width="6%"><spring:message code="aca.Extra"/></th>
	                <th width="8%">F. Extra</th>
	                <th width="3%"><spring:message code="aca.Tipo"/></th>
	                <th width="3%">Edo</th>
	              </tr>
<%
			}//Termina if de cambio de ciclo
			cont++;
			sumaCreditos[Integer.parseInt(semestre)] += Float.parseFloat(mapaCurso.getCreditos());
			String tipoCurso = mapaCurso.getTipoCursoId().trim();
			
			String nota 		= "0";
			String notaExtra	= "0";
			
				
			if(cursado){//Si el ciclo fue cursado por el alumno
				OKc=OKe=false;numCursos++;
				if (!alumnoCurso.getNotaExtra().trim().equals("0"))OKe=true;
				if (!alumnoCurso.getNota().trim().equals("0")) OKc=true;
				
				if(tipoCurso.equals("1") || tipoCurso.equals("2") || tipoCurso.equals("7")){
					if (OKe){
						sumaNota[Integer.parseInt(semestre)]+=Integer.parseInt(alumnoCurso.getNotaExtra())*Float.parseFloat(alumnoCurso.getCreditos());
					}else{
						sumaNota[Integer.parseInt(semestre)]+=Integer.parseInt(alumnoCurso.getNota())*Float.parseFloat(alumnoCurso.getCreditos());
					}
					if(OKe || OKc){
						if(tipoCurso.equals("1"))
							sumaCreditosAC[Integer.parseInt(semestre)]+=Float.parseFloat(alumnoCurso.getCreditos());
						else
							creditosOptativosAC[Integer.parseInt(semestre)] += Float.parseFloat(alumnoCurso.getCreditos());
					}
				}else{
					creditosNoValidos[Integer.parseInt(semestre)] += Float.parseFloat(mapaCurso.getCreditos());
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
				
%>
              <tr class="tr2">
                <td align="center"><%=cont %></td>
                <td><%=alumnoCurso.getCursoId().substring(8) %></td>
                <td><%=alumnoCurso.getNombreCurso() %></td>
                <td align="center"><%= nf.format(Double.parseDouble(alumnoCurso.getCreditos()))%></td>
                <td align="center"><%=alumnoCurso.getHt() %></td>
                <td align="center"><%=alumnoCurso.getHp() %></td>
                <td align="center"><font size="1" >
<%
				if(OKc){
					if (escala.equals("10"))
						nota = getFormato.format( Double.valueOf(String.valueOf(Integer.parseInt(alumnoCurso.getNota())/10)) );
					else
						nota = alumnoCurso.getNota();
					// Imprime la nota de la materia	
					out.print(nota); 
				}else 
					out.print("&nbsp;");
%>
                </font></td>
                <td align="center"><%out.print(alumnoCurso.getFEvaluacion());%></td>
                <td align="center">
                <%
                if(OKe){
                	notaExtra = getFormato.format( Double.valueOf(String.valueOf(Integer.parseInt(alumnoCurso.getNotaExtra())/10)) );
                	out.print(notaExtra);
                }else 
                	out.print("***");%>
               	</td>
                <td align="center"><%if(alumnoCurso.getFExtra()==null) out.print("******"); else out.print(alumnoCurso.getFExtra()); %></td>
                <td align="center"><%
                						if(alumnoCurso.getTipoCalId().trim().equals("1") | alumnoCurso.getTipoCalId().trim().equals("2")){
                							if(alumnoCurso.getTitulo().trim().equals("S"))
                								out.print("TS");
                							else if(alumnoCurso.getConvalidacion().trim().equals("S"))
                								out.print("Conv");
                							else if(alumnoCurso.getNotaExtra().trim().equals("0"))
                								out.print("Ord");
                							else
                								out.print("Ext");
                						}
                						else{
                							if(alumnoCurso.getTipoCalId().trim().equals("3"))
                								out.print("Baja");
                							else if(alumnoCurso.getTipoCalId().trim().equals("4"))
                								out.print("Dif");
                							else if(alumnoCurso.getTipoCalId().trim().equals("5"))
                								out.print("RA");
                						}
                					%></td>
                <td align="center">
                <%
                aca.catalogo.CatTipoCal ctc = new aca.catalogo.CatTipoCal(); 
                ctc = TipoCalU.mapeaRegId(conEnoc, alumnoCurso.getTipoCalId()); out.print(ctc.getNombre());  %></td>
              </tr>
<%					
			}else{//Termina if de cursados
				
				String creditos = "";
				if(mapaCurso.getCreditos().length() <= 1){
					creditos = mapaCurso.getCreditos() + ".0";
				}else{
					creditos = nf.format(Double.parseDouble(mapaCurso.getCreditos()));
				}
%>
				  <tr class="tr2">
	                <td align="center"><%=cont %></td>
	                <td><%=mapaCurso.getCursoId().substring(8) %></td>
	                <td><%=mapaCurso.getNombreCurso() %></td>
	                <td align="center"><%= creditos%></td>
	                <td align="center"><%=mapaCurso.getHt() %></td>
	                <td align="center"><%=mapaCurso.getHp() %></td>
	                <td align="center"><font size="1" >***</font></td>
	                <td align="center">******</td>
	                <td align="center">***</td>
	                <td align="center">******</td>
	                <td align="center">****</td>
	                <td align="center">***</td>
	              </tr>
<%
			}//else de cursados   
			
		}  //for principal que recorre el listor %> 
			<tr valign="">
                <td colspan="24" align="center">
                	<font  color='#FF6600'>Ponderado del ciclo: 
<%
		int nSemestre = Integer.parseInt(semestre);
		double promMes = sumaNota[nSemestre]/((sumaCreditosAC[nSemestre]+creditosOptativosAC[nSemestre])-creditosNoValidos[nSemestre]);
		if (sumaCreditosAC[nSemestre] > 0 || creditosOptativosAC[nSemestre] > 0){
			out.print(nf.format(promMes));
		}else out.print("0.00");
		
		totalCreditos += Float.parseFloat(mapaCredito.getCreditos().trim())+Float.parseFloat(mapaCredito.getOptativos().trim());
		totalAcreditados += sumaCreditosAC[nSemestre] + creditosOptativosAC[nSemestre];
%>
	                &nbsp;Creditos[
	                	Tot: <%=Float.parseFloat(mapaCredito.getCreditos().trim())+Float.parseFloat(mapaCredito.getOptativos().trim()) %>; 
	                	CO: <%=mapaCredito.getCreditos() %>; CE: <%=mapaCredito.getOptativos() %>;] 
	                Faltan[
	                	CO: <%=(float)(Float.parseFloat(mapaCredito.getCreditos().trim())-sumaCreditosAC[nSemestre]) %>; 
	                	CE: <%=Float.parseFloat(mapaCredito.getOptativos().trim())-creditosOptativosAC[nSemestre] %>;
	                ]
	                [<%=((Float.parseFloat(mapaCredito.getCreditos().trim())+Float.parseFloat(mapaCredito.getOptativos().trim()))==(sumaCreditosAC[nSemestre] + creditosOptativosAC[nSemestre]))?"¡Completo!":"¡Incompleto!" %>]</font>
                </td>
            </tr>
		</table></td>
	</tr>

<%//--------------------------------------------------------------------------- %>

        <tr>
          <td height="18" colspan="10">&nbsp;</td>
        </tr>
        <tr align="center" valign="top">
          <td width="100%" colspan="3">
          	<table class="tabla" width="100%"   border='1'>
<%				
				contCurso 	= 0;
				ArrayList lisCAlumno = alumnoCursoUtil.getListAlumno(conEnoc,matricula," AND PLAN_ID='"+planid+"' AND TIPOCAL_ID IN ('1','I') AND CREDITOS =0 AND HT = 0 ORDER BY CURSO_ID, CICLO, NOMBRE_CURSO");
				
				if(lisCAlumno.size() != 0){
%>
				<tr>
					<th width="20%">Componentes de:</th>
					<th align="center">I</th>
					<th align="center">II</th>
					<th align="center">III</th>
					<th align="center">IV</th>
					<th align="center">V</th>
					<th align="center">VI</th>
					<th align="center">VII</th>
					<th align="center">VIII</th>
					<th align="center">IX</th>
					<th align="center">X</th>
				</tr>
<%
					int ciclo = -1,contador = 0,secuencial = 0;
					String fechas[] = new String[10];
					
					for(int s = 0; s < 10; s++)//Llena de Strings vacios el arreglo de fechas
						fechas[s] = "";
					
					for(int j = 0; j < lisCAlumno.size(); j++){//Ciclo que despliega los componentes
						aca.vista.AlumnoCurso alumnoC = (aca.vista.AlumnoCurso) lisCAlumno.get(j);
						
						if(ciclo != Integer.parseInt(alumnoC.getCiclo())){//Si el ciclo es diferente al ciclo del alumno
							
							if(ciclo == -1){//Si es la primera vez
%>
								<tr class="tr2">
<%
							}
							else{
							
								for(int l = 0; l < 10-contador;l++){//Ciclo que llena las casillas vacias
%>
									<td align="center">******</td>
<%
								}
								contador = 0;
%>
								</tr>
								<tr class="tr2">
									<td align="right">Fecha de acreditación</td>
<%								
									for(int k = 0; k < 10; k++){//Ciclo que llena las fechas %>
										<td align="center"><%if(fechas[k] != null && !fechas[k].equals("")) out.print(fechas[k]); else out.print("******"); fechas[k] = "";%></td>
<%									} %>
								</tr>
								<tr class="tr2">
<%
							}//Else de la primera vez
							switch(secuencial){
								case 0:{%><td>Legado Cultural</td><%}break;
								case 1:{%><td>Salud</td><%}break;
								case 2:{%><td>Servicio comunitario</td><%}break;
								case 3:{%><td>Trabajo manual</td><%}break;
							}
							secuencial++;
							ciclo = Integer.parseInt(alumnoC.getCiclo());
							
						}//Termina if de ciclos diferentes
%>
						<td align="center">
						<%						
						aca.catalogo.CatTipoCal ctcc = new aca.catalogo.CatTipoCal(); 
						ctcc = TipoCalU.mapeaRegId(conEnoc, alumnoC.getTipoCalId()); 
						out.print(ctcc.getNombre());  
						fechas[contador] = alumnoC.getFEvaluacion(); 
						contador++;
						%>
						</td>
	<%				}//for principal para desplegarlos componentes
					
				
					for(int l = 0; l < 10-contador;l++){
%>
						<td align="center">******</td>
<%
					}
%>
				</tr>
				<tr class="tr2">
					<td align="right">Fecha de acreditación</td>
<%					
					for(int k = 0; k < 10; k++){ %>
						<td align="center"><%if(fechas[k] != null && !fechas[k].equals("")) out.print(fechas[k]); else out.print("******"); fechas[k] = ""; %></td>
<%					} %>
				</tr>
<%				} %>
          	</table>
          </td>
        </tr>
        <tr>
        	<td colspan="24" align="center"><font  color='#FF6600'><br>
                	&nbsp;Resumen del programa: <%= totalCreditos%> Creditos[ Aprobados: <%=totalAcreditados %>; No Aprobados: <%=totalCreditos-totalAcreditados %>] 
                	Promedio ponderado <%=aca.alumno.AlumUtil.getPromedioFinal(conEnoc,matricula,planid)%> 
                	[<%if(totalCreditos == totalAcreditados) out.print("¡Completo!"); else out.print("¡Incompleto!"); %>]</font></td>
        </tr>
        <tr>
          <td align="center" colspan="10"><br>
              <br>
              <br>
              <br>
              <br>
              <font size="2">_________________________________________</font></td>
        </tr>
        <tr>
          <td align="center" colspan="10"><font size="2"><%=Parametros.getCertificados()%></font></td> 
        </tr>
        <tr>
          <td align="center" colspan="10"><font size="2">Director de Certificación y Archivo</font></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
<%}else{
%>
<table style="width:100%; margin:0 auto">
<tr><td align="center"><br><br><br><font size="3">
Tus estudios han sido cancelados por el siguiente motivo:<br><%cancelaEstudio.mapeaRegId(conEnoc, matricula, plan.getPlanId()); out.print(cancelaEstudio.getComentario()); %></font>
</td></tr>
</table>
<%
} %>
<!--table width="100%"    align="center"><tr><td background="../../imagenes/shadow.gif" height="4"></td></tr></table-->
<%@ include file= "../../cierra_enoc.jsp" %>