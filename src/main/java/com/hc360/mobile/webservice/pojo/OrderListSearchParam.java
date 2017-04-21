package com.hc360.mobile.webservice.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "order_list_search_request")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderListSearchParam {
    	//买家列表
		public static final String LIST_TYPE_BUYER = "1";
		//卖家列表
		public static final String LIST_TYPE_SELLER = "2";
		
		
		//订单状态
		private Integer orderState;
		//用户ssoid
		private Long userSsoId;
		//下单时间范围-起始时间
		private Long startTime;
		//下单时间范围-结束时间
		private Long endTime;
		//订单来源
		private String orderSource;
		// 1 买家订单列表  2 卖家订单列表
		private String listType;
		
		/**
		 * 分页信息
		 */
		//每页几条
		private Integer pageSize;
		//第几页
		private Integer page;
		public Integer getOrderState() {
			return orderState;
		}
		public void setOrderState(Integer orderState) {
			this.orderState = orderState;
		}
		public Long getUserSsoId() {
			return userSsoId;
		}
		public void setUserSsoId(Long userSsoId) {
			this.userSsoId = userSsoId;
		}
		public Long getStartTime() {
			return startTime;
		}
		public void setStartTime(Long startTime) {
			this.startTime = startTime;
		}
		public Long getEndTime() {
			return endTime;
		}
		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}
		public String getOrderSource() {
			return orderSource;
		}
		public void setOrderSource(String orderSource) {
			this.orderSource = orderSource;
		}
		public String getListType() {
			return listType;
		}
		public void setListType(String listType) {
			this.listType = listType;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}

}
