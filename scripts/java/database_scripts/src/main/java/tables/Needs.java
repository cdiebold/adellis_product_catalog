package tables;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Needs {
    private String code;
    private String desc;

    public Needs(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String toElixir() {
        return String.format("%%Priority{code: \"%s\", description: \"%s\"}",
                code, desc);
    }

    public static List<Needs> parse(File file) throws IOException, InvalidFormatException {
        FileInputStream excelFile = new FileInputStream(file);
        List<Needs> needsList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // there is only one sheet
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header row
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell codeCell = row.getCell(1);
            Cell descCell = row.getCell(2);
            String desc = descCell.getStringCellValue();
            Needs need = new Needs(codeCell.getStringCellValue(), desc);

            needsList.add(need);
        }

        return needsList;
    }

    public static void main(String[] args) {
        List<Needs> needsList = new ArrayList<>();
        try {
            needsList = Needs.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_NEEDS.xlsx"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("priorities.exs");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("alias Adellis.Sales.Priority\n");
        printWriter.write("priorities = [ \n");
        for (int i = 0; i < needsList.size(); i++) {
            printWriter.write(needsList.get(i).toElixir());
            if (i < needsList.size() - 1) {
                printWriter.write(",\n");
            }

        }
        printWriter.write("\n");
        printWriter.write("]\n\n\n");
        printWriter.write("Enum.map(priorities, fn priority -> Repo.insert!(priority) end)");
        printWriter.close();
        System.out.println(needsList.size());
    }
}
