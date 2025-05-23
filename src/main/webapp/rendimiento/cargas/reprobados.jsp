<%@page import="java.util.List"%>
<%@page import= "java.util.HashMap"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>

<%@ page import="java.text.*"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script>
	function Refrescar(){
		document.forma.submit();
	}
</script>
<%	
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");
	
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
	String periodoId 			= (String)request.getAttribute("periodoId");
	String cargaId 				= (String)request.getAttribute("cargaId");
	Acceso acceso 				= (Acceso)request.getAttribute("acceso");
	
	List<CatPeriodo> listaPeriodos 					= (List<CatPeriodo>) request.getAttribute("listaPeriodos");
	List<Carga> listaCarga 							= (List<Carga>) request.getAttribute("listaCarga");
	List<CatFacultad> lisFacultades					= (List<CatFacultad>) request.getAttribute("lisFacultades");
	List<CatCarrera> lisCarreras 					= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<AlumnoCurso> listMaterias					= (List<AlumnoCurso>) request.getAttribute("listMaterias");
	HashMap<String, String> mapaInscritosPorMateria = (HashMap<String, String>) request.getAttribute("mapaInscritosPorMateria");
	
	double indiceAlumRep		= 0;
	double indiceMatRep			= 0;	
	float indiceMatPorAlumno	= 0;
	float totMatPorAlumno		= 0;
	
	int inscritos=0, totalMaterias=0, matReprobadas=0, reprobados=0;
	int totIns =0, totRep=0, totMat=0, totNA=0;
	int Ins=0, Rep=0, Mat=0, NA=0;
	
	// Inicializar la variable carreraId en sesion
	session.setAttribute("carreraId", "0");
	
%>
<div class="container-fluid">
	<h1>Totales de Alumnos Reprobados</h1>
	<form id="noayuda" name="forma" action="reprobados" method='post'>
	<div class="alert alert-info">
		<spring:message code="aca.Periodo"/>: 
		<select onchange="javascript:Refrescar();" name="PeriodoId" class="input input-medium">
<%		for(CatPeriodo periodo : listaPeriodos){ %>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>">
			<%=periodo.getNombre().replace("Periodo ", "")%>
			</option>
<%		}%>
		</select>
		Carga: 
		<select onchange='javascript:Refrescar();' name="CargaId" style="width:350px;">
<%		
		for(Carga carga :listaCarga){			
%>			<option value='<%=carga.getCargaId()%>' <%if (cargaId.equals(carga.getCargaId()))out.print("selected");%>>
				<%=carga.getCargaId()%><%=carga.getNombreCarga()%>
			</option>
<%		}%>		
		</select>
	</div>
	</form>
<%	
	for(CatFacultad facultad : lisFacultades){
		
%>
	<table style="width:77%"  > 
  		<tr> <td colspan="9"> &nbsp;</td> </tr>
  		<tr> <td colspan="9" style="font-size:12pt;"><strong><%=facultad.getNombreFacultad()%></strong></td> </tr>
	</table>
	<table class="table table-bordered">
	<thead class="table-info">
  	<tr>
	  	<th width="20%" class="text-center"><spring:message code="aca.Carrera"/></th>
	  	<th width="3%" class="text-end">N° Inscritos </th>
	  	<th width="3%" class="text-end">N° Reprobados</th>
	  	<th width="3%" class="text-end" title="Indice de alumnos reprobados">IRA(%)</th>
	  	<th width="3%" class="text-end">N° Mat.</th>
	  	<th width="3%" class="text-end">N° Mat.NA</th>
	  	<th width="3%" class="text-end" title="Indice de materias reprobadas">IRM(%)</th>
	  	<th width="3%" class="text-end" title="Indice de Materias reprobadas por Alumno">IMXA(%)</th>
  	</tr>
  	</thead>
<%  	
		totIns =0; totRep=0; totMat=0; totNA=0;
		for(CatCarrera carrera : lisCarreras){	
			//if( Acceso.getAccesos().indexOf( carrera.getCarreraId() ) != -1 || Acceso.getAdministrador().equals("S") || Acceso.getSupervisor().equals("S") ){
			if (carrera.getFacultadId().equals(facultad.getFacultadId())){	
				inscritos = 0;			
				if(mapaInscritosPorMateria.containsKey(carrera.getCarreraId())){
					inscritos = Integer.parseInt(mapaInscritosPorMateria.get(carrera.getCarreraId()));
				}
				
				HashMap<String, String> mapAlumnosReprobados = new HashMap<String, String>();
				
				int contTotalMaterias 			= 0;
				int contTotalMateriasReprobadas = 0;
				for(AlumnoCurso cargaAca: listMaterias){

					if(cargaAca.getCarreraId().equals(carrera.getCarreraId())){
						contTotalMaterias++;
						
						if(cargaAca.getTipoCalId().equals("2") || cargaAca.getTipoCalId().equals("4")){
							contTotalMateriasReprobadas++;
							mapAlumnosReprobados.put(cargaAca.getCodigoPersonal(), cargaAca.getCursoId());
						}
					}
					
				}
				
				totalMaterias = contTotalMaterias;
				matReprobadas = contTotalMateriasReprobadas;
				reprobados 	  = mapAlumnosReprobados.size();
				
				/*inscritos 		= aca.vista.EstadisticaUtil.numInscritosxCarga(conEnoc, cargaId, carrera.getCarreraId());
				totalMaterias 	= aca.vista.AlumnoCursoUtil.getTotalMat(conEnoc, cargaId, carrera.getCarreraId());
				matReprobadas 	= aca.vista.AlumnoCursoUtil.getTotalReprobadas(conEnoc, cargaId, carrera.getCarreraId());
				reprobados    	= aca.vista.AlumnoCursoUtil.numReprobadosxCarga(conEnoc, cargaId, carrera.getCarreraId());
				*/
				
				indiceMatRep	= 0;
				indiceAlumRep 	= 0;
				
				totIns		+= inscritos;
				totRep 		+= reprobados;
				totMat 		+= totalMaterias;			
				totNA 		+= matReprobadas;
				
				if (inscritos>0 && totalMaterias>0) {
					indiceAlumRep 		= (double)reprobados/inscritos*100;
					indiceMatRep 		= (double)matReprobadas/totalMaterias*100;					
				}
				
				if (matReprobadas>0 && reprobados>0){
					indiceMatPorAlumno 	= (float) matReprobadas/reprobados;
				}else{
					indiceMatPorAlumno 	= 0;
				}
					
%>
	<tr class="tr2">
	  	<td><a href="detalle?CargaId=<%=cargaId%>&CarreraId=<%=carrera.getCarreraId()%>"><%= carrera.getNombreCarrera()%></a></td>
    	<td class="text-end"><%= inscritos %></td>
    	<td class="text-end"><a href="alumreprobados?CarreraId=<%=carrera.getCarreraId()%>&CargaId=<%=cargaId%>"><%=reprobados %></a></td>
    	<td class="text-end"><%= getFormato.format(indiceAlumRep) %></td>
    	<td class="text-end"><%= totalMaterias %></td>
    	<td class="text-end"><%= matReprobadas%></td>
    	<td class="text-end"><%= getFormato.format(indiceMatRep) %></td>
    	<td class="text-end"><%= getFormato.format(indiceMatPorAlumno) %></td>
	</tr>
			
<%			}
		}		
		Ins += totIns;
		Rep += totRep;
		Mat += totMat;
		NA  += totNA;
		
		if (totRep>0 && totNA>0) totMatPorAlumno = (float)totNA/(float)totRep; else totMatPorAlumno = 0;
%>
	<tr>
    	<th>Totales</th>
    	<th class="text-end"><%=totIns%></th>
    	<th class="text-end"><%=totRep%></th>
    	<th class="text-end"><%=getFormato.format((double)totRep/totIns*100)%></th>
    	<th class="text-end"><%=totMat%></th>
    	<th class="text-end"><%=totNA%></th>
    	<th class="text-end"><%=getFormato.format((double)totNA/totMat*100)%></th>
    	<th class="text-end"><%=getFormato.format((double)totMatPorAlumno)%></th>
	</tr>  
<%	}
	float totalImxa = 0;
	if (Rep>0 && NA>0) totalImxa = (float)NA/(float)Rep;
%>
</table>
<table class="table table-bordered" style="width:20%">
	<tr>
    	<th colspan="2">Total Gral.</th>
  	</tr>
  	<tr>
    	<td><spring:message code="aca.Inscritos"/></td>
    	<td class="text-end"><%=Ins %></td>
  	</tr>
  	<tr>
    	<td>Reprobados</td>
    	<td class="text-end"><%=Rep %></td>
  	</tr>
   	<tr>
	    <td>IRA</td>
	    <td class="text-end"><%=getFormato.format((double)Rep/Ins*100)%>%</td>
  	</tr>  
  	<tr>
	    <td>#Mat</td>
    	<td class="text-end"><%=Mat %></td>
	</tr>
    <tr>
    	<td>#Mat NA</td>
    	<td class="text-end"><%=NA %></td>
  	</tr>
  	</tr>
  	<tr>  	
    	<td>IRM</td>
    	<td class="text-end"><%=getFormato.format((double)NA/Mat*100)%> %</td>
  	</tr>
  	<tr>  	
    	<td title="Indice de materias reprobadas por alumno">IMXA</td>
    	<td class="text-end"><%=getFormato.format(totalImxa)%> %</td>
  	</tr>
</table>