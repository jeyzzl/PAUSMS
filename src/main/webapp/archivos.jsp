<%@include file= "con_enoc.jsp" %>
<%@ include file= "conectadbp.jsp" %>
<%@ page import="aca.archivos.ArchivoVO" %>
<jsp:useBean id="bArchivosAlumno" scope="page" class="aca.archivos.Archivos"/>
<html>
<head><title>Sistema Academico</title>
	<link href="academico.css" rel="STYLESHEET" type="text/css">
	<link href="print.css" rel="STYLESHEET" type="text/css" media="print">
</head>
<body>
<script>
	var o = parent.document.getElementById('cargando');
	o.parentNode.removeChild(o);
</script>
<table style="width:100%">
<%
	ArrayList archivosNuevos = bArchivosAlumno.obtenerArchivosNuevosdeProfesor(conEnoc,conn2,(String)session.getAttribute("codigoPersonal"),(String)session.getAttribute("cargaId"));
	String mensaje="",materia="",materiaant="",id="",nAlumno="",nAlumnoant="";
	int contArchivos=0;
	String link="",sNombre="",idAnt="",mat="",matAnt="";
	aca.alumno.AlumUtil alumnoU = new aca.alumno.AlumUtil();	
	sNombre	= alumnoU.getNombre(conEnoc,(String)session.getAttribute("codigoPersonal"), "NOMBRE");
	ArchivoVO archivo=new ArchivoVO();
	if(archivosNuevos.size()>0){ mensaje="Tienes <b>"+archivosNuevos.size()+"</b> archivos nuevos.";
%>
					<tr  height="15">
					    <td valign='top'><%=mensaje%></td>
					</tr>
<%
		for(int i=0;i<archivosNuevos.size();i++){
			archivo=(ArchivoVO)archivosNuevos.get(i);
			materia=archivo.getNombreProfesor();
			nAlumno = archivo.getNombreAlumno();
			id=archivo.getId().substring(0,11);
			mat=archivo.getCodigoPersonal();
			materia=archivo.getNombreProfesor();
			if(!materia.equals(materiaant)){
				if(i!=0){
					link="portales/alumno/transferir?matricula="+matAnt+"&id="+idAnt+"&vista=xAlumno&alumno="+nAlumnoant+"&nomProfesor="+sNombre+"&origen=Profesor&nomMateria="+materiaant;
					out.println("<tr height='15'><td valign='top'><a href='"+link+"'><dd>"+nAlumnoant+" (<b>"+contArchivos+"</b>)</a></td></tr>");
				}
				contArchivos=0;
				nAlumnoant="";
				out.println("<tr height='15'><td valign='top'>- "+materia+"</td></tr>");
				materiaant=materia;
				if(!nAlumno.equals(nAlumnoant)){
					contArchivos++;
					nAlumnoant=nAlumno;
					matAnt=mat;
					idAnt=id;
				}
			}else{
				materiaant=materia;
				if(!nAlumno.equals(nAlumnoant)){
					link="portales/alumno/transferir?matricula="+matAnt+"&id="+idAnt+"&vista=xAlumno&alumno="+nAlumnoant+"&nomProfesor="+sNombre+"&origen=Profesor&nomMateria="+materiaant;
					out.println("<tr height='15'><td valign='top'><a href='"+link+"'><dd>"+nAlumnoant+" (<b>"+contArchivos+"</b>)</a></td></tr>");
					contArchivos=1;
					matAnt=mat;
					nAlumnoant=nAlumno;
					idAnt=id;
				}else{
					matAnt=mat;
					nAlumnoant=nAlumno;
					idAnt=id;
					contArchivos++;
				}
			}
		}
		link="portales/alumno/transferir?matricula="+archivo.getCodigoPersonal()+"&id="+archivo.getId().substring(0,11)+"&vista=xAlumno&alumno="+archivo.getNombreAlumno()+"&nomProfesor="+sNombre+"&origen=Profesor&nomMateria="+archivo.getNombreProfesor();
		out.println("<tr height='15'><td valign='top'><a href='"+link+"'><dd>"+nAlumnoant+" (<b>"+contArchivos+"</b>)</a></td></tr>");
	}else{
%>
					<tr>
					    <td><font size="2"><b>No hay archivos nuevos</b></font></td>
					</tr>
<%
	}
%>
</table>
</body>
</html>
<%@ include file= "../../cierradbp.jsp" %>
<%@ include file= "cierra_enoc.jsp" %>