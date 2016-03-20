package com.jee.api;

import java.util.List;

import com.jee.model.exception.AgeRangeException;
import com.jee.model.exception.GenderException;
import com.jee.model.userAdmin.JeeUser;
import com.jee.model.voucher.Beneficiary;
import com.jee.model.voucher.Category;
import com.jee.model.voucher.Distribution;
import com.jee.model.voucher.DistributionDetail;
import com.jee.model.voucher.Participation;
import com.jee.model.voucher.ParticipationDetail;
import com.jee.model.voucher.Voucher;

/**
 * The <code>VoucheringManager</code> interface represents the API layer of the main web service the connect server to client.
 */
public interface VoucheringManager {
	public JeeUser login(String username, String password);
	
	/* Users */
	public List<JeeUser> getAllJeeUsers();
	public JeeUser getJeeUserById(String id);
	public JeeUser getUserByName(String userName);
	public List<JeeUser> getUsersList(String username, boolean isEnabled, boolean isAdmin);
	
	/* Vouchers */
	public List<Voucher> getAllVouchers();
	public Voucher getVoucherByCode(String code);
	
	public List<Category> getAllCategories();
	public Category getCategoryById(String id);
	public Category getCategoryByIdOrCreateOne(String id);
	
	public List<Beneficiary> getAllBeneficiarys();
	public Beneficiary getBeneficiaryById(String id);
	
	public List<Distribution> getAllDistribution();
	public Distribution getDistributionById(String id);
	
	public boolean importVouchers(Category category, int firstId, int count, int value);
	public Beneficiary createBeneficiary(String id, String name, String gender, int age) throws AgeRangeException, GenderException;
	public Distribution createDistribution(String id, List<DistributionDetail> details);
	public List<ParticipationDetail> participate(Beneficiary beneficiary, Distribution distribution);
	public List<ParticipationDetail> getParticipationDetails(Participation participation);
}
