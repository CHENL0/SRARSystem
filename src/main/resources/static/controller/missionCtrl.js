MyApp
    .controller('missionController',['$scope', '$interval','taskService','notifyService',
        'missionService','commonService','checkService',
        function ($scope,$interval,taskService,notifyService,missionService,commonService,checkService) {
        //get username from localStorage
        $scope.pageClass = 'mission';
        $scope.name = localStorage.getItem("data");
        $scope.taskPageCount = 0;
        $scope.taskInfoData = missionService.initTaskData();
        $scope.taskIdModal = '';

        $scope.getTaskInfoData = function () {
            missionService.getTaskInfoListData($scope.name).then(
                function (response) {
                    $scope.taskInfoSliceList = commonService.sliceArr(response.taskInfos.sort(checkService.compare("taskStatus")),6);
                    $scope.taskInfos = $scope.taskInfoSliceList[$scope.taskPageCount];
                }
            )
        };
        $scope.getTaskInfoData();

            $scope.notifyNextPage = function(){
                if($scope.taskPageCount >= $scope.taskInfoSliceList.length -1){
                    alert("this is the last page");
                }else {
                    $scope.taskPageCount +=1;
                    $scope.taskInfos = $scope.taskInfoSliceList[$scope.taskPageCount];
                }
            };
            $scope.notifyPrePage = function(){
                if($scope.taskPageCount === 0){
                    alert("this is the first page");
                }else {
                    $scope.taskPageCount -=1;
                    $scope.taskInfos = $scope.taskInfoSliceList[$scope.taskPageCount];
                }
            };


        $scope.getTaskDetail = function (taskId) {
            taskService.getTaskInfoData(taskId).then(
                function (response) {
                    $scope.taskInfo = response.taskInfo;
                    var currentDate = dateFormat(new Date(), "yyyy-mm-dd");
                    $scope.countdown = taskService.getCountdownDate(currentDate,$scope.taskInfo.deadline);
                }
            )
        };



        $scope.download = function (taskId, taskName) {
            missionService.downloadTask(taskId).then(
                function (response) {
                    var link = document.createElement('a');
                    var blob = new Blob([response], {type: 'application/zip'});
                    link.setAttribute('href', window.URL.createObjectURL(blob));
                    link.setAttribute('download', taskName );
                    link.style.visibility = 'hidden';
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                }
            )
        };

        $scope.getUserDetail = function (userName) {
            missionService.getUserInfoData(userName).then(
                function (response) {
                    $scope.userInfo = response.userInfo;
                }
            )
        };

        $scope.editTaskStatus = function (pjId,taskId) {
            $scope.selectStatusData = missionService.getStatusData ();
            checkService.getOnePjInfoData(pjId).then(
                function (value) {
                    $scope.onePjInfo = value.projectInfo;
                    $scope.taskIdModal = taskId;
                }
            )
        };


        $scope.submitTaskData = function () {
            $scope.taskInfoData.pfName = $scope.name;
            if(!$scope.taskInfoData.userName || !$scope.taskInfoData.taskName ||
                !$scope.taskInfoData.deadline || !$scope.taskInfoData.taskDescription ||
                !$scope.taskInfoData.pjId){
                alert("Haven't fill in complete information");
                return;
            }
            missionService.getPjInfoDataForPjTitle($scope.taskInfoData.pjId).then(
                function (value) {
                    $scope.taskInfoData.pjTitle = value.projectInfo.pjTitle;
                    $scope.submit();
                }
            );
        };
        $scope.submit = function (){
            missionService.submitTaskInfoData($scope.taskInfoData).then(
                function (value) {
                    if(value.responseType === "SUCCESS"){
                        alert("create success");
                        $scope.getTaskInfoData();
                        notifyService.setNotify($scope.name,$scope.taskInfoData.userName,'Task',2,$scope.taskInfoData.taskName);
                    }

                }
            )
        };



        $scope.getUserNameList = function () {
            missionService.getUserNameListByPfName($scope.name).then(
                function (value) {
                    $scope.userNameList = value.projectInfos;
                }
            )
        };

        $scope.getProjectTitle = function (userName) {
            missionService.getPjTitleForSelecting($scope.name,userName).then(
                function (value) {
                    $scope.pjInfoList = value.projectInfos;
                }
            )
        };

        $scope.changeTaskStatus = function (pjId,selectedStatusCode) {
            if(!selectedStatusCode){
                alert("the select can't be null");
                return;
            }
            missionService.changeTaskStatus(pjId,selectedStatusCode).then(
                function (value) {
                    $scope.getTaskInfoData();
                    $scope.selectedStatusCode = "";
                    alert("change success");
                }
            )
        };

        $scope.deleteTaskInfo = function (taskId) {
            taskService.removeTaskInfoDataForAudit(taskId).then(
                function (value) {
                    alert("delete success");
                    $scope.getTaskInfoData();
                }
            )
        }
    }
    ]);

