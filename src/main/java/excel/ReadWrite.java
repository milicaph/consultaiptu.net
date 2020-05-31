package excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ReadWrite {

    public static HashMap<String, String> hmap = new HashMap<String, String>();

    public static void readExcel(String location) {

        try {

            FileInputStream xlsxFile = new FileInputStream(new File(location));
            Workbook workbook = new XSSFWorkbook(xlsxFile);
            Sheet workSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = workSheet.iterator();

            while(iterator.hasNext()){
                Row currentRow = iterator.next();
                Cell cell1 = currentRow.getCell(0);
                Cell cell2 = currentRow.getCell(1);

                String address = cell1.toString().trim();
                String code = Integer.toString((int)cell2.getNumericCellValue());
                hmap.put(address, code);
                System.out.println(cell1.toString().trim());
                System.out.println((int) cell2.getNumericCellValue());

            }

            xlsxFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToExcel(String location, ArrayList<String> names, ArrayList<String> address1, ArrayList<String> address2){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("All results");

        int i = 0;
        for (String name : names) {
            int columName = 0, columAdd1 = 1, columAdd2 =2;
            Row row = sheet.createRow(i);

            Cell cell0 = row.createCell(columName);
            Cell cell1 = row.createCell(columAdd1);
            Cell cell2 = row.createCell(columAdd2);

            cell0.setCellValue(name);
            cell1.setCellValue(address1.get(i));
            cell2.setCellValue(address2.get(i));
            i++;


        }


        try (FileOutputStream outputStream = new FileOutputStream(location)) {
            workbook.write(outputStream);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
