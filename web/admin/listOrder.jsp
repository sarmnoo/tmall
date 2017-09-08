<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<div class="workingArea">
	<h1 class="label label-info">分类管理</h1>
	<br> <br>
	<div class="listDataTableDiv">
		<table
			class="table table-striped table-bordered table-hover table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>状态</th>
					<th>金额</th>
					<th>商品数量</th>
					<th>买家名称</th>
					<th>创建时间</th>
					<th>支付时间</th>
					<th>发货时间</th>
					<th>确认收货时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${os}" var="o">
					<tr>
						<td>${o.id}</td>
						<td>${o.status}</td>
						<td>￥<fmt:formatNumber type="number" value="${o.total}" minFractionDigits="2" /></td>
						<td align="center">${o.totalNumber}</td>
						<td align="center">${o.user.name}</td>
						<td><fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:MM:SS" /></td>
						<td><fmt:formatDate value="${o.payDate}" pattern="yyyy-MM-dd HH:MM:SS" /></td>
						<td><fmt:formatDate value="${o.deliveryDate}" pattern="yyyy-MM-dd HH:MM:SS" /></td>
						<td><fmt:formatDate value="${o.confirmDate}" pattern="yyyy-MM-dd HH:MM:SS" /></td>
						<td>
							
						</td>						
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="pageDiv">
	<%@include file="../include/admin/adminPage.jsp"%>
	</div>