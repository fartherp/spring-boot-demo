/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

import com.github.fartherp.demo.common.util.FormatUtils;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/19
 */
public class PDFTest {
    @Test
    @DisplayName("😱")
    public void main() throws Exception {
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(FormatUtils.TEMPLATE_RESOURCE_PATH);
        String html = IOUtils.toString(resources[0].getInputStream(), Charset.forName("GBK"));
        String[] h = html.split("\\$\\$\\{}");
        List<String> htmls = Collections.unmodifiableList(Arrays.asList(h));
        StringBuilder sb = new StringBuilder();
        sb.append(htmls.get(0));
        sb.append("20183732736");
        sb.append(htmls.get(1));
        sb.append("开发机械");
        sb.append(htmls.get(2));
        sb.append("测试物流");
        sb.append(htmls.get(3));
        sb.append("20180064");
        sb.append(htmls.get(4));
        sb.append("2,567");
        sb.append(htmls.get(5));
        sb.append("食品");
        sb.append(htmls.get(6));
        sb.append("箱");
        sb.append(htmls.get(7));
        sb.append("80");
        sb.append(htmls.get(8));
        sb.append("10");
        sb.append(htmls.get(9));
        sb.append("30");
        sb.append(htmls.get(10));
        sb.append("浙江杭州");
        sb.append(htmls.get(11));
        sb.append("陕西西安");
        sb.append(htmls.get(12));
        sb.append("开发");
        sb.append(htmls.get(13));
        sb.append("测试");
        sb.append(htmls.get(14));
        html = sb.toString();
        String pdfDest = "D:\\contract.pdf";
        PdfWriter writer = new PdfWriter(pdfDest);
        PdfDocument pdfDoc = new PdfDocument(writer);

        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fp = new FontProvider();
        fp.addStandardPdfFonts();
        fp.addDirectory(resources[0].getFile().getParent());

        converterProperties.setFontProvider(fp);
        HtmlConverter.convertToPdf(html, pdfDoc, converterProperties);
    }
}
