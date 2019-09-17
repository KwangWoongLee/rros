var config = require('../config');
var route_loader = {};
var express = require('express');
var router = express.Router();
var user = require('../routes/user');
var fs = require('fs');
var Pusher = require('pusher');

var pusher = new Pusher({
    appId: '777095',
    key: '525ea213e2211393ff19',
    secret: 'e1c60596b3d26fa2c812',
    cluster: 'ap3',
    encrypted: true
});

route_loader.init = function (app) {

    app.use('/', router); // 라우터객체 모듈 등록
    //get 방식으로 경로로 보낼때는 redirect 사용.. ejs로 화면 보여주고 싶을 때는 render사용
    //인덱스 라우팅
    router.route('/').get(function (req, res) {
        res.redirect('login');
    });
    router.route('/adminMain').get(function (req, res) {
        console.log('/adminMain get 호출')
        if (!req.session.name)
            return res.redirect('/login');
        res.redirect('/pasing/' + 1);
    });
    router.route('/adminMain2').get(function (req, res) {
        console.log('adminMain2 get 호출')
        if (!req.session.name)
            return res.redirect('/login');
        res.redirect('/pasing2/' + 1);
    })

    router.route('/search').get(function (req, res) {
        console.log('search get 호출');

        res.render('search.ejs');
    });
    router.route('/search').post(user.searchUser);

    //로그인 라우팅
    router.route('/login').post(user.login);
    router.route('/login').get(function (req, res) {
        res.render('login.ejs');
    })
    router.route('/failedLogin').get(function (req, res) {
        res.render('failedLogin.ejs');
    })


    router.route('/adduser').post(user.adduser);







    //페이지 라우팅
    router.route("/pasing/:cur").get(user.list);
    router.route("/pasing2/:cur").get(user.list2);



    //유저 삭제
    router.route('/delete/:id').get(user.delete);
    router.route('/delete2/:id').get(user.delete2);

    //유저 검색
    router.route('/search?:key').get(user.search);





    router.route('/update2/:id').get(function (req, res) {
        if (!req.session.name)
            return res.redirect('/login');
        res.render('update2.ejs');
    });
    router.route('/update2/:id').post(function (req, res) {
        res.render('update.ejs');
    });

    //로그아웃 라우팅 추가로 세션 이용 ?
    router.route('/logout').get(function (req, res) {
        if (req.session.name) {
            console.log('로그아웃합니다.')
            req.session.destroy(function (err) {
                if (err) {
                    console.log('에러발생')
                }
                console.log('세션 삭제 후 로그아웃됨.');
                res.redirect('/login');
            })
        }
    });


    router.route("/update/:id").get(user.getUpdate);

    router.route("/update").post(user.update);


    router.route('/androidLogin').post(user.Android);


    router.route('/reservation').post(user.reservation);

    router.route('/authWifi').post(user.authWifi);
    router.route('/authReservation').post(user.authReservation);
    router.route('/authPayment/:Id').get(user.authPayment);

    router.route('/getMenu').post(user.reqMenu);
    router.route('/getMenu').get(user.reqMenu);

    router.route('/myImage/:image').get(user.Cusimage);
    router.route('/License/:LicenseNo').get(user.Licenseimage);
    router.route('/CouponImage/:Image').get(user.CouponImage);

    router.route('/licenseInfo2').post(user.reqLicense);


    router.route('/Menu/:MenuNo').get(user.MenuImage);

    router.route('/member/getRestaurantInfo').get(user.getRes);
    router.route('/member/sortRestaurantInfo').get(user.sort);
    router.route('/reqMyInfo').get(user.test);

    router.route('/getReview').get(user.test);

    router.route('/api').get(user.api);

    router.route('/giveCoupon/:LicenseNo/:CustomerNo').get(user.giveCoupon);

    router.route('/giveCoupon').get(user.giveCoupon);

    router.route('/reqCoupon').get(user.reqCoupon);

    router.route('/reqCouponPlace').get(user.reqCouponPlace);

    router.route('/reqFiltering').get(user.reqFiltering);

    router.route('/compareRestaurant').get(user.compareRestaurant);

    router.route('/alertCoupon').get(user.alertCoupon);

    router.route('/message').get(function (req, res) {
        console.log('일단오는가 ?');
        pusher.trigger('my-channel', 'my-event', {
            "user": "웅이",
            "time": "지금",
            "message" : "하위~"
        });
        
        res.send("1");

    });

    router.route('/message').post(function (req, res) {
        console.log('post방식으로 /message호출')
        pusher.trigger('chat', 'my_message', {
            "message": "hello world"
        });
        res.status(200);
    })

        router.route('/test').post(function(req,res){
            console.log('성공')
            var param = req.body.id;
            
            	var responseData = {}
    
                responseData.result = param
    
                res.json(responseData)
        })
            router.route('/test').get(function(req,res){
            console.log('겟방식으로옴')
            
            	var responseData = {}
    
                responseData.result = "되나욤?"
    
                res.json(responseData)
        })
        
        //여기서부터는 코틀린프로젝트
//        router.route('/kt').get(user.kt);

}

    router.route('/getMember').get(user.getMember);

    router.route('/shake').get(user.shake);

    router.route('/confirmShake').get(user.confirmShake);

module.exports = route_loader;
