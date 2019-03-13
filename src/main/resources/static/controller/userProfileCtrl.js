MyApp
    .controller('userProfileController',['$scope', '$interval', 'userProfileService',
        'checkService','$sce','$compile','commonService',
        function ($scope,$interval, userProfileService,checkService,$sce,$compile,commonService) {
        //get username from localStorage
        $scope.pageClass = 'userProfile';
        $scope.username = localStorage.getItem("data");
            commonService.validateLogin($scope.username);
        $scope.disabledInput = true;
        // $scope.disabledSubmit = true;
        $scope.iconFromatError = false;
        $scope.buttonValue = "modify";
        // $scope.checkAgree = false;
        // $scope.checkAgreeForPf = false
        $scope.checkMale = true;
        $scope.iconFile = '';
        $scope.userProfileData = userProfileService.getUserProfileData();
        $scope.showRole = "";
        $scope.prefix = $scope.name.split("_")[0];
        $scope.ifShowChooseAddress = false;
        // address
        $scope.pUser = '';
        $scope.cUser = '';
        $scope.aUser = '';
        $scope.dUser = '';

        $scope.pPf = '';
        $scope.cPf = '';
        $scope.aPf = '';
        $scope.dPf = '';

        $scope.changeShowApply = function () {
            window.location.href = "index.html#/apply";
        };

        $scope.changeShowAddress = function (){
            if($scope.prefix ==="USER"){
                if(!$scope.ifShowChooseAddress){
                    $scope.ifShowChooseAddress = true;
                    var html = '<input id="selectAdd" select-address p="pUser" c="cUser" a="aUser" d="dUser" ng-model="userInfo.userAddress" \n' +
                        '            placeholder="请选择所在地" type="text" class="form-control" />';
                    var ele = $compile(html)($scope);
                    angular.element('.col-sm-6').append(ele);
                }else {
                    $scope.ifShowChooseAddress = false;
                    $('#selectAdd').remove();
                }
            }else if($scope.prefix ==="PROFESSOR"){
                if(!$scope.ifShowChooseAddress){
                    $scope.ifShowChooseAddress = true;
                    var html = '<input id="selectAdd" select-address p="pPf" c="cPf" a="aPf" d="dPf" ng-model="professorInfo.pfAddress" \n' +
                        '            placeholder="请选择所在地" type="text" class="form-control" />';
                    var ele = $compile(html)($scope);
                    angular.element('.col-sm-6').append(ele);
                }else {
                    $scope.ifShowChooseAddress = false;
                    $('#selectAdd').remove();
                }
            }

        };

        $scope.changeShow = function () {
            if($scope.prefix === "USER") {
                $scope.showRole = "user";
            }else if($scope.prefix === "PROFESSOR"){
                $scope.showRole = "professor";
            }
        };
        $scope.changeShow();


        $scope.getProfileData = function () {
            if($scope.prefix === "USER"){
                userProfileService.getUserInfoData($scope.username).then(
                    function (response) {
                        $scope.userInfo =  response.userInfo;
                        if($scope.userInfo.userGender === "Male"){
                            $scope.checkMale = true;
                        }else {
                            $scope.checkFemale = true;
                        }
                        $scope.userProfileData.userIntroduce = $scope.userInfo.userIntroduce;
                        $scope.userProfileData.userNickname = $scope.userInfo.userNickname;
                    }
                );
            }else if($scope.prefix === "PROFESSOR"){
                userProfileService.getPfInfoData($scope.username).then(
                    function (response) {
                        $scope.professorInfo =  response.professorInfo;
                        if($scope.professorInfo.pfGender === "Male"){
                            $scope.checkMale = true;
                        }else {
                            $scope.checkFemale = true;
                        }
                        $scope.pfProfileData.pfIntroduce = $scope.professorInfo.pfIntroduce;
                        $scope.pfProfileData.pfNickname = $scope.professorInfo.pfNickname;
                    }
                );
            }
        };
        $scope.getProfileData();

        // userProfileService.getOneUserPjInfoListData($scope.username).then(
        //     function (response) {
        //         $scope.pjInfoList = response.oneUserPjInfoList.sort(checkService.compare("pjStatus"));
        //     }
        // );
        
        $scope.validateIcon = function () {
            var file = document.getElementById("fileUpload").files[0];
            $scope.file = file;
            if(!file){
                return;
            }
            var suffix = file.name.split(".")[1];
            var fileSize = file.size;
            if(suffix === "jpg" || suffix === "png" || suffix === "JPG" || suffix === "PNG"){
                $scope.iconFromatError = false;
                if(fileSize < 1000000){
                    $scope.iconSizeError = false;
                }else {
                    $scope.iconSizeError = true;
                }
            }else {
                $scope.iconFromatError = true;
            }
            $scope.$apply();
        };
        $scope.validateIconForPf = function () {
            var file = document.getElementById("fileUploadForPf").files[0];
            $scope.file = file;
            if(!file){
                return;
            }
            var suffix = file.name.split(".")[1];
            var fileSize = file.size;
            if(suffix === "jpg" || suffix === "png" || suffix === "JPG" || suffix === "PNG"){
                $scope.iconFromatError = false;
                if(fileSize < 1000000){
                    $scope.iconSizeError = false;
                }else {
                    $scope.iconSizeError = true;
                }
            }else {
                $scope.iconFromatError = true;
            }
            $scope.$apply();
        };
        $scope.modify = function () {
            if($scope.buttonValue === "modify"){
                $scope.disabledInput = false;
                $scope.buttonValue = "cancel";
            }else {
                $scope.disabledInput = true;
                $scope.buttonValue = "modify";
            }
        };

        // $scope.changeDisabledForSubmit = function () {
        //     if($scope.disabledSubmit){
        //         $scope.disabledSubmit = false;
        //     }else {
        //         $scope.disabledSubmit = true;
        //     }
        // };

        $scope.submit = function () {
            if($scope.prefix === "USER") {
                if ($scope.validateUserAllData()) {
                    $scope.userProfileData.userName = $scope.username;
                    userProfileService.submitData($scope.file, $scope.userProfileData).then(
                        function (response) {
                            if (response.responseType === "SUCCESS") {
                                alert("Congratulations, and your information submitted to success");
                                $scope.ifShowChooseAddress = false;
                                $scope.modify();
                            } else {
                                alert("sorry,your information submitted to error")
                            }
                        }
                    )
                }
            }else if($scope.prefix === "PROFESSOR"){
                if ($scope.validatePfAllData()) {
                    $scope.pfProfileData.pfName = $scope.username ;
                    userProfileService.submitPfData($scope.file, $scope.pfProfileData).then(
                        function (response) {
                            if (response.responseType === "SUCCESS") {
                                alert("Congratulations, and your information submitted to success");
                                $scope.ifShowChooseAddress = false;
                                $scope.modify();
                            } else {
                                alert("sorry,your information submitted to error")
                            }
                        }
                    )
                }
            }
            // userProfileService.test();
        };

        $scope.validateUserAllData = function () {

            // if(!document.getElementById("fileUpload").files[0]){
            //     alert("Icon message have not finish");
            //     return false;
            // }
            if($scope.ifShowChooseAddress){
                if(!$scope.pUser ||!$scope.cUser ||!$scope.dUser){
                    alert("Address message have not finish");
                    return false;
                }else {
                    var area = $scope.aUser?$scope.aUser:''
                    $scope.userProfileData.userAddress = $scope.pUser+" "+$scope.cUser+" "+area+" "+$scope.dUser;
                }
            }else if(!$scope.userInfo.userAddress){
                alert("Address message have not finish");
                return false;
            }else if($scope.userInfo.userAddress){
                $scope.userProfileData.userAddress = $scope.userInfo.userAddress;
            }
            if(!($scope.userProfileData.userNickname.trim())){
                alert("Nickname message have not finish");
                return false;
            }
            if ($scope.checkMale){
                $scope.userProfileData.userGender = "Male";
            }else {
                $scope.userProfileData.userGender = "Female";
            }
            if(!($scope.userProfileData.userIntroduce.trim())){
                alert("Introduce message have not finish");
                return false;
            }
            alert("ok ,the message have finished");
            return true;
        }

        // pf ------
        $scope.pfProfileData = userProfileService.getPfProfileData();

        $scope.validatePfAllData = function () {
            // if(!document.getElementById("fileUploadForPf").files[0]){
            //     alert("Icon message have not finish");
            //     return false;
            // }
            if($scope.ifShowChooseAddress){
                if(!$scope.pPf ||!$scope.cPf ||!$scope.dPf){
                    alert("Address message have not finish");
                    return false;
                }else {
                    var area = $scope.aPf?$scope.aPf:''
                    $scope.pfProfileData.pfAddress = $scope.pPf+" "+$scope.cPf+" "+area+" "+$scope.dPf;
                }
            }else if(!$scope.professorInfo.pfAddress){
                alert("Address message have not finish");
                return false;
            }else if($scope.professorInfo.pfAddress){
                $scope.pfProfileData.pfAddress = $scope.professorInfo.pfAddress;
            }

            if(!($scope.pfProfileData.pfNickname.trim())){
                alert("Nickname message have not finish");
                return false;
            }
            if ($scope.checkMale){
                $scope.pfProfileData.pfGender = "Male";
            }else {
                $scope.pfProfileData.pfGender = "Female";
            }
            if(!($scope.pfProfileData.pfIntroduce.trim())){
                alert("Introduce message have not finish");
                return false;
            }
            alert("ok ,the message have finished");
            return true;
        }

        // $scope.reader = new FileReader(); //创建一个FileReader接口
        // $scope.thumb; //用于存放图片的base64
        //
        // $scope.uploadFiles = function() { //单次提交图片的函数
        //     var file = $scope.file;
        //     $scope.reader.onload = function(ev) {
        //         $scope.$apply(function() {
        //             $scope.thumb = ev.target.result; //接收base64
        //         });
        //
        //         $scope.imgSrc = $scope.thumb;
        //     };
        //     $scope.reader.readAsDataURL(file); //FileReader的方法，把图片转成base64
        // };

    }
    ]);

