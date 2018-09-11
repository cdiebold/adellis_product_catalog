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

public class ShelfLife {
    private String code;
    private int life;

    public ShelfLife(String code, int life) {
        this.code = code;
        this.life = life;
    }

    public String toElixir() {
        return String.format("%%ShelfLife{code: \"%s\", life: %d}",
                code, life);
    }
    public static List<ShelfLife> parse(File file) throws IOException, InvalidFormatException {

        FileInputStream excelFile = new FileInputStream(file);
        List<ShelfLife> shelfLives = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // there is only one sheet
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header row
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell codeCell = row.getCell(1);
            Cell lifeCell = row.getCell(2);
            int life = (int)lifeCell.getNumericCellValue();
            ShelfLife sl = new ShelfLife(codeCell.getStringCellValue(), life);

            shelfLives.add(sl);
        }

        return shelfLives;
    }

    public static void main(String[] args) {
        List<ShelfLife> shelfLives = new ArrayList<>();
        try {
            shelfLives = ShelfLife.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_SHELFLIFE.xlsx"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("shelf_life.exs");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("alias Adellis.Catalog.ShelfLife\n");
        printWriter.write("shelf_lives = [ \n");
        for (int i = 0; i < shelfLives.size(); i++) {
            printWriter.write(shelfLives.get(i).toElixir());
            if (i < shelfLives.size() - 1) {
                printWriter.write(",\n");
            }

        }
        printWriter.write("\n");
        printWriter.write("]\n\n\n");
        printWriter.write("Enum.map(shelf_lives, fn shelf_life -> Repo.insert!(shelf_life) end)");
        printWriter.close();
        System.out.println(shelfLives.size());
    }
}
