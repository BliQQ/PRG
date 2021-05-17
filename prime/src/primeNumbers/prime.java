package primeNumbers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;


public class prime{
    public static void main(String args[]) throws IOException
    {
        File inputFile = new File ("import.xlsx");
        FileInputStream inputStream = new FileInputStream(inputFile);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rows = sheet.iterator();

        while(rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()){
                Boolean isPrime = true;

                Cell cell = cellIterator.next();

                boolean isNumeric = cell.getStringCellValue().chars().allMatch(Character::isDigit);
                if (isNumeric==false)break;
                if (cell == null || cell.getCellType() == CellType.BLANK) break;

                int cellValue = Integer.valueOf(String.valueOf(cell));

                if((cellValue==1)||(cellValue==0))break;

                for (int i=2;i<cellValue;i++){
                    if(cellValue % i == 0){
                        isPrime=false;
                        break;
                    }
                }
                if(isPrime){
                    System.out.println(cellValue);
                }
            }
         }
        workbook.close();
        inputStream.close();
    }
}

