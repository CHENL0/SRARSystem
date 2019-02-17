MyApp
    .controller('missionController',['$scope', '$interval','taskService','notifyService', 'missionService', function ($scope,$interval,taskService,notifyService,missionService) {
        //get username from localStorage
        $scope.pageClass = 'mission';
        $scope.name = localStorage.getItem("data");

        $scope.taskInfoData = missionService.initTaskData();

        $scope.getTaskInfoData = function () {
            missionService.getTaskInfoListData($scope.name).then(
                function (response) {
                    $scope.taskInfos = response.taskInfos;
                }
            )
        };
        $scope.getTaskInfoData();

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

        $scope.submitTaskData = function (taskInfo) {
            missionService.submitTaskInfoData(taskInfo).then(
                function (value) {
                    alert("success");
                }
            )
        }
    }
    ]);

