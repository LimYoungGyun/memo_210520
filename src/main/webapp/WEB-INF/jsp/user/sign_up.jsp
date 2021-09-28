<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container">
	<div class="container col-6">
		<table class="table">
			<tr>
				<th>* 아이디</th>
				<td><input type="text" id="" class="form-control col-8 d-inline-block">
					<button type="button" class="btn btn-success ml-3">중복확인</button>
					<div class="text-success small loginIdOk">사용 가능한 아이디 입니다.</div>
					<div class="text-danger small loginIdNo">사용 불가능한 아이디 입니다.</div>
				</td>
			</tr>
			<tr>
				<th>* 비밀번호</th>
				<td><input type="text" id="" class="form-control"></td>
			</tr>
			<tr>
				<th>* 비밀번호 확인</th>
				<td><input type="text" id="" class="form-control"></td>
			</tr>
			<tr>
				<th>* 이름</th>
				<td><input type="text" id="" class="form-control"></td>
			</tr>
			<tr>
				<th>* 이메일 주소</th>
				<td><input type="text" id="" class="form-control"></td>
			</tr>
		</table>

		<button type="button" class="btn btn-primary btn-block">회원가입</button>
	</div>
</div>
<script>
	$(document).ready(function() {
		$('.loginIdOk').addClass('d-none');
		$('.loginIdNo').addClass('d-none');
	});
</script>