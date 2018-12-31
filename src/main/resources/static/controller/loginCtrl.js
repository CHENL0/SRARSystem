loginSignApp
    .controller('loginSignController',['$scope','$interval','$http', function ($scope,$interval,$http) {
        var InterValObj; //timer变量，控制时间
        var count = 60; //间隔函数，1秒执行
        var curCount;//当前剩余秒数
        var code = ""; //验证码
        var codeLength = 6;//验证码长度

        $scope.signUp = function(){
            console.log($scope.username);
       };

       $scope.getCode = function () {
           var $ctrl =this;
           curCount = count;
           var jbPhone = "13242424";
           var jbPhoneTip = "√";
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
                       url: 'http://localhost:8080/loginSign/getCode',
                       data: {
                           'phone' : 13244822126
                       }
                   }).then(function successCallback(response) {
                       alert("success");
                   }, function errorCallback(response) {
                       // 请求失败执行代码
                   });
               }
           }else{
               $("#telephonenameTip").html("<font color='red'>× 手机号码不能为空  code</font>");
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
       }

    }
    ]);

