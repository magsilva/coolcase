package coolcase.casetool.action;

import java.awt.event.ActionEvent;
import java.awt.print.PrinterJob;

import javax.swing.AbstractAction;

public class PrintDiagramAction extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(Sistema.drawingPanel);
		if (job.printDialog()) {
			try {
				job.print();
			} catch (Exception e) {}
		}
	}
}