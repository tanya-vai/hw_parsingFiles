package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import guru.qa.domain.Shop;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipFile;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.*;

public class FileParseTest {

    ClassLoader classLoader = FileParseTest.class.getClassLoader();
    String zipName = "test1.zip",
            pdfName = "test.pdf",
            csvName = "testic.csv",
            xlsxName = "test (2).xlsx";

   InputStream getFileFromZip(String fileName) throws Exception {
        File zipFile = new File("src/test/resources/" + zipName);
        ZipFile zip = new ZipFile(zipFile);
        return zip.getInputStream(zip.getEntry(fileName));
    }

    @Test
    void parsePdfTest() throws Exception {
        try (InputStream pdfFileStream = getFileFromZip(pdfName)) {
            PDF pdf = new PDF(pdfFileStream);
            assertThat(pdf.text).contains("Lorem ipsum");
            assertThat(pdf.numberOfPages).isEqualTo(5);
        }
    }

    @Test
    void parseXlsxTest() throws Exception {
        try (InputStream xlsxFileStream = getFileFromZip(xlsxName)) {
            XLS xlsx = new XLS(xlsxFileStream);
            assertThat(xlsx
                    .excel
                    .getSheetAt(0)
                    .getRow(4)
                    .getCell(1)
                    .getStringCellValue())
                    .contains("Kathleen");
        }
    }

    @Test
    void parseCsvTest() throws Exception {
        try (InputStream csvFileStream = getFileFromZip(csvName)) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(csvFileStream, UTF_8));
            List<String[]> csv = csvReader.readAll();
            assertThat(csv).contains(
                    new String[]{"Date (UTC time)", "Segment ID", "Segment Name", "Inventory Delivery Name", "Target", "Volumes"},
                    new String[]{"2022-06-18", "resumed", "seg1", "INV", "15"}
            );
        }
    }


    @Test
    void jsonTestWithJackson() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        InputStream is = classLoader.getResourceAsStream("shop.json");
        Shop jsonObject = mapper.readValue(new InputStreamReader(is), Shop.class);
        assertThat(jsonObject.getName()).isEqualTo("Sosedi");
        assertThat(jsonObject.getDepartmentQuantity()).isEqualTo(1032);
        assertThat(jsonObject.getLocations()).contains("Minsk", "Grodno", "Vitebsk");
        assertThat(jsonObject.isActive()).isEqualTo(true);
        assertThat(jsonObject.getEntityData().getPhone()).isEqualTo("+375447645607");
        assertThat(jsonObject.getEntityData().getInn()).isEqualTo(38947389274810L);

    }
}
