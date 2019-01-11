IndexApp
    .controller('indexController',['$scope', '$uibModal', '$interval', 'indexService', function ($scope, $uibModal,$interval, indexService) {
        //get username from localStorage
        $scope.username = localStorage.getItem("data");

        indexService.getTasksListData($scope.username).then(
            function (response) {
                $scope.taskInfoList = response.taskInfoList;
            });

        indexService.getUserInfoData($scope.username).then(
            function (response) {
                $scope.userInfo = response.userInfo;
            }
        );

        $scope.getTasksListData = function(){
            indexService.getTasksListData($scope.username).then(
                function (response) {
                    $scope.taskInfoList = response.taskInfoList;
                });
        }

        $scope.logout = function () {
            localStorage.clear();
            window.location.href = "loginSign.html";
        }
        $scope.getTaskDetail = function (taskId) {
            indexService.getTaskInfoData(taskId).then(
                function (response) {
                    $scope.taskInfo = response.taskInfo;
                    var currentDate = dateFormat(new Date(), "yyyy-mm-dd");
                    $scope.countdown = indexService.getCountdownDate(currentDate,$scope.taskInfo.deadline);
                    // var countdown  = $interval(function () {
                    //     getCountdownDate($scope.deadlineDate , $scope.deadlineDate);
                    // },3000)
                }
            )

        };

        $scope.getOneStatusTask = function (statusData) {
            indexService.getOneStatusTaskList(statusData).then(
                function (response) {
                    $scope.taskInfoList = response.oneStatusTaskList;
                }
            )
        }
        

        // $scope.openTaskModal = function () {
        //     $scope.items = "111";
        //     var modalInstance = $uibModal.open({
        //         animation: false,//打开时的动画开关
        //         templateUrl: 'myModalContent.html',//模态框的页面内容,这里的url是可以自己定义的,也就意味着什么都可以写
        //         controller: 'ModalInstanceCtrl',//这是模态框的控制器,是用来控制模态框的
        //         size: 'md',//模态框的大小尺寸 sm, md, lg
        //         resolve: {//这是一个入参,这个很重要,它可以把主控制器中的参数传到模态框控制器中
        //             items: function () {//items是一个回调函数
        //                 return $scope.items;//这个值会被模态框的控制器获取到
        //             }
        //         }
        //     });
            // modalInstance.result.then(function (selectedItem) {//这是一个接收模态框返回值的函数
            //     $scope.selected = selectedItem;//模态框的返回值
            // }, function () {
            //     alert("11111");
            // });
        // };


    }
    ]);

