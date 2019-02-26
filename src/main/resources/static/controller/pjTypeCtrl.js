MyApp
    .controller('pjTypeController',['$scope', '$interval', 'submitService','taskService','notifyService', 'pjTypeService','pjDetailService',
        function ($scope,$interval, submitService,taskService,notifyService,pjTypeService,pjDetailService) {
        //get username from localStorage
        $scope.pageClass = 'pjType';
        $scope.username = localStorage.getItem("data");
        $scope.pageType = 'add';
        $scope.disabledSubmit = true;
        $scope.disabledInput = true;
        $scope.changeBtnName = "Change";
        $scope.projectTypeData = pjTypeService.setPjTypeInfoData();
        
        $scope.submitPjTypeData = function () {
            if($scope.projectTypeData.pjType && $scope.projectTypeData.pjTypeDescription){
                pjTypeService.submitPjTypeData($scope.projectTypeData).then(
                    function (value) {
                        alert("submit success");
                    }
                )
            }else {
                alert("Haven't fill in complete information")
            }
        };

        $scope.changeDisabledForSubmit = function (checkAgree){
            if(checkAgree){
                $scope.disabledSubmit = false;
            }else {
                $scope.disabledSubmit = true;
            }
        };

            $scope.changeType = function (typeStr) {
                if(typeStr === 'add'){
                    $scope.pageType = 'add';
                }else if(typeStr === 'update'){
                    $scope.pageType = 'update';
                    $scope.getAllPjTypeInfoForShowing();
                }
            };

            $scope.getAllPjTypeInfoForShowing = function () {
                pjDetailService.getPjInfoListData().then(
                    function (value) {
                        $scope.pjTypeInfoList = value.pjInfoList;
                        $scope.getPjTypeNumber($scope.pjTypeInfoList);
                    }
                )
            };

            $scope.getPjTypeNumber = function (pjTypeInfoList){
                pjTypeService.getPjTypeNumberByPjTypeList(pjTypeInfoList).then(
                    function (value) {
                        // for(var i=0;i<$scope.pjTypeInfoList.length();i++){
                        //     value.getKey($scope.pjTypeInfoList)
                        // }
                        $scope.pjTypeNumbers = value.pjTypeList;
                    }
                )
            }

            $scope.editPjTypeInfo = function (pjTypeId) {
                pjTypeService.getPjTypeInfoByPjTypeId(pjTypeId).then(
                    function (value) {
                        $scope.pjTypeInfo = value.pjTypeInfo;
                    }
                )
            };

            $scope.updateAndChange = function (pjTypeId,pjType,PjTypeDescription) {
                if($scope.disabledInput){
                    $scope.disabledInput = false;
                    $scope.changeBtnName = "Confirm";
                    return;
                }else {
                    $scope.projectTypeData.pjTypeId = pjTypeId;
                    $scope.projectTypeData.pjType = pjType;
                    $scope.projectTypeData.pjTypeDescription = PjTypeDescription;
                    $scope.submitPjTypeData();
                    $scope.disabledInput = true;
                    $scope.changeBtnName = "Change";
                    var td = document.getElementById('updateBtn');//获取标签
                    td.setAttribute('data-dismiss','modal'); //设置标签属性data-dismiss=‘modal’
                }
            }

            $scope.close = function () {
                $scope.disabledInput = true;
                $scope.changeBtnName = "Change";
            }

            $scope.deletePjTypeInfo = function (pjTypeId,pjType) {
                for(var i = 0;i<$scope.pjTypeNumbers.length;i++){
                    if(pjType === $scope.pjTypeNumbers[i].name && $scope.pjTypeNumbers[i].number !=0){
                        alert("sorry,the type have people,you can't delete");
                        break;
                    }else if(pjType === $scope.pjTypeNumbers[i].name && $scope.pjTypeNumbers[i].number ===0) {
                        pjTypeService.deletePjTypeInfo(pjTypeId).then(
                            function (value) {
                                $scope.getAllPjTypeInfoForShowing();
                                alert("delete success");
                            }
                        );
                        break;
                    }
                }

            }
    }
    ]);

