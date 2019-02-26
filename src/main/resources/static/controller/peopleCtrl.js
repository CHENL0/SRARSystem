MyApp
    .controller('peopleController',['$scope', '$interval', 'commonService',
        'taskService','notifyService', 'pjTypeService','pjDetailService','peopleService',
        function ($scope,$interval, commonService,taskService,notifyService,pjTypeService,pjDetailService,peopleService) {
        //get username from localStorage
        $scope.pageClass = 'people';
        $scope.username = localStorage.getItem("data");
        $scope.pagaType = 'user';
        $scope.userPageCount = 0;
        $scope.pfPageCount = 0;
        $scope.getAllUserForShowing = function () {
            peopleService.getAllUserInfo().then(
                function (value) {
                    $scope.userInfoListSliceList = commonService.sliceArr(value.userInfoList,6);
                    $scope.userInfoList = $scope.userInfoListSliceList[$scope.userPageCount];
                }
            )
        }; $scope.getAllUserForShowing();

            $scope.getAllPfForShowing = function () {
                peopleService.getAllPfInfo().then(
                    function (value) {
                        $scope.pfInfoListSliceList = commonService.sliceArr(value.pfInfoList,6);
                        $scope.pfInfoList = $scope.pfInfoListSliceList[$scope.pfPageCount];
                    }
                )
            };



            $scope.changeType = function (type){
                $scope.pagaType = type;
                if(type === "user"){
                    $scope.userPageCount = 0;
                    $scope.getAllUserForShowing();
                }else if(type === "professor"){
                    $scope.pfPageCount = 0;
                    $scope.getAllPfForShowing();
                }

            }

        $scope.getUserInfoModelData = function (userName) {
            pjDetailService.getUserInfoData(userName).then(
                function (value) {
                    $scope.userInfoModel = value.userInfo;
                }
            )
        };

            $scope.getPfInfoModelData = function (pfName) {
                pjDetailService.getPfInfoData(pfName).then(
                    function (value) {
                        $scope.pfInfoModel = value.professorInfo;
                    }
                )
            };
        $scope.editDelFlag = function (Id,status,type) {
            if(status === 'freeze'){
                status = 1;
            }else {
                status = 0;
            }
            if(type === 'pf'){
                peopleService.updateDelFlagForPf(Id,status).then(
                    function (value) {
                        $scope.getAllPfForShowing();
                        alert("change success");
                    }
                )
            }else if(type === 'user'){
                peopleService.updateDelFlagForUser(Id,status).then(
                    function (value) {
                        $scope.getAllUserForShowing();
                        alert("change success");
                    }
                )
            }
        };

            $scope.userNextPage = function(){
                if($scope.userPageCount >= $scope.userInfoListSliceList.length -1){
                    alert("this is the last page");
                }else {
                    $scope.userPageCount +=1;
                    $scope.userInfoList = $scope.userInfoListSliceList[$scope.userPageCount];
                }
            };
            $scope.userPrePage = function(){
                if($scope.userPageCount === 0){
                    alert("this is the first page");
                }else {
                    $scope.userPageCount -=1;
                    $scope.userInfoList = $scope.userInfoListSliceList[$scope.userPageCount];
                }
            };

            $scope.pfNextPage = function(){
                if($scope.pfPageCount >= $scope.pfInfoListSliceList.length -1){
                    alert("this is the last page");
                }else {
                    $scope.pfPageCount +=1;
                    $scope.pfInfoList = $scope.pfInfoListSliceList[$scope.pfPageCount];
                }
            };
            $scope.pfPrePage = function(){
                if($scope.pfPageCount === 0){
                    alert("this is the first page");
                }else {
                    $scope.pfPageCount -=1;
                    $scope.pfInfoList = $scope.pfInfoListSliceList[$scope.pfPageCount];
                }
            };
    }
    ]);

