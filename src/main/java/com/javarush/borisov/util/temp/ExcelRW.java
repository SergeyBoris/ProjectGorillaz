package com.javarush.borisov.util.temp;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelRW {


    public List<String[]> read(String fileName, String sheet, int rowBegin, int rowEnd, int colBegin, int colEnd) {

        try (FileInputStream file = new FileInputStream(fileName);
             Workbook workbook = WorkbookFactory.create(file);) {
            List<String[]> addresses = new ArrayList<>();

            Sheet activeSheet = workbook.getSheet(sheet);
            for (int i = rowBegin; i <= rowEnd; i++) {
                String[] cells = new String[colEnd - colBegin + 1];
                int index = 0;
                Row row1 = activeSheet.getRow(i);

                if (row1 != null) {
                    for (int j = colBegin; j <= colEnd; j++) {
                        Cell cell = row1.getCell(j);
                        if (cell != null) {

                            if (cell.getCellType() == CellType.NUMERIC) {
                                cells[index] = cell.getLocalDateTimeCellValue().toString();
                            }else {
                                cells[index] = cell.getStringCellValue();
                            }


                            if (j == 4 && sheet.equalsIgnoreCase("Склад Альфа")) {
                                cells[index] = "склад";
                                index++;
                                System.out.println(i + " " + j + " " + " пусто");
                            }
                        } else {
                            cells[index] = null;
                            System.out.println("cell is null" + i + " " + j);
                            index++;
                        }
                        index++;
                    }
                    addresses.add(cells);
                } else {
                    System.out.println("cells is null " + i);
                }
            }
            return addresses;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void writeMyExcel(String filePath, Map<Integer, List<String>> data) {
        // Убедимся, что файл имеет расширение .xlsx
        if (!filePath.endsWith(".xlsx")) {
            filePath = filePath.replaceAll("\\.xlsm?$", ".xlsx");
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Альфа");

        for (Map.Entry<Integer, List<String>> entry : data.entrySet()) {
            Row row = sheet.createRow(entry.getKey());
            Cell cell = row.createCell(1);
            cell.setCellValue(entry.getValue().get(0));
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(entry.getValue().get(1));
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            System.out.println("Файл успешно сохранён: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
