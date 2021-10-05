<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="w-50">
		<h1>글쓰기</h1>
		
		<input type="text" id="subject" class="form-control" placeholder="제목을 입력해주세요.">
		<textarea id="content" rows="10" cols="100" class="form-control" placeholder="내용을 입력해주세요."></textarea>
		
		<div class="d-flex justify-content-end">
		<!-- accept=".jpg, .jpeg, .png, .gif" ==> 편의를 위한 기능일 뿐이지 validation은 따로 해주어야 한다.-->
			<input type="file" id="file" class="" accept=".jpg, .jpeg, .png, .gif">
		</div>
		
		<!-- float-left, float-right를 사용하면 그 부모 태그에 flearfix를 작성해 주어야 한다. -->
		<div class="clearfix">
			<a href="/post/post_list_view" class="btn btn-dark float-left">목록</a>
			
			<div class="float-right">
				<button type="button" id="clearBtn" class="btn btn-secondary">모두 지우기</button>
				<button type="button" id="saveBtn" class="btn btn-primary">저장</button>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {
		// 모두 지우기 버튼 클릭
		$('#clearBtn').on('click', function() {
			// 제목 input, 내용 textarea 영역을 빈칸으로 만든다.
			if (confirm('내용을 지우시겠습니까??')) {
				$('#subject').val('');
				$('#content').val('');
			}
			// $('#file').val('');
		});
		
		// 글 내용 저장버튼 클릭
		$('#saveBtn').on('click', function(){
			let subject = $('#subject').val().trim();
			let content = $('#content').val().trim();
			
			if (subject == '') {
				alert("제목을 입력해주세요.");
				return;
			}
			if (content == '') {
				alert('내용을 입력해주세요.');
				return;
			}
			
			// 파일이 업로드 된 경우에 확장자 검사
			let file = $('#file').val(); // 파일을 가져오는게 아니라 파일경로 글자만 가져온다.
			console.log('file : ' + file);
			
			if (file != '') {
				// console.log(file.split('.')); // 파일명을 . 기준으로 자른다. (배열에 저장)
				let ext = file.split('.').pop().toLowerCase(); // pop -> 스택에서 뽑을때의 용어, toLowerCase -> 소문자로 변경
				console.log('==>> file : ' + ext);
				if ($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) == -1) {
					alert('이미지 파일만 업로드 할 수 있습니다.');
					$('#file').val(''); // 잘못된 파일을 비운다.
					return;
				}
			}
			
			// form태그를 자바스크립트에서 만든다.
			let formData = new FormData(); // 자바스크립트에서 제공해주는 객체
			formData.append('subject', subject);
			formData.append('content', content);
			formData.append('file', $('#file')[0].files[0]);
			
			$.ajax({
				type: 'post'
				, url : '/post/create'
				, data : formData
				, enctype : 'multipart/form-data' // 파일 업로드할때 필수태그 - 파일 업로드 필수 설정
				, processData : false // 파일 업로드할때 필수태그 - 파일 업로드 필수 설정
				, contentType : false // 파일 업로드할때 필수태그 - 파일 업로드 필수 설정
				, success : function(data) {
					if (data.result == 'success') {
						alert('메모가 저장되었습니다.');
						location.href="/post/post_list_view";
					}
				}
				, error : function(e) {
					alert('메모 저장에 실패했습니다.' + e);
				}
			});
			
		});
	});
</script>