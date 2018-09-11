package tables;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class Manufacturer {
    private String partNumber;
    private String agency;
    private String name;

    public Manufacturer(String partNumber, String agency, String name) {
        this.partNumber = partNumber;
        this.name = name;
        if(agency == null) {
            agency = "";
        }
        else {
            this.agency = agency;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return Objects.equals(partNumber, that.partNumber) &&
                Objects.equals(agency, that.agency) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(partNumber, agency, name);
    }

    public String toElixir() {
        if(!agency.isEmpty()) {
            return String.format("%%Manufacturer{part_number: \"%s\", agency_code: \"%s\", name: \"%s\"}",
                    partNumber, agency, name);
        }

        return String.format("%%Manufacturer{part_number: \"%s\", name: \"%s\"}",
                partNumber, name);
    }

    public static List<Manufacturer> parse(File file) throws IOException {
        FileInputStream excelFile = new FileInputStream(file);
        Set<Manufacturer> manufacturers = new HashSet<>();

        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(1);
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header row
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Cell partNumberCell = row.getCell(1);
            Cell agencyCell = row.getCell(7);
            Cell nameCell = row.getCell(8);
            String partNumber = "";
            if(partNumberCell != null && partNumberCell.getCellTypeEnum() != CellType.BLANK) {
                if(partNumberCell.getCellTypeEnum() == CellType.NUMERIC) {
                    partNumber = String.valueOf(partNumberCell.getNumericCellValue());
                }
                else {
                    partNumber = partNumberCell.getStringCellValue();
                }
            }
            String agency = "";
            if(agencyCell != null && agencyCell.getCellTypeEnum() != CellType.BLANK) {
                if(agencyCell.getCellTypeEnum() == CellType.NUMERIC) {
                    agency = String.valueOf((int)agencyCell.getNumericCellValue());
                }
                else {
                    agency = agencyCell.getStringCellValue();
                }
            }
            String name = "";
            if(nameCell != null && nameCell.getCellTypeEnum() != CellType.BLANK) {
                name = nameCell.getStringCellValue();
            }

            manufacturers.add(new Manufacturer(partNumber, agency, name));
        }

        return new ArrayList<>(manufacturers);
    }

    public static void main(String[] args) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        try {
            manufacturers = Manufacturer.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_NSNMCRL(1).xlsx"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("manufacturer.exs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(manufacturers.size());

        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("alias Adellis.Catalog.Manufacturer\n");
        printWriter.write("alias Adellis.Repo\n");

        printWriter.write("manufacturers = [ \n");
        for (int i = 0; i < manufacturers.size(); i++) {
            printWriter.write(manufacturers.get(i).toElixir());
            if (i < manufacturers.size() - 1) {
                printWriter.write(",\n");
            }

        }
        printWriter.write("\n");
        printWriter.write("]\n\n\n");
        printWriter.write("Enum.map(manufacturers, fn manufacturer -> Repo.insert!(manufacturer) end)");
        printWriter.close();

    }
}
