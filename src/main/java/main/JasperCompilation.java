package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JasperCompilation {

	public static void main(String[] args) {
		new JasperCompilation().a();
	}

	private void a() {
//		try {
//			JasperCompileManager
//					.compileReportToFile("/home/michael/workspace/SerwisManager/sprzedaz.jrxml");
//		} catch (JRException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		String sourceFileName = "/home/michael/workspace/SerwisManager/sprzedaz.jasper";
		DataBeanList DataBeanList = new DataBeanList();
		ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();

		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
		Map parameters = new HashMap();
		try {
			String fillReportToFile = JasperFillManager.fillReportToFile(sourceFileName, parameters,
					beanColDataSource);
			JasperExportManager.exportReportToPdfFile(fillReportToFile,
					"/home/michael/workspace/SerwisManager/sprzedaz.pdf");
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

}
