package com.jee.clienttest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jee.api.dao.Filter;
import com.jee.api.dao.HelperDao;
import com.jee.kernel.voucher.VoucheringManagerImpl;
import com.jee.model.exception.AgeRangeException;
import com.jee.model.exception.GenderException;
import com.jee.model.voucher.Beneficiary;
import com.jee.model.voucher.Category;
import com.jee.model.voucher.Distribution;
import com.jee.model.voucher.DistributionDetail;

public class JeeclientTest {
	
	private VoucheringManagerImpl userAdminManager;
    
    @Before
    public void setUp() throws Exception {
    	userAdminManager = new VoucheringManagerImpl();
    	userAdminManager.setHelperDao(helperDaoMock);
    }

    @Test
    public void testImportVouchersNotFailed() throws Exception {
    	Category category = new Category("A", "description");
    	boolean result = userAdminManager.importVouchers(category, 1000, 100, 1000);
    	assertEquals(true, result);
    }
    
    @Test
    public void testCreateBeneficiaryNotFailed() throws Exception {
    	Beneficiary beneficiary = userAdminManager.createBeneficiary("123", "Jo Mem", "M", 19);
    	Assert.assertNotNull(beneficiary);
    }
    
    @Test (expected=GenderException.class)
    public void testCreateBeneficiaryThrowGenderException() throws AgeRangeException, GenderException {
    	userAdminManager.createBeneficiary("123", "Jo Mem", "O", 19);
    }
    
    @Test (expected=AgeRangeException.class)
    public void testCreateBeneficiaryThrowAgeException() throws AgeRangeException, GenderException {
    	userAdminManager.createBeneficiary("123", "Jo Mem", "M", 133);
    }
    
    @Test
    public void testCreateDistributionNotFailed() throws Exception {
    	Category category = new Category("A", "description");
    	List<DistributionDetail> details = new ArrayList<DistributionDetail>();
    	details.add(new DistributionDetail("1", category, 1000));
    	Distribution distribution = userAdminManager.createDistribution("1", details);
    	Assert.assertNotNull(distribution);
    }
    
    @Test
    public void testParticipationNoExceptionRaised() throws Exception {
    	Beneficiary beneficiary = new Beneficiary("1", "A", "M", 30);
    	Distribution distribution = new Distribution("1", new Date());
    	userAdminManager.participate(beneficiary, distribution);
    }
    
	HelperDao helperDaoMock = new HelperDao() {
		
		@Override
		public Object update(Object o) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<?> search(String querySql) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public Object save(Object o) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void executeUpdate(String querSql) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public Filter entity(Class<?> clazz) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void delete(Object o) throws Exception {
			// TODO Auto-generated method stub
			
		}
	};
}