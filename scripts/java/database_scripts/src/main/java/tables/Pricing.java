package tables;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class Pricing {
    private String nsn;
    private String price;
    private String aac;
    private String quantityPerUnitPack;
    private String unitIssue;
    private int priceAsInt;

    public Pricing(String nsn, int price, String aac, String quantityPerUnitPack, String unitIssue) {
        this.nsn = nsn;
        this.priceAsInt = price;
        if (aac.isEmpty() == false) {
            this.aac = aac;
        }
        if (quantityPerUnitPack.isEmpty() == false) {
            this.quantityPerUnitPack = quantityPerUnitPack;
        }
        if (unitIssue.isEmpty() == false) {
            this.unitIssue = unitIssue;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pricing pricing = (Pricing) o;
        return Objects.equals(nsn, pricing.nsn) &&
                Objects.equals(price, pricing.price) &&
                Objects.equals(quantityPerUnitPack, pricing.quantityPerUnitPack) &&
                Objects.equals(unitIssue, pricing.unitIssue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nsn, price, quantityPerUnitPack, unitIssue);
    }
    static int errorCnt = 0;

    static int formatPrice(String price) {

        int indexOfDot = price.lastIndexOf(".");
        if(price.length() - (indexOfDot + 1) != 2) {
            price = price + "0";
        }
        if(price.length() - (indexOfDot + 1) > 2) {
            errorCnt++;
        }

        // remove the dot
        price = price.replace(".", "");


            return Integer.parseInt(price);
    }

    public String toElixir() {
        if( aac == null) {
            aac = "";
        }
        if(unitIssue == null){
            unitIssue = "";
        }
        if(quantityPerUnitPack == null) {
            quantityPerUnitPack = "";
        }
        if (priceAsInt != 0 && !unitIssue.isEmpty()) {
            return String.format("%%Pricing{nsn: \"%s\", price: %d, aac: \"%s\", quantity_per_unit_pack: \"%s\", unit_issue: \"%s\"}",
                    nsn, priceAsInt, aac, quantityPerUnitPack, unitIssue);
        }
        return "";
    }

    public static List<Pricing> parse(File file) throws Exception {
        Set<Pricing> pricings = new HashSet<>();
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(2);// NSN_MLC
        Iterator<Row> rowIterator = sheet.rowIterator();
        rowIterator.next(); // skip header row

        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell nsnCell = row.getCell(0);
            Cell unitIssueCell = row.getCell(4);
            Cell qupCell = row.getCell(5);
            Cell aacCell = row.getCell(6);
            Cell priceCell = row.getCell(10);

            String nsn = nsnCell.getStringCellValue();
            String price = priceCell.getCellTypeEnum() == CellType.NUMERIC ? String.valueOf(priceCell.getNumericCellValue())
                    : priceCell.getStringCellValue();
            int priceAsInt = formatPrice(price);

            if(price.charAt(0) == '$') {
                price = price.substring(1); // strip off $
            }
            String aac = "";
            if(aacCell != null && aacCell.getCellTypeEnum() != CellType.BLANK) {
                aac = aacCell.getCellTypeEnum() == CellType.NUMERIC ? String.valueOf(aacCell.getNumericCellValue()) :
                        aacCell.getStringCellValue();
            }

            String qup = "";
            if(qupCell != null && qupCell.getCellTypeEnum() != CellType.BLANK) {
                qup = qupCell.getCellTypeEnum() == CellType.NUMERIC ? String.valueOf(qupCell.getNumericCellValue()) :
                        qupCell.getStringCellValue();
            }

            String unitIssue = "";
            if(unitIssueCell != null && unitIssueCell.getCellTypeEnum() != CellType.BLANK) {
                unitIssue = unitIssueCell.getStringCellValue();
            }

            pricings.add(new Pricing(nsn, priceAsInt, aac, qup, unitIssue));
        }

        return new ArrayList<>(pricings);
    }

    public static void main(String[] args) {
        List<Pricing> pricings = new ArrayList<>();
        try {
            pricings = Pricing.parse(new File("/home/chris/developer/adellis_phx/scripts/java/database_scripts/src/main/resources/data/export_table_NSNMCRL.xlsx"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("pricing.exs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(pricings.size());
        System.out.println(Pricing.errorCnt);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.write("alias Adellis.Catalog.Pricing\n");
        printWriter.write("alias Adellis.Repo\n");
        printWriter.write("pricings = [ \n");
        for (int i = 0; i < pricings.size(); i++) {
            String price = pricings.get(i).toElixir();
            if(price.equals("")) {
                continue;
            }
            printWriter.write(price);

            if (i < pricings.size() - 1) {
                printWriter.write(",\n");
            }

        }
        printWriter.write("\n");
        printWriter.write("]\n\n\n");
        printWriter.write("Enum.map(pricings, fn price -> Repo.insert!(price) end)");
        printWriter.close();

    }
}