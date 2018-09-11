package tables;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class Requirement {
    private String nsn;
    private String requirement;
    private String reply1;
    private String reply2;

    public Requirement(String nsn, String requirement, String reply1) {
        this.nsn = nsn;
        this.requirement = requirement;
        this.reply1 = reply1;
        reply2 = "";
    }

    public Requirement(String nsn, String requirement, String reply1, String reply2) {
        this(nsn, requirement, reply1);
        this.reply2 = reply2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirement that = (Requirement) o;
        return Objects.equals(nsn, that.nsn) &&
                Objects.equals(requirement, that.requirement) &&
                Objects.equals(reply1, that.reply1) &&
                Objects.equals(reply2, that.reply2);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nsn, requirement, reply1, reply2);
    }

    public String toElixir() {
        if(reply2.isEmpty())
        {
            return String.format("%%Requirement{nsn: \"%s\", requirement: \"%s\", reply_one: \"%s\"}",
                    nsn, requirement, reply1);
        }

        return String.format("%%Requirement{nsn: \"%s\", requirement: \"%s\", reply_one: \"%s\", reply_two: \"%s\"}",
                nsn, requirement, reply1, reply2);
    }

    public static List<Requirement> parse(File file) throws Exception {
        FileInputStream excelFile = new FileInputStream(file);
        Set<Requirement> requirements = new HashSet<>();

        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // there is only one sheet
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header row
        int reply2NotBlank = 0;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell nsnCell = row.getCell(1);
            Cell req = row.getCell(3);
            Cell reply1Cell = row.getCell(4);
            Cell reply2Cell = row.getCell(5);
            String nsnStr = nsnCell.getStringCellValue();
            String requirement = req.getStringCellValue();
            String reply1 = (reply1Cell.getCellTypeEnum() == CellType.NUMERIC)? String.valueOf((int)reply1Cell.getNumericCellValue())
                    : reply1Cell.getStringCellValue();

            reply1 = reply1.replaceAll(":", "-");
            reply1 = reply1.replaceAll("\"", "");

            String reply2 = "";
            if(reply2Cell.getCellTypeEnum() != CellType.BLANK) {
                reply2 = (reply2Cell.getCellTypeEnum() == CellType.NUMERIC)? String.valueOf((int)reply2Cell.getNumericCellValue())
                        : reply2Cell.getStringCellValue();

                reply2 = reply2.replaceAll(":", "-");

            }
            if(!requirement.isEmpty() && !reply1.isEmpty()) {
                if(reply2.isEmpty()) {
                    requirements.add(new Requirement(nsnStr, requirement, reply1));
                }
                else {
                    reply2NotBlank++;
                    System.out.println("reply2 not blank " + reply2NotBlank);
                    requirements.add(new Requirement(nsnStr, requirement, reply1));

                }
            }
        }

        return new ArrayList<>(requirements);

    }

    public static void main(String[] args) {
        List<Requirement> requirements = new ArrayList<>();
        try {
            requirements = Requirement.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_NSNTECHCHAR.xlsx"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("requirement.exs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("alias Adellis.Catalog.Requirement\n");
        printWriter.write("alias Adellis.Repo\n");

        printWriter.write("requirements = [ \n");
        for (int i = 0; i < requirements.size(); i++) {
            printWriter.write(requirements.get(i).toElixir());
            if (i < requirements.size() - 1) {
                printWriter.write(",\n");
            }

        }
        printWriter.write("\n");
        printWriter.write("]\n\n\n");
        printWriter.write("Enum.map(requirements, fn req -> Repo.insert!(req) end)");
        printWriter.close();
        System.out.println(requirements.size());
    }
}
