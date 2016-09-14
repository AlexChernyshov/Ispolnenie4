package ru.jcross.ispolnenie4.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import ru.jcross.ispolnenie4.util.BuildReport.model.Cursor;
import ru.jcross.ispolnenie4.util.BuildReport.model.RangeDynamic;
import ru.jcross.ispolnenie4.util.BuildReport.model.TargetCell;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chernyshov on 11.07.2016.
 */
public class Buildreport {


    static final Logger rootLogger = LogManager.getRootLogger();
    static final Logger CursorLogger = LogManager.getLogger(Buildreport.class);

    private  String URL_CONNECTION = null;
    private  String USER_CONNECTION = null;
    private  String PWD_CONNECTION = null;


    private File temp = null; //файл шаблона
    private String query = null; // запрос
    private RangeDynamic rdyn = null; // Объект
   // private  StyleCursor StC = null;


    public  void TestSaveXML() throws JAXBException {
        rdyn = new RangeDynamic();
        List<Cursor> lCursors = new ArrayList<>();
        for(int i=1;i<10;i++){
            Cursor curCursor = new Cursor();
            curCursor.setId(i);
            curCursor.setStyle(2);
            List<TargetCell> lcell = new ArrayList<>();

            for(int j=1;j<15;j++){
                TargetCell tc = new TargetCell();
                tc.setId(j);
                tc.setValstring(UUID.randomUUID().toString());
                tc.setFrom("field-"+j);
                tc.setTocell("cell["+i+";"+j+"]");
                lcell.add(tc);
            }
            curCursor.setListCells(lcell);
            lCursors.add(curCursor);

        rdyn.setId(1);
        rdyn.setName("pro");
        rdyn.setSsql("SQL select from end;");
        rdyn.setCursors(lCursors);

	/* init jaxb marshaler */

        JAXBContext jaxbContext = JAXBContext.newInstance( RangeDynamic.class );
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	/* set this flag to true to format the output */
        jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
	/* marshaling of java objects in xml (output to file and standard output) */
        jaxbMarshaller.marshal( rdyn, new File( "RangeDynamic.xml" ) );
       // jaxbMarshaller.marshal( rdyn, System.out );
        }
    }



    //XML to Object
    public void preLoadObject(String sbxml){
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(RangeDynamic.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Unmarshaller jaxbUnmarshaller = null;
        try {
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        if(jaxbUnmarshaller!=null) {
            try {
                if(jaxbUnmarshaller.unmarshal(new StreamSource(new StringReader(sbxml.toString()))) instanceof RangeDynamic){
                    rdyn = (RangeDynamic) jaxbUnmarshaller.unmarshal(new StreamSource(new StringReader(sbxml.toString())));
                }
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
        this.query = rdyn.getSsql().toString();
    }



    public void setPropertyQuerys(HashMap prop) {
        Set<Map.Entry<String,String>> entery = prop.entrySet();
        for (Map.Entry<String,String> entry : entery) {
            Pattern pattern  = Pattern.compile(entry.getKey());
            Matcher matcher = pattern.matcher(this.query);
            this.query= matcher.replaceAll(entry.getValue());
            if(rootLogger.isDebugEnabled()) {
               rootLogger.debug("Key: " + entry.getKey() + " Value: " + entry.getValue());
            }
        }
        if(rootLogger.isDebugEnabled()) {
            rootLogger.debug("> " + query);
        }
    }
    //СОединение с базой
    public void setConfigDB(String url,String user, String pass){
        this.URL_CONNECTION = url;
        this.USER_CONNECTION = user;
        this.PWD_CONNECTION = pass;
    }

    private ResultSet getSourceDataSQL() {
        ResultSet rs = null;
        if (URL_CONNECTION != null) {
            rootLogger.info("* * * PostgreSQL JDBC Connection Testing * * *");
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                rootLogger.error("Где ваш PostgreSQL JDBC драйвер? "
                        + "Включите в ваш путь для библиотек!"+e.getMessage());
            }
            rootLogger.info("PostgreSQL JDBC Драйвер зарегистрирован!");
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(URL_CONNECTION, USER_CONNECTION, PWD_CONNECTION);
            } catch (SQLException e) {
                rootLogger.error("Соединение не установленно! Подробности в console"+e.getMessage());

            }
            if (connection != null) {
                rootLogger.info("Database connection ......[OK]");
                //Запрос к базе
                Statement st = null;
                try {
                    st = connection.createStatement();
                } catch (SQLException e) {
                    rootLogger.error("Statement to PostgreSQL"+e.getMessage());
                }
                //try {
                    String[] subquery = query.split("#");
                    int j= 1;
                    for(String s:subquery){

                        if(s.startsWith("SELECT")){
                            if(rootLogger.isDebugEnabled()) {
                                rootLogger.debug(j+": RESULT -"+s);
                            }
                            try {
                                rs = st.executeQuery(s);
                            } catch (SQLException e) {
                                rootLogger.error("Ошибка выполнения запроса: "+s+" "+e.getMessage());
                            }
                        }else{
                            if(rootLogger.isDebugEnabled()) {
                                rootLogger.debug(j+": EXEC -"+s);
                            }
                            try {
                                st.execute(s);
                            } catch (SQLException e) {
                                rootLogger.error("Ошибка выполнения запроса: "+s+" "+e.getMessage());

                            }
                        }

                        j++;
                    }

               /* } catch (SQLException e) {
                    e.printStackTrace();
                }*/
            }
        }
        return rs;
    }
    //загрузка данных в объект
    public void getloadObject() throws IOException, ClassNotFoundException {
        if(rdyn!=null){
                ResultSet rs = getSourceDataSQL();
                ResultSetMetaData metaData = null;
                int count = 0;
                try {
                    metaData = rs.getMetaData();
                    count = metaData.getColumnCount();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                int i =0;
                try {
                    while(rs.next()){
                        i++;
                        Cursor cur = rdyn.getCursors().get(0);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream ous = new ObjectOutputStream(baos);
                        //Сохраним курсор в поток
                        ous.writeObject(cur);
                        ous.flush();
                        ous.close();
                        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                        ObjectInputStream ois = new ObjectInputStream(bais);

                        Cursor newCursor = (Cursor)ois.readObject();
                        newCursor.setId(i);
                        newCursor.setStyle(rs.getInt("lvl"));
                        List<TargetCell> listcell = newCursor.getListCells();
                        for(TargetCell tc:listcell){

                            Object object = rs.getObject(tc.getFrom());
                            switch (object.getClass().getName()){
                                case "java.lang.Integer": tc.setValstring(String.valueOf(rs.getInt(tc.getFrom())));
                                    break;
                                case "java.math.BigDecimal": tc.setValstring(String.valueOf(rs.getBigDecimal(tc.getFrom())));
                                    break;
                                case "java.lang.String": tc.setValstring(String.valueOf(rs.getString(tc.getFrom())));
                                    break;
                            }
                        }
                        if(rootLogger.isDebugEnabled()) {
                            CursorLogger.debug(newCursor.toString());
                        }
                        rdyn.getCursors().add(newCursor);
                    }
                } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
              rootLogger.error("RangeDynamic = null");
        }
    }
    private static void copyRow(Workbook workbook, Sheet worksheet, int sourceRowNum, int destinationRowNum) {
        // Get the source / new row
        Row newRow = worksheet.getRow(destinationRowNum);
        Row sourceRow = worksheet.getRow(sourceRowNum);

        // If the row exist in destination, push down all rows by 1 else create a new row
        if (newRow != null) {
            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
        } else {
            newRow = worksheet.createRow(destinationRowNum);
        }

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Copy style from old cell and apply to new cell
            CellStyle newCellStyle = workbook.createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            ;
            newCell.setCellStyle(newCellStyle);

            // If there is a cell comment, copy
            if (newCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellType());

            // Set the cell data value
            switch (oldCell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
        }

        // If there are are any merged regions in the source row, copy to new row
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() +
                                (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
                                )),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            }
        }
    }

    private void multiplyRow(Workbook wb, Sheet sheet,Row row, int count) {
        if (count>1) {
            int rowCountAll = sheet.getLastRowNum();
            int rowCurrent = row.getRowNum();
            sheet.shiftRows(rowCurrent+1, rowCountAll, count - 1);
            for (int i = 1; i < count;i++) {
                copyRow(wb, sheet, rowCurrent, rowCurrent + i);
            }
        }
    }

    private void writeDataExcelTemplate(RangeDynamic rd, Workbook workbook ) throws IOException {

        String cellName = rd.getName();

        rd.getCursors().remove(0);
        int namedCellIdx = workbook.getNameIndex(cellName);
        Name aNamedCell = workbook.getNameAt(namedCellIdx);
        int rowCount = rd.getCursors().size();
        AreaReference aref = new AreaReference(aNamedCell.getRefersToFormula(),SpreadsheetVersion.EXCEL2007);
        CellReference[] crefs = aref.getAllReferencedCells();
        Sheet sh=workbook.getSheet(crefs[0].getSheetName());
        Row targetRow = sh.getRow(aref.getFirstCell().getRow());
        multiplyRow(workbook,sh,targetRow,rowCount);
        //Границы колонок динамического диапазона
        int beginColumn = aref.getFirstCell().getCol();
        int endColumn = aref.getLastCell().getCol();
        int beginRow = targetRow.getRowNum();
        int i=beginRow;
        ListIterator<Cursor> iter = rd.getCursors().listIterator();
        while(iter.hasNext()){
            Cursor cur =  iter.next();
            Row writeRow =sh.getRow(i);
            writeCursorInRow(cur,beginColumn,endColumn,writeRow);
            i++;
        }


    }
    private void writeCursorInRow(Cursor cursor, int firstColumn,int lastColumn,Row writerow){
        Workbook workbook =writerow.getSheet().getWorkbook();
       // Sheet s = writerow.getSheet();
        Row r = writerow;
          //  CellReference topLeft = new CellReference(c);
         //   CellReference botRight = new CellReference(c);
            for (TargetCell tc:cursor.getListCells()) {
                String cname = tc.getTocell();
                int namedCellId = workbook.getNameIndex(cname);
                if(namedCellId!=-1){
                    Name xNamedCell = workbook.getNameAt(namedCellId);
                    AreaReference areffield = new AreaReference(xNamedCell.getRefersToFormula(),SpreadsheetVersion.EXCEL2007);
                    Cell cell = r.getCell(areffield.getFirstCell().getCol());

                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            cell.setCellValue(tc.getValstring());
                            //System.out.println("Строка");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {

                            //    System.out.println("Формат даты");
                            } else {
                                cell.setCellValue(Double.valueOf(tc.getValstring()));
                            //    System.out.println("Число");
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                           // System.out.println(cell.getBooleanCellValue());
                            break;
                        default:
                           // System.out.println();
                    }
                   // cell.setCellValue(tc.getValstring());

                    if (areffield.isWholeColumnReference()) {

                    }
                }else{
                    rootLogger.error("Диапазон с именем: "+cname+" не найден");
                }
            }
            fillRow(cursor.getStyle(),writerow,firstColumn, lastColumn);
    }
    private void fillRow(int colorLvl,Row row,int fcol,int lastcol){
        Workbook workbook = row.getSheet().getWorkbook();
        int namedCellId = workbook.getNameIndex("fillcell");
        if(namedCellId!=-1) {
            Name xNamedCell = workbook.getNameAt(namedCellId);
            AreaReference areffield = new AreaReference(xNamedCell.getRefersToFormula(),SpreadsheetVersion.EXCEL2007);
            CellReference[] crefs =areffield.getAllReferencedCells();

            Sheet sh=workbook.getSheet(crefs[colorLvl].getSheetName());
            Row r = sh.getRow(crefs[colorLvl].getRow());
            Cell c = r.getCell(crefs[colorLvl].getCol());

            XSSFCellStyle sourcestyle = (XSSFCellStyle) c.getCellStyle();

            String argbHex = renderColor(sourcestyle.getFillForegroundColorColor());
            if(!argbHex.equals("(none)")){
            String rgbHex = argbHex.substring(2);
            XSSFColor foregroundcolor =new XSSFColor(DatatypeConverter.parseHexBinary(rgbHex));

            for(int i=fcol;i<=lastcol;i++){
                Cell cell = row.getCell(i);
               // cell.getCellStyle().setFillForegroundColor(style.getFillForegroundColor());
                XSSFCellStyle destStyle = (XSSFCellStyle) cell.getCellStyle();
                destStyle.setFillForegroundColor(foregroundcolor);
                destStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            }}
        }else   {
            rootLogger.error("Настройка заливки не найдена");
        }
    }

    private static String renderColor(Color color) {
        if(color instanceof HSSFColor) {
            return ((HSSFColor)color).getHexString();
        } else if(color instanceof XSSFColor) {
            return ((XSSFColor)color).getARGBHex();
        } else {
            return "(none)";
        }
    }
    public File createReport(File filetemplate) throws IOException, ClassNotFoundException {
        Workbook wb = getWorkbook(filetemplate.getAbsolutePath());

        /*================================
        Work on bookreport
         */
        writeDataExcelTemplate(rdyn,wb);
        //================================
        FileOutputStream fileOut = null;
        try {
            try {
                temp =  File.createTempFile("I_v4_",".xlsx");
                if(rootLogger.isDebugEnabled()) {
                    rootLogger.debug("Temp book:" + temp.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileOut = new FileOutputStream(temp);
        } catch (FileNotFoundException e) {
            rootLogger.error("Файл для записи не найден "+e.getMessage());

        }
        try {
            wb.write(fileOut);
        } catch (IOException e) {
            rootLogger.error("Файл защищён от записи "+e.getMessage());
        }
        try {
            fileOut.close();
        } catch (IOException e) {
            rootLogger.error("Ошибка ввода/вывода при закрытии файла "+e.getMessage());
        }
        temp.deleteOnExit();
        return temp;
    }

    private Workbook getWorkbook(String excelFilePath) throws IOException {
        File file = new File(excelFilePath);
        Workbook workbook = null;
        if(file.exists()) {
            FileInputStream fis = new FileInputStream(new File(excelFilePath));
            if (rootLogger.isDebugEnabled()) {
                rootLogger.debug("Open book:" + excelFilePath);
            }
            try {
                workbook = WorkbookFactory.create(fis);
            } catch (InvalidFormatException | EncryptedDocumentException ex) {
                rootLogger.error(Buildreport.class.getName() + ex);
            }
        }else{
            rootLogger.fatal("Файл не найден");
        }
        return workbook;
    }
}
