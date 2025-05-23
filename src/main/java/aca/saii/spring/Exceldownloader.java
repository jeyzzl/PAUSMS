package aca.saii.spring;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Exceldownloader {
	public static ByteArrayInputStream SaiiDatosExtrasToExcel(List<SaiiDatosExtra> ListaDatosExtra) throws IOException {
	    String[] COLUMNs = {"PLAN_ID","PLAN_NOMBRE","FECHA", "DIS_N_ING", "DIS_R_ING", "DIS_TOTAL", "REG_ALUM_H", "REG_ALUM_M", "REG_ALUM_T", "CUR_REM_H", "CUR_REM_M", "CUR_REM_T", "RET_SEG_H", "RET_SEG_M", "RET_SEG_T", "DESERT_H", "DESERT_M", "DESERT_T", "DESERT_19", "DESERT_20_24", "DESERT_25_29", "DESERT_30", "DESERT_DIS", "TRAB_SIM_H", "TRAB_SIM_M", "TRAB_SIM_T", "MOVIL_NAC_H", "MOVIL_NAC_M", "MOVIL_NAC_T", "MOVIL_NAC_19", "MOVIL_NAC_20_24", "MOVIL_NAC_25_29", "MOVIL_NAC_30", "MOVIL_NAC_DIS", "MOVIL_INT_H", "MOVIL_INT_M", "MOVIL_INT_T", "MOVIL_INT_19", "MOVIL_INT_20_24", "MOVIL_INT_25_29", "MOVIL_INT_30", "MOVIL_INT_DIS", "SERV_COM_H", "SERV_COM_M", "SERV_COM_T", "SERV_SOC_H", "SERV_SOC_M", "SERV_SOC_T", "EGEL", "EGEL_SOB", "TITU_PROG", "CONC", "EGRE_TRAB_H", "EGRE_TRAB_M", "EGRE_TRAB_T", "EGRE_TRAB_19", "EGRE_TRAB_20_24", "EGRE_TRAB_25_29", "EGRE_TRAB_30", "EGRE_TRAB_DIS"};
	    try(
	        Workbook workbook = new XSSFWorkbook();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ){
//	      CreationHelper createHelper = workbook.getCreationHelper();
	   
	      Sheet sheet = workbook.createSheet("SaiiDatosExtras_Excel_File");
	   
	      Font headerFont = workbook.createFont();
	      headerFont.setBold(true);
	      headerFont.setColor(IndexedColors.BLUE.getIndex());
	   
	      CellStyle headerCellStyle = workbook.createCellStyle();
	      headerCellStyle.setFont(headerFont);
	   
	      // Row for Header
	      Row headerRow = sheet.createRow(0);
	   
	      // Header
	      for (int col = 0; col < COLUMNs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(COLUMNs[col]);
	        cell.setCellStyle(headerCellStyle);
	      }
	  
	   
	      int rowIdx = 1;
	      for (SaiiDatosExtra DatosExtra : ListaDatosExtra) { 
	        Row row = sheet.createRow(rowIdx++);
	   
	        row.createCell(0).setCellValue(DatosExtra.getPLAN_ID());
	        row.createCell(1).setCellValue(DatosExtra.getPLAN_NOMBRE());
	        row.createCell(2).setCellValue(DatosExtra.getFECHA()); 
	        row.createCell(3).setCellValue(DatosExtra.getDIS_N_ING()); 
	        row.createCell(4).setCellValue(DatosExtra.getDIS_R_ING()); 
	        row.createCell(5).setCellValue(DatosExtra.getDIS_TOTAL()); 
	        row.createCell(6).setCellValue(DatosExtra.getREG_ALUM_H()); 
	        row.createCell(7).setCellValue(DatosExtra.getREG_ALUM_M()); 
	        row.createCell(8).setCellValue(DatosExtra.getREG_ALUM_T()); 
	        row.createCell(9).setCellValue(DatosExtra.getCUR_REM_H()); 
	        row.createCell(10).setCellValue(DatosExtra.getCUR_REM_M()); 
	        row.createCell(11).setCellValue(DatosExtra.getCUR_REM_T()); 
	        row.createCell(12).setCellValue(DatosExtra.getRET_SEG_H()); 
	        row.createCell(13).setCellValue(DatosExtra.getRET_SEG_M()); 
	        row.createCell(14).setCellValue(DatosExtra.getRET_SEG_T()); 
	        row.createCell(15).setCellValue(DatosExtra.getDESERT_H()); 
	        row.createCell(16).setCellValue(DatosExtra.getDESERT_M()); 
	        row.createCell(17).setCellValue(DatosExtra.getDESERT_T()); 
	        row.createCell(18).setCellValue(DatosExtra.getDESERT_19()); 
	        row.createCell(19).setCellValue(DatosExtra.getDESERT_20_24()); 
	        row.createCell(20).setCellValue(DatosExtra.getDESERT_25_29()); 
	        row.createCell(21).setCellValue(DatosExtra.getDESERT_30()); 
	        row.createCell(22).setCellValue(DatosExtra.getDESERT_DIS()); 
	        row.createCell(23).setCellValue(DatosExtra.getTRAB_SIM_H()); 
	        row.createCell(24).setCellValue(DatosExtra.getTRAB_SIM_M()); 
	        row.createCell(25).setCellValue(DatosExtra.getTRAB_SIM_T()); 
	        row.createCell(26).setCellValue(DatosExtra.getMOVIL_NAC_H()); 
	        row.createCell(27).setCellValue(DatosExtra.getMOVIL_NAC_M()); 
	        row.createCell(28).setCellValue(DatosExtra.getMOVIL_NAC_T()); 
	        row.createCell(29).setCellValue(DatosExtra.getMOVIL_NAC_19()); 
	        row.createCell(30).setCellValue(DatosExtra.getMOVIL_NAC_20_24()); 
	        row.createCell(31).setCellValue(DatosExtra.getMOVIL_NAC_25_29()); 
	        row.createCell(32).setCellValue(DatosExtra.getMOVIL_NAC_30()); 
	        row.createCell(33).setCellValue(DatosExtra.getMOVIL_NAC_DIS()); 
	        row.createCell(34).setCellValue(DatosExtra.getMOVIL_INT_H()); 
	        row.createCell(35).setCellValue(DatosExtra.getMOVIL_INT_M()); 
	        row.createCell(36).setCellValue(DatosExtra.getMOVIL_INT_T()); 
	        row.createCell(37).setCellValue(DatosExtra.getMOVIL_INT_19()); 
	        row.createCell(38).setCellValue(DatosExtra.getMOVIL_INT_20_24()); 
	        row.createCell(39).setCellValue(DatosExtra.getMOVIL_INT_25_29()); 
	        row.createCell(40).setCellValue(DatosExtra.getMOVIL_INT_30()); 
	        row.createCell(41).setCellValue(DatosExtra.getMOVIL_INT_DIS()); 
	        row.createCell(42).setCellValue(DatosExtra.getSERV_COM_H()); 
	        row.createCell(43).setCellValue(DatosExtra.getSERV_COM_M()); 
	        row.createCell(44).setCellValue(DatosExtra.getSERV_COM_T()); 
	        row.createCell(45).setCellValue(DatosExtra.getSERV_SOC_H()); 
	        row.createCell(46).setCellValue(DatosExtra.getSERV_SOC_M()); 
	        row.createCell(47).setCellValue(DatosExtra.getSERV_SOC_T()); 
	        row.createCell(48).setCellValue(DatosExtra.getEGEL()); 
	        row.createCell(49).setCellValue(DatosExtra.getEGEL_SOB()); 
	        row.createCell(50).setCellValue(DatosExtra.getTITU_PROG()); 
	        row.createCell(51).setCellValue(DatosExtra.getCONC()); 
	        row.createCell(52).setCellValue(DatosExtra.getEGRE_TRAB_H()); 
	        row.createCell(53).setCellValue(DatosExtra.getEGRE_TRAB_M()); 
	        row.createCell(54).setCellValue(DatosExtra.getEGRE_TRAB_T()); 
	        row.createCell(55).setCellValue(DatosExtra.getEGRE_TRAB_19()); 
	        row.createCell(56).setCellValue(DatosExtra.getEGRE_TRAB_20_24()); 
	        row.createCell(57).setCellValue(DatosExtra.getEGRE_TRAB_25_29()); 
	        row.createCell(58).setCellValue(DatosExtra.getEGRE_TRAB_30()); 
	        row.createCell(59).setCellValue(DatosExtra.getEGRE_TRAB_DIS());
	      }
	   
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    }
	  }

}