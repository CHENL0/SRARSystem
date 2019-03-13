MyApp
    .controller('taskController',['$scope', '$uibModal', '$interval', 'taskService','checkService',
        'commonService', function ($scope, $uibModal,$interval, taskService, checkService, commonService) {
        //get username from localStorage
        $scope.pageClass = 'tasks';
        $scope.username = localStorage.getItem("data");
        commonService.validateLogin($scope.username);
        $scope.taskPageCount = 0;
        $scope.pjPageCount =0;
        $scope.taskStatus = 0;

        $scope.getPjInfoModelData = function(PjId) {
            checkService.getOnePjInfoData(PjId).then(
                function (response) {
                    $scope.pjInfoModel = response.projectInfo;
                }
            )
        };

        taskService.getTasksListData($scope.username).then(
            function (response) {
                $scope.taskInfoSliceList = commonService.sliceArr(response.taskInfoList,6)
                $scope.taskInfoList = $scope.taskInfoSliceList[$scope.taskPageCount];
            });

        $scope.taskNextPage = function(){
            if($scope.taskPageCount >= $scope.taskInfoSliceList.length -1){
                alert("this is the last page");
            }else {
                $scope.taskPageCount +=1;
                $scope.taskInfoList = $scope.taskInfoSliceList[$scope.taskPageCount];
            }
        };
        $scope.taskPrePage = function(){
            if($scope.taskPageCount === 0){
                alert("this is the first page");
            }else {
                $scope.taskPageCount -=1;
                $scope.taskInfoList = $scope.taskInfoSliceList[$scope.taskPageCount];
            }
        };


        taskService.getUserInfoData($scope.username).then(
            function (response) {
                $scope.userInfo = response.userInfo;
            }
        );

        $scope.getPjListData = function(){
            taskService.getOneUserPjInfoListData($scope.username).then(
                function (response) {
                    $scope.pjInfoListSort = response.oneUserPjInfoList.sort(checkService.compare("pjStatus"));
                    $scope.pjInfoSliceList = commonService.sliceArr($scope.pjInfoListSort,6)
                    $scope.pjInfoList = $scope.pjInfoSliceList[$scope.pjPageCount];
                }
            );
        };$scope.getPjListData();


        $scope.pjNextPage = function(){
            if($scope.pjPageCount >= $scope.pjInfoSliceList.length-1){
                alert("this is the last page");
            }else {
                $scope.pjPageCount +=1;
                $scope.pjInfoList = $scope.pjInfoSliceList[$scope.pjPageCount];
            }
        };
        $scope.pjPrePage = function(){
            if($scope.pjPageCount === 0){
                alert("this is the first page");
            }else {
                $scope.pjPageCount -=1;
                $scope.pjInfoList = $scope.pjInfoSliceList[$scope.pjPageCount];
            }
        };



        $scope.getTasksListData = function(){
            taskService.getTasksListData($scope.username).then(
                function (response) {
                    $scope.taskInfoList = response.taskInfoList;
                });
        }

        $scope.getTaskDetail = function (taskId) {
            taskService.getTaskInfoData(taskId).then(
                function (response) {
                    $scope.taskInfo = response.taskInfo;
                    var currentDate = dateFormat(new Date(), "yyyy-mm-dd");
                    $scope.countdown = taskService.getCountdownDate(currentDate,$scope.taskInfo.deadline);
                    // var countdown  = $interval(function () {
                    //     getCountdownDate($scope.deadlineDate , $scope.deadlineDate);
                    // },3000)
                }
            )

        };

        $scope.getOneStatusTask = function (statusData) {
            $scope.taskStatus = statusData;
            taskService.getOneStatusTaskList(statusData,$scope.username).then(
                function (response) {
                    $scope.taskInfoSliceList = commonService.sliceArr(response.oneStatusTaskList,6)
                    $scope.taskInfoList = $scope.taskInfoSliceList[$scope.taskPageCount];
                }
            )
        };

            $scope.getOneStatusPj = function (statusData) {
                $scope.pjStatus = statusData;
                taskService.getOneStatusPjList(statusData,$scope.username).then(
                    function (response) {
                        $scope.pjInfoSliceList = commonService.sliceArr(response.oneStatusPjList,6)
                        $scope.pjInfoList = $scope.pjInfoSliceList[$scope.pjPageCount];
                    }
                )
            };

        
        $scope.removeTaskInfoData = function (taskId) {
            taskService.removeTaskInfoData(taskId).then(
                function (response) {
                    alert("remove success");
                    if($scope.taskStatus ===1 ||$scope.taskStatus ===2 ||$scope.taskStatus ===3){
                        $scope.getOneStatusTask($scope.taskStatus);
                    }else {
                        $scope.getTasksListData();
                    }

                }
            )
        }
    }
    ]);

