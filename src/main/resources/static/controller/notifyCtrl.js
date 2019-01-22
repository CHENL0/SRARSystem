MyApp
    .controller('notifyController',['$scope', '$interval','notifyService','checkService', function ($scope,$interval,notifyService,checkService) {
        //get username from localStorage
        $scope.pageClass = 'notify';
        $scope.name = localStorage.getItem("data");



        notifyService.getNotifyData($scope.name).then(
            function (response) {
                $scope.notifyInfoList = response.notifyInfoList.sort(checkService.compare("notifyStatus"));
            }
        );

        $scope.getOneUserNotifyData = function () {
            notifyService.getNotifyData($scope.name).then(
                function (response) {
                    $scope.notifyInfoList = response.notifyInfoList.sort(checkService.compare("notifyStatus"));
                }
            )
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
                    $scope.notifyInfo = response.notifyInfo;
                    notifyService.changeNotifyData(requestData,2)
                    $scope.getOneUserNotifyData();
                }
            )
        }

        $scope.getReadNotifyList = function () {
            notifyService.getNotifyData($scope.name).then(
                function (response) {
                    $scope.notifyInfoList = [];
                    for(var i = 0 ; i<response.notifyInfoList.length ; i++){
                        if(response.notifyInfoList[i].notifyStatus === 2){
                            $scope.notifyInfoList.push(response.notifyInfoList[i]) ;
                        }
                    }
                }
            );
        }

        $scope.getNotReadNotifyList = function () {
            notifyService.getNotifyData($scope.name).then(
                function (response) {
                    $scope.notifyInfoList = [];
                    for(var i = 0 ; i<response.notifyInfoList.length ; i++){
                        if(response.notifyInfoList[i].notifyStatus === 1){
                            $scope.notifyInfoList.push(response.notifyInfoList[i]) ;
                        }
                    }
                }
            );
        }

    }
    ]);

