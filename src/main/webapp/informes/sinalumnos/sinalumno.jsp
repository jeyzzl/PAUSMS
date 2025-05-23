<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.carga.CargaUtil"%>
<%@ page import= "aca.vista.CargaAcaUtil"%>
<%@ page import= "aca.catalogo.FacultadUtil"%>
<%@ page import= "aca.catalogo.CarreraUtil"%>

<%
	String sCarga				= request.getParameter("CargaId");
	String sFacultad 			= "X";
	String sFacultadTmp			= "";			
	String sCarrera 			= "X";
	String sCarreraTemp			= "";
	String sNomFacultad 		= "";
	String sNombreCarrera		= "";
	String sNombreCarga 		= "";
	String sBgcolor				= "";
	int i	 					= 0;
	int con						= 0;
	
	CargaUtil cargaU			= new CargaUtil();
	sNombreCarga 				= cargaU.getNombre(conEnoc, sCarga);
	FacultadUtil facU			= new FacultadUtil();
	CarreraUtil carrU			= new CarreraUtil();
	
	ArrayList lisCarga				= new ArrayList(); 
	CargaAcaUtil cargaAcaU		= new CargaAcaUtil();
	lisCarga					= cargaAcaU.getListSinAlumno(conEnoc, sCarga, "Order by 6, 5");
%>	 	
<table id="noayuda" width="95%"  align="center" height="23">
  <tr> 
    <td align="center">&nbsp;</td>
  </tr>
  <tr> 
    <td align="center"><font size="2"><strong>MATERIAS SIN ALUMNOS</strong></font></td>
  </tr>
  <tr> 
    <td align="center"><strong><font size="2">Carga: &nbsp;&nbsp;&nbsp;<%=sNombreCarga%></font></strong></td>
  </tr>
  <tr> 
    <td align="center"><a href="carga">[Regresar]</a> </td>
  </tr>
  <tr> 
    <td align="center"><div align="right"><a href="borrarAll?CargaId=<%=sCarga%>"><font color="#FF0000">[<em>Borrar 
        todo</em>]</font></a></div></td>
  </tr>
</table>
<table style="width:98%"  align="center" class="tabla">
  <%		for (i=0; i<lisCarga.size(); i++){
			aca.vista.CargaAcademica cargaA = (aca.vista.CargaAcademica) lisCarga.get(i);
			sCarreraTemp = cargaA.getCarreraId();
			sFacultadTmp = sCarreraTemp.substring(0,3);
			if(!sFacultad.equals(sFacultadTmp)){
		 		sFacultad = sFacultadTmp;
				sNomFacultad				= facU.getNombreFacultad(conEnoc, sFacultad, "Order by 1");
%>
  <tr> 
    <td colspan="6">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="6"><div align="center"><b><font color="#000099" size="3"><%=sNomFacultad%></font></b></div></td>
  </tr>
  <%  
       		}//fin del if de facultades diferentes
			if(sFacultad.equals(sFacultadTmp) && !sCarrera.equals(sCarreraTemp)){
					sCarrera = sCarreraTemp;
					sNombreCarrera = carrU.getNombreCarrera(conEnoc, sCarrera, "1");
%>
 <tr> 
    <td colspan="6">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="6"><strong><font color="#000099">Programa:&nbsp;<em>&nbsp;<%=sNombreCarrera%></em></font></strong></td>
  </tr>
  <tr> 
    <th width="3%" height="22" align="center"><spring:message code="aca.Numero"/></th>
    <th width="10%" height="22" align="center"><font size="2" face="Arial, Helvetica, sans-serif">Clv. 
      Curso </font></th>
    <th width="11%" height="22" align="center"><font size="2" face="Arial, Helvetica, sans-serif"><b>N° 
      Nomina</b></font></th>
    <th width="30%" height="22" align="center"><font face="Times New Roman, Times, serif"><b><font size="2" face="Arial, Helvetica, sans-serif"><spring:message code="aca.Nombre"/></font></b></font></th>
    <th width="39%" height="22" align="center"><b><font face="Arial, Helvetica, sans-serif" size="2">Materias</font></b></th>
    <th width="7%" align="center">&nbsp;</th>
  </tr>
<%
           }//fin del if de carreras diferentes
			con++; 
		   if ((con % 2) == 0) { sBgcolor = sColor; } else { sBgcolor = ""; } 
%>
  <tr class="tr2" <%=sBgcolor%>> 
    <td><font size="1" face="Arial, Helvetica, sans-serif"><%=con%></font></td>
    <td><font size="1" face="Arial, Helvetica, sans-serif"><%=cargaA.getCursoCargaId()%></font></td>
    <td align="center"><font size="1" face="Arial, Helvetica, sans-serif"><%=cargaA.getCodigoPersonal()%></font></td>
    <td><font size="1" face="Arial, Helvetica, sans-serif"><%=cargaA.getNombre()%></font></td>
    <td align="rigth"><font size="1" face="Arial, Helvetica, sans-serif"><%=cargaA.getNombreCurso()%></font></td>
    <td width="7%" align="center"><a href="borrar?CursoCargaId=<%=cargaA.getCursoCargaId()%>&CargaId=<%=sCarga%>&CursoId=<%=cargaA.getCursoId()%>"><img src="no.png" width="15" height="11" ></a></td>
  </tr>
<% 	} // fin del For 

  		cargaU			= null;
		facU			= null;
		carrU			= null;
		lisCarga		= null;
		cargaAcaU		= null;
%>
</table> 
<br>
<div align="center"><font size="3" face="Times New Roman, Times, serif"> <br>
  <b><font color="#000099"></font></b></font>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>