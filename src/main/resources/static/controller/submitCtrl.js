MyApp
    .controller('submitController',['$scope', '$interval', 'submitService', function ($scope,$interval, submitService) {
        //get username from localStorage
        $scope.pageClass = 'submit';
        $scope.username = localStorage.getItem("data");
        $scope.checkPfType = "基础研究";
        $scope.disabledSubmit = true;
        $scope.count = 0;
        $scope.datas=[];

        $scope.hidden=true;//选择框是否隐藏
        // $scope.searchField='';//文本框数据

        $scope.projectData = submitService.setPjInfoData();
        $scope.projectData.pjUser = $scope.username;

        submitService.getOneUserPjInfoListData($scope.username).then(
            function (response) {
                $scope.pjInfoList = response.oneUserPjInfoList;
                for(var i = 0;i<$scope.pjInfoList.length;i++){
                    if($scope.pjInfoList[i].pjStatus === 2){
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
        $scope.change=function(x){;
            $scope.projectData.pjReviewer=x;
            $scope.hidden=true;
        }



        submitService.getPjTypeListData().then(
            function (response) {
                $scope.pjTypeList = response.pjInfoList;
            });

        $scope.setOneTypePfInfoListData = function(){
            $scope.datas = [];
            for(var i = 0;i<$scope.respPfInfoListData.length;i++){
                if($scope.checkPfType === $scope.respPfInfoListData[i].pfType ){
                    $scope.projectData.pjType = $scope.checkPfType;
                    $scope.datas.push($scope.respPfInfoListData[i].pfName);
                }else if($scope.checkPfType === $scope.respPfInfoListData[i].pfType){
                    $scope.projectData.pjType = $scope.checkPfType;
                    $scope.datas.push($scope.respPfInfoListData[i].pfName);
                }else if($scope.checkPfType === $scope.respPfInfoListData[i].pfType){
                    $scope.projectData.pjType = $scope.checkPfType;
                    $scope.datas.push($scope.respPfInfoListData[i].pfName);
                }
                $scope.tempdatas = $scope.datas; //下拉框选项副本
            }
        };

        submitService.getPfInfoListData().then(
            function (response) {
                $scope.respPfInfoListData = response.professorInfoList;
                $scope.setOneTypePfInfoListData();
            });




        //获取的数据值与下拉选逐个比较，如果包含则放在临时变量副本，并用临时变量副本替换下拉选原先的数值，如果数据为空或找不到，就用初始下拉选项副本替换
        $scope.changeKeyValue=function(v){
            var newDate=[];  //临时下拉选副本
            //如果包含就添加
            angular.forEach($scope.datas ,function(data,index,array){
                if(data.indexOf(v)>=0){
                    newDate.unshift(data);
                }
            });
            //用下拉选副本替换原来的数据
            $scope.datas=newDate;
            // $scope.projectData.pjReviewer = v;
            // if($scope.datas.length ===0){
            //     $scope.pfError = true;
            //     $scope.hidden=true;
            // }
            //下拉选展示
            $scope.hidden=false;
            //如果不包含或者输入的是空字符串则用初始变量副本做替换
            if($scope.datas.length==0 || ''==v){
                $scope.datas=$scope.tempdatas;
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

        $scope.changeDisabledForSubmit = function () {
            if($scope.checkAgree){
                $scope.disabledSubmit = false;
            }else {
                $scope.disabledSubmit = true;
            }

        };
        
        $scope.submit = function () {
            if($scope.validateAllData()){
                alert("ok ,the message have finished");
                $scope.projectData.pjReviewer = $scope.projectData.pjReviewer[0]
                submitService.submitData($scope.file,$scope.projectData).then(
                    function (response) {
                        if(response.responseType === "SUCCESS"){
                            alert("Congratulations, and your information submitted to success");
                            window.location.href = "index.html#/userProfile";
                        }else {
                            alert("sorry,your information submitted to error")
                        }
                    }
                )
            }
        };

        $scope.validateAllData = function () {
            if(!$scope.projectData.pjReviewer){
                alert("professor have not selected");
                return false;
            }else if(!$scope.datas.includes($scope.projectData.pjReviewer[0])){
                alert("sorry,the professor is not correct");
                return false;
            }
            if(!$scope.file){
                alert("file message have not uploaded");
                return false;
            }
            if(!($scope.projectData.pjDescription.trim())){
                alert("description message have not finish");
                return false;
            }
            return true;
        };

    }
    ]);

