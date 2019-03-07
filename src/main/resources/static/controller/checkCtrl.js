MyApp
    .controller('checkController',['$scope', '$interval','notifyService', 'checkService', 'commonService',
        function ($scope,$interval,notifyService, checkService,commonService) {
        //get username from localStorage
        $scope.pageClass = 'check';
        $scope.name = localStorage.getItem("data");
        commonService.validateLogin($scope.name);

        $scope.getOnePfPjInfoListData = function (name){
            checkService.getOnePfPjInfoListData(name).then(
                function (response) {
                    $scope.pjInfoList = response.onePfPjInfoList.sort(checkService.compare("pjStatus"));
                }
            );
        };
        $scope.getOnePfPjInfoListData($scope.name);

       $scope.download = function (pjId, pjName) {
           checkService.downloadPj(pjId).then(
               function (response) {
                   var link = document.createElement('a');
                   var blob = new Blob([response], {type: 'application/zip'});
                   link.setAttribute('href', window.URL.createObjectURL(blob));
                   link.setAttribute('download', pjName );
                   link.style.visibility = 'hidden';
                   document.body.appendChild(link);
                   link.click();
                   document.body.removeChild(link);
               }
           )
       };

       $scope.getUserDetail = function (userName) {
           checkService.getUserInfoData(userName).then(
               function (response) {
                   $scope.userInfo = response.userInfo;
               }
           )
       };

        $scope.editPjStatus = function (pjId) {
            checkService.getOnePjInfoData(pjId).then(
                function (response) {
                    $scope.onePjInfo = response.projectInfo;
                    $scope.selectStatusData = checkService.getStatusData();
                    if($scope.onePjInfo.pjStatus === 1){
                        $scope.pjStatusModel = "Review";
                        $scope.selectedStatusCode = $scope.onePjInfo.pjStatus;
                    }else if($scope.onePjInfo.pjStatus === 2){
                        $scope.pjStatusModel = "Pass";
                        $scope.selectedStatusCode = $scope.onePjInfo.pjStatus;
                    }else if($scope.onePjInfo.pjStatus === 3){
                        $scope.pjStatusModel = "Reject";
                        $scope.selectedStatusCode = $scope.onePjInfo.pjStatus;
                    }else if($scope.onePjInfo.pjStatus === 4){
                        $scope.pjStatusModel = "Finished";
                        $scope.selectedStatusCode = $scope.onePjInfo.pjStatus;
                    }else if($scope.onePjInfo.pjStatus === 5){
                        $scope.pjStatusModel = "Break";
                        $scope.selectedStatusCode = $scope.onePjInfo.pjStatus;
                    }
                }
            )
        };

        $scope.getPjInfoModelData = function(PjId) {
            checkService.getOnePjInfoData(PjId).then(
                function (response) {
                    $scope.pjInfoModel = response.projectInfo;
                }
            )
        };

        $scope.changePjStatus = function (pjId,pjStatus,pjUser,pjName) {
            checkService.changeProjectStatus(pjId,pjStatus).then(
                function (response) {
                    $scope.getOnePfPjInfoListData($scope.name);
                    var pjNameSplit = pjName.split(".")[0];
                    notifyService.setNotify($scope.name,pjUser,"Project",pjStatus,pjNameSplit);
                }
            )
        };
    }
    ]);

