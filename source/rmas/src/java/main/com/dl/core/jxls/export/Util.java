package com.dl.core.jxls.export;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 导出Util辅助类
 * fuck！
 * @author Lucas
 * @date 2014-3-21 下午4:10:11
 */
public class Util implements InitializingBean {
	
	private static final String BLANK_VALUE = "";
	private static final String CHECK_VALUE = "√";
	private static final String NON_CHECK_VALUE = "　";
	private static final String SEX_MALE = "男";
	private static final String SEX_FEMALE = "女";
	protected Map<String, String> deptGroup = new HashMap<String, String>();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Util() {
	}
	
	public static boolean validObjectIsNull(Object value) {
		if (value == null
				|| StringUtils.isBlank(value.toString())
				|| StringUtils.equals("0", value.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean validObjectIsNotNull(Object value) {
		return !validObjectIsNull(value);
	}

	public void initDeptGroup() {
		String sql = "select T_NAME,T_CODE,T_ORDER from DATA_DICTIONARY d"
				+ ",const_type c where d.ct_id=c.id and CT_CODE='ZCDJBM' order by T_ORDER";
		List<Map<String, Object>> _group = jdbcTemplate.queryForList(sql);
		if (_group != null && _group.size() > 0) {
			for (Map<String, Object> _map : _group) {
				deptGroup.put(_map.get("T_CODE").toString(), _map.get("T_NAME").toString());
			}
		}
	}

	public String formatString(int no) {
		String strNo = String.valueOf(no);
		while (strNo.length() <= 6) {
			strNo = "0" + strNo;
		}
		return strNo;
	}

	public String getDeptName(Object deptCode) {
		String code = deptCode == null ? BLANK_VALUE : deptCode.toString().trim();
		if (deptGroup.size() == 0) initDeptGroup();
			
		return deptGroup.get(code);
	}

	public String switchValue(Object value, String code) {
		String strVal = value == null ? BLANK_VALUE : value.toString().trim();
		String[] vals = strVal.split(",");
		for (String str : vals){ if(code.equals(str)) return CHECK_VALUE; }
		return NON_CHECK_VALUE;
	}
	
	/**
	 * 根据关联字段值决定默认选值
	 * @param value
	 * @param code
	 * @param otherValue
	 * @return
	 */
	public String switchValue(Object value, String code, Object otherValue) {
		if (otherValue == null 
				|| StringUtils.isBlank(otherValue.toString())
				|| new BigDecimal(otherValue.toString()).compareTo(BigDecimal.ZERO) == 0) return BLANK_VALUE;
		else return CHECK_VALUE;
	}
	
	public String switchSexValue(Object value) {
		String strVal = value == null ? BLANK_VALUE : value.toString().trim();
		if ("1".equals(strVal)) return SEX_MALE;
		else if ("0".equals(strVal)) return SEX_FEMALE;
		return NON_CHECK_VALUE;
	}
	
	/**
	 * 根据其他字段来决定是否可以选择性别项,如果关联项有值,但是当前项空值,设置'男'为默认项
	 * @param value 当前项值
	 * @param otherValue 关联项值
	 * @return
	 */
    public String switchSexValue(Object value, Object otherValue){
    	if (otherValue == null || StringUtils.isBlank(otherValue.toString())) return BLANK_VALUE;
    	if (value == null || StringUtils.isBlank(value.toString())) return SEX_MALE;
    	return switchSexValue(value);
    }
	
	public String formatInteger(Object value){
		if (value == null) return BLANK_VALUE;
		String val = value.toString().trim();
		BigDecimal bd = new BigDecimal(val).setScale(0, BigDecimal.ROUND_HALF_UP);
		
		return bd.compareTo(BigDecimal.ZERO) == 0 
				? BLANK_VALUE : String.valueOf(bd);
	}
	
	public String formatDecimal(Object value){
		if (value == null) return BLANK_VALUE;
		String val = value.toString().trim();
		BigDecimal bd = new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		return String.valueOf(bd);
	}
	
	public String replaceZero(Object value){
		if (value == null) return BLANK_VALUE;
		String val = value.toString().trim();
		BigDecimal bd = new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		if (bd.compareTo(BigDecimal.ZERO) > 0) return String.valueOf(bd);
		else return BLANK_VALUE;
	}
	
	/**
	 * 设置默认值，处理特殊数据
	 * @param value
	 * @param code
	 * @return
	 */
    public String setDefaultValue(Object value, String code){
    	if (value == null || StringUtils.isBlank(value.toString())) return CHECK_VALUE;
    	return code.equals(value.toString()) ? CHECK_VALUE : NON_CHECK_VALUE;
    }
    
    /**
     * 为固定电话添加上海区号 021-xxxxxxxx
     * @param phone
     * @return
     */
    public String setPhoneHead(Object phone){
    	if (phone == null || StringUtils.isBlank(phone.toString())) return BLANK_VALUE;
    	
    	if (phone.toString().indexOf("-") == -1) return "021-" + phone.toString();
    	
    	return phone.toString();
    }
    
    /**
     * 设置农产品认证
     * @param value
     * @param cert
     * @return
     */
    public String setCert(Object value, Object cert){
    	if (cert == null || StringUtils.isBlank(cert.toString())) return BLANK_VALUE;
    	else if (value == null || StringUtils.isBlank(value.toString())) {
    		return String.valueOf(cert.toString().split(";").length);
    	}
    	return value.toString();
    }
    
    /**
     * 为已按《登记条例》登记的合作社设置默认的登记注册号
     * @param value
     * @param isRegist
     * @return
     */
    public String setDefaultTaxNumber(String value, Object isRegist) {
    	if ("1".equals(isRegist)
    			&& StringUtils.isBlank(value)) return "000000000000000";
    	else if (StringUtils.isNotBlank(value)
    			&& value.length() == 15) return value;
    	else if (StringUtils.isNotBlank(value)
    			&& value.length() > 15) return value.substring(0, 16);
    	return BLANK_VALUE;
    }
    
    /**
     * 设置默认农民合作组织类型
     * @param value
     * @param defaultValue
     * @param members
     * @return
     */
    public String setDefaultCooperValue(String value, String defaultValue, String members){
    	if (StringUtils.isBlank(value)
    			&& StringUtils.isNotBlank(members)) {
    		return new BigDecimal(members).compareTo(BigDecimal.ZERO) > 0
    				? CHECK_VALUE : BLANK_VALUE;
    	} else {
    		return switchValue(value, defaultValue);
    	}
    }

	public String switchYesOrNo(Object value) {
		String strVal = value == null ? BLANK_VALUE : value.toString().trim();
		if ("1".equals(strVal)) return "是";
		else return "否";
	}
	
	public void afterPropertiesSet() throws Exception {
		initDeptGroup();
	}
	
}
