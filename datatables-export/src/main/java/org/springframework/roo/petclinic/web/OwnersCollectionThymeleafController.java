package org.springframework.roo.petclinic.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.roo.petclinic.domain.Owner;
import org.springframework.roo.petclinic.web.reports.JasperReportsCsvExporter;
import org.springframework.roo.petclinic.web.reports.JasperReportsExporter;
import org.springframework.roo.petclinic.web.reports.JasperReportsPdfExporter;
import org.springframework.roo.petclinic.web.reports.JasperReportsXlsExporter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import io.springlets.data.domain.GlobalSearch;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * = OwnersCollectionThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Owner.class, type = ControllerType.COLLECTION)
@RooThymeleaf
public class OwnersCollectionThymeleafController {

	/**
	 * It delegates in the {@code export} method providing the necessary information
	 * to generate a CSV report.
	 * 
	 * @param search GlobalSearch that contains the filter provided by the Datatables component
	 * @param pageable Pageable that contains the Sort info provided by the Datatabes component
	 * @param datatablesColumns Columns displayed in the Datatables component
	 * @param response The HttpServletResponse
	 * @throws JRException 
	 * @throws IOException
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	@GetMapping(name = "exportCsv", value = "/export/csv")
	@ResponseBody
	public void exportCsv(GlobalSearch search, @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
			String[] datatablesColumns, HttpServletResponse response)
			throws JRException, IOException, ColumnBuilderException, ClassNotFoundException {
		export(search, pageable, datatablesColumns, response, new JasperReportsCsvExporter(), "owners_report.csv");
	}
	
	/**
	 * It delegates in the {@code export} method providing the necessary information
	 * to generate a XLS report.
	 * 
	 * @param search GlobalSearch that contains the filter provided by the Datatables component
	 * @param pageable Pageable that contains the Sort info provided by the Datatabes component
	 * @param datatablesColumns Columns displayed in the Datatables component
	 * @param response The HttpServletResponse
	 * @throws JRException 
	 * @throws IOException
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	@GetMapping(name = "exportXls", value = "/export/xls")
	@ResponseBody
	public void exportXls(GlobalSearch search, @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
			String[] datatablesColumns, HttpServletResponse response)
			throws JRException, IOException, ColumnBuilderException, ClassNotFoundException {
		export(search, pageable, datatablesColumns, response, new JasperReportsXlsExporter(), "owners_report.xls");
	}
	
	/**
	 * It delegates in the {@code export} method providing the necessary information
	 * to generate a PDF report.
	 *  
	 * @param search GlobalSearch that contains the filter provided by the Datatables component
	 * @param pageable Pageable that contains the Sort info provided by the Datatabes component
	 * @param datatablesColumns Columns displayed in the Datatables component
	 * @param response The HttpServletResponse
	 * @throws JRException 
	 * @throws IOException
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	@GetMapping(name = "exportPdf", value = "/export/pdf")
	@ResponseBody
	public void exportPdf(GlobalSearch search, @PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
			String[] datatablesColumns, HttpServletResponse response)
			throws JRException, IOException, ColumnBuilderException, ClassNotFoundException {
		export(search, pageable, datatablesColumns, response, new JasperReportsPdfExporter(), "owners_report.pdf");
	}

	/**
	 * Method that obtains the filtered and ordered records using the Datatables information and 
	 * export them to a new report file. (It ignores the current pagination).
	 * 
	 * To generate the report file it uses the "DynamicJasper" library
	 * (http://dynamicjasper.com). This library allows developers to generate reports dynamically
	 * without use an specific template to each entity.
	 * 
	 * To customize the appearance of ALL generated reports, you could customize the 
	 * "export_default.jrxml" template located in "src/main/resources/templates/reports/". However,
	 * if you want to customize the appearance of this specific report, you could create a new
	 * ".jrxml" file and provide it to the library replacing the {@code builder.setTemplateFile();} 
	 * operation used in this implementation.
	 * 
	 * @param search GlobalSearch that contains the filter provided by the Datatables component
	 * @param pageable Pageable that contains the Sort info provided by the Datatabes component
	 * @param datatablesColumns Columns displayed in the Datatables component
	 * @param response The HttpServletResponse
	 * @param exporter An specific JasperReportsExporter to be used during export process.
	 * @param fileName The final filename to use
	 * @throws JRException
	 * @throws IOException
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	private void export(GlobalSearch search, Pageable pageable, String[] datatablesColumns,
			HttpServletResponse response, JasperReportsExporter exporter, String fileName)
			throws JRException, IOException, ColumnBuilderException, ClassNotFoundException {

		// Obtain the filtered and ordered elements
		Page<Owner> owners = ownerService.findAll(search, pageable);
		
		// Prevent generation of reports with empty data
		if(owners == null || owners.getContent().isEmpty()){
			throw new RuntimeException("ERROR: Not available data to generate a report.");
		}
		
		// Creates a new ReportBuilder using DynamicJasper library
		FastReportBuilder builder = new FastReportBuilder();
		
		// IMPORTANT: By default, this application uses "export_default.jrxml"
		// to generate all reports. If you want to customize this specific report,
		// create a new ".jrxml" template and customize it. Take in account the DynamicJasper 
		// restrictions: 
		// http://dynamicjasper.com/2010/10/06/how-to-use-custom-jrxml-templates/)
		builder.setTemplateFile("templates/reports/export_default.jrxml");
		
		// The generated report will display the same columns as the Datatables component.
		// However, this is not mandatory. You could edit this code if you want to ignore 
		// the provided datatablesColumns
		if(datatablesColumns != null){
			for(String column : datatablesColumns){
				// Delegates in addColumnToReportBuilder to include each datatables column
				// to the report builder
				addColumnToReportBuilder(column, builder);
			}
		}
		
		// This property resizes the columns to use full width page.
		// Set false value if you want to use the specific width of each column.
		builder.setUseFullPageWidth(true);

		// Creates a new Jasper Reports Datasource using the obtained elements
		JRDataSource ds = new JRBeanCollectionDataSource(owners.getContent());
		
		// Generates the JasperReport
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(builder.build(), new ClassicLayoutManager(), ds);

		// Converts the JaspertReport element to a ByteArrayOutputStream and
		// write it into the response stream using the provided JasperReportExporter
		exporter.export(jp, fileName, response);
	}
	
	/**
	 * This method contains all the entity fields that are able to be displayed in a 
	 * report. The developer could add a new column to the report builder providing the 
	 * field name and the builder where the new field will be added as column.
	 * 
	 * @param columnName The field name to show as column
	 * @param builder The builder where the new field will be added as column.
	 * @throws ColumnBuilderException
	 * @throws ClassNotFoundException
	 */
	private void addColumnToReportBuilder(String columnName, FastReportBuilder builder) throws ColumnBuilderException, ClassNotFoundException{
		if (columnName.equals("id")) {
			builder.addColumn("Id", "id", Long.class.getName(), 50);
		} else if (columnName.equals("version")) {
			builder.addColumn("Version", "version", Integer.class.getName(), 100);
		} else if (columnName.equals("firstName")) {
			builder.addColumn("Name", "firstName", String.class.getName(), 100);
		} else if (columnName.equals("lastName")) {
			builder.addColumn("Last Name", "lastName", String.class.getName(), 100);
		} else if (columnName.equals("address")) {
			builder.addColumn("Address", "address", String.class.getName(), 100);
		} else if (columnName.equals("city")) {
			builder.addColumn("City", "city", String.class.getName(), 100);
		} else if (columnName.equals("telephone")) {
			builder.addColumn("Telephone", "telephone", String.class.getName(), 100);
		} else if (columnName.equals("homePage")) {
			builder.addColumn("Homepage", "homepage", String.class.getName(), 100);
		} else if (columnName.equals("email")) {
			builder.addColumn("Email", "email", String.class.getName(), 100);
		} else if (columnName.equals("birthDay")) {
			builder.addColumn("Birthday", "birthDay", Date.class.getName(), 100);
		}
	}
}
