MyApp
    .controller('notifyController',['$scope', '$interval','notifyService','checkService','commonService', function ($scope,$interval,notifyService,checkService,commonService) {
        //get username from localStorage
        $scope.pageClass = 'notify';
        $scope.name = localStorage.getItem("data");
        $scope.notifyPageCount = 0;


        $scope.getOneUserNotifyData = function () {
            notifyService.getNotifyDataForUser($scope.name).then(
                function (response) {
                    $scope.notifyInfoListSort = response.notifyInfoList.sort(checkService.compare("notifyStatus"));
                    $scope.notifyInfoSliceList = commonService.sliceArr($scope.notifyInfoListSort,6);
                    $scope.notifyInfoList = $scope.notifyInfoSliceList[$scope.notifyPageCount];
                }
            )
        };
        $scope.getOneUserNotifyData();

        $scope.notifyNextPage = function(){
            if($scope.notifyPageCount >= $scope.notifyInfoSliceList.length -1){
                alert("this is the last page");
            }else {
                $scope.notifyPageCount +=1;
                $scope.notifyInfoList = $scope.notifyInfoSliceList[$scope.notifyPageCount];
            }
        };
        $scope.notifyPrePage = function(){
            if($scope.notifyPageCount === 0){
                alert("this is the first page");
            }else {
                $scope.notifyPageCount -=1;
                $scope.notifyInfoList = $scope.notifyInfoSliceList[$scope.notifyPageCount];
            }
        };


        $scope.deleteNotifyInfo = function (notifyId) {
            var requestData = notifyId;
            notifyService.changeNotifyData(requestData,3).then(
                function (response) {
                    alert("delete success");
                    $scope.getOneUserNotifyData();
                }
            )
        };

        $scope.getNotifyDetail = function (notifyId) {
            var requestData = notifyId;
            notifyService.getOneNotifyData(requestData).then(
                function (response) {
                    notifyService.changeNotifyData(requestData,2);
                    $scope.notifyInfo = response.notifyInfo;
                    $scope.getOneUserNotifyData();
                }
            )
        }

        $scope.getReadNotifyList = function () {
            notifyService.getNotifyDataForUser($scope.name).then(
                function (response) {
                    $scope.notifyInfoListMap = [];
                    $scope.notifyInfoList =  [];
                    $scope.notifyPageCount = 0;
                    for(var i = 0 ; i<response.notifyInfoList.length ; i++){
                        if(response.notifyInfoList[i].notifyStatus === 2){
                            $scope.notifyInfoListMap.push(response.notifyInfoList[i]) ;
                            $scope.notifyInfoSliceList = commonService.sliceArr($scope.notifyInfoListMap,6)
                            $scope.notifyInfoList = $scope.notifyInfoSliceList[$scope.notifyPageCount];
                        }
                    }
                }
            );
        };

        $scope.getNotReadNotifyList = function () {
            notifyService.getNotifyDataForUser($scope.name).then(
                function (response) {
                    $scope.notifyInfoListMap = [];
                    $scope.notifyInfoList =  [];
                    $scope.notifyPageCount = 0;
                    for(var i = 0 ; i<response.notifyInfoList.length ; i++){
                        if(response.notifyInfoList[i].notifyStatus === 1){
                            $scope.notifyInfoListMap.push(response.notifyInfoList[i]) ;
                            $scope.notifyInfoSliceList = commonService.sliceArr($scope.notifyInfoListMap,6)
                            $scope.notifyInfoList = $scope.notifyInfoSliceList[$scope.notifyPageCount];
                        }
                    }
                }
            );
        };


    }
    ]);

