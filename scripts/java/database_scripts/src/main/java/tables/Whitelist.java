package tables;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Whitelist {
    private List<Integer> fscs;
    private List<Integer> fsgs;

    public Whitelist() {
        fscs = new ArrayList<>();
        fsgs = new ArrayList<>();

    }

    public List<Integer> getFscs() {
        return fscs;
    }

    public List<Integer> getFsgs() {
        return fsgs;
    }

    public void parse(File file) throws IOException {
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // there is only one sheet
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header row
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell fscCell = row.getCell(0);
            Cell fsgCell = row.getCell(1);

            if(fscCell.getCellTypeEnum() != CellType.BLANK) {
                fscs.add((int)fscCell.getNumericCellValue());

            }
            if(fsgCell != null && fsgCell.getCellTypeEnum() != CellType.BLANK) {
                fsgs.add((int)fsgCell.getNumericCellValue());

            }
        }
    }

    public static void main(String[] args) {
        Whitelist list = new Whitelist();
        try {
            list.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/Filtered FSC list.xlsx"));

            System.out.println(list.getFscs().size());
            System.out.println(list.getFsgs().size());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
