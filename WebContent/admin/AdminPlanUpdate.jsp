<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<br>
<div class="container col-md-5">
	<form action="<%=request.getContextPath()%>/admin/update-plan"
		method="post">

		<h2 style="text-align: center;">Edit Plan</h2>

		<input type="hidden" name="id" value="<c:out value='${plan.id}' />" />

		<fieldset class="form-group">
			<label>First Name</label> <input type="text"
				value="<c:out value='${plan.name}' />" class="form-control"
				name="name" required="required">
		</fieldset>

		<fieldset class="form-group">
			<label>Last Name</label> <input type="number"
				value="<c:out value='${plan.price}' />" class="form-control"
				name="price">
		</fieldset>

		<button type="submit" class="btn btn-success">Save</button>
		&nbsp;&nbsp;&nbsp;&nbsp;<a
			href="<%=request.getContextPath()%>/admin/plan-list">Cancel</a>
	</form>
</div>