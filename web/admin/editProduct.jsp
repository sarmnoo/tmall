<%@ page language="java" contentType="text/html;charset=UTF-8" 
pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<script>
	$(function() {
		$("#editForm").submit(function() {
			if(!checkEmpty("name", "产品名称"))
				return false;
			if(!checkEmpty("subTitle", "产品小标题"))
				return false;
			if(!checkEmpty("orignalPrice", "原价格"))
				return false;
			if(!checkEmpty("promotePrice", "优惠价格"))
				return false;
			if(!checkEmpty("stock", "库存"))
				return false;
			if(!checkNumber("orignalPrice", "原价格"))
				return false;
			if(!checkNumber("promotePrice", "优惠价格"))
				return false;
			if(!checkNumber("stock", "库存"))
				return false;
			if(!checkInt("stock", "库存"))
				return false;
			return true;
		});
	});
</script>
<title>编辑产品</title>
<div class="workingArea">
	<ol class="breadcrumb">
		<li>
			<a href="admin_category_list">所有分类</a>
		</li>
		<li>
			<a href="admin_category_list?cid=${c.id}">${c.name}分类</a>
		</li>
		<li class="active">编辑产品</li>
	</ol>
	<div class="panel panel-warning addDiv">
		<div class="panel-heading">编辑产品</div>
		<div class="panel-body">
			<form method="post" id="editForm" action="admin_product_update">
				<table class="addTable">
					<tr>
						<td>产品名称</td>
						<td><input id="name" name="name" type="text" class="form-control" value="${p.name}"></td>
					</tr>
					<tr>
						<td>产品小标题</td>
						<td><input id="subTitle" name="subTitle" type="text" class="form-control" value="${p.subTitle}"></td>
					</tr>
					<tr>
						<td>原价格</td>
						<td><input id="orignalPrice" name="orignalPrice" type="text" class="form-control" value="${p.orignalPrice}"></td>
					</tr>
					<tr>
						<td>优惠价格</td>
						<td><input id="promotePrice" name="promotePrice" type="text" class="form-control" value="${p.promotePrice}"></td>
					</tr>
					<tr>
						<td>库存</td>
						<td><input id="stock" name="stock" type="number" class="form-control" value="${p.stock}"></td>
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" id="pid" name="pid" value=${p.id}>
							<button type="submit" class="btn btn-success">更新</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<%@include file="../include/admin/adminFooter.jsp"%>