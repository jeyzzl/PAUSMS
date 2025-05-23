<%@ page import= "aca.plan.MapaPlan"%>
<%@ page import= "aca.plan.MapaCurso"%>
<%@ page import= "aca.catalogo.CatTipoCal"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<jsp:useBean id="alumnoUtil"  class="aca.alumno.AlumUtil" scope="page"/>
<jsp:useBean id="carreraUtil"  class="aca.catalogo.CarreraUtil" scope="page"/>
<jsp:useBean id="importadoUtil"  class="aca.kardex.ImportadoUtil" scope="page"/>
<jsp:useBean id="planUtil"  class="aca.plan.PlanUtil" scope="page"/>
<jsp:useBean id="krdImport" scope="page" class="aca.kardex.KrdxCursoImp">
<jsp:setProperty name="krdImport" property="*"/> 
</jsp:useBean>
<script type="text/javascript"> src ="../../validacion.js"> </script>
<%
//variables
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	String CarreraId		= "";
	String PlanId			= request.getParameter("PlanId"); // plan recuperado del combo
	String check			= "";
	String NombreCarrera 	= "";
	String NombreAlumno		= "";
	String cursoId			= "";
	String fecha			= "";
	String fechaExtra		= "";
	String observaciones	= "";
	String condicion		= "";
	String convalidada		= "";
	String califExtr		= "";
	String calificacion		= "";
	String notaConva		= "-";
	String titulo			= "";
	String optativa			= "";
	String optativaNombre   = "";
	int cicloTem			= 0;
	int ciclo				= 0;
	int cont				= 0;
	int folio				= 0;
	ArrayList lisMaterias	= new ArrayList();
	ArrayList lisPlan		= new ArrayList();
	ArrayList lisCondicion	= new ArrayList();
	
	//Grabar datos
	if(request.getParameter("Accion")!=null){
	
		int accion = Integer.parseInt(request.getParameter("Accion"));
		folio = importadoUtil.getFolioAlumno(conEnoc, codigoAlumno);
		if(accion == 1){
			lisMaterias	= importadoUtil.getListMatPosibles(conEnoc, codigoAlumno, PlanId, "ORDER BY CICLO, NOMBRE_CURSO" );
			
			for(int i= 0; i < lisMaterias.size();  i++){	
				check			= request.getParameter("check"+i);
				cursoId			= request.getParameter("cursoId"+i);
				fecha			= request.getParameter("fecha"+i);
				fechaExtra		= request.getParameter("fechaExtra"+i);
				observaciones	= request.getParameter("observaciones"+i);
				condicion		= request.getParameter("condicion"+i);
				convalidada		= request.getParameter("convalidada"+i);
				califExtr		= request.getParameter("califExtra"+i);
				calificacion	= request.getParameter("calificacion"+i);
				titulo			= request.getParameter("titulo"+i);
				optativa		= request.getParameter("optativa"+i);
				notaConva 		= "-";
				optativaNombre  = request.getParameter("tipoCursoId"+i);
				
				
			 	if( titulo==null || titulo.equals(" ")|| titulo.equals("")){
			 		titulo = "N";
				}
			 	if( optativa==null || optativa.equals(" ") || optativa.equals("")){
			 		optativa = "N";
				}
			 	if (convalidada.equals("S")){
			 		notaConva = calificacion;
			 		calificacion = "0";
			 	}			 	
				
				if(check != null){
					if((!calificacion.equals("")) && (!fecha.equals(""))){ 
						krdImport.setCodigoPersonal(codigoAlumno);
						krdImport.setFolio(Integer.toString(folio));
						krdImport.setFCreada(fecha);
						krdImport.setCursoId(cursoId);
						krdImport.setCursoId2(cursoId);
						krdImport.setConvalidacion(convalidada);
						krdImport.setTipoCalId(condicion);
						krdImport.setNota(calificacion);
						krdImport.setNotaExtra(califExtr);
						krdImport.setFExtra(fechaExtra);
						krdImport.setObservaciones(observaciones);
						krdImport.setNotaConva(notaConva);
						krdImport.setTitulo(titulo);
						krdImport.setOptativa(optativa);
						krdImport.setOptativaNombre(optativaNombre);
						
						if(!krdImport.existeReg(conEnoc)){
							krdImport.insertReg(conEnoc);							
						}
						folio++;
					}
				}
			}
		}
		lisMaterias	= null;
		out.print("<div class='alert alert-success'><b>Registrado...<a class='btn btn-primary' href='cursos'>Regresar</a></div>");
		//response.sendRedirect("cursos");
	}
%>

	
<%	
	// utilizacion de las variables
	
	if(PlanId==null){  // si el planId viene igual  a null le asignamos el plan de alumno para que muestre el las materias del plan
		PlanId = alumnoUtil.getPlanActivo(conEnoc,codigoAlumno);	
	}
	NombreAlumno	= alumnoUtil.getNombre(conEnoc,codigoAlumno, "NOMBRE");
	CarreraId		= alumnoUtil.getCarreraId(conEnoc,alumnoUtil.getPlanActivo(conEnoc,codigoAlumno) );	
	NombreCarrera	= carreraUtil.getNombreCarrera(conEnoc, CarreraId, "1");
	lisMaterias		= importadoUtil.getListMatPosibles(conEnoc, codigoAlumno, PlanId, "ORDER BY CICLO, NOMBRE_CURSO" );
	lisPlan			= planUtil.getListAll(conEnoc, "ORDER BY 1,2,3");
	lisCondicion	= importadoUtil.getListTipoCalificacion(conEnoc);
%>
<script>
	function grabar(){
		var error = true;
		var recorrido = 0;
<%
		for(int i = 0 ; i < lisMaterias.size(); i++){
%>	
			if(recorrido < <%=lisMaterias.size()%>){
				if(frmimportcalif.check<%=i%>.checked){
					error = false;
					if(frmimportcalif.calificacion<%=i%>.value ==""){
						error = true;
						recorrido = <%=lisMaterias.size() + 1%>
						alert("La calificacion no puede ir vacia");
						frmimportcalif.calificacion<%=i%>.focus();
						
					}else if (frmimportcalif.fecha<%=i%>.value == ""){
						error = true;
						recorrido = <%=lisMaterias.size() + 1%>
						alert("La fecha no puede ir vacia");						
						frmimportcalif.fecha<%=i%>.focus();				
					}				
				}
			}	
<%
		}
%>		
		if(!error){
			frmimportcalif.Accion.value = 1;
			frmimportcalif.submit();									
		
		}
	}

</script>

<html>
<head>
<title>Documento sin t&iacute;tulo</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
.Estilo1 {font-size: 9px}
.Estilo3 {color: #993300}
-->
</style>
</head>

<body>
<table style="width:100%"   align="center">
  <tr align="center">
    <td colspan="12">[<%=codigoAlumno%>] [<%=NombreAlumno%>] -- [<%=alumnoUtil.getPlanActivo(conEnoc,codigoAlumno)%>] [<%=NombreCarrera%>]</td>
  </tr>
<form name="frmimportplan" method="post" action="accion">

  <tr align="center">
    <td colspan="12"><select name="PlanId" id="PlanId" onChange="document.frmimportplan.submit()">
<% 
	for(int i= 0; i<lisPlan.size(); i++){
		MapaPlan plan	= (MapaPlan) lisPlan.get(i);
		if(plan.getPlanId().equals(PlanId)){
			out.print(" <option value='"+plan.getPlanId()+"'");			
			out.print("Selected>"+ plan.getNombrePlan()+"</option>");
		}else{
			out.print(" <option value='"+plan.getPlanId()+"'");			
			out.print(" >"+ plan.getNombrePlan()+"</option>");
		}	
	}	
%>	
    </select></td>
  </tr>
</form>
  <tr align="center">
    <td colspan="12"><a href="cursos"><strong>Regresar</strong></a></td>
  </tr>
<form name="frmimportcalif" method="post" action="accion">
<input name="Accion" type="hidden" value="">
<% 
	for(int i=0; i < lisMaterias.size(); i ++){
		MapaCurso curso = (MapaCurso) lisMaterias.get(i);
		ciclo	= Integer.parseInt(curso.getCiclo());
		if(ciclo != cicloTem){
			cicloTem = ciclo;
%>
  <tr>
    <td colspan="12"><strong>Semestre <%=ciclo%></strong></td>
  </tr>
  <tr>
    <th width="4%" height="16"><span class="Estilo5">Aplicar</span></th>
    <th width="30%"><span class="Estilo5"><spring:message code="aca.Materia"/></span></th>
    <th width="3%"><span class="Estilo5 ">Calif.</span></th>
    <th width="7%"><span class="Estilo5">Fecha </span></th>
    <th width="12%"><span class="Estilo5 ">Calif. Extra </span></th>
    <th width="10%"><span class="Estilo5 ">Fecha Extra </span></th>
    <th width="13%"><span class="Estilo5 ">Observaciones</span></th>
    <th width="6%"><span class="Estilo5 ">Condicion</span></th>
    <th width="7%"><span class="Estilo5">Conv.</span></th>
    <th width="3%"><span class="Estilo5">Titulo</span></th>
    <th width="5%"><span class="Estilo5">Optativa</span></th>
  </tr>
<%
		}
%>  
  <tr>
    <td align="center"><input name="check<%=i%>" type="checkbox"  value="SI"></td>
    <td><%=curso.getNombreCurso()%><input name="cursoId<%=i%>" type="hidden" id="cursoId<%=i%>" value="<%=curso.getCursoId()%>">
    	<%if(curso.getTipoCursoId().equals("2")||curso.getTipoCursoId().equals("7")){%>
    	<input name="tipoCursoId<%=i%>" type="text" id="tipoCursoId<%=i%>" value="<%=curso.getNombreCurso() %>"> 
    	<%} %>
    </td>
    
    <td align="center"><input name="calificacion<%=i%>" type="text" class="text"  size="3" maxlength="4" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"></td>
    <td align="center"><input name="fecha<%=i%>" type="text" id="fecha" size="9" maxlength="10" onChange="validaFecha(this.value); this.value=borrar"></td>

    <td align="center"><input name="califExtra<%=i%>" type="text" class="text"  size="3" maxlength="3" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"> </td>
    <td align="center"><input name="fechaExtra<%=i%>" type="text" class="text"  size="9" maxlength="10" onChange="validaFecha(this.value); this.value=borrar"></td>
    <td align="center"><input name="observaciones<%=i%>" type="text" class="text"  size="20" maxlength="200"></td>
    <td align="center"><select name="condicion<%=i%>" id="condicion<%=i%>">
<%
		for(int j=0; j<lisCondicion.size(); j++){
			CatTipoCal tipocalf	= (CatTipoCal) lisCondicion.get(j);
			
			if(tipocalf.getTipoCalId().equals(condicion)){
				out.print(" <option value='"+tipocalf.getTipoCalId()+"'");
				out.print("Selected>"+ tipocalf.getNombreTipoCal()+"</option>");
			}else{
				out.print(" <option value='"+tipocalf.getTipoCalId()+"'");
				out.print(" >"+ tipocalf.getNombreTipoCal()+"</option>");
			}		
		}	
%>	
    </select></td>
    <td align="center">
		<select name="convalidada<%=i %>" id="convalidada<%=i %>">
			<option value="N" selected="selected">No</option>
			<option value="I">Int</option>
			<option value="S">Ext</option>
		</select>
	</td>
    <td align="center"><input name="titulo<%=i%>" type="checkbox" id="titulo<%=i%>" value="S"></td>
    <td align="center"><input name="optativa<%=i%>" type="checkbox" id="optativa<%=i%>" value="S"></td>
  </tr>
<% 
	}
	alumnoUtil 		= null;
	carreraUtil 	= null;
	importadoUtil 	= null;
	planUtil 		= null;
	lisMaterias		= null;
	lisPlan			= null;
	lisCondicion	= null;	
%>
  <tr align="center">
    <td colspan="12"><input type="button" name="Submit" value="Grabar" onClick="grabar()">
      <input name="PlanId" type="hidden" id="PlanId" value="<%=PlanId%>"></td>
  </tr>
</form>
</table>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>