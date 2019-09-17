/* 파일 전체에서 ESLint 규칙 경고 미사용 선언 */
/* eslint-disable */

// 마커 라벨을 자동으로 생성하기 위한 MARKER_LABELS 변수 선언
var MARKER_LABELS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';

console.log(MARKER_LABELS);

//구글 맵을 저장하기 위한 map 변수 선언
var map;
//마커를 저장할 markers 배열 선언 
var markers = {};

// 쿠키에 저장된 데이터로 여행자 목록 만드는 함수
$(function() {
  //Cookies.getJSON('MYTRIPS') 함수를 사용해서 저장되어 있는 쿠기값을 가져와 myTrips에 저장
  var myTrips = Cookies.getJSON('MYTRIPS');

  // 쿠키값이 존재하지 않을 경우 빈 배열로 초기화
  if (!myTrips)
    myTrips = [];
  
  // 지도 객체를 생성하는 showMap() 함수 호출 
  showMap();
    
  //여행지 목록 엘리먼트를 만드는 generateMyTripList 함수(쿠기정보) 호출
  generateMyTripList(myTrips);
});

//쿠기에 등록된 여행지 목록(list)으로 여행지 목록 엘리먼트를 만드는 함수 
function generateMyTripList(list) {
  /* 등록된 모든 여행지의 위/경도를 저장할 수 있는 '빈 영역의 인스턴스' 생성
     - 등록된 모든 여행지의 위/경도를 이 영역에 추가하고, map.fitBounds(bounds)함수에 넣어 주면
       최적의 줌 레벨을 계산하여 지도를 보여줌(모든 마커가 한 화면에 나오는 지도를 표시)       
  */
  var bounds = new google.maps.LatLngBounds();
    
  //여행지 목록을 출력할 '#mytrip-list' 객체를 획득하여 $list에 저장
  var $list = $('#mytrip-list');

  // 쿠키정보(list 배열)를 가지고 for문으로 각각의 여행지 목록 엘리먼트 만들어 목록에 추가
  for (var i = 0; i < list.length; i++) {
    // 여행지 등록 정보를 myTrip에 저장 
    var myTrip = list[i];

    //지도의 위/경도 객체 생성(pos)
    var pos = {
      lat: myTrip.x,
      lng: myTrip.y
    };

    //마커 라벨 (markerLabel)을 자동으로 생성 
    var markerLabel = MARKER_LABELS[i];

    console.log(markerLabel);
    
    /* 여행지 목록 list를 만들기 위한 탬플릿('#mytrip-item-template')을 복제하고,
       id의 중복을 막기 위해 id 속성 제거
    */
    var $item = $('#mytrip-item-template').clone().removeAttr('id');
      
    // data() 메서드를 이용하여 $item 엘리먼트에 id 추가(key:id, value:myTrip.id)
      // $(selector). data(key, value) : selector에 데이터 저장
      // 각 여행지 목록 리스트에 해당 여행지의 id 저장하여 삭제 등 처리
    $item.data('id', myTrip.id);
    //목록 list의 '.item-name'에 '마커 라벨.여행지 이름' 표시  
    $item.find('.item-name').html(markerLabel + '. ' + myTrip.name);
    //목록 list의 '.item-city-name'에 '도시명' 표시  
    $item.find('.item-city-name').html(myTrip.cityName);

    // x버튼('.item-remove')을 클릭하면 
    $item.find('.item-remove').click(function() {
      //closest() 함수를 사용해 제거하려는 엘리먼트를 찾아 변수 $elem에 저장
      var $elem = $(this).closest('.mytrip-item');
      // 제거하려는 엘리먼트의 id를 가져와 변수 id에 저장
      var id = $elem.data('id');

      //remove() 함수를 이용하여 해당 엘리먼트를 목록에서 제거
      $elem.remove();

      /* 지도에서 마커 지우기
       - setMap(null)함수에 인자로 null을 넘겨주면 지도에서 해당 마커가 제거됨
      */
      markers[id].setMap(null);
      // 지도에서 마커를 지우고, markers 배열에 저장해 놓은 마커를 null로 할당해 지움
      markers[id] = null;

      /* 제거한 여행지를 제외시킨 후,  쿠키를 다시 저장
         - removeFromList() 함수를 제거한 여행지의 list와 id를 인자로 호출하여 
           제거한 여행지를 뺀, 나머지 여행지를 리턴 받아 newList에 저장
      */
      var newList = removeFromList(list, id);

      //Cookies.set() 함수를 이용하여 쿠기에 다시 저장
      Cookies.set('MYTRIPS', newList);

    });

    $list.append($item);

    ////마커를 생성하여, marker 변수에 저장(마커에 접근하기 위해)
    var marker = new google.maps.Marker({
      position: pos,
      label: markerLabel,
      map: map
    });

    /* markers 객체에 [myTrip.id]를 키값으로 마커(marker)를 저장하면,
       나중에 원하는 여행지의 id값으로 마커를 찾을 수 있음
    */
    markers[myTrip.id] = marker;

    /* extend() 함수를 이용해 등록된 모든 여행지의 위/경도 정보를 bounds 객체에 추가
       - 등록된 여행지의 위/경도 값이 저장된 위경도 객체(pos)를 
         bounds에 넣어주면 해당 영역이 모든 좌표를 포함할 수 있도록 넓어짐
    */
    bounds.extend(pos);
  }

  /* bounds 객체를 map.fitBounds(bounds)함수에 넣어 주면
     최적의 줌 레벨을 계산하여 지도를 보여줌(모든 마커가 한 화면에 나오는 지도를 표시)
  */
  map.fitBounds(bounds);

}

//splice() 함수를 이용해 제거한 id를 list 배열에서 삭제한 후 list를 반환
function removeFromList(list, id) {
  var index = -1;//index 초기값을 -1(찾지 못했을 때 값)로 설정

  //list배열에서 제거한 id를 찾으면 index=1로 갱신한 후 for문 종료
  for (var i = 0; i < list.length; i++) {
    if (list[i].id === id) {
      index = i;
      break;
    }
  }

  /* index가 -1이 아니면, 원하는 요소를 찾은 것이므로,
     splice()함수를 이용해 list배열에서 해당 요소를 제거
  */
  if (index !== -1) {
    list.splice(index, 1);
  }

  // list 배열 리턴
  return list;
}

//google.maps.Map() 함수를 이용하여 여행지별로 지도 객체를 생성하는 함수
function showMap() {
  map = new google.maps.Map(document.getElementById('map'));
}
