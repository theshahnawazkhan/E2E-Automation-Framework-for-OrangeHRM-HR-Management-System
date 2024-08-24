package com.fastcode.orangehrm.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling Excel file operations such as reading data from Excel sheets.
 * This class provides methods to retrieve data from an Excel file in different formats, such as lists of rows,
 * columns, or specific data based on test case names.
 */
public class ExcelUtil {

    // Workbook instance to represent the Excel file
    private Workbook workbook;

    /**
     * Constructor that initializes the ExcelUtil object and loads the Excel file from the given file path.
     *
     * @param fileName The name of the Excel file to be read.
     * The file is expected to be located in the "test-data" directory under the project root.
     */
    public ExcelUtil(String fileName) {
        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/testdata/" + fileName)) {
            // Create a Workbook instance from the file input stream
            workbook = WorkbookFactory.create(fis);
        } catch (Exception e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
    }

    /**
     * Method to retrieve all data from a specific sheet in the Excel file.
     * Each row in the sheet is represented as a String array.
     *
     * @param sheetName The name of the sheet from which data is to be extracted.
     * @return A list of String arrays, where each array represents a row in the sheet.
     */
    public List<String[]> getDataFromSheet(String sheetName) {
        List<String[]> data = new ArrayList<>(); // List to hold all rows of data
        Sheet sheet = workbook.getSheet(sheetName); // Get the sheet by name

        if (sheet == null) {
            throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in the workbook.");
        }

        // Iterate through each row in the sheet
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>(); // List to hold data for a single row

            // Iterate through each cell in the current row and add its content to rowData
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        rowData.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            rowData.add(cell.getDateCellValue().toString());
                        } else {
                            rowData.add(String.valueOf(cell.getNumericCellValue()));
                        }
                        break;
                    case BOOLEAN:
                        rowData.add(String.valueOf(cell.getBooleanCellValue()));
                        break;
                    case FORMULA:
                        rowData.add(cell.getCellFormula());
                        break;
                    case BLANK:
                        rowData.add("");
                        break;
                    default:
                        rowData.add("Unsupported cell type");
                        break;
                }
            }
            // Convert rowData list to array and add it to the data list
            data.add(rowData.toArray(new String[0]));
        }

        // Return the list of rows, each represented as a string array
        return data;
    }

    /**
     * Method to retrieve data from a specific column in a sheet, identified by the column name.
     * The first row of the sheet is expected to be the header row.
     *
     * @param sheetName  The name of the sheet from which data is to be extracted.
     * @param columnName The name of the column whose data is to be retrieved.
     * @return A list of strings representing the data in the specified column.
     * @throws IllegalArgumentException if the column or sheet does not exist.
     */
    public List<String> getColumnData(String sheetName, String columnName) {
        List<String> columnData = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(0);

        int columnIndex = -1;
        // Identify the index of the column with the specified name
        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                columnIndex = cell.getColumnIndex();
                break;
            }
        }

        if (columnIndex == -1) {
            throw new IllegalArgumentException("Column " + columnName + " does not exist in the sheet.");
        }

        // Iterate through all rows after the header row and collect data from the identified column
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(columnIndex);
                columnData.add(cell.toString());
            }
        }

        return columnData;
    }

    /**
     * Method to retrieve data for a specific test case by name.
     * The first column of the sheet is expected to contain the test case names.
     *
     * @param sheetName    The name of the sheet from which data is to be extracted.
     * @param testCaseName The name of the test case whose data is to be retrieved.
     * @return A String array representing the row of data associated with the specified test case.
     * @throws IllegalArgumentException if the test case name is not found in the sheet.
     */
    public String[] getDataByTestCaseName(String sheetName, String testCaseName) {
        Sheet sheet = workbook.getSheet(sheetName);
        for (Row row : sheet) {
            Cell firstCell = row.getCell(0);
            if (firstCell != null && firstCell.getStringCellValue().equalsIgnoreCase(testCaseName)) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(cell.toString());
                }
                return rowData.toArray(new String[0]);
            }
        }
        throw new IllegalArgumentException("Test case " + testCaseName + " not found in sheet " + sheetName);
    }

    /**
     * Method to close the Workbook and release any resources associated with it.
     * This method should be called after all operations on the Excel file are completed.
     */
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
