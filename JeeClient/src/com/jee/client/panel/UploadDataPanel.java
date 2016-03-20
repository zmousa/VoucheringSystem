package com.jee.client.panel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.jee.client.JeeclientUI;
import com.jee.kernel.util.DbUtil;
import com.jee.model.voucher.Beneficiary;
import com.jee.model.voucher.Category;
import com.jee.model.voucher.Distribution;
import com.jee.model.voucher.DistributionDetail;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class UploadDataPanel extends MainPanel {
	private static final long serialVersionUID = 1L;
	private File tempFile;
	private Table voucherTable;
	private Table participationTable;
	private Upload uploadVouchers;
	private Upload uploadParticipation;
	private String[] columnHeaders;
	
	private static Logger log = Logger.getLogger(UploadDataPanel.class);	
	
	public UploadDataPanel(){
		super();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initUi() {
		initVoucherUploadComponent();
		initParticipationUploadComponent();
		
	    voucherTable = new Table();
	    voucherTable.setVisible(false);
	    voucherTable.setHeight("20%");
	    
	    participationTable = new Table();
	    participationTable.setVisible(false);
	    participationTable.setHeight("20%");

	    this.setSizeFull();
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		Label vouchersUploadLbl = new Label("Please choose *.CSV voucher file...");
		Button importVouchers = new Button("Import Vouchers");
		importVouchers.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -1351361261638051288L;

			@Override
			public void buttonClick(ClickEvent event) {
				importVouchers();
			}
		});
		
		Button importParticpations = new Button("Import Particpations");
		importParticpations.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 3235578163863661118L;

			@Override
			public void buttonClick(ClickEvent event) {
				importParticipations();
			}
		});
		
		
		layout.addComponent(vouchersUploadLbl);
	    layout.addComponent(voucherTable);
	    layout.addComponent(uploadVouchers);
	    layout.addComponent(importVouchers);
	    
	    layout.addComponent(new Label("<hr/>", Label.CONTENT_XHTML));
	    
	    Label participationUploadLbl = new Label("Please choose *.CSV Participations file...");
		layout.addComponent(participationUploadLbl);
	    layout.addComponent(participationTable);
	    layout.addComponent(uploadParticipation);
	    layout.addComponent(importParticpations);
	    
	    layout.setSpacing(true);
	    layout.setMargin(true);
	}
	
	@SuppressWarnings("deprecation")
	private void initVoucherUploadComponent(){
		uploadVouchers = new Upload("Upload CSV File", new Upload.Receiver() {
	    	private static final long serialVersionUID = -2708335256341828682L;

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
		        try {
		          tempFile = File.createTempFile("temp", ".csv");
		          return new FileOutputStream(tempFile);
		        } catch (IOException e) {
		          e.printStackTrace();
		          return null;
		        }
			}
	    });
		uploadVouchers.setButtonCaption("Vouchers Upload");
	    uploadVouchers.addListener(new Upload.FinishedListener() {
			private static final long serialVersionUID = -3013834011132878777L;

			@Override
			public void uploadFinished(Upload.FinishedEvent finishedEvent) {
				try {
	        		FileReader reader = new FileReader(tempFile);
	        		IndexedContainer indexedContainer = buildContainerFromCSV(reader);
	        		reader.close();
	        		tempFile.delete();
	
	        		voucherTable.setCaption(finishedEvent.getFilename());
	        		voucherTable.setContainerDataSource(indexedContainer);
	        		voucherTable.setVisible(true);
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
			}
	    });
	}
	
	@SuppressWarnings("deprecation")
	private void initParticipationUploadComponent(){
		uploadParticipation = new Upload("Upload CSV File", new Upload.Receiver() {
	    	private static final long serialVersionUID = -2708335256341828682L;

			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
		        try {
		          tempFile = File.createTempFile("temp", ".csv");
		          return new FileOutputStream(tempFile);
		        } catch (IOException e) {
		          e.printStackTrace();
		          return null;
		        }
			}
	    });
		uploadParticipation.setButtonCaption("Participations Upload");
		uploadParticipation.addListener(new Upload.FinishedListener() {
			private static final long serialVersionUID = -3013834011132878777L;

			@Override
			public void uploadFinished(Upload.FinishedEvent finishedEvent) {
				try {
	        		FileReader reader = new FileReader(tempFile);
	        		IndexedContainer indexedContainer = buildContainerFromCSV(reader);
	        		reader.close();
	        		tempFile.delete();
	
	        		participationTable.setCaption(finishedEvent.getFilename());
	        		participationTable.setContainerDataSource(indexedContainer);
	        		participationTable.setVisible(true);
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
			}
	    });
	}
	
	@Override
	public void onLoad() {
		
	}
	
	private IndexedContainer buildContainerFromCSV(Reader reader) throws IOException {
	    IndexedContainer container = new IndexedContainer();
	    @SuppressWarnings("resource")
		CSVReader csvReader = new CSVReader(reader);
	    columnHeaders = null;
	    String[] record;
	    while ((record = csvReader.readNext()) != null) {
	    	if (columnHeaders == null) {
	    		columnHeaders = record;
	    		addItemProperties(container, columnHeaders);
	    	} else {
	    		addItem(container, columnHeaders, record);
	    	}
	    }
	    return container;
	}

	private void addItemProperties(IndexedContainer container, String[] columnHeaders) {
	    for (String propertyName : columnHeaders) {
	    	container.addContainerProperty(propertyName, String.class, null);
	    }
	}

	@SuppressWarnings("unchecked")
	private void addItem(IndexedContainer container, String[] propertyIds, String[] fields) {
	    if (propertyIds.length != fields.length) {
	    	throw new IllegalArgumentException("Different number of columns to fields in the record");
	    }
	    Object itemId = container.addItem();
	    Item item = container.getItem(itemId);
	    for (int i = 0; i < fields.length; i++) {
	    	String propertyId = propertyIds[i];
	    	String field = fields[i];
	    	item.getItemProperty(propertyId).setValue(field);
	    }
	}

	private void importVouchers(){
		Category categoryObj = null;
		String category = "";
		int firstId, count, value;
		
		for (Iterator<?> i = voucherTable.getItemIds().iterator(); i.hasNext();){
			int iid = (Integer)i.next();
			Item item = voucherTable.getItem(iid);
			try {
				category = item.getItemProperty(CSVHeaders.VOUCHER_CATEGORY).getValue().toString();
				categoryObj = JeeclientUI.getCurrent().getUserAdminManager().getCategoryByIdOrCreateOne(category);
				firstId = Integer.parseInt(item.getItemProperty(CSVHeaders.VOUCHER_FIRST_NO).getValue().toString());
				count = Integer.parseInt(item.getItemProperty(CSVHeaders.VOUCHER_COUNT).getValue().toString());
				value = Integer.parseInt(item.getItemProperty(CSVHeaders.VOUCHER_VALUE).getValue().toString());
				
				JeeclientUI.getCurrent().getUserAdminManager().importVouchers(categoryObj, firstId, count, value);
			} catch (Exception ex){
				log.error(ex.getMessage());
			}
		}
	}
	
	private void importParticipations(){
		Category categoryObj = null;
		DistributionDetail distributionDetail = null;
		String id, name, gender;
		int age;
		
		for (Iterator<?> i = participationTable.getItemIds().iterator(); i.hasNext();){
			int iid = (Integer)i.next();
			Item item = participationTable.getItem(iid);
			try {
				id = item.getItemProperty(CSVHeaders.PARTICIPATE_ID).getValue().toString();
				name = item.getItemProperty(CSVHeaders.PARTICIPATE_NAME).getValue().toString();
				gender = item.getItemProperty(CSVHeaders.PARTICIPATE_GENDER).getValue().toString();
				age = Integer.parseInt(item.getItemProperty(CSVHeaders.PARTICIPATE_AGE).getValue().toString());
				
				Beneficiary beneficiary = JeeclientUI.getCurrent().getUserAdminManager().createBeneficiary(id, name, gender, age);
				
				List<DistributionDetail> distributionDetails = new ArrayList<DistributionDetail>();
				for (int index = 4; index < columnHeaders.length; index++){
					
					distributionDetail = new DistributionDetail();
					
					if (!item.getItemProperty(columnHeaders[index]).getValue().toString().isEmpty()) {
						categoryObj = JeeclientUI.getCurrent().getUserAdminManager().getCategoryByIdOrCreateOne(columnHeaders[index]);
						distributionDetail.setId(DbUtil.generateGUID());
						distributionDetail.setCategory(categoryObj);
						distributionDetail.setAmount(Integer.parseInt(item.getItemProperty(columnHeaders[index]).getValue().toString()));
						distributionDetails.add(distributionDetail);
					}
				}
				
				Distribution distribution = JeeclientUI.getCurrent().getUserAdminManager().createDistribution(DbUtil.generateGUID(), distributionDetails);
				JeeclientUI.getCurrent().getUserAdminManager().participate(beneficiary, distribution);
				
			} catch (Exception ex){
				log.error(ex.getMessage());
			}
		}
	}
	
}
