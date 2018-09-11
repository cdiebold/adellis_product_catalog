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

public class Demil {
    private String code;
    private String description;

    public Demil(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String toElixir() {
        return String.format("%%Demilitarization{code: \"%s\", description: \"%s\"}",
                code, description);
    }

    public String toPostgresql(String tableName) {
        return String.format("INSERT INTO \"%s\" (\"code\", \"description\") VALUES ($1, $2) [\"%s\", \"%s\"]",
                tableName, code, description);
    }

    public static List<Demil> parse(File file) throws IOException, InvalidFormatException {
        FileInputStream excelFile = new FileInputStream(file);
        List<Demil> demils = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // there is only one sheet
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header row
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell codeCell = row.getCell(1);
            Cell descCell = row.getCell(2);
            String desc = descCell.getStringCellValue();
            desc = desc.replaceAll("\"", "");
            Demil demil = new Demil(codeCell.getStringCellValue(), desc);

            demils.add(demil);
        }

        return demils;
    }

    public void writeToFile(String fileToParse, String fileToCreate) {

    }

    public static void main(String[] args) {
        List<Demil> demils = null;
        // /home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_DEMIL.xlsx
        try {
            demils = Demil.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_DEMIL.xlsx"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("demil.exs");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("alias Adellis.Catalog.Demilitarization\n");
        printWriter.write("demils = [ \n");
        for (int i = 0; i < demils.size(); i++) {
            printWriter.write(demils.get(i).toElixir());
            if (i < demils.size() - 1) {
                printWriter.write(",\n");
            }

        }
        printWriter.write("\n");
        printWriter.write("]\n\n\n");
        printWriter.write("Enum.map(demils, fn demil -> Repo.insert!(demil) end)");
        printWriter.close();
        System.out.println(demils.size());
    }
}