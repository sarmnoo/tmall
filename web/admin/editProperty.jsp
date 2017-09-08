<%@ page language="java" contentType="text/html;charset=UTF-8" 
pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<title>编辑属性</title>
<script>
$(function(){	
	$("#editForm").submit(function(){
		if(!checkEmpty("name","属性名称"))
			return false;

		return true;
	});
});

</script>
<div class="workingArea">

	<ol class="breadcrumb">
		<li><a href="admin_category_list">所有分类</a></li>
		 <li><a href="admin_property_list?cid=${c.id}">${c.name}分类</a></li>
	  <li><a href="admin_property_list?cid=${c.id}">属性列表</a></li>
	  <li class="active">编辑属性</li>
	</ol>

<div class="panel panel-warning editDiv">
	  <div class="panel-heading">编辑属性</div>
	  <div class="panel-body">
			<form method="post" id="editForm" action="admin_property_update">
				<table class="editTable">
					<tr>
						<td>属性名称</td>
						<td><input id="name" name="propertyname" value="${property.name}" type="text" class="form-control" ></td>
						
					</tr>
					<tr class="submitTR">
						<td colspan="2" align="center">
							<input type="hidden" name="ptyid"  value="${property.id}">
							<input type="hidden" name="cid"  value="${property.category.id}">
							<button type="submit" class="btn btn-success">更 新</button>
						</td>
					</tr>
				</table>
			
			</form>
		</div>
	</div>
</div>