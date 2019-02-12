MyApp
    .controller('pjDetailController',['$scope', '$interval', 'pjDetailService','checkService','notifyService', function ($scope,$interval, pjDetailService,checkService,notifyService) {
        //get username from localStorage
        $scope.pageClass = 'pjDetail';
        $scope.index = 0;
        $scope.name = localStorage.getItem("data");
        $scope.prefixName = $scope.name.split("_")[0];

        $scope.activeCode = 1;
        $scope.onePfTypeAllCount = 0;
        $scope.pageMap=[1,2,3,4,5];
        $scope.currentType ="基础研究";

        //
        pjDetailService.getPjInfoListData().then(
            function (response) {
                $scope.pjInfoList = response.pjInfoList;
            });
        //
        pjDetailService.getPfInfoListData("基础研究",0).then(
            function (response) {
                $scope.pfInfoListPage = response.pfInfoListPage.content;
            }
        );

        $scope.getPersonageInfoData = function(){
            if($scope.prefixName === "USER"){
                pjDetailService.getUserInfoData($scope.name).then(
                    function (response) {
                        $scope.userInfo =  response.userInfo;
                    }
                )
            }else if($scope.prefixName === "PROFESSOR") {
                pjDetailService.getPfInfoData($scope.name).then(
                    function (response) {
                        $scope.pfInfo = response.professorInfo;
                    }
                )
            }
        };
        $scope.getPersonageInfoData();
        //
        var pfType = "基础研究"
        pjDetailService.getOnePfTypeCount(pfType).then(
            function (response) {
                $scope.onePfTypeAllCount = response;
                $scope.pageMap = [];
                var ceil = Math.ceil(response/6);
                if(ceil === 5){
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

        //
        $scope.refreshActiveCode = function (){
            $scope.activeCode = 1;
        };
        //
        $scope.changeActive = function(page){
          $scope.activeCode = page;
        };


        //
        $scope.getPreviousPage = function(){
          var min_page = $scope.pageMap[0];
            for(var i = 0;i<5;i++){
                $scope.pageMap[i] = min_page - (5- i);
            }
            if($scope.onePfTypeAllCount>30){
                $scope.showNext = true;
            }
          return $scope.pageMap;
        };

        $scope.getNextPage = function(){
            var typeAllCount = Math.ceil($scope.onePfTypeAllCount/6);
            for(var count = typeAllCount-5;count>0;count = count-5){
                var max_page = $scope.pageMap[4];
                var ceilNum = typeAllCount-max_page;
                $scope.pageMap = [];
                if(ceilNum>5){
                    for (var i = 0; i < 5; i++) {
                        max_page += 1;
                        $scope.pageMap[i] = max_page;
                        $scope.showNext = true;
                    }
                }else if(ceilNum === 5){
                    max_page += 1;
                    $scope.pageMap[i] = max_page;
                    $scope.showNext = false;
                } else {
                    for (var i = 0; i < ceilNum; i++) {
                        max_page += 1;
                        $scope.pageMap[i] = max_page;
                        $scope.showNext = false;
                    }
                }
            }
        };


        $scope.showPjTypeDetail = function (pjTypeId) {
            $scope.index = pjTypeId;
        };

        $scope.getPfList = function (pfType,page) {
           pjDetailService.getPfInfoListData(pfType,page).then(
               function (response) {
                   $scope.pfInfoListPage = response.pfInfoListPage.content;
                   $scope.currentType = pfType;
                   document.getElementById("pageAddress").scrollIntoView()
               }
           );
           if($scope.pageMap[0] === 1){
                pjDetailService.getOnePfTypeCount(pfType).then(
                    function (response) {
                        $scope.onePfTypeAllCount = response;
                        $scope.pageMap = [];
                        $scope.showNext = false;
                        var ceil = Math.ceil(response/6);
                        if(ceil === 5){
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
           }

        };

        $scope.clickDetailOfPf = function (pfName) {
            pjDetailService.getPfInfoData(pfName).then(
                function (response) {
                    $scope.pfInfoModel = response.professorInfo;
                }
            )

        };

        $scope.logout = function () {
            localStorage.clear();
            window.location.href = "loginSign.html";
        };




        // $scope.clickCheck = function () {
        //
        // };
        // var timer = $interval(function(){
        //     var $ctrl = this;
        //     $scope.getCheckCount();
        // },10000);



        // $scope.getCheckCount = function () {
        //     checkService.getOnePfPjInfoListData($scope.name).then(
        //         function (value) {
        //             var onePfPjInfoList = value.onePfPjInfoList;
        //             $scope.checkCount = 0;
        //             for(var i =0;i<onePfPjInfoList.length;i++){
        //                 if(onePfPjInfoList[i].pjStatus === 1){
        //                     $scope.checkCount =$scope.checkCount +1;
        //                 }
        //             }
        //         }
        //     )
        // }
    }
    ]);

