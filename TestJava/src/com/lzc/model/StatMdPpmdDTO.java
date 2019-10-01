package com.lzc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * 生产计划主数据
 * 
 */

public class StatMdPpmdDTO  implements Serializable{
	private static final long serialVersionUID = 1L;

		/**
	 * ID
	 */

			        private Long id;
        			/**
	 * 是否标准化
	 */


		private String isStandardization;

			/**
	 * 指标行编码
	 */

	 
		private String indexLineCoding;

		/**
	 * 项目/装置/油种/
	 */

	
		private String project;

		/**
	 * 单位
	 */

		
		private String unit;

			/**
	 * 父级编码
	 */

	
		private String parentCode;

			/**
	 * 标准码
	 */

	  
		private String standardCode;

			/**
	 * 标准名称
	 */


		 private String standardName;

	/**
	 * 备注
	 */
	
		 private String remark;

			/**
	 * 排序
	 */
		
			 private String sortOrder;
        			/**
	 * 板块编码
	 */

	    	//@ExcelField(title="板块编码", align=2, sort=0)
		private String plateCode;

			/**
	 * 菜单编码
	 */

	    	//@ExcelField(title="菜单编码", align=2, sort=0)
		private String menuCode;

			/**
	 * 组织机构
	 */

			        private Long fkOrgId;


			/**
	 * 船名
	 */

	    	//@ExcelField(title="船名", align=2, sort=0)
		private String shipName;


			/**
	 * 子维度01
	 */

	    	//@ExcelField(title="子维度01", align=2, sort=0)
		private String suby01;

			/**
	 * 子维度02
	 */

	    	//@ExcelField(title="子维度02", align=2, sort=0)
		private String suby02;

			/**
	 * 子维度03
	 */

	    	//@ExcelField(title="子维度03", align=2, sort=0)
		private String suby03;

			/**
	 * 子维度04
	 */

	    	//@ExcelField(title="子维度04", align=2, sort=0)
		private String suby04;

			/**
	 * 子维度05
	 */

	    	//@ExcelField(title="子维度05", align=2, sort=0)
		private String suby05;

			/**
	 * 子维度06
	 */

	    	//@ExcelField(title="子维度06", align=2, sort=0)
		private String suby06;

			/**
	 * 子维度07
	 */

	    	//@ExcelField(title="子维度07", align=2, sort=0)
		private String suby07;

			/**
	 * 子维度08
	 */

	    	//@ExcelField(title="子维度08", align=2, sort=0)
		private String suby08;

			/**
	 * 子维度09
	 */

	    	//@ExcelField(title="子维度09", align=2, sort=0)
		private String suby09;

			/**
	 * 子维度10
	 */

	    	//@ExcelField(title="子维度10", align=2, sort=0)
		private String suby10;


        			/**
	 * 创建用户id
	 */

			        private Long createUserId;
        			/**
	 * 创建时间
	 */

			        private Date createTime;
        			/**
	 * 创建用户姓名
	 */

			        private String createUserName;
        			/**
	 * 最后修改用户id
	 */

			        private Long lastUpdateUserId;
        			/**
	 * 最后修改时间
	 */

			        private Date lastUpdateTime;
        			/**
	 * 最后修改用户姓名
	 */

			        private String lastUpdateUserName;
        			/**
	 * 逻辑删除标志
	 */
	/**
	 * 逻辑删除标志
	 */

					private Integer deleted;
						/**
	 * 使用状态(1-可用,0-停用)
	 */

			        private Integer useFlag;

		/**
	 	* 层序码
	 	*/
		private String layerCode;

        			/**
	 * 数据类型（1-物料主数据，2-装置主数据，3层级主数据）
	 */
	 private Integer dataType;
	 private String dataTypeName;



	private Long parentId;

	private String hasChildren;  //前端应用
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsStandardization() {
		return isStandardization;
	}

	public void setIsStandardization(String isStandardization) {
		this.isStandardization = isStandardization;
	}

	public String getIndexLineCoding() {
		return indexLineCoding;
	}

	public void setIndexLineCoding(String indexLineCoding) {
		this.indexLineCoding = indexLineCoding;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getStandardCode() {
		return standardCode;
	}

	public void setStandardCode(String standardCode) {
		this.standardCode = standardCode;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getPlateCode() {
		return plateCode;
	}

	public void setPlateCode(String plateCode) {
		this.plateCode = plateCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public Long getFkOrgId() {
		return fkOrgId;
	}

	public void setFkOrgId(Long fkOrgId) {
		this.fkOrgId = fkOrgId;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getSuby01() {
		return suby01;
	}

	public void setSuby01(String suby01) {
		this.suby01 = suby01;
	}

	public String getSuby02() {
		return suby02;
	}

	public void setSuby02(String suby02) {
		this.suby02 = suby02;
	}

	public String getSuby03() {
		return suby03;
	}

	public void setSuby03(String suby03) {
		this.suby03 = suby03;
	}

	public String getSuby04() {
		return suby04;
	}

	public void setSuby04(String suby04) {
		this.suby04 = suby04;
	}

	public String getSuby05() {
		return suby05;
	}

	public void setSuby05(String suby05) {
		this.suby05 = suby05;
	}

	public String getSuby06() {
		return suby06;
	}

	public void setSuby06(String suby06) {
		this.suby06 = suby06;
	}

	public String getSuby07() {
		return suby07;
	}

	public void setSuby07(String suby07) {
		this.suby07 = suby07;
	}

	public String getSuby08() {
		return suby08;
	}

	public void setSuby08(String suby08) {
		this.suby08 = suby08;
	}

	public String getSuby09() {
		return suby09;
	}

	public void setSuby09(String suby09) {
		this.suby09 = suby09;
	}

	public String getSuby10() {
		return suby10;
	}

	public void setSuby10(String suby10) {
		this.suby10 = suby10;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Long getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Long lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUserName() {
		return lastUpdateUserName;
	}

	public void setLastUpdateUserName(String lastUpdateUserName) {
		this.lastUpdateUserName = lastUpdateUserName;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public String getLayerCode() {
		return layerCode;
	}

	public void setLayerCode(String layerCode) {
		this.layerCode = layerCode;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(String hasChildren) {
		this.hasChildren = hasChildren;
	}
	
	
	
	
}
