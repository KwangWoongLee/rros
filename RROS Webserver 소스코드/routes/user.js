var database = require('../database');
var fs = require('fs');
var path = require('path');
require('date-utils');
var Name;
var user = {};
var request = require('request');
var cheerio = require("cheerio")
/*
var b = function(callback){
     console.log('b호출');
    database.pool.getConnection(function(err,conn){
        if(err){
            console.log('무슨에러 ?')
            console.dir(err)
            if(conn){
                conn.release();
            }
            callback(err,null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);
        var id = 'tpgus';
        var tablename = 'Customer';
        var columns = ['Id', 'CusName','Password'];
        var exec= conn.query("select ?? from ?? where Id =?", [columns,tablename,id], function(err,rows){
            conn.release();
            console.log('실행된 sql : ' + exec.sql);
            
            if(err){
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err,null);
                return;
            }
            if(rows.length>0){
                console.log('사용자 찾음');
                callback(null,rows);
                
                
            } else {
                console.log('사용자 찾지 못함');
                callback(null,null);
                
            }
        });
    });
}
user.b = function(req,res){
     console.log('제이슨으로 만들게요');

                    b(function(err,rows){
                        if(err){
                            console.log('에러발생');
                            res.writeHead(200, {"Content-Type":"text/html;charset=utf8"});
                            res.write('<h1>에러발생</h1>');
                            res.end();
                            return;
                        }
                        if(rows){
                            var result = [];
                            console.dir(rows);
                            for(var i=0;i<rows.length;i++){
                                    var data = {
                                        "id":""+rows[i].Id,
                                        "password":""+rows[i].Password,
                                        "name":""+rows[i].CusName
                                    };
                                result[i]=JSON.stringify(data);
                                    
                                        
                                
                            }
                                console.dir(result);
                            return;
                            }

                        else{
                            console.log('에러 발생');
                            return;
                        }
                    });
}

*/









//유저 추가
var addUser = function (id, name, password, email, phone, age, callback) {
    console.log('addUser 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var data = {
            Id: id,
            CusName: name,
            Password: password,
            phoneNo: phone,
            Email: email,
            age: age,
            Flag: 0
        };
        var exec = conn.query('insert into Customer set ?', data, function (err, result) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            callback(null, result);
        });
    });
};
user.adduser = function (req, res) {
    console.log('/process/adduser 라우팅 호출');
    var paramId = req.body.id || req.query.id;
    var paramPassword = req.body.password || req.query.password;
    var paramName = req.body.name || req.query.name;
    var paramEmail = req.body.email || req.query.email;
    var paramPhone = req.body.phone || req.query.phone;
    var paramAge = req.body.age || req.query.age;
    console.log('요청파라미터 : ' + paramId + ', ' + paramPassword + ',' + paramName);

    addUser(paramId, paramName, paramPassword, paramEmail, paramPhone, paramAge, function (err, addedUser) {
        if (err) {
            console.log('에러발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러발생' + console.dir(err) + '</h1>');
            res.end();
            return;
        }
        if (addedUser) {
            console.dir(addedUser);
            res.render('adduser_success.ejs');

        } else {
            console.log('에러 발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>사용자 추가 실패.</h1>');
            res.end();
            return;
        }
    });

};




//로그인
var authUser = function (id, password, callback) {
    console.log('authUser 호출됨 안드로이드에서');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'Customer';
        var columns = ['Id', 'CusName', 'Password'];
        var exec = conn.query("select ?? from ?? where Id =? and Password = ?", [columns, tablename, id, password], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);


            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
};
var authAdmin = function (id, password, callback) {
    console.log('authAdmin 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'user';
        var columns = ['id', 'name', 'password'];
        var exec = conn.query("select ?? from ?? where id =? and password = ?", [columns, tablename, id, password], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');

                callback(null, rows);

            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);
            }
        });
    });
};
user.login = function (req, res) {
    console.log('/login 라우팅 호출');
    var paramId = req.body.id || req.query.id;
    var paramPassword = req.body.password || req.query.password;
    console.log('요청파라미터 : ' + paramId + ', ' + paramPassword);

    authAdmin(paramId, paramPassword, function (err, rows) {
        if (err) {
            console.log('에러발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러발생</h1>');
            res.end();
            return;
        }
        if (rows) {
            console.log('세션정보저장');
            req.session.name = {
                id: paramId,
                password: paramPassword,
                authorized: true
            }
            var data = {
                username: paramId
            }
            console.log('adminMain라우팅할예정')
            res.redirect('adminMain');
        } else {
            console.log('에러 발생');
            res.redirect('failedLogin')
            return;
        }
    });
}









//유저 검색
var search = function (key, callback) {
    console.log('search 함수 호출됨');

    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'License';
        var columns = ['Cusno', 'id', 'name', 'age', 'email', 'password', 'phone'];
        if (key == "") {
            var exec = conn.query("select * from ??", [columns, tablename],
                function (err, rows) {
                    conn.release();
                    console.log('실행된 sql : ' + exec.sql);
                    if (err) {
                        console.log('sql 실행 시 에러 발생');
                        console.dir(err);
                        callback(err, null);
                        return;
                    }
                    if (rows.length > 0) {
                        console.log('사용자 찾음');
                        rows
                        callback(null, rows);
                    } else {
                        console.log('사용자 찾지 못함');
                        callback(null, null);
                    }

                });
        } else {
            var exec = conn.query("select * from ?? where Name = ?", [columns, tablename, key],
                function (err, rows) {
                    conn.release();
                    console.log('실행된 sql : ' + exec.sql);
                    if (err) {
                        console.log('sql 실행 시 에러 발생');
                        console.dir(err);
                        callback(err, null);
                        return;
                    }
                    if (rows.length > 0) {
                        console.log('사용자 찾음');
                        rows
                        callback(null, rows);
                    } else {
                        console.log('사용자 찾지 못함');
                        callback(null, null);
                    }

                });
        }

    });
};
user.search = function (req, res) {
    console.log('/seach?:key 라우팅 호출');
    var paramKey = req.body.key || req.query.key;
    console.log('요청파라미터 : ' + paramKey);

    search(paramKey, function (err, rows) {
        if (err) {
            console.log('에러발생')
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러 발생</h1>');
            res.end();
            return;
        }
        if (rows) {
            console.dir(rows);

        } else {
            console.log('에러 발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>원하는 사용자가 없습니다.</h1>');
            res.end();
            return;
        }
    });
};









//유저 삭제
var delUser = function (id, callback) {
    console.log('delUser 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query('delete from Customer where id=?', [id], function (err, result) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            callback(null, result);
        });
    });
};
user.delete = function (req, res) {
    console.log('/delete/:id 라우팅 호출');
    var paramId = req.body.id || req.query.id || req.params.id;

    console.log('요청파라미터 : ' + paramId);

    delUser(paramId, function (err, deletedUser) {
        if (err) {
            console.log('에러발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러발생' + console.dir(err) + '</h1>');
            res.end();
            return;
        }
        if (deletedUser) {
            console.dir(deletedUser);
            res.redirect('/pasing/' + 1);
            return;
        } else {
            console.log('에러 발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>삭제 실패.</h1>');
            res.end();
            return;
        }
    });

};
user.delete2 = function (req, res) {
    console.log('/delete/:id 라우팅 호출');
    var paramId = req.body.id || req.query.id || req.params.id;

    console.log('요청파라미터 : ' + paramId);

    delUser(paramId, function (err, deletedUser) {
        if (err) {
            console.log('에러발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러발생' + console.dir(err) + '</h1>');
            res.end();
            return;
        }
        if (deletedUser) {
            console.dir(deletedUser);
            res.redirect('/pasing2/' + 1);
            return;
        } else {
            console.log('에러 발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>삭제 실패.</h1>');
            res.end();
            return;
        }
    });

};









//유저 수정
var update = function (id, password, name, user, callback) {
    console.log('update 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query('update Customer set name = ?, id = ?, password = ? where id=?', [name, id, password, user], function (err, result) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            callback(null, result);
        });
    });
};
user.update = function (req, res) {
    console.log('/update 라우팅 호출');
    var paramId = req.body.id || req.query.id || req.params.id;
    var paramPassword = req.body.password;
    var paramName = req.body.name;
    var paramUser = req.body.paramId || req.query.paramId || req.params.paramId;
    console.log('요청파라미터 : ' + paramId + ' ,' + paramName + ' ,' + paramPassword + ',' + paramUser);

    update(paramId, paramPassword, paramName, paramUser, function (err, insertedUser) {
        if (err) {
            console.log('에러발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러발생' + console.dir(err) + '</h1>');
            res.end();
            return;
        } else {
            console.log('수정 성공');
            res.redirect('/pasing/' + 1);
            return;
        }
    });

};
var getupdate = function (id, callback) {
    console.log("수정 진행")

    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'Customer';
        var columns = ['id', 'name', 'password'];
        var exec = conn.query("select ?? from ?? where id =?", [columns, tablename, id], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);

            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);
            }
        });
    });
};
user.getUpdate = function (req, res) {
    console.log('/login 라우팅 호출');
    var paramId = req.body.id || req.query.id || req.params.id;
    console.log('요청파라미터 : ' + paramId);
    getupdate(paramId, function (err, rows) {
        if (err) {
            console.log('에러발생');
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러발생</h1>');
            res.end();
            return;
        }
        if (rows) {
            var data = {
                paramId: paramId,
                name: rows.name,
                id: rows.id,
                password: rows.password
            };
            req.app.render('update', data, function (err, html) {
                    if (err) {
                        console.error('뷰 렌터링 중 오류 발생 : ' + err.stack);

                        res.writeHead(200, {
                            "Content-Type": "text/html;charset=utf8"
                        });
                        console.dir(err)
                        res.write('<h1>에러발생</h1>');
                        res.end();

                        return;
                    }


                    res.end(html);
                }

            )
        };

    })
}







//아이디 비밀번호 찾기
var searchuser = function (name, phone, email, callback) {
    console.log('search 함수 호출됨');

    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'user';
        var columns = ['id', 'password'];

        var exec = conn.query("select ?? from ?? where name = ? and phone = ? and email=  ?", [columns, tablename, name, phone, email],
            function (err, rows) {
                conn.release();
                console.log('실행된 sql : ' + exec.sql);
                if (err) {
                    console.log('sql 실행 시 에러 발생');
                    console.dir(err);
                    callback(err, null);
                    return;
                }
                if (rows.length > 0) {
                    console.log('사용자 찾음');
                    rows
                    callback(null, rows);
                } else {
                    console.log('사용자 찾지 못함');
                    callback(null, null);
                }

            });
    })
}
user.searchUser = function (req, res) {
    console.log('/seachUser 라우팅 호출');
    var paramName = req.body.name || req.query.name || req.params.name;
    var paramEmail = req.body.email || req.query.email || req.params.email;
    var paramPhone = req.body.phone || req.query.phone || req.params.phone;
    console.log('요청파라미터 : ' + paramName + ' ,' + paramEmail + ' ,' + paramPhone);

    searchuser(paramName, paramPhone, paramEmail, function (err, rows) {
        if (err) {
            console.log('에러발생')
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러 발생</h1>');
            res.end();
            return;
        }
        if (rows) {
            console.dir(rows);
            var data = {
                id: rows.id,
                password: rows.password
            };
            req.app.render('searchUser_success', data, function (err, html) {
                    if (err) {
                        console.error('뷰 렌터링 중 오류 발생 : ' + err.stack);

                        res.writeHead(200, {
                            "Content-Type": "text/html;charset=utf8"
                        });
                        console.dir(err)
                        res.write('<h1>에러발생</h1>');
                        res.end();

                        return;
                    }

                    res.end(html);
                }

            )
        }

    })
};





//
user.list = function (req, res) {
    if (!req.session.name)
        return res.redirect('/login');
    //페이지당 게시물 수 : 한 페이지 당 10개 게시물
    var page_size = 10;
    //페이지의 갯수 : 1 ~ 10개 페이지
    var page_list_size = 10;
    //limit 변수
    var no = "";
    //전체 게시물의 숫자
    var totalPageCount = 0;

    var queryString = 'select count(*) as cnt from Customer';
    database.pool.query(queryString, function (error2, data) {
        if (error2) {
            console.log(error2 + "메인 화면 mysql 조회 실패");
            return
        }
        //전체 게시물의 숫자
        totalPageCount = data[0].cnt

        //현제 페이지
        var curPage = req.params.cur;

        console.log("현재 페이지 : " + curPage, "전체 페이지 : " + totalPageCount);


        //전체 페이지 갯수
        if (totalPageCount < 0) {
            totalPageCount = 0
        }

        var totalPage = Math.ceil(totalPageCount / page_size); // 전체 페이지수
        var totalSet = Math.ceil(totalPage / page_list_size); //전체 세트수
        var curSet = Math.ceil(curPage / page_list_size) // 현재 셋트 번호
        var startPage = ((curSet - 1) * 10) + 1 //현재 세트내 출력될 시작 페이지
        var endPage = (startPage + page_list_size) - 1; //현재 세트내 출력될 마지막 페이지


        //현재페이지가 0 보다 작으면
        if (curPage < 0) {
            no = 0
        } else {
            //0보다 크면 limit 함수에 들어갈 첫번째 인자 값 구하기
            no = (curPage - 1) * 10
        }

        console.log('[0] curPage : ' + curPage + ' | [1] page_list_size : ' + page_list_size + ' | [2] page_size : ' + page_size + ' | [3] totalPage : ' + totalPage + ' | [4] totalSet : ' + totalSet + ' | [5] curSet : ' + curSet + ' | [6] startPage : ' + startPage + ' | [7] endPage : ' + endPage)

        var result2 = {
            "curPage": curPage,
            "page_list_size": page_list_size,
            "page_size": page_size,
            "totalPage": totalPage,
            "totalSet": totalSet,
            "curSet": curSet,
            "startPage": startPage,
            "endPage": endPage
        };

        var queryString = 'select * from Customer order by Id desc limit ?,?';
        database.pool.query(queryString, [no, page_size], function (error, result) {
            if (error) {
                console.log("페이징 에러" + error);
                return
            }
            var data = {
                data: result,
                pasing: result2
            };

            req.app.render('adminMain', data, function (err, html) {
                if (err) {
                    console.error('뷰 렌터링 중 오류 발생 : ' + err.stack);

                    res.writeHead(200, {
                        "Content-Type": "text/html;charset=utf8"
                    });
                    console.dir(err)
                    res.write('<h1>에러발생</h1>');
                    res.end();

                    return;
                }


                res.end(html);
            })
        });


    })
};
user.list2 = function (req, res) {
    if (!req.session.name)
        return res.redirect('/login');
    //페이지당 게시물 수 : 한 페이지 당 10개 게시물
    var page_size = 10;
    //페이지의 갯수 : 1 ~ 10개 페이지
    var page_list_size = 10;
    //limit 변수
    var no = "";
    //전체 게시물의 숫자
    var totalPageCount = 0;

    var queryString = 'select count(*) as cnt from License';
    database.pool.query(queryString, function (error2, data) {
        if (error2) {
            console.log(error2 + "메인 화면 mysql 조회 실패");
            return
        }
        //전체 게시물의 숫자
        totalPageCount = data[0].cnt

        //현제 페이지
        var curPage = req.params.cur;

        console.log("현재 페이지 : " + curPage, "전체 페이지 : " + totalPageCount);


        //전체 페이지 갯수
        if (totalPageCount < 0) {
            totalPageCount = 0
        }

        var totalPage = Math.ceil(totalPageCount / page_size); // 전체 페이지수
        var totalSet = Math.ceil(totalPage / page_list_size); //전체 세트수
        var curSet = Math.ceil(curPage / page_list_size) // 현재 셋트 번호
        var startPage = ((curSet - 1) * 10) + 1 //현재 세트내 출력될 시작 페이지
        var endPage = (startPage + page_list_size) - 1; //현재 세트내 출력될 마지막 페이지


        //현재페이지가 0 보다 작으면
        if (curPage < 0) {
            no = 0
        } else {
            //0보다 크면 limit 함수에 들어갈 첫번째 인자 값 구하기
            no = (curPage - 1) * 10
        }

        console.log('[0] curPage : ' + curPage + ' | [1] page_list_size : ' + page_list_size + ' | [2] page_size : ' + page_size + ' | [3] totalPage : ' + totalPage + ' | [4] totalSet : ' + totalSet + ' | [5] curSet : ' + curSet + ' | [6] startPage : ' + startPage + ' | [7] endPage : ' + endPage)

        var result2 = {
            "curPage": curPage,
            "page_list_size": page_list_size,
            "page_size": page_size,
            "totalPage": totalPage,
            "totalSet": totalSet,
            "curSet": curSet,
            "startPage": startPage,
            "endPage": endPage
        };

        var queryString = 'select * from License order by Id desc limit ?,?';
        database.pool.query(queryString, [no, page_size], function (error, result) {
            if (error) {
                console.log("페이징 에러" + error);
                return
            }
            var data = {
                data: result,
                pasing: result2
            };

            req.app.render('adminMain2', data, function (err, html) {
                if (err) {
                    console.error('뷰 렌터링 중 오류 발생 : ' + err.stack);

                    res.writeHead(200, {
                        "Content-Type": "text/html;charset=utf8"
                    });
                    console.dir(err)
                    res.write('<h1>에러발생</h1>');
                    res.end();

                    return;
                }


                res.end(html);
            })
        });


    })
};









user.Android = function (req, res) {
    console.log('/androidLogin 라우팅 호출');
    var paramId = req.body.id || req.query.id;
    var paramPassword = req.body.password || req.query.password;
    console.log('요청 파라미터: ' + paramId + ',' + paramPassword);
    authUser(paramId, paramPassword, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);
            console.log('안드로이드 로그인 허용');
            res.sendStatus(200);
            return;
        } else {
            console.log('에러 발생');
            console.log('400코드 보냄');
            res.sendStatus(400);

            return;
        }
    });
}


var reqCusno = function (paramId) {
    console.log(paramId);
    console.log('해당하는 customerno 찾기작업');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {

                conn.release();
            }
            return;
        }

        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query('select CustomerNo from Customer where Id = ?', [paramId], function (err, result) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.log('sql 실행시 에러 발생');
                return;
            }
            var data = result[0].CustomerNo;
            return data;

        });
    });
}

var Reservation = function (cusno, time, noofpeople, menuno, LicenseNo, Content, callback) {
    console.log('reservation 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);
        var ResNo = createResNo(cusno);
        var data = {
            ReservationTime: time,
            fk_menuNo: 1,
            fk_customerNo: cusno,
            NoOfPeople: noofpeople,
            fk_LicenseNo: LicenseNo,
            reqContent:Content
        };
        var exec = conn.query('insert into Reservation set ?', data, function (err, result) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            callback(null, result);
        });
    });
};
user.reservation = function (req, res) {
    console.log('/reservation 라우팅 호출');
    var paramCustomerNo = req.body.fk_customerNo || req.query.fk_customerNo;
    console.log(paramCustomerNo);
    var paramNoOfPeople = req.body.NoOfPeople || req.query.NoOfPeople;
    var paramLicenseNo = req.body.fk_LicenseNo || req.query.fk_LicenseNo;
    var parammenuNo = req.body.menuNo || req.query.menuNo;
    var paramTime = req.body.ReservationTime || req.query.ReservationTime;
    var paramContent = req.body.Content || req.query.Content;
    console.log('요청 파라미터: ' + paramCustomerNo + ',' + paramNoOfPeople + ' ,' + paramTime + ' ,' + paramContent);
    Reservation(paramCustomerNo, paramTime, paramNoOfPeople, parammenuNo, paramLicenseNo,paramContent, function (err, addedReservation) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            res.sendStatus(400);
            return;
        }
        if (addedReservation) {
            console.dir(addedReservation);
            update(4);
            res.status(200);
            return;
        } else {
            console.log('에러 발생');

            res.sendStatus(400);
            return;
        }
    });
};


var update = function (no) {
    console.log('update 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);


        var exec = conn.query('update Customer set Flag=1 where CustomerNo=?', [no], function (err, result) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.log('sql 실행시 에러 발생');

                return;
            }

        });
    });
}

var authBeacon = function (id, callback) {
    console.log('authUser 호출됨 안드로이드에서');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'Customer';
        var columns = ['Flag'];
        var exec = conn.query("select ?? from ?? where Id =?", [columns, tablename, id], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].Flag == 1) {
                        console.log('플래그1임');
                        conn.query("update Customer set Flag=2 where id= ?",[id])
                        conn.release;
                        callback(null, rows[i].Flag);
                        return;
                    } else if (rows[i].Flag == 0)
                        callback(null, null);
                    else if(rows[i].Flag==2)
                    {   console.log('플래그2임')
                        callback(null, rows[i].Flag);
                     
                        return;
                    }
                }



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
};

var authWifi= function (LicenseNo, callback) {
    console.log('authWifi 호출됨 안드로이드에서');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'License';
        var columns = ['ssid','netpwd'];
        var exec = conn.query("select ?? from ?? where LicenseNo =?", [columns, tablename, LicenseNo], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                for (var i = 0; i < rows.length; i++) {
                        console.log('해당하는 와이파이비밀번호와 아이디');
                        callback(null, rows);
                        return;

                }



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
};
user.authWifi = function (req, res) {
    console.log('authWifi 호출');
    var paramLicenseNo = req.body.LicenseNo || req.query.LicenseNo;
    console.log('요청파라미터 : ' + paramId);
    authWifi(paramLicenseNo, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);


            var result = JSON.stringify(rows);

        }
        console.dir(result);
        try {

            res.status(200).json(rows);
            console.log('에러없음');
            console.log('200코드 보냄');
        } catch (e) {
            console.log(e.message)
            console.log('에러있음')
        };

        })
};

user.authReservation = function (req, res) {
    console.log('/authReservation 라우팅 호출, 플래그확인진행');
    var paramId = req.body.Id || req.query.Id;
    var paramLicenseNo = req.body.LicenseNo || req.query.LicenseNo;
    console.log('요청파라미터 : ' + paramId);
    authBeacon(paramId, function (err, returnValue) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (returnValue==1) {
        
            console.log('비콘플래그1 존재');
            res.status(200).send("1");
            return;
        }
        if (returnValue==2){
            console.log('2를보냄');
            res.status(200).send("2");
            return;
        }
    });
};

var authPayment = function (id, callback) {
    console.log('authPayment 호출됨 안드로이드에서');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'Customer';
        var columns = ['Flag'];
        var exec = conn.query("select ?? from ?? where Id =?", [columns, tablename, id], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].Flag == 2) {
                        console.log('플래그2임');
                        callback(null, rows);
                        return;
                    } else {
                        console.log('플래그1이거나 없음')
                        callback(null, null);
                }
                }


            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
};
user.authPayment = function (req, res) {
    console.log('/authReservation 라우팅 호출, 플래그확인진행');
    var paramId = req.body.Id || req.query.Id || req.params.Id;

    console.log('요청파라미터 : ' + paramId);
    authPayment(paramId, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);
            console.log('비콘플래그2 존재');
            res.status(200).send("2");
            return;

        }
    });
};


var reqMenu = function (LicenseNo, callback) {
    console.log('reqMenu 호출됨 안드로이드에서');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query("SELECT * FROM menu WHERE fk_LicenseNo_menu =?", LicenseNo, function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}
user.reqMenu = function (req, res) {
    console.log('/reqMenu 라우팅 호출');
    var paramNo = req.body.licenseNo || req.query.licenseNo || req.params.licenseNo;

    console.log('요청파라미터 : ' + paramNo);
    reqMenu(paramNo, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);


            var result = JSON.stringify(rows);

        }
        console.dir(result);
        try {

            res.status(200).json(rows);
            console.log('에러없음');
            console.log('200코드 보냄');
        } catch (e) {
            console.log(e.message)
            console.log('에러있음')
        };



        return;
    });
}

var createResNo = function (CustomerNo) {

    var date = new Date();
    var time = date.toFormat('YYYYMMDDHH24MISS');
    var result = time + CustomerNo;
    return result;
}


var reqLicense = function (x, y, callback) {
    console.log('reqLicense 함수 호출됨');

    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'License';
        var columns = ['Name', 'type', 'address', 'review'];

        var exec = conn.query("select ?? from ?? Where LicenseX=? and LicenseY =?", [columns, tablename, x, y], function (err, rows) {
            conn.release();
            console.log('여기까지는돼?')
            console.log('실행된 sql : ' + exec.sql);
            if (err) {
                console.log('sql 실행 시 에러 발생');
                console.dir(err);
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('라이센스 찾음');
                rows
                callback(null, rows);
            } else {
                console.log('라이센스 찾지 못함');
                callback(null, null);
            }

        });
    });


}
user.reqLicense = function (req, res) {
    console.log('/getLicense 라우팅 호출');
    var paramX = 1; //req.body.Name || req.query.Name;
    var paramY = 1;
    //var paramName = req.body.Name || req.query.Name;
    console.log('요청파라미터 : ');

    reqLicense(paramX, paramY, function (err, rows) {
        if (err) {
            console.log('에러발생')
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러 발생</h1>');
            res.end();
            return;
        }
        if (rows) {
            console.dir(rows);


            var result = JSON.stringify(rows);


            console.dir(result);
            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };



            return;
        }
    });
}

var test = function (myId, callback) {
    console.log('test함수 호출됨');

    database.pool.getConnection(function (err, conn) {
        if (err) {
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);
        var exec = conn.query("select * from Customer where id = ?", [myId],
            function (err, rows) {
                conn.release();
                console.log('실행된 sql : ' + exec.sql);
                if (err) {
                    console.log('sql 실행 시 에러 발생');
                    console.dir(err);
                    callback(err, null);
                    return;
                }
                if (rows.length > 0) {
                    console.log('사용자 찾음');
                    rows
                    callback(null, rows);
                } else {
                    console.log('사용자 찾지 못함');
                    callback(null, null);
                }

            });

    });
}
user.test = function (req, res) {
    console.log('test 라우팅 호출');

    test("lgu4821", function (err, rows) {
        if (err) {
            console.log('에러발생')
            res.writeHead(200, {
                "Content-Type": "text/html;charset=utf8"
            });
            res.write('<h1>에러 발생</h1>');
            res.end();
            return;
        }
        if (rows) {
            console.dir(rows);
            var result = JSON.stringify(rows);


            console.dir(result);
            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };

            return;
        }
    });
}









var image = function (MenuNo, callback) {
    console.log('image 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query("SELECT Image FROM menu WHERE menuNo =?", 1, function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.image = function (req, res) {
    console.log('/image 라우팅 호출');
    //var parammenuNo = req.body.menuNo || req.query.menuNo;

    console.log('요청파라미터 : ');
    image(1, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            var obj = rows;
            console.log(obj[0].Image)

            var pathImage = path.join('./public/Images', rows[0].Image);
            fs.readFile(pathImage, function (err, data) {
                if (err) console.dir(err);
                if (data) {
                    res.writeHead(200, {
                        "Context-Type": "image/jpg"
                    }); //보낼 헤더를 만듬
                    res.write(data); //본문을 만들고
                    res.end(); //클라이언트에게 응답을 전송한다
                }

            })


            return;
        }
    });
}

var cusimage = function (Id, callback) {
    console.log('image 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query("SELECT Image FROM Customer WHERE Id =?", Id, function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.Cusimage = function (req, res) {
    console.log('/"image 라우팅 호출');
    //var parammenuNo = req.body.menuNo || req.query.menuNo;

    console.log('요청파라미터 : ');
    cusimage("lgu4821", function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            var obj = rows;
            console.log(obj[0].Image)

            var pathImage = path.join('./public/Images', rows[0].Image);
            fs.readFile(pathImage, function (err, data) {
                if (err) console.dir(err);
                if (data) {
                    res.writeHead(200, {
                        "Context-Type": "image/jpg"
                    }); //보낼 헤더를 만듬
                    res.write(data); //본문을 만들고
                    res.end(); //클라이언트에게 응답을 전송한다
                }

            })


            return;
        }
    });
}


var licenseImage = function (LicenseNo, callback) {
    console.log('라이센스이미지부르기함수');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log(LicenseNo);

        var exec = conn.query("SELECT Image FROM License where LicenseNo = ?", [LicenseNo], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.Licenseimage = function (req, res) {
    console.log('라이센스이미지라우팅');
    var paramLicenseNo = req.body.LicenseNo || req.query.LicenseNo || req.params.LicenseNo;
    console.log(paramLicenseNo);
    licenseImage(paramLicenseNo, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            var obj = rows;
            console.log(obj[0].Image)

            var pathImage = path.join('./public/Images', rows[0].Image);
            fs.readFile(pathImage, function (err, data) {
                if (err) console.dir(err);
                if (data) {
                    res.writeHead(200, {
                        "Context-Type": "image/jpg"
                    }); //보낼 헤더를 만듬
                    res.write(data); //본문을 만들고
                    res.end(); //클라이언트에게 응답을 전송한다
                }

            })


            return;
        }
    });
}



var sort = function (orderType, callback) {
    console.log('image 호출됨');
    var order;
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query("select * from License", function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.sort = function (req, res) {
    console.log('sort 시작출');
    var paramType = req.body.orderType || req.query.orderType;

    console.log('요청파라미터 : ' + paramType);
    sort(paramType, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            var result = JSON.stringify(rows);


            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };

            return;

        }
    });
}

var getRes = function (callback) {
    console.log('image 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query("select * from License", function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.getRes = function (req, res) {
    console.log('get 시작출');

    getRes(function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);
            var result = JSON.stringify(rows);


            console.dir(result);
            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };

            return;

        }
    });
}


var api = function (name, type, addr, x, y, callback) {
    console.log('api를 통해 db업데이트하는 함수');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query("update License set Name = ?, type= ?, address = ?, LicenseX=?, LicenseY = ? ", [name, type, addr, x, y], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}


user.api = function (req, res) {
    console.log('api호출하여 업데이트');
    request('https://openapi.gg.go.kr/Genrestrtlunch?KEY=41c752ab2353447084d1a529476eec16&TYPE=json&SIGUN_NM=%ED%99%94%EC%84%B1%EC%8B%9C', function (err, res, body) {

        var jsonObj = res.body;
        console.log(jsonObj[0])
    });


}

//        
//        api(paramName,paramtype,paramAddr,paramX,paramY,function(err,rows){
//            if(err){
//                console.log('에러발생');
//                console.dir(err);
//
//                return;
//            }
//        if(rows){
//                           console.dir(rows);
//                            var result = JSON.stringify(rows);
//                   
//                
//                console.dir(result);
//                        try{
//                       
//                        res.status(200).json(rows);
//                            console.log('에러없음');
//                            console.log('200코드 보냄');
//                       }
//                        catch(e){console.log(e.message)
//                                console.log('에러있음')};
//
//                return;
//                
//                }
//        });
//
//}

var MenuImage = function (MenuNo, callback) {
    console.log('라이센스이미지부르기함수');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }


        var exec = conn.query("SELECT Image FROM menu where MenuNo = ?", [MenuNo], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.MenuImage = function (req, res) {
    console.log('메뉴이미지라우팅');
    var paramMenuNo = req.body.MenuNo || req.query.MenuNo || req.params.MenuNo;
    console.log(paramMenuNo);
    MenuImage(paramMenuNo, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            var obj = rows;
            console.log(obj[0].Image)

            var pathImage = path.join('./public/Images', rows[0].Image);
            fs.readFile(pathImage, function (err, data) {
                if (err) console.dir(err);
                if (data) {
                    res.writeHead(200, {
                        "Context-Type": "image/jpg"
                    }); //보낼 헤더를 만듬
                    res.write(data); //본문을 만들고
                    res.end(); //클라이언트에게 응답을 전송한다
                }

            })


            return;
        }
    });
}

var giveCoupon = function (mac, id, callback) {
    console.log('여기까지는??');
    console.log(mac + ',' + id)
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);
        //insert into CRC values (1,(select couponNo from Coupon where fk_LicenseNo_coupon = 1),1)
        var exec = conn.query("insert into CRC values ((select CustomerNo from Customer where id = ?),(select couponNo from Coupon where fk_LicenseNo_coupon = (select LicenseNo from License where Mac = ?)),(select couponDate from Coupon where fk_LicenseNo_coupon=(select LicenseNo from License where Mac = ?)),null)", [id, mac,mac], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                conn.query("update Customer set Flag=0 where id= ?",[id])
                conn.release();
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.giveCoupon = function (req, res) {
    console.log('여기까지오니?');
    var paramMac = req.params.mac || req.query.mac;
    var paramId = req.params.id || req.query.id;

    console.log(paramMac);
    console.log(paramId);
    giveCoupon(paramMac, paramId, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);
            var result = JSON.stringify(rows);
            console.dir(result);
            res.status(200);

            return;

        }
    });
}
var reqCoupon = function (Id, callback) {

    console.log(Id)
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);
        //insert into CRC values (1,(select couponNo from Coupon where fk_LicenseNo_coupon = 1),1)
        var exec = conn.query("select * from Coupon where couponNo in (select couponNo from CRC where fk_CustomerNo =(select CustomerNo from Customer where Id = ?))",[Id], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
//                for(val i=0;i<rows.length;i++){
//                    conn.query("select * from ")
//                }
                
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.reqCoupon = function (req, res) {
    console.log('가지고있는쿠폰이 뭔지 요청들어옵니다');
    var paramId = req.params.Id || req.query.Id;
    console.log(paramId)
    reqCoupon(paramId, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);
            var result = JSON.stringify(rows);


            console.dir(result);
            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };

            return;

        }
    });
}

var reqCouponPlace = function (LicenseNo, callback) {

    console.log(LicenseNo)
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);
        //insert into CRC values (1,(select couponNo from Coupon where fk_LicenseNo_coupon = 1),1)
        var exec = conn.query("select LicenseX, LicenseY from License where LicenseNo = ?",[LicenseNo], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.reqCouponPlace = function (req, res) {
    console.log('가지고있는쿠폰이 뭔지 요청들어옵니다');
    var paramLicenseNo = req.body.LicenseNo || req.query.LicenseNo || req.params.LicenseNo;
    console.log(paramLicenseNo)
    reqCouponPlace(paramLicenseNo, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);
            var result = JSON.stringify(rows);


            console.dir(result);
            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };

            return;

        }
    });
}



user.CouponImage = function (req, res) {
    console.log('메뉴이미지라우팅');
    var paramImage = req.body.Image || req.query.Image || req.params.Image;
    console.log(paramImage);

            var pathImage = path.join('./public/Images', paramImage);
            fs.readFile(pathImage, function (err, data) {
                if (err) console.dir(err);
                if (data) {
                    res.writeHead(200, {
                        "Context-Type": "image/jpg"
                    }); //보낼 헤더를 만듬
                    res.write(data); //본문을 만들고
                    res.end(); //클라이언트에게 응답을 전송한다
                }

            })


            return;
        }
var reqFiltering = function (orderType, callback) {
    console.log('reqFiltering');
    var type;
    console.log(orderType)
//    switch(orderType){
//        case 0:
//            type = 'review';
//            break;
//            
//        case 1:
//            type = 'LicenseNo';
//            break;
//            
//        case 2:
//            type = 'LicenseNo';
//            break;
//            
//        case 3:
//            type = 'Name';
//            break;
//    }
//    console.log(type)
    
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);
        
        var exec = conn.query("select * from License order by Name", function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.reqFiltering = function (req, res) {
    console.log('필터호출');
    var paramType = req.body.selectedItem || req.query.selectedItem;

    console.log('요청파라미터 : ' + paramType);
    reqFiltering(paramType, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            var result = JSON.stringify(rows);

            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };

            return;

        }
    });
}
var compare = function (name, callback) {
    console.log('비교 호출됨');
    var order;
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query("select * from License where Name = ?", [name],function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null,null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows,null);



            } else {
                console.log('사용자 찾지 못함'); 
                callback(null, null,1);

            }
        });
    });
}

user.compareRestaurant = function (req, res) {
    console.log('비교 시작출');
    var paramName = req.body.Name || req.query.Name;

    console.log('요청파라미터 : ' + paramName);
    compare(paramName, function (err, rows,code) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            var result = JSON.stringify(rows);


            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };

            return;

        }
        if(code){
            res.status(555).send("1");
            console.log('1보냄');
            return;
        }
    });
}

var alertCoupon = function (Id, callback) {

    console.log(Id)
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);
        //insert into CRC values (1,(select couponNo from Coupon where fk_LicenseNo_coupon = 1),1)
        var exec = conn.query("select * from Coupon where couponNo in (select couponNo from CRC where fk_CustomerNo =(select CustomerNo from Customer where Id = ?))",[Id], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.alertCoupon = function (req, res) {
    console.log('매장에 도차쿠했어요!');
    var paramMac = req.params.mac  || req.query.mac;
    var paramId = req.params.id || req.query.id;
    console.log(paramId, paramMac)
    alertCoupon(paramId, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);
            var result = JSON.stringify(rows);


            console.dir(result);
            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };

            return;

        }
    });
}

var getMember = function (Email,callback) {
    console.log('getMember 호출됨');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var exec = conn.query("select * from Customer where Email = ?",[Email], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                console.log('사용자 찾음');
                callback(null, rows);



            } else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
}

user.getMember = function (req, res) {
    console.log('get 시작출');
    var paramEmail = req.query.Email || req.params.Email || req.body.Email;
    
    getMember(paramEmail,function(err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir(rows);
            var result = JSON.stringify(rows);


            console.dir(result);
            try {

                res.status(200).json(rows);
                console.log('에러없음');
                console.log('200코드 보냄');
            } catch (e) {
                console.log(e.message)
                console.log('에러있음')
            };

            return;

        }
    });
}

var shake= function(CustomerNo, callback) {
    console.log('Shake 메소드 호출');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'Customer';
        var exec = conn.query("update ?? set shakeFlag=? where CustomerNo =?", [tablename, 1,CustomerNo], function (err, rows) {
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                
                callback(null,1);
                }



             else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
};
user.shake = function (req, res) {
    console.log('shake 호출');
    var paramCustomerNo = req.body.cusNum || req.query.cusNum;
    console.log('요청파라미터 : ' + paramCustomerNo);
    shake(paramCustomerNo, function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            console.dir('쉐이큭!');
             res.send("1");

        }

        });
};

var confirmShake= function(callback) {
    console.log('Shake 확인ㄱㄱ');
    database.pool.getConnection(function (err, conn) {
        if (err) {
            console.log('무슨에러 ?')
            console.dir(err)
            if (conn) {
                conn.release();
            }
            callback(err, null);
            return;
        }
        console.log('데이터베이스 연결의 스레드 아이디 : ' + conn.thradId);

        var tablename = 'Customer';
        var exec = conn.query("select CustomerNo from ?? where shakeFlag=?", [tablename,1], function (err, rows) {
//            conn.query("update ?? set shakeFlag=?  where shakeFlag=?", [tablename,0,1]);
            conn.release();
            console.log('실행된 sql : ' + exec.sql);

            if (err) {
                console.dir(err);
                console.log('sql 실행시 에러 발생');
                callback(err, null);
                return;
            }
            if (rows.length > 0) {
                
                callback(null,rows);
                }



             else {
                console.log('사용자 찾지 못함');
                callback(null, null);

            }
        });
    });
};
user.confirmShake = function (req, res) {
    console.log('shake확인 호출');
    confirmShake(function (err, rows) {
        if (err) {
            console.log('에러발생');
            console.dir(err);

            return;
        }
        if (rows) {
            var result = JSON.stringify(rows);
            console.log(result);
            
            res.status(200).json(rows);
            
        }

        });
};

module.exports = user;
