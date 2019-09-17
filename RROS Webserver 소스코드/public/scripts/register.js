/* 파일 전체에서 ESLint 규칙 경고 미사용 선언 */
/* eslint-disable */

$(function() {
  //출생년도 생성함수 호출
  generateYears($('#sel-birth'));

  /* jQuery UI 컴포넌트를 사용하여 출생년도 목록 처리
     - selectmenu() 함수는 'menuWidget'이라는 문자열을 인자로 넣어주면
       출생년도 목록을 리턴해줌
  */
  //출생년도 셀렉터 박스 생성
  var birthSelect = $('#sel-birth').selectmenu();
  //출생년도 목록 생성
  birthSelect.selectmenu('menuWidget').addClass('overflow');

  /* 회원가입 페이지(#form-register)에서 submit 이벤트가 발생하면 처리할 이벤트 핸들러 등록
  */
  $('#form-register').submit(function(e) {
    e.preventDefault();
    
    //'.txt-warning' 클래스를 찾아 비우고, 감춘다.
    $(this).find('.txt-warning').empty().hide();

    // 입력한 이메일을 email 변수에 저장
    var email = $('#inp-email').val();

    /* 이메일 주소 체크
       - validateEmail() 함수를 이용해 유효한 이메일 주소인지 체크하여
         잘못된 형식이면 오류 메시지를 출력하고 리턴함
    */
    if (!validateEmail(email)) {
      $('#inp-email').next().html('잘못된 형식입니다.').show();
      return;
    }

    // 입력한 password를 password 변수에 저장
    var password = $('#inp-password').val();

    /* password 체크
       - validatePassword() 함수를 이용해 유효한 패스워드인지 체크하여
         잘못된 형식이면 오류 메시지를 출력하고 리턴함
    */
    if (!validatePassword(password)) {
      $('#inp-password').next().html('대문자와 숫자가 포함된 최소 8자의 문자열이여야 합니다.').show();
      return;
    }

    //입력한 비밀번호 저장
    var confirm = $('#inp-confirm').val();
    //비밀번호 일치 확인
    if (password !== confirm) {
      $('#inp-confirm').next().html('비밀번호와 일치하지 않습니다.').show();
      return;
    }

    //성별 checked 값 저장
    var gender = $('input[name="gender"]:checked').val();

    //성별 입력여부 check
    if (!gender) {
      $('#inp-female').siblings('.txt-warning').html('필수 항목입니다.').show();
      return;
    }

    //출생년도 저장
    var birth = $('#sel-birth').val();
    // 출생년도 입력여부 check
    if (!birth) {
      $('#sel-birth').siblings('.txt-warning').html('필수 항목입니다.').show();
      return;
    }

    //동의 checked 값 저장
    var accept = $('#inp-accept:checked').val();

    //동의 check여부 확인
    if (!accept) {
      $('#inp-accept').next().next().html('필수 항목입니다.').show();
      return;
    }
    
    //서버로 전송하기 위해 submit() 함수 호출
    submit(email, password, gender, birth);
  });

  $('#btn-back').click(function() {
    document.location.href = 'index.html';
  });
});

//출생년도 목록 생성 함수
function generateYears($select) {
  for (var i = 1970; i <= 2010; i++) {
    $select.append('<option value="' + i + '">' + i + '</option>');
  }
}

// 이메일 체크 함수를 정규식으로 작성
function validateEmail(email) {
  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
}

// 패스워드 체크 함수를 정규식으로 작성
function validatePassword(password) {
  var re = /^(?=.{8,})(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$/;
  return re.test(password);
}

//서버로 폼 전송하는 함수
function submit(email, password, gender, birth) {
  var params = {
    email: email,
    password: password,
    gender: gender,
    birth: birth
  };

  //$.post() 함수를 통해 서버로 전송
  $.post('some-api-url', params, function(r) {
    console.log(r);
  });
}