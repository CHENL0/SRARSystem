PjDetailApp
    .controller('pjDetailController',['$scope', '$interval', 'pjDetailService', function ($scope,$interval, pjDetailService) {
        //get username from localStorage
        $scope.index = 0;
        $scope.username = localStorage.getItem("data");

        pjDetailService.getPjInfoListData().then(
            function (response) {
                $scope.pjInfoList = response.pjInfoList;
            });

        $scope.showPjTypeDetail = function (pjTypeId) {
            $scope.index = pjTypeId;
        }
    }
    ]);

