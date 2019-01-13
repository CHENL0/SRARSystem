loginSignApp
    .controller('loginSignController',['$scope','$interval','$http', function ($scope,$interval,$http) {
        var InterValObj; //timer变量，控制时间
        var count = 60; //间隔函数，1秒执行
        var curCount;//当前剩余秒数
        var code = ""; //验证码
        var codeLength = 6;//验证码长度

        $scope.trim = function (str) {
            var strnew=str.replace(/^\s*|\s*$/g, "");
            return strnew;
        }

       $scope.getCode = function () {
           var $ctrl =this;
           curCount = count;
           var jbPhone = $scope.userPhone;
           var jbPhoneTip = $scope.phoneError;
           if (jbPhone != "") {
               if(jbPhoneTip == "√" ){
                   // 产生验证码
                   for ( var i = 0; i < codeLength; i++) {
                       code += parseInt(Math.random() * 9).toString();
                   }
                   // 设置button效果，开始计时
                   $("#btnSendCode").attr("disabled", "true");
                   $("#btnSendCode").val("    "+curCount + "秒后重新获取    ");
                   var timer = $interval(function(){
                       var $ctrl = this;
                       $scope.SetRemainTime();
                   },1000,60);
                   InterValObj = window.setInterval($ctrl.SetRemainTime(), 1000); // 启动计时器，1秒执行一次

                   // 向后台发送处理数据
                   $http({
                       method: 'POST',
                       url: 'http://localhost:8080/getCode',
                       data: {
                           registerPhone : jbPhone
                       },
                       headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                       transformRequest: function(obj) {
                           var str = [];
                           for (var s in obj) {
                               str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                           }
                           return str.join("&");
                       }
                   }).then(function successCallback(response) {
                   }, function errorCallback(response) {
                       // 请求失败执行代码
                   });
               }
           }
       };

       $scope.SetRemainTime = function () {
           if (curCount == 0) {
               window.clearInterval(InterValObj);// 停止计时器
               $("#btnSendCode").removeAttr("disabled");// 启用按钮
               $("#btnSendCode").val("重新发送验证码");
               code = ""; // 清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
           }else {
               curCount--;
               $("#btnSendCode").val("    "+curCount + "秒后重新获取    ");
           }
       };

        $scope.validatePw = function () {
            var firstPassword = $scope.password;
            if(!firstPassword){
                $scope.pswError = "the  password should no be null";
            }else{
                $scope.pswError = "√";
            }
        };

       $scope.validateRPw = function () {
            var firstPassword = $scope.password;
            var secondPassword = $scope.password2;
            if(!secondPassword){
                $scope.pswError2 = "the repeat password should no be null";
            }else if(firstPassword === secondPassword){
                $scope.pswError2 = "√";
            }else{
                $scope.pswError2 = "The two passwords don't match";
            }
        };
       
       $scope.validatePh = function () {
           var userPhone = $scope.userPhone;
           var triUserPhone = $scope.trim(userPhone);
           var re= /^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\d{8}$/;
           if(!triUserPhone){
               $scope.phoneError = "the phone should not be null";
               return;
           }else if(!re.test(triUserPhone)){
               $scope.phoneError = "Phone number format is wrong";
           }else{
           // 向后台发送处理数据
               var callhttp = $http({
                   method: 'POST',
                   url: 'http://localhost:8080/verifyPhone',
                   data: {
                       registerPhone : triUserPhone
                   },
                   headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                   transformRequest: function(obj) {
                       var str = [];
                       for (var s in obj) {
                           str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                       }
                       return str.join("&");
                   }
               }).then(function successCallback(response) {
                   if(response.data){
                       $scope.phoneError = "the phone has already been registered";
                   }else{
                       $scope.phoneError = "√";
                   }
               }, function errorCallback(response) {
                   // 请求失败执行代码
               });
           }
       }

        $scope.validateCode = function (){
           var code = $scope.code;
           if(code){
               var triCode = $scope.trim(code);
           }
           if(!triCode){
               $scope.codeError = "the code should no be null"
           }else{
               // 向后台发送处理数据
               var callhttp = $http({
                   method: 'POST',
                   url: 'http://localhost:8080/verifyCode',
                   data: {
                       code : triCode,
                   },
                   headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                   transformRequest: function(obj) {
                       var str = [];
                       for (var s in obj) {
                           str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                       }
                       return str.join("&");
                   }
               }).then(function successCallback(response) {
                   if(response.data){
                       $scope.codeError = "√";
                   }else{
                       $scope.codeError = "the code is not correct";
                   }
               }, function errorCallback(response) {
                   // 请求失败执行代码
               });
           }
        }
        
        $scope.signUp = function () {
            if($scope.pswError2 === "√" && $scope.pswError === "√"
                && $scope.phoneError === "√" && $scope.codeError === "√"
                && $scope.ansError === "√" && $scope.queError === "√"){
                var callhttp = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/register',
                    data: {
                        registerPhone : $scope.trim($scope.userPhone),
                        registerPassword : $scope.trim($scope.password),
                        urSecurityQuestion : $scope.question,
                        urSecurityAnswer : $scope.answer,
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                }).then(function successCallback(response) {
                    console.log(response.data);
                    if(response.data){
                        alert("Your username is '"+response.data.userName +"',and you sholud be remember it");
                        $scope.cleanAllMsg();
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }else {
                alert("You should complete your information!!");
            }
        };

       $scope.validateQue = function () {
           if(!$scope.question){
               $scope.queError = "the question should no be null";
           }else {
               $scope.queError = "√";
           }
       };

        $scope.validateAns = function () {
            if(!$scope.answer){
                $scope.ansError = "the question should no be null";
            }else {
                $scope.ansError = "√";
            }
        }

        $scope.cleanAllMsg = function () {
            $scope.ansError = "";
            $scope.queError = "";
            $scope.pswError2 = "";
            $scope.pswError = "";
            $scope.phoneError = "";
            $scope.codeError = "";
            $scope.userPhone = "";
            $scope.password = "";
            $scope.password2 = "";
            $scope.question = "";
            $scope.answer = "";
            $scope.code = "";
            $scope.codeError = "";
            $scope.usernameError = "";
            $scope.username= "";
            $scope.forAnswer= "";
            $scope.forQuestion= "";
        };

        $scope.validateUsername = function (){
            var username = $scope.username;
            if(!username){
                $scope.usernameError = "the username should no be null";
            }else {
                $scope.usernameError = "√";
            }
        };

        $scope.getQuestion = function () {
            var username = $scope.username;
            if(!username){
                $scope.usernameError = "the username should no be null";
            }else {
                $scope.usernameError = "√";
                var callhttp = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/findUrSecurity',
                    data: {
                        userName : username,
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                }).then(function successCallback(response) {
                    if(response.data){
                        $scope.forQuestion = response.data.question;
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }
        };

        $scope.validateAnswer = function () {
            var answer = $scope.forAnswer;
            var username = $scope.username;
            if(!answer){
                $scope.ansError = "the answer should no be null"
            }else {
                var callhttp = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/validateAnswer',
                    data: {
                        userName : username,
                        urSecurityAnswer: answer
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                }).then(function successCallback(response) {
                    if(response.data){
                        $scope.ansError = "√";
                    }else {
                        $scope.ansError = "your answer is wrong";
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }
        }

        $scope.updatePassword = function () {
            if($scope.usernameError === "√" && $scope.ansError === "√"
                && $scope.pswError === "√" && $scope.pswError2 === "√"){
                var callhttp = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/updatePassword',
                    data: {
                        userName : $scope.username,
                        urSecurityAnswer : $scope.forAnswer,
                        newUserPassword : $scope.password
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                }).then(function successCallback(response) {
                    console.log(response.data);
                    if(response.data){
                        alert("Your password Has been updated,and you should be remember it");
                        $scope.cleanAllMsg();
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }else {
                alert("You should complete your information!!");
            }
        };

        $scope.validataPassword = function (){
            var password = $scope.password;
            if(!password){
                $scope.pswError = "the password should not be null";
            }else {
                $scope.pswError = "√";
            }
        };

        $scope.login = function () {
            if($scope.usernameError === "√" && $scope.pswError === "√" ){
                var callhttp = $http({
                    method: 'POST',
                    url: 'http://localhost:8080/login',
                    data: {
                        loginName : $scope.username,
                        loginPassword : $scope.password
                    },
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    transformRequest: function(obj) {
                        var str = [];
                        for (var s in obj) {
                            str.push(encodeURIComponent(s) + "=" + encodeURIComponent(obj[s]));
                        }
                        return str.join("&");
                    }
                }).then(function successCallback(response) {
                    console.log(response.data);
                    if(response.data){
                        localStorage.setItem("data",$scope.username);
                        window.location.href = "index.html";
                        $scope.cleanAllMsg();
                    }else {
                        alert("Your username or password is wrong");
                    }
                }, function errorCallback(response) {
                    // 请求失败执行代码
                });
            }else {
                alert("You should complete your information!!");
            }
        }



    }
    ]);

