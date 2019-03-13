MyApp
    .controller('submitController',['$scope', '$interval', 'submitService','taskService',
        'notifyService', 'commonService',
        function ($scope,$interval, submitService,taskService,notifyService,commonService) {
        //get username from localStorage
        $scope.pageClass = 'submit';
        $scope.username = localStorage.getItem("data");
            commonService.validateLogin($scope.username);
        $scope.checkPfType = "基础研究";
        $scope.disabledSubmit = true;
        $scope.submitType = 'submitProject';
        $scope.count = 0;
        $scope.datasForPj=[];

        $scope.hidden=true;//选择框是否隐藏
        // $scope.searchField='';//文本框数据

        $scope.projectData = submitService.setPjInfoData();
        $scope.projectData.pjUser = $scope.username;

        submitService.getOneUserPjInfoListData($scope.username).then(
            function (response) {
                $scope.pjInfoList = response.oneUserPjInfoList;
                for(var i = 0;i<$scope.pjInfoList.length;i++){
                    if($scope.pjInfoList[i].pjStatus === 2 || $scope.pjInfoList[i].pjStatus === 1){
                        $scope.count += 1;
                    }
                }
                if($scope.count>=3){
                    $scope.isOverCount =true;
                }else {
                    $scope.isOverCount =false;
                }
            }
        );


        //将下拉选的数据值赋值给文本框
        $scope.changeForPj=function(x){;
            $scope.validatePf(x);
            $scope.projectData.pjReviewer=x;
            $scope.hidden=false;
        };
        //将下拉选的数据值赋值给文本框
        $scope.changeForTask=function(x){;
            $scope.taskData.taskName=x;
            $scope.hidden=false;
        }



        submitService.getPjTypeListData().then(
            function (response) {
                $scope.pjTypeList = response.pjInfoList;
            });

        $scope.setOneTypePfInfoListData = function(){
            $scope.datasForPj = [];
            for(var i = 0;i<$scope.respPfInfoListData.length;i++){
                if($scope.checkPfType === $scope.respPfInfoListData[i].pfType ){
                    $scope.projectData.pjType = $scope.checkPfType;
                    $scope.datasForPj.push($scope.respPfInfoListData[i].pfName);
                }else if($scope.checkPfType === $scope.respPfInfoListData[i].pfType){
                    $scope.projectData.pjType = $scope.checkPfType;
                    $scope.datasForPj.push($scope.respPfInfoListData[i].pfName);
                }else if($scope.checkPfType === $scope.respPfInfoListData[i].pfType){
                    $scope.projectData.pjType = $scope.checkPfType;
                    $scope.datasForPj.push($scope.respPfInfoListData[i].pfName);
                }
                $scope.tempdatasForPj = $scope.datasForPj; //下拉框选项副本
            }
        };

        submitService.getPfInfoListData().then(
            function (response) {
                $scope.respPfInfoListData = response.professorInfoList;
                $scope.setOneTypePfInfoListData();
            });




        //获取的数据值与下拉选逐个比较，如果包含则放在临时变量副本，并用临时变量副本替换下拉选原先的数值，如果数据为空或找不到，就用初始下拉选项副本替换
        $scope.changeKeyValueForPj=function(v){
            var newDate=[];  //临时下拉选副本
            //如果包含就添加
            angular.forEach($scope.datasForPj ,function(data,index,array){
                if(data.indexOf(v)>=0){
                    newDate.unshift(data);
                }
            });
            //用下拉选副本替换原来的数据
            $scope.datasForPj=newDate;
            //下拉选展示
            $scope.hidden=false;
            //如果不包含或者输入的是空字符串则用初始变量副本做替换
            if($scope.datasForPj.length==0 || ''==v){
                $scope.datasForPj=$scope.tempdatasForPj;
            }

        };

        $scope.changeKeyValueForTask=function(v){
            var newDate=[];  //临时下拉选副本
            //如果包含就添加
            angular.forEach($scope.datasForTask ,function(data,index,array){
                if(data.indexOf(v)>=0){
                    newDate.unshift(data);
                }
            });
            //用下拉选副本替换原来的数据
            $scope.datasForTask=newDate;
            //下拉选展示
            $scope.hidden=false;
            //如果不包含或者输入的是空字符串则用初始变量副本做替换
            if($scope.datasForTask.length==0 || ''==v){
                $scope.datasForTask=$scope.tempdatasForTask;
            }

        };



        submitService.getUserInfoData($scope.username).then(
            function (response) {
                $scope.userInfo = response.userInfo;
            }
        );

        $scope.changePfType =function (pfType) {
            $scope.checkPfType = pfType;
            $scope.projectData.pjType = pfType;
            $scope.setOneTypePfInfoListData();
        }

        $scope.validateFiles = function () {
            var file = document.getElementById("fileUpload").files[0];
            $scope.file = file;
            if(!file){
                return;
            }
            var suffix = file.name.split(".")[1];
            var fileSize = file.size;
            if(suffix === "zip" || suffix === "rar"){
                $scope.fileFromatError = false;
                if(fileSize < 40000000){
                    $scope.fileSizeError = false;
                }else {
                    $scope.file = '';
                    $scope.fileSizeError = true;
                }
            }else {
                $scope.file = '';
                $scope.fileFromatError = true;
            }
            $scope.$apply();
        }

        $scope.changeDisabledForSubmit = function (checkAgree){
            if(checkAgree){
                $scope.disabledSubmit = false;
            }else {
                $scope.disabledSubmit = true;
            }

        };
        
        $scope.submit = function () {
            if($scope.submitType === 'submitProject'){
                if($scope.validateAllDataForPj()){
                        alert("ok ,the message have finished");
                        $scope.projectData.pjReviewer = $scope.projectData.pjReviewer[0];
                        $scope.projectData.pjUser = $scope.username;
                        submitService.submitDataForPj($scope.file,$scope.projectData).then(
                            function (response) {
                                if(response.responseType === "SUCCESS"){
                                    alert("Congratulations, and your information submitted to success");
                                    window.location.href = "index.html#/tasks";
                                }else {
                                    alert("sorry,your information submitted to error")
                                }
                            }
                        )
                }
            }else if($scope.submitType === 'submitTask'){
                if($scope.validateAllDataForTask()){
                    alert("ok ,the message have finished");
                    $scope.taskData.taskName = $scope.taskData.taskName[0];
                    submitService.submitDataForTask($scope.file,$scope.taskData).then(
                        function (response) {
                            if(response.responseType === "SUCCESS"){
                                alert("Congratulations, and your information submitted to success");
                                notifyService.setNotifyForAudit($scope.name,$scope.taskData.pfName,"Task",2,$scope.taskData.taskName);
                                window.location.href = "index.html#/tasks";
                            }else {
                                alert("sorry,your information submitted to error")
                            }
                        }
                    )
                }
            }

        };
        $scope.validateAllDataForTask = function (){
            if(!$scope.taskData.taskName){
                alert("task have not selected");
                return false;
            }else if(!$scope.datasForTask.includes($scope.taskData.taskName[0])){
                alert("sorry,the task is not correct");
                return false;
            }
            if(!$scope.file){
                alert("file message have not uploaded");
                return false;
            }
            if(!($scope.taskData.taskMessage.trim())){
                alert("commit message have not finish");
                return false;
            }
            return true;
        };

        $scope.validatePf = function (pfName) {
            submitService.validatePf(pfName,$scope.name).then(
                function (value) {
                    if(value.responseType){
                        $scope.pfError=false;
                        $scope.hidden=true;
                    }else {
                        $scope.pfError=true;
                        $scope.hidden=true;
                    }
                }
            )
        };

        $scope.validateTitle = function(){
            if(!$scope.projectData.pjTitle){
                $scope.titleFromatError = true;
                $scope.titleSizeError = false;
            }else if($scope.projectData.pjTitle.length >20){
                $scope.titleFromatError = false;
                $scope.titleSizeError = true;
            }else {
                $scope.titleSizeError = false;
                $scope.titleFromatError = false;
            }
        };

        $scope.validateAllDataForPj = function () {
            if(!$scope.projectData.pjReviewer){
                alert("professor have not selected");
                return false;
            }else if(!$scope.datasForPj.includes($scope.projectData.pjReviewer[0])){
                alert("sorry,the professor is not correct");
                return false;
            }else if($scope.pfError){
                alert("professor can't select yourself");
                return false;
            }
            if(!$scope.file){
                alert("file message have not uploaded");
                return false;
            }
            if(!$scope.projectData.pjTitle){
                alert("pjTitle message should not be null");
                return false;
            }else if($scope.titleSizeError){
                alert("pjTitle message have not correct");
                return false;
            }
            if(!($scope.projectData.pjDescription.trim())){
                alert("description message have not finish");
                return false;
            }
            return true;
        };

        $scope.initTaskInfo = function (){
            taskService.getTasksListData($scope.username).then(
                function (response) {
                    $scope.taskInfoList = response.taskInfoList;
                    $scope.datasForTask =[];
                    for(var i = 0;i<$scope.taskInfoList.length; i++){
                        $scope.datasForTask.push($scope.taskInfoList[i].taskName)
                    }
                    $scope.tempdatasForTask = $scope.datasForTask;
                }
            );
        };

        $scope.getChangeSubmitType = function (type) {
            if(type === "submitProject"){
                $scope.projectData = submitService.setPjInfoData();
                $scope.setOneTypePfInfoListData();
            }else if(type === "submitTask"){
                $scope.projectData = submitService.setPjInfoData();
                $scope.taskData = submitService.setTaskInfoData();
                $scope.initTaskInfo();

            }
            $scope.submitType = type;

        }

    }
    ]);

