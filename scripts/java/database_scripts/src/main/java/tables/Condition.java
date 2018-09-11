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

public class Condition {
    private String code;
    private String desc;

    public Condition(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String toElixir() {
        return String.format("%%Condition{code: \"%s\", description: \"%s\"}",
                code, desc);
    }

    public static List<Condition> parse(File file) throws IOException, InvalidFormatException {
        FileInputStream excelFile = new FileInputStream(file);
        List<Condition> conditions = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // there is only one sheet
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header row
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell codeCell = row.getCell(1);
            Cell descCell = row.getCell(2);
            String desc = descCell.getStringCellValue();
            Condition condition = new Condition(codeCell.getStringCellValue(), desc);

            conditions.add(condition);
        }

        return conditions;
    }

    public static void main(String[] args) {
        List<Condition> conditions = new ArrayList<>();
        try {
            conditions = Condition.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_CONDITIONTBL.xlsx"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("condition.exs");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("alias Adellis.Sales.Condition\n");
        printWriter.write("conditions = [ \n");
        for (int i = 0; i < conditions.size(); i++) {
            printWriter.write(conditions.get(i).toElixir());
            if (i < conditions.size() - 1) {
                printWriter.write(",\n");
            }

        }
        printWriter.write("\n");
        printWriter.write("]\n\n\n");
        printWriter.write("Enum.map(conditions, fn condition -> Repo.insert!(condition) end)");
        printWriter.close();
        System.out.println(conditions.size());
    }
}
