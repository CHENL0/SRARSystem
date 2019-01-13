PjDetailApp
    .controller('pjDetailController',['$scope', '$interval', 'pjDetailService', function ($scope,$interval, pjDetailService) {
        //get username from localStorage
        $scope.index = 0;
        $scope.username = localStorage.getItem("data");
        $scope.pageMap = [1,2,3,4,5];
        $scope.activeCode = 1;
        pjDetailService.getPjInfoListData().then(
            function (response) {
                $scope.pjInfoList = response.pjInfoList;
            });
        pjDetailService.getPfInfoListData("基础研究",0).then(
            function (response) {
                $scope.pfInfoListPage = response.pfInfoListPage.content;
            }
        );
        var pfType = "基础研究"
        pjDetailService.getOnePfTypeCount(pfType).then(
            function (response) {
                var ceil = Math.ceil(response/5);
                if(ceil =5){
                    $scope.pageMap = [1,2,3,4,5];
                    $scope.showNext = false;
                }else if(ceil>5){
                    $scope.pageMap = [1,2,3,4,5];
                    $scope.showNext = true;
                }else {
                    for(var i = 0;i<ceil;i++){
                        $scope.pageMap[i] = i+1;
                    }
                }
            }
        );


        $scope.refreshActiveCode = function (){
            $scope.activeCode = 1;
        }

        $scope.changeActive = function(page){
          $scope.activeCode = page;
        };



        $scope.getPreviousPage = function(){
          var min_page = $scope.pageMap[0];
          for(var i = 0;i<5;i++){
             $scope.pageMap[i] = $scope.pageMap[i] - 5;
          }
          return $scope.pageMap;
        };

        $scope.getNextPage = function(){
            var max_page = $scope.pageMap[4];
            pjDetailService.getOnePfTypeCount().then(
                function (response) {
                   var ceil = Math.ceil(response/5);
                   if(ceil > 5){
                       var residue = ceil - max_page;
                       $scope.pageMap = [];
                       for(var i = 0;i<residue;i++){
                           max_page += 1;
                           $scope.pageMap[i] = max_page;
                       }
                   }
                }
            );
            $scope.pageMap = [];
            for(var i = 0;i<5;i++){
                max_page += 1;
                $scope.pageMap[i] = max_page;
            }
            return $scope.pageMap;
        };


        $scope.showPjTypeDetail = function (pjTypeId) {
            $scope.index = pjTypeId;
        };

        $scope.getPfList = function (pfType,page) {
           pjDetailService.getPfInfoListData(pfType,page).then(
               function (response) {
                   $scope.pfInfoListPage = response.pfInfoListPage.content;
                   $scope.currentType = pfType;
               }
           )
        };


    }
    ]);

