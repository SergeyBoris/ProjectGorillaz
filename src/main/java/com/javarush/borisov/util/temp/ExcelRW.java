package com.javarush.borisov.util.temp;

import com.javarush.borisov.db.constants.RequestStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelRW {
    private static final List<CellRangeAddress> mergedRegion = new ArrayList<>();

    public List<Map<String, String>> readReqRow(String fileName, String openSheet, int rowToRead,int rowEnd) {
        int reqCount = rowEnd-rowToRead+1;
        rowToRead = rowToRead - 1;
        List<Map<String, String>> result = new ArrayList<>();

        try (FileInputStream file = new FileInputStream(fileName);
             Workbook workbook = WorkbookFactory.create(file);) {
            Sheet sheet = workbook.getSheet(openSheet);
            for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
                mergedRegion.add(sheet.getMergedRegion(i));
            }
            for (int l = 0; l < reqCount; l++) {
                if (rowToRead == 0) {
                    rowToRead++;
                    continue;
                }


                Row row = sheet.getRow(rowToRead);

                if (row != null) {

                    Cell firstCell = row.getCell(0);

                    if (firstCell != null) {
                        if (!isMergedCell(rowToRead, 0)) {

                            Map<String, String> req = new LinkedHashMap<>();

                            RequestStatus cellFillHex = ExcelColorUtils.detectWorkType(row, workbook);
                            if (cellFillHex == null) {
                                System.out.println("cellFillHex is null " + (row.getRowNum() + 1));

                            }
                            //  System.out.println("cellFillHex = " + cellFillHex );


                            String[] reqStrings = new String[reqCount];
                            for (int i = 0; i < reqCount; i++) {
                                Cell cell = row.getCell(i);
                                if (cell != null) {
                                    CellType cellType = cell.getCellType();

                                    if (cellType == CellType.STRING) {
                                        if (cell.getStringCellValue().trim().isEmpty() || cell.getStringCellValue().trim().equals(" ")) {
                                            reqStrings[i] = null;
                                        } else {
                                            reqStrings[i] = cell.getStringCellValue().trim();
                                        }

                                    } else if (cellType == CellType.NUMERIC) {
                                        if (i == 4) {
                                            String replace = String.valueOf(cell.getNumericCellValue()).replace(".", "");
                                            String e = replace.substring(0, replace.indexOf("E") - 1);
                                            reqStrings[i] = e;
                                        } else if (DateUtil.isCellDateFormatted(cell)) {
                                            Date date = cell.getDateCellValue();
                                            reqStrings[i] = new SimpleDateFormat("dd.MM.yyyy").format(date);
                                        } else {
                                            reqStrings[i] = String.valueOf(cell.getNumericCellValue());
                                        }
                                    }
                                    // System.out.println(reqStrings[i]);
                                } else {
                                    reqStrings[i] = "";
                                }
                            }

                            req.put("ReqNum", reqStrings[0]);
                            req.put("TST", reqStrings[1]);
                            req.put("Customer", reqStrings[2]);
                            req.put("CustomerPhone", reqStrings[3]);
                            req.put("Tid", reqStrings[4]);
                            req.put("WorkType", reqStrings[5]);
                            if (!cellFillHex.equals(RequestStatus.CANCELED)) {
                                req.put("ReqStatus" , cellFillHex.toString());
                                if (isNull(reqStrings[6]) && isNull(reqStrings[7])) {
                                    req.put("EquipmentMontage", null);

                                } else if (isNull(reqStrings[6]) && !isNull(reqStrings[7])) {


                                    System.out.println("ОШИБКА В ОБОРУДОВАНИИ!!! 1 " + reqStrings[0]);
                                    System.out.println("|" + reqStrings[7] + "|");
                                    req.put("EquipmentMontage", reqStrings[7].toUpperCase());

                                } else if (!isNull(reqStrings[6]) && isNull(reqStrings[7])) {

                                    System.out.println("ОШИБКА В ОБОРУДОВАНИИ!!! 2 " + reqStrings[0]);
                                    System.out.println(reqStrings[6]);
                                    req.put("EquipmentMontage", reqStrings[6].toUpperCase());

                                } else {
                                    // оба присутствуют
                                    req.put("EquipmentMontage", reqStrings[6].toUpperCase() + " / " + reqStrings[7].toUpperCase());
                                }
                                if (isNull(reqStrings[8]) && isNull(reqStrings[9])) {
                                    req.put("EquipmentUnMontage", null);
                                } else if (isNull(reqStrings[8]) && !isNull(reqStrings[9])) {

                                    System.out.println("ОШИБКА В ОБОРУДОВАНИИ!!! 1 " + reqStrings[0]);
                                    System.out.println("|" + reqStrings[9] + "|");


                                } else if (!isNull(reqStrings[8]) && isNull(reqStrings[9])) {

                                    System.out.println("ОШИБКА В ОБОРУДОВАНИИ!!! 2 " + reqStrings[0]);
                                    System.out.println(reqStrings[8]);
                                } else {

                                    // оба присутствуют
                                    req.put("EquipmentUnMontage", reqStrings[8].toUpperCase() + " / " + reqStrings[9].toUpperCase());
                                }
                            }else {
                                req.put("ReqStatus" , cellFillHex.toString());
                            }

                            req.put("Address", reqStrings[10]);
                            req.put("Priority", reqStrings[11]);
                            req.put("Zone", reqStrings[12]);
                            req.put("DateOfComplete", reqStrings[13]);
                            req.put("Sim", reqStrings[14]);
                            req.put("Comment", reqStrings[15]);
                            req.put("Range", reqStrings[18]);
                            req.put("ClosedDate", reqStrings[19]);
//                            System.out.println();
//                            System.out.println("MAPA : ");
//                            System.out.println();
                            // req.forEach((k, v) -> System.out.println(k + ": " + v));
                            result.add(req);
                        } else {
                            // System.out.println("first cell is merged " + (rowToRead + 1));
                        }
                        rowToRead++;
                    } else {
                        System.out.println("first cell is null " + (rowToRead + 1));
                        rowToRead++;
                    }

                } else {
                    System.out.println("row is null " + (rowToRead + 1));
                    rowToRead++;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private boolean isNull(String str) {
        return str == null || str.trim().isEmpty() || str.trim().equals("null") || str.isBlank();

    }


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
                            } else {
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

    private static boolean isMergedCell(int rowIndex, int colIndex) {
        for (CellRangeAddress range : mergedRegion) {
            if (range.isInRange(rowIndex, colIndex)) {
                return true; // ячейка входит в объединённый диапазон
            }
        }
        return false;
    }

}
