package com.jee.client.useradmin;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.tepi.filtertable.FilterDecorator;
import org.tepi.filtertable.FilterTable;
import org.tepi.filtertable.numberfilter.NumberFilterPopupConfig;

import com.jee.client.JeeclientUI;
import com.jee.client.voucher.BeneficiaryPanel;
import com.jee.client.voucher.CategoryPanel;
import com.jee.client.voucher.DistributionPanel;
import com.jee.client.voucher.VoucherPanel;
import com.jee.model.common.ToolBarEventEnum;
import com.jee.model.voucher.Beneficiary;
import com.jee.model.voucher.Category;
import com.jee.model.voucher.Distribution;
import com.jee.model.voucher.Voucher;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractSelect.ItemDescriptionGenerator;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

public class LeftSideComponent extends CustomComponent implements ValueChangeListener{
	private static final long serialVersionUID = 307244976478610176L;
	private FilterTable content;
	private static final String PROPERTY_NAME = "Name";
    private static final String PROPERTY_ID = "ID";
    private ToolBarEventEnum eventType;
    private static Logger log = Logger.getLogger(LeftSideComponent.class);
	
	public LeftSideComponent()
    {
        init();
    }

	private void init()
    {
        content = new FilterTable();
        content.setSelectable(true);
        content.setImmediate(true);
        content.setSizeFull();
        content.setFilterBarVisible(true);
        content.setFilterDecorator(new FilterDecorator()
        {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isTextFilterImmediate(Object propertyId)
            {
                return true;
            }

            @Override
            public int getTextChangeTimeout(Object propertyId)
            {
                return 500;
            }

            @Override
            public String getEnumFilterDisplayName(Object propertyId, Object value)
            {
                return null;
            }

            @Override
            public Resource getEnumFilterIcon(Object propertyId, Object value)
            {
                return null;
            }

            @Override
            public String getBooleanFilterDisplayName(Object propertyId, boolean value)
            {
                return null;
            }

            @Override
            public Resource getBooleanFilterIcon(Object propertyId, boolean value)
            {
                return null;
            }

            @Override
            public String getFromCaption()
            {
                return null;
            }

            @Override
            public String getToCaption()
            {
                return null;
            }

            @Override
            public String getSetCaption()
            {
                return null;
            }

            @Override
            public String getClearCaption()
            {
                return null;
            }

            @Override
            public Resolution getDateFieldResolution(Object propertyId)
            {
                return null;
            }

            @Override
            public String getDateFormatPattern(Object propertyId)
            {
                return null;
            }

            @Override
            public Locale getLocale()
            {
                return null;
            }

            @Override
            public String getAllItemsVisibleString()
            {
                return null;
            }

            @Override
            public NumberFilterPopupConfig getNumberFilterPopupConfig()
            {
                return null;
            }

            @Override
            public boolean usePopupForNumericProperty(Object propertyId)
            {
                return false;
            }
        });

        initProperties();
        content.setVisibleColumns(new Object[] { PROPERTY_NAME });
        content.setColumnHeaders(new String[] { PROPERTY_NAME });
        content.setItemDescriptionGenerator(new ItemDescriptionGenerator()
        {
			private static final long serialVersionUID = -5408049718530867034L;

			@Override
            public String generateDescription(Component source, Object itemId, Object propertyId)
            {
                return (String) ((FilterTable) source).getItem(itemId).getItemProperty(PROPERTY_NAME).getValue();
            }
        });
        content.addValueChangeListener(this);
        this.setSizeFull();
        setCompositionRoot(content);
    }
	
	@Override
	public void valueChange(ValueChangeEvent event) {
		try
        {
            if (event.getProperty().getValue() != null)
            {
                RightSideComponent detailsPanel = null;
                final String elementId = event.getProperty().getValue().toString();
                switch (eventType)
                {
                    case Voucher:
                        final Voucher selectedVoucher = JeeclientUI.getCurrent().getUserAdminManager().getVoucherByCode(elementId);
                        if (selectedVoucher != null)
                        {
                            detailsPanel = new VoucherPanel(selectedVoucher);
                        }
                        break;
                    case Beneficiary:
                        final Beneficiary selectedBene = JeeclientUI.getCurrent().getUserAdminManager().getBeneficiaryById(elementId);
                        if (selectedBene != null)
                        {
                            detailsPanel = new BeneficiaryPanel(selectedBene);
                        }
                        break;
                    case Category:
                        final Category selectedCategory = JeeclientUI.getCurrent().getUserAdminManager().getCategoryById(elementId);
                        if (selectedCategory != null)
                        {
                            detailsPanel = new CategoryPanel(selectedCategory);
                        }
                        break;
                    case Distribution:
                        final Distribution selectedDistribution = JeeclientUI.getCurrent().getUserAdminManager().getDistributionById(elementId);
                        if (selectedDistribution != null)
                        {
                            detailsPanel = new DistributionPanel(selectedDistribution);
                        }
                        break;
                    default:
                        break;
                }
                if (detailsPanel != null)
                {
                    ((VoucheringUnitPanel) JeeclientUI.getCurrent().getSelectedPanel()).getSelectedTab().setRightSide(detailsPanel);
                }
            }
        }
        catch (Exception e)
        {
        	log.error(e.getMessage(), e);
        }
	}

	private void initProperties()
    {
        final Container container = new IndexedContainer();
        container.addContainerProperty(PROPERTY_ID, String.class, "");
        container.addContainerProperty(PROPERTY_NAME, String.class, "");
        content.setContainerDataSource(container);
    }
	
	@SuppressWarnings("unchecked")
	public void fillTable(ToolBarEventEnum eventType, final Object targetObject)
    {
		try {
	        content.resetFilters();
	        content.removeAllItems();
	        String itemToSelect = null;
	        switch (eventType)
	        {
	            case Voucher:
	                final List<Voucher> voucherList = JeeclientUI.getCurrent().getUserAdminManager().getAllVouchers();
	                if (voucherList != null && !voucherList.isEmpty())
	                {
	                    for (Voucher voucher : voucherList)
	                    {
	                    	content.addItem(voucher.getCode());
	                    	content.getContainerProperty(voucher.getCode(), PROPERTY_ID).setValue(voucher.getCode());
	                    	content.getContainerProperty(voucher.getCode(), PROPERTY_NAME).setValue(voucher.getCode());
                    		itemToSelect = voucherList.get(0).getCode();
	                    }
	                }
	                break;
	            case Beneficiary:
	                final List<Beneficiary> beneList = JeeclientUI.getCurrent().getUserAdminManager().getAllBeneficiarys();
	                if (beneList != null && !beneList.isEmpty())
	                {
	                    for (Beneficiary beneficiary : beneList)
	                    {
	                    	content.addItem(beneficiary.getId());
	                    	content.getContainerProperty(beneficiary.getId(), PROPERTY_ID).setValue(beneficiary.getId());
	                    	content.getContainerProperty(beneficiary.getId(), PROPERTY_NAME).setValue(beneficiary.getName());
                    		itemToSelect = beneList.get(0).getId();
	                    }
	                }
	                break;
	            case Category:
	                final List<Category> categoryList = JeeclientUI.getCurrent().getUserAdminManager().getAllCategories();
	                if (categoryList != null && !categoryList.isEmpty())
	                {
	                    for (Category category : categoryList)
	                    {
	                    	content.addItem(category.getId());
	                    	content.getContainerProperty(category.getId(), PROPERTY_ID).setValue(category.getId());
	                    	content.getContainerProperty(category.getId(), PROPERTY_NAME).setValue(category.getId());
                    		itemToSelect = categoryList.get(0).getId();
	                    }
	                }
	                break;
	            case Distribution:
	                final List<Distribution> distributions = JeeclientUI.getCurrent().getUserAdminManager().getAllDistribution();
	                if (distributions != null && !distributions.isEmpty())
	                {
	                    for (Distribution distribution : distributions)
	                    {
	                    	content.addItem(distribution.getId());
	                    	content.getContainerProperty(distribution.getId(), PROPERTY_ID).setValue(distribution.getId());
	                    	content.getContainerProperty(distribution.getId(), PROPERTY_NAME).setValue(distribution.getId());
                    		itemToSelect = distributions.get(0).getId();
	                    }
	                }
	                break;  
	            default:
	                break;
	        }
	        content.select(itemToSelect);
		}
        catch (Exception e)
        {
        	log.error(e.getMessage(), e);
        }
    }

	public ToolBarEventEnum getEventType() {
		return eventType;
	}

	public void setEventType(ToolBarEventEnum eventType) {
		this.eventType = eventType;
	}
	
	public void refreshTree(Object target){
		fillTable(this.eventType, target);		
	}
}
