/* 파일 전체에서 ESLint 규칙 경고 미사용 선언 */
/* eslint-disable */

//구글 맵을 저장하기 위한 map 변수 선언
var map;

$(function() {
  /* window.location의 search 속성 값에서 id값만 파싱하여 id변수에 저장
     - window.location을 사용하면 현재 페이지의 주소에 관련된 여러 정보들을 확인할 수 있는데, 
       여행지 목록을 구분하는 id값은 search 속성에 포함되어 있음
       윈도우로케이션에 기본으로 들어있는 속성인 서치를 이용하여 아이디를 가져오기
     - search:?id=0~20
     - search 속성값에서 id 값만 파싱하기 위해 parseId()함수를 호출하여
       파싱한 결과(id값만 파싱됨)를 리턴받아 id 변수에 저장
  */
  var id = parseId(window.location.search);
  console.log('id : ' + id);
  
  /* 사용자가 선택한 여행지의 상세보기 페이지를 표시하기 위한 데이터를 
     서버에 요청하기 위해 getDetail() 함수 호출
  */
  getDetail(id);
  
  // 지도 객체를 생성하는 showMap() 함수 호출 
  showMap();
});

// //서버에 상세보기 페이지 API 호출 함수
function getDetail(id) {
  // 서버 URL
  var url = 'https://javascript-basic.appspot.com/locationDetail';

  // $.getJSON() 함수로 Ajax 요청(url, id로 여행지 상세보기 정보 요청)
  $.getJSON(url, {
    id: id
  }, function(detail_data) {
    console.log(detail_data);
    
    //여행지 이름 표시
    $('.detail-header-name').html(detail_data.name);
    //도시명 표시
    $('.detail-header-city-name').html(detail_data.cityName);
    //장소 소개 표시
    $('.detail-desc-text').html(detail_data.desc);

    //'#detail-images' 객체를 획득하여  $gallery에 저장
    var $gallery = $('#detail-images');
    
    // 상세정보에 있는 추가 이미지 url(배열)을 images에 저장
    var images = detail_data.subImageList;

    //images 배열에 있는 추가 이미지들을 <img> 태그를 생성하여 $gallery에 추가
    for (var i = 0; i < images.length; i++) {
      var $image = $('<img src="' + images[i] + '" />');
      $gallery.append($image);
    }

    //Galleria 라이브러리를 사용하여 이미지 갤러리 생성
    Galleria.loadTheme('libs/galleria/themes/classic/galleria.classic.min.js');
    Galleria.run('#detail-images');

    // 여행지 지도(구글 맵)에 마커 추가하는 showMarker 함수 호출(위도, 경도값)
    showMarker(detail_data.position.x, detail_data.position.y);

    /* '여행지 등록하기' 버튼을 클릭했을 때 쿠키를 등록
       - '.btn-register'에 클릭 이벤트 핸들러 등록  
       - 쿠기는 문자열로만 저장이 가능함(배열이나 객체로도 저장할 수 없음)
         따라서 선택한 복수의 여행지를 쿠기로 등록할 때는 json 형식으로 변환하여 저장해야 함 
    */
    $('.btn-register').click(function() {
      /* 쿠키명 : 'MYTRIPS'
         - Cookies.getJSON('MYTRIPS') 함수를 사용해서 저장되어 있는 쿠기값을 가져와 myTrips에 저장 
      */
      var myTrips = Cookies.getJSON('MYTRIPS');

      // 쿠키값이 존재하지 않을 경우 빈 배열로 초기화
      if (!myTrips)
        myTrips = [];

      // 여행지를 myTrips에 추가
      myTrips.push({
        id: id,
        name: detail_data.name,
        cityName: detail_data.cityName,
        x: detail_data.position.x,
        y: detail_data.position.y
      });

      // 쿠키값 저장(myTrips을 쿠키 'MYTRIPS'에 저장)
      Cookies.set('MYTRIPS', myTrips);

      alert('여행지가 등록되었습니다!');
    });
  });
}

/* search 속성 값에서 id값만 파싱하는 함수
   - search 속성값 : ?id=0
*/
function parseId(str) {
  // ?제외한 나머지 문자열을 가져옴(0번째부터 끝까지(id=0))  , 섭스트링 함수의 인덱스를 1로 주면 , ?id=0에서 1번인덱스인 i 부터 출력
  var s = str.substring(1);
  // url 포함된 인자들은 '&'기호로 연결되어 있기 때문에 '&'기호로 구분자를 설정, 분리하여 args(배열)에 추가 
  var args = s.split('&');

  //args 배열을 파싱하여 id(id)와 값(0)을 분리 
  for (var i = 0; i < args.length; i++) {
    var arg = args[i];
    
    // '=' 구분자로 id와 값을 분리하여 tokens 배열에 추가 
    var tokens = arg.split('=');

    // tokens이 id인지 비교(타입까지 비교)하여 id값을 반환
    if (tokens[0] === 'id')
      return tokens[1];
  }

  return null;
}

//google.maps.Map() 함수를 이용하여 여행지별로 지도 객체를 생성하는 함수
function showMap() {
  /* 지도 객체 생성
     - google.maps.Map(지도가 표시될 엘리먼트, 지도 옵션 오브젝트) 
  */
  map = new google.maps.Map(document.getElementById('map'), {
    zoom: 12, //줌 레벨 설정
    center: { //지도 중심 좌표
      lat: 33.3617,
      lng: 126.5292
    }
  });
}

//지도에 마커 추가하는 함수
function showMarker(lat, lng) {
  var pos = {
    lat: lat,
    lng: lng
  };

  //마커 생성
  new google.maps.Marker({
    position: pos,
    map: map
  });

  //지도를 주어진 위치(pos)로 이동하는 함수
  map.panTo(pos);
}