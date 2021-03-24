import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Table {
    private ArrayList<String> colNames;
    private ArrayList<Column> columns;
    private int numCol;
    private ArrayList<Row> table = new ArrayList<>();

    Table(ArrayList<String> colNames){
        numCol = colNames.size();
        this.colNames = colNames;
    }

    public static class Row implements Cloneable{
        ArrayList<Column> row;
        Row(ArrayList<Column> row){
            this.row = row;
        }

        public Column get(int num){
            return row.get(num);
        }

        public int size(){
            return row.size();
        }

        @Override
        public Object clone(){
            try {
                Row clone = (Row) super.clone();
                for (Column col : row){
                    clone.row.add((Column) col.clone());
                }
                return clone;
            }catch (CloneNotSupportedException e){
                throw new InternalError();
            }
        }
    }

    public void addColumns(ArrayList<String> types){
        columns = new ArrayList<>();
        for (String colName : types) {
            switch (colName) {
                case "IntColumn" -> columns.add(new IntColumn());
                case "FloatColumn" -> columns.add(new FloatColumn());
                case "DateColumn" -> columns.add(new DateColumn());
                case "TimeColumn" -> columns.add(new TimeColumn());
                case "GPSColumn" -> columns.add(new GPSColumn());
            }
        }
    }

    public void addStringRow(ArrayList<String> row){
        ArrayList<Column> newRow = new ArrayList<>();
        for (int i = 0; i < row.size(); i++){
            newRow.add(columns.get(i).fromStringToObject(row.get(i)));
        }
        table.add(new Row(newRow));
    }

    public void addRow(Row row){
        table.add(row);
    }

    public void sort(int colNum){
        
        Comparator<Row> comparator = Comparator.comparing(o -> o.get(colNum));
        table.sort(comparator);
    }

    public Row sumRows(int row1, int row2){
        if (row1 > table.size() - 1 || row2 > table.size() - 1){
            throw new IllegalArgumentException("Неверные индексы строк!");
        }
        Row arrRow1 = table.get(row1);
        Row arrRow2 = table.get(row2);
        ArrayList<Column> res = new ArrayList<>();

        for(int i = 0; i < arrRow1.size(); i++){
            res.add(arrRow1.get(i).addition(arrRow2.get(i)));
        }

        return new Row(res);
    }

    public void saveToFile(String fileName) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        int rowNum = 0;
        Cell cell;
        org.apache.poi.ss.usermodel.Row row;

        //заполянем строку типов
        row = sheet.createRow(rowNum);

        for(int i = 0; i < columns.size(); i++){
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(columns.get(i).getName());
        }

        //заполняем строку имен столбцов
        rowNum++;
        row = sheet.createRow(rowNum);

        for(int i = 0; i < colNames.size(); i++){
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(colNames.get(i));
        }

        //заносим данные
        for (Row value : table) {
            rowNum++;
            row = sheet.createRow(rowNum);
            for (int j = 0; j < value.size(); j++) {
                cell = row.createCell(j);
                cell.setCellValue(value.get(j).toString());
            }
        }

        //записываем в файл
        File file = new File(fileName);

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
    }

    public Table loadFromFile(String fileName) throws IOException {

        Table tableFromFile = null;
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> colTypes = new ArrayList<>();
        ArrayList<String> someRow = new ArrayList<>();

        int rowCount = 0;

        //читаем xsl файл
        FileInputStream inputStream = new FileInputStream(new File(fileName));
        //получаем экземпляр книги из xsl файла
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

        //получаем первый лист из книги
        HSSFSheet sheet = workbook.getSheetAt(0);

        //идем построчно по нашему листу
        for (org.apache.poi.ss.usermodel.Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();

            //идем по столбцам текущей строки
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                CellType cellType = cell.getCellType();

                //нулевая строка таблицы - это типы данных, сохраняем их в список colTypes
                if (rowCount == 0) {
                    colTypes.add(cell.getStringCellValue());
                }
                //первая строка таблицы - это название столбзов, записываем их в список names
                else if (rowCount == 1) {
                    names.add(cell.getStringCellValue());
                }
                //остальные строки таблицы - это просто данные, добавляем их в список someRow
                else {
                    //чтобы правильно перобразовать типы, испоьзуем switch
                    switch (cellType) {
                        case STRING -> {
                            String temp = cell.getStringCellValue();
                            someRow.add(temp);
                            System.out.println(temp + " it is String");
                        }
                        case BOOLEAN -> {
                            boolean temp = cell.getBooleanCellValue();
                            someRow.add(temp + "");
                            System.out.println(temp + " it is bool");
                        }
                        case NUMERIC -> {
                            if(colTypes.get(cell.getColumnIndex()).equals("IntColumn")){
                                int temp = (int)cell.getNumericCellValue();
                                someRow.add(temp + "");
                                System.out.println(temp + " it is int");
                            } else if(colTypes.get(cell.getColumnIndex()).equals("FloatColumn")){
                                double temp = cell.getNumericCellValue();
                                someRow.add(temp + "");
                                System.out.println(temp + " it is float");
                            }
                        }
                    }
                }
            }

            //после того, как считали тапы данных и название столбцов можно созать таблицу
            if (rowCount == 1) {
                tableFromFile = new Table(names);
                tableFromFile.addColumns(colTypes);
            }
            //добавляем строки в таблицу и очищаем список someRow
            else if (rowCount > 1) {
                tableFromFile.addStringRow(someRow);
                someRow.clear();
            }

            rowCount++;
        }

        return tableFromFile;
    }

    public void show(){
        for (String colName : colNames) {
            System.out.print(colName + " ");
        }
        System.out.println();

        for (Row row : table) {
            for (int i = 0; i < row.size(); i++) {
                System.out.print(row.get(i) + " ");
            }
            System.out.println();
        }
    }

    public void showTableInGUI(DefaultTableModel tableModel){
        tableModel.setRowCount(0);

        tableModel.setColumnIdentifiers(colNames.toArray());

        for (Row row : table) {
            tableModel.addRow(row.row.toArray());
        }
    }
}
