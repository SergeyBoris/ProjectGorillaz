package com.javarush.borisov.util.temp;

import com.javarush.borisov.db.constants.RequestStatus;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Set;

public class ExcelColorUtils {

    // вернуть HEX цвета заливки ячейки (foreground), или null если заливки нет
    public static String getCellFillHex(Cell cell, Workbook wb) {
        if (cell == null) return null;
        CellStyle style = cell.getCellStyle();
        if (style == null) return null;
        if (style.getFillPattern() == FillPatternType.NO_FILL) return null;

        // XSSF (.xlsx)
        if (wb instanceof XSSFWorkbook) {
            XSSFCellStyle xs = (XSSFCellStyle) style;
            XSSFColor xc = xs.getFillForegroundXSSFColor(); // POI 4.0+: удобный метод
            if (xc == null) {
                xc = xs.getFillForegroundColorColor() instanceof XSSFColor
                        ? (XSSFColor) xs.getFillForegroundColorColor()
                        : null;
            }
            if (xc != null) {
                byte[] rgb = xc.getRGB();
                if (rgb != null) {
                    return String.format("#%02X%02X%02X", rgb[0] & 0xFF, rgb[1] & 0xFF, rgb[2] & 0xFF);
                } else {
                    String argb = xc.getARGBHex(); // может вернуть "AARRGGBB"
                    if (argb != null && argb.length() == 8) {
                        return "#" + argb.substring(2); // убираем альфу
                    }
                }
            }
        }

        // HSSF (.xls)
        if (wb instanceof HSSFWorkbook) {
            HSSFCellStyle hs = (HSSFCellStyle) style;
            short idx = hs.getFillForegroundColor();
            HSSFPalette palette = ((HSSFWorkbook) wb).getCustomPalette();
            HSSFColor hc = palette.getColor(idx);
            if (hc != null) {
                short[] triplet = hc.getTriplet();
                return String.format("#%02X%02X%02X", triplet[0] & 0xFF, triplet[1] & 0xFF, triplet[2] & 0xFF);
            }
        }

        return null;
    }

    // простая евклидова метрика на RGB
    public static boolean isNearColor(String hex, String targetHex, int threshold) {
        if (hex == null || targetHex == null) return false;
        int[] c1 = hexToRgb(hex);
        int[] c2 = hexToRgb(targetHex);
        int dr = c1[0] - c2[0];
        int dg = c1[1] - c2[1];
        int db = c1[2] - c2[2];
        double dist = Math.sqrt(dr*dr + dg*dg + db*db);
        return dist <= threshold;
    }

    public static int[] hexToRgb(String hex) {
        hex = hex.replace("#", "");
        return new int[] {
                Integer.parseInt(hex.substring(0,2), 16),
                Integer.parseInt(hex.substring(2,4), 16),
                Integer.parseInt(hex.substring(4,6), 16)
        };
    }
    // пример использования в вашем коде:
    public static RequestStatus detectWorkType(Row row, Workbook wb) {
        // Ячейка, по которой определяем цвет
        Cell colorCell = row.getCell(0); // замените индекс на тот, где у вас цвет
        String hex = ExcelColorUtils.getCellFillHex(colorCell, wb);
      // System.out.println("cell fill hex = " + hex + " (row=" + (row.getRowNum()+1) + ")");

        if (hex == null) return null;

        // нормализуем к верхнему регистру
        hex = hex.toUpperCase();

        // Зелёные оттенки
        Set<String> greenColors = Set.of("#00B050", "#00FF00", "#92D050", "#99CC00");
        // Серые оттенки
        Set<String> grayColors = Set.of("#C0C0C0", "#808080", "#999999", "#BFBFBF" , "#FFFFFF" , "#8064A2");
        // Жёлтые оттенки
        Set<String> yellowColors = Set.of("#FFFF00", "#FFD966", "#FFC000");

        if (greenColors.contains(hex)) {
            return RequestStatus.COMPLETED;
        } else if (grayColors.contains(hex)) {
            return RequestStatus.CANCELED;
        } else if (yellowColors.contains(hex)) {
            return RequestStatus.ASSIGNED;
        }

        return null; // если цвет не совпал
    }
}