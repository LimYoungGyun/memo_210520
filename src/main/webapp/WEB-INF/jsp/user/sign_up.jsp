<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 참고파일 --%>
<%-- https://github.com/marobiana/spring_project_memo/blob/master/src/main/webapp/WEB-INF/jsp/user/sign_up.jsp --%>
<div class="container col-6">
	<h1 class="mb-4">회원가입</h1>
	<form id="signUpForm" method="post" action="/user/sign_up">
		<table class="table">
			<tr>
				<th>* 아이디</th>
				<td>
					<input type="text" id="loginId" name="loginId" class="form-control col-8 d-inline-block">
					<button type="button" id="loginIdCheckBtn" class="btn btn-success ml-3">중복확인</button>
					
					<%-- 아이디 체크 결과 --%>
					<%-- d-none 클래스: display none (보이지 않게) --%>
					<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
					<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
					<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
				</td>
			</tr>
			<tr>
				<th>* 비밀번호</th>
				<td><input type="password" id="password" name="password" class="form-control"></td>
			</tr>
			<tr>
				<th>* 비밀번호 확인</th>
				<td><input type="password" id="confirmPassword" class="form-control"></td>
			</tr>
			<tr>
				<th>* 이름</th>
				<td><input type="text" id="name" name="name" class="form-control"></td>
			</tr>
			<tr>
				<th>* 이메일 주소</th>
				<td><input type="text" id="email" name="email" class="form-control"></td>
			</tr>
		</table>
	
		<button type="button" id="signUpBtn" class="btn btn-primary btn-block">회원가입</button>
	</form>
</div>
<script>
	$(document).ready(function() {
		
		// 아이디 중복 확인
		$('#loginIdCheckBtn').on('click', function(e) {
			let loginId = $('#loginId').val().trim();
			
			if (loginId.length < 4) {
				$('#idCheckLength').removeClass('d-none'); // 경고문구 노출
				$('#idCheckDuplicated').addClass('d-none'); // 숨김
				$('#idCheckOk').addClass('d-none'); // 숨김
				return;
			}
			
			// ajax 서버 호출 (중복여부)
			$.ajax({
				type:'get'
				, url: '/user/is_duplicated_id'
				, data: {'loginId':loginId}
				, success: function(data) {
					
					if (data.result) {
						// 중복이다. 
						$('#idCheckDuplicated').removeClass('d-none'); // 경고문구 노출
						$('#idCheckLength').addClass('d-none'); // 숨김
						$('#idCheckOk').addClass('d-none'); // 숨김
						
					} else {
						// 중복이 아니면 => 가능
						$('#idCheckOk').removeClass('d-none'); // 경고문구 노출
						$('#idCheckLength').addClass('d-none'); // 숨김
						$('#idCheckDuplicated').addClass('d-none'); // 숨김
					}
				}
				, error : function(e) {
					alert("아이디 중복확인에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		});
		
		// 회원가입
		$('#signUpBtn').on('click', function(e) {
			let loginId = $('#loginId').val().trim();
			let password = $('#password').val().trim();
			let confirmPassword = $('#confirmPassword').val().trim();
			let name = $('#name').val().trim();
			let email = $('#email').val().trim();
			
			if (loginId == '') {
				alert('아이디를 입력하세요');
				return;
			}
			if (password == '' || confirmPassword == '') {
				alert('비밀번호를 입력하세요');
				return;
			}
			if (password != confirmPassword) {
				alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요.');
				// 비밀번호, 비밀번호 확인 입력 초기화
				$('#password').val('');
				$('#confirmPassword').val('');
				return;
			}
			if (name == '') {
				alert('이름을 입력하세요');
				return;
			}
			if (email == '') {
				alert('이메일을 입력하세요');
				return;
			}
			
			// 아이디 중복확인이 완료됐는지 확인
			// idCheckOk <div> 클래스에 'd-none'이 없으면 사용가능
			if ($('#idCheckOk').hasClass('d-none')) {
				alert('아이디 중복확인을 해주세요.');
				return;
			}
			
			// 서버에 요청!!!
			//let url = '/user/sign_up';
			let url = $('#signUpForm').attr('action'); // form을 사용했을시에 사용가능
			let data = $('#signUpForm').serialize(); // 폼태그에 있는 데이터를 한번에 보낼 수 있게 구성한다. 그렇지 않으면 json으로 구성을 해야한다.(queryString으로 만들어준다.)
			
			// ajax
			$.post(url, data)
			.done(function(data){
				if (data.result == 'success') {
					alert('가입을 환영합니다.!!! 로그인 해주세요.');
					location.href = '/user/sign_in_view';
				} else {
					alert('가입에 실패했습니다.');
				}
				
			});
		});
	});
</script>