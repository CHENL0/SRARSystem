UserProfileApp
    .controller('userProfileController',['$scope', '$interval', 'userProfileService', function ($scope,$interval, userProfileService) {
        //get username from localStorage
        $scope.username = localStorage.getItem("data");
        $scope.disabledInput = true;
        $scope.disabledSubmit = true;
        $scope.iconFromatError = false;
        $scope.buttonValue = "modify";
        $scope.checkMale = true;
        $scope.iconFile = '';
        $scope.userProfileData = userProfileService.getUserProfileData();
        $scope.userProfileData.userName = $scope.username;
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

        userProfileService.getOneUserPjInfoListData($scope.username).then(
            function (response) {
                $scope.pjInfoList = response.oneUserPjInfoList;
            }
        );
        
        $scope.validateIcon = function () {
            var file = document.getElementById("fileUpload").files[0];
            $scope.file = file;
            if(!file){
                return;
            }
            var suffix = file.name.split(".")[1];
            var fileSize = file.size;
            if(suffix === "jpg" || suffix === "png"){
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

        $scope.changeDisabledForSubmit = function () {
            if($scope.checkAgree){
                $scope.disabledSubmit = false;
            }else {
                $scope.disabledSubmit = true;
            }

        };

        $scope.submit = function () {
            if($scope.validateAllData()){
                userProfileService.submitData($scope.file,$scope.userProfileData).then(
                    function (response) {
                        if(response.responseType === "SUCCESS"){
                            alert("Congratulations, and your information submitted to success");
                            $scope.modify();
                        }else {
                            alert("sorry,your information submitted to error")
                        }
                    }
                )
            }
            // userProfileService.test();
        };

        $scope.validateAllData = function () {
            if(!document.getElementById("fileUpload").files[0]){
                alert("Icon message have not finish");
                return false;
            }
            if(!$scope.province ||!$scope.city){
                alert("Address message have not finish");
                return false;
            }else {
                var area = $scope.area?$scope.area:''
                $scope.userProfileData.userAddress = $scope.province+$scope.city +area;
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


    }
    ]);

