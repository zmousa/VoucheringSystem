package com.jee.kernel.voucher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.jee.api.VoucheringManager;
import com.jee.api.dao.HelperDao;
import com.jee.kernel.util.DbUtil;
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
 * The <code>VoucheringManagerImpl</code> class represents the implementation of the main web service.
 */
@Transactional
public class VoucheringManagerImpl implements VoucheringManager {

	private static VoucheringManagerImpl instance;
	private HelperDao helperDao;

	private static Logger log = Logger.getLogger(VoucheringManagerImpl.class);
	public VoucheringManagerImpl(){
		instance = this;
	}

	public static VoucheringManagerImpl getInstance(){
		if(instance ==null)
			return new VoucheringManagerImpl();
		else
			return instance;
	}

	@Override
	public JeeUser getJeeUserById(String id){
		JeeUser jeeUser = null;
		try {
			jeeUser = (JeeUser) helperDao.entity(JeeUser.class).filterE("id", id).single();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jeeUser;
	}

	@Override
	public JeeUser getUserByName(String userName) {

		JeeUser jeeUser = null;
		try {
			jeeUser = (JeeUser) helperDao.entity(JeeUser.class).filterE("userName", userName).single();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jeeUser;
	}

	@Override
	public JeeUser login(String loginId, String password) {
		JeeUser jeeUser = null;
		try {
			jeeUser = (JeeUser) helperDao.entity(JeeUser.class).filterE("userName", loginId).filterE("password", password).filterE("enabled", 1).single();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(jeeUser!=null){
			return jeeUser;
		}
		return null;
	}

	public HelperDao getHelperDao() {
		return helperDao;
	}

	public void setHelperDao(HelperDao helperDao) {
		this.helperDao = helperDao;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<JeeUser> getAllJeeUsers(){
		List<JeeUser> jeeUsers = null;
		try {
			jeeUsers = (List<JeeUser>) helperDao.entity(JeeUser.class).list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return jeeUsers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JeeUser> getUsersList(String username,boolean isEnabled, boolean isAdmin){


		String queryStr = "from JeeUser r where 1=1 ";
		if(username!=null && !username.equals("")) queryStr+= "and userName like'%" + username+"%'";
		if(isEnabled) queryStr+=" and enabled=1";
		if(isAdmin) queryStr+=" and isAdmin=1";

		try {
			return (List<JeeUser>) helperDao.search(queryStr);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * get All Vouchers
	 * 
     * @return List<Voucher>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Voucher> getAllVouchers() {
		List<Voucher> vouchers = null;
		try {
			vouchers = (List<Voucher>) helperDao.entity(Voucher.class).list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return vouchers;
	}

	/**
	 * create get Voucher By Code
	 * 
	 * @param  code
     *         primary key
     * 
     * @return Voucher
	 */
	@Override
	public Voucher getVoucherByCode(String code) {
		Voucher voucher = null;
		try {
			voucher = (Voucher) helperDao.entity(Voucher.class).filterE("code", code).single();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return voucher;
	}
	
	/**
	 * get Voucher Value By Category Id
	 * 
	 * @param  category
     *         category of voucher
     * 
     * @return value of voucher
	 */
	private Integer getVoucherValueByCategoryId(Category category) {
		try {
			@SuppressWarnings("unchecked")
			List<Voucher> vouchers = (List<Voucher>) helperDao.entity(Voucher.class).filterE("category", category).list();
			if (vouchers != null && vouchers.size() > 0)
				return vouchers.get(0).getValue();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return -1;
	}

	/**
	 * create get All Beneficiaries
	 * 
     * @return List<Beneficiary>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Beneficiary> getAllBeneficiarys() {
		List<Beneficiary> beneficiaries = null;
		try {
			beneficiaries = (List<Beneficiary>) helperDao.entity(Beneficiary.class).list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return beneficiaries;
	}

	/**
	 * create get Beneficiary By Id
	 * 
	 * @param  id
     *         primary key
     * 
     * @return Beneficiary
	 */
	@Override
	public Beneficiary getBeneficiaryById(String id) {
		Beneficiary beneficiary = null;
		try {
			beneficiary = (Beneficiary) helperDao.entity(Beneficiary.class).filterE("id", id).single();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return beneficiary;
	}

	/**
	 * import Vouchers
	 * 
	 * @param  category
     *         Category of Vouchers
     * 
     * @param  firstId
     *        firstId of Vouchers sequence
     *
     * @param  count
     *        count of Vouchers
     * 
     * @param  	value    
     * 			value of Vouchers
     *        
     * @return boolean true if succeeded
	 */
	@Override
	public boolean importVouchers(Category category, int firstId, int count, int value) {
		Voucher voucher = null;
		String code = "";
		boolean resultWithNoErrors = true;
		for (int index = firstId; index < firstId + count; index++) {
			try {
				voucher = new Voucher();
				code = category.getId() + String.format("%06d", index);
				voucher.setCode(code);
				voucher.setCategory(category);
				voucher.setValue(value);
				helperDao.save(voucher);
			} catch (Exception e) {
				log.error(e.getMessage() , e);
				resultWithNoErrors = false;
			}
		}
		
		return resultWithNoErrors;
	}

	/**
	 * create Beneficiary
	 * 
	 * @param  id
     *         primary key
     * 
     * @param  name
     *        Name of Beneficiary
     *
     * @param  gender
     *        gender of Beneficiary
     * 
     * @param  	age    
     * 			age of Beneficiary
     *        
     * @return Beneficiary
	 */
	@Override
	public Beneficiary createBeneficiary(String id, String name, String gender, int age) throws AgeRangeException, GenderException{
		Beneficiary beneficiary = null;
		try {
			beneficiary = new Beneficiary(id, name, gender, age);
			helperDao.save(beneficiary);
		} catch (AgeRangeException a) {
			throw a;
		} catch (GenderException g) {
			throw g;
		} catch (Exception e) {
			log.error(e.getMessage() , e);
			return null;
		}
		
		return beneficiary;
	}

	/**
	 * create Distribution
	 * 
	 * @param  id
     *         primary key
     * 
     * @param  details
     *         list of DistributionDetail
     *
     * @return Distribution
	 */
	@Override
	public Distribution createDistribution(String id, List<DistributionDetail> details) {
		Distribution distribution = null;
		try {
			distribution = new Distribution(id, new Date());
			helperDao.save(distribution);
			
			for (DistributionDetail detail : details){
				detail.setDistribution(distribution);
				helperDao.save(detail);
			}
		} catch (Exception e) {
			log.error(e.getMessage() , e);
			return null;
		}
		
		return distribution;
	}

	/**
	 * participate beneficiary to distribution
	 * 
	 * @param  beneficiary
     *         instance of beneficiary
     * 
     * @param  participation
     *         instance of participation
     *
     * @return List<ParticipationDetail>
	 */
	@Override
	public List<ParticipationDetail> participate(Beneficiary beneficiary, Distribution distribution) {
		Participation participation = null; 
		try {
			participation = new Participation();
			participation.setId(DbUtil.generateGUID());
			participation.setBeneficiary(beneficiary);
			participation.setDistribution(distribution);
			helperDao.save(participation);
			
			return getParticipationDetails(participation);
		} catch (Exception e) {
			log.error(e.getMessage() , e);
			return null;
		}
	}
	
	/**
	 * Get Details of participation
	 * 
	 * @param  participation
     *         instance of participation
     *
     * @return List<ParticipationDetail>
	 */
	@Override
	public List<ParticipationDetail> getParticipationDetails(Participation participation){
		List<ParticipationDetail> participationDetails = new ArrayList<ParticipationDetail>();
		ParticipationDetail participationDetail = null;
		if (participation.getDistribution() != null && participation.getDistribution().getDistributionDetails() != null){
			for (DistributionDetail distributionDetail : participation.getDistribution().getDistributionDetails()){
				// Get voucher's category value.
				int voucherValue = getVoucherValueByCategoryId(distributionDetail.getCategory());
				// Calculate Number of Vouchers
				int numberOfVouchers = distributionDetail.getAmount() / voucherValue;
				participationDetail = new ParticipationDetail(participation, distributionDetail.getCategory(), numberOfVouchers);
				participationDetails.add(participationDetail);
			}
		}
		return participationDetails;
	}

	/**
	 * Get all Categories
	 * 
     * @return List<Category>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = null;
		try {
			categories = (List<Category>) helperDao.entity(Category.class).list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return categories;
	}

	/**
	 * Get Category By Id
	 * 
	 * @param  id
     *         The primary key for the Category
     *
     * @return Category instance
	 */
	@Override
	public Category getCategoryById(String id) {
		Category category = null;
		try {
			category = (Category) helperDao.entity(Category.class).filterE("id", id).single();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return category;
	}
	
	/**
	 * Get Category By Id or create new one
	 * 
	 * @param  id
     *         The primary key for the Category
     *
     * @return Category instance
	 */
	@Override
	public Category getCategoryByIdOrCreateOne(String id) {
		Category category = getCategoryById(id);
		if (category == null) {
			try {
				category = new Category(id, id);
				helperDao.save(category);
			} catch (Exception e) {
				log.error(e.getMessage() , e);
				return null;
			}
		}
		
		return category;
	}

	/**
	 * get All Distributions
	 * 
     * @return List<Distribution>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Distribution> getAllDistribution() {
		List<Distribution> distributions = null;
		try {
			distributions = (List<Distribution>) helperDao.entity(Distribution.class).list();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return distributions;
	}

	/**
	 * Get Distribution By Id
	 * 
	 * @param  id
     *         The primary key for the Distribution
     *
     * @return Distribution instance
	 */
	@Override
	public Distribution getDistributionById(String id) {
		Distribution distribution = null;
		try {
			distribution = (Distribution) helperDao.entity(Distribution.class).filterE("id", id).single();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return distribution;
	}
}
