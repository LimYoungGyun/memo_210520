<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!-- 값이 가지고와지면 session이 있다는것 -->
<%-- ${userName} --%>

<div class="container col-4 my-5">
	<h1>로그인</h1>
	<br>
	<form id="loginForm" action="/user/sign_in" method="post">
		<section>
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<div class="input-group-text">
						<!-- 아이콘 -->ID
					</div>
				</div>
				<input type="text" id="loginId" name="loginId" class="form-control" placeholder="아이디를 입력하세요.">
			</div>
	
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<div class="input-group-text">
						<!-- 아이콘 -->PW
					</div>
				</div>
				<input type="password" id="password" name="password" class="form-control" placeholder="비밀번호를 입력하세요.">
			</div>
			<button type="submit" class="btn btn-primary btn-block">로그인</button>
			<a href="/user/sign_up_view" class="btn btn-secondary btn-block">회원가입</a>
<!-- 			<button type="button" class="btn btn-secondary btn-block">회원가입</button> -->
		</section>
	</form>
</div>

<script>
	$(document).ready(function(e) {
		
		$('#loginForm').submit(function(e) {
			e.preventDefault(); // submit 자동 수행 중단
			
			let loginId = $('input[name=loginId]').val().trim();
			let password = $('input[name=password]').val().trim();
			
			if (loginId == '') {
				alert('아이디를 입력해주세요.');
				return false;
			}
			if (password == '') {
				alert('비밀번호를 입력해주세요.');
				return false;
			}
			
			let url = $(this).attr('action');
			let data = $(this).serialize(); // 쿼리스트링으로 name 값들을 구성하고 request body에 붙여 보낸다
			console.log('url : ' + url);
			console.log('data : ' + data);
			
			$.post(url, data)
			.done(function(data) {
				if (data.result == 'success') {
					location.href = '/post/post_list_view';
				} else {
					alert('로그인에 실패했습니다. 다시 시도해주세요.');
				}
			});
		});
	})
</script>