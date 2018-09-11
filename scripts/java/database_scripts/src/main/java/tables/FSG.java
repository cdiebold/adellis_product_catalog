package tables;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FSG {
    private int id;
    private String desc;

    public FSG(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String toElixir() {
        return String.format("%%FederalSupplyGroup{id: %d, name: \"%s\"}", id, desc);
    }

    public String toPostgresql(String tableName) {
        // INSERT INTO "federal_supply_groups" ("id","name") VALUES ($1,$2) [9330, "Plastics Fabricated Materials"]
        return "";
    }

    public static List<FSG> parse(File file, List<Integer> whitelist) throws IOException {
        List<FSG> fsgs = new ArrayList<>();
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
            for(int fsg : whitelist) {
                if(id == fsg) {
                    found = true;
                    break;
                }
            }
            if(found) {
                fsgs.add(new FSG(id, name));
            }

        }

        return fsgs;
    }


    public static void main(String[] args) {
        List<FSG> fsgs = null;

        Whitelist list = new Whitelist();
        try {
            list.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/Filtered FSC list.xlsx"));
            fsgs = FSG.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_FSCSHORT.xlsx"), list.getFsgs());

        } catch (Exception e) {
            e.printStackTrace();
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("fsgs.exs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("alias Adellis.Catalog.FederalSupplyGroup\n");
        printWriter.write("fsgs = [ \n");
        for (int i = 0; i < fsgs.size(); i++) {
            printWriter.write(fsgs.get(i).toElixir());
            if (i < fsgs.size() - 1) {
                printWriter.write(",\n");
            }

        }
        printWriter.write("\n");
        printWriter.write("]\n\n\n");
        printWriter.write("Enum.map(fsgs, fn fsg -> Repo.insert!(fsg) end)");
        printWriter.close();
        System.out.println(fsgs.size());
    }

}
