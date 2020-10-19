package edu.zju.gis.dldsj.server.pojo;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import java.io.*;
import java.util.List;

/**
 * @author wll 2020/10/17
 */
@Getter
@Setter
public class DocxToMd {
    // 段落
    private List<XWPFParagraph> paragraphs;
    // 表格
    private List<XWPFTable> tables;
    // 图片
    private List<XWPFPictureData> allPictures;
    // 页眉
    private List<XWPFHeader> headerList;
    // 页脚
    private List<XWPFFooter> footerList;
    //图片编号
    private int picIndex = 0;

    public void readFromdocx(String path) throws IOException {
        XWPFDocument doc = new XWPFDocument(new FileInputStream(path));
        paragraphs = doc.getParagraphs();
        tables = doc.getTables();
        allPictures = doc.getAllPictures();
        headerList = doc.getHeaderList();
        footerList = doc.getFooterList();
    }

    public static String matchStyle(String style, String text) {
        StringBuilder resultText= new StringBuilder();
        switch (style)
        {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
                for(int i = 0; i < Integer.parseInt(style);i++) {
                    resultText.append("#");
                }
                resultText.append(" ").append(text);
                break;
            default:
                if(text!=null)
                    resultText = new StringBuilder(text);
        }
        return resultText.toString();
    }

    public String paragraphTrans(List<XWPFRun> xwpfRuns, int parIndex, String pathPrefix) throws IOException {
        String result = "";
        String paragraphStyle = null;
        StringBuilder preRun = new StringBuilder();
        StringBuilder paragraphText = new StringBuilder();

        int runIndex = 0;
        boolean isTitle = false;

        for(XWPFRun run : xwpfRuns) {
            //该run是否存在图片
            List<XWPFPicture> embeddedPictures = run.getEmbeddedPictures();
            for (XWPFPicture xwpfPicture : embeddedPictures) {
                byte[] data = xwpfPicture.getPictureData().getData();
                //输出图片
                String pathName = pathPrefix + "/" + "pic"+ picIndex + ".png";
                FileImageOutputStream imageOutput = new FileImageOutputStream(new File(pathName));
                imageOutput.write(data, 0, data.length);
                imageOutput.close();

                paragraphText.append("![image](").append("pic"+ picIndex + ".png").append(")");
                picIndex++;
            }

            //该run是否存在文字
            String pureText = run.getText(0);
            if(pureText == null)
                pureText = "";
            paragraphStyle = run.getParagraph().getStyle();

            //是否加粗
            if(run.isBold()){
                if(!preRun.toString().contains("Bold")) {
                    paragraphText.append("**");
                    preRun.append("Bold");
                }
            }else{
                if(preRun.toString().contains("Bold")){
                    paragraphText.append("**");
                }
            }

            //是否斜体
            if(run.isItalic()){
                if(!preRun.toString().contains("Italic")) {
                    paragraphText.append("*");
                    preRun.append("Italic");
                }
            }else {
                if(preRun.toString().contains("Italic")){
                    paragraphText.append("*");
                }
            }

            //是否有删除线
            String underlineColor = run.getUnderlineColor();
            run.getUnderlineThemeColor();
            if(run.isStrikeThrough()){
                if(!preRun.toString().contains("Strikeline")) {
                    paragraphText.append("~~");
                    preRun.append("Strikeline");
                }
            }else {
                if(preRun.toString().contains("Strikeline")){
                    paragraphText.append("~~");
                }
            }

            //是否是标题
            if(paragraphStyle != null){
                isTitle = true;
            }

            //都不是
            if(!run.isBold() && !run.isItalic() && !run.isStrikeThrough() &&paragraphStyle==null){
                preRun = new StringBuilder();
            }

            paragraphText.append(pureText);
            runIndex++;
        }

        //如果特殊字符恰好在段末
        if(preRun.toString().contains("Bold")){
            paragraphText.append("**");
        }
        if(preRun.toString().contains("Italic")){
            paragraphText.append("*");
        }
        if(preRun.toString().contains("Strikeline")){
            paragraphText.append("~~");
        }

        if(isTitle){
            result = matchStyle(paragraphStyle,paragraphText.toString());
        }else {
            result = paragraphText.toString();
        }
        return result+"\n";
    }

    public String tableTrans(XWPFTable xwpfTable, int tableIndex) {
        String result = "";
        StringBuilder tableBuilder = new StringBuilder();

        List<XWPFTableRow> rows = xwpfTable.getRows(); //获取所有行

        int rowIndex = 0;
        for (XWPFTableRow row : rows) { //遍历每行
            List<XWPFTableCell> cells = row.getTableCells(); //获取每行单元格

            for (XWPFTableCell cell : cells) { //遍历单元格
                tableBuilder.append("|");

                List<XWPFParagraph> paragraphs = cell.getParagraphs(); //获取单元格段落
                for (XWPFParagraph paragraph : paragraphs) {
                    List<XWPFRun> runs = paragraph.getRuns();//获取单元格文本
                    for (XWPFRun run : runs) {
                        String pureText = run.getText(0);
                        tableBuilder.append(pureText);
                    }
                }
            }

            tableBuilder.append("|");

            if(rowIndex == 0){
                tableBuilder.append("\n");
                for(int i=0;i<cells.size();i++)
                    tableBuilder.append("|--");
                tableBuilder.append("|");
            }

            tableBuilder.append("\n");
            rowIndex++;
        }
        result = tableBuilder.toString();
        return result;
    }

    public void docxToMarkdown(String inputPath, String outputPath) throws IOException{
        XWPFDocument doc = new XWPFDocument(new FileInputStream(inputPath));
        List<IBodyElement> elements=  doc.getBodyElements();//获取所有元素

        int paragraphIndex = 0;
        int tableIndex = 0;
        String pathPrefix = outputPath.substring(0,outputPath.lastIndexOf('/'));
        FileOutputStream fos = new FileOutputStream(new File(outputPath));
        for(IBodyElement element:elements){
            System.out.print(element.getElementType()+" ");

            if(BodyElementType.PARAGRAPH.equals(element.getElementType())){
                List<XWPFRun> xwpfRuns = element.getBody().getParagraphArray(paragraphIndex).getRuns();
                String paragraphText = paragraphTrans(xwpfRuns,paragraphIndex,pathPrefix);
                System.out.print(paragraphText);
                fos.write(paragraphText.getBytes());
                fos.flush();
                paragraphIndex++;
            }else if (BodyElementType.TABLE.equals(element.getElementType())){
                XWPFTable xwpfTable = element.getBody().getTableArray(tableIndex);
                String tableText = tableTrans(xwpfTable,tableIndex);
                System.out.print(tableText);
                fos.write(tableText.getBytes());
                fos.flush();
                tableIndex++;
            }else {
                System.out.println("Unknown ElementType.");
            }
        }

        fos.close();
    }

    public void docxToMarkdown(FileInputStream fileInputStream, String outputPath) throws IOException{
        XWPFDocument doc = new XWPFDocument(fileInputStream);
        List<IBodyElement> elements=  doc.getBodyElements();//获取所有元素

        int paragraphIndex = 0;
        int tableIndex = 0;
        String pathPrefix = outputPath.substring(0,outputPath.lastIndexOf('/'));
        FileOutputStream fos = new FileOutputStream(new File(outputPath));
        for(IBodyElement element:elements){
            System.out.print(element.getElementType()+" ");

            if(BodyElementType.PARAGRAPH.equals(element.getElementType())){
                List<XWPFRun> xwpfRuns = element.getBody().getParagraphArray(paragraphIndex).getRuns();
                String paragraphText = paragraphTrans(xwpfRuns,paragraphIndex,pathPrefix);
                System.out.print(paragraphText);
                fos.write(paragraphText.getBytes());
                fos.flush();
                paragraphIndex++;
            }else if (BodyElementType.TABLE.equals(element.getElementType())){
                XWPFTable xwpfTable = element.getBody().getTableArray(tableIndex);
                String tableText = tableTrans(xwpfTable,tableIndex);
                System.out.print(tableText);
                fos.write(tableText.getBytes());
                fos.flush();
                tableIndex++;
            }else {
                System.out.println("Unknown ElementType.");
            }
        }

        fos.close();
    }

    public static FileInputStream transferToFileInputStream(MultipartFile multipartFile) throws IOException {
        //选择用缓冲区来实现这个转换即使用java创建的临时文件
        //使用MultipartFile.transferto()方法
        FileInputStream ips = null;
        File file1 = null;

        file1 = File.createTempFile("temp", null);
        multipartFile.transferTo(file1);
        ips = new FileInputStream(file1);
        file1.deleteOnExit();

        return ips;
    }

}
