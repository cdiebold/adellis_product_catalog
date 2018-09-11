package tables;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FSC {
    private int id;
    private String desc;

    public FSC(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String toElixir() {
        return String.format("%%FederalSupplyClassification{id: %d, name: \"%s\"}", id, desc);
    }

    public String toPostgresql(String tableName) {
        // INSERT INTO "federal_supply_classifications" ("id","name") VALUES ($1,$2) [9330, "Plastics Fabricated Materials"]
        return "";
    }

    public static List<FSC> parse(File file, List<Integer> whitelist) throws IOException {
        List<FSC> fscs = new ArrayList<>();
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // there is only one sheet
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header row

        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int id = (int)row.getCell(0).getNumericCellValue();
            String name = row.getCell(1).getStringCellValue();

            boolean found = false;
            for(int fsc : whitelist) {
                if(id == fsc) {
                    found = true;
                    break;
                }
            }
            if(found) {
                fscs.add(new FSC(id, name));
            }

        }

        return fscs;
    }


    public static void main(String[] args) {
        List<FSC> fscs = null;

        Whitelist list = new Whitelist();
        try {
            list.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/Filtered FSC list.xlsx"));
            fscs = FSC.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_FSCLONG.xlsx"), list.getFscs());

        } catch (Exception e) {
            e.printStackTrace();
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("fscs.exs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("alias Adellis.Catalog.FederalSupplyClassification\n");
        printWriter.write("fscs = [ \n");
        for (int i = 0; i < fscs.size(); i++) {
            printWriter.write(fscs.get(i).toElixir());
            if (i < fscs.size() - 1) {
                printWriter.write(",\n");
            }

        }
        printWriter.write("\n");
        printWriter.write("]\n\n\n");
        printWriter.write("Enum.map(fscs, fn fsc -> Repo.insert!(fsc) end)");
        printWriter.close();
        System.out.println(fscs.size());
    }

}
