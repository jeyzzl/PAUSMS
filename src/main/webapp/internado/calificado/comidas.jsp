<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="apers" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="aplan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="mplan" scope="page" class="aca.plan.MapaPlan"/>
<jsp:useBean id="MapaPlanU" scope="page" class="aca.plan.PlanUtil"/>
<jsp:useBean id="aacad" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="Academico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="cutil" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="butil" scope="page" class="aca.carga.CargaBloqueUtil"/>
<jsp:useBean id="iluco" scope="page" class="aca.internado.IntLugarComida"/>
<jsp:useBean id="Internado" scope="page" class="aca.internado.IntCalificado"/>
<jsp:useBean id="InternadoU" scope="page" class="aca.internado.IntCalificadoUtil"/>

<script type="text/javascript">
	function Carga(e){
		document.location.href='comidas?cargaid='+e;
	}
	
	function Graba(a){		
		var j;
		var i;
			
		if( document.frmcomidas.Dormitorio.value != "" &&
			document.frmcomidas.CargaId.value != "" &&
			document.frmcomidas.BloqueId.value != "" &&
			document.frmcomidas.Alimentos.value != "" &&
			document.frmcomidas.Lugar.value != ""){
			i = 0;
			if(document.frmcomidas.Desayuno.checked == true){
				j = "1";i = i + 1;
			}else
				j = "0";
			if(document.frmcomidas.Comida.checked == true){
				j = j+"1";i = i + 1;
			}else
				j = j+"0";
			if(document.frmcomidas.Cena.checked == true){
				j = j+"1";i = i + 1;
			}else
				j = j+"0";
						
			if(i != document.frmcomidas.Alimentos.value)
				alert("The number of meals does not match the selected ones!");
			else{
				if(a == true)
					document.location.href='comidas?Accion=2&Comidas='+j+'&CargaId='+document.frmcomidas.CargaId.value+
					'&BloqueId='+document.frmcomidas.BloqueId.value+'&Alimentos='+document.frmcomidas.Alimentos.value+
					'&Lugar='+document.frmcomidas.Lugar.value+'&Comentario='+document.frmcomidas.Comentario.value+'';
				else
					document.location.href='comidas?Accion=1&Comidas='+j+'&CargaId='+document.frmcomidas.CargaId.value+'&BloqueId='+
					document.frmcomidas.BloqueId.value+'&Alimentos='+document.frmcomidas.Alimentos.value+'&Lugar='+
					document.frmcomidas.Lugar.value+'&Comentario='+document.frmcomidas.Comentario.value+'';
			}
		}else{
			alert("Fill out the entire form");
		}		
	}
</script>
<%
	String codigoAlumno = (String) session.getAttribute("codigoAlumno");
	String Accion = request.getParameter("Accion");		
	if(Accion == null) Accion = "0";	
	String frase = "Fin!!";
	String car 	= request.getParameter("CargaId");//
	String blo 	= request.getParameter("BloqueId");//
	String ali 	= request.getParameter("Alimentos");//
	String com 	= request.getParameter("Comidas");//
	String lug 	= request.getParameter("Lugar");//
	String ctr	= request.getParameter("Comentario");//
	if(Accion.trim().equals("1")){//Comienza if de guardar nuevo	
		aca.internado.IntCalificado ic = new aca.internado.IntCalificado();
		ic.setCodigoPersonal(codigoAlumno);
		ic.setCargaId(car);
		ic.setBloqueId(blo);
		ic.setNumComidas(ali);
		ic.setTipoComida(com);
		ic.setLugarId(lug);
		ic.setComentario(ctr);
		
		if(InternadoU.insertReg(conEnoc, ic))
			frase = "Saved";
		else
			frase = "Error saving";
	}else{//Termina if de guardar nuevo... comienza si no guarda nuevo
		if(Accion.trim().equals("2")){//Comienza if de update
			aca.internado.IntCalificado ic = new aca.internado.IntCalificado();
			ic.setCodigoPersonal(codigoAlumno);
			ic.setCargaId(car);
			ic.setBloqueId(blo);
			ic.setNumComidas(ali);
			ic.setTipoComida(com);
			ic.setLugarId(lug);
			ic.setComentario(ctr);
			
			if(InternadoU.updateReg(conEnoc, ic))
				frase = "Saved";
			else
				frase = "Error saving";
		}else{//Termina if de update... comienza si no se pidio guardar
			if(aca.alumno.AcademicoUtil.getResidencia(conEnoc, codigoAlumno).trim().equals("I")){//Revisa si es interno
%>
	<form action="" method="post" name="frmcomidas">
	<table id="noayuda" width="70%"  align="center"  >
	<tr><td align="center"  colspan=3><font size="3"><b>Customized Boarding</b></font></td></tr>
<%
				apers = AlumUtil.mapeaRegId(conEnoc, codigoAlumno);
				aplan.mapeaRegId(conEnoc, codigoAlumno);
				mplan = MapaPlanU.mapeaRegId(conEnoc, aplan.getPlanId());
				Academico = AcademicoU.mapeaRegId(conEnoc, codigoAlumno);
%>
    <tr>
      <th width="11%" height="20" colspan="5">
	    Student:[<%=codigoAlumno%>] [<%=apers.getNombreLegal()%>] [<%=mplan.getNombrePlan()%> ] 
	  </th>
    </tr>
	</table>
	<br>
	<table style="width:60%" class="table table-condensed" align="center"  >	
    <tr>
      <td><spring:message code='aca.Dormitorio'/></td>
      <td colspan="4"><input name="Dormitorio" type="hidden" id="Dormitorio" value="<%=Academico.getDormitorio()%>" 
		size="2" maxlength="0" readonly />
          <%=Academico.getDormitorio()%> </td>
    </tr>
	<tr>
      <td><spring:message code="aca.Carga"/></td>
      <td colspan="4">
		<select name="CargaId" id="CargaId" 
		onchange="location = 'comidas?cargaid='+this.options[this.selectedIndex].value;">          
        <option value='"0"'>&nbsp;</option>
<%
				String CI = request.getParameter("cargaid"),fi="",ff="";							
				if(CI == null) CI = "0";
			
				ArrayList lisCarga = cutil.getListPeriodoActual(conEnoc, "ORDER BY ENOC.CARGA.F_INICIO");
				for(int i = 0; i < lisCarga.size();i++){
					aca.carga.Carga carga = (aca.carga.Carga)lisCarga.get(i);
					if(CI.equals("0"))
						out.print(" <option value='" + carga.getCargaId() + "'>" + carga.getNombreCarga() + "</option>");
					else 
					if(CI.equals(carga.getCargaId())){
						out.print(" <option value='" + carga.getCargaId() + "' Selected>" + carga.getNombreCarga() + "</option>");
						fi = carga.getFInicio();
						ff = carga.getFFinal();
					}else
						out.print(" <option value='" + carga.getCargaId() + "'>" + carga.getNombreCarga() + "</option>");
				}
%>
        </select>
	  
<%				if(!CI.equals("0")){	//Si la carga es diferente de cero 
      				out.print(fi+" al "+ff);
%>
	  </td>
	</tr>
	<tr>
      <td><spring:message code="aca.Bloque"/></td>
      <td colspan="4">
		<select name="BloqueId" id="BloqueId" onchange="location='comidas?cargaid='+CargaId.options[CargaId.selectedIndex].value+'&bloqueid='+this.options[this.selectedIndex].value;">
        <option value='"0"'>&nbsp;</option>
<%
					String BI = request.getParameter("bloqueid"),bfi="",bff="";
		
					if(BI == null) BI = "0";
			
					ArrayList lisBloque = butil.getLista(conEnoc,CI,"ORDER BY 2");
					for(int i = 0; i < lisBloque.size();i++){
						aca.carga.CargaBloque bloque = (aca.carga.CargaBloque)lisBloque.get(i);
										
						if(BI.equals("0"))
							out.print(" <option value='" + bloque.getBloqueId() + "'>" + bloque.getNombreBloque() + "</option>");
						else 
							if(BI.equals(bloque.getBloqueId())){
								out.print(" <option value='" + bloque.getBloqueId() + "' Selected>" + bloque.getNombreBloque() + "</option>");
								bfi = bloque.getFInicio();
								bff = bloque.getFInicio();
							}else
								out.print(" <option value='" + bloque.getBloqueId() + "'>" + bloque.getNombreBloque() + "</option>");
					}
%>
        </select>       
<%
					if(!BI.equals("0")){//Si el bloque es diferente de cero
						out.print(bfi+" al "+bff);
						
						//Aqui vamos a buscar si al alumno ya se le asignó
						//algo en esta carga y en este bloque
						String alimentos 	= "0";
						String tipoComida 	= "000";
						String lugar		= "";
						String comentario	= "";
						boolean update	= false;
						if(InternadoU.existeReg(conEnoc,codigoAlumno,CI,BI)){
							//Si el alumno ya tiene asignada comidas
							Internado.mapeaRegId(conEnoc,codigoAlumno,CI,BI);
							alimentos = Internado.getNumComidas();
							tipoComida = Internado.getTipoComida();
							lugar = Internado.getLugarId();
							comentario = Internado.getComentario();
							update = true;
						}//Termina if de si el alumno ya tiene asignadas comidas
%>
      </td>
      </tr>
      <tr>
        <td>N° Meals</td>
        <td colspan="4"><select name="Alimentos" id="Alimentos" >
            <%
						for(int i = 0; i <= 3; i++){//Comienza for de cantidad de alimentos
							if(i == Integer.parseInt(alimentos.trim()))
								out.print("<option value='"+i+"' Selected>"+i+"</option>");
							else
								out.print("<option value='"+i+"'>"+i+"</option>");
						}//Termina for de canditad de alimentos
%>
          </select>
        </td>
      </tr>
      <tr>
        <td>Meal Type</td>
        <td width="20">D</td>
        <td width="24">C</td>
        <td width="28">C</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td width="20"><input type=checkbox name="Desayuno" 
<%						if(tipoComida.trim().substring(0,1).equals("1"))out.print("checked");
%> />
        </td>
        <td width="24"><input type=checkbox name="Comida"
<%						if(tipoComida.trim().substring(1,2).equals("1"))out.print("checked");
%> />
        </td>
        <td width="28"><input type=checkbox name="Cena" 
<%						if(tipoComida.trim().substring(2).equals("1"))out.print("checked");
%> />
        </td>
        <td width="249">&nbsp;</td>
      </tr>
      <tr>
        <td>Place</td>
        <td colspan="4"><select name="Lugar" id="Lugar" >
            <option value=''>&nbsp;</option>
<%
						for(int i = 1; i <=4; i++){//Comienza for de lugar de comidas
							iluco.mapeaRegId(conEnoc,String.valueOf(i),"ORDER BY LUGAR_ID");
							
							if(lugar.trim().equals(String.valueOf(i)))
								out.print("<option value='"+i+"' Selected>"+iluco.getNombreLugar()+"</option>");
							else
								out.print("<option value='"+i+"'>"+iluco.getNombreLugar()+"</option>");
							
						}//Termina for de lugar de comidas
%>
          </select>
        </td>
      </tr>
      <tr>
        <td width="144"><spring:message code="aca.Comentario"/></td>
        <td colspan="4"><input name="Comentario" type="text" class="text" id="Comentario" value="<%=comentario%>" size="30" maxlength="100" />
        </td>
      </tr>      
      <tr><td colspan="5" align="center"><input type=button name="Grabar" value="Save" onClick="javascript:Graba(<%=update%>)"/></td></tr>
      <%
					}//Termina if de bloque diferente de cero
				}//Termina if de carga diferente de cero
			}//Termina if de si es interno
			else{//Si no es interno
%>
      <tr>
        <td align="center"  colspan="5"><font size="3"><b>Not a Boarding Student</b></font></td>
      </tr>
      <%
			}//Termina si no es interno
		}//Termina si no se pidio guardar
	}//Termina si no guarda nuevo
%>
    </table>
	</form>
<br><div align="center"><strong><font size="2" face="Arial, Helvetica, sans-serif"><%=frase%></font></strong></div>
<%@ include file= "../../cierra_enoc.jsp" %>